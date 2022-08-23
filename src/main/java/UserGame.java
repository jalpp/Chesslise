import chariot.Client;
import chariot.model.Entries;
import chariot.model.Fail;
import chariot.model.Game;
import chariot.model.Many;
import net.dv8tion.jda.api.EmbedBuilder;
import java.util.Optional;


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

        Optional<Game> getGame = this.client.games().byUserId(this.getUserID).stream().findFirst();
        if(!getGame.get().players().white().name().equalsIgnoreCase(this.getUserID)){
            return true;
        }

        return false;
    }

    public String getGameId(){
        Optional<Game> getGameID = this.client.games().byUserId(this.getUserID).stream().findFirst();
        this.gameID = getGameID.get().id();

        return this.gameID;
    }


    public String getUserGames() {

        


        var client = Client.basic(conf -> conf.retries(0));
        Many<Game> games = client.games().byUserId(this.getUserID);
        if (games instanceof Fail<Game> fail) {
            if (fail.status() == 429) {
                return "Bot Overloaded please try again  later!";
            } else {
                return "Error " + fail.status() + " - " + fail.message();
            }
        } else if (games instanceof Entries<Game> entries) {

            Optional<Game> getGame = entries.stream().findFirst();

            if (getGame.isPresent()) {


                if (isBlack()) {
                    gamelinkId += "https://lichess1.org/game/export/gif/black/" + getGame.get().id() + ".gif";

                } else {
                    gamelinkId += "https://lichess1.org/game/export/gif/white/" + getGame.get().id() + ".gif";

                }


            }
            return gamelinkId;

        }

       return "error!";



    }





  






    public String getUserID(){
        return this.getUserID;
    }

    public Client getClient(){
        return this.client;
    }




}
