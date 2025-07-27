package discord.helpermodules;

import java.util.List;

import abstraction.CommandTrigger;
import discord.helpermodules.data.PuzzleDataService;
import discord.helpermodules.data.UserSettingsService;
import discord.helpermodules.logic.PuzzleMoveValidator;
import discord.helpermodules.ui.PuzzleEmbedBuilder;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import setting.SettingSchemaModule;

/**
 * Refactored main module for handling puzzle solution commands
 * Now uses modular components for better separation of concerns
 */
public class PuzzleSolutionHelperModule extends SettingSchemaModule implements CommandTrigger {

    private final SlashCommandInteractionEvent event;
    private final PuzzleEmbedBuilder embedBuilder;

    public PuzzleSolutionHelperModule(SlashCommandInteractionEvent event) {
        super(event.getUser().getId());
        this.event = event;
        this.embedBuilder = new PuzzleEmbedBuilder();
    }

    @Override
    public void trigger(String commandName) {
        System.out.println("Command triggered: " + this.event.getOptionsByName("move") +
                " with command name: " + commandName);

        List<OptionMapping> options = event.getOptions();
        if (commandName.equalsIgnoreCase("puzzlesolve")) {
            validateMove(options.get(0).getAsString());
        }
    }

    /**
     * Validate and process a user's puzzle move
     */
    private void validateMove(String move) {
        this.event.deferReply().queue();

        try {
            String userId = this.event.getUser().getId();

            // Validate move using the modular validator
            PuzzleMoveValidator.ValidationResult result = PuzzleMoveValidator.validateMove(move, userId);

            // Get user settings
            String boardTheme = UserSettingsService.getBoardTheme(userId);
            String pieceType = UserSettingsService.getPieceType(userId);

            // Handle different result scenarios
            if ("Invalid Move Format".equals(result.getMessage())) {
                EmbedBuilder errorEmbed = embedBuilder.createErrorEmbed("Invalid Move",
                        "Please provide your move in format like `e2e4` or `e7e8q`.");
                this.event.getHook().sendMessageEmbeds(errorEmbed.build()).queue();
                return;
            }

            if ("No Puzzle Found".equals(result.getMessage())) {
                this.event.reply(
                        "❗ You haven't generated a LichessDB puzzle yet. Use the `/puzzle` command to get started!")
                        .queue();
                return;
            }

            // Create appropriate embed based on result
            EmbedBuilder embed;
            if (result.isPuzzleComplete()) {
                embed = embedBuilder.createSuccessEmbed(result.getNewFen(), boardTheme, pieceType,
                        result.getUserMove());
            } else if (result.isCorrect()) {
                embed = embedBuilder.createPuzzleEmbed(result.getNewFen(), boardTheme, pieceType,
                        result.getUserMove(), result.getOpponentMove(),
                        String.valueOf(result.getStepsLeft()), "✅ Correct Move");
            } else {
                embed = embedBuilder.createPuzzleEmbed(result.getNewFen(), boardTheme, pieceType,
                        "", "", String.valueOf(result.getStepsLeft()), result.getMessage());
            }

            this.event.getHook().sendMessageEmbeds(embed.build()).queue();

        } catch (Exception e) {
            System.out.println("Error validating move: " + e.getMessage());
            EmbedBuilder errorEmbed = embedBuilder.createErrorEmbed("Error",
                    "An error occurred while processing your move. Please try again.");
            this.event.getHook().sendMessageEmbeds(errorEmbed.build()).queue();
        }
    }

    // Static utility methods for external use
    /**
     * Save puzzle data for a user (instance method wrapper)
     */
    public void saveGeneratedPuzzleData(String fen, List<String> uciMoves) {
        PuzzleDataService.saveGeneratedPuzzleData(fen, uciMoves, this.event.getUser().getId());
    }

    /**
     * Static method for saving puzzle data
     */
    public static void saveGeneratedPuzzleData(String fen, List<String> uciMoves, String userId) {
        PuzzleDataService.saveGeneratedPuzzleData(fen, uciMoves, userId);
    }
}
