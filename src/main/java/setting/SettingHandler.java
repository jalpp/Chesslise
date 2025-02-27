package setting;


import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import runner.Main;

/**
 * the class to handle setting updates and creation
 */
public class SettingHandler {

    private static final MongoCollection<Document> settingCollection = Main.getSettingCollection();

    /**
     * method to update the setting
     * @param schema the given schema the setting is updated
     * @return the status message
     */
    public static String updateSetting(SettingSchema schema){
        Document settingDoc = settingCollection.find(new Document("userid", schema.getUserid())).first();
        if(settingDoc != null){
            settingCollection.updateOne(settingDoc, Updates.combine(
                    Updates.set("boardtheme", schema.getBoardTheme()),
                    Updates.set("piecetype", schema.getPieceType())
            ));
        }else{
            settingCollection.insertOne(new Document("userid", schema.getUserid())
                    .append("boardtheme", schema.getBoardTheme())
                    .append("piecetype", schema.getPieceType()));
        }

        return "Successfully updated the Chesslise Board theme to " + schema.getBoardTheme() + " and piecetype to " + schema.getPieceType();
    }

    /**
     * gets the given user's setting
     * @param userid the user id
     * @return the settingSchema object for given user id
     */
    public static SettingSchema getUserSetting(String userid){
        Document settingDoc = settingCollection.find(new Document("userid", userid)).first();
        if(settingDoc != null){
            return new SettingSchema(settingDoc.getString("boardtheme"), settingDoc.getString("piecetype"), userid);
        }else{
            return new SettingSchema("blue", "kosal", userid);
        }
    }


}
