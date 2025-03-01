package discord.handlermodules;


import discord.helpermodules.AutoCompleteHelperModule;
import chariot.Client;
import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent;



public class AutoCompleteContextModule {


   
    public void handleLogic(CommandAutoCompleteInteractionEvent autoEvent, Client client) {

        AutoCompleteHelperModule autoTool = new AutoCompleteHelperModule(autoEvent, client);

        if (autoEvent.getName().equalsIgnoreCase("profile")) {
            autoTool.onLichessProfileAutoComplete();
        }
    }
}
