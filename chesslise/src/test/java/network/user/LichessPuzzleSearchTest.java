package network.user;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class LichessPuzzleSearchTest {

    @ParameterizedTest
    @ValueSource(strings = {"mate", "endgame", "opening", "middlegame", "sacrifice","long","master"})
    public void testChessgubbinsAPIAccessibilityForThemes(String theme) {
        String testUrl = "https://api.chessgubbins.com/puzzles/random?themes=" + theme;
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(testUrl))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            
            Assertions.assertEquals(200, response.statusCode(), 
                "Expected status code 200 from Chessgubbins API for theme: " + theme);
            Assertions.assertNotNull(response.body(), "Response body should not be null for theme: " + theme);
            Assertions.assertFalse(response.body().isEmpty(), "Response body should not be empty for theme: " + theme);
        } catch (IOException | InterruptedException e) {
            Assertions.fail("Exception occurred while testing the Chessgubbins API for theme " + theme + ": " + e.getMessage());
        }
    }

    @Test
    public void testInvalidThemeShouldReturnErrorOrEmptyResponse() {
        String testUrl = "https://api.chessgubbins.com/puzzles/random?themes=invalidtheme";
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(testUrl))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            // Adjust the expected behavior based on the API, for example:
            // If an unknown theme returns a 404 or an empty body, verify that.
            if(response.statusCode() != 200) {
                Assertions.assertTrue(response.statusCode() >= 400, "Expected client error status code for invalid theme");
            } else {
                // If status code is 200, then response body might be empty or have a specific error structure.
                Assertions.assertNotNull(response.body(), "Expected a response body even for an invalid theme");
            }
        } catch (IOException | InterruptedException e) {
            Assertions.fail("Exception occurred while testing with an invalid theme: " + e.getMessage());
        }
    }
}