
import com.github.bhlangonijr.chesslib.*;
import com.github.bhlangonijr.chesslib.move.Move;
import com.github.bhlangonijr.chesslib.move.MoveGenerator;



import java.util.*;


public class LiseChessEngine {

    private Board board;



    public LiseChessEngine(Board board){
        this.board = board;
    }



    public Boolean gameOver(){
        if(this.board.isMated() || this.board.isDraw() || this.board.isStaleMate()){
            return true;
        }

        return false;
    }



    public void playDiscordBotMoves(){
        this.board.doMove(findBestMove());
    }

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

    public int calculatePieceEval(Board board){

        List<Integer> whiteSide = countPiecesByTypeForSide(board.getFen(), 'w');
        List<Integer> blackSide = countPiecesByTypeForSide(board.getFen(), 'b');



        int evalwhite = (whiteSide.get(0) * 10) + (whiteSide.get(1) * 30) + (whiteSide.get(2) * 50) + (whiteSide.get(3) * 150) + (whiteSide.get(4) * 30) + (1 * 700);
        int evalblack = (blackSide.get(0) * 10) + (blackSide.get(1) * 30) + (blackSide.get(2) * 50) + (blackSide.get(3) * 150) + (blackSide.get(4) * 30) + (1 * 700);

        return (evalwhite - evalblack);


    }


    private int evaluateBoard(Board board) {



        try {


            int BISHOP_VALUE = 6;
            int KNIGHT_VALUE = BISHOP_VALUE + 2;
            int PAWN_VALUE = BISHOP_VALUE + 1;
            int QUEEN_VALUE = 1;
            int ROOK_VALUE = 1;
            int KING_VALUE = 0;

            int KING_ATTACKED = 70;
            int KING_MATED = 500;

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
                    }else if(board.getSideToMove().flip().equals(Side.BLACK)){
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
                            } else if (board.getPiece(board.legalMoves().get(i).getFrom()).getPieceType().equals(PieceType.KING)){
                                scoreb -= KING_VALUE;
                            }

                    }
                }



                if(!board.isKingAttacked()) {
                    for (int j = 0; j < board.pseudoLegalCaptures().size(); j++) {
                        if (board.getSideToMove().equals(Side.WHITE)) {
                            if (board.getPiece(board.pseudoLegalCaptures().get(j).getFrom()).getPieceType().equals(PieceType.BISHOP)) {
                                scorew += BISHOP_VALUE + 1;
                            } else if (board.getPiece(board.pseudoLegalCaptures().get(j).getFrom()).getPieceType().equals(PieceType.KNIGHT)) {
                                scorew += KNIGHT_VALUE + 1;
                            } else if (board.getPiece(board.pseudoLegalCaptures().get(j).getFrom()).getPieceType().equals(PieceType.PAWN)) {
                                scorew += PAWN_VALUE + 1;
                            } else if (board.getPiece(board.pseudoLegalCaptures().get(j).getFrom()).getPieceType().equals(PieceType.QUEEN)) {
                                scorew += QUEEN_VALUE + 1;
                            } else if (board.getPiece(board.pseudoLegalCaptures().get(j).getFrom()).getPieceType().equals(PieceType.ROOK)) {
                                scorew += ROOK_VALUE + 1;
                            } else {
                                scorew += KING_VALUE + 1;
                            }
                        } else if(board.getSideToMove().flip().equals(Side.BLACK)){
                            if (board.getPiece(board.pseudoLegalCaptures().get(j).getFrom()).getPieceType().equals(PieceType.BISHOP)) {
                                scoreb -= BISHOP_VALUE + 1;
                            } else if (board.getPiece(board.pseudoLegalCaptures().get(j).getFrom()).getPieceType().equals(PieceType.KNIGHT)) {
                                scoreb -= KNIGHT_VALUE + 1;
                            } else if (board.getPiece(board.pseudoLegalCaptures().get(j).getFrom()).getPieceType().equals(PieceType.PAWN)) {
                                scoreb -= PAWN_VALUE + 1;
                            } else if (board.getPiece(board.pseudoLegalCaptures().get(j).getFrom()).getPieceType().equals(PieceType.QUEEN)) {
                                scoreb -= QUEEN_VALUE + 1;
                            } else if (board.getPiece(board.pseudoLegalCaptures().get(j).getFrom()).getPieceType().equals(PieceType.ROOK)) {
                                scoreb -= ROOK_VALUE + 1;
                            } else if (board.getPiece(board.pseudoLegalCaptures().get(j).getFrom()).getPieceType().equals(PieceType.KING)){
                                scoreb -= KING_VALUE + 1;
                            }

                        }
                    }
                }else{
                    if(board.getSideToMove().equals(Side.WHITE)){
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
            }else{
                score -= KING_ATTACKED;
            }

            if (board.isMated() && board.getSideToMove().equals(Side.WHITE)) {
                score += KING_MATED;
            }else{
                score -= KING_MATED;
            }


            if(board.getSideToMove().equals(Side.WHITE)){
                return score + (scorew - scoreb);
            }



            return -1 * score;
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        return 0;
    }


    public Move generateHumanPlayFromBlackSide(Board board){
        List<Integer> intPos = new ArrayList<>();
        Random random = new Random();
        HashMap<Integer, Move> generator = new HashMap<>();


        for (int i = 0; i < board.legalMoves().size(); i++) {
            int indexPicker = random.nextInt(0, 65);
            intPos.add(Integer.valueOf(indexPicker));
        }


        for (int j = 0; j < board.legalMoves().size(); j++) {
            generator.put(intPos.get(j), board.legalMoves().get(j));
        }

        Collections.shuffle(intPos);
        Collections.rotate(intPos, 2);


        return generator.get(intPos.get(new Random().nextInt(intPos.size())));
    }



    public Move generateMoveFromIndexLookUp(Board board){

        try {

            if (board.getSideToMove().equals(Side.WHITE)) {
                for(Move m: board.legalMoves()){
                    if(board.getContext().isCastleMove(m)){
                        return m;
                    }
                }

            if(board.getSideToMove().equals(Side.WHITE)){
                Board boardCheckMate = board.clone();
                for(Move m: board.legalMoves()){
                    boardCheckMate.doMove(m);
                    if(boardCheckMate.isMated()){
                        return m;
                    }
                    boardCheckMate.undoMove();
                }
            }

              return LocalfindBestMove(board, 14);
            } else if (board.getSideToMove().equals(Side.BLACK)) {

               return generateHumanPlayFromBlackSide(board);
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;

        }

        return null;


    }


    public Move LocalfindBestMove(Board board, int depth) {


        // Adjust the search depth as needed
        int bestValue = Integer.MIN_VALUE;
        Move bestMove = null;
        for (Move move : board.legalMoves()) {
            board.doMove(move);
            int value = negamaxlocal(depth - 1, Integer.MAX_VALUE, Integer.MIN_VALUE, board);
            board.undoMove();
            if (value > bestValue) {
                bestValue = value;
                bestMove = move;
            }
        }

        try {

            return bestMove;


        }catch (Exception e){
            System.out.println(e.getMessage());
            return board.legalMoves().get(0);
        }



    }


    private int negamaxlocal(int depth, int alpha, int beta, Board board) {
        if (depth == 0 || board.isMated() || board.isDraw()) {

            return evaluateBoard(board);
        }
        int bestValue = Integer.MIN_VALUE;
        for (Move move : board.legalMoves()) {
            board.doMove(move);
            int value = negamaxlocal(depth - 1, -beta, -alpha, board);
            board.undoMove();
            bestValue = Math.max(bestValue, value);
            alpha = Math.max(alpha, value);
            if (alpha >= beta) {
                break;
            }
        }


        return bestValue;
    }


    public Move findBestMove() {


            int depth = 5;
            int bestValue = Integer.MIN_VALUE;
            Move bestMove = null;
            for (Move move : MoveGenerator.generateLegalMoves(this.board)) {
                this.board.doMove(move);
                int value = negamax(depth - 1, Integer.MAX_VALUE, Integer.MIN_VALUE);
                this.board.undoMove();
                if (value > bestValue) {
                    bestValue = value;
                    bestMove = move;
                }
            }


            return bestMove;

    }



    private int negamax(int depth, int alpha, int beta) {
        if (depth == 0 || this.board.isMated() || this.board.isDraw()) {

            return evaluate();
        }
        int bestValue = Integer.MIN_VALUE;
        for (Move move : MoveGenerator.generateLegalMoves(this.board)) {
            this.board.doMove(move);
            int value = negamax(depth - 1, -beta, -alpha);
            this.board.undoMove();
            bestValue = Math.max(bestValue, value);
            alpha = Math.max(alpha, value);
            if (alpha >= beta) {
                break;
            }
        }


        return bestValue;
    }





    private int evaluate() {
        int score = 0;

        score -= 64;

        score += this.board.legalMoves().size();


        if(board.getSideToMove().equals(Side.WHITE)){
            return score * 1;
        }else{
            return score * -1;
        }

    }


    public void resetBoard(){
        this.board = new Board();
    }



    public String getImageOfCurrentBoard(){
        ChessUtil chessUtil = new ChessUtil();
        return chessUtil.getImageFromFEN(this.board.getFen());
    }



}
