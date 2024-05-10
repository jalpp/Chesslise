package Discord.HelperModules;

import Lichess.UserProfile;
import chariot.Client;
import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.Command;
import java.util.List;

public class AutoCompleteHelperModule {

    public AutoCompleteHelperModule(){

    }


    public void onLichessProfileAutoComplete(CommandAutoCompleteInteractionEvent event, Client client){

        if(event.getName().equalsIgnoreCase("profile") && event.getFocusedOption().getName().equalsIgnoreCase("search-user")){
            List<Command.Choice> options =  client.users().autocompleteNames(event.getFocusedOption().getValue()).stream()
                    .filter(username -> username.startsWith(event.getFocusedOption().getValue()))
                    .map(username -> new Command.Choice(username, username))
                    .toList();
            event.replyChoices(options).queue();
        }

    }


    public void onSelectLichessUserNameHandleRequest(SlashCommandInteractionEvent event, Client client){

        String userID = event.getOptionsByName("search-user").get(0).getAsString();
        UserProfile userProfile = new UserProfile(client, userID);
        event.deferReply(false).queue();
        event.getHook().sendMessage(userProfile.getUserProfile()).setEphemeral(false).queue();


    }



    

}
