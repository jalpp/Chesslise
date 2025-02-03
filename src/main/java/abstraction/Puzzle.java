package abstraction;

import net.dv8tion.jda.api.EmbedBuilder;

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
     * GET the command card in Embed form
     *
     * @return the command card
     */
    EmbedBuilder defineCommandCard();


}
