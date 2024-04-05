package Discord.MainHandler;

import Discord.HandlerModules.ButtonContextModule;
import Discord.HandlerModules.MessageContextModule;
import Discord.HandlerModules.ModalContextModule;
import Discord.HandlerModules.SlashContextModule;
import chariot.Client;
import com.github.bhlangonijr.chesslib.Board;
import lombok.SneakyThrows;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.MessageContextInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

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

    public CommandHandler() {

    }

    @Override
    public void onMessageContextInteraction(@NotNull MessageContextInteractionEvent event) {
        msgContext.handleLogic(event, null, null, null, client, null, null, null, null, null);
    }


    @SneakyThrows
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        slashContext.handleLogic(null, event, null, null, client, board, blackboard, spam, dailySpam, watchLimit);
    }

    @Override
    public void onModalInteraction(@NotNull ModalInteractionEvent event) {

        modalContext.handleLogic(null, null, null, event, client, null, null, null, null, null);

    }


    @Override
    public void onButtonInteraction(@NotNull ButtonInteractionEvent event) {

        buttonContext.handleLogic(null, null, event, null, client, board, blackboard, null, null, null);

    }


}








}
