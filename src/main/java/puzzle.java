import io.github.sornerol.chess.pubapi.client.DailyPuzzleClient;
import io.github.sornerol.chess.pubapi.exception.ChessComPubApiException;
import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;
import java.io.IOException;

public class puzzle {

    private DailyPuzzleClient dailyPuzzleClient;
    private EmbedBuilder embedBuilder;
    private String pgn;
    private String moveSay = "";

    private String solLink = "";



    public puzzle(){
        this.dailyPuzzleClient = new DailyPuzzleClient();
    }

    public String getRandom()  {

        try {


            String cor = this.dailyPuzzleClient.getRandomDailyPuzzle().getFen();
            this.pgn = this.dailyPuzzleClient.getRandomDailyPuzzle().getPgn();


            String[] split = cor.split(" ");

            String coordImg = "";

            if(split[1].contains("w")){
                moveSay += "White To Move";
            }else{
                moveSay += "Black To Move";
            }

            if(Main.boardOriginal == true){
                //https://lichess1.org/export/fen.gif?fen=3r4%2Fp3k1p1%2F1p3p1p%2F4n3%2F1R2B3%2F4R3%2FP1P3P1%2F3r3K+w+-+-+8+37&color=white&lastMove=d2d1&variant=standard&theme=blue&piece=kosal

            if(split[1].contains("w")){
                coordImg = "https://chessboardimage.com/" + split[0] + ".png";
            }else{
                coordImg = "https://chessboardimage.com/" + split[0] + "-flip" + ".png";
            }

            return coordImg;


            }


            coordImg = "https://lichess1.org/export/fen.gif?fen=" + split[0] + "&theme=blue&piece=alpha";


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

    public String getPgn(){
        return pgn;
    }


    public String getMoveSay(){
        return moveSay;
    }


    public String getSolLink(){
        return solLink;
    }









}
