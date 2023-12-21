package Chesscom;
import Abstraction.*;
import Abstraction.Puzzle;
import io.github.sornerol.chess.pubapi.domain.puzzle.DailyPuzzle;
import io.github.sornerol.chess.pubapi.exception.ChessComPubApiException;
import java.io.IOException;


public class puzzle extends ChessPuzzle implements Puzzle {

    private String moveSay = "";
    private String solLink = "";

    private String FEN = "";
    private String fullSol = "";

    public puzzle(){
        super();
    }

    @Override
    public String getPuzzle() {
        try {
            DailyPuzzle puzzleClient = this.getDailyPuzzleClient().getRandomDailyPuzzle();
            FEN = puzzleClient.getFen();

            fullSol += puzzleClient.getPgn();
            moveSay = defineSideToMove(this.getUtil(), FEN);
            solLink = defineAnalysisBoard(this.getUtil(), FEN);

           return renderImage(this.getUtil(), FEN);

        } catch (IOException | ChessComPubApiException e) {
            e.printStackTrace();
            return "loading..";
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


    public String getFullSol(){
        return this.fullSol;
    }

    public String getFEN(){
        return FEN;
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
