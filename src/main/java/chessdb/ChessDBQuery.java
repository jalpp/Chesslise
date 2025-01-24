package chessdb;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class ChessDBQuery {

    public String getTop3BestMove(String fen) {
        StringBuilder builder = new StringBuilder();

        // Properly encode the FEN string
        String encodedFen = URLEncoder.encode(fen, StandardCharsets.UTF_8);
        String apiUrl = "https://www.chessdb.cn/cdb.php?action=queryall&board=" + encodedFen + "&json=1";

        try {
            // Create HttpClient
            HttpClient client = HttpClient.newHttpClient();

            // Create a GET request
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(apiUrl))
                    .GET()
                    .build();

            // Send the request and get a response
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                // Parse the JSON response
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode rootNode = objectMapper.readTree(response.body());

                // Check the status field
                String status = rootNode.path("status").asText();
                if (!"ok".equals(status)) {
                    return "API returned a non-OK status: " + status;
                }

                // Get moves
                JsonNode moves = rootNode.path("moves");
                if (moves.isArray()) {
                    int count = 0;
                    for (JsonNode move : moves) {
                        if (count >= 3) break;

                        String uci = move.path("uci").asText();
                        String san = move.path("san").asText();
                        String score = move.path("score").asText();
                        String winrate = move.path("winrate").asText();

                        builder.append(count + 1).append(") ").append(uci).append(" (")
                                .append(san).append(") score: ").append(score)
                                .append(", Win rate: ").append(winrate).append("%\n");

                        count++;
                    }
                    return builder.toString();
                } else {
                    return "No moves found in the response.";
                }
            } else {
                return "Failed to fetch data. HTTP status code: " + response.statusCode();
            }
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

}



