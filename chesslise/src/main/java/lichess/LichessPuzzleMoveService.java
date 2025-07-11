package lichess;

import com.github.bhlangonijr.chesslib.*;
import com.github.bhlangonijr.chesslib.move.Move;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LichessPuzzleMoveService {

    private static final HashMap<String, Map<String, Object>> userPuzzleMapping = new HashMap<>();

//    private static final MongoCollection<Document> lichessPuzzleCollection = Main.getLichessPuzzleCollection();

    public LichessPuzzleMoveService() {

    }

    public void updateLichessPuzzleData(String userid, String FEN , List<String>moves,String theme,String pieceType,String boardTheme,String rating,String puzzleType) {
        try {
            System.out.println("Inside updateLichessPuzzleData");
            Map<String, Object> puzzleDetails = new HashMap<>();
            puzzleDetails.put("FEN", FEN);
            puzzleDetails.put("moves", moves);
            puzzleDetails.put("theme",theme);
            puzzleDetails.put("pieceType",pieceType);
            puzzleDetails.put("currentStep", 0);
            puzzleDetails.put("boardTheme",boardTheme);
            puzzleDetails.put("rating",rating);
            puzzleDetails.put("puzzletype",puzzleType);
            userPuzzleMapping.put(userid, puzzleDetails);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public Map<String, Object> isPuzzleExistForUser(String userid) {
        try {
            System.out.println("Inside isPuzzleExistForUser");
            if (userPuzzleMapping.containsKey(userid)) {
                return userPuzzleMapping.get(userid);
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Map<String, Object> updatePuzzleStep(String userid, String move) {
        try {
            System.out.println("Inside updatePuzzleStep");
            Map<String, Object> currentPuzzleData = userPuzzleMapping.get(userid);
            List<String> moves = (List<String>) currentPuzzleData.get("moves");
            Integer currentStep = (Integer) currentPuzzleData.get("currentStep");
            String FEN = (String) currentPuzzleData.get("FEN");

            if (moves.get(currentStep).equalsIgnoreCase(move)) {

                String newFEN = generateFEN(FEN,moves.get(currentStep));
                if(currentStep < moves.size()-1){
                    newFEN = generateFEN(newFEN,moves.get(currentStep+1));
                }
                Map<String, Object> puzzleDetails = new HashMap<>();

                puzzleDetails.put("FEN", newFEN);
                puzzleDetails.put("moves", currentPuzzleData.get("moves"));
                puzzleDetails.put("currentStep", currentStep + 2);
                puzzleDetails.put("theme", currentPuzzleData.get("theme"));
                puzzleDetails.put("pieceType", currentPuzzleData.get("pieceType"));
                puzzleDetails.put("boardTheme",currentPuzzleData.get("boardTheme"));
                puzzleDetails.put("rating",currentPuzzleData.get("rating"));
                puzzleDetails.put("userMove",move);

                puzzleDetails.put("stepsLeft", (int) Math.ceil((moves.size() - currentStep) / 2.0) -1 ) ;
                puzzleDetails.put("message", "✅ Correct Move");
                if(currentStep >= moves.size()-1){
                    puzzleDetails.put("message", "✅ Correct Move \nPuzzle Solved");
                    userPuzzleMapping.remove(userid);
                }
                else{
                    puzzleDetails.put("opponentMove",moves.get(currentStep+1));
                    userPuzzleMapping.put(userid,puzzleDetails);
                }
                System.out.println("puzzleDetails==="+puzzleDetails);
                return puzzleDetails;
            }
            else{
                currentPuzzleData.put("message","❌ Incorrect Move");
                return currentPuzzleData;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String generateFEN(String fen, String move) {

        Board board = new Board();
        board.loadFromFen(fen);

        Square from = Square.valueOf(move.substring(0, 2).toUpperCase());
        Square to = Square.valueOf(move.substring(2, 4).toUpperCase());

        Move moveObj;

        // Handle promotion
        if (move.length() == 5) {
            char promoChar = move.charAt(4);
            Piece promotionPiece = getPromotionPiece(promoChar, board.getSideToMove());

            moveObj = new Move(from, to, promotionPiece);
        } else {
            // Normal move
            moveObj = new Move(from, to);
        }

        board.doMove(moveObj);
        System.out.println(board.getFen());
        return board.getFen();

    }

    private Piece getPromotionPiece(char promo, Side sideToMove) {
        return switch (Character.toLowerCase(promo)) {
            case 'q' -> sideToMove == Side.WHITE ? Piece.WHITE_QUEEN : Piece.BLACK_QUEEN;
            case 'r' -> sideToMove == Side.WHITE ? Piece.WHITE_ROOK : Piece.BLACK_ROOK;
            case 'b' -> sideToMove == Side.WHITE ? Piece.WHITE_BISHOP : Piece.BLACK_BISHOP;
            case 'n' -> sideToMove == Side.WHITE ? Piece.WHITE_KNIGHT : Piece.BLACK_KNIGHT;
            default -> throw new IllegalArgumentException("Invalid promotion piece: " + promo);
        };
    }


}
