package lichess;


import abstraction.PuzzleView;
import chariot.Client;
import chariot.model.One;
import chariot.model.Puzzle;
import com.github.bhlangonijr.chesslib.Board;
import discord.mainhandler.Thumbnail;
import net.dv8tion.jda.api.EmbedBuilder;
import setting.SettingSchema;

import java.awt.*;

/**
 * Class to handle the daily puzzle for Lichess
 */
public class DailyCommand extends PuzzleView implements abstraction.Puzzle {


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
    public String definePuzzleLogo() {
        return Thumbnail.getLichessLogo();
    }

    @Override
    public String definePuzzleTitle() {
        return "Lichess Daily Puzzle";
    }

    @Override
    public Color defineEmbedColor() {
        return Color.GRAY;
    }

    @Override
    public String definePuzzleDescription() {
        String fen = definePuzzleFen();
        return "**Turn: **" + defineSideToMove(fen) + "\n **Rating: **" + getRating() + "\n**FEN: **" + fen;
    }

    @Override
    public EmbedBuilder defineCommandCard(SettingSchema schema) {
        return new EmbedBuilder().setDescription(definePuzzleDescription()).setColor(defineEmbedColor()).setTitle(definePuzzleTitle()).setImage(renderImage(definePuzzleFen(), schema)).setThumbnail(definePuzzleLogo());
    }


    /**
     * Gets the Lichess Daily puzzle rating
     *
     * @return gets the rating
     */
    public int getRating() {
        return client.puzzles().dailyPuzzle().get().puzzle().rating();
    }


    /**
     * Gets the themes for current puzzle
     *
     * @return gets the current puzzle themes
     */
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







