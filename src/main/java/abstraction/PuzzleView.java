package abstraction;

public class PuzzleView {

    private final ChessUtil util = new ChessUtil();

    /**
     * Render image from FEN
     *
     * @param fen the fen
     * @return the image link
     */
    public String renderImage(String fen) {
        return util.getImageFromFEN(fen, "green", "alpha");
    }

    /**
     * Define which side to move
     *
     * @param fen the fen
     * @return the side to move
     */
    public String defineSideToMove(String fen) {
        return util.getWhichSideToMove(fen);
    }

    /**
     * Define the analysis board URL
     *
     * @param fen the fen
     * @return the analysis board
     */
    public String defineAnalysisBoard(String fen) {
        return util.getAnalysisBoard(fen);
    }


}
