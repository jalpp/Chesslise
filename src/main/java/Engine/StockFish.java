package Engine;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;

public class StockFish {



    public StockFish(){

    }

    public static HashMap<String, String> getUserFen = new HashMap<>();




    public static String getTopEngineLine(int depth, String fen){
        try {
            String apiUrl = "https://stockfish.online/api/stockfish.php";

            String mode = "lines";

            String queryString = String.format("fen=%s&depth=%d&mode=%s",
                    URLEncoder.encode(fen, StandardCharsets.UTF_8),
                    depth,
                    URLEncoder.encode(mode, StandardCharsets.UTF_8));

            URL url = new URL(apiUrl + "?" + queryString);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                StringBuilder response = new StringBuilder();

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }

                reader.close();

                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(response.toString());

                JsonNode dataNode = jsonNode.get("data");
                if (dataNode != null) {
                    return dataNode.asText();
                } else {
                    System.out.println("No 'data' field found in the response.");
                }
            } else {
                System.out.println("Failed to make API request. Response Code: " + responseCode);
            }

            connection.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "error!";
    }



    public static String getEvalForFEN(int depth, String fen){
        try {

            String apiUrl = "https://stockfish.online/api/stockfish.php";

            String mode = "eval";


            String queryString = String.format("fen=%s&depth=%d&mode=%s",
                    URLEncoder.encode(fen, StandardCharsets.UTF_8),
                    depth,
                    URLEncoder.encode(mode, StandardCharsets.UTF_8));


            URL url = new URL(apiUrl + "?" + queryString);


            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();


            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                StringBuilder response = new StringBuilder();

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }

                reader.close();

                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(response.toString());

                JsonNode dataNode = jsonNode.get("data");
                if (dataNode != null) {
                    return dataNode.toString();
                } else {
                    System.out.println("No 'data' field found in the response.");
                }
            } else {
                System.out.println("Failed to make API request. Response Code: " + responseCode);
            }

            // Close the connection
            connection.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "Error!";
    }



    public static String getBestMove(int depth, String fen){
        try {

            String apiUrl = "https://stockfish.online/api/stockfish.php";

            String mode = "bestmove";


            String queryString = String.format("fen=%s&depth=%d&mode=%s",
                    URLEncoder.encode(fen, StandardCharsets.UTF_8),
                    depth,
                    URLEncoder.encode(mode, StandardCharsets.UTF_8));


            URL url = new URL(apiUrl + "?" + queryString);


            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");


            int responseCode = connection.getResponseCode();


            if (responseCode == HttpURLConnection.HTTP_OK) {

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                StringBuilder response = new StringBuilder();

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }

                reader.close();

                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(response.toString());

                JsonNode dataNode = jsonNode.get("data");
                if (dataNode != null) {
                    return Arrays.stream(dataNode.asText().split(" ")).toList().get(1);
                } else {
                    System.out.println("No 'data' field found in the response.");
                }
            } else {
                System.out.println("Failed to make API request. Response Code: " + responseCode);
            }

            connection.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "error!";
    }



    public static String getStockFishTextExplanation(int depth, String fen){
        return "**StockFish's Analysis** \n\n**Best move:** || " + StockFish.getBestMove(depth, fen) + "|| \n\n" +
                "**Eval:** || " + StockFish.getEvalForFEN(depth, fen) + "|| \n\n" +
                "**Top engine line: ** ||" + StockFish.getTopEngineLine(depth, fen) + "|| \n\n" +
                "**FEN:** " + fen + "\n\n **Depth: ** " + depth;
    }




















}
