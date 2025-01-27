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

/**
 * SlashContextModule class to handle the slash context
 */
public class SlashContextModule implements ContextHandler {

    /**
     * Handle the logic for the slash context
     *
     * @param context     the message context
     * @param slashEvent  the slash command event
     * @param buttonEvent the button event
     * @param eventModal  the modal event
     * @param autoEvent   the autocomplete event
     * @param client      the client
     * @param board       the board
     * @param blackboard  the blackboard
     * @param spam        the spam
     * @param dailyspam   the daily spam
     * @param watchlimit  the watch limit
     */
    @Override
    public void handleLogic(MessageContextInteractionEvent context, SlashCommandInteractionEvent slashEvent, ButtonInteractionEvent buttonEvent, ModalInteractionEvent eventModal, CommandAutoCompleteInteractionEvent autoEvent, Client client, Board board, Board blackboard, AntiSpam spam, AntiSpam dailyspam, AntiSpam watchlimit) {
        String name = slashEvent.getName();
        EngineHelperModule engineTool = new EngineHelperModule(slashEvent, board, blackboard);
        CommandInfo infoTool = new CommandInfo(slashEvent);
        ChessSlashHelperModule chessTool = new ChessSlashHelperModule(slashEvent);
        AutoCompleteHelperModule autoTool = new AutoCompleteHelperModule(autoEvent, client, slashEvent);
        NetworkHelperModule networkTool = new NetworkHelperModule(slashEvent);
        DoubleContextHelperModule twoContextTool = new DoubleContextHelperModule(slashEvent, context, spam, client, true);

        switch (name) {

            case "resetboard" -> engineTool.sendresetBoardCommand();

            case "move" -> engineTool.sendwhiteSideMoveCommand();

            case "puzzle" -> twoContextTool.sendPuzzleMenuCommand();

            case "help" -> infoTool.sendInfoCommand();

            case "learnchess" -> infoTool.sendLearnCommand();

            case "play" -> twoContextTool.sendPlayChallengeCommand();

            case "profile" -> autoTool.sendSelectLichessUserNameHandleRequest();

            case "profilecc" -> chessTool.sendChessComUserProfileInputForm();

            case "watch" -> chessTool.sendLichessWatchGameCommand();

            case "chessdb" -> chessTool.sendChessDBInfo();

            case "connect" -> networkTool.sendConnect();

            case "disconnect" -> networkTool.sendDisconnect();

            case "setpreference" -> networkTool.sendSetPreference();

            case "mychallenges" -> networkTool.sendMyChallenge();

            case "pairchallenge" -> networkTool.sendChallengeGlobal();

            case "pairchallengenetwork" -> networkTool.sendChallengeSelf();

            case "seekchallenge" -> networkTool.sendSeekChallenge();

            case "cancelchallenge" -> networkTool.sendCancelChallenge();

            case "completechallenge" -> networkTool.sendCompleteChallenge();

            case "sendfriendrequest" -> networkTool.sendSendFriendRequest();

            case "acceptfriendrequest" -> networkTool.sendAcceptFriendRequest();

            case "cancelfriendrequest" -> networkTool.sendcancelFriendRequest();

            case "findfriend" -> networkTool.sendFriendFinderNetwork();

            case "removefriend" -> networkTool.sendRemoveFriendRequest();

            case "blockfriend" -> networkTool.sendBlockFriendRequest();

            case "viewfriends" -> networkTool.sendViewFriendRequest();


        }
    }

}

