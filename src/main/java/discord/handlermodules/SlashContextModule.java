package discord.handlermodules;


import discord.helpermodules.*;
import discord.mainhandler.AntiSpam;
import discord.mainhandler.CommandInfo;
import chariot.Client;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;


/**
 * SlashContextModule class to handle the slash context
 */
public class SlashContextModule {



    public void handleLogic(SlashCommandInteractionEvent slashEvent, Client client, AntiSpam spam) {
        String name = slashEvent.getName();
        EngineHelperModule engineTool = new EngineHelperModule(slashEvent);
        CommandInfo infoTool = new CommandInfo(slashEvent);
        ChessSlashHelperModule chessTool = new ChessSlashHelperModule(slashEvent);
        NetworkHelperModule networkTool = new NetworkHelperModule(slashEvent);
        PuzzleContextHelperModule puzzleTool = new PuzzleContextHelperModule(slashEvent,spam,client);

        switch (name) {

            case "move" -> engineTool.sendwhiteSideMoveCommand();

            case "playengine" -> engineTool.sendPlayEngine();

            case "setengine" -> engineTool.sendSetEngineMode();

            case "puzzle" -> puzzleTool.sendPuzzleMenuCommand();

            case "help" -> infoTool.sendInfoCommand();

            case "learnchess" -> infoTool.sendLearnCommand();

            case "play" -> chessTool.sendPlayChallengeCommand();

            case "profile" -> chessTool.sendSelectLichessUserNameHandleRequest();

            case "profilecc" -> chessTool.sendChessComUserProfileInputForm();

            case "watch" -> chessTool.sendLichessWatchGameCommand();

            case "chessdb" -> chessTool.sendChessDBInfo();

            case "fen" -> chessTool.sendChessFEN();

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

