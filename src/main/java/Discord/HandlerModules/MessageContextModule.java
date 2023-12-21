package Discord.HandlerModules;

import Abstraction.ChessUtil;
import Abstraction.Context.ContextHandler;
import Chesscom.DailyCommandCC;
import Discord.MainHandler.AntiSpam;
import Engine.StockFish;
import Lichess.BroadcastLichess;
import Lichess.DailyCommand;
import Lichess.WatchMaster;
import chariot.Client;
import com.github.bhlangonijr.chesslib.Board;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.MessageContextInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import java.awt.*;
import java.util.Objects;

public class MessageContextModule implements ContextHandler {



    public MessageContextModule(){

    }


    @Override
    public void handleLogic(MessageContextInteractionEvent context, SlashCommandInteractionEvent slashEvent, ButtonInteractionEvent buttonEvent, ModalInteractionEvent eventModal, Client client, Board board, Board blackboard, AntiSpam spam, AntiSpam dailyspam, AntiSpam watchlimit) {
        switch (context.getName()) {
            case "Lichess Daily Puzzle" -> {
                DailyCommand.getLichessDailyPuzzle(client, slashEvent, context, false);

            }
            case "Play Chess" -> {
                context.reply("**⚔️ Please Pick Your Lichess.Game's Mode **" + "\n\n").addActionRow(
                        net.dv8tion.jda.api.interactions.components.buttons.Button.success("casmode", "Casual"), net.dv8tion.jda.api.interactions.components.buttons.Button.danger("ratedmode", "Rated"), net.dv8tion.jda.api.interactions.components.buttons.Button.primary("enginemode", "Play BOT"), net.dv8tion.jda.api.interactions.components.buttons.Button.link("https://lichess.org/login", "Login/Register"), net.dv8tion.jda.api.interactions.components.buttons.Button.secondary("playhelp", "❓ Help")).queue();
            }
            case "View Lichess Broadcasts" -> {
                BroadcastLichess broadcast = new BroadcastLichess(client);
                context.replyEmbeds(broadcast.getBroadData().build()).queue();
            }
            case "Watch GMs" -> {
                WatchMaster watchMaster = new WatchMaster(client);
                context.deferReply(true).queue();
                Objects.requireNonNull(context.getChannel()).sendMessage(watchMaster.getMasterGames()).addActionRow(net.dv8tion.jda.api.interactions.components.buttons.Button.link("https://lichess.org" + watchMaster.getGameId()[1], "Analyze")).queue();
            }
            case "Chess.com Daily Puzzle" -> {
                DailyCommandCC daily = new DailyCommandCC();
                context.deferReply(true).queue();
                Objects.requireNonNull(context.getChannel()).sendMessageEmbeds(new EmbedBuilder().setColor(Color.magenta).setTitle("Chess.com Daily Puzzle").setDescription(StockFish.getStockFishTextExplanation(13, daily.getFEN()) + "\n\n " + daily.defineSideToMove(new ChessUtil(), daily.getFEN())).setImage(daily.getPuzzle()).setFooter("run /analyze [fen] to view the moves in action!").build()).queue();
            }
        }
    }
}
