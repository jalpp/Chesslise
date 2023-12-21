import io.github.sornerol.chess.pubapi.client.DailyPuzzleClient;
import io.github.sornerol.chess.pubapi.exception.ChessComPubApiException;
import java.io.IOException;


public class puzzle extends ChessPuzzle{

    private final DailyPuzzleClient dailyPuzzleClient = this.getDailyPuzzleClient();

    private String moveSay = "";
    private String solLink = "";





    public puzzle(){
        super();
    }

    @Override
    public String getPuzzle() {
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
                coordImg = "https://lichess1.org/export/fen.gif?fen=" + split[0] + "&color=white&theme=brown&piece=kosal";
            }else{
                coordImg = "https://lichess1.org/export/fen.gif?fen=" + split[0] + "&color=black&theme=brown&piece=kosal";
            }


            this.solLink += "https://lichess.org/analysis/standard/" + cor.replace(" ", "_");


            return coordImg;



        } catch (IOException | ChessComPubApiException e) {
            e.printStackTrace();
            return "loading..";
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
