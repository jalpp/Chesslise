package discord.helpermodules;

import abstraction.CommandTrigger;
import ladders.LichessLaddersParser;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.component.EntitySelectInteractionEvent;


import java.io.IOException;
import java.util.List;

public class EntityHelperModule implements CommandTrigger {

    private final EntitySelectInteractionEvent event;

    public EntityHelperModule(EntitySelectInteractionEvent event) {
        this.event = event;
    }


    public void sendViewPlayerLadder(){
        List<User> users = event.getMentions().getUsers();
        event.deferReply(true).queue();
        try {
            event.getHook().sendMessage(LichessLaddersParser.getPlayerInfo(users.getFirst().getId())).queue();
        } catch (IOException e) {
            event.getHook().sendMessage("error! Something went wrong" + e.getMessage()).queue();
        }
    }


    @Override
    public void trigger(String commandName) {
        if(commandName.equalsIgnoreCase("ladder-player-menu")){
            sendViewPlayerLadder();
        }
    }
}
