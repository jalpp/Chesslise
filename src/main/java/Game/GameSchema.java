package Game;

public class GameSchema {

    private String userid;
    private String fen;
    private Integer depth;

    public GameSchema(String userid, String fen, Integer depth) {
        this.userid = userid;
        this.fen = fen;
        this.depth = depth;
    }

   
    public String getUserid() {
        return userid;
    }

    
    public String getFen() {
        return fen;
    }

    
    @Override
    public String toString() {
        return "GameSchema{" +
                "userid='" + userid + '\'' +
                ", fen='" + fen + '\'' +
                ", depth=" + depth +
                '}';
    }

    
    public void setFen(String fen) {
        this.fen = fen;
    }

    
    public Integer getDepth() {
        return depth;
    }

}
