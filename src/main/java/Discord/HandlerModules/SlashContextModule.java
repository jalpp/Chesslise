package Discord.HandlerModules;

import Abstraction.Context.ContextHandler;
import Discord.HelperModules.BotContextModule;
import Discord.HelperModules.ModalHelperContextModule;
import Discord.HelperModules.ToolContextModule;
import Discord.MainHandler.AntiSpam;
import Discord.MainHandler.CommandInfo;
import chariot.Client;
import com.github.bhlangonijr.chesslib.Board;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.MessageContextInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;


public class SlashContextModule implements ContextHandler {


    @Override
    public void handleLogic(MessageContextInteractionEvent context, SlashCommandInteractionEvent slashEvent, ButtonInteractionEvent buttonEvent, ModalInteractionEvent eventModal, Client client, Board board, Board blackboard, AntiSpam spam, AntiSpam dailyspam, AntiSpam watchlimit) {
        String name = slashEvent.getName();
        BotContextModule botCommands = new BotContextModule();
        CommandInfo info_sender = new CommandInfo();
        ToolContextModule tools = new ToolContextModule();
        ModalHelperContextModule form_tools = new ModalHelperContextModule();
        switch (name) {

            case "resetboard" -> botCommands.resetBoardCommand(slashEvent, board, blackboard);

            case "move" -> botCommands.whiteSideMoveCommand(slashEvent, board);

            case "community" -> info_sender.sendCommunityCommand(slashEvent);

            case "analyze" -> botCommands.runAnalyzeCommand(slashEvent);

            case "broadcast" -> tools.sendBroadcastCommand(slashEvent, client, context, true);

            case "service" -> info_sender.sendServiceCommand(slashEvent);

            case "watchmaster" -> tools.sendBroadcastMasterCommand(slashEvent, client, context, true);

            case "puzzle" -> tools.sendPuzzleMenuCommand(slashEvent, client, context, spam);

            case "help" -> info_sender.sendInfoCommand(slashEvent);

            case "learnchess" -> info_sender.sendLearnCommand(slashEvent);

            case "play" -> tools.sendPlayChallengeCommand(slashEvent, context, true);

            case "profile" -> form_tools.sendLichessUserProfileInputForm(slashEvent);

            case "profilecc" -> form_tools.sendChessComUserProfileInputForm(slashEvent);

            case "streamers" -> tools.sendStreamerCommand(slashEvent, client);

            case "watch" -> form_tools.sendLichessWatchGameCommand(slashEvent, watchlimit);

            case "arena" -> tools.sendLichessArenaURLCommand(slashEvent, client);

            case "invite" -> info_sender.sendInviteMeCommand(slashEvent);

        }
    }

}

