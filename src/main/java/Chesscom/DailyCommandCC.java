package Chesscom;

import Abstraction.*;
import Abstraction.Puzzle;
import io.github.sornerol.chess.pubapi.exception.ChessComPubApiException;
import net.dv8tion.jda.api.EmbedBuilder;

import java.io.IOException;


public class DailyCommandCC extends ChessPuzzle implements Puzzle {



    private String moveSay = "";
    private String solLink = "";

    public DailyCommandCC(){
        super();
    }

    @Override
    public String getPuzzle() {
        try {
            String fen = this.getDailyPuzzleClient().getTodaysDailyPuzzle().getFen();
            this.moveSay = defineSideToMove(this.getUtil(), fen);
            this.solLink = defineAnalysisBoard(this.getUtil(), fen);
            return renderImage(this.getUtil(), fen);

        } catch (IOException | ChessComPubApiException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getSolution() {
        return null;
    }

    @Override
    public String getPuzzleURL() {
        return solLink;
    }

    @Override
    public String getPuzzleSideToMove() {
        return moveSay;
    }

    public String getFEN(){
        try {
            return  this.getDailyPuzzleClient().getTodaysDailyPuzzle().getFen();
        } catch (IOException | ChessComPubApiException e) {
            return "error!";
        }
    }

    @Override
    public String renderImage(ChessUtil util, String fen) {
        return util.getImageFromFEN(fen, fen.contains("b"), "brown", "kosal");
    }

    @Override
    public String defineSideToMove(ChessUtil util, String fen) {
        return util.getWhichSideToMove(fen);
    }

    @Override
    public String defineAnalysisBoard(ChessUtil util, String fen) {
        return util.getAnalysisBoard(fen);
    }
}
