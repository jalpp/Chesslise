package Fide;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


// represents the FIDE API client for Java
public class FideClient {


    private static final String API_ENDPOINT = "https://fide.thanh.se/api/players";

    public FideClient(){

    }


    public static void main(String[] args) {
       String res = getQueryByTag("name", "magnus");
       System.out.println(res);
    }


    public static String getTopNPlayerForNRatingBand(int size, String bandName){
        try {

            //'fide.thanh.se/api/players?sort_by=standard&order=desc&size=10'
            URL url = new URL(API_ENDPOINT + "?sort_by=" + bandName + "&order=desc&size=" + size);


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

                return response.toString();
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


    public static String getQueryByTag(String query, String searchTag){
        try {

            //'fide.thanh.se/api/players?sort_by=standard&order=desc&size=10'
            URL url = new URL(API_ENDPOINT + "?" + query+ "=" + searchTag);


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

                return response.toString();
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


    public static String StringfyResponse(String responseData) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(responseData);
            StringBuilder formattedData = new StringBuilder();
            JsonNode itemsNode = rootNode.get("items");
            int counter = 0;
            for (JsonNode item : itemsNode) {
                counter++;
                String name = item.get("name").asText();
                String title = item.get("title").asText();
                int standardRating = item.get("standard").asInt();
                formattedData.append(counter).append(". ").append(title).append(" ").append(name).append(", standard: (").append(standardRating).append(") \n");
            }
            return formattedData.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return "Error parsing response";
        }
    }


    public static String getTopNInString(String ratingBand, int size){
        String res = getTopNPlayerForNRatingBand(size, ratingBand);
        return StringfyResponse(res);
    }




    

}
