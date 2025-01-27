package discord.handlermodules;

import abstraction.Context.ContextHandler;
import discord.helpermodules.AutoCompleteHelperModule;
import discord.mainhandler.AntiSpam;
import chariot.Client;
import com.github.bhlangonijr.chesslib.Board;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.MessageContextInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;

/**
 * AutoCompleteContextModule class to handle the autocomplete context
 */
public class AutoCompleteContextModule implements ContextHandler {

    /**
     * Handle the logic for the autocomplete context
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

        AutoCompleteHelperModule autoTool = new AutoCompleteHelperModule(autoEvent, client, slashEvent);

        if (autoEvent.getName().equalsIgnoreCase("profile")) {
            autoTool.onLichessProfileAutoComplete();
        }
    }
}
