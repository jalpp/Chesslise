import io.github.sornerol.chess.pubapi.client.DailyPuzzleClient;
import io.github.sornerol.chess.pubapi.client.StreamersClient;
import io.github.sornerol.chess.pubapi.domain.streamers.Streamer;
import io.github.sornerol.chess.pubapi.exception.ChessComPubApiException;

import java.awt.*;
import java.util.*;
import java.io.IOException;
import java.util.List;

public class DailyCommandCC {

    private DailyPuzzleClient dailyPuzzleClient = new DailyPuzzleClient();
    private String ans = "";
    private String fen = "";


    public String getpuzzleImg(){


        try {


            String fen = dailyPuzzleClient.getTodaysDailyPuzzle().getFen();
            String[] split = fen.split(" ");

            if(Main.boardOriginal == true){

                String coordImg = "";
                //https://lichess1.org/export/fen.gif?fen=3r4%2Fp3k1p1%2F1p3p1p%2F4n3%2F1R2B3%2F4R3%2FP1P3P1%2F3r3K+w+-+-+8+37&color=white&lastMove=d2d1&variant=standard&theme=blue&piece=kosal

                if(split[1].contains("w")){
                    coordImg = "https://chessboardimage.com/" + split[0] + ".png";
                }else{
                    coordImg = "https://chessboardimage.com/" + split[0] + "-flip" + ".png";
                }

                return coordImg;


            }
            String img =  "https://lichess1.org/export/fen.gif?fen=" + split[0] + "&theme=blue&piece=alpha";

            return img;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ChessComPubApiException e) {
            throw new RuntimeException(e);
        }
    }

    public String getPGN(){

        try {
            return dailyPuzzleClient.getTodaysDailyPuzzle().getPgn();
        } catch (IOException e) {
            return "error!";
        } catch (ChessComPubApiException e) {
            return "error!";
        }
    }


}
