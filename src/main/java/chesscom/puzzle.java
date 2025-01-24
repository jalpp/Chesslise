package chesscom;

import abstraction.ChessUtil;
import abstraction.Puzzle;
import io.github.sornerol.chess.pubapi.client.DailyPuzzleClient;
import io.github.sornerol.chess.pubapi.exception.ChessComPubApiException;
import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;
import java.io.IOException;


public class puzzle implements Puzzle {


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
    public String renderImage(ChessUtil util, String fen) {
        return util.getImageFromFEN(fen, fen.contains("b"), "green", "alpha");
    }

    @Override
    public String defineSideToMove(ChessUtil util, String fen) {
        return util.getWhichSideToMove(fen);
    }

    @Override
    public String defineAnalysisBoard(ChessUtil util, String fen) {
        return util.getAnalysisBoard(fen);
    }

    @Override
    public ChessUtil defineUtil() {
        return new ChessUtil();
    }

    @Override
    public String definePuzzleFen() {
        return randomCachedFen;
    }

    @Override
    public EmbedBuilder defineCommandCard() {
        String fen = definePuzzleFen();
        return new EmbedBuilder().setColor(Color.green).setTitle("Chess.com Random Puzzle").setImage(renderImage(defineUtil(), definePuzzleFen())).setThumbnail("https://static.wikia.nocookie.net/logopedia/images/4/4a/Chess.com_2019_%28App_Icon%29.png/revision/latest/scale-to-width-down/250?cb=20221006103032").setDescription("\n **Turn: **" + defineSideToMove(defineUtil(), fen) + "\n**FEN: **" + fen);
    }
}