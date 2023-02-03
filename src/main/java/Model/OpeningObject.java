

public class OpeningObject {

    private String fen;

    public OpeningObject(String fen){

        this.fen = fen;
    }


    public String getImage(){
        return "https://chessboardimage.com/" + this.fen + ".png";
    }





}
