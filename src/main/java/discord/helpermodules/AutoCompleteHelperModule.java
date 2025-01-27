package discord.helpermodules;

import lichess.UserProfile;
import chariot.Client;
import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.Command;

import java.util.List;

/**
 * AutoCompleteHelperModule class to handle the autocomplete
 */
public class AutoCompleteHelperModule {

    private final CommandAutoCompleteInteractionEvent event;
    private final Client client;
    private final SlashCommandInteractionEvent slashEvent;

    public AutoCompleteHelperModule(CommandAutoCompleteInteractionEvent event, Client client, SlashCommandInteractionEvent slashEvent) {
        this.event = event;
        this.client = client;
        this.slashEvent = slashEvent;
    }

    /**
     * Handle the lichess profile autocomplete
     */
    public void onLichessProfileAutoComplete() {

        if (event.getName().equalsIgnoreCase("profile") && event.getFocusedOption().getName().equalsIgnoreCase("search-user")) {
            List<Command.Choice> options = client.users().autocompleteNames(event.getFocusedOption().getValue()).stream()
                    .filter(username -> username.startsWith(event.getFocusedOption().getValue()))
                    .map(username -> new Command.Choice(username, username))
                    .toList();
            event.replyChoices(options).queue();
        }

    }

    /**
     * Send the select lichess username handle request
     */
    public void sendSelectLichessUserNameHandleRequest() {
        String userID = event.getOptionsByName("search-user").get(0).getAsString();
        UserProfile userProfile = new UserProfile(client, userID);
        slashEvent.deferReply(false).queue();
        slashEvent.getHook().sendMessage(userProfile.getUserProfile()).setEphemeral(false).queue();

    }


}
