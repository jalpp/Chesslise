package lichess;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import discord.helpermodules.PuzzleSolutionHelperModule;
import setting.SettingHandler;
import setting.SettingSchema;

public class LichessPuzzleSearch {

    private static final int MAX_PUZZLE_SEARCH = 5000;
    private static final int MAX_RETRY_COUNT = 2; // Retry count for calling the external API if the API is down

    /**
     * gets the puzzle values in the csv line for given search column
     *
     * @param searchColumn the search column (puzzle theme)
     * @param searchValue  the search value (puzzle theme value)
     * @param maxPuzzles   max puzzles to search for
     * @return return the csv line
     */

    private static final String puzzleUrl = "https://api.chessgubbins.com/puzzles/random?themes=";

    private static final HttpClient client = HttpClient.newHttpClient();

    private static List<List<String>> searchPuzzlesGubbinsApi(String searchColumn, String searchValue, int maxPuzzles,
            String userId) {
        List<String> neededColumns = Arrays.asList("lichessId", "FEN", "moves", "rating", "themes", "gameURL");
        List<List<String>> results = new ArrayList<>();
        int retryCount = 0;

        try {
            SettingSchema settingSchema = SettingHandler.getUserSetting(userId);
            int puzzleCount = 0;

            while (retryCount < MAX_RETRY_COUNT) {
                try {
                    HttpRequest request = HttpRequest.newBuilder()
                            .uri(URI.create(puzzleUrl + searchValue + "&ratingFrom="
                                    + getDifficultyLevels(settingSchema.getPuzzleDifficulty())[0] + "&"
                                    + getDifficultyLevels(settingSchema.getPuzzleDifficulty())[1]))
                            .header("Accept", "application/json")
                            .header("User-Agent", "ChessPuzzleApp/1.0")
                            .GET()
                            .build();

                    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                    // Check HTTP status code
                    if (response.statusCode() != 200) {
                        System.err.println("HTTP Error " + response.statusCode() + " for: " + puzzleUrl + searchValue);
                        retryCount++;
                        continue;
                    }

                    // Validate response body
                    String responseBody = response.body();
                    if (responseBody == null || responseBody.trim().isEmpty()) {
                        System.err.println("Empty response body for: " + puzzleUrl + searchValue);
                        retryCount++;
                        continue;
                    }

                    // Log response for debugging (remove in production)
                    System.out.println("API Response: " + responseBody);

                    // Parse JSON with proper error handling
                    JSONParser parser = new JSONParser();
                    Object parsed;

                    try {
                        parsed = parser.parse(responseBody);
                    } catch (Exception e) {
                        System.err.println("JSON parsing failed for response: " + responseBody);
                        System.err.println("Parse error: " + e.getMessage());
                        retryCount++;
                        continue;
                    }

                    // Verify parsed object is JSONObject
                    if (!(parsed instanceof JSONObject)) {
                        System.err.println("Response is not a JSON object. Response: " + responseBody);
                        retryCount++;
                        continue;
                    }

                    JSONObject jsonObject = (JSONObject) parsed;

                    // Safely extract rating with null check
                    Object ratingObj = jsonObject.get("rating");
                    if (ratingObj == null) {
                        System.err.println("Rating field is missing from JSON response");
                        retryCount++;
                        continue;
                    }

                    int difficultyLevel;
                    try {
                        difficultyLevel = Integer.parseInt(ratingObj.toString());
                    } catch (NumberFormatException e) {
                        System.err.println("Invalid rating format: " + ratingObj.toString());
                        retryCount++;
                        continue;
                    }

                    // Check difficulty validity
                    String userDifficulty = Optional.ofNullable(settingSchema.getPuzzleDifficulty()).orElse("Medium");
                    if (!isValidDifficulty(difficultyLevel, userDifficulty)) {
                        retryCount++;
                        continue;
                    }

                    // Extract puzzle data
                    List<String> puzzleStringList = new ArrayList<>();
                    boolean hasValidData = false;

                    for (String columnName : neededColumns) {
                        Object columnValue = jsonObject.get(columnName);
                        if (columnValue != null) {
                            puzzleStringList.add(columnValue.toString());
                            hasValidData = true;
                        } else {
                            // Add empty string for missing fields to maintain column order
                            puzzleStringList.add("");
                        }
                    }

                    if (!hasValidData) {
                        System.err.println("No valid data found in puzzle response");
                        retryCount++;
                        continue;
                    }

                    results.add(puzzleStringList);
                    puzzleCount++;

                    // Return immediately after finding one puzzle (based on original logic)
                    System.out.println("Successfully found " + puzzleCount + " puzzle(s).");
                    return results;

                } catch (IOException e) {
                    System.err.println("Network error occurred: " + e.getMessage());
                    retryCount++;
                    continue;
                } catch (InterruptedException e) {
                    System.err.println("Request was interrupted: " + e.getMessage());
                    Thread.currentThread().interrupt(); // Restore interrupted status
                    throw new RuntimeException("Request interrupted", e);
                } catch (Exception e) {
                    System.err.println("Unexpected error occurred: " + e.getMessage());
                    e.printStackTrace();
                    retryCount++;
                    continue;
                }
            }

            // If all retries failed, fall back to local search
            System.err.println("Max retry count reached. Falling back to local search.");
            String userDifficulty = Optional.ofNullable(settingSchema.getPuzzleDifficulty()).orElse("Medium");
            results = searchPuzzleLocal(searchColumn, searchValue, maxPuzzles, userDifficulty);

            System.out.println("Total puzzles found: " + results.size());

        } catch (Exception e) {
            System.err.println("Fatal error in searchPuzzlesGubbinsApi: " + e.getMessage());
            e.printStackTrace();
        }

        return results;
    }

    private static List<List<String>> searchPuzzleLocal(String searchColumn, String searchValue, int maxPuzzles,
            String difficulty) {
        String currentFolderPath = Paths.get("").toAbsolutePath().toString();
        String csvFilePath = currentFolderPath + File.separator + "lichess_db_puzzle.csv";
        List<String> neededColumns = Arrays.asList("PuzzleId", "FEN", "Moves", "Rating", "Themes", "GameUrl");

        List<List<String>> results = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
            String headerLine = br.readLine();
            if (headerLine == null)
                return results;

            String[] headers = headerLine.split(",");
            Map<String, Integer> columnIndexMap = new HashMap<>();
            for (int i = 0; i < headers.length; i++) {
                columnIndexMap.put(headers[i].trim(), i);
            }

            Integer searchIndex = columnIndexMap.get(searchColumn);
            List<Integer> neededIndexes = new ArrayList<>();
            for (String col : neededColumns) {
                if (columnIndexMap.containsKey(col)) {
                    neededIndexes.add(columnIndexMap.get(col));
                }
            }

            if (searchIndex == null || neededIndexes.isEmpty()) {
                System.out.println("Invalid column names.");
                return results;
            }

            String line;
            int puzzleCount = 0;
            while ((line = br.readLine()) != null && puzzleCount < maxPuzzles) {
                String[] values = line.split(",", -1);
                if (values.length > searchIndex) {
                    String[] themes = values[searchIndex].split(" ");
                    if (Arrays.asList(themes).contains(searchValue)) {
                        List<String> puzzleData = new ArrayList<>();
                        for (int index : neededIndexes) {
                            if (index < values.length) {
                                puzzleData.add(values[index].trim());
                            }
                        }
                        if (isValidDifficulty(Integer.parseInt(puzzleData.get(3)), difficulty)) {
                            results.add(puzzleData);
                            puzzleCount++;
                        }
                    }
                }
            }
            System.out.println("Found " + puzzleCount + " puzzles.");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return results;
    }

    private static List<String> getRandomPuzzle(List<List<String>> puzzles) {
        if (puzzles == null || puzzles.isEmpty()) {
            return Collections.emptyList();
        }
        Random random = new Random();
        int randomIndex = random.nextInt(puzzles.size());
        return puzzles.get(randomIndex);
    }

    public static LichessDBPuzzle getDatabasePuzzle(String themeSearch, String userId) {
        List<String> randomPuzzle = getRandomPuzzle(
                searchPuzzlesGubbinsApi("Themes", themeSearch, MAX_PUZZLE_SEARCH, userId));
        List<String> moveList = Arrays.asList(randomPuzzle.get(2).split(" "));
        PuzzleSolutionHelperModule.saveGeneratedPuzzleData((String) randomPuzzle.get(1), moveList, userId);
        return new LichessDBPuzzle(randomPuzzle.get(1), randomPuzzle.get(3),
                randomPuzzle.get(5));
    }

    private static int[] getDifficultyLevels(String diff) {
        switch (diff) {
            case "Easy" -> {
                return new int[] { 0, 1200 };
            }
            case "Medium" -> {
                return new int[] { 1200, 2000 };
            }
            case "Hard" -> {
                return new int[] { 2000, 3000 };
            }
        }

        return new int[] { 0, 3000 };
    }

    private static boolean isValidDifficulty(int rating, String difficulty) {
        try {

            return switch (difficulty) {
                case "Easy" -> rating <= 1200;
                case "Medium" -> (rating > 1200 && rating <= 2000);
                case "Hard" -> (rating > 2000);
                default -> false;
            };

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
