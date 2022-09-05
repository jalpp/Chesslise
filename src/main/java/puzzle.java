import io.github.sornerol.chess.pubapi.client.DailyPuzzleClient;
import io.github.sornerol.chess.pubapi.exception.ChessComPubApiException;
import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;
import java.io.IOException;

public class puzzle {

    private DailyPuzzleClient dailyPuzzleClient;
    private EmbedBuilder embedBuilder;
    private String moveSay = "";

    private String solLink = "";



    public puzzle(){
        this.dailyPuzzleClient = new DailyPuzzleClient();
    }

    public String getRandom()  {

        try {


            String cor = this.dailyPuzzleClient.getRandomDailyPuzzle().getFen();


            String[] split = cor.split(" ");

            String coordImg = "";

            if(split[1].contains("w")){
                moveSay += "White To Move";
            }else{
                moveSay += "Black To Move";
            }

            if(split[1].contains("w")){
                coordImg = "https://chessboardimage.com/" + split[0] + ".png";
            }else{
                coordImg = "https://chessboardimage.com/" + split[0] + "-flip" + ".png";
            }


            this.solLink += "https://lichess.org/analysis/standard/" + cor.replace(" ", "_");





            return coordImg;





        } catch (IOException e) {
            e.printStackTrace();
            return "loading..";
        } catch (ChessComPubApiException e) {
            e.printStackTrace();
            return "loading..";
        }



    }


    public String getMoveSay(){
        return moveSay;
    }


    public String getSolLink(){
        return solLink;
    }









}
