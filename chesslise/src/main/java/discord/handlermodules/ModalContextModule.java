package discord.handlermodules;

import abstraction.HandleContext;
import discord.helpermodules.ModalHelperModule;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;


public class ModalContextModule implements HandleContext {

    private final ModalInteractionEvent eventModal;

    public ModalContextModule(ModalInteractionEvent eventModal) {
        this.eventModal = eventModal;
    }

    @Override
    public void handleLogic() {
        String name = eventModal.getModalId();
        ModalHelperModule modalTool = new ModalHelperModule(eventModal);
        modalTool.trigger(name);
    }

}