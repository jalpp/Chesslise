package discord.mainhandler;

/**
 * Handles the thumbnail logos across the app
 */
public class Thumbnail {

    private static String ChessliseLogo = "https://raw.githubusercontent.com/jalpp/DojoIcons/dd7365ea7d768fe17056d9b14ee6740c2bf4e261/oldIcons/Black%20Blue%20White%20Tactical%20eSports%20Discord%20Logo.png";

    private static String StockfishLogo = "https://stockfishchess.org/images/logo/icon_512x512@2x.png";

    private static String LichessLogo = "https://upload.wikimedia.org/wikipedia/commons/4/47/Lichess_logo_2019.png";

    private static String ChesscomLogo = "https://static.wikia.nocookie.net/logopedia/images/4/4a/Chess.com_2019_%28App_Icon%29.png/revision/latest/scale-to-width-down/250?cb=20221006103032";

    /**
     * Gets the Chesslise logo
     *
     * @return the Chesslise logo
     */
    public static String getChessliseLogo() {
        return ChessliseLogo;
    }

    /**
     * Gets the Stockfish logo
     *
     * @return get the stockfish logo
     */
    public static String getStockfishLogo() {
        return StockfishLogo;
    }

    /**
     * Gets the Lichess logo
     *
     * @return gets the Lichess logo
     */
    public static String getLichessLogo() {
        return LichessLogo;
    }

    /**
     * Gets the Chess.com logo
     *
     * @return gets the chess.com logo
     */
    public static String getChesscomLogo() {
        return ChesscomLogo;
    }
}
