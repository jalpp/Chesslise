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


    public DailyCommand(Client client) {
        this.client = client;
        this.gameID = "";
    }



    public EmbedBuilder getPuzzleData() {


        try {

            Result<Puzzle> dailypuzzle = client.puzzles().dailyPuzzle();


            if (dailypuzzle.isPresent()) { // check if the puzzle is present
                Puzzle puzzle1 = dailypuzzle.get();

                Board board = new Board();

                String[] moves = chariot.Client.basic().puzzles().dailyPuzzle().get().game().pgn().split(" ");
                for (String move : moves) {
                    board.doMove(move);
                }


                String cor = board.getFen();

                String[] split = cor.split(" ");

                String coordImg = "https://chessboardimage.com/" + split[0] + ".png";




                Puzzle.PuzzleInfo puzzleInfo = puzzle1.puzzle();

                int puzzleRating = puzzleInfo.rating();



                this.embedBuilder = new EmbedBuilder();
                this.embedBuilder.setColor(Color.blue);
                this.embedBuilder.setTitle("\uD83E\uDDE9 Puzzle of The Day \uD83E\uDDE9");
                this.embedBuilder.setImage(coordImg);
                this.embedBuilder.setDescription("**" + "Puzzle Rating: " + puzzleRating+ "**");


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




}


