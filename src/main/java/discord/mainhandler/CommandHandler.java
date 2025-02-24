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

/**
 * CommandHandler class to handle the command
 */
public class CommandHandler extends ListenerAdapter {

    private static final Client client = Client.basic(conf -> conf.retries(0));
    private final AntiSpam spam = new AntiSpam(300000, 1);
    private final SlashContextModule slashContext = new SlashContextModule();
    private final ModalContextModule modalContext = new ModalContextModule();
    private final ButtonContextModule buttonContext = new ButtonContextModule();

    private final AutoCompleteContextModule autoContext = new AutoCompleteContextModule();

    public CommandHandler() {

    }


    /**
     * Handle the slash command interaction
     *
     * @param event
     */
    @SneakyThrows
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        slashContext.handleLogic(event, client, spam);
    }

    /**
     * Handle the modal interaction
     *
     * @param event
     */
    @Override
    public void onModalInteraction(@NotNull ModalInteractionEvent event) {

        modalContext.handleLogic(event, client);

    }

    /**
     * Handle the button interaction
     *
     * @param event
     */
    @Override
    public void onButtonInteraction(@NotNull ButtonInteractionEvent event) {

        buttonContext.handleLogic(event, client);

    }

    /**
     * Handle the autocomplete interaction
     *
     * @param event
     */
    @Override
    public void onCommandAutoCompleteInteraction(@NotNull CommandAutoCompleteInteractionEvent event) {

        autoContext.handleLogic(event, client);

    }

    /**
     * Handle the ready event
     *
     * @param event
     */
    @Override
    public void onReady(ReadyEvent event) {
        JDA jda = event.getJDA();
        int guildCount = jda.getGuilds().size();

        jda.getPresence().setActivity(Activity.watching("V17 Servers: " + guildCount + " 40k puzzles added!"));
    }


}




