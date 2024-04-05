package Chesscom;

import Abstraction.*;
import Abstraction.Puzzle;
import Engine.StockFish;
import io.github.sornerol.chess.pubapi.client.DailyPuzzleClient;
import io.github.sornerol.chess.pubapi.exception.ChessComPubApiException;
import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;
import java.io.IOException;


public class DailyCommandCC implements Puzzle {


    private final DailyPuzzleClient puzzleClient = new DailyPuzzleClient();


    public DailyCommandCC() {
        super();
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
        try {
            return puzzleClient.getTodaysDailyPuzzle().getFen();
        } catch (IOException | ChessComPubApiException e) {
            return "invalid fen";
        }
    }

    @Override
    public EmbedBuilder defineCommandCard() {
        return new EmbedBuilder().setColor(Color.GREEN).setTitle("Chess.com Daily Puzzle").setThumbnail("https://static.wikia.nocookie.net/logopedia/images/4/4a/Chess.com_2019_%28App_Icon%29.png/revision/latest/scale-to-width-down/250?cb=20221006103032").setDescription(StockFish.getStockFishTextExplanation(15, definePuzzleFen()) + "\n\n " + defineSideToMove(defineUtil(), definePuzzleFen()) + "\n\n [Join our Server ♟\uFE0F](https://discord.gg/uncmhknmYg)").setFooter("use /analyze [fen] to further analyze/check your answer").setImage(renderImage(defineUtil(), definePuzzleFen()));
    }


}



