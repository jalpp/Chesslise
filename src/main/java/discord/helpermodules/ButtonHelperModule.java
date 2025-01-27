package discord.helpermodules;

import discord.mainhandler.CommandInfo;
import lichess.DailyCommand;
import lichess.Game;
import chariot.Client;
import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.Side;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import net.dv8tion.jda.api.interactions.modals.Modal;

import java.awt.*;

import static discord.helpermodules.ChessSlashHelperModule.*;

/**
 * ButtonHelperModule class to handle the button logic
 */
public class ButtonHelperModule {

    private final ButtonInteractionEvent buttonEvent;
    private final Board board;
    private final Board blackBoard;
    private final Client client;


    public ButtonHelperModule(ButtonInteractionEvent buttonEvent, Board board, Board blackBoard, Client client) {
        this.buttonEvent = buttonEvent;
        this.board = board;
        this.blackBoard = blackBoard;
        this.client = client;
    }

    /**
     * Handle the logic for playing the engine with /move
     */
    public void sendPlayingEngineFlow() {
        switch (buttonEvent.getComponentId()) {
            case "bot-lose" -> {
                board.loadFromFen(new Board().getFen());
                buttonEvent.editMessageEmbeds(new EmbedBuilder().setDescription("You have resigned the game!").setColor(Color.red).build()).setActionRow(net.dv8tion.jda.api.interactions.components.buttons.Button.danger("bot-lose", "Resgin").asDisabled(), net.dv8tion.jda.api.interactions.components.buttons.Button.secondary("bot-draw", "Draw").asDisabled()).queue();
            }
            case "bot-lose-black" -> {
                blackBoard.loadFromFen(new Board().getFen());
                buttonEvent.editMessageEmbeds(new EmbedBuilder().setDescription("You have resigned the game!").setColor(Color.red).build()).setActionRow(net.dv8tion.jda.api.interactions.components.buttons.Button.danger("bot-lose-black", "Resgin").asDisabled(), net.dv8tion.jda.api.interactions.components.buttons.Button.secondary("bot-draw-black", "Draw").asDisabled()).queue();
            }
            case "bot-draw" -> {
                board.loadFromFen(new Board().getFen());
                buttonEvent.editMessageEmbeds(new EmbedBuilder().setDescription("Lise accepts the draw!").setColor(Color.yellow).build()).setActionRow(net.dv8tion.jda.api.interactions.components.buttons.Button.danger("bot-lose", "Resgin").asDisabled(), net.dv8tion.jda.api.interactions.components.buttons.Button.secondary("bot-draw", "Draw").asDisabled()).queue();
            }
            case "bot-draw-black" -> {
                board.loadFromFen(new Board().getFen());
                buttonEvent.editMessageEmbeds(new EmbedBuilder().setDescription("Lise accepts the draw!").setColor(Color.yellow).build()).setActionRow(net.dv8tion.jda.api.interactions.components.buttons.Button.danger("bot-lose-black", "Resgin").asDisabled(), Button.secondary("bot-draw-black", "Draw").asDisabled()).queue();
            }
            case "white-side" -> {
                board.setSideToMove(Side.WHITE);
                buttonEvent.reply("Set to White Side!").queue();
            }
            case "black-side" -> {
                board.setSideToMove(Side.BLACK);
                buttonEvent.reply("Set to Black Side!").queue();
            }
        }
    }

    /**
     * Handle the logic for play command flow with /play
     */
    public void sendPlayCommandFlow() {

        switch (buttonEvent.getComponentId()) {
            case "load-again" -> buttonEvent.reply("""
                    ## Please Pick Your Lichess Game's Mode ⚔️\s
                    
                    """).addActionRow(
                    Button.success("casmode", "\uD83D\uDC4C Casual"), Button.danger("ratedmode", "\uD83E\uDD3A Rated"), Button.success("friend", "\uD83D\uDDE1️ Play Friend")).addActionRow(Button.link("https://discord.gg/d2EHaw27hn", "\uD83D\uDC4B Join our server!"), Button.link("https://lichess.org/login", "\uD83D\uDD12 Login/Register"), Button.secondary("playhelp", "❓ Help")).queue();
            case "loadr" ->
                    buttonEvent.editMessage(" ## Please Pick Your Time Control ⏱️").setActionRow(Button.danger("3+0r", "\uD83D\uDD25 3+0")
                            , Button.danger("5+0r", "\uD83D\uDD25 5+0")
                            , Button.danger("10+0r", "\uD83D\uDC07 10+0"), Button.success("load-again", "↩️ Home")).queue();
            case "loadc" ->
                    buttonEvent.editMessage(" ## Please Pick Your Time Control ⏱️").setActionRow(Button.primary("3+0c", " \uD83D\uDD253+0")
                            , Button.primary("5+0c", "\uD83D\uDD25 5+0")
                            , Button.primary("10+0c", "\uD83D\uDC07 10+0"), Button.success("load-again", "↩️ Home")).queue();
            case "3+0r" ->
                    buttonEvent.editMessage(Game.generateOpenEndedChallengeURLs(3, 0, true, client)).setActionRow(Button.primary("3+0r", "Rematch"), Button.success("load-again", "↩️ Home")).queue();
            case "5+0r" ->
                    buttonEvent.editMessage(Game.generateOpenEndedChallengeURLs(5, 0, true, client)).setActionRow(Button.primary("5+0r", "Rematch"), Button.success("load-again", "↩️ Home")).queue();
            case "10+0r" ->
                    buttonEvent.editMessage(Game.generateOpenEndedChallengeURLs(10, 0, true, client)).setActionRow(Button.primary("10+0r", "Rematch"), Button.success("load-again", "↩️ Home")).queue();
            case "3+0c" ->
                    buttonEvent.editMessage(Game.generateOpenEndedChallengeURLs(3, 0, false, client)).setActionRow(Button.primary("3+0c", "Rematch"), Button.success("load-again", "↩️ Home")).queue();
            case "5+0c" ->
                    buttonEvent.editMessage(Game.generateOpenEndedChallengeURLs(5, 0, false, client)).setActionRow(Button.primary("5+0c", "Rematch"), Button.success("load-again", "↩️ Home")).queue();
            case "10+0c" ->
                    buttonEvent.editMessage(Game.generateOpenEndedChallengeURLs(10, 0, false, client)).setActionRow(Button.primary("10+0c", "Rematch"), Button.success("load-again", "↩️ Home")).queue();
            case "playhelp" -> {
                buttonEvent.replyEmbeds(getPlayCommandInfo().build()).setEphemeral(true).queue();
            }
            case "friend" -> sendSelfUserInputForm();
        }

    }

    /**
     * Handle the logic for the play command UI for /play
     */
    public void sendPlayCommandUI() {

        switch (buttonEvent.getComponentId()) {
            case "casmode" -> buttonEvent.editMessage("## Please Pick Your Time Control ⏱️").setActionRow(
                    Button.primary("ultrafastc", "\uD83D\uDE85 1/4+0"),
                    Button.primary("bulletfastc", "\uD83D\uDE85 1+0"),
                    Button.primary("blitzfastc", "\uD83D\uDD25 3+2"),
                    Button.primary("rapidfastc", "\uD83D\uDD25 5+5"),
                    Button.success("loadc", "\uD83D\uDD04 Load More Time Controls")
            ).queue();

            case "ratedmode" -> buttonEvent.editMessage("## Please Pick Your Time Control ⏱️").setActionRow(
                    Button.danger("ultrafastr", "\uD83D\uDE85 1/4+0"),
                    Button.danger("bulletfastr", "\uD83D\uDE85 1+0"),
                    Button.danger("blitzfastr", "\uD83D\uDD25 3+2"),
                    Button.danger("rapidfastr", "\uD83D\uDD25 5+5"),
                    Button.success("loadr", "\uD83D\uDD04 Load More Time Controls")
            ).queue();

        }

    }

    /**
     * Handle the logic for more time controls for /play
     */
    public void sendMoreTimeControls() {

        switch (buttonEvent.getComponentId()) {
            case "ultrafastc" ->
                    buttonEvent.editMessage(Game.generateOpenEndedChallengeURLs(0, 0, false, client)).setActionRow(Button.primary("ultrafastc", "Rematch"), Button.success("load-again", "↩️ Home")).queue();
            case "bulletfastc" ->
                    buttonEvent.editMessage(Game.generateOpenEndedChallengeURLs(1, 0, false, client)).setActionRow(Button.primary("bulletfastc", "Rematch"), Button.success("load-again", "↩️ Home")).queue();
            case "blitzfastc" ->
                    buttonEvent.editMessage(Game.generateOpenEndedChallengeURLs(3, 2, false, client)).setActionRow(Button.primary("blitzfastc", "Rematch"), Button.success("load-again", "↩️ Home")).queue();
            case "rapidfastc" ->
                    buttonEvent.editMessage(Game.generateOpenEndedChallengeURLs(5, 5, false, client)).setActionRow(Button.primary("rapidfastc", "Rematch"), Button.success("load-again", "↩️ Home")).queue();
        }

        switch (buttonEvent.getComponentId()) {
            case "ultrafastr" ->
                    buttonEvent.editMessage(Game.generateOpenEndedChallengeURLs(0, 0, true, client)).setActionRow(Button.primary("ultrafastr", "Rematch"), Button.success("load-again", "↩️ Home")).queue();
            case "bulletfastr" ->
                    buttonEvent.editMessage(Game.generateOpenEndedChallengeURLs(1, 0, true, client)).setActionRow(Button.primary("bulletfastr", "Rematch"), Button.success("load-again", "↩️ Home")).queue();
            case "blitzfastr" ->
                    buttonEvent.editMessage(Game.generateOpenEndedChallengeURLs(3, 2, true, client)).setActionRow(Button.primary("blitzfastr", "Rematch"), Button.success("load-again", "↩️ Home")).queue();
            case "rapidfastr" ->
                    buttonEvent.editMessage(Game.generateOpenEndedChallengeURLs(5, 5, true, client)).setActionRow(Button.primary("rapidfastr", "Rematch"), Button.success("load-again", "↩️ Home")).queue();

        }
    }

    /**
     * Handle the logic for the puzzle buttons for /puzzle
     */
    public void sendPuzzleButtons() {
        DailyCommand dailyCommand = new DailyCommand(client);
        switch (buttonEvent.getComponentId()) {
            case "puzzlecc" ->
                    buttonEvent.replyEmbeds(dailyCommand.defineCommandCard().build()).setEphemeral(true).queue();
            case "hint" -> buttonEvent.replyEmbeds(dailyCommand.getThemes().build()).setEphemeral(true).queue();
        }

    }

    /**
     * Handle the logic for the learn command for /learnchess
     */
    public void sendLearnCommand() {

        switch (buttonEvent.getComponentId()) {
            case "Bishop" ->
                    buttonEvent.editMessageEmbeds(getLearnChessCard("Bishop").build()).setActionRow(Button.primary("Rook", "⬅️"), Button.primary("Knight", "♞")).queue();
            case "Knight" ->
                    buttonEvent.editMessageEmbeds(getLearnChessCard("Knight").build()).setActionRow(Button.primary("Bishop", "⬅️"), Button.primary("Queen", "♛")).queue();
            case "Queen" ->
                    buttonEvent.editMessageEmbeds(getLearnChessCard("Queen").build()).setActionRow(Button.primary("Knight", "⬅️"), Button.primary("King", "♚")).queue();
            case "Rook" ->
                    buttonEvent.editMessageEmbeds(getLearnChessCard("Rook").build()).setActionRow(Button.primary("Bishop", "♝")).queue();
            case "King" ->
                    buttonEvent.editMessageEmbeds(getLearnChessCard("King").build()).setActionRow(Button.primary("Queen", "⬅️"), Button.primary("King-castle", "Castle ♚")).queue();
            case "King-castle" ->
                    buttonEvent.editMessageEmbeds(getLearnChessCard("King-castle").build()).setActionRow(Button.primary("king", "⬅️"), Button.primary("Pawn", "♟️")).queue();
            case "Pawn" ->
                    buttonEvent.editMessageEmbeds(getLearnChessCard("Pawn").build()).setActionRow(Button.primary("King-castle", "⬅️"), Button.primary("Pawn-en", "En-passant ♟️")).queue();
            case "Pawn-en" ->
                    buttonEvent.editMessageEmbeds(getLearnChessCard("Pawn-en").build()).setActionRow(Button.primary("Pawn-en", "⬅️"), Button.primary("Pawn-pro", "Promotion ♟️")).queue();
            case "Pawn-pro" ->
                    buttonEvent.editMessageEmbeds(getLearnChessCard("Pawn-pro").build()).setActionRow(Button.primary("Pawn-pro", "All set for chess!").asDisabled()).queue();

        }

    }

    /**
     * handle the logic for the challenge friend form
     */
    private void buildButtonInputForm() {
        TextInput ptext = TextInput.create("self-user", "Input Your Lichess Username", TextInputStyle.SHORT)
                .setPlaceholder("Input Your Lichess Username")
                .setMinLength(2)
                .setMaxLength(100)
                .setRequired(true)
                .build();

        TextInput targettext = TextInput.create("self-user" + "tar-user", "Enter Your Friend Lichess Username", TextInputStyle.SHORT)
                .setPlaceholder("Enter Your Friend Lichess Username")
                .setMinLength(2)
                .setMaxLength(100)
                .setRequired(true)
                .build();
        Modal pmodal = Modal.create("modal-self-user", "Challenge Friend ")
                .addComponents(ActionRow.of(ptext), ActionRow.of(targettext))
                .build();
        buttonEvent.replyModal(pmodal).queue();
    }

    /**
     * Send the self user input form
     */
    public void sendSelfUserInputForm() {
        buildButtonInputForm();
    }

    /**
     * Get the learn chess card EmbedBuilder for /learnchess
     *
     * @param searchKey the button Search key
     * @return the EmbedBuilder
     */
    public static EmbedBuilder getLearnChessCard(String searchKey) {
        for (int i = 0; i < LEARN_CHESS.length; i++) {
            for (int j = 0; j < LEARN_CHESS[i].length; i++) {
                if (LEARN_CHESS[i][0].equalsIgnoreCase(searchKey)) {
                    return new EmbedBuilder().setDescription(LEARN_CHESS[i][1]).setImage(LEARN_CHESS[i][2]).setColor(Color.BLUE).setThumbnail(logo);
                }
            }
        }

        return null;
    }

    /**
     * Get the play command info for /play
     *
     * @return the EmbedBuilder
     */
    public EmbedBuilder getPlayCommandInfo() {
        EmbedBuilder help = new EmbedBuilder();
        help.setThumbnail("https://static-00.iconduck.com/assets.00/lichess-icon-512x512-q0oh5bwk.png");
        help.setTitle("Guide for /play");
        help.setDescription("""
                /play allows you to play LIVE chess with friends and BOTs!, to set up a **Casual game (friendly)/ Rated (gain/lose rating)**  all users need to do is click on **casual/rated** button
                After you will be prompted to select **Time control**, this option is timecontrol (how long game lasts).\s
                **Start the game**: One you have selected mode and time, Bot sends Lichess live URL, where you and your friend can click same time to start a **LIVE Chess game**.
                
                 **Login/Register**\s
                 To play rated make sure to Login/Register on Lichess.org to get chess rating, otherwise just play casual games!
                
                 **Play BOTS**\s
                
                 To play BOTS click on **Play BOTS**, to play live computer click on **Stockfish** to play BOTS on Lichess click on other options!
                
                 **Challenge Friend**\s
                 play your friend by entering your and your friend's Lichess user, ready for challenge? The time control is randomly generated for fun games!
                 **Need more help?**\s
                 Join our Support server and Developer will help you!""");
        return help;
    }


}
