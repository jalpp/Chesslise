package chesscom;

import java.awt.Color;
import java.io.IOException;

import abstraction.Puzzle;
import abstraction.PuzzleView;
import discord.mainhandler.Thumbnail;
import io.github.sornerol.chess.pubapi.client.DailyPuzzleClient;
import io.github.sornerol.chess.pubapi.exception.ChessComPubApiException;
import net.dv8tion.jda.api.EmbedBuilder;
import setting.SettingSchema;

public class puzzle extends PuzzleView implements Puzzle {

    private final String randomCachedFen;
    private String puzzlePGN;

    public puzzle() {
        try {
            DailyPuzzleClient dailyPuzzleClient = new DailyPuzzleClient();
            this.randomCachedFen = dailyPuzzleClient.getRandomDailyPuzzle().getFen();
            this.puzzlePGN = dailyPuzzleClient.getRandomDailyPuzzle().getPgn().toString();
        } catch (IOException | ChessComPubApiException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String definePuzzleFen() {
        return randomCachedFen;
    }

    @Override
    public String definePuzzleLogo() {
        return Thumbnail.getChesscomLogo();
    }

    @Override
    public String definePuzzleTitle() {
        return "Chess.com Random Puzzle";
    }

    @Override
    public Color defineEmbedColor() {
        return Color.green;
    }

    @Override
    public String definePuzzleDescription() {
        String fen = definePuzzleFen();
        return "\n **Turn: **" + defineSideToMove(fen) + "\n**FEN: **" + fen;
    }

    @Override
    public EmbedBuilder defineCommandCard(SettingSchema schema) {
        return new EmbedBuilder().setColor(defineEmbedColor()).setTitle(definePuzzleTitle())
                .setImage(renderImage(definePuzzleFen(), schema)).setThumbnail(definePuzzleLogo())
                .setDescription(definePuzzleDescription());
    }

    public String definePGN() {
        try {
            return this.puzzlePGN;
        } catch (Exception e) {
            e.printStackTrace();
            return "INvalid PGN";
        }
    }
}
