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
    private static final String API_ENDPOINT = "https://stockfish.online/api/s/v2.php";
    public static HashMap<String, String> getUserFen = new HashMap<>();


    public StockFish() {

    }

    public static void main(String[] args) {
        System.out.println(getStockFishTextExplanation(15, "rnbqkbnr/pp1ppppp/8/2p5/4P3/8/PPPP1PPP/RNBQKBNR w KQkq - 0 2"));
    }

    public static String getTopEngineLine(int depth, String fen) {
        try {

            String queryString = String.format("fen=%s&depth=%d",
                    URLEncoder.encode(fen, StandardCharsets.UTF_8),
                    depth);

            URL url = new URL(API_ENDPOINT + "?" + queryString);

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

                JsonNode dataNode = jsonNode.get("continuation");
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


    public static String getEvalForFEN(int depth, String fen) {
        try {

            String queryString = String.format("fen=%s&depth=%d",
                    URLEncoder.encode(fen, StandardCharsets.UTF_8),
                    depth);


            URL url = new URL(API_ENDPOINT + "?" + queryString);


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

                JsonNode dataNode = jsonNode.get("evaluation");
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


    public static String getBestMove(int depth, String fen) {
        try {

            String queryString = String.format("fen=%s&depth=%d",
                    URLEncoder.encode(fen, StandardCharsets.UTF_8),
                    depth);


            URL url = new URL(API_ENDPOINT + "?" + queryString);


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

                JsonNode dataNode = jsonNode.get("bestmove");
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


    public static String getStockFishTextExplanation(int depth, String fen) {
        return "**StockFish's Analysis** \n\n**Best move:** || " + StockFish.getBestMove(depth, fen) + "|| \n\n" +
                "**Eval:** || " + StockFish.getEvalForFEN(depth, fen) + "|| \n\n" +
                "**Top engine line: ** ||" + StockFish.getTopEngineLine(depth, fen) + "|| \n\n" +
                "**FEN:** " + fen + "\n\n **Depth: ** " + depth;
    }


}
