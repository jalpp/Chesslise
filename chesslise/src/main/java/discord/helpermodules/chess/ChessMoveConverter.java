package discord.helpermodules.chess;

import java.util.ArrayList;
import java.util.List;

import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.Piece;
import com.github.bhlangonijr.chesslib.PieceType;
import com.github.bhlangonijr.chesslib.Side;
import com.github.bhlangonijr.chesslib.Square;
import com.github.bhlangonijr.chesslib.move.Move;
import com.github.bhlangonijr.chesslib.move.MoveGenerator;

/**
 * Utility class for converting between UCI and SAN chess notation
 */
public class ChessMoveConverter {

    /**
     * Convert UCI moves to SAN notation
     */
    public static List<String> convertUciToSan(String fen, List<String> uciMoves) {
        List<String> sanMoves = new ArrayList<>();
        Board board = new Board();
        board.loadFromFen(fen);

        for (String uci : uciMoves) {
            Move move = findMoveFromUci(board, uci);

            if (move != null) {
                String san = convertMoveToSan(move, board);
                sanMoves.add(san);
                board.doMove(move);
            } else {
                throw new IllegalArgumentException("Illegal move: " + uci + " at " + board.getFen());
            }
        }
        return sanMoves;
    }

    /**
     * Convert SAN moves to UCI notation
     */
    public static List<String> convertSanToUci(String fen, List<String> sanMoves) {
        List<String> uciMoves = new ArrayList<>();
        Board board = new Board();
        board.loadFromFen(fen);

        for (String san : sanMoves) {
            Move move = findMoveFromSan(board, san);

            if (move != null) {
                String uci = moveToUci(move);
                uciMoves.add(uci);
                board.doMove(move);
            } else {
                throw new IllegalArgumentException("Illegal SAN move: " + san + " at " + board.getFen());
            }
        }

        return uciMoves;
    }

    private static Move findMoveFromUci(Board board, String uci) {
        Square from = Square.valueOf(uci.substring(0, 2).toUpperCase());
        Square to = Square.valueOf(uci.substring(2, 4).toUpperCase());

        List<Move> legalMoves = MoveGenerator.generateLegalMoves(board);

        for (Move move : legalMoves) {
            if (move.getFrom().equals(from) && move.getTo().equals(to)) {
                if (uci.length() == 5) {
                    char promotionChar = Character.toLowerCase(uci.charAt(4));
                    Piece expectedPromotion = getPromotionPiece(promotionChar, board.getSideToMove());
                    if (move.getPromotion().equals(expectedPromotion)) {
                        return move;
                    }
                } else if (move.getPromotion() == Piece.NONE) {
                    return move;
                }
            }
        }
        return null;
    }

    private static Move findMoveFromSan(Board board, String san) {
        List<Move> legalMoves = MoveGenerator.generateLegalMoves(board);
        String cleanSan = san.replaceAll("[+#!?]", "");

        // Handle castling
        if (cleanSan.equals("O-O") || cleanSan.equals("0-0")) {
            return findCastlingMove(legalMoves, true);
        }
        if (cleanSan.equals("O-O-O") || cleanSan.equals("0-0-0")) {
            return findCastlingMove(legalMoves, false);
        }

        for (Move move : legalMoves) {
            if (doesMoveMatchSan(move, cleanSan, board)) {
                return move;
            }
        }

        return null;
    }

    private static Move findCastlingMove(List<Move> legalMoves, boolean kingSide) {
        for (Move move : legalMoves) {
            Square from = move.getFrom();
            Square to = move.getTo();

            if ((from == Square.E1 || from == Square.E8)) {
                if (kingSide && (to == Square.G1 || to == Square.G8)) {
                    return move;
                } else if (!kingSide && (to == Square.C1 || to == Square.C8)) {
                    return move;
                }
            }
        }
        return null;
    }

    private static boolean doesMoveMatchSan(Move move, String san, Board board) {
        Piece piece = board.getPiece(move.getFrom());
        PieceType pieceType = piece.getPieceType();

        if (pieceType == PieceType.PAWN) {
            return matchesPawnMove(move, san, board);
        }

        return matchesPieceMove(move, san, pieceType, board);
    }

    private static boolean matchesPawnMove(Move move, String san, Board board) {
        boolean isCapture = board.getPiece(move.getTo()) != Piece.NONE || isEnPassant(move, board);

        if (isCapture) {
            if (!san.contains("x"))
                return false;

            char expectedFromFile = (char) ('a' + move.getFrom().getFile().ordinal());
            String expectedToSquare = move.getTo().toString().toLowerCase();
            String expectedSan = expectedFromFile + "x" + expectedToSquare;

            if (move.getPromotion() != Piece.NONE) {
                expectedSan += "=" + move.getPromotion().getPieceType().getSanSymbol().toUpperCase();
            }

            return san.equals(expectedSan);
        } else {
            String expectedToSquare = move.getTo().toString().toLowerCase();

            if (move.getPromotion() != Piece.NONE) {
                expectedToSquare += "=" + move.getPromotion().getPieceType().getSanSymbol().toUpperCase();
            }

            return san.equals(expectedToSquare);
        }
    }

    private static boolean matchesPieceMove(Move move, String san, PieceType pieceType, Board board) {
        String pieceSymbol = pieceType.getSanSymbol().toUpperCase();

        if (!san.startsWith(pieceSymbol)) {
            return false;
        }

        boolean isCapture = board.getPiece(move.getTo()) != Piece.NONE;
        String toSquare = move.getTo().toString().toLowerCase();

        StringBuilder expectedSan = new StringBuilder();
        expectedSan.append(pieceSymbol);

        String disambiguation = getDisambiguationForSan(move, board);
        expectedSan.append(disambiguation);

        if (isCapture) {
            expectedSan.append("x");
        }

        expectedSan.append(toSquare);

        return san.equals(expectedSan.toString());
    }

    private static String convertMoveToSan(Move move, Board board) {
        String moveStr = move.toString();
        if (moveStr != null && !moveStr.isEmpty()) {
            return moveStr;
        }

        return buildSanManually(move, board);
    }

    private static String buildSanManually(Move move, Board board) {
        StringBuilder san = new StringBuilder();

        Piece piece = board.getPiece(move.getFrom());
        PieceType pieceType = piece.getPieceType();

        // Handle castling
        if (pieceType == PieceType.KING) {
            if (move.getFrom().equals(Square.E1) || move.getFrom().equals(Square.E8)) {
                if (move.getTo().equals(Square.G1) || move.getTo().equals(Square.G8)) {
                    return "O-O";
                } else if (move.getTo().equals(Square.C1) || move.getTo().equals(Square.C8)) {
                    return "O-O-O";
                }
            }
        }

        // Add piece symbol (except for pawns)
        if (pieceType != PieceType.PAWN) {
            san.append(pieceType.getSanSymbol().toUpperCase());
            String disambiguation = getDisambiguation(move, board);
            san.append(disambiguation);
        }

        // Check for captures
        boolean isCapture = board.getPiece(move.getTo()) != Piece.NONE || isEnPassant(move, board);

        if (isCapture) {
            if (pieceType == PieceType.PAWN) {
                san.append(move.getFrom().toString().charAt(0));
            }
            san.append("x");
        }

        // Add destination square
        san.append(move.getTo().toString().toLowerCase());

        // Add promotion
        if (move.getPromotion() != Piece.NONE) {
            san.append("=").append(move.getPromotion().getPieceType().getSanSymbol().toUpperCase());
        }

        // Add check symbol
        san.append(getCheckSymbol(move, board));

        return san.toString();
    }

    private static boolean isEnPassant(Move move, Board board) {
        if (board.getPiece(move.getFrom()).getPieceType() != PieceType.PAWN) {
            return false;
        }

        return board.getPiece(move.getTo()) == Piece.NONE &&
                move.getFrom().getFile() != move.getTo().getFile();
    }

    private static String getDisambiguation(Move move, Board board) {
        List<Move> legalMoves = MoveGenerator.generateLegalMoves(board);
        Piece piece = board.getPiece(move.getFrom());

        List<Move> samePieceMoves = new ArrayList<>();
        for (Move legalMove : legalMoves) {
            if (board.getPiece(legalMove.getFrom()).equals(piece) &&
                    legalMove.getTo().equals(move.getTo()) &&
                    !legalMove.getFrom().equals(move.getFrom())) {
                samePieceMoves.add(legalMove);
            }
        }

        if (samePieceMoves.isEmpty()) {
            return "";
        }

        boolean needFileDisambiguation = false;
        boolean needRankDisambiguation = false;

        for (Move otherMove : samePieceMoves) {
            if (otherMove.getFrom().getFile() == move.getFrom().getFile()) {
                needRankDisambiguation = true;
            } else {
                needFileDisambiguation = true;
            }
        }

        StringBuilder disambiguation = new StringBuilder();
        if (needFileDisambiguation && !needRankDisambiguation) {
            disambiguation.append((char) ('a' + move.getFrom().getFile().ordinal()));
        } else if (needRankDisambiguation && !needFileDisambiguation) {
            disambiguation.append(move.getFrom().getRank().ordinal() + 1);
        } else if (needFileDisambiguation && needRankDisambiguation) {
            disambiguation.append(move.getFrom().toString().toLowerCase());
        }

        return disambiguation.toString();
    }

    private static String getDisambiguationForSan(Move move, Board board) {
        List<Move> legalMoves = MoveGenerator.generateLegalMoves(board);
        Piece piece = board.getPiece(move.getFrom());

        List<Move> conflictingMoves = new ArrayList<>();
        for (Move otherMove : legalMoves) {
            if (!otherMove.equals(move) &&
                    board.getPiece(otherMove.getFrom()).equals(piece) &&
                    otherMove.getTo().equals(move.getTo())) {
                conflictingMoves.add(otherMove);
            }
        }

        if (conflictingMoves.isEmpty()) {
            return "";
        }

        boolean fileIsUnique = true;
        for (Move conflicting : conflictingMoves) {
            if (conflicting.getFrom().getFile() == move.getFrom().getFile()) {
                fileIsUnique = false;
                break;
            }
        }

        if (fileIsUnique) {
            return String.valueOf((char) ('a' + move.getFrom().getFile().ordinal()));
        }

        boolean rankIsUnique = true;
        for (Move conflicting : conflictingMoves) {
            if (conflicting.getFrom().getRank() == move.getFrom().getRank()) {
                rankIsUnique = false;
                break;
            }
        }

        if (rankIsUnique) {
            return String.valueOf(move.getFrom().getRank().ordinal() + 1);
        }

        return move.getFrom().toString().toLowerCase();
    }

    private static String getCheckSymbol(Move move, Board board) {
        Board tempBoard = board.clone();
        tempBoard.doMove(move);

        if (tempBoard.isKingAttacked()) {
            return "+";
        }

        return "";
    }

    private static String moveToUci(Move move) {
        StringBuilder uci = new StringBuilder();
        uci.append(move.getFrom().toString().toLowerCase());
        uci.append(move.getTo().toString().toLowerCase());

        if (move.getPromotion() != Piece.NONE) {
            char promotionChar = move.getPromotion().getPieceType().getSanSymbol().toLowerCase().charAt(0);
            uci.append(promotionChar);
        }

        return uci.toString();
    }

    public static Piece getPromotionPiece(char promotionChar, Side side) {
        boolean isWhite = side == Side.WHITE;
        switch (promotionChar) {
            case 'q':
                return isWhite ? Piece.WHITE_QUEEN : Piece.BLACK_QUEEN;
            case 'r':
                return isWhite ? Piece.WHITE_ROOK : Piece.BLACK_ROOK;
            case 'b':
                return isWhite ? Piece.WHITE_BISHOP : Piece.BLACK_BISHOP;
            case 'n':
                return isWhite ? Piece.WHITE_KNIGHT : Piece.BLACK_KNIGHT;
            default:
                throw new IllegalArgumentException("Invalid promotion piece: " + promotionChar);
        }
    }
}
