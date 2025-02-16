package lichess;

public class LichessDBPuzzle {

    private final String puzzleId;
    private final String fen;
    private final String moves;
    private final String rating;
    private final String themes;
    private final String gameURL;

    public LichessDBPuzzle(String puzzleId, String fen, String moves, String rating, String themes, String gameURL) {
        this.puzzleId = puzzleId;
        this.fen = fen;
        this.moves = moves;
        this.rating = rating;
        this.themes = themes;
        this.gameURL = gameURL;
    }

    public String getPuzzleId() {
        return puzzleId;
    }

    public String getFen() {
        return fen;
    }

    public String getMoves() {
        return moves;
    }

    public String getRating() {
        return rating;
    }

    public String getThemes() {
        return themes;
    }

    public String getGameURL() {
        return gameURL;
    }


}
