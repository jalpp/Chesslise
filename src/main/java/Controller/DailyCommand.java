import chariot.Client;
import chariot.model.One;
import chariot.model.Puzzle;
import com.github.bhlangonijr.chesslib.Board;
import net.dv8tion.jda.api.EmbedBuilder;
import java.awt.*;
import java.util.List;


public class DailyCommand extends ChessPuzzle {


    private String moveSay = "";
    private int rating;
    Client client = this.getClient();
    Board board = this.getBoard();


    public DailyCommand(Client client) {
        super(client, new Board());
    }



    @Override
    public String getSolution(){


            One<Puzzle> dailypuzzle = client.puzzles().dailyPuzzle();


            if (dailypuzzle.isPresent()) {
                Puzzle puzzle1 = dailypuzzle.get();


                String[] moves = chariot.Client.basic().puzzles().dailyPuzzle().get().game().pgn().split(" ");
                for (String move : moves) {
                    board.doMove(move);

                }
                board.doMove(puzzle1.puzzle().solution().get(0));


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

    @Override
    public String getPuzzle() {
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
                //this.blindmode = "https://lichess1.org/export/fen.gif?fen=" + split[0] + "&color=white&theme=blue&piece=disguised";
            }else{
                coordImg = "https://lichess1.org/export/fen.gif?fen=" + split[0] + "&color=black&theme=blue&piece=cardinal";
                //this.blindmode = "https://lichess1.org/export/fen.gif?fen=" + split[0] + "&color=black&theme=blue&piece=disguised";

            }



            Puzzle.PuzzleInfo puzzleInfo = puzzle1.puzzle();

            this.rating = puzzleInfo.rating();






            return coordImg;
        }else{
            return "loading..";
        }
    }

    @Override
    public String getPuzzleURL() {
        return "https://lichess.org/training/" + client.puzzles().dailyPuzzle().get().puzzle().id();
    }

    @Override
    public EmbedBuilder getThemes(){
        EmbedBuilder themes = new EmbedBuilder();

        themes.setColor(Color.orange);

        One<Puzzle> dailypuzzle = client.puzzles().dailyPuzzle();

        StringBuilder themessay = new StringBuilder();

        for(int i = 0; i < dailypuzzle.get().puzzle().themes().size(); i++){
            themessay.append(dailypuzzle.get().puzzle().themes().get(i)).append(" ");
        }

        themes.setDescription( "**"+ themessay + "**");
        themes.setTitle("Some Hints...");
        return themes;
    }

    @Override
    public String getPuzzleSideToMove() {
        return moveSay;
    }

    @Override
    public int getRating(){
        return rating;
    }

    @Override
    public boolean checkSolution(String answer) {
        try {
            Board checker = new Board();
            String[] moves = chariot.Client.basic().puzzles().dailyPuzzle().get().game().pgn().split(" ");
            for (String move : moves) {
                checker.doMove(move);
            }

            String startFenPuzzle = checker.getFen();

            String[] aMove = answer.split(" ");
            for (String move : aMove) {
                checker.doMove(move);
            }

            String ansFEN = checker.getFen();

            Board checkSolutionBoard = new Board();
            checkSolutionBoard.loadFromFen(startFenPuzzle);

            List<String> chechSol = chariot.Client.basic().puzzles().dailyPuzzle().get().puzzle().solution();

            System.out.println(chechSol);

            for (String move : chechSol) {
                checkSolutionBoard.doMove(move);
            }

            String puzzleEND = checkSolutionBoard.getFen();

            System.out.println(puzzleEND);
            System.out.println(ansFEN);

            return puzzleEND.equalsIgnoreCase(ansFEN);

        }catch (Exception e){
            return false;
        }

    }


}







