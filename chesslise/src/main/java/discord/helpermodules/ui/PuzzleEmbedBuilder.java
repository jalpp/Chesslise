package discord.helpermodules.ui;

import abstraction.PuzzleView;
import chariot.model.Enums.Color;
import discord.mainhandler.Thumbnail;
import net.dv8tion.jda.api.EmbedBuilder;

/**
 * Builder class for creating Discord embeds for puzzle interactions
 */
public class PuzzleEmbedBuilder {

    private final PuzzleView puzzleView = new PuzzleView();

    /**
     * Create an embed for puzzle command responses
     */
    public EmbedBuilder createPuzzleEmbed(String fen, String boardTheme, String pieceType,
            String userMove, String opponentMove, String stepsLeft, String message) {

        StringBuilder description = new StringBuilder();

        System.out.println("User Move: " + userMove);

        if ("✅ Correct Move".equalsIgnoreCase(message)) {
            description.append(message)
                    .append("\nUser Move: ").append((String) userMove)
                    .append("\nOpponent Move: ").append(
                            (String) opponentMove)
                    .append("\nSteps Left: ").append((String) stepsLeft);
        } else {
            description.append(message);
        }

        System.out.println("User description: " + description);

        return new EmbedBuilder()
                .setDescription(createPuzzleDescription(fen))
                .setFooter(
                        "\n**Note: To adjust your puzzle difficulty, use the `/setting` command to update your preferences. **")
                .setImage(puzzleView.renderImage(fen, boardTheme, pieceType))
                .setThumbnail(getLichessLogo())
                .setDescription(description.toString());
    }

    /**
     * Create an error embed for invalid moves
     */
    public EmbedBuilder createErrorEmbed(String title, String description) {
        return new EmbedBuilder()
                .setTitle("❌ " + title)
                .setDescription(description);
    }

    /**
     * Create a success embed for puzzle completion
     */
    public EmbedBuilder createSuccessEmbed(String fen, String boardTheme, String pieceType, String userMove) {
        return new EmbedBuilder()
                .setDescription("✅ Puzzle Completed")
                .setImage(puzzleView.renderImage(fen, boardTheme, pieceType))
                .setThumbnail(getLichessLogo())
                .setFooter("Great job! Use `/puzzle` to get a new puzzle.");
    }

    /**
     * Create puzzle title based on theme
     */
    public String createPuzzleTitle(String theme) {
        return "Lichess " + theme + " Puzzle";
    }

    /**
     * Create puzzle description with turn and FEN information
     */
    public String createPuzzleDescription(String fen) {
        return "**Turn: **" + puzzleView.defineSideToMove(fen)
                + "\n**FEN: **" + fen;
    }

    /**
     * Get Lichess logo URL
     */
    public String getLichessLogo() {
        return Thumbnail.getLichessLogo();
    }

    /**
     * Get embed color
     */
    public Color getEmbedColor() {
        return Color.black;
    }
}
