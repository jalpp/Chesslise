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

        return this.getClient().games().currentByUserId(this.getUserID()).get().players().black().name()
                .equalsIgnoreCase(getUserID());

    }

    public String getUserGamesGif(SettingSchema schema) {

        Game getGame = this.getClient().games().currentByUserId(getUserID()).get();

        if (isBlack()) {
            return getGameGif("black", getGame.id(), schema);
        } else {
            return getGameGif("white", getGame.id(), schema);
        }

    }

    public static String getGameGif(String side, String gameId, SettingSchema schema) {
        return "https://lichess1.org/game/export/gif/" + side + "/" + gameId + ".gif?theme=" + schema.getBoardTheme()
                + "&piece=" + schema.getPieceType();
    }

}
