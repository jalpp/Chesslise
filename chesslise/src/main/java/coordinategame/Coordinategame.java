package coordinategame;


import abstraction.Puzzle;
import abstraction.PuzzleView;
import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.Piece;
import com.github.bhlangonijr.chesslib.Square;
import discord.mainhandler.Thumbnail;
import net.dv8tion.jda.api.EmbedBuilder;
import setting.SettingSchema;

import java.awt.*;
import java.util.*;


public class Coordinategame extends PuzzleView implements Puzzle {

    private final String fen;
    private final ArrayList<String> answers;
    private Piece piece;
    private Square square;
    private final HashMap<String, String> answersmap = new HashMap<>();



    public Coordinategame(){
        this.fen = getRandomPiecePlacement();
        this.answers = generateAnswers(this.fen);
    }

    public ArrayList<String> getAnswers() {
        return answers;
    }

    public HashMap<String, String> getAnswersmap() {
        return answersmap;
    }

    private ArrayList<String> generateAnswers(String fen){
        ArrayList<String> answers = new ArrayList<>(5);
        String[] Files = {"a","b","c","d","e","f","g","h"};
        Integer[] Ranks = {1,2,3,4,5,6,7,8};
        String correctAnswer = (this.square.getFile().getNotation() + this.square.getRank().getNotation()).toLowerCase();

        for(int i = 0; i < Files.length; i++){
            if(Files[i].equalsIgnoreCase(this.square.getFile().getNotation())) {
                String swap = Files[Files.length - 1];
                Files[i] = swap;
                Files[Files.length - 1] = this.square.getFile().getNotation();
            }
        }

        for(int j = 0; j < Ranks.length; j++){
            if(Ranks[j] == Integer.parseInt(this.square.getRank().getNotation())){
                int swap = Ranks[Ranks.length - 1];
                Ranks[j] = swap;
                Ranks[Ranks.length - 1] = Integer.parseInt(this.square.getRank().getNotation());
            }
        }

        Random random = new Random();
        for(int i = 0; i < 4; i++){
            answers.add(Files[random.nextInt(Files.length)] + Ranks[random.nextInt(Ranks.length)]);
            answersmap.put(answers.get(i), "f");
        }

        answers.add(correctAnswer);
        answersmap.put(correctAnswer, "t");

        Collections.shuffle(answers);

        return answers;
    }

    private String getRandomPiecePlacement() {
        Piece[] pieces = getActualPieces();
        Square[] sqaures = getActualSqaures();

        Random random = new Random();
        Board board = new Board();
        board.clear();
        this.piece = pieces[random.nextInt(pieces.length)];
        this.square = sqaures[random.nextInt(sqaures.length)];
        board.setPiece(piece, square);

        return board.getFen();
    }

    private Piece[] getActualPieces(){
        Piece[] pieces = Piece.values();
        Piece[] finalPieces = new Piece[pieces.length];
        for(int i = 0; i < pieces.length; i++){
            if(pieces[i] != Piece.NONE){
                finalPieces[i] = pieces[i];
            }
        }

        return finalPieces;
     }

     private Square[] getActualSqaures(){
        Square[] squares = Square.values();
        Square[] finalSquares = new Square[squares.length];
        for(int j = 0; j < squares.length; j++){
            if(squares[j] != Square.NONE){
                finalSquares[j] = squares[j];
            }
        }

        return finalSquares;
     }

    @Override
    public String definePuzzleFen() {
        return this.fen;
    }


    @Override
    public EmbedBuilder defineCommandCard(SettingSchema schema) {
        return new EmbedBuilder().setDescription(definePuzzleDescription()).setColor(defineEmbedColor()).setTitle(definePuzzleTitle()).setImage(renderImage(definePuzzleFen(), schema)).setThumbnail(definePuzzleLogo());
    }

    @Override
    public String definePuzzleLogo() {
        return Thumbnail.getChessliseLogo();
    }

    @Override
    public String definePuzzleTitle() {
        return "Find the correct coordinate \n";
    }

    @Override
    public Color defineEmbedColor() {
        return Color.blue;
    }

    @Override
    public String definePuzzleDescription() {
        return  "Where is " + this.piece.getSanSymbol() + " located?";
    }

    @Override
    public String toString() {
        return "Coordinategame{" +
                "fen='" + fen + '\'' +
                ", answers=" + Arrays.toString(answers.toArray()) +
                ", piece=" + piece +
                ", square=" + square +
                '}';
    }
}