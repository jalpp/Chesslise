package discord.helpermodules;

import chariot.Client;
import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.Command;

import java.util.List;

public class AutoCompleteHelperModule {

    private final CommandAutoCompleteInteractionEvent event;
    private final Client client;

    public AutoCompleteHelperModule(CommandAutoCompleteInteractionEvent event, Client client) {
        this.event = event;
        this.client = client;
    }

    public void onLichessProfileAutoComplete() {

        if (event.getName().equalsIgnoreCase("profile")
                && event.getFocusedOption().getName().equalsIgnoreCase("search-user")) {
            List<Command.Choice> options = client.users().autocompleteNames(event.getFocusedOption().getValue())
                    .stream()
                    .filter(username -> username.startsWith(event.getFocusedOption().getValue()))
                    .map(username -> new Command.Choice(username, username))
                    .toList();
            event.replyChoices(options).queue();
        }

    }

}
