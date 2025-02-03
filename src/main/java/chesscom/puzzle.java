package chesscom;


import abstraction.Puzzle;
import abstraction.PuzzleView;
import io.github.sornerol.chess.pubapi.client.DailyPuzzleClient;
import io.github.sornerol.chess.pubapi.exception.ChessComPubApiException;
import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;
import java.io.IOException;

/**
 * Chess.com Random Puzzle Command class
 */
public class puzzle extends PuzzleView implements Puzzle {


    private final String randomCachedFen;


    public puzzle() {
        try {
            DailyPuzzleClient dailyPuzzleClient = new DailyPuzzleClient();
            this.randomCachedFen = dailyPuzzleClient.getRandomDailyPuzzle().getFen();
        } catch (IOException | ChessComPubApiException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public String definePuzzleFen() {
        return randomCachedFen;
    }

    @Override
    public EmbedBuilder defineCommandCard() {
        String fen = definePuzzleFen();
        return new EmbedBuilder().setColor(Color.green).setTitle("Chess.com Random Puzzle").setImage(renderImage(definePuzzleFen())).setThumbnail("https://static.wikia.nocookie.net/logopedia/images/4/4a/Chess.com_2019_%28App_Icon%29.png/revision/latest/scale-to-width-down/250?cb=20221006103032").setDescription("\n **Turn: **" + defineSideToMove(fen) + "\n**FEN: **" + fen);
    }
}