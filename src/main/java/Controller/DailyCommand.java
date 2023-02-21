import chariot.Client;
import chariot.model.One;
import chariot.model.Puzzle;
import com.github.bhlangonijr.chesslib.Board;
import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;


public class DailyCommand {

    private Client client;
    private String gameID;
    private Board board;
    private String moveSay = "";
    private int rating;
    private String blindmode = "";


    public DailyCommand(Client client) {
        this.client = client;
        this.gameID = "";
        this.board = new Board();
    }



    public String getPuzzleData() {


            One<Puzzle> dailypuzzle = client.puzzles().dailyPuzzle();


            if (dailypuzzle.isPresent()) { // check if the puzzle is present
                Puzzle puzzle1 = dailypuzzle.get();


                String[] moves = chariot.Client.basic().puzzles().dailyPuzzle().get().game().pgn().split(" ");
                for (String move : moves) {
                    board.doMove(move);
                }




                String cor = board.getFen();

                String[] split = cor.split(" ");

                if(split[1].contains("w")){
                    moveSay += "White To Move";
                }else{
                    moveSay += "Black To Move";

                }

                String coordImg = "";
                

                if(split[1].contains("w")){
                    coordImg = "https://lichess1.org/export/fen.gif?fen=" + split[0] + "&color=white&theme=blue&piece=cardinal";
                    
                }else{
                    coordImg = "https://lichess1.org/export/fen.gif?fen=" + split[0] + "&color=black&theme=blue&piece=cardinal";
                    
                }

                

                Puzzle.PuzzleInfo puzzleInfo = puzzle1.puzzle();

                this.rating = puzzleInfo.rating();






              return coordImg;
            }else{
                return "loading..";
            }


    }


    public String getMoveSay(){
        return moveSay;
    }

    public String getSolution(){
        


            One<Puzzle> dailypuzzle = client.puzzles().dailyPuzzle();


            if (dailypuzzle.isPresent()) { // check if the puzzle is present
                Puzzle puzzle1 = dailypuzzle.get();


                String[] moves = chariot.Client.basic().puzzles().dailyPuzzle().get().game().pgn().split(" ");
                for (String move : moves) {
                    board.doMove(move);

                }

                this.board.doMove(puzzle1.puzzle().solution().get(0));


                String cor = board.getFen();

                String coordImg = "";

                String[] split = cor.split(" ");



                if(split[1].contains("b")){
                    coordImg = "https://lichess1.org/export/fen.gif?fen=" + split[0] + "&color=white&theme=blue&piece=cardinal";
                }else{
                    coordImg = "https://lichess1.org/export/fen.gif?fen=" + split[0] + "&color=black&theme=blue&piece=cardinal";
                }

                

                return coordImg;
            }else{
                return "loading...";
            }

            

    }

    public EmbedBuilder getThemes(){
        EmbedBuilder themes = new EmbedBuilder();

        themes.setColor(Color.orange);

        One<Puzzle> dailypuzzle = client.puzzles().dailyPuzzle();

        String themessay = "";

        for(int i = 0; i < dailypuzzle.get().puzzle().themes().size(); i++){
            themessay += dailypuzzle.get().puzzle().themes().get(i) + " ";
        }

        themes.setDescription( "**"+ themessay + "**");
        themes.setTitle("Some Hints...");
        return themes;
    }


    public int getRating(){
        return rating;
    }

     public String getPuzzleLink(){
        return "https://lichess.org/training/" + client.puzzles().dailyPuzzle().get().puzzle().id();
    }

    public String getBlindmode() {
        return blindmode;
    }
}




