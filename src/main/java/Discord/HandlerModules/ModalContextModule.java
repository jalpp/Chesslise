package Discord.HandlerModules;

import Abstraction.Context.ContextHandler;
import Discord.HelperModules.ModalHelperContextModule;
import Discord.MainHandler.AntiSpam;
import chariot.Client;
import com.github.bhlangonijr.chesslib.Board;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.MessageContextInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;

public class ModalContextModule implements ContextHandler {

    @Override
    public void handleLogic(MessageContextInteractionEvent context, SlashCommandInteractionEvent slashEvent, ButtonInteractionEvent buttonEvent, ModalInteractionEvent eventModal, Client client, Board board, Board blackboard, AntiSpam spam, AntiSpam dailyspam, AntiSpam watchlimit) {

        String name = eventModal.getModalId();
        ModalHelperContextModule modalHelper = new ModalHelperContextModule();

        switch (name) {
            case "modalwatch" -> modalHelper.handleGameInputOnFormSubmit(eventModal, client);
            case "modalpro" -> modalHelper.handleLichessProfileOnFormSubmit(eventModal, client);
            case "modalproc" -> modalHelper.handleChessComProfileOnFormSubmit(eventModal);
        }
    }

}