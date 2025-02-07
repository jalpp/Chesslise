package chessdb;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * ChessDBQuery class to query the ChessDB API
 */
public class ChessDBQuery {
    /**
     * Get the top 3 best moves for the given FEN position
     *
     * @param fen the FEN position
     * @return the top 3 best moves
     */
    public String getTop3BestMove(String fen) {
        StringBuilder builder = new StringBuilder();


        String encodedFen = URLEncoder.encode(fen, StandardCharsets.UTF_8);
        String apiUrl = "https://www.chessdb.cn/cdb.php?action=queryall&board=" + encodedFen + "&json=1";

        try {

            HttpClient client = HttpClient.newHttpClient();


            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(apiUrl))
                    .GET()
                    .build();


            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {

                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode rootNode = objectMapper.readTree(response.body());


                String status = rootNode.path("status").asText();
                if (!"ok".equals(status)) {
                    return "Seems like this position eval isnt in Chess DB right now!" + status;
                }


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


    /**
     * Get the top 3 cloud chessdb moves in a form of list
     * @param parseView the incoming string view
     * @return the top 3 moves
     */
    public static List<String> getTop3Moves(String parseView){
        Pattern pattern = Pattern.compile("\\d\\)\\s([a-h][1-8][a-h][1-8])");
        Matcher matcher = pattern.matcher(parseView);

        List<String> moves = new ArrayList<>();

        while (matcher.find()) {
            moves.add(matcher.group(1)); 
        }

        return moves;
    }

}



