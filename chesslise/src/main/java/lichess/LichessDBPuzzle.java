package lichess;


public class LichessDBPuzzle {

    private final String fen;
    private final String rating;
    private final String gameURL;

    
    public LichessDBPuzzle(String fen, String rating, String gameURL) {
        this.fen = fen;
        this.rating = rating;
        this.gameURL = gameURL;
    }

    
    public String getFen() {
        return fen;
    }

    
    public String getRating() {
        return rating;
    }


    public String getGameURL() {
        return gameURL;
    }
}