package discord.helpermodules;

import abstraction.ChessUtil;
import abstraction.Puzzle;
import engine.StockFish;
import com.github.bhlangonijr.chesslib.Board;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import java.awt.*;


public class BotContextModule {


    public BotContextModule() {

    }


    public void resetBoardCommand(SlashCommandInteractionEvent slashEvent, Board boardwhite, Board boardblack) {
        boardwhite.loadFromFen(new Board().getFen());
        boardblack.loadFromFen(new Board().getFen());
        slashEvent.reply("board is reset!").setEphemeral(true).queue();
    }


    public void whiteSideMoveCommand(SlashCommandInteractionEvent slashEvent, Board board) {
        try {

            String makemove = slashEvent.getOption("play-move").getAsString();
            EmbedBuilder embedBuilder = new EmbedBuilder();
            ChessUtil util = new ChessUtil();

            if (board.isMated() || board.isDraw() || board.isStaleMate()) {
                slashEvent.reply("game over!").queue();
                board = new Board();
            }

            board.doMove(makemove);

            board.doMove(StockFish.getBestMove(15, board.getFen()));
            slashEvent.reply("Game Manager Tab \n **resign** to end the game \n **draw** to draw the game!").addActionRow(net.dv8tion.jda.api.interactions.components.buttons.Button.danger("bot-lose", "Resign"), Button.secondary("bot-draw", "Draw")).setEphemeral(true).queue();
            embedBuilder.setTitle("White to move");
            embedBuilder.setColor(Color.green);
            embedBuilder.setThumbnail("https://stockfishchess.org/images/logo/icon_512x512@2x.png");
            embedBuilder.setDescription("\n\n [Join our Server ♟️](https://discord.gg/uncmhknmYg)");
            embedBuilder.setImage(util.getImageFromFEN(board.getFen(), false, "brown", "kosal"));
            slashEvent.getHook().sendMessageEmbeds(embedBuilder.build()).queue();


        } catch (Exception e) {
            slashEvent.reply("Not valid move! \n\n **If you are trying to castle use Captial letters (O-O & O-O-O)** \n\n (If you are running this command first time) The game is already on in other server, please reset the board with **/resetboard**!").setEphemeral(true).queue();
        }
    }


    public void runAnalyzeButton(ButtonInteractionEvent event) {
        getStockfishSearchCommand(StockFish.getUserFen.get(event.getUser().getId()), null, true, event);
    }

    public void runAnalyzeOnPuzzleCommand(ButtonInteractionEvent event, Puzzle puzzleCommand){
        event.reply(StockFish.getStockFishTextExplanation(13, puzzleCommand.definePuzzleFen())).setEphemeral(true).queue();
    }



    public void getStockfishSearchCommand(String fen, SlashCommandInteractionEvent slashEvent, boolean isButton, ButtonInteractionEvent buttonEvent) {
        if (!isButton) {
            ChessUtil util = new ChessUtil();
            EmbedBuilder sf = new EmbedBuilder();
            Board b = new Board();
            b.loadFromFen(fen);
            sf.setThumbnail("https://stockfishchess.org/images/logo/icon_512x512@2x.png");
            sf.setImage(util.getImageFromFEN(fen, !fen.contains("w"), "brown", "kosal"));
            sf.setDescription(StockFish.getStockFishTextExplanation(13, fen) + "\n\n [Join our Server ♟️](https://discord.gg/uncmhknmYg)");
            sf.setColor(Color.green);
            b.doMove(StockFish.getBestMove(13, fen));
            StockFish.getUserFen.put(slashEvent.getUser().getId(), b.getFen());
            slashEvent.reply("Analyzing .... ").setEphemeral(false).queue();
            slashEvent.getHook().sendMessageEmbeds(sf.build()).addActionRow(Button.secondary("sf", "Play move")).setEphemeral(false).queue();
        } else {
            ChessUtil util = new ChessUtil();
            EmbedBuilder sf = new EmbedBuilder();
            Board b = new Board();
            b.loadFromFen(fen);
            sf.setThumbnail("https://stockfishchess.org/images/logo/icon_512x512@2x.png");
            sf.setImage(util.getImageFromFEN(fen, !fen.contains("w"), "brown", "kosal"));
            sf.setDescription(StockFish.getStockFishTextExplanation(13, fen) + "\n\n [Join our Server ♟️](https://discord.gg/uncmhknmYg)");
            sf.setColor(Color.green);
            b.doMove(StockFish.getBestMove(13, fen));
            StockFish.getUserFen.put(buttonEvent.getUser().getId(), b.getFen());
            buttonEvent.editMessageEmbeds(sf.build()).setActionRow(Button.secondary("sf", "Play move")).queue();
        }
    }



}