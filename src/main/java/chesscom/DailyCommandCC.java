package chesscom;

import abstraction.*;
import abstraction.Puzzle;
import discord.mainhandler.Thumbnail;
import io.github.sornerol.chess.pubapi.client.DailyPuzzleClient;
import io.github.sornerol.chess.pubapi.exception.ChessComPubApiException;
import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;
import java.io.IOException;

/**
 * Chess.com Daily Puzzle Command class
 */
public class DailyCommandCC extends PuzzleView implements Puzzle {

    private final DailyPuzzleClient puzzleClient = new DailyPuzzleClient();


    public DailyCommandCC() {
        super();
    }

    @Override
    public String definePuzzleFen() {
        try {
            return puzzleClient.getTodaysDailyPuzzle().getFen();
        } catch (IOException | ChessComPubApiException e) {
            return "invalid fen";
        }
    }

    @Override
    public String definePuzzleLogo(){
        return Thumbnail.getChesscomLogo();
    }

    @Override
    public String definePuzzleTitle() {
        return "Chess.com Daily Puzzle";
    }

    @Override
    public Color defineEmbedColor() {
        return Color.GREEN;
    }

    @Override
    public String definePuzzleDescription() {
        String fen = definePuzzleFen();
        return "**Turn: **" + defineSideToMove(fen) + "\n **FEN: **" + fen;
    }

    @Override
    public EmbedBuilder defineCommandCard() {
        return new EmbedBuilder().setColor(defineEmbedColor()).setTitle(definePuzzleTitle()).setThumbnail(definePuzzleLogo()).setDescription(definePuzzleDescription()).setImage(renderImage(definePuzzleFen()));
    }


}
