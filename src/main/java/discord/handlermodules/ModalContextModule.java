package discord.handlermodules;

import abstraction.Context.ContextHandler;
import discord.helpermodules.ModalHelperContextModule;
import discord.mainhandler.AntiSpam;
import lichess.Game;
import chariot.Client;
import com.github.bhlangonijr.chesslib.Board;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.MessageContextInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;

public class ModalContextModule implements ContextHandler {

    @Override
    public void handleLogic(MessageContextInteractionEvent context, SlashCommandInteractionEvent slashEvent, ButtonInteractionEvent buttonEvent, ModalInteractionEvent eventModal, CommandAutoCompleteInteractionEvent autoEvent, Client client, Board board, Board blackboard, AntiSpam spam, AntiSpam dailyspam, AntiSpam watchlimit) {

        String name = eventModal.getModalId();
        ModalHelperContextModule modalHelper = new ModalHelperContextModule();

        switch (name) {
            case "modalwatch" -> modalHelper.handleGameInputOnFormSubmit(eventModal, client);
            case "modalproc" -> modalHelper.handleChessComProfileOnFormSubmit(eventModal);
            case "modal-self-user" -> modalHelper.handlePlayFriendChallenge(eventModal, client, new Game());
        }
    }

}