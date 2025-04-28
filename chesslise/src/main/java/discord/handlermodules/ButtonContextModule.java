package discord.handlermodules;

import abstraction.HandleContext;
import discord.helpermodules.ButtonHelperModule;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;

public class ButtonContextModule implements HandleContext {

    private final ButtonInteractionEvent buttonEvent;

    public ButtonContextModule(ButtonInteractionEvent buttonEvent) {
        this.buttonEvent = buttonEvent;
    }

    @Override
    public void handleLogic() {
        ButtonHelperModule buttonTool = new ButtonHelperModule(buttonEvent);
        buttonTool.trigger(buttonEvent.getComponentId());
    }


}