import chariot.Client;
import chariot.model.Game;



public class UserGame {


    private Client client;
    private String getUserID;
    private String gameID;
    private String gamelinkId = "";

    private String flip = "";
    private String openingName ="";




    public UserGame(Client client, String userID){
        this.client = client;
        this.getUserID = userID.toLowerCase();
        this.flip = "";
    }

    public boolean isBlack(){


       return this.client.games().currentByUserId(this.getUserID).get().players().black().name().equalsIgnoreCase(this.getUserID);

    }




    public String getUserGames() {




       Game getGame = this.client.games().currentByUserId(this.getUserID).get();


          if(Main.boardOriginal == true){

              if (isBlack()) {
                  gamelinkId += "https://lichess1.org/game/export/gif/black/" + getGame.id() + ".gif?theme=brown&piece=kosal";
                  

              } else {
                  gamelinkId += "https://lichess1.org/game/export/gif/white/" + getGame.id() + ".gif?theme=brown&piece=kosal";
                 

              }

              return gamelinkId;
          }

        if (isBlack()) {
            gamelinkId += "https://lichess1.org/game/export/gif/black/" + getGame.id() + ".gif?theme=green&piece=alpha";

        } else {
            gamelinkId += "https://lichess1.org/game/export/gif/white/" + getGame.id() + ".gif?theme=green&piece=alpha";

        }



        return gamelinkId;



    }


    public String getOpeningName(){

        if(this.client.games().currentByUserId(this.getUserID).get().opening() != null){
            return this.client.games().currentByUserId(this.getUserID).get().opening().name();
        }else{
            return "Non-Standard-Opening";
        }

   
    }


    public String getWinner(){
        if(this.client.games().currentByUserId(this.getUserID).get().winner() != null) {
            if (this.client.games().currentByUserId(this.getUserID).get().winner().name().equalsIgnoreCase("white")) {
                return this.client.games().currentByUserId(this.getUserID).get().players().white().name();
            } else {
                return this.client.games().currentByUserId(this.getUserID).get().players().black().name();
            }
        }else{
            return "Draw/Live Game";
        }
    }

    public String getSpeed(){
        return this.client.games().currentByUserId(this.getUserID).get().speed();
    }

    


    public String getUserID(){
        return this.getUserID;
    }

    public Client getClient(){
        return this.client;
    }




}
