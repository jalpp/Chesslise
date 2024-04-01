package Discord.HelperModules;

import Abstraction.ChessUtil;
import Chesscom.DailyCommandCC;
import Chesscom.puzzle;
import Discord.MainHandler.AntiSpam;
import Engine.StockFish;
import Lichess.*;
import chariot.Client;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.MessageContextInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import java.awt.*;
import java.util.Objects;

public class ToolContextModule {




    public ToolContextModule(){

    }


    public void sendBroadcastCommand(SlashCommandInteractionEvent slashEvent, Client client, MessageContextInteractionEvent context, boolean isSlash){
        if(isSlash) {
            BroadcastLichess broadcast = new BroadcastLichess(client);
            slashEvent.deferReply(true).queue();
            slashEvent.getChannel().sendMessageEmbeds(broadcast.getBroadData().build()).queue();
        }else {
            BroadcastLichess broadcast = new BroadcastLichess(client);
            context.getChannel().sendMessageEmbeds(broadcast.getBroadData().build()).queue();
        }
    }


    public void sendBroadcastMasterCommand(SlashCommandInteractionEvent slashEvent, Client client, MessageContextInteractionEvent context, boolean isSlash){
        if(isSlash) {
            WatchMaster watchMaster = new WatchMaster(client);
            slashEvent.deferReply(true).queue();
            slashEvent.getChannel().sendMessage(watchMaster.getMasterGames()).addActionRow(Button.link("https://lichess.org" + watchMaster.getGameId()[1], "Analyze")).queue();
        }else {
            WatchMaster watchMaster = new WatchMaster(client);
            context.deferReply(true).queue();
            Objects.requireNonNull(context.getChannel()).sendMessage(watchMaster.getMasterGames()).addActionRow(net.dv8tion.jda.api.interactions.components.buttons.Button.link("https://lichess.org" + watchMaster.getGameId()[1], "Analyze")).queue();
        }

    }

    public void sendSlashLichesspuzzleCommand(SlashCommandInteractionEvent slashEvent, Client client, MessageContextInteractionEvent context, boolean isSlash){
        DailyCommand.getLichessDailyPuzzle(client, slashEvent, context, isSlash);
    }
    public void sendDailyPuzzleChessComCommand(SlashCommandInteractionEvent slashEvent, MessageContextInteractionEvent context, boolean isSlash){
        if(isSlash) {
            DailyCommandCC daily = new DailyCommandCC();
            slashEvent.deferReply(true).queue();
            slashEvent.getChannel().sendMessageEmbeds(new EmbedBuilder().setColor(Color.GREEN).setTitle("Chess.com Daily Puzzle").setThumbnail("https://static.wikia.nocookie.net/logopedia/images/4/4a/Chess.com_2019_%28App_Icon%29.png/revision/latest/scale-to-width-down/250?cb=20221006103032").setDescription(StockFish.getStockFishTextExplanation(15, daily.getFEN()) + "\n\n [Join our Server ♟\uFE0F](https://discord.gg/uncmhknmYg)").setFooter("use /analyze [fen] to further analyze/check your answer").setImage(daily.getPuzzle()).build()).addActionRow(Button.link("https://www.chess.com/home", daily.defineSideToMove(new ChessUtil(), daily.getFEN())).asDisabled()).queue();
        }else {
            DailyCommandCC daily = new DailyCommandCC();
            context.deferReply(true).queue();
            Objects.requireNonNull(context.getChannel()).sendMessageEmbeds(new EmbedBuilder().setColor(Color.magenta).setTitle("Chess.com Daily Puzzle").setDescription(StockFish.getStockFishTextExplanation(13, daily.getFEN()) + "\n\n " + daily.defineSideToMove(new ChessUtil(), daily.getFEN())).setImage(daily.getPuzzle()).setFooter("run /analyze [fen] to view the moves in action!").build()).queue();
        }
    }

    public void sendRandomPuzzleChessComCommand(SlashCommandInteractionEvent slashEvent, AntiSpam spam){
        if (spam.checkSpam(slashEvent)) {
            slashEvent.reply("Only 1 Chesscom puzzle request within 5 mins! Please take your time to solve the Chesscom puzzle!").setEphemeral(true).queue();
        } else {
            try {
                slashEvent.deferReply(true).queue();
                puzzle puzzle = new puzzle();
                String s = puzzle.getPuzzle();
                String fen = puzzle.getFEN();
                slashEvent.getChannel().sendMessageEmbeds(new EmbedBuilder().setColor(Color.green).setTitle("Chess.com Random Puzzle").setImage(s).setThumbnail("https://static.wikia.nocookie.net/logopedia/images/4/4a/Chess.com_2019_%28App_Icon%29.png/revision/latest/scale-to-width-down/250?cb=20221006103032").setDescription(StockFish.getStockFishTextExplanation(15, fen) + "\n\n [Join our Server ♟\uFE0F](https://discord.gg/uncmhknmYg)").setFooter("use /analyze [fen] to further analyze/check your answer").build()).addActionRow(Button.link("https://www.chess.com/home", puzzle.defineSideToMove(new ChessUtil(), fen)).asDisabled()).queue();
            } catch (Exception e) {
                slashEvent.getChannel().sendMessage("An error occurred.. Please contact Dev, or wait for few mins to rerun the command").queue();
            }

        }
    }

    public void sendPuzzleMenuCommand(SlashCommandInteractionEvent slashEvent, Client client, MessageContextInteractionEvent context, AntiSpam spam){
        switch (slashEvent.getOptionsByName("pick-puzzle").get(0).getAsString()){
            case "lip" -> sendSlashLichesspuzzleCommand(slashEvent,client,context, true);

            case "cpp" -> sendDailyPuzzleChessComCommand(slashEvent, context, true);

            case "random" -> sendRandomPuzzleChessComCommand(slashEvent, spam);
        }

    }

    public void sendPlayChallengeCommand(SlashCommandInteractionEvent slashEvent, MessageContextInteractionEvent context, boolean isSlash){
        if(isSlash) {
            slashEvent.reply("## Please Pick Your Lichess Game's Mode ⚔️ " + "\n\n").addActionRow(
                    Button.success("casmode", "Casual"), Button.danger("ratedmode", "Rated"), Button.link("https://discord.gg/uncmhknmYg", "Join our server!"), Button.link("https://lichess.org/login", "Login/Register"), Button.secondary("playhelp", "❓ Help")).queue();
        }else {
            context.reply("**⚔️ Please Pick Your Lichess.Game's Mode **" + "\n\n").addActionRow(
                    net.dv8tion.jda.api.interactions.components.buttons.Button.success("casmode", "Casual"), net.dv8tion.jda.api.interactions.components.buttons.Button.danger("ratedmode", "Rated"), net.dv8tion.jda.api.interactions.components.buttons.Button.primary("enginemode", "Play BOT"), net.dv8tion.jda.api.interactions.components.buttons.Button.link("https://lichess.org/login", "Login/Register"), net.dv8tion.jda.api.interactions.components.buttons.Button.secondary("playhelp", "❓ Help")).queue();
        }
    }


    public void sendStreamerCommand(SlashCommandInteractionEvent slashEvent, Client client){
        LiveStreamers liveStreamers = new LiveStreamers(client);
        slashEvent.replyEmbeds(liveStreamers.getTv().build()).queue();
    }

    public void sendLichessArenaURLCommand(SlashCommandInteractionEvent slashEvent, Client client){
        String arenaLink = slashEvent.getOption("arenaid").getAsString().trim();
        UserArena userArena = new UserArena(client, arenaLink);
        slashEvent.deferReply(true).queue();
        slashEvent.getChannel().sendMessageEmbeds(userArena.getUserArena().build()).queue();
    }


    
}