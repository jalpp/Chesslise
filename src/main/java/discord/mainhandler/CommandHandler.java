package discord.mainhandler;

import discord.handlermodules.*;
import chariot.Client;
import lombok.SneakyThrows;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;


public class CommandHandler extends ListenerAdapter {

    private static final Client client = Client.basic(conf -> conf.retries(0));
    private final SlashContextModule slashContext = new SlashContextModule();
    private final ModalContextModule modalContext = new ModalContextModule();
    private final ButtonContextModule buttonContext = new ButtonContextModule();

    private final AutoCompleteContextModule autoContext = new AutoCompleteContextModule();

    public CommandHandler() {

    }


    
    @SneakyThrows
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        slashContext.handleLogic(event, client);
    }

   
    @Override
    public void onModalInteraction(@NotNull ModalInteractionEvent event) {

        modalContext.handleLogic(event, client);

    }

    
    @Override
    public void onButtonInteraction(@NotNull ButtonInteractionEvent event) {

        buttonContext.handleLogic(event, client);

    }

    
    @Override
    public void onCommandAutoCompleteInteraction(@NotNull CommandAutoCompleteInteractionEvent event) {

        autoContext.handleLogic(event, client);

    }

    
    @Override
    public void onReady(ReadyEvent event) {
        JDA jda = event.getJDA();
        int guildCount = jda.getGuilds().size();

        jda.getPresence().setActivity(Activity.watching("V17 Servers: " + guildCount + " 40k puzzles added!"));
    }


}




