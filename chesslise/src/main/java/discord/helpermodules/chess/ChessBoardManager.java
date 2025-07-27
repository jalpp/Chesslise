package discord.helpermodules.chess;

import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.Piece;
import com.github.bhlangonijr.chesslib.Square;
import com.github.bhlangonijr.chesslib.move.Move;

/**
 * Manages chess board operations and FEN generation
 */
public class ChessBoardManager {

    /**
     * Generate a new FEN string after applying a move
     *
     * @param fen  Current position in FEN notation
     * @param move Move in UCI notation (e.g., "e2e4", "e7e8q")
     * @return New FEN string after the move
     */
    public static String generateFEN(String fen, String move) {
        Board board = new Board();
        board.loadFromFen(fen);

        Square from = Square.valueOf(move.substring(0, 2).toUpperCase());
        Square to = Square.valueOf(move.substring(2, 4).toUpperCase());

        Move moveObj;

        // Handle promotion
        if (move.length() == 5) {
            char promoChar = move.charAt(4);
            Piece promotionPiece = ChessMoveConverter.getPromotionPiece(promoChar, board.getSideToMove());
            moveObj = new Move(from, to, promotionPiece);
        } else {
            // Normal move
            moveObj = new Move(from, to);
        }

        board.doMove(moveObj);
        return board.getFen();
    }

    /**
     * Validate if a move is in proper UCI format
     *
     * @param move Move string to validate
     * @return true if the move is in valid UCI format
     */
    public static boolean isValidUciFormat(String move) {
        return move != null && move.matches("^[a-h][1-8][a-h][1-8][qrbn]?$");
    }

    /**
     * Check if a board position is in check
     *
     * @param fen FEN string representing the position
     * @return true if the side to move is in check
     */
    public static boolean isInCheck(String fen) {
        Board board = new Board();
        board.loadFromFen(fen);
        return board.isKingAttacked();
    }

    /**
     * Get the side to move from a FEN string
     *
     * @param fen FEN string
     * @return "w" for white, "b" for black
     */
    public static String getSideToMove(String fen) {
        String[] fenParts = fen.split(" ");
        return fenParts.length > 1 ? fenParts[1] : "w";
    }
}
