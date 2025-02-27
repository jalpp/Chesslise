package Game;

/**
 *  the game schema
 */
public class GameSchema {

    private String userid;
    private String fen;
    private Integer depth;

    public GameSchema(String userid, String fen, Integer depth) {
        this.userid = userid;
        this.fen = fen;
        this.depth = depth;
    }

    /**
     * gets the user id
     * @return the user id
     */
    public String getUserid() {
        return userid;
    }

    /**
     * gets the fen
     * @return the fen
     */
    public String getFen() {
        return fen;
    }

    /**
     * the toString of the game
     * @return the game string rep
     */
    @Override
    public String toString() {
        return "GameSchema{" +
                "userid='" + userid + '\'' +
                ", fen='" + fen + '\'' +
                ", depth=" + depth +
                '}';
    }

    /**
     * gets the fen
     * @param fen the fen
     */
    public void setFen(String fen) {
        this.fen = fen;
    }

    /**
     * gets the depth
     * @return the depth
     */
    public Integer getDepth() {
        return depth;
    }

}
