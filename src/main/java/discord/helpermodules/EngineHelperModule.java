package discord.helpermodules;

import Game.NoGameException;
import Game.GameHandler;
import Game.GameSchema;
import abstraction.ChessUtil;
import discord.mainhandler.Thumbnail;
import engine.StockFish;
import com.github.bhlangonijr.chesslib.Board;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import runner.Main;
import setting.SettingSchema;
import setting.SettingSchemaModule;

import java.awt.*;


public class EngineHelperModule extends SettingSchemaModule {

    private final SlashCommandInteractionEvent event;
    private final GameHandler gameHandler = new GameHandler(Main.getGamesCollection());

    
    public EngineHelperModule(SlashCommandInteractionEvent event) {
        super(event.getUser().getId());
        this.event = event;
    }

    
    public void sendwhiteSideMoveCommand() {
        try {
            event.deferReply(true).queue();
            String makemove = event.getOption("play-move").getAsString();
            ChessUtil util = new ChessUtil();
            GameSchema schema = gameHandler.lookUpGame(event.getUser().getId());
            Board board = new Board();
            board.loadFromFen(schema.getFen());

            if (board.isMated() || board.isDraw() || board.isStaleMate()) {
                event.getHook().sendMessage("game over!").queue();
                gameHandler.updateFen(event.getUser().getId(), "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
                return;
            }

            board.doMove(makemove);
            board.doMove(StockFish.getBestMove(schema.getDepth(), board.getFen()));

            SettingSchema setting = getSettingSchema();
            EmbedBuilder embedBuilder = new EmbedBuilder()
                .setTitle("White to move")
                .setColor(Color.green)
                .setThumbnail(Thumbnail.getStockfishLogo())
                .setImage(util.getImageFromFEN(board.getFen(), setting.getBoardTheme(), setting.getPieceType()));

            event.getHook().sendMessage("Game Manager Tab \n **resign** to end the game \n **draw** to draw the game!")
                .addActionRow(Button.danger("bot-lose", "Resign"), Button.secondary("bot-draw", "Draw"))
                .setEphemeral(true).queue();
            event.getHook().sendMessageEmbeds(embedBuilder.build()).queue();
            gameHandler.updateFen(event.getUser().getId(), board.getFen());

        } catch (Exception e) {
            if (e instanceof NoGameException) {
                event.getHook().sendMessage(e.getMessage()).queue();
            } else {
                event.getHook().sendMessage("Not valid move! \n\n **If you are trying to castle use Capital letters (O-O & O-O-O)** \n\n (If you are running this command first time) please use **/playengine** to select the engine level and start a new game!")
                    .setEphemeral(true).queue();
            }
        }
    }

   
    public void sendPlayEngine() {
        try {
            event.deferReply(true).queue();
            GameSchema schema = new GameSchema(event.getUser().getId(), "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1", Integer.parseInt(event.getOptionsByName("difficulty").get(0).getAsString()));
            gameHandler.createGame(schema);
            event.getHook().sendMessage("Game created against Stockfish Level " + event.getOptionsByName("difficulty").get(0).getAsString() + " \n run **/move <move>** use Chess SAN notation (e4) or UCI notation (e2e4)").queue();
        } catch (NoGameException g) {
            event.getHook().sendMessage(g.getMessage()).queue();
        }
    }

   
    public void sendSetEngineMode() {
        try {
            event.deferReply(true).queue();
            gameHandler.updateDepth(event.getUser().getId(), Integer.parseInt(event.getOptionsByName("difficulty").get(0).getAsString()));
            event.getHook().sendMessage("Engine Difficulty updated!").queue();
        } catch (NoGameException g) {
            event.getHook().sendMessage(g.getMessage()).queue();
        }
    }
}