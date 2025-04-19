package abstraction;

import com.github.bhlangonijr.chesslib.Board;

public class ChessUtil {

    private final Board board;

    public ChessUtil() {

        this.board = new Board();

    }

    public String getImageFromFEN(String fen, String boardColor, String pieceType) {
        try {
            String img;
            this.board.loadFromFen(fen);
            String[] getImgCord = this.board.getFen().split(" ");

            if (fen.contains("w")) {
                img = "https://lichess1.org/export/fen.gif?fen=" + getImgCord[0] + "&color=white&theme=" + boardColor
                        + "&piece=" + pieceType;
            } else {
                img = "https://lichess1.org/export/fen.gif?fen=" + getImgCord[0] + "&color=black&theme=" + boardColor
                        + "&piece=" + pieceType;
            }

            return img;

        } catch (Exception e) {
            return "Please provide a valid FEN!";
        }
    }

    public String getChessDBImage(String fen, String boardColor, String pieceType, String lastMove) {
        String img = getImageFromFEN(fen, boardColor, pieceType);

        if (!img.equalsIgnoreCase("please provide a valid fen!")) {
            return img + "&lastMove=" + lastMove;
        }

        return img;
    }

    public String getWhichSideToMove(String fen) {
        if (fen.contains("w")) {
            return "White to move";
        } else {
            return "Black to move";
        }
    }

    public String getAnalysisBoard(String fen) {
        return "https://lichess.org/analysis/standard/" + fen.replace(" ", "_");
    }

}