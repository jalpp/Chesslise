package discord.handlermodules;

import discord.helpermodules.ModalHelperModule;
import chariot.Client;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;


/**
 * ModalContextModule class to handle the modal context
 */
public class ModalContextModule {


    /**
     * handles the modal logic
     * @param eventModal the modal event
     * @param client the chariot client
     */
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