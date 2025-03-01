package lichess;

import abstraction.Player.UserObject;
import chariot.Client;
import chariot.model.Game;
import setting.SettingSchema;

public class UserGame extends UserObject {


    public UserGame(Client client, String userID) {
        super(client, userID);

    }

    
    private boolean isBlack() {

        return this.getClient().games().currentByUserId(this.getUserID()).get().players().black().name().equalsIgnoreCase(getUserID());

    }


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

