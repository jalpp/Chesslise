package discord.handlermodules;

import abstraction.Context.ContextHandler;
import discord.helpermodules.ModalHelperModule;
import discord.mainhandler.AntiSpam;
import lichess.Game;
import chariot.Client;
import com.github.bhlangonijr.chesslib.Board;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.MessageContextInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;

/**
 * ModalContextModule class to handle the modal context
 */
public class ModalContextModule implements ContextHandler {

    /**
     * Handle the logic for the modal context
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

        String name = eventModal.getModalId();
        ModalHelperModule modalTool = new ModalHelperModule(eventModal, client);

        switch (name) {
            case "modalwatch" -> modalTool.sendGameInputOnFormSubmit();
            case "modalproc" -> modalTool.sendChessComProfileOnFormSubmit();
            case "modal-self-user" -> modalTool.sendPlayFriendChallenge();
        }
    }

}