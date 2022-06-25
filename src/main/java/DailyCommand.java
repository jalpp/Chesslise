import chariot.Client;
import chariot.model.ExploreResult;
import chariot.model.Puzzle;
import chariot.model.Result;
import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.move.MoveList;
import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;
import java.util.List;

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

                this.gameID = puzzle1.game().id();



                String puzzleID = puzzle1.puzzle().id();
                String puzzleImg = "https://lichess1.org/training/export/gif/thumbnail/"  + puzzleID + ".gif";

                List<String> solution = puzzle1.puzzle().solution();

                String textMoves = "";

                for(int i = 0; i < solution.size(); i++){
                    textMoves += solution.get(i) + " ";
                }



                Puzzle.PuzzleInfo puzzleInfo = puzzle1.puzzle();

                String url = "https://lichess.org/training/daily";

                int puzzleRating = puzzleInfo.rating();
                List<String> themes = puzzleInfo.themes();

                String the = "";

                for (int i = 0; i < themes.size(); i++) {
                    the += themes.get(i) + " \n ";
                }

                this.embedBuilder = new EmbedBuilder();
                this.embedBuilder.setColor(Color.blue);
                this.embedBuilder.setTitle("Daily Puzzle");
                this.embedBuilder.setThumbnail("https://emojipedia-us.s3.dualstack.us-west-1.amazonaws.com/thumbs/160/apple/155/jigsaw-puzzle-piece_1f9e9.png");
                this.embedBuilder.setImage(puzzleImg);
                this.embedBuilder.setDescription("**Puzzle rating:** \n" + puzzleRating + "\n\n **Puzzle Themes:** \n " + the + "\n [Try the Puzzle](" + url + ")");

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


    public String getPuzzleGame(){
        Result<Puzzle> dailypuzzle = client.puzzles().dailyPuzzle();
        this.gameID = dailypuzzle.get().game().id();
        String puzzleGame = "";
        puzzleGame += "https://lichess1.org/game/export/gif/" + this.gameID + ".gif";

        return puzzleGame;
    }





}


