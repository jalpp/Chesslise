package discord.handlermodules;

import abstraction.Context.ContextHandler;
import discord.helpermodules.*;
import discord.mainhandler.AntiSpam;
import discord.mainhandler.CommandInfo;
import chariot.Client;
import com.github.bhlangonijr.chesslib.Board;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.MessageContextInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;


public class SlashContextModule implements ContextHandler {



    @Override
    public void handleLogic(MessageContextInteractionEvent context, SlashCommandInteractionEvent slashEvent, ButtonInteractionEvent buttonEvent, ModalInteractionEvent eventModal, CommandAutoCompleteInteractionEvent autoEvent, Client client, Board board, Board blackboard, AntiSpam spam, AntiSpam dailyspam, AntiSpam watchlimit) {
        String name = slashEvent.getName();
        BotContextModule botCommands = new BotContextModule();
        CommandInfo info_sender = new CommandInfo();
        ToolContextModule tools = new ToolContextModule();
        ModalHelperContextModule form_tools = new ModalHelperContextModule();
        AutoCompleteHelperModule autoHelper = new AutoCompleteHelperModule();
        NetworkContextModule network = new NetworkContextModule(slashEvent);

        switch (name) {

            case "resetboard" -> botCommands.resetBoardCommand(slashEvent, board, blackboard);

            case "move" -> botCommands.whiteSideMoveCommand(slashEvent, board);

            case "puzzle" -> tools.sendPuzzleMenuCommand(slashEvent, client, context, spam);

            case "help" -> info_sender.sendInfoCommand(slashEvent);

            case "learnchess" -> info_sender.sendLearnCommand(slashEvent);

            case "play" -> tools.sendPlayChallengeCommand(slashEvent, context, true);

            case "profile" -> autoHelper.onSelectLichessUserNameHandleRequest(slashEvent, client);

            case "profilecc" -> form_tools.sendChessComUserProfileInputForm(slashEvent);

            case "watch" -> form_tools.sendLichessWatchGameCommand(slashEvent, watchlimit);

            case "chessdb" -> tools.sendChessDBInfo(slashEvent);

            case "connect" -> network.sendConnect();

            case "disconnect" -> network.sendDisconnect();

            case "setpreference" -> network.sendSetPreference();

            case "mychallenges" -> network.sendMyChallenge();

            case "pairchallenge" -> network.sendChallengeGlobal();

            case "pairchallengenetwork" -> network.sendChallengeSelf();

            case "seekchallenge" -> network.sendSeekChallenge();

            case "cancelchallenge" -> network.sendCancelChallenge();

            case "completechallenge" -> network.sendCompleteChallenge();

            case "sendfriendrequest" -> network.sendSendFriendRequest();

            case "acceptfriendrequest" -> network.sendAcceptFriendRequest();

            case "cancelfriendrequest" -> network.sendcancelFriendRequest();

            case "findfriend" -> network.sendFriendFinderNetwork();

            case "removefriend" -> network.sendRemoveFriendRequest();

            case "blockfriend" -> network.sendBlockFriendRequest();

            case "viewfriends" -> network.sendViewFriendRequest();


        }
    }

}

