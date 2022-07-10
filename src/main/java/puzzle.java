import io.github.sornerol.chess.pubapi.client.DailyPuzzleClient;
import io.github.sornerol.chess.pubapi.exception.ChessComPubApiException;
import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;
import java.io.IOException;

public class puzzle {

    private DailyPuzzleClient dailyPuzzleClient;
    private EmbedBuilder embedBuilder;



    public puzzle(){
        this.dailyPuzzleClient = new DailyPuzzleClient();
    }

    public EmbedBuilder getRandom()  {
        this.embedBuilder = new EmbedBuilder();
        try {
            String cor = this.dailyPuzzleClient.getRandomDailyPuzzle().getFen();


            String[] split = cor.split(" ");
            String moveSay = "";
            String coordImg = "";

            if(split[1].contains("w")){
                moveSay += "**White To Move**";
            }else{
                moveSay += "**Black To Move**";
            }

            if(split[1].contains("w")){
                coordImg = "https://chessboardimage.com/" + split[0] + ".png";
            }else{
                coordImg = "https://chessboardimage.com/" + split[0] + "-flip" + ".png";
            }



            this.embedBuilder.setDescription(moveSay);
            this.embedBuilder.setTitle(this.dailyPuzzleClient.getRandomDailyPuzzle().getTitle());
            this.embedBuilder.setColor(Color.blue);

            this.embedBuilder.setImage(coordImg);

        } catch (IOException e) {
            e.printStackTrace();
            this.embedBuilder.setDescription("Loading...Please try again");
        } catch (ChessComPubApiException e) {
            e.printStackTrace();
            this.embedBuilder.setDescription("Loading...Please try again");
        }


        return this.embedBuilder;
    }









}
