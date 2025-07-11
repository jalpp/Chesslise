package abstraction;

import setting.SettingSchema;

public class PuzzleView {

    private final ChessUtil util = new ChessUtil();

    public String renderImage(String fen, SettingSchema schema) {
        return util.getImageFromFEN(fen, schema.getBoardTheme(), schema.getPieceType());
    }

    public String renderImage(String fen, String boardTheme , String pieceType){
        return util.getImageFromFEN(fen, boardTheme,pieceType);
    }

    public String defineSideToMove(String fen) {
        return util.getWhichSideToMove(fen);
    }

    public String defineAnalysisBoard(String fen) {
        return util.getAnalysisBoard(fen);
    }

}
