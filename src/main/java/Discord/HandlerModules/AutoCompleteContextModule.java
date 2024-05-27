package Discord.HandlerModules;

import Abstraction.Context.ContextHandler;
import Discord.HelperModules.AutoCompleteHelperModule;
import Discord.MainHandler.AntiSpam;
import chariot.Client;
import com.github.bhlangonijr.chesslib.Board;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.MessageContextInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;

public class AutoCompleteContextModule implements ContextHandler {


    @Override
    public void handleLogic(MessageContextInteractionEvent context, SlashCommandInteractionEvent slashEvent, ButtonInteractionEvent buttonEvent, ModalInteractionEvent eventModal, CommandAutoCompleteInteractionEvent autoEvent, Client client, Board board, Board blackboard, AntiSpam spam, AntiSpam dailyspam, AntiSpam watchlimit) {

        AutoCompleteHelperModule autoHelper = new AutoCompleteHelperModule();

        if(autoEvent.getName().equalsIgnoreCase("profile")){
            autoHelper.onLichessProfileAutoComplete(autoEvent, client);
        }


    }
}
