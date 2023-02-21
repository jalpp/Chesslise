
import com.github.bhlangonijr.chesslib.Board;

public class ChessUtil {

    private Board board;
    private chariot.util.Board Chariotboard;

    public ChessUtil(){

        this.board = new Board();

    }


    public String getImageFromFEN(String fen){
        try{
            String img = "";
            this.board.loadFromFen(fen);
            String[] getImgCord = this.board.getFen().split(" ");
            img += "https://lichess1.org/export/fen.gif?fen=" + getImgCord[0] + "&theme=blue&piece=alpha";
            return img;

        }catch(Exception e){
         return "Please provide a valid FEN!";
        }



    }

    

}
