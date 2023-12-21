package Lichess;

import Abstraction.Player.UserObject;
import chariot.Client;
import chariot.model.Game;



public class UserGame extends UserObject {



    public UserGame(Client client, String userID){
        super(client, userID);

    }

    public boolean isBlack(){

       return this.getClient().games().currentByUserId(this.getUserID()).get().players().black().name().equalsIgnoreCase(getUserID());

    }


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
