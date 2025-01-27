package lichess;

import abstraction.Player.UserObject;
import chariot.Client;
import chariot.model.Game;

/**
 * UserGame class to handle the user game
 */
public class UserGame extends UserObject {


    public UserGame(Client client, String userID) {
        super(client, userID);

    }

    /**
     * Check if the user is black in the game
     *
     * @return true if the user is black
     */
    public boolean isBlack() {

        return this.getClient().games().currentByUserId(this.getUserID()).get().players().black().name().equalsIgnoreCase(getUserID());

    }

    /**
     * Get the user games
     *
     * @return the game link id
     */
    public String getUserGames() {

        String gamelinkId = "";
        Game getGame = this.getClient().games().currentByUserId(getUserID()).get();


        if (isBlack()) {
            gamelinkId += "https://lichess1.org/game/export/gif/black/" + getGame.id() + ".gif?theme=brown&piece=kosal";

        } else {
            gamelinkId += "https://lichess1.org/game/export/gif/white/" + getGame.id() + ".gif?theme=brown&piece=kosal";

        }

        return gamelinkId;

    }


}

