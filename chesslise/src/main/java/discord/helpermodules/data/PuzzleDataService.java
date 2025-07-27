package discord.helpermodules.data;

import java.util.List;

import org.bson.Document;
import org.json.simple.JSONObject;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Updates;

import runner.Main;

/**
 * Service class for managing puzzle data in MongoDB
 */
public class PuzzleDataService {

    private static final MongoCollection<Document> puzzleCollection = Main.getPuzzleCollection();

    /**
     * Save or update puzzle data for a user
     */
    public static void saveGeneratedPuzzleData(String fen, List<String> uciMoves, String userId) {
        try {
            Document puzzleDoc = puzzleCollection.find(new Document("userId", userId)).first();
            if (puzzleDoc == null) {
                puzzleCollection
                        .insertOne(new Document("userId", userId).append("fen", fen).append("solution", uciMoves));
            } else {
                puzzleCollection
                        .updateOne(puzzleDoc, Updates.combine(
                                Updates.set("userid", userId),
                                Updates.set("fen", fen),
                                Updates.set("solution", uciMoves)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Update existing puzzle data for a user
     */
    public static JSONObject updateGeneratedPuzzleData(String fen, List<String> uciMoves, String userId) {
        JSONObject response = new JSONObject();
        try {
            Document puzzleDoc = puzzleCollection.find(new Document("userId", userId)).first();
            if (puzzleDoc == null) {
                response.put("success", false);
                response.put("message", "You have not generated a puzzle before");
                return response;
            }
            puzzleCollection
                    .updateOne(puzzleDoc, Updates.combine(
                            Updates.set("userid", userId),
                            Updates.set("fen", fen),
                            Updates.set("solution", uciMoves)));
            response.put("success", true);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            response.put("success", false);
            return response;
        }
    }

    /**
     * Get puzzle data for a user
     */
    public static JSONObject getUserGeneratedPuzzleData(String userId) {
        JSONObject response = new JSONObject();
        try {
            Document puzzleDoc = puzzleCollection.find(new Document("userId", userId)).first();
            if (puzzleDoc != null) {
                JSONObject jsonObject = new JSONObject(puzzleDoc);
                response.put("success", true);
                response.put("data", jsonObject);
            } else {
                response.put("success", false);
                response.put("message", "No puzzle found");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.put("success", false);
        }
        return response;
    }

    /**
     * Delete puzzle data for a user
     */
    public static void deleteUserGeneratedPuzzle(String userId) {
        try {
            Document puzzleDoc = puzzleCollection.find(new Document("userId", userId)).first();
            if (puzzleDoc != null) {
                puzzleCollection.deleteOne(puzzleDoc);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
