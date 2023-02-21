import chariot.Client;
import com.github.bhlangonijr.chesslib.*;
import com.github.bhlangonijr.chesslib.move.Move;
import com.github.bhlangonijr.chesslib.move.MoveGenerator;
import org.jetbrains.annotations.NotNull;


import java.util.*;


public class LiseChessEngine {

    private Board board;
    private ArrayList<Board> trainingGames = new ArrayList<>();
    private String[] openingBook;
    private List<Move> captures;
    private List<Move> attackMoves;
    private chariot.util.Board board1;


    private Client client;
    private Random random;
    private String movesan;
    private int movePicker;


    public LiseChessEngine(Board board){
        this.board = board;
    }



    public boolean doWhiteMove(String move){


        Move m = new Move(move, Side.WHITE);

        if(this.board.isMoveLegal(m, true)){
            return true;
        }else{
            return false;
        }


    }


    // check game state

    public Boolean gameOver(){
        if(this.board.isMated() || this.board.isDraw() || this.board.isStaleMate()){
            this.trainingGames.add(this.board);
            return true;
        }

        return false;
    }

    // Two main opening books



    public String playFromOpeningBookHippo(int index, boolean side){

        if(side == false) {
            this.openingBook = new String[]{"move-maker", "e7e6", "g8e7", "g7g6", "f8g7", "e8g8", "d7d6", "b8d7", "b7b6", "c8b7", "a7a6", "h7h6", "c7c6", "d8c7"};

            return this.openingBook[index];
        }else{
            String[]white = new String[]{"move-maker", "e2e3", "g1e2", "g2g3", "e1g1", "d2d3", "b1d2", "b2b3", "c1b2", "a2a3", "c2c3", "d1c2", "f1e1", "a1d1"};
            return white[index];
        }




    }


    public String playFromOpeningBookKIA(int index){
        this.openingBook = new String[]{"move-maker", "Nf6", "d6", "g6", "Bg7", "O-O", "a6", "Nd7", "b6", "c6", "Re8", "Qc7", "Rb8", "d5"};

        return this.openingBook[index];
    }

    // calculate and store all valid captures when king is not attacked, if the king is attacked captures are placed with non captures


    public List<Move> getCaptures(){

        if(this.board.isKingAttacked()){
            return this.board.legalMoves();
        }

        this.captures = this.board.pseudoLegalCaptures();

        return this.captures;
    }


    // searching for attacking moves in the middlegame, not currently used in main Engine logic, but will be added soon




    public List<Move> searchMiddleGameMoves(){

        for(Move m: this.board.legalMoves()){
            if(this.board.isAttackedBy(m)){
                this.attackMoves.add(m);
            }

        }

        if(this.attackMoves.size() > 0){
            return this.attackMoves;
        }

        return this.board.legalMoves();
    }


    // Opening Engine algo which can be used in Discord, for Lichess sides switch resulting this algo to break
    // plays best chess move from negamax algo + opening book, if openings don't go as planned well, you just play the best move!


    public void abstractedRandomizer(){
        this.random = new Random();
        this.movePicker = random.nextInt(this.board.legalMoves().size());
        this.board.setSideToMove(Side.BLACK);



        if(this.board.getMoveCounter() <= 13){

            try {

                Random mover = new Random();
                int coinFlip = mover.nextInt(2);

                if(coinFlip == 0){


                    this.board.doMove(this.playFromOpeningBookHippo(this.board.getMoveCounter(), false));


                }else{

                    this.board.doMove(this.playFromOpeningBookKIA(this.board.getMoveCounter()));

                }


                //this.board.setSideToMove(Side.WHITE);
            }catch (Exception e){
                //this.board.undoMove();

                this.board.doMove(findBestMove());
            }

        }

        else{

            //this.board.
            this.board.doMove(findBestMove());
        }

    }



    public void playRandomLegalMoves(){



        this.board.doMove(findBestMove());



        // return this.board;
    }


    public Move getKingsRunningSqs(){
        return this.board.legalMoves().get(this.board.legalMoves().size()-1);
    }

    public List<Move> getTail(){
        List<Move> tail = this.board.legalMoves().subList(((this.board.legalMoves().size()-1)/2),this.board.legalMoves().size()-1 );

        return tail;
    }


    // main chess algo for Lichess
    // find the best chess move according to negamax algo, depth set to 5 for less search fast moves to plat


    public Move getBestMove(Board board) {
        return minimax(board, 0, 4, Integer.MIN_VALUE, Integer.MAX_VALUE, board.getSideToMove());
    }

    private Move minimax(Board board, int depth, int maxDepth, int alpha, int beta, Side side) {
        Move bestMove = null;

        if (depth == maxDepth || board.isMated() || board.isStaleMate()) {
            return null;
        }

        for (Move move : board.legalMoves()) {
            board.doMove(move);
            int score = minimaxScore(board, depth + 1, maxDepth, alpha, beta, side.flip());
            board.undoMove();

            if (side == Side.WHITE && score > alpha) {
                alpha = score;
                bestMove = move;
            } else if (side == Side.BLACK && score < beta) {
                beta = score;
                bestMove = move;
            }

            if (alpha >= beta) {
                break;
            }
        }

        return bestMove;
    }

    private int minimaxScore(Board board, int depth, int maxDepth, int alpha, int beta, Side side) {
        if (depth == maxDepth || board.isMated() || board.isStaleMate()) {
            return evaluateBoard(board);
        }

        for (Move move : board.legalMoves()) {
            board.doMove(move);
            int score = minimaxScore(board, depth + 1, maxDepth, alpha, beta, side.flip());
            board.undoMove();

            if (side == Side.WHITE && score > alpha) {
                alpha = score;
            } else if (side == Side.BLACK && score < beta) {
                beta = score;
            }

            if (alpha >= beta) {
                break;
            }
        }

        return (side == Side.WHITE) ? alpha : beta;
    }

    private int evaluateBoard(Board board) {
        int score = 0;

        score += (whitePieceAdvantage() - blackPieceAdvantage());

        // chessboard area eval, less space less eval and vise versa

        score += this.board.legalMoves().size();
        // Add your own evaluation function here
        return score;
    }


    public Move generateMoveFromIndexLookUp(Board board){
        List<Integer> intPos = new ArrayList<>();
        Random random = new Random();
        Random movePicker = new Random();
        HashMap<Integer, Move> generator = new HashMap<>();


        for(int i = 0; i < board.legalMoves().size(); i++){
            int indexPicker = random.nextInt(0, 65);
            intPos.add(Integer.valueOf(indexPicker));
        }


        for(int j = 0; j < board.legalMoves().size(); j++){
            generator.put(intPos.get(j), board.legalMoves().get(j));
        }

        Collections.shuffle(intPos);

        Move m =  generator.get(intPos.get(new Random().nextInt(intPos.size())));
        int tableBaseMovePicker = movePicker.nextInt(2);

        if(board.getSideToMove().equals(Side.WHITE)){
            if(tableBaseMovePicker == 0){
                return this.playTopTableBaseMove(board);
            }else {
                return this.getBestMove(board);
            }
        }else{
            if(tableBaseMovePicker == 0){
                return playTopTableBaseMove(board);
            }else{
                return m;
            }

        }

//        if(board.getMoveCounter() <= 10){
//            if(m.getTo().getFile().name().equalsIgnoreCase("FILE_A") || m.getTo().getFile().name().equalsIgnoreCase("FILE_H")){
//                List<Move> boardclone = new ArrayList<>();
//                for(int x = 0; x < board.legalMoves().size(); x++){
//                    boardclone.add(board.legalMoves().get(x));
//                }
//                boardclone.remove(m);
//                return generator.get(boardclone.get(new Random().nextInt(boardclone.size())));
//            }
//            return m;
//        }
        //System.out.println(m.getType().toString());
        //System.out.println(board.isA);




    }


    public Move findBestMove() {



        List<Move> tail = this.board.legalMoves().subList(((this.board.legalMoves().size()-1)/2),this.board.legalMoves().size()-1 );
//        tail.addAll(this.board.pseudoLegalCaptures());

        List<Move> head = this.board.legalMoves().subList(0, (this.board.legalMoves().size()-1)/2);

        if(this.board.isKingAttacked()){
            return getKingsRunningSqs();
        }else {

            if(this.board.isKingAttacked()){
                return getKingsRunningSqs();
            }

            int depth = 5; // Adjust the search depth as needed
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

            System.out.println(bestMove);

            return bestMove;
        }
    }

    // negamax algo that uses max and min values to determine the best move to find, uses a simple eval function

    private int negamax(int depth, int alpha, int beta) {
        List<Move> tail = this.board.legalMoves().subList((this.board.legalMoves().size()-1)/2,this.board.legalMoves().size()-1 );
        if (depth == 0 || this.board.isMated() || this.board.isDraw()) {
            // Evaluate the current board position and return a score
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

        System.out.println(bestValue);

        return bestValue;
    }



    // piece value function, follows "normal" chess piece values
    // change piece value from 1 to 100 to 1000 according the higher the value, the better eval function's move findings
    // for Lise queen and king are same value, to help in endgames



    private int valueMapping(Piece p){
        int value = 0;
        switch (p){
            case WHITE_PAWN:
                return 1;
            case WHITE_BISHOP:
                return 3;

            case WHITE_KNIGHT:
                return 4;

            case WHITE_ROOK:
                return 5;

            case WHITE_QUEEN:
                return 9;

            case WHITE_KING:
                return 200;

            case BLACK_PAWN:
                return 1;

            case BLACK_BISHOP:
                return 3;

            case BLACK_KNIGHT:
                return 4;

            case BLACK_ROOK:
                return 5;

            case BLACK_QUEEN:
                return 9;

            case BLACK_KING:
                return 200;

            default:
                return 0;

        }
        //return 0;
    }


    public int getBCount(Board board){
        int count = 0;
        for (Square square : Square.values()) {
            if (board.getPiece(square).getPieceType().equals(PieceType.BISHOP)) {
                count++;
            }
        }

        return count;
    }



    // calculate white piece advantage on number of pieces with the values attached to it

    private int whitePieceAdvantage(){
        int counter = 0;
        Random random = new Random();
        int picker = random.nextInt(2);
        int pawn = random.nextInt(8);


        counter += (picker * valueMapping(Piece.WHITE_BISHOP))+ (pawn * valueMapping(Piece.WHITE_PAWN)) + ( picker * valueMapping(Piece.WHITE_QUEEN)) + (picker  * valueMapping(Piece.WHITE_ROOK)) +
                (picker * valueMapping(Piece.WHITE_KNIGHT)) + (Piece.WHITE_KING.ordinal() * valueMapping(Piece.WHITE_KING));

        return counter;
    }

    // calculate black piece advantage on number of pieces with the values attached to it

    private int blackPieceAdvantage(){
        int blackCounter = 0;
        blackCounter += (Piece.BLACK_BISHOP.ordinal()* valueMapping(Piece.BLACK_BISHOP)) + (Piece.BLACK_KNIGHT.ordinal() * valueMapping(Piece.BLACK_KNIGHT))+ (Piece.BLACK_QUEEN.ordinal()* valueMapping(Piece.BLACK_QUEEN)) + (Piece.BLACK_ROOK.ordinal()
                * valueMapping(Piece.BLACK_ROOK))+ (Piece.BLACK_PAWN.ordinal()* valueMapping(Piece.BLACK_PAWN)) + (Piece.BLACK_KING.ordinal() * valueMapping(Piece.BLACK_KING));

        return blackCounter;
    }

    private int evaluate() {
        int score = 0;
        //Random random = new Random();
        //score += random.nextInt(64);

        //core += random.nextInt(6400);
        // material evaluation

        score += (whitePieceAdvantage() - blackPieceAdvantage());

        score -= 64;



        // chessboard area eval, less space less eval and vise versa

        score += this.board.legalMoves().size();

//        if(board.getSideToMove().equals(Side.WHITE) && board.isKingAttacked()){
//            score -= 3000;
//
//        }else if(board.getSideToMove().equals(Side.BLACK) && board.isKingAttacked()){
//            score += 3000;
//        }

        if(board.getSideToMove().equals(Side.WHITE)){
            return score * 1;
        }else{
            return score * -1;
        }

    }


    // very basic tactic creator, not used will come in future

    public List<Move> generateCheckTactics() {
        List<Move> tactics = new ArrayList<>();
        for (Move move : this.board.legalMoves()) {
            this.board.doMove(move);
            if (this.board.isKingAttacked()) {
                tactics.add(move);
            }
            this.board.undoMove();
        }
        return tactics;
    }



    // still in works, better way to implement an endgame tablebase support



    public Move playTopTableBaseMove(Board board){
        Client client1 = Client.basic();
        String annotation = client1.tablebase().standard(board.getFen()).get().moves().get(0).san();
        Move move = new Move(annotation, board.getSideToMove());
        return move;
    }



    public void playTablebaseMoves(){
        // this.board = new Board();
        this.board.setSideToMove(Side.BLACK);
        this.client = Client.basic();

        String move = this.client.tablebase().standard(this.board.getFen()).get().moves().get(this.movePicker).san();
        this.board.doMove(move);
        this.board.setSideToMove(Side.WHITE);

        //return this.board;

    }

    // Discord helper methods

    public void resetBoard(){
        this.board = new Board();
    }

    public String getFenBoard(){
        return this.board.getFen();
    }

    public String getImageOfCurrentBoard(){
        ChessUtil chessUtil = new ChessUtil();
        return chessUtil.getImageFromFEN(this.board.getFen());
    }












}
