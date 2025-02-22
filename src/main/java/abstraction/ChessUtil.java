package abstraction;

import com.github.bhlangonijr.chesslib.Board;

/**
 * ChessUtil class to handle chess utilities
 */
public class ChessUtil {

    private final Board board;

    public ChessUtil() {

        this.board = new Board();

    }

    /**
     * Get the image from the FEN
     *
     * @param fen        the chess fen
     * @param boardColor the board color
     * @param pieceType  the piece type
     * @return the image link
     */
    public String getImageFromFEN(String fen, String boardColor, String pieceType) {
        try {
            String img;
            this.board.loadFromFen(fen);
            String[] getImgCord = this.board.getFen().split(" ");

            if (fen.contains("w")) {
                img = "https://lichess1.org/export/fen.gif?fen=" + getImgCord[0] + "&color=white&theme=" + boardColor + "&piece=" + pieceType;
            } else {
                img = "https://lichess1.org/export/fen.gif?fen=" + getImgCord[0] + "&color=black&theme=" + boardColor + "&piece=" + pieceType;
            }

            return img;

        } catch (Exception e) {
            return "Please provide a valid FEN!";
        }
    }

    /**
     * Get the image for given lastMove
     * @param fen the fen
     * @param boardColor board colour
     * @param pieceType pieceType
     * @param lastMove the last move
     * @return the image
     */
    public String getChessDBImage(String fen, String boardColor, String pieceType, String lastMove){
        String img = getImageFromFEN(fen, boardColor, pieceType);

        if(!img.equalsIgnoreCase("please provide a valid fen!")){
            return img + "&lastMove=" + lastMove;
        }

        return img;
    }

    /**
     * Get which side to move
     *
     * @param fen the chess fen
     * @return the side to move
     */
    public String getWhichSideToMove(String fen) {
        if (fen.contains("w")) {
            return "White to move";
        } else {
            return "Black to move";
        }
    }

    /**
     * Get the analysis board URL
     *
     * @param fen the chess fen
     * @return the analysis board
     */
    public String getAnalysisBoard(String fen) {
        return "https://lichess.org/analysis/standard/" + fen.replace(" ", "_");
    }


}