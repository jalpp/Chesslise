package discord.handlermodules;

import abstraction.HandleContext;
import discord.helpermodules.SelectHelperModule;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;

public class SelectContextModule implements HandleContext {

    private final StringSelectInteractionEvent stringSelectEvent;


    public SelectContextModule(StringSelectInteractionEvent stringSelectEvent) {
        this.stringSelectEvent = stringSelectEvent;
    }

    @Override
    public void handleLogic() {
        SelectHelperModule selectHelperModule = new SelectHelperModule(stringSelectEvent);
        selectHelperModule.trigger(stringSelectEvent.getComponentId());
    }
}
