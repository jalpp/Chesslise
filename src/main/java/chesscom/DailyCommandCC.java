package chesscom;

import abstraction.*;
import abstraction.Puzzle;
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
    public EmbedBuilder defineCommandCard() {
        String fen = definePuzzleFen();
        return new EmbedBuilder().setColor(Color.GREEN).setTitle("Chess.com Daily Puzzle").setThumbnail("https://static.wikia.nocookie.net/logopedia/images/4/4a/Chess.com_2019_%28App_Icon%29.png/revision/latest/scale-to-width-down/250?cb=20221006103032").setDescription("**Turn: **" + defineSideToMove(fen) + "\n **FEN: **" + fen).setImage(renderImage(definePuzzleFen()));
    }


}