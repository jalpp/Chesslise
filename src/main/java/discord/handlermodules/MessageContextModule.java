package discord.handlermodules;


import abstraction.Context.ContextHandler;
import discord.helpermodules.DoubleContextHelperModule;
import discord.mainhandler.AntiSpam;
import chariot.Client;
import com.github.bhlangonijr.chesslib.Board;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.MessageContextInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;

/**
 * MessageContextModule class to handle the message context
 */
public class MessageContextModule implements ContextHandler {


    public MessageContextModule() {

    }

    /**
     * Handle the logic for the message context
     *
     * @param context     the message context
     * @param slashEvent  the slash command event
     * @param buttonEvent the button event
     * @param eventModal  the modal event
     * @param autoEvent   the autocomplete event
     * @param client      the client
     * @param board       the board
     * @param blackboard  the blackboard
     * @param spam        the spam
     * @param dailyspam   the daily spam
     * @param watchlimit  the watch limit
     */
    @Override
    public void handleLogic(MessageContextInteractionEvent context, SlashCommandInteractionEvent slashEvent, ButtonInteractionEvent buttonEvent, ModalInteractionEvent eventModal, CommandAutoCompleteInteractionEvent autoEvent, Client client, Board board, Board blackboard, AntiSpam spam, AntiSpam dailyspam, AntiSpam watchlimit) {

        DoubleContextHelperModule twoContextTool = new DoubleContextHelperModule(slashEvent, context, spam, client, false);

        switch (context.getName()) {
            case "Lichess Daily Puzzle" -> twoContextTool.sendSlashLichesspuzzleCommand();

            case "Play Chess" -> twoContextTool.sendPlayChallengeCommand();

            case "Chess.com Daily Puzzle" -> twoContextTool.sendDailyPuzzleChessComCommand();
        }
    }
}
