package lichess;

import abstraction.Player.UserObject;
import chariot.Client;
import chariot.model.Game;
import setting.SettingSchema;

/**
 * UserGame class to handle the user game
 */
public class UserGame extends UserObject {


    public UserGame(Client client, String userID) {
        super(client, userID);

    }

    /**
     * helper function to check if the user is black in the game
     *
     * @return true if the user is black
     */
    private boolean isBlack() {

        return this.getClient().games().currentByUserId(this.getUserID()).get().players().black().name().equalsIgnoreCase(getUserID());

    }

    /**
     *  Gets the 1 previous Lichess game played for given userid, and shows the game in the given user setting schema
     * @param schema the setting schema
     * @return the game gif
     */
    public String getUserGamesGif(SettingSchema schema) {

        String theme = schema.getBoardTheme();
        String pieceType = schema.getPieceType();

        String gamelinkId = "";
        Game getGame = this.getClient().games().currentByUserId(getUserID()).get();

        if (isBlack()) {
            gamelinkId += "https://lichess1.org/game/export/gif/black/" + getGame.id() + ".gif?theme=" + theme + "&piece=" + pieceType;

        } else {
            gamelinkId += "https://lichess1.org/game/export/gif/white/" + getGame.id() + ".gif?theme=" + theme + "&piece=" + pieceType;

        }

        return gamelinkId;

    }


}

