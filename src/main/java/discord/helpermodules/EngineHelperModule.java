package discord.helpermodules;

import abstraction.ChessUtil;
import engine.StockFish;
import com.github.bhlangonijr.chesslib.Board;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import java.awt.*;

/**
 * EngineHelperModule class to handle the engine playing
 */
public class EngineHelperModule {

    private final SlashCommandInteractionEvent event;
    private Board whiteBoard;
    private final Board blackBoard;

    public EngineHelperModule(SlashCommandInteractionEvent event, Board whiteBoard, Board blackBoard) {
        this.event = event;
        this.whiteBoard = whiteBoard;
        this.blackBoard = blackBoard;
    }

    /**
     * Send the resetboard command to reset the current board state
     */
    public void sendresetBoardCommand() {
        whiteBoard.loadFromFen(new Board().getFen());
        blackBoard.loadFromFen(new Board().getFen());
        event.reply("board is reset!").setEphemeral(true).queue();
    }

    /**
     * send the move command to the engine from the white side
     */
    public void sendwhiteSideMoveCommand() {
        try {

            String makemove = event.getOption("play-move").getAsString();
            EmbedBuilder embedBuilder = new EmbedBuilder();
            ChessUtil util = new ChessUtil();

            if (whiteBoard.isMated() || whiteBoard.isDraw() || whiteBoard.isStaleMate()) {
                event.reply("game over!").queue();
                whiteBoard = new Board();
            }

            whiteBoard.doMove(makemove);
            whiteBoard.doMove(StockFish.getBestMove(15, whiteBoard.getFen()));
            event.reply("Game Manager Tab \n **resign** to end the game \n **draw** to draw the game!").addActionRow(net.dv8tion.jda.api.interactions.components.buttons.Button.danger("bot-lose", "Resign"), Button.secondary("bot-draw", "Draw")).setEphemeral(true).queue();
            embedBuilder.setTitle("White to move");
            embedBuilder.setColor(Color.green);
            embedBuilder.setThumbnail("https://stockfishchess.org/images/logo/icon_512x512@2x.png");
            embedBuilder.setImage(util.getImageFromFEN(whiteBoard.getFen(), false, "brown", "kosal"));
            event.getHook().sendMessageEmbeds(embedBuilder.build()).queue();

        } catch (Exception e) {
            event.reply("Not valid move! \n\n **If you are trying to castle use Captial letters (O-O & O-O-O)** \n\n (If you are running this command first time) The game is already on in other server, please reset the board with **/resetboard**!").setEphemeral(true).queue();
        }
    }


}