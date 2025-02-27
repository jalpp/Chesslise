package discord.handlermodules;


import discord.helpermodules.AutoCompleteHelperModule;
import chariot.Client;
import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent;


/**
 * AutoCompleteContextModule class to handle the autocomplete context
 */
public class AutoCompleteContextModule {


    /**
     * handles the auto complete event logic
     * @param autoEvent the auto complete event
     * @param client the chariot client
     */
    public void handleLogic(CommandAutoCompleteInteractionEvent autoEvent, Client client) {

        AutoCompleteHelperModule autoTool = new AutoCompleteHelperModule(autoEvent, client);

        if (autoEvent.getName().equalsIgnoreCase("profile")) {
            autoTool.onLichessProfileAutoComplete();
        }
    }
}
