import chariot.Client;
import chariot.model.Game;



public class UserGame {


    private final Client client;
    private final String getUserID;

    private String gamelinkId = new String("");
    
    

    public UserGame(Client client, String userID){
        this.client = client;
        this.getUserID = userID.toLowerCase();
        
    }

    public boolean isBlack(){

       return this.client.games().currentByUserId(this.getUserID).get().players().black().name().equalsIgnoreCase(this.getUserID);

    }




    public String getUserGames() {




       Game getGame = this.client.games().currentByUserId(this.getUserID).get();


        if (isBlack()) {
            gamelinkId += "https://lichess1.org/game/export/gif/black/" + getGame.id() + ".gif?theme=green&piece=alpha";

        } else {
            gamelinkId += "https://lichess1.org/game/export/gif/white/" + getGame.id() + ".gif?theme=green&piece=alpha";

        }



        return gamelinkId;



    }


    

    public Client getClient(){
        return this.client;
    }




}
