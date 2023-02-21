import io.github.sornerol.chess.pubapi.client.DailyPuzzleClient;
import io.github.sornerol.chess.pubapi.exception.ChessComPubApiException;
import java.io.IOException;


public class puzzle {

    private DailyPuzzleClient dailyPuzzleClient;

    private String pgn;
    private String moveSay = "";
    private String theme = "";
    private String solLink = "";
    

    


    public puzzle(){
        this.dailyPuzzleClient = new DailyPuzzleClient();
    }

    public String getRandom()  {



        try {


            String cor = this.dailyPuzzleClient.getRandomDailyPuzzle().getFen();
            this.pgn = this.dailyPuzzleClient.getRandomDailyPuzzle().getPgn();
            this.theme = this.dailyPuzzleClient.getRandomDailyPuzzle().getTitle();


            String[] split = cor.split(" ");

            String coordImg = "";

            if(split[1].contains("w")){
                moveSay += "White To Move";

            }else{
                moveSay += "Black To Move";

            }


            if(split[1].contains("w")){
                coordImg = "https://lichess1.org/export/fen.gif?fen=" + split[0] + "&color=white&theme=blue&piece=cardinal";
            }else{
                coordImg = "https://lichess1.org/export/fen.gif?fen=" + split[0] + "&color=black&theme=blue&piece=cardinal";
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

    public String getPgn(){
        return pgn;
    }


    public String getMoveSay(){
        return moveSay;
    }

    public String getTheme() {
        return theme;
    }

    public String getSolLink(){
        return solLink;
    }


}

