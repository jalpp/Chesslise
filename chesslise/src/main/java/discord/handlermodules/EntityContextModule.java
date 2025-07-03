package discord.handlermodules;

import abstraction.HandleContext;
import discord.helpermodules.EntityHelperModule;
import net.dv8tion.jda.api.events.interaction.component.EntitySelectInteractionEvent;

public class EntityContextModule implements HandleContext {

    private final EntitySelectInteractionEvent entitySelectInteractionEvent;

    public EntityContextModule(EntitySelectInteractionEvent entitySelectInteractionEvent) {
        this.entitySelectInteractionEvent = entitySelectInteractionEvent;
    }


    @Override
    public void handleLogic() {
        EntityHelperModule entityHelperModule = new EntityHelperModule(entitySelectInteractionEvent);
        entityHelperModule.trigger(entitySelectInteractionEvent.getComponentId());
    }
}
