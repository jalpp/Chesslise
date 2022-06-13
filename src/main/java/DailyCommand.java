import chariot.Client;
import chariot.model.ExploreResult;
import chariot.model.Puzzle;
import chariot.model.Result;
import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;
import java.util.List;

public class DailyCommand {

    private Client client;
    private EmbedBuilder embedBuilder;

    public DailyCommand(Client client) {
        this.client = client;
    }

    

    public EmbedBuilder getPuzzleData() {

        try {

            Result<Puzzle> dailypuzzle = client.puzzles().dailyPuzzle();


            if (dailypuzzle.isPresent()) { // check if the puzzle is present
                Puzzle puzzle1 = dailypuzzle.get();


                String puzzleID = puzzle1.puzzle().id();
                String puzzleImg = "https://lichess1.org/training/export/gif/thumbnail/"  + puzzleID + ".gif";

                Puzzle.PuzzleInfo puzzleInfo = puzzle1.puzzle();

                String url = "https://lichess.org/training/daily";

                int puzzleRating = puzzleInfo.rating();
                List<String> themes = puzzleInfo.themes();

                String the = "";

                for (int i = 0; i < themes.size(); i++) {
                    the += themes.get(i) + " \n ";
                }

                this.embedBuilder = new EmbedBuilder();
                this.embedBuilder.setColor(Color.white);
                this.embedBuilder.setTitle("Daily Puzzle");
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





}


