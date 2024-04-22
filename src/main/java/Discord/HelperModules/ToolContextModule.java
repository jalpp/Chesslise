package Discord.HelperModules;

import Chesscom.DailyCommandCC;
import Chesscom.puzzle;
import Discord.MainHandler.AntiSpam;
import Fide.FideClient;
import Lichess.*;
import chariot.Client;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.MessageContextInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import java.awt.*;
import java.util.HashMap;

public class ToolContextModule {

    private static final HashMap<String, PuzzleSolverContextModule> solverMapper = new HashMap<>();



    public ToolContextModule() {

    }


    public void sendBroadcastCommand(SlashCommandInteractionEvent slashEvent, Client client, MessageContextInteractionEvent context, boolean isSlash) {


        if (isSlash) {
            BroadcastLichess broadcast = new BroadcastLichess(client);
            slashEvent.replyEmbeds(broadcast.getBroadData().build()).queue();

        } else {
            BroadcastLichess broadcast = new BroadcastLichess(client);
            context.replyEmbeds(broadcast.getBroadData().build()).queue();
        }
    }


    public void sendBroadcastMasterCommand(SlashCommandInteractionEvent slashEvent, Client client, MessageContextInteractionEvent context, boolean isSlash) {
        if (isSlash) {
            WatchMaster watchMaster = new WatchMaster(client);
            slashEvent.reply(watchMaster.getMasterGames()).addActionRow(Button.link("https://lichess.org" + watchMaster.getGameId()[1], "Analyze")).queue();
        } else {
            WatchMaster watchMaster = new WatchMaster(client);
            context.reply(watchMaster.getMasterGames()).addActionRow(net.dv8tion.jda.api.interactions.components.buttons.Button.link("https://lichess.org" + watchMaster.getGameId()[1], "Analyze")).queue();
        }

    }

    public void sendSlashLichesspuzzleCommand(SlashCommandInteractionEvent slashEvent, Client client, MessageContextInteractionEvent context, boolean isSlash) {
        if (isSlash) {
            DailyCommand dailyCommand = new DailyCommand(client);
            slashEvent.replyEmbeds(dailyCommand.defineCommandCard().build()).addActionRow(Button.primary("hint", "hint"), Button.success("fishaz-li", "Stockfish Analysis"), Button.link(dailyCommand.defineAnalysisBoard(dailyCommand.defineUtil(), dailyCommand.definePuzzleFen()), "Analysis Board")).queue();
        } else {
            DailyCommand dailyCommand = new DailyCommand(client);
            context.replyEmbeds(dailyCommand.defineCommandCard().build()).addActionRow(Button.primary("hint", "hint"), Button.success("fishaz-li", "Stockfish Analysis"), Button.link(dailyCommand.defineAnalysisBoard(dailyCommand.defineUtil(), dailyCommand.definePuzzleFen()), "Analysis Board")).queue();
        }
    }

    public void sendDailyPuzzleChessComCommand(SlashCommandInteractionEvent slashEvent, MessageContextInteractionEvent context, boolean isSlash) {
        if (isSlash) {
            DailyCommandCC daily = new DailyCommandCC();
            slashEvent.replyEmbeds(daily.defineCommandCard().build()).addActionRow(Button.success("fishaz-cc", "Stockfish Analysis"), Button.link(daily.defineAnalysisBoard(daily.defineUtil(), daily.definePuzzleFen()), "Analysis Board")).queue();
        } else {
            DailyCommandCC daily = new DailyCommandCC();
            context.deferReply(false).queue();
            context.replyEmbeds(daily.defineCommandCard().build()).addActionRow(Button.success("fishaz-cc", "Stockfish Analysis"), Button.link(daily.defineAnalysisBoard(daily.defineUtil(), daily.definePuzzleFen()), "Analysis Board")).queue();
        }
    }

    public void sendRandomPuzzleChessComCommand(SlashCommandInteractionEvent slashEvent, AntiSpam spam) {
        if (spam.checkSpam(slashEvent)) {
            slashEvent.reply("Only 1 Chesscom puzzle request within 5 mins! Please take your time to solve the Chesscom puzzle!").setEphemeral(true).queue();
        } else {
            try {
                puzzle puzzle = new puzzle();
                slashEvent.replyEmbeds(puzzle.defineCommandCard().build()).addActionRow(Button.success("fishaz-ccr", "Stockfish Analysis"),Button.link(puzzle.defineAnalysisBoard(puzzle.defineUtil(), puzzle.definePuzzleFen()), "Analysis Board")).queue();
            } catch (Exception e) {
                slashEvent.getChannel().sendMessage("An error occurred.. Please contact Dev, or wait for few mins to rerun the command").queue();
            }

        }
    }

    public void sendPuzzleMenuCommand(SlashCommandInteractionEvent slashEvent, Client client, MessageContextInteractionEvent context, AntiSpam spam) {
        switch (slashEvent.getOptionsByName("pick-puzzle").get(0).getAsString()) {
            case "lip" -> {
                switch (slashEvent.getOptionsByName("pick-mode").get(0).getAsString()){
                    case "post" ->  sendSlashLichesspuzzleCommand(slashEvent, client, context, true);
                    case "live" -> sendLiveLichesspuzzleCommand(slashEvent, client);
                }
            }

            case "cpp" -> {
                switch (slashEvent.getOptionsByName("pick-mode").get(0).getAsString()){
                    case "post" ->  sendDailyPuzzleChessComCommand(slashEvent, context, true);
                    case "live" -> sendLiveChessComDailyCommand(slashEvent);
                }
            }

            case "random" -> {
                switch (slashEvent.getOptionsByName("pick-mode").get(0).getAsString()){
                    case "post" ->  sendRandomPuzzleChessComCommand(slashEvent, spam);
                    case "live" -> sendLiveChessComRandomCommand(slashEvent);
                }
            }
        }
    }


    public void sendLiveLichesspuzzleCommand(SlashCommandInteractionEvent slashEvent, Client client){
       sendLivePuzzleBuilder(slashEvent, new DailyCommand(client).definePuzzleFen());
    }


    public void sendLiveChessComDailyCommand(SlashCommandInteractionEvent slashEvent){
        sendLivePuzzleBuilder(slashEvent, new DailyCommandCC().definePuzzleFen());
    }

    public void sendLiveChessComRandomCommand(SlashCommandInteractionEvent slashEvent){
        sendLivePuzzleBuilder(slashEvent, new puzzle().definePuzzleFen());
    }

    public void sendLivePuzzleBuilder(SlashCommandInteractionEvent slashEvent, String fen){
        PuzzleSolverContextModule solver = new PuzzleSolverContextModule(
                slashEvent.getUser().getId(),
                fen
        );

        solverMapper.put(slashEvent.getUser().getId(), solver);
        solver.getPuzzleSolverCard(slashEvent);
    }

    public void sendPuzzleSolverTrigger(SlashCommandInteractionEvent slashEvent){
        try {
            PuzzleSolverContextModule solver = solverMapper.get(slashEvent.getUser().getId());
            solver.getSolverCard(slashEvent);
        }catch (Exception e){
            slashEvent.reply("You have not created a puzzle challenge! please use /puzzle!").queue();
        }

    }


    public void sendPlayChallengeCommand(SlashCommandInteractionEvent slashEvent, MessageContextInteractionEvent context, boolean isSlash) {
        if (isSlash) {
            slashEvent.reply("## Please Pick Your Lichess Game's Mode ⚔️ " + "\n\n").addActionRow(
                    Button.success("casmode", "\uD83D\uDC4C Casual"), Button.danger("ratedmode", "\uD83E\uDD3A Rated"), Button.success("friend", "\uD83D\uDDE1\uFE0F Play Friend")).addActionRow(Button.link("https://discord.gg/uncmhknmYg", "\uD83D\uDC4B Join our server!"), Button.link("https://lichess.org/login", "\uD83D\uDD12 Login/Register"), Button.secondary("playhelp", "❓ Help")).queue();
        } else {
            context.reply("**⚔️ Please Pick Your Lichess.Game's Mode **" + "\n\n").addActionRow(
                    net.dv8tion.jda.api.interactions.components.buttons.Button.success("casmode", "Casual"), net.dv8tion.jda.api.interactions.components.buttons.Button.danger("ratedmode", "Rated"), net.dv8tion.jda.api.interactions.components.buttons.Button.primary("enginemode", "Play BOT"), net.dv8tion.jda.api.interactions.components.buttons.Button.link("https://lichess.org/login", "Login/Register"), net.dv8tion.jda.api.interactions.components.buttons.Button.secondary("playhelp", "❓ Help")).queue();
        }
    }


    public void sendStreamerCommand(SlashCommandInteractionEvent slashEvent, Client client) {
        LiveStreamers liveStreamers = new LiveStreamers(client);
        slashEvent.replyEmbeds(liveStreamers.getTv().build()).queue();
    }

    public void sendLichessArenaURLCommand(SlashCommandInteractionEvent slashEvent, Client client) {
        String arenaLink = slashEvent.getOption("arenaid").getAsString().trim();
        UserArena userArena = new UserArena(client, arenaLink);
        slashEvent.replyEmbeds(userArena.getUserArena().build()).queue();
    }

    public void sendTop10FideEmbed(SlashCommandInteractionEvent slashEvent){
        EmbedBuilder builder = new EmbedBuilder();
        builder.setTitle("FIDE Top10 Players");
        builder.setThumbnail("https://upload.wikimedia.org/wikipedia/en/thumb/5/5b/Fidelogo.svg/1200px-Fidelogo.svg.png");
        builder.setDescription(FideClient.getTopNInString("standard", 10));
        builder.setColor(Color.WHITE);
        slashEvent.replyEmbeds(builder.build()).queue();
    }




}

