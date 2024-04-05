import Discord.HelperModules.ToolContextModule;
import Discord.MainHandler.AntiSpam;
import chariot.Client;
import com.github.bhlangonijr.chesslib.Board;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.MessageContextInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;


public class MessageContextModule implements ContextHandler {


    public MessageContextModule() {

    }

    @Override
    public void handleLogic(MessageContextInteractionEvent context, SlashCommandInteractionEvent slashEvent, ButtonInteractionEvent buttonEvent, ModalInteractionEvent eventModal, Client client, Board board, Board blackboard, AntiSpam spam, AntiSpam dailyspam, AntiSpam watchlimit) {

        ToolContextModule tools = new ToolContextModule();
        boolean isSlash = false;
        switch (context.getName()) {
            case "Lichess Daily Puzzle" -> tools.sendSlashLichesspuzzleCommand(slashEvent, client, context, isSlash);

            case "Play Chess" -> tools.sendPlayChallengeCommand(slashEvent, context, isSlash);

            case "View Lichess Broadcasts" -> tools.sendBroadcastCommand(slashEvent, client, context, isSlash);

            case "Watch GMs" -> tools.sendBroadcastMasterCommand(slashEvent, client, context, isSlash);

            case "Chess.com Daily Puzzle" -> tools.sendDailyPuzzleChessComCommand(slashEvent, context, isSlash);
        }
    }
}

