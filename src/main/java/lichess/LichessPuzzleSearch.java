package lichess;

import java.io.*;
import java.nio.file.Paths;
import java.util.*;

public class LichessPuzzleSearch {

   // change to higher if needed, 5k works well in 3s search time good for Discord
    private static final int MAX_PUZZLE_SEARCH = 5000;

    public static void main(String[] args) {

        List<List<String>> list = searchPuzzles("Themes", "master", MAX_PUZZLE_SEARCH);

        System.out.println(getRandomPuzzle(list));
    }

    private static List<List<String>> searchPuzzles(String searchColumn, String searchValue, int maxPuzzles) {

        String currentFolderPath = Paths.get("").toAbsolutePath().toString();
        String csvFilePath = currentFolderPath + File.separator + "lichess_db_puzzle.csv";
        List<String> neededColumns = Arrays.asList("PuzzleId", "FEN", "Moves", "Rating", "Themes", "GameUrl");

        List<List<String>> results = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
            String headerLine = br.readLine();
            if (headerLine == null) return results;

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
                        results.add(puzzleData);
                        puzzleCount++;
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

    public static LichessDBPuzzle getDatabasePuzzle(String themeSearch){
        List<String> randomPuzzle = getRandomPuzzle(searchPuzzles("Themes", themeSearch, MAX_PUZZLE_SEARCH));
        return new LichessDBPuzzle(randomPuzzle.get(0), randomPuzzle.get(1), randomPuzzle.get(2), randomPuzzle.get(3), randomPuzzle.get(4), randomPuzzle.get(5));
    }
}

