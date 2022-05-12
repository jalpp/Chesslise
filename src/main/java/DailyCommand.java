import chariot.Client;
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


    /**
     *
     * ,daily command to see the daily puzzles of lichess [I LOVE THEM]
     *
     * input: None
     *
     * output: the puzzle rating and the themes the puzzle is about, you can try the puzzle on lichess also
     *
     *
     *
     */



    public EmbedBuilder getPuzzleData() {
        Result<Puzzle> dailypuzzle = client.puzzles().dailyPuzzle();

        if (dailypuzzle.isPresent()) { // check if the puzzle is present
            Puzzle puzzle1 = dailypuzzle.get();

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
            this.embedBuilder.setDescription("**Puzzle rating:** \n" + puzzleRating + "\n\n **Puzzle Themes:** \n " + the + "\n [Try the Puzzle](" + url + ")");



        }

        return this.embedBuilder;

    }


}
