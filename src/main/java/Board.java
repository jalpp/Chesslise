import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;
import java.io.File;

public class Board {

    private EmbedBuilder embedBuilder;
    private String[] moves;
    private com.github.bhlangonijr.chesslib.Board board;

    public Board(String moves){
        this.board = new com.github.bhlangonijr.chesslib.Board();
        this.moves =  moves.split(" ");
    }

    public EmbedBuilder getView(){
        try {
            this.embedBuilder = new EmbedBuilder();

            

            for (String m : moves) {
                this.board.doMove(m);
            }

            String cor = board.getFen();

            String[] split = cor.split(" ");
            String coordImg = "https://chessboardimage.com/" + split[0] + ".png";

            this.embedBuilder.setColor(Color.blue);
            this.embedBuilder.setImage(coordImg);

            return this.embedBuilder;
        }catch (Exception e){
            return this.embedBuilder.setDescription("error!");
        }


    }



}
