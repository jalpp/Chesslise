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

            return dailyPuzzleClient.getTodaysDailyPuzzle().getImageUrl();
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
