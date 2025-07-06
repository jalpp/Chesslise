package discord.helpermodules;

import abstraction.CommandTrigger;
import abstraction.PuzzleView;
import discord.mainhandler.Thumbnail;
import lichess.LichessPuzzleMoveService;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.awt.*;
import java.util.Map;
import java.util.Objects;

public class MoveHelperModule extends PuzzleView implements CommandTrigger {

    private final SlashCommandInteractionEvent slashEvent;


    public MoveHelperModule(SlashCommandInteractionEvent event) {
        this.slashEvent = event;
    }

    public void checkIfPuzzleExistForUser(String move) {
        String userId = this.slashEvent.getUser().getId();
        LichessPuzzleMoveService lichessPuzzleMoveService = new LichessPuzzleMoveService();
        if (lichessPuzzleMoveService.isPuzzleExistForUser(userId) == null) {
            slashEvent.reply("❗ You haven't generated a LichessDB puzzle yet. Use the `/puzzle` command to get started!")
                    .setEphemeral(true) // optional: only visible to the user
                    .queue();
            return;
        }

        move = Objects.requireNonNull(this.slashEvent.getOption("move")).getAsString();
        System.out.println(move);

        slashEvent.deferReply().queue();

        if (move == null || !move.matches("^[a-h][1-8][a-h][1-8][qrbn]?$")) {
            EmbedBuilder errorEmbed = new EmbedBuilder()
                    .setTitle("❌ Invalid Move")
                    .setDescription("Please provide your move in format like `e2e4` or `e7e8q`.")
                    .setColor(Color.RED);

            slashEvent.getHook()
                    .sendMessageEmbeds(errorEmbed.build())
                    .queue();
            return;
        }

        System.out.println(move);

        Map<String, Object> updatedMoves = lichessPuzzleMoveService.updatePuzzleStep(userId, move);
//
        EmbedBuilder embed = defineCommandCard((String) updatedMoves.get("FEN"), (String) updatedMoves.get("theme"), (String) updatedMoves.get("rating"), (String) updatedMoves.get("pieceType"), (String) updatedMoves.get("boardTheme"), (String) updatedMoves.get("userMove"), (String) updatedMoves.get("opponentMove"), String.valueOf(updatedMoves.get("stepsLeft")), (String) updatedMoves.get("message"));
//
        slashEvent.getHook()
                .sendMessageEmbeds(embed.build())
                .queue();

    }

    @Override
    public void trigger(String commandName) {
        if (commandName.equalsIgnoreCase("lichessdbpuzzlemove")) {
            checkIfPuzzleExistForUser(commandName);
        }

    }

    public EmbedBuilder defineCommandCard(String fen, String theme, String rating, String pieceType, String boardTheme, String userMove, String opponentMove, String stepsLeft, String message) {
        System.out.println("Inside defineCommandCard params ");
        StringBuilder description = new StringBuilder();
        if (message.equalsIgnoreCase(("✅ Correct Move"))) {
            description.append(message).append("\nUser Move: ").append(userMove).append("\nOpponent Move: ").append(opponentMove).append("\nSteps Left: ").append(stepsLeft);
        } else {
            description.append(message);
        }
        return new EmbedBuilder().setDescription(definePuzzleDescription(fen, rating)).setColor(defineEmbedColor())
                .setTitle(definePuzzleTitle(theme)).setFooter("\n**Note: To adjust your puzzle difficulty, use the `/setting` command to update your preferences. **").setImage(renderImage(fen, boardTheme, pieceType))
                .setThumbnail(definePuzzleLogo())
                .setDescription(description.toString());
    }


    public String definePuzzleTitle(String theme) {
        return "Lichess " + theme + " Puzzle";
    }

    public String definePuzzleDescription(String fen, String rating) {
        return "**Turn: **" + defineSideToMove(fen) + "\n **Rating: **" + rating
                + "\n**FEN: **" + fen;
    }

    public String definePuzzleLogo() {
        return Thumbnail.getLichessLogo();
    }

    public Color defineEmbedColor() {
        return Color.CYAN;
    }
}
