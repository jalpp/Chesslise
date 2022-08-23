import chariot.Client;
import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;

public class ViewGame {

    private Client client;
    private EmbedBuilder embedBuilder;
    private String gameLink;

    public ViewGame(Client client, String gameLink){
        this.client = Client.basic();
        this.gameLink = gameLink;
    }

    public EmbedBuilder getViewGame(){

        String gif = "https://lichess1.org/game/export/gif";

        if(this.gameLink.contains("https://lichess.org")){
            EmbedBuilder embedBuilder = new EmbedBuilder();
           String[] splitone = this.gameLink.split("https://lichess.org");
            gif += splitone[1] + ".gif";
            embedBuilder.setColor(Color.green);
            embedBuilder.setImage(gif);
            return embedBuilder;


        }else{
            return new EmbedBuilder().setDescription("error!").setColor(Color.red);
        }


    }



}
