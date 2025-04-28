
package lichess;

import abstraction.Puzzle;
import abstraction.PuzzleView;
import discord.mainhandler.Thumbnail;
import net.dv8tion.jda.api.EmbedBuilder;
import setting.SettingSchema;

import java.awt.*;

public class FenPuzzle extends PuzzleView implements Puzzle {

    private final String fen;

    public FenPuzzle(String fen) {
        this.fen = fen;
    }

    @Override
    public String definePuzzleFen() {
        return this.fen;
    }

    public boolean isValidFen(){
        try {
            Board board = new Board();
            board.loadFromFen(this.fen);
            return true;
        } catch (Exception e) {
            return false;
        }
      }

    @Override
    public EmbedBuilder defineCommandCard(SettingSchema schema) {
        return new EmbedBuilder().setDescription(definePuzzleDescription()).setColor(defineEmbedColor())
                .setTitle(definePuzzleTitle()).setImage(renderImage(definePuzzleFen(), schema))
                .setThumbnail(definePuzzleLogo());
    }

    @Override
    public String definePuzzleLogo() {
        return Thumbnail.getChessliseLogo();
    }

    @Override
    public String definePuzzleTitle() {
        return "View Position";
    }

    @Override
    public Color defineEmbedColor() {
        return Color.orange;
    }

    @Override
    public String definePuzzleDescription() {
        return this.defineSideToMove(this.fen);
    }
}
