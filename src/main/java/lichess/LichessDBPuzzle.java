package lichess;

/**
 * Represents a Lichess database puzzle.
 */
public class LichessDBPuzzle {

    private final String puzzleId;
    private final String fen;
    private final String moves;
    private final String rating;
    private final String themes;
    private final String gameURL;

    /**
     * Constructs a LichessDBPuzzle instance.
     *
     * @param puzzleId the ID of the puzzle
     * @param fen the FEN string of the puzzle
     * @param moves the moves of the puzzle
     * @param rating the rating of the puzzle
     * @param themes the themes of the puzzle
     * @param gameURL the URL of the game
     */
    public LichessDBPuzzle(String puzzleId, String fen, String moves, String rating, String themes, String gameURL) {
        this.puzzleId = puzzleId;
        this.fen = fen;
        this.moves = moves;
        this.rating = rating;
        this.themes = themes;
        this.gameURL = gameURL;
    }

    /**
     * Gets the FEN string of the puzzle.
     *
     * @return the FEN string
     */
    public String getFen() {
        return fen;
    }

    /**
     * Gets the rating of the puzzle.
     *
     * @return the rating
     */
    public String getRating() {
        return rating;
    }

    /**
     * Gets the URL of the game.
     *
     * @return the game URL
     */
    public String getGameURL() {
        return gameURL;
    }
}