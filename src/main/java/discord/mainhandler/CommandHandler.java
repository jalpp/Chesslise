package discord.mainhandler;

import discord.handlermodules.*;
import chariot.Client;
import com.github.bhlangonijr.chesslib.Board;
import lombok.SneakyThrows;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.MessageContextInteractionEvent;
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
    private final AntiSpam dailySpam = new AntiSpam(86400000, 2);
    private final AntiSpam watchLimit = new AntiSpam(86400000, 24);
    private final Board board = new Board();
    private final Board blackboard = new Board();
    private final MessageContextModule msgContext = new MessageContextModule();
    private final SlashContextModule slashContext = new SlashContextModule();
    private final ModalContextModule modalContext = new ModalContextModule();
    private final ButtonContextModule buttonContext = new ButtonContextModule();

    private final AutoCompleteContextModule autoContext = new AutoCompleteContextModule();

    public CommandHandler() {

    }

    /**
     * Handle the message context interaction
     *
     * @param event
     */
    @Override
    public void onMessageContextInteraction(@NotNull MessageContextInteractionEvent event) {
        msgContext.handleLogic(event, null, null, null, null, client, null, null, null, null, null);
    }

    /**
     * Handle the slash command interaction
     *
     * @param event
     */
    @SneakyThrows
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        slashContext.handleLogic(null, event, null, null, null, client, board, blackboard, spam, dailySpam, watchLimit);
    }

    /**
     * Handle the modal interaction
     *
     * @param event
     */
    @Override
    public void onModalInteraction(@NotNull ModalInteractionEvent event) {

        modalContext.handleLogic(null, null, null, event, null, client, null, null, null, null, null);

    }

    /**
     * Handle the button interaction
     *
     * @param event
     */
    @Override
    public void onButtonInteraction(@NotNull ButtonInteractionEvent event) {

        buttonContext.handleLogic(null, null, event, null, null, client, board, blackboard, null, null, null);

    }

    /**
     * Handle the autocomplete interaction
     *
     * @param event
     */
    @Override
    public void onCommandAutoCompleteInteraction(@NotNull CommandAutoCompleteInteractionEvent event) {

        autoContext.handleLogic(null, null, null, null, event, client, null, null, null, null, null);

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

        jda.getPresence().setActivity(Activity.watching("V16 Servers: " + guildCount + " Join CSSN!"));
    }


}




