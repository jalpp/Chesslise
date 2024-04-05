package Engine;

import chariot.Client;
import chariot.model.Enums;
import chariot.model.One;
import chariot.model.PerformanceStatistics;
import com.github.bhlangonijr.chesslib.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LiquidSearchEngine {


    private static Board board = null;

    /**
     * Constructor that builds the chess engine
     *
     * @param board board state
     */

    public LiquidSearchEngine(Board board) {
        LiquidSearchEngine.board = board;

    }

    /**
     * determine how should Liquid adapt based on the opponent's Lichess blitz rating
     *
     * @param opponent user
     * @return the engine level it should auto adapt to
     */


    public static Liquid_Levels determineAdaptability(String opponent) {
        Client client = Client.basic();
        One<PerformanceStatistics> userBlitz = client.users().performanceStatisticsByIdAndType(opponent, Enums.PerfType.blitz);
        int blitz_rating = userBlitz.get().perf().glicko().rating().intValue();


        if (userBlitz.isPresent() && !userBlitz.get().perf().glicko().provisional()) {
            if (blitz_rating > 2500) { //2500+
                return Liquid_Levels.BEAST;
            } else if (blitz_rating > 1900 && blitz_rating < 2500) { // [1900 - 2500]
                return Liquid_Levels.STRONG;
            } else if (blitz_rating > 1400 && blitz_rating < 1900) { // [1400 - 1900]
                return Liquid_Levels.NOVICE;
            } else if (blitz_rating < 1400) {
                return Liquid_Levels.BEGINNER;
            }
        } else {
            return determineRandomLevel();
        }

        return null;
    }

    public static Liquid_Levels determineRandomLevel() {
        Liquid_Levels[] levels = {Liquid_Levels.BEAST, Liquid_Levels.STRONG, Liquid_Levels.NOVICE, Liquid_Levels.BEGINNER};
        return levels[new Random().nextInt(levels.length)];
    }

    public static boolean isValidMove(String move) {
        chariot.util.Board b = chariot.util.Board.fromFEN(board.getFen());

        List<String> validMovesUCI = b.validMoves().stream()
                .map(chariot.util.Board.Move::uci)
                .sorted()
                .toList();

        return validMovesUCI.contains(move);
    }

    /**
     * count how much chess pieces a side has
     *
     * @param fen  board fen
     * @param side side black or white
     * @return List of piece counts
     */

    public List<Integer> countPiecesByTypeForSide(String fen, char side) {
        String[] parts = fen.split(" ");
        String placement = parts[0];
        List<Integer> getVal = new ArrayList<>();


        int pawnCount = 0;
        int knightCount = 0;
        int bishopCount = 0;
        int rookCount = 0;
        int queenCount = 0;
        int kingCount = 0;

        for (char c : placement.toCharArray()) {

            switch (c) {
                case 'P':
                    if (Character.isUpperCase(c) == (side == 'w')) {
                        pawnCount++;
                    }
                    break;
                case 'N':
                    if (Character.isUpperCase(c) == (side == 'w')) {
                        knightCount++;
                    }
                    break;
                case 'B':
                    if (Character.isUpperCase(c) == (side == 'w')) {
                        bishopCount++;
                    }
                    break;
                case 'R':
                    if (Character.isUpperCase(c) == (side == 'w')) {
                        rookCount++;
                    }
                    break;
                case 'Q':
                    if (Character.isUpperCase(c) == (side == 'w')) {
                        queenCount++;
                    }
                    break;
                case 'K':
                    if (Character.isUpperCase(c) == (side == 'w')) {
                        kingCount++;
                    }
                    break;
                case 'p':
                    if (Character.isLowerCase(c) == (side == 'b')) {
                        pawnCount++;
                    }
                    break;
                case 'n':
                    if (Character.isLowerCase(c) == (side == 'b')) {
                        knightCount++;
                    }
                    break;
                case 'b':
                    if (Character.isLowerCase(c) == (side == 'b')) {
                        bishopCount++;
                    }
                    break;
                case 'r':
                    if (Character.isLowerCase(c) == (side == 'b')) {
                        rookCount++;
                    }
                    break;
                case 'q':
                    if (Character.isLowerCase(c) == (side == 'b')) {
                        queenCount++;
                    }
                    break;
                case 'k':
                    if (Character.isLowerCase(c) == (side == 'b')) {
                        kingCount++;
                    }
                    break;
                default:
                    break;
            }
        }

        getVal.add(pawnCount);
        getVal.add(knightCount);
        getVal.add(rookCount);
        getVal.add(queenCount);
        getVal.add(bishopCount);


        return getVal;


    }

    /**
     * calculate piece eval for the current board state
     *
     * @param board current board state
     * @return the piece eval
     */

    public int calculatePieceEval(com.github.bhlangonijr.chesslib.Board board) {

        List<Integer> whiteSide = countPiecesByTypeForSide(board.getFen(), 'w');
        List<Integer> blackSide = countPiecesByTypeForSide(board.getFen(), 'b');

        int evalwhite = (whiteSide.get(0) * 10) + (whiteSide.get(1) * 30) + (whiteSide.get(2) * 50) + (whiteSide.get(3) * 150) + (whiteSide.get(4) * 30) + (700);
        int evalblack = (blackSide.get(0) * 10) + (blackSide.get(1) * 30) + (blackSide.get(2) * 50) + (blackSide.get(3) * 150) + (blackSide.get(4) * 30) + (700);

        return (evalwhite - evalblack);


    }

    /**
     * evaluate the current board state based on:
     * - amount of pieces both sides have
     * - both kings are attacked
     * - board space and center space
     * - look at captures
     * - look at which pieces are where
     *
     * @return eval for the board
     */


    public int evaluateBoard() {

        try {
            int BISHOP_VALUE = 8;
            int KNIGHT_VALUE = BISHOP_VALUE + 4;
            int ROOK_VALUE = 5;
            int KING_VALUE = 500;
            int QUEEN_VALUE = BISHOP_VALUE + ROOK_VALUE;
            int PAWN_VALUE = QUEEN_VALUE - 1;
            int KING_ATTACKED = 70;
            int KING_MATED = 500;
            int divideFactor = 2000;
            int score = 0;
            int scoreb = 0;
            int scorew = 0;
            score += (board.legalMoves().size());
            score += calculatePieceEval(board);

            try {

                for (int i = 0; i < board.legalMoves().size(); i++) {
                    if (board.getSideToMove().equals(Side.WHITE)) {
                        if (board.getPiece(board.legalMoves().get(i).getFrom()).getPieceType().equals(PieceType.BISHOP)) {
                            scorew += BISHOP_VALUE;
                        } else if (board.getPiece(board.legalMoves().get(i).getFrom()).getPieceType().equals(PieceType.KNIGHT)) {
                            scorew += KNIGHT_VALUE;
                        } else if (board.getPiece(board.legalMoves().get(i).getFrom()).getPieceType().equals(PieceType.PAWN)) {
                            scorew += PAWN_VALUE;
                        } else if (board.getPiece(board.legalMoves().get(i).getFrom()).getPieceType().equals(PieceType.QUEEN)) {
                            scorew += QUEEN_VALUE;
                        } else if (board.getPiece(board.legalMoves().get(i).getFrom()).getPieceType().equals(PieceType.ROOK)) {
                            scorew += ROOK_VALUE;
                        } else {
                            scorew += KING_VALUE;
                        }
                    } else if (board.getSideToMove().flip().equals(Side.BLACK)) {
                        if (board.getPiece(board.legalMoves().get(i).getFrom()).getPieceType().equals(PieceType.BISHOP)) {
                            scoreb -= BISHOP_VALUE;
                        } else if (board.getPiece(board.legalMoves().get(i).getFrom()).getPieceType().equals(PieceType.KNIGHT)) {
                            scoreb -= KNIGHT_VALUE;
                        } else if (board.getPiece(board.legalMoves().get(i).getFrom()).getPieceType().equals(PieceType.PAWN)) {
                            scoreb -= PAWN_VALUE;
                        } else if (board.getPiece(board.legalMoves().get(i).getFrom()).getPieceType().equals(PieceType.QUEEN)) {
                            scoreb -= QUEEN_VALUE;
                        } else if (board.getPiece(board.legalMoves().get(i).getFrom()).getPieceType().equals(PieceType.ROOK)) {
                            scoreb -= ROOK_VALUE;
                        } else if (board.getPiece(board.legalMoves().get(i).getFrom()).getPieceType().equals(PieceType.KING)) {
                            scoreb -= KING_VALUE;
                        }

                    }
                }


                if (!board.isKingAttacked()) {
                    for (int j = 0; j < board.pseudoLegalCaptures().size(); j++) {
                        if (board.getSideToMove().equals(Side.WHITE)) {
                            if (board.getPiece(board.pseudoLegalCaptures().get(j).getFrom()).getPieceType().equals(PieceType.BISHOP)) {
                                scorew += BISHOP_VALUE + 3;
                            } else if (board.getPiece(board.pseudoLegalCaptures().get(j).getFrom()).getPieceType().equals(PieceType.KNIGHT)) {
                                scorew += KNIGHT_VALUE + 2;
                            } else if (board.getPiece(board.pseudoLegalCaptures().get(j).getFrom()).getPieceType().equals(PieceType.PAWN)) {
                                scorew += PAWN_VALUE + 1;
                            } else if (board.getPiece(board.pseudoLegalCaptures().get(j).getFrom()).getPieceType().equals(PieceType.QUEEN)) {
                                scorew += QUEEN_VALUE + 9;
                            } else if (board.getPiece(board.pseudoLegalCaptures().get(j).getFrom()).getPieceType().equals(PieceType.ROOK)) {
                                scorew += ROOK_VALUE + 8;
                            } else {
                                scorew += KING_VALUE + 1;
                            }
                        } else if (board.getSideToMove().flip().equals(Side.BLACK)) {
                            if (board.getPiece(board.pseudoLegalCaptures().get(j).getFrom()).getPieceType().equals(PieceType.BISHOP)) {
                                scoreb -= BISHOP_VALUE + 3;
                            } else if (board.getPiece(board.pseudoLegalCaptures().get(j).getFrom()).getPieceType().equals(PieceType.KNIGHT)) {
                                scoreb -= KNIGHT_VALUE + 2;
                            } else if (board.getPiece(board.pseudoLegalCaptures().get(j).getFrom()).getPieceType().equals(PieceType.PAWN)) {
                                scoreb -= PAWN_VALUE + 1;
                            } else if (board.getPiece(board.pseudoLegalCaptures().get(j).getFrom()).getPieceType().equals(PieceType.QUEEN)) {
                                scoreb -= QUEEN_VALUE + 9;
                            } else if (board.getPiece(board.pseudoLegalCaptures().get(j).getFrom()).getPieceType().equals(PieceType.ROOK)) {
                                scoreb -= ROOK_VALUE + 8;
                            } else if (board.getPiece(board.pseudoLegalCaptures().get(j).getFrom()).getPieceType().equals(PieceType.KING)) {
                                scoreb -= KING_VALUE + 1;
                            }

                        }
                    }

                    for (int j = 0; j < board.getPieceLocation(Piece.WHITE_BISHOP).size(); j++) {
                        if (board.getPieceLocation(Piece.WHITE_BISHOP).get(j).getFile().equals(File.FILE_A) ||
                                board.getPieceLocation(Piece.WHITE_BISHOP).get(j).getFile().equals(File.FILE_H)) {
                            scorew -= BISHOP_VALUE - 1;
                        }
                    }

                    for (int p = 0; p < board.getPieceLocation(Piece.WHITE_PAWN).size(); p++) {
                        if (board.getPieceLocation(Piece.WHITE_PAWN).get(p).getFile().equals(File.FILE_D)
                                || board.getPieceLocation(Piece.WHITE_PAWN).get(p).getFile().equals(File.FILE_C) ||
                                board.getPieceLocation(Piece.WHITE_PAWN).get(p).getFile().equals(File.FILE_E)) {
                            scorew += PAWN_VALUE * 5;
                        }
                    }

                    for (int n = 0; n < board.getPieceLocation(Piece.WHITE_KNIGHT).size(); n++) {
                        if (board.getPieceLocation(Piece.WHITE_KNIGHT).get(n).getFile().equals(File.FILE_A)
                                || board.getPieceLocation(Piece.WHITE_KNIGHT).get(n).getFile().equals(File.FILE_H)) {
                            scorew -= KNIGHT_VALUE * 7;
                        }
                    }

                    if (board.getMoveCounter() < 10) {

                        for (int r = 0; r < board.getPieceLocation(Piece.WHITE_ROOK).size(); r++) {
                            if (board.getPieceLocation(Piece.WHITE_ROOK).get(r).getFile().equals(File.FILE_A)
                                    || board.getPieceLocation(Piece.WHITE_ROOK).get(r).getFile().equals(File.FILE_H)) {
                                scorew -= ROOK_VALUE * 7;
                            }
                        }

                    } else {
                        for (int r = 0; r < board.getPieceLocation(Piece.WHITE_ROOK).size(); r++) {
                            if (board.getPieceLocation(Piece.WHITE_ROOK).get(r).getFile().equals(File.FILE_E)
                                    || board.getPieceLocation(Piece.WHITE_ROOK).get(r).getFile().equals(File.FILE_D)) {
                                scorew += ROOK_VALUE * 7;
                            }
                        }
                    }

                    if (board.getMoveCounter() < 20) {
                        if (board.getKingSquare(Side.WHITE).getFile().equals(File.FILE_D) ||
                                (board.getKingSquare(Side.WHITE).getFile().equals(File.FILE_F) ||
                                        board.getKingSquare(Side.WHITE).getFile().equals(File.FILE_C) && board.getKingSquare(Side.WHITE).getRank().equals(Rank.RANK_2))) {
                            scorew -= KING_ATTACKED;
                        }
                    }

                } else {
                    if (board.getSideToMove().equals(Side.WHITE)) {
                        scorew += board.pseudoLegalCaptures().size() + 1;

                    } else if (board.getSideToMove().equals(Side.BLACK)) {
                        scoreb -= board.pseudoLegalCaptures().size() - 1;
                    }
                }


            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            if (board.isKingAttacked() && board.getSideToMove().equals(Side.WHITE)) {
                score += KING_ATTACKED;
            } else {
                score -= KING_ATTACKED;
            }

            if (board.isMated() && board.getSideToMove().equals(Side.WHITE)) {
                score += KING_MATED;
            } else {
                score -= KING_MATED;
            }


            if (board.getSideToMove().equals(Side.WHITE)) {
                return (-1 * score + (scorew - scoreb)) / divideFactor;
            }

            return (-1 * score + (scorew - scoreb)) / divideFactor;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return 0;
    }

    /**
     * find best move on opponent levels
     *
     * @param levels opponent level
     * @param board  current board state
     * @return Liquid engine move
     */


    public String findBestMoveBasedOnLevels(Liquid_Levels levels, Board board) {

        switch (levels) {

            case BEGINNER -> {
                return getMoveBasedOnFreqLevels(Liquid_Levels.BEGINNER, 3, board);
            }

            case NOVICE -> {
                return getMoveBasedOnFreqLevels(Liquid_Levels.NOVICE, 1, board);
            }

            case STRONG -> {
                return getMoveBasedOnFreqLevels(Liquid_Levels.STRONG, 1, board);
            }

            case BEAST -> {
                return getMoveBasedOnFreqLevels(Liquid_Levels.BEAST, 0, board);

            }

        }

        return null;

    }

    /**
     * get Liquid engine move based on level and frequency of mistakes
     *
     * @param levels    opponent level
     * @param frequency frequency of mistakes
     * @param board     current board state
     * @return Liquid engine move
     */


    public String getMoveBasedOnFreqLevels(Liquid_Levels levels, int frequency, Board board) {
        Random random = new Random();
        String move = StockFish.getBestMove(13, board.getFen());
        String movelower = StockFish.getBestMove(5, board.getFen());
        String movehigher = StockFish.getBestMove(10, board.getFen());
        String[] candidates = {movehigher, movelower, move};
        boolean isFirstMove = board.getMoveCounter() == 1;
        chariot.util.Board b = chariot.util.Board.fromFEN(board.getFen());

        List<String> validMovesUCI = b.validMoves().stream()
                .map(chariot.util.Board.Move::uci)
                .sorted()
                .toList();

        if (isFirstMove) {
            return move;
        }

        switch (levels) {

            case BEGINNER -> {
                if (random.nextInt(0, frequency) == 0) {
                    return move;
                } else {
                    return LiquidFindBestMove(2);
                }
            }

            case NOVICE -> {
                if (random.nextInt(0, frequency) == 0) {
                    return movelower;
                } else {
                    return LiquidFindBestMove(2);
                }
            }

            case STRONG -> {
                if (random.nextInt(0, frequency) == 0) {
                    return movehigher;
                } else {
                    return LiquidFindBestMove(2);
                }
            }

            case BEAST -> {
                return move;
            }
        }

        return null;
    }

    /**
     * Find Liquid's best move using eval and negamax algorithm
     *
     * @param depth depth for analysis
     * @return Liquid chess engine move
     */


    public String LiquidFindBestMove(int depth) {

        chariot.util.Board b = chariot.util.Board.fromFEN(board.getFen());

        List<String> validMovesUCI = b.validMoves().stream()
                .map(chariot.util.Board.Move::uci)
                .sorted()
                .toList();


        int bestValue = Integer.MIN_VALUE;
        String bestMove = null;
        for (String move : validMovesUCI) {
            board.doMove(move);
            int value = -negamaxliquid(depth - 1, Integer.MIN_VALUE, Integer.MAX_VALUE, false);
            board.undoMove();
            if (value > bestValue) {
                bestValue = value;
                bestMove = move;
            }
        }


        if (isValidMove(bestMove)) {
            return bestMove;
        } else {
            return StockFish.getBestMove(5, board.getFen());

        }
    }

    /**
     * negamax liquid algorithm
     *
     * @param depth       the depth
     * @param alpha       alpha value
     * @param beta        beta value
     * @param isMaxPlayer maxing boolean value
     * @return the value to be used in finding move function
     */

    private int negamaxliquid(int depth, int alpha, int beta, boolean isMaxPlayer) {

        chariot.util.Board b = chariot.util.Board.fromFEN(board.getFen());

        List<String> validMovesUCI = b.validMoves().stream()
                .map(chariot.util.Board.Move::uci)
                .sorted()
                .toList();


        if (depth == 0 || board.isMated() || board.isDraw()) {

            return evaluateBoard();
        }
        int bestValue = Integer.MIN_VALUE;
        for (String move : validMovesUCI) {
            board.doMove(move);
            int value = -negamaxliquid(depth - 1, -beta, -alpha, !isMaxPlayer);
            board.undoMove();
            bestValue = Math.max(bestValue, value);
            alpha = Math.max(alpha, value);
            if (alpha >= beta) {
                break;
            }
        }


        return bestValue;
    }


    public String EngineInfo() {
        return "Author: Jalp \n" + "Version: Liquid Engine V11 \n \n Source Code: https://github.com/jalpp/LiseChessEngine \n Please Note Lise uses Stockfish's help, please read source code." +
                "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣠⣴⣶⠾⠿⠿⠯⣷⣄⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣼⣾⠛⠁⠀⠀⠀⠀⠀⠀⠈⢻⣦⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣠⣾⠿⠁⠀⠀⠀⢀⣤⣾⣟⣛⣛⣶⣬⣿⣆⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣾⠟⠃⠀⠀⠀⠀⠀⣾⣿⠟⠉⠉⠉⠉⠛⠿⠟⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                "⠀⠀⠀⠀⠀⠀⠀⠀⢀⣴⡟⠋⠀⠀⠀⠀⠀⠀⠀⣿⡏⣤⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                "⠀⠀⠀⠀⠀⠀⠀⣠⡿⠛⠀⠀⠀⠀⠀⠀⠀⠀⠀⠙⣷⡍⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣀⣀⣤⣤⣤⣀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                "⠀⠀⠀⠀⠀⣠⣼⡏⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⠙⠷⣤⣤⣠⣤⣤⡤⡶⣶⢿⠟⠹⠿⠄⣿⣿⠏⠀⣀⣤⡦⠀⠀⠀⠀⣀⡄\n" +
                "⢀⣄⣠⣶⣿⠏⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⠉⠓⠚⠋⠉⠀⠀⠀⠀⠀⠀⠈⠛⡛⡻⠿⠿⠙⠓⢒⣺⡿⠋⠁\n" +
                "⠉⠉⠉⠛⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠉⠉⠁⠀" + "art by (https://emojicombos.com/ocean-ascii-art)";


    }


    public void EngineUCICommands() {
        System.out.println("LISE UCI COMMANDS: \n");
        System.out.println("-------------------------------------------------------");
        System.out.println("isready (check if engine is ready)");
        System.out.println("level (Change level)");
        System.out.println("position <FEN_STRING> (get Lise's best move in given FEN)");
        System.out.println("eval <FEN_STRING> (get Lise's eval in given FEN)");
        System.out.println("uci (view Lise UCI commands)");
        System.out.println("quit (Quit the engine)");

    }


}
