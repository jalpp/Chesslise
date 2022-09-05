import chariot.Client;
import chariot.model.Game;



public class UserGame {


    private Client client;
    private String getUserID;
    private String gameID;
    private String gamelinkId = "";
    private String flip = "";




    public UserGame(Client client, String userID){
        this.client = client;
        this.getUserID = userID;
        this.flip = "";
    }

    public boolean isBlack(){

       return this.client.games().currentByUserId(this.getUserID).get().players().black().name().equalsIgnoreCase(this.getUserID);

    }




    public String getUserGames() {




       Game getGame = this.client.games().currentByUserId(this.getUserID).get();


            if (isBlack()) {
                gamelinkId += "https://lichess1.org/game/export/gif/black/" + getGame.id() + ".gif";

            } else {
                gamelinkId += "https://lichess1.org/game/export/gif/white/" + getGame.id() + ".gif";

            }


        return gamelinkId;



    }





  






    public String getUserID(){
        return this.getUserID;
    }

    public Client getClient(){
        return this.client;
    }




}
