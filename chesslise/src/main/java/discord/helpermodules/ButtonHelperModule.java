package discord.helpermodules;

import abstraction.ChessUtil;
import abstraction.CommandTrigger;
import chessdb.ChessDBQuery;
import coordinategame.Coordinategame;
import discord.mainhandler.Thumbnail;
import lichess.DailyCommand;
import lichess.Game;
import chariot.Client;
import Game.*;
import com.github.bhlangonijr.chesslib.Board;
import lichess.ThemePuzzle;
import lichess.UserGame;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import net.dv8tion.jda.api.interactions.modals.Modal;
import runner.Main;
import setting.SettingSchema;
import setting.SettingSchemaModule;

import java.awt.*;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


import static discord.helpermodules.ChessSlashHelperModule.*;


public class ButtonHelperModule extends SettingSchemaModule implements CommandTrigger {

    private final ButtonInteractionEvent buttonEvent;
    private static final Client client = Client.basic(conf -> conf.retries(0));
    private final SettingSchema setting = getSettingSchema();


    public ButtonHelperModule(ButtonInteractionEvent buttonEvent) {
        super(buttonEvent.getUser().getId());
        this.buttonEvent = buttonEvent;
    }

  
    public void sendPlayingEngineFlow() {
        GameHandler gameHandler = new GameHandler(Main.getGamesCollection());
        switch (buttonEvent.getComponentId()) {
            case "bot-lose" -> {

                buttonEvent.editMessageEmbeds(new EmbedBuilder().setDescription("You have resigned the game!").setColor(Color.red).build()).setActionRow(Button.danger("bot-lose", "Resgin").asDisabled(),Button.secondary("bot-draw", "Draw").asDisabled()).queue();
                try{
                    gameHandler.updateFen(buttonEvent.getUser().getId(), "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
                }catch (Exception e){
                    System.out.println(e.getMessage());
                }
            }
            case "bot-draw" -> {
                buttonEvent.editMessageEmbeds(new EmbedBuilder().setDescription("Chesslise accepts the draw!").setColor(Color.yellow).build()).setActionRow(Button.danger("bot-lose", "Resgin").asDisabled(), Button.secondary("bot-draw", "Draw").asDisabled()).queue();
                try{
                    gameHandler.updateFen(buttonEvent.getUser().getId(), "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
                }catch (Exception e){
                    System.out.println(e.getMessage());
                }
            }
        }
    }

  
    public void sendPlayCommandFlow() {

        switch (buttonEvent.getComponentId()) {
            case "load-again" -> buttonEvent.reply("""
                    ## Please Pick Your Lichess Game's Mode ⚔️\s
                    
                    ⚔️ You can now join Chesslise's own chess server! Find new chess friends, new challenges,
                    read more by clicking on the ❓ **CSSN Network Help**
                    
                    """).addActionRow(
                    Button.success("casmode", "\uD83D\uDC4C Casual"), Button.danger("ratedmode", "\uD83E\uDD3A Rated"), Button.success("friend", "\uD83D\uDDE1️ Play Friend")).addActionRow(Button.link("https://lichess.org/login", "\uD83D\uDD12 Login/Register"), Button.secondary("playhelp", "❓ Help"), Button.success("cssnhelp", "❓ CSSN Network Help")).queue();
            case "loadr" ->
                    buttonEvent.editMessage(" ## Please Pick Your Time Control ⏱️").setActionRow(Button.danger("3+0r", "\uD83D\uDD25 3+0")
                            , Button.danger("5+0r", "\uD83D\uDD25 5+0")
                            , Button.danger("10+0r", "\uD83D\uDC07 10+0"), Button.success("load-again", "↩️ Home")).queue();
            case "loadc" ->
                    buttonEvent.editMessage(" ## Please Pick Your Time Control ⏱️").setActionRow(Button.primary("3+0c", " \uD83D\uDD253+0")
                            , Button.primary("5+0c", "\uD83D\uDD25 5+0")
                            , Button.primary("10+0c", "\uD83D\uDC07 10+0"), Button.success("load-again", "↩️ Home")).queue();
            case "3+0r" ->
                    buttonEvent.editMessage(Game.generateOpenEndedChallengeURLs(3, 0, true, client)).setActionRow(Button.primary("3+0r", "Rematch"), Button.success("load-again", "↩️ Home")).queue();
            case "5+0r" ->
                    buttonEvent.editMessage(Game.generateOpenEndedChallengeURLs(5, 0, true, client)).setActionRow(Button.primary("5+0r", "Rematch"), Button.success("load-again", "↩️ Home")).queue();
            case "10+0r" ->
                    buttonEvent.editMessage(Game.generateOpenEndedChallengeURLs(10, 0, true, client)).setActionRow(Button.primary("10+0r", "Rematch"), Button.success("load-again", "↩️ Home")).queue();
            case "3+0c" ->
                    buttonEvent.editMessage(Game.generateOpenEndedChallengeURLs(3, 0, false, client)).setActionRow(Button.primary("3+0c", "Rematch"), Button.success("load-again", "↩️ Home")).queue();
            case "5+0c" ->
                    buttonEvent.editMessage(Game.generateOpenEndedChallengeURLs(5, 0, false, client)).setActionRow(Button.primary("5+0c", "Rematch"), Button.success("load-again", "↩️ Home")).queue();
            case "10+0c" ->
                    buttonEvent.editMessage(Game.generateOpenEndedChallengeURLs(10, 0, false, client)).setActionRow(Button.primary("10+0c", "Rematch"), Button.success("load-again", "↩️ Home")).queue();
            case "playhelp" -> {
                buttonEvent.replyEmbeds(getPlayCommandInfo().build()).setEphemeral(true).queue();
            }
            case "cssnhelp" -> buttonEvent.replyEmbeds(getCSSNHelpInfo().build()).setEphemeral(true).queue();
            case "friend" -> sendSelfUserInputForm();
        }

    }

    
    public void sendPlayCommandUI() {

        switch (buttonEvent.getComponentId()) {
            case "casmode" -> buttonEvent.editMessage("## Please Pick Your Time Control ⏱️").setActionRow(
                    Button.primary("ultrafastc", "\uD83D\uDE85 1/4+0"),
                    Button.primary("bulletfastc", "\uD83D\uDE85 1+0"),
                    Button.primary("blitzfastc", "\uD83D\uDD25 3+2"),
                    Button.primary("rapidfastc", "\uD83D\uDD25 5+5"),
                    Button.success("loadc", "\uD83D\uDD04 Load More Time Controls")
            ).queue();

            case "ratedmode" -> buttonEvent.editMessage("## Please Pick Your Time Control ⏱️").setActionRow(
                    Button.danger("ultrafastr", "\uD83D\uDE85 1/4+0"),
                    Button.danger("bulletfastr", "\uD83D\uDE85 1+0"),
                    Button.danger("blitzfastr", "\uD83D\uDD25 3+2"),
                    Button.danger("rapidfastr", "\uD83D\uDD25 5+5"),
                    Button.success("loadr", "\uD83D\uDD04 Load More Time Controls")
            ).queue();

        }

    }

    public void sendPlayCoordinateGameUI() {
        String id = buttonEvent.getComponentId();

        if (id.contains("ansidt")) {
            buttonEvent.reply("Correct! Keep moving")
                    .setEphemeral(true)
                    .queue(hook -> sendRunCoordinateGame());

        } else if (id.contains("ansidf")) {
            buttonEvent.reply("Incorrect! Good luck next time!")
                    .setEphemeral(true)
                    .queue(hook -> sendRunCoordinateGame());

        } else if (id.equalsIgnoreCase("startcoorgame")) {
          
            buttonEvent.deferReply().setEphemeral(true).queue(hook -> {
                sendRunCoordinateGame();
            });
        }
    }


    private void sendRunCoordinateGame() {
        Coordinategame game = new Coordinategame();
        ArrayList<String> ans = game.getAnswers();
        HashMap<String, String> ansmap = game.getAnswersmap();
        ItemComponent[] components = new ItemComponent[5];
        for (int i = 0; i < ans.size(); i++) {
            components[i] = Button.secondary("ansid" + ansmap.get(ans.get(i)) + ans.get(i) + i, "pick " + ans.get(i));
        }

      
        buttonEvent.getHook().editOriginalEmbeds(game.defineCommandCard(setting).build())
                .setActionRow(components)
                .queue(sentMessage -> {
                   
                    ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
                    scheduler.schedule(() -> {
                        sentMessage.editMessageEmbeds(new EmbedBuilder()
                                .setTitle("⏰ Time's up!")
                                .setDescription("Game Over. You didn't respond in time.")
                                .setColor(Color.RED)
                                .build()
                        ).queue(); 
                        scheduler.shutdown();
                    }, 3, TimeUnit.MINUTES);
                });
    }


    public void sendChessDBButtonView(){

        switch (buttonEvent.getComponentId()){
            case "onemove" -> sendChessDBMove(0);
            case "twomove" -> sendChessDBMove(1);
            case "threemove" -> sendChessDBMove(2);
        }
    }


    
    public void sendPuzzleDBTheme(){
        switch (buttonEvent.getComponentId()){
            case "loadpuzzles" ->  buttonEvent.editMessageEmbeds(new EmbedBuilder().setDescription("Pick more for themes!").build()).setActionRow(Button.success("sacrifice", "sacrifice"), Button.success("long", "long"),  Button.success("master", "From Master Games")).queue();
            case "mate" -> sendPuzzleThemeCard("mate");
            case "middlegame" -> sendPuzzleThemeCard("middlegame");
            case "endgame" -> sendPuzzleThemeCard("endgame");
            case "long" -> sendPuzzleThemeCard("long");
            case "sacrifice" -> sendPuzzleThemeCard("sacrifice");
            case "master" -> sendPuzzleThemeCard("master");
        }
    }

   
    private void sendPuzzleThemeCard(String theme){
        ThemePuzzle puzzle = new ThemePuzzle(theme);
        buttonEvent.editMessageEmbeds(puzzle.defineCommandCard(setting).build()).setActionRow(Button.link(puzzle.defineAnalysisBoard(puzzle.definePuzzleFen()), "Analysis Board")).setActionRow(Button.link(puzzle.getGameURL(), "Game")).queue();
    }

    
    private void sendChessDBMove(int moveNumber){
        buttonEvent.deferEdit().queue();
        ChessDBQuery query = new ChessDBQuery();

        String fen = buttonEvent.getMessage().getEmbeds().get(0).getFields().get(0).getValue();
        String move = ChessDBQuery.getTop3Moves(buttonEvent.getMessage().getEmbeds().get(0).getDescription()).get(moveNumber);
        Board position = new Board();
        position.loadFromFen(fen);
        position.doMove(move);
        String newInfo = query.getTop3BestMove(position.getFen());
        EmbedBuilder chessDBView = getChessDBEdited(newInfo, position.getFen(), move);

        buttonEvent.getHook().editOriginalEmbeds(chessDBView.build()).setActionRow(Button.success("onemove", "Play 1st move"), Button.success("twomove", "Play 2nd move"), Button.success("threemove", "Play 3rd move")).queue();
    }

    public void sendFlipBoard() {
        if(buttonEvent.getComponentId().equalsIgnoreCase("flip-board")){
            String gameLink = buttonEvent.getMessage().getEmbeds().get(0).getFields().get(1).getValue();
            assert gameLink != null;
            String color = gameLink.contains("white") ? "black" : "white";
            String[] gameArray = gameLink.split("/");
            String gameId = gameArray[gameArray.length - 1].split("gif")[0].replace(".", "");
            String gameShareUrl = UserGame.getGameGif(color, gameId, getSettingSchema());
            buttonEvent.replyEmbeds(new EmbedBuilder().setColor(Color.BLUE).addField("Share", gameShareUrl, true).setImage(gameShareUrl).setThumbnail(Thumbnail.getChessliseLogo()).build()).setEphemeral(true).queue();
        }
    }
    
    public void sendMoreTimeControls() {

        switch (buttonEvent.getComponentId()) {
            case "ultrafastc" ->
                    buttonEvent.editMessage(Game.generateOpenEndedChallengeURLs(0, 0, false, client)).setActionRow(Button.primary("ultrafastc", "Rematch"), Button.success("load-again", "↩️ Home")).queue();
            case "bulletfastc" ->
                    buttonEvent.editMessage(Game.generateOpenEndedChallengeURLs(1, 0, false, client)).setActionRow(Button.primary("bulletfastc", "Rematch"), Button.success("load-again", "↩️ Home")).queue();
            case "blitzfastc" ->
                    buttonEvent.editMessage(Game.generateOpenEndedChallengeURLs(3, 2, false, client)).setActionRow(Button.primary("blitzfastc", "Rematch"), Button.success("load-again", "↩️ Home")).queue();
            case "rapidfastc" ->
                    buttonEvent.editMessage(Game.generateOpenEndedChallengeURLs(5, 5, false, client)).setActionRow(Button.primary("rapidfastc", "Rematch"), Button.success("load-again", "↩️ Home")).queue();
        }

        switch (buttonEvent.getComponentId()) {
            case "ultrafastr" ->
                    buttonEvent.editMessage(Game.generateOpenEndedChallengeURLs(0, 0, true, client)).setActionRow(Button.primary("ultrafastr", "Rematch"), Button.success("load-again", "↩️ Home")).queue();
            case "bulletfastr" ->
                    buttonEvent.editMessage(Game.generateOpenEndedChallengeURLs(1, 0, true, client)).setActionRow(Button.primary("bulletfastr", "Rematch"), Button.success("load-again", "↩️ Home")).queue();
            case "blitzfastr" ->
                    buttonEvent.editMessage(Game.generateOpenEndedChallengeURLs(3, 2, true, client)).setActionRow(Button.primary("blitzfastr", "Rematch"), Button.success("load-again", "↩️ Home")).queue();
            case "rapidfastr" ->
                    buttonEvent.editMessage(Game.generateOpenEndedChallengeURLs(5, 5, true, client)).setActionRow(Button.primary("rapidfastr", "Rematch"), Button.success("load-again", "↩️ Home")).queue();

        }
    }


    
    public void sendPuzzleButtons() {
        DailyCommand dailyCommand = new DailyCommand(client);
        if (buttonEvent.getComponentId().equals("hint")) {
            buttonEvent.replyEmbeds(dailyCommand.getThemes().build()).setEphemeral(true).queue();
        }

    }

    public void deleteCurrentMessage() {
        if (buttonEvent.getComponentId().equalsIgnoreCase("delete")) {
            String issuerId = Objects.requireNonNull(buttonEvent.getMessage().getEmbeds().get(0).getFields().get(0).getValue());
            System.out.println(issuerId);

            if (!issuerId.equalsIgnoreCase(buttonEvent.getUser().getAsMention())) {

                buttonEvent.deferReply(true)
                        .queue(hook -> hook.sendMessage("only op can delete the message!")
                                .queue(message ->
                                        hook.deleteOriginal().queueAfter(3, TimeUnit.SECONDS)
                                )
                        );
                return;
            }

            buttonEvent.deferReply().queue(interactionHook -> {
                buttonEvent.getMessage().delete().queue();
                interactionHook.deleteOriginal().queueAfter(3, TimeUnit.SECONDS);
            });
        }
    }

  
    public void sendLearnCommand() {

        switch (buttonEvent.getComponentId()) {
            case "Bishop" ->
                    buttonEvent.editMessageEmbeds(getLearnChessCard("Bishop").build()).setActionRow(Button.primary("Rook", "⬅️"), Button.primary("Knight", "♞")).queue();
            case "Knight" ->
                    buttonEvent.editMessageEmbeds(getLearnChessCard("Knight").build()).setActionRow(Button.primary("Bishop", "⬅️"), Button.primary("Queen", "♛")).queue();
            case "Queen" ->
                    buttonEvent.editMessageEmbeds(getLearnChessCard("Queen").build()).setActionRow(Button.primary("Knight", "⬅️"), Button.primary("King", "♚")).queue();
            case "Rook" ->
                    buttonEvent.editMessageEmbeds(getLearnChessCard("Rook").build()).setActionRow(Button.primary("Bishop", "♝")).queue();
            case "King" ->
                    buttonEvent.editMessageEmbeds(getLearnChessCard("King").build()).setActionRow(Button.primary("Queen", "⬅️"), Button.primary("King-castle", "Castle ♚")).queue();
            case "King-castle" ->
                    buttonEvent.editMessageEmbeds(getLearnChessCard("King-castle").build()).setActionRow(Button.primary("king", "⬅️"), Button.primary("Pawn", "♟️")).queue();
            case "Pawn" ->
                    buttonEvent.editMessageEmbeds(getLearnChessCard("Pawn").build()).setActionRow(Button.primary("King-castle", "⬅️"), Button.primary("Pawn-en", "En-passant ♟️")).queue();
            case "Pawn-en" ->
                    buttonEvent.editMessageEmbeds(getLearnChessCard("Pawn-en").build()).setActionRow(Button.primary("Pawn-en", "⬅️"), Button.primary("Pawn-pro", "Promotion ♟️")).queue();
            case "Pawn-pro" ->
                    buttonEvent.editMessageEmbeds(getLearnChessCard("Pawn-pro").build()).setActionRow(Button.primary("Pawn-pro", "All set for chess!").asDisabled()).queue();

        }

    }

    private void buildButtonInputForm() {
        TextInput ptext = TextInput.create("self-user", "Input Your Lichess Username", TextInputStyle.SHORT)
                .setPlaceholder("Input Your Lichess Username")
                .setMinLength(2)
                .setMaxLength(100)
                .setRequired(true)
                .build();

        TextInput targettext = TextInput.create("self-user" + "tar-user", "Enter Your Friend Lichess Username", TextInputStyle.SHORT)
                .setPlaceholder("Enter Your Friend Lichess Username")
                .setMinLength(2)
                .setMaxLength(100)
                .setRequired(true)
                .build();
        Modal pmodal = Modal.create("modal-self-user", "Challenge Friend ")
                .addComponents(ActionRow.of(ptext), ActionRow.of(targettext))
                .build();
        buttonEvent.replyModal(pmodal).queue();
    }

    
    public void sendSelfUserInputForm() {
        buildButtonInputForm();
    }

  
    public static EmbedBuilder getLearnChessCard(String searchKey) {
        for (int i = 0; i < LEARN_CHESS.length; i++) {
            for (int j = 0; j < LEARN_CHESS[i].length; i++) {
                if (LEARN_CHESS[i][0].equalsIgnoreCase(searchKey)) {
                    return new EmbedBuilder().setDescription(LEARN_CHESS[i][1]).setImage(LEARN_CHESS[i][2]).setColor(Color.BLUE).setThumbnail(Thumbnail.getChessliseLogo());
                }
            }
        }

        return null;
    }

    
    public EmbedBuilder getPlayCommandInfo() {
        EmbedBuilder help = new EmbedBuilder();
        help.setThumbnail(Thumbnail.getChessliseLogo());
        help.setTitle("Guide for /play");
        help.setDescription("""
                /play allows you to play LIVE chess with friends and BOTs!, to set up a **Casual game (friendly)/ Rated (gain/lose rating)**  all users need to do is click on **casual/rated** button
                After you will be prompted to select **Time control**, this option is timecontrol (how long game lasts).\s
                **Start the game**: One you have selected mode and time, Bot sends Lichess live URL, where you and your friend can click same time to start a **LIVE Chess game**.
                
                 **Login/Register**\s
                 To play rated make sure to Login/Register on Lichess.org to get chess rating, otherwise just play casual games!
                
                 **Play BOTS**\s
                
                 To play BOTS click on **Play BOTS**, to play live computer click on **Stockfish** to play BOTS on Lichess click on other options!
                
                 **Challenge Friend**\s
                 play your friend by entering your and your friend's Lichess user, ready for challenge? The time control is randomly generated for fun games!
                 **Need more help?**\s
                 Join our Support server and Developer will help you!""");
        return help;
    }

   

    public EmbedBuilder getCSSNHelpInfo(){
        EmbedBuilder help = new EmbedBuilder();
        help.setThumbnail(Thumbnail.getChessliseLogo());
        help.setTitle("Guide for playing in your network CSSN");
        help.setDescription("""
                You can find new chess friends and find chess challenges with CSSN! 
                
                to get started run **/connect** you would be asked your playing preferences and friend preferences, after connecting you can run 
                
                **/seekchallenge** to post the challenge in global network to pair run **/pairchallenge**, you can also play within your mutual friends network (once you have enough friends)
                
                by running **/pairchallengenetwork** to find like minded chess friends run **/findfriend** to view status of challenges and friendships 
                
                run **/mychallenges** and **/viewfriends** you can send friend request from paired from challenges or from finding a friend by running **/sendfriendrequest** <Username>
                
                The network just opened up so having you join CSSN will be a pleasure! 😊 Please join and find various new chess friends/challenges in one place!
                
                you can see the full guide [here](https://lichess.org/@/Noobmasterplayer123/blog/the-chesslise-social-server-network-cssn/iirHzk9R)
                
                got stuck? No worries let our developer explain to you! 
                [Join Chesslise Server](https://discord.gg/d2EHaw27hn)
                """);
        return help;
    }



   
    public EmbedBuilder getChessDBEdited(String moveDesc, String fen, String move){
        ChessUtil chessUtil = new ChessUtil();
        EmbedBuilder builder = new EmbedBuilder();
        builder.setImage(chessUtil.getChessDBImage(fen, setting.getBoardTheme(), setting.getPieceType(), move));
        builder.setTitle("ChessDB CN Analysis");
        builder.setDescription(moveDesc);
        builder.addField("fen", fen, true);
        builder.setFooter("Analysis by ChessDB CN see more here https://chessdb.cn/cloudbookc_info_en.html");

        return builder;
    }


    @Override
    public void trigger(String commandName) {
        sendLearnCommand();

        sendPuzzleButtons();

        deleteCurrentMessage();

        sendPlayCommandUI();

        sendPlayCommandFlow();

        sendMoreTimeControls();

        sendPlayingEngineFlow();

        sendChessDBButtonView();

        sendPuzzleDBTheme();

       sendFlipBoard();
    }
}
