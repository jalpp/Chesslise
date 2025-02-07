package discord.helpermodules;

import abstraction.ChessUtil;
import chessdb.ChessDBQuery;
import net.dv8tion.jda.api.EmbedBuilder;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import net.dv8tion.jda.api.interactions.modals.Modal;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import java.awt.*;

/**
 * ChessSlashHelperModule class to handle the chess slash helper module
 */
public class ChessSlashHelperModule {

    private final SlashCommandInteractionEvent event;
    /**
     * The chesslise logo
     */
    public final static String logo = "https://raw.githubusercontent.com/jalpp/DojoIcons/dd7365ea7d768fe17056d9b14ee6740c2bf4e261/oldIcons/Black%20Blue%20White%20Tactical%20eSports%20Discord%20Logo.png";
    /**
     * the learn chess information
     */
    public final static String[][] LEARN_CHESS = {
            {"Rook", "**Rook:** Move any number of squares horizontally or vertically.", "https://images.chesscomfiles.com/uploads/v1/images_users/tiny_mce/pdrpnht/phpfyINI1.png"},
            {"Bishop", "**Bishop:** Move any number of squares diagonally.", "https://images.chesscomfiles.com/uploads/v1/images_users/tiny_mce/PeterDoggers/phpdzgpdQ.png"},
            {"Knight", "**Knight:** Move in an 'L' shape: two squares in one direction and then one square perpendicular.", "https://images.chesscomfiles.com/uploads/v1/images_users/tiny_mce/pdrpnht/phpVuLl4W.png"},
            {"Queen", "**Queen:** Move any number of squares horizontally, vertically, or diagonally.", "https://images.chesscomfiles.com/uploads/v1/images_users/tiny_mce/pdrpnht/phpCQgsYR.png"},
            {"King", "**King:** Move 1 square at a time horizontally, vertically, or diagonally.", "https://images.chesscomfiles.com/uploads/v1/images_users/tiny_mce/pdrpnht/phpmVRKYr.png"},
            {"King-castle", "King Special move: castling, when king and rook gets space between his king and the rooks and no piece are present he can castle that way:", "https://www.chessbazaar.com/blog/wp-content/uploads/2014/11/castling.gif"},
            {"Pawn", "Pawn Move: can only go up the board and capture on the flank side:", "https://images.chesscomfiles.com/uploads/v1/images_users/tiny_mce/pdrpnht/php8nbVYg.gif"},
            {"Pawn-en", "Pawn Special Move: en-passant capture when opposing side moves side pawn beside your pawn:", "https://images.chesscomfiles.com/uploads/v1/images_users/tiny_mce/pdrpnht/phpZmdTyW.gif"},
            {"Pawn-pro", "Pawn Special Move: Promotion, when your pawn reaches the 8th rank you can promote to queen,rook,bishop, or a night:", "https://images.chesscomfiles.com/uploads/v1/images_users/tiny_mce/PedroPinhata/phpFSZHst.gif"}
    };

    /**
     * Instantiates a new Chess slash helper module.
     *
     * @param event the event
     */
    public ChessSlashHelperModule(SlashCommandInteractionEvent event) {
        this.event = event;
    }

    /**
     * Send the ChessDB for /chessdb command
     */
    public void sendChessDBInfo() {
        ChessDBQuery query = new ChessDBQuery();
        ChessUtil chessUtil = new ChessUtil();

        event.deferReply().queue();
        String fen = event.getOption("paste-fen").getAsString();
        String info = query.getTop3BestMove(fen);

        EmbedBuilder builder = new EmbedBuilder();
        builder.setImage(chessUtil.getImageFromFEN(fen, "green", "kosal"));
        builder.setTitle("ChessDB CN Analysis");
        builder.setDescription(info);
        builder.setFooter("Analysis by ChessDB CN see more here https://chessdb.cn/cloudbookc_info_en.html");

        event.getHook().sendMessageEmbeds(builder.build()).queue();
    }

    /**
     * send view fen embed for /fen
     */
    public void sendChessFEN(){
        ChessUtil util = new ChessUtil();

        event.deferReply().queue();
        String fen = event.getOption("input-fen").getAsString();
        EmbedBuilder builder = new EmbedBuilder();
        builder.setImage(util.getImageFromFEN(fen, "green", "kosal"));
        builder.setColor(Color.PINK);

        event.getHook().sendMessageEmbeds(builder.build()).addActionRow(Button.link(util.getAnalysisBoard(fen), "Analysis Board")).queue();
    }

    /**
     * Send the input form for Watch command and Chess.com user profile
     *
     * @param inputid     the input id
     * @param label       the label
     * @param placeholder the placeholder
     * @param modalid     the modal id
     * @param modaltitle  the modal title
     */
    private void buildInputForm(String inputid, String label, String placeholder, String modalid, String modaltitle) {
        TextInput ptext = TextInput.create(inputid, label, TextInputStyle.SHORT)
                .setPlaceholder(placeholder)
                .setMinLength(2)
                .setMaxLength(100)
                .setRequired(true)
                .build();
        Modal pmodal = Modal.create(modalid, modaltitle)
                .addComponents(ActionRow.of(ptext))
                .build();
        event.replyModal(pmodal).queue();
    }

    /**
     * Send the Chess.com user profile input form
     */
    public void sendChessComUserProfileInputForm() {
        buildInputForm("profileusercc", "Input Chess.com Username", "Input Chess.com Username", "modalproc", "View Chess.com Profiles!");
    }

    /**
     * Send the Lichess watch game command form
     */
    public void sendLichessWatchGameCommand() {
        buildInputForm("watch_user_or_game", "Input Lichess Username Or Lichess Game", "Input Lichess Username Or Lichess Game", "modalwatch", "Watch Live Or Recent Lichess Games!");

    }


}