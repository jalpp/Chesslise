import io.github.sornerol.chess.pubapi.client.DailyPuzzleClient;
import io.github.sornerol.chess.pubapi.exception.ChessComPubApiException;
import java.io.IOException;


public class DailyCommandCC extends ChessPuzzle {


    private final DailyPuzzleClient dailyPuzzleClient = this.getDailyPuzzleClient();
    private String moveSay = new String("");
    private String solLink = new String("");


    public DailyCommandCC(){
        super();
    }

    @Override
    public String getPuzzle() {
        try {
            String fen = dailyPuzzleClient.getTodaysDailyPuzzle().getFen();
            String[] split = fen.split(" ");
            if(split[1].contains("w")){
                moveSay += "White To Move";

            }else{
                moveSay += "Black To Move";

            }
            this.solLink += "https://lichess.org/analysis/standard/" + fen.replace(" ", "_");

            return "https://lichess1.org/export/fen.gif?fen=" + split[0] + "&theme=brown&piece=kosal";

        } catch (IOException | ChessComPubApiException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getSolution() {
        return null;
    }

    @Override
    public String getPuzzleURL() {
        return solLink;
    }

    @Override
    public String getPuzzleSideToMove() {
        return moveSay;
    }





}
