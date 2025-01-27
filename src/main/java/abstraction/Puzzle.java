package abstraction;

import net.dv8tion.jda.api.EmbedBuilder;

/**
 * Interface for handling Puzzle Commands
 */
public interface Puzzle {
    /**
     * Render image from FEN
     *
     * @param util the chess util
     * @param fen  the fen
     * @return the image link
     */
    String renderImage(ChessUtil util, String fen);

    /**
     * Define which side to move
     *
     * @param util the chess util
     * @param fen  the fen
     * @return the side to move
     */
    String defineSideToMove(ChessUtil util, String fen);

    /**
     * Define the analysis board URL
     *
     * @param util the chess util
     * @param fen  the fen
     * @return the analysis board
     */
    String defineAnalysisBoard(ChessUtil util, String fen);

    /**
     * Get the chess util
     *
     * @return the chess util
     */
    ChessUtil defineUtil();

    /**
     * Get the puzzle FEN
     *
     * @return the puzzle FEN
     */
    String definePuzzleFen();

    /**
     * GET the command card in Embed form
     *
     * @return the command card
     */
    EmbedBuilder defineCommandCard();


}
