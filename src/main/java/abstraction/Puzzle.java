package abstraction;

import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;

/**
 * Interface for handling Puzzle Commands
 */
public interface Puzzle {


    /**
     * Get the puzzle FEN
     *
     * @return the puzzle FEN
     */
    String definePuzzleFen();

    /**
     * Get the command card in Embed form
     *
     * @return the command card
     */
    EmbedBuilder defineCommandCard();

    /**
     * Get the puzzle website's logo
     *
     * @return the puzzle logo URL
     */
    String definePuzzleLogo();

    /**
     * Get the puzzle cards title
     *
     * @return the title
     */
    String definePuzzleTitle();

    /**
     * Get the embed color
     *
     * @return the embed color
     */
    Color defineEmbedColor();

    /**
     * Get the puzzle embed description
     *
     * @return the puzzle description
     */
    String definePuzzleDescription();


}
