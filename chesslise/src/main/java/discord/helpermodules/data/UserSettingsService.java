package discord.helpermodules.data;

import org.bson.Document;
import org.json.simple.JSONObject;

import com.mongodb.client.MongoCollection;

import runner.Main;

/**
 * Service class for managing user settings in MongoDB
 */
public class UserSettingsService {

    private static final MongoCollection<Document> settingCollection = Main.getSettingCollection();

    /**
     * Get user settings from database
     */
    public static JSONObject getUserSetting(String userId) {
        JSONObject response = new JSONObject();
        try {
            Document settingDoc = settingCollection.find(new Document("userId", userId)).first();
            if (settingDoc != null) {
                JSONObject jsonObject = new JSONObject(settingDoc);
                response.put("success", true);
                response.put("data", jsonObject);
            } else {
                response.put("success", false);
                response.put("message", "No settings found");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.put("success", false);
        }
        return response;
    }

    /**
     * Get user's board theme setting with default fallback
     */
    public static String getBoardTheme(String userId) {
        JSONObject userSettingData = getUserSetting(userId);
        if ((boolean) userSettingData.get("success")) {
            JSONObject boardSettingData = (JSONObject) userSettingData.get("data");
            return (String) boardSettingData.get("boardtheme");
        }
        return "blue"; // default theme
    }

    /**
     * Get user's piece type setting with default fallback
     */
    public static String getPieceType(String userId) {
        JSONObject userSettingData = getUserSetting(userId);
        if ((boolean) userSettingData.get("success")) {
            JSONObject boardSettingData = (JSONObject) userSettingData.get("data");
            return (String) boardSettingData.get("piecetype");
        }
        return "kosal"; // default piece type
    }
}
