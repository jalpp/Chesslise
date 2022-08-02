import chariot.Client;
import chariot.model.Puzzle;
import chariot.model.Result;
import com.github.bhlangonijr.chesslib.Board;
import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;


public class DailyCommand {

    private Client client;
    private EmbedBuilder embedBuilder;
    private String gameID;
    private Board board;


    public DailyCommand(Client client) {
        this.client = client;
        this.gameID = "";
        this.board = new Board();
    }



    public EmbedBuilder getPuzzleData() {


        try {

            String moveSay = "";

            Result<Puzzle> dailypuzzle = client.puzzles().dailyPuzzle();


            if (dailypuzzle.isPresent()) { // check if the puzzle is present
                Puzzle puzzle1 = dailypuzzle.get();


                String[] moves = chariot.Client.basic().puzzles().dailyPuzzle().get().game().pgn().split(" ");
                for (String move : moves) {
                    board.doMove(move);
                }




                String cor = board.getFen();

                String[] split = cor.split(" ");

                if(split[1].contains("w")){
                    moveSay += "**White To Move**";
                }else{
                    moveSay += "**Black To Move**";
                }

                String coordImg = "";

                if(split[1].contains("w")){
                    coordImg = "https://chessboardimage.com/" + split[0] + ".png";
                }else{
                    coordImg = "https://chessboardimage.com/" + split[0] + "-flip" + ".png";
                }





                Puzzle.PuzzleInfo puzzleInfo = puzzle1.puzzle();

                int puzzleRating = puzzleInfo.rating();



                this.embedBuilder = new EmbedBuilder();
                this.embedBuilder.setColor(Color.blue);
                this.embedBuilder.setTitle("\uD83E\uDDE9 Puzzle of The Day \uD83E\uDDE9");
                this.embedBuilder.setImage(coordImg);
                this.embedBuilder.setDescription("**" + "Puzzle Rating: " + puzzleRating+ "** \n\n" + moveSay);


              return this.embedBuilder;
            }



        } catch (ExceptionInInitializerError ex) {

            Throwable throwable = ex.getException();
            if (throwable != null) {
                throwable.printStackTrace();
            } else {
                System.out.println("Throwable not present");
            }


        }

        return this.embedBuilder;

    }

    public EmbedBuilder getSolution(){


        try {



            Result<Puzzle> dailypuzzle = client.puzzles().dailyPuzzle();


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
                    coordImg += "https://chessboardimage.com/" + split[0] + ".png";
                }else{
                    coordImg += "https://chessboardimage.com/" + split[0] + "-flip" + ".png";
                }


                this.embedBuilder = new EmbedBuilder();
                this.embedBuilder.setColor(Color.blue);
                this.embedBuilder.setTitle("\uD83E\uDDE9 Puzzle of The Day Solution \uD83E\uDDE9");
                this.embedBuilder.setImage(coordImg);




                return this.embedBuilder;
            }



        } catch (ExceptionInInitializerError ex) {

            Throwable throwable = ex.getException();
            if (throwable != null) {
                throwable.printStackTrace();
            } else {
                System.out.println("Throwable not present");
            }


        }

        return this.embedBuilder;




    }

    public String getanswer() {
        Result<Puzzle> dailypuzzle = client.puzzles().dailyPuzzle();



            Puzzle puzzle1 = dailypuzzle.get();


            String[] moves = chariot.Client.basic().puzzles().dailyPuzzle().get().game().pgn().split(" ");
            for (String move : moves) {
                board.doMove(move);

            }

            this.board.doMove(puzzle1.puzzle().solution().get(0));


            String cor = board.getFen();

            String[] split = cor.split(" ");

            String coordImg = "https://chessboardimage.com/" + split[0] + ".png";

            return coordImg;

    }

    public String getFen(){
        return this.board.getFen();
    }




}


