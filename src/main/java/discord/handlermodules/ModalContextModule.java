package discord.handlermodules;

import discord.helpermodules.ModalHelperModule;
import chariot.Client;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;


public class ModalContextModule {


    
    public void handleLogic( ModalInteractionEvent eventModal, Client client) {

        String name = eventModal.getModalId();
        ModalHelperModule modalTool = new ModalHelperModule(eventModal, client);

        switch (name) {
            case "modalwatch" -> modalTool.sendGameInputOnFormSubmit();
            case "modalproc" -> modalTool.sendChessComProfileOnFormSubmit();
            case "modal-self-user" -> modalTool.sendPlayFriendChallenge();
        }
    }

}