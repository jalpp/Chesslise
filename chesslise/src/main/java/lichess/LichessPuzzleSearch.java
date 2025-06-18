package lichess;
import java.io.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Paths;
import java.util.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import setting.SettingHandler;
import setting.SettingSchema;

public class LichessPuzzleSearch {

    private static final int MAX_PUZZLE_SEARCH = 5000;
    private static final int MAX_RETRY_COUNT = 2; //Retry count for calling the external API if the API is down

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


    private static List<List<String>> searchPuzzles(String searchColumn, String searchValue, int maxPuzzles, String userId) {
        List<String> neededColumns = Arrays.asList("lichessId", "FEN", "moves", "rating", "themes", "gameURL");
        List<List<String>> results = new ArrayList<>();
        int retryCount = 0;
        try {
            SettingSchema settingSchema;
            settingSchema = SettingHandler.getUserSetting(userId);
            int puzzleCount = 0;
            while (retryCount < MAX_RETRY_COUNT){
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(puzzleUrl+searchValue))
                        .GET()
                        .build();
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                if (response.statusCode() != 200 || response.body() == null || response.body().isEmpty()) {
                    System.err.println("Invalid response for: " + puzzleUrl + searchValue);
                    retryCount++;
                    continue;
                }
                JSONParser parser = new JSONParser();
                JSONObject jsonObject = (JSONObject) parser.parse(response.body());
                int difficultyLevel = Integer.parseInt(jsonObject.get("rating").toString());
                if(!isValidDifficulty(difficultyLevel, settingSchema.getDifficultyLevel())){
                    continue;
                }
                List<String> puzzleStringList = new ArrayList<>();
                for(String columns: neededColumns){
                    Object column = jsonObject.get(columns);
                    if(column!=null) {
                        puzzleStringList.add(column.toString());
                    }
                }
                if(puzzleStringList.isEmpty()){
                    continue;
                }
                results.add(puzzleStringList);
                return results;
            }
            if(retryCount == MAX_RETRY_COUNT){
                results = searchPuzzleLocal(searchColumn,searchValue,maxPuzzles,settingSchema.getDifficultyLevel());
            }
            System.out.println("Found " + puzzleCount + " puzzles.");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (Exception e){
            e.printStackTrace();
        }

        return results;
    }

    private static List<List<String>> searchPuzzleLocal(String searchColumn, String searchValue, int maxPuzzles, String difficulty){
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
                        if(isValidDifficulty(Integer.parseInt(puzzleData.get(3)),difficulty)){
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

    public static LichessDBPuzzle getDatabasePuzzle(String themeSearch,String userId) {
        List<String> randomPuzzle = getRandomPuzzle(searchPuzzles("Themes", themeSearch, MAX_PUZZLE_SEARCH,userId));
        return new LichessDBPuzzle(randomPuzzle.get(1), randomPuzzle.get(3),
                randomPuzzle.get(5));
    }

    private static boolean isValidDifficulty(int rating , String difficulty){
        try{

            return switch (difficulty) {
                case "Easy" -> rating <= 1200;
                case "Medium" -> (rating > 1200 && rating <= 2000);
                case "Hard" -> (rating > 2000);
                default -> false;
            };

        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
