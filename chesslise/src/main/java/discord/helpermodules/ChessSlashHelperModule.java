package discord.helpermodules;

import abstraction.ChessUtil;
import abstraction.CommandTrigger;
import chariot.Client;
import chessdb.ChessDBQuery;

import discord.mainhandler.Thumbnail;
import lichess.FenPuzzle;
import lichess.UserProfile;
import net.dv8tion.jda.api.EmbedBuilder;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.components.ActionRow;

import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import net.dv8tion.jda.api.interactions.modals.Modal;
import setting.SettingHandler;
import setting.SettingSchema;
import setting.SettingSchemaModule;

import java.awt.*;


public class ChessSlashHelperModule extends SettingSchemaModule implements CommandTrigger {

    private final SlashCommandInteractionEvent event;
    private final SettingSchema setting = getSettingSchema();

    
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

    
    public ChessSlashHelperModule(SlashCommandInteractionEvent event) {
        super(event.getUser().getId());
        this.event = event;
    }

    public void sendChessDBInfo() {
        ChessDBQuery query = new ChessDBQuery();

        event.deferReply().queue();
        String fen = event.getOption("paste-fen").getAsString();
        String info = query.getTop3BestMove(fen);

        EmbedBuilder builder = getChessDBEmbed(info, fen);

        event.getHook().sendMessageEmbeds(builder.build()).addActionRow(Button.success("onemove", "Play 1st move"), Button.success("twomove", "Play 2nd move"), Button.success("threemove", "Play 3rd move")).queue();
    }

    
    public void sendChessFEN(){
        ChessUtil util = new ChessUtil();

        event.deferReply().queue();
        String fen = event.getOption("input-fen").getAsString();
        FenPuzzle fenPuzzle = new FenPuzzle(fen);
        if(fenPuzzle.isValidFen()) {
            event.getHook().sendMessageEmbeds(fenPuzzle.defineCommandCard(setting).addField("Author", event.getUser().getAsMention(), true).build())
                    .addActionRow(Button.link(util.getAnalysisBoard(fen), "Analysis Board"), Button.danger("delete", "delete")).queue();
        }else{
            event.getHook().sendMessage("Invalid FEN!").setEphemeral(true).queue();
        }
    }


    private EmbedBuilder getChessDBEmbed(String moveDesc, String fen){
        ChessUtil chessUtil = new ChessUtil();
        EmbedBuilder builder = new EmbedBuilder();
        builder.setImage(chessUtil.getImageFromFEN(fen, setting.getBoardTheme(), setting.getPieceType()));
        builder.setTitle("ChessDB CN Analysis");
        builder.setDescription(moveDesc);
        builder.addField("fen", fen, true);
        builder.setFooter("Analysis by ChessDB CN see more here https://chessdb.cn/cloudbookc_info_en.html");

        return builder;
    }

    public void sendCoordinateGame() {
        event.deferReply().queue();

        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setThumbnail(Thumbnail.getChessliseLogo());
        embedBuilder.setTitle("The Chesslise Coordinate Game");
        embedBuilder.setDescription("""
                Welcome to the coordinate game!
                
                You have 3 mins to view the position and pick the correct coordinate,
                
                you have button options which you have to select the right answer,
                
                Your job is do the most coordinates, no points for now but stats will be added later on!
                
                Are you ready? Click the button below to start the game!
              
                """);
        embedBuilder.setColor(Color.MAGENTA);
        event.getHook().sendMessageEmbeds(embedBuilder.build()).addActionRow(Button.success("startcoorgame", "Start!")).queue();
    }


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

   
    public void sendChessComUserProfileInputForm() {
        buildInputForm("profileusercc", "Input Chess.com Username", "Input Chess.com Username", "modalproc", "View Chess.com Profiles!");
    }

   
    public void sendLichessWatchGameCommand() {
        buildInputForm("watch_user_or_game", "Input Lichess Username Or Lichess Game", "Input Lichess Username Or Lichess Game", "modalwatch", "Watch Live Or Recent Lichess Games!");

    }



    public void sendPlayChallengeCommand() {
        event.reply("""
                ## Please Pick Your Lichess Game's Mode ⚔️\s

                ⚔️ You can now join Chesslise's own chess server! Find new chess friends, new challenges,
                read more by clicking on the ❓ **CSSN Network Help**

                """)
                .addActionRow(
                        Button.success("casmode", "\uD83D\uDC4C Casual"),
                        Button.danger("ratedmode", "\uD83E\uDD3A Rated"),
                        Button.success("friend", "\uD83D\uDDE1️ Play Friend"))
                .addActionRow(
                        Button.link("https://lichess.org/login", "\uD83D\uDD12 Login/Register"),
                        Button.secondary("playhelp", "❓ Help"),
                        Button.success("cssnhelp", "❓ CSSN Network Help"))
                .queue();
    }

    /**
     * Send the select lichess username handle request
     */
    public void sendSelectLichessUserNameHandleRequest() {
        String userID = event.getOptionsByName("search-user").get(0).getAsString();
        UserProfile userProfile = new UserProfile(Client.basic(), userID);
        event.deferReply(false).queue();
        event.getHook().sendMessage(userProfile.getUserProfile()).setEphemeral(false).queue();

    }


    public void sendUserSettingCommand(){
        event.deferReply(true).queue();
        String theme = event.getOptionsByName("theme").get(0).getAsString();
        String pieceType = event.getOptionsByName("piecetype").get(0).getAsString();
        String difficultyLevel = event.getOptionsByName("puzzledifficultylevel").get(0).getAsString();
        String status = SettingHandler.updateSetting(new SettingSchema(theme, pieceType, event.getUser().getId(),difficultyLevel));
        event.getHook().sendMessage(status).queue();
    }


    @Override
    public void trigger(String commandName) {
        switch (commandName) {
            case "play" -> sendPlayChallengeCommand();

            case "profile" -> sendSelectLichessUserNameHandleRequest();

            case "profilecc" -> sendChessComUserProfileInputForm();

            case "watch" -> sendLichessWatchGameCommand();

            case "chessdb" -> sendChessDBInfo();

            case "fen" -> sendChessFEN();

            case "setting" -> sendUserSettingCommand();

            case "coordinategame" -> sendCoordinateGame();

        }
    }
}