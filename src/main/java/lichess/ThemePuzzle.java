package lichess;

import abstraction.Puzzle;
import abstraction.PuzzleView;
import discord.mainhandler.Thumbnail;
import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;


public class ThemePuzzle extends PuzzleView implements Puzzle {

    private final String theme;
    private final LichessDBPuzzle currentPuzzle;

    public ThemePuzzle(String theme) {
        this.theme = theme;
        this.currentPuzzle = LichessPuzzleSearch.getDatabasePuzzle(theme);
    }

    @Override
    public String definePuzzleFen() {
        return currentPuzzle.getFen();
    }

    @Override
    public EmbedBuilder defineCommandCard() {
        return new EmbedBuilder().setDescription(definePuzzleDescription()).setColor(defineEmbedColor()).setTitle(definePuzzleTitle()).setImage(renderImage(definePuzzleFen())).setThumbnail(definePuzzleLogo());
    }

    @Override
    public String definePuzzleLogo() {
        return Thumbnail.getLichessLogo();
    }

    @Override
    public String definePuzzleTitle() {
        return "Lichess " + this.theme + " Puzzle";
    }

    @Override
    public Color defineEmbedColor() {
        return Color.CYAN;
    }

    @Override
    public String definePuzzleDescription() {
        return "**Turn: **" + defineSideToMove(definePuzzleFen()) + "\n **Rating: **" + currentPuzzle.getRating() + "\n**FEN: **" + definePuzzleFen();
    }

    public String getGameURL(){
        return currentPuzzle.getGameURL();
    }



}
