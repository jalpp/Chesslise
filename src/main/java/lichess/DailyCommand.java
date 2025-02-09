package lichess;

import abstraction.ChessUtil;
import abstraction.PuzzleView;
import chariot.Client;
import chariot.model.One;
import chariot.model.Puzzle;
import com.github.bhlangonijr.chesslib.Board;
import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;

/**
 * Class to handle the daily puzzle for Lichess
 */
public class DailyCommand extends PuzzleView implements abstraction.Puzzle {


    String hostimg = "https://upload.wikimedia.org/wikipedia/commons/4/47/Lichess_logo_2019.png";
    private Client client = Client.basic();


    public DailyCommand(Client client) {
        this.client = client;

    }


    @Override
    public String definePuzzleFen() {
        Board fenRender = new Board();
        for (String moves : client.puzzles().dailyPuzzle().get().game().pgn().split(" ")) {
            fenRender.doMove(moves);
        }

        return fenRender.getFen();
    }

    @Override
    public EmbedBuilder defineCommandCard() {
        String fen = definePuzzleFen();
        return new EmbedBuilder().setDescription("**Turn: **" + defineSideToMove(fen) + "\n **Rating: **" + getRating() + "\n**FEN: **" + fen).setColor(Color.cyan).setTitle("Lichess Daily Puzzle").setImage(renderImage(definePuzzleFen())).setThumbnail(hostimg);
    }


    public int getRating() {
        return client.puzzles().dailyPuzzle().get().puzzle().rating();
    }


    public EmbedBuilder getThemes() {
        EmbedBuilder themes = new EmbedBuilder();

        themes.setColor(Color.orange);

        One<Puzzle> dailypuzzle = client.puzzles().dailyPuzzle();

        StringBuilder themessay = new StringBuilder();

        for (int i = 0; i < dailypuzzle.get().puzzle().themes().size(); i++) {
            themessay.append(dailypuzzle.get().puzzle().themes().get(i)).append(" ");
        }

        themes.setDescription("**" + themessay + "**");
        themes.setTitle("Some Hints...");
        return themes;
    }


}






