package discord.handlermodules;


import discord.helpermodules.AutoCompleteHelperModule;
import discord.mainhandler.AntiSpam;
import chariot.Client;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.MessageContextInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;

/**
 * AutoCompleteContextModule class to handle the autocomplete context
 */
public class AutoCompleteContextModule {


    public void handleLogic(CommandAutoCompleteInteractionEvent autoEvent, Client client) {

        AutoCompleteHelperModule autoTool = new AutoCompleteHelperModule(autoEvent, client);

        if (autoEvent.getName().equalsIgnoreCase("profile")) {
            autoTool.onLichessProfileAutoComplete();
        }
    }
}
