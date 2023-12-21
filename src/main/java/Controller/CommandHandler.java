import chariot.Client;
import com.github.bhlangonijr.chesslib.Board;
import lombok.SneakyThrows;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.MessageContextInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.components.selections.StringSelectMenu;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import net.dv8tion.jda.api.interactions.modals.Modal;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class CommandHandler extends ListenerAdapter {

    private final AntiSpam spam = new AntiSpam(300000, 1);
    private final AntiSpam dailySpam = new AntiSpam(86400000, 1);
    private final AntiSpam watchLimit = new AntiSpam(86400000, 24);
    private String ButtonUserId;
    private Board board = new Board();
    private Client ButtonClient;
    private static final Client client = Client.basic(conf -> conf.retries(0));

    public CommandHandler(){

    }

    @Override
    public void onMessageContextInteraction(MessageContextInteractionEvent event) {

        switch (event.getName()) {
            case "Lichess Daily Puzzle" -> {
                DailyCommand dailyCommand = new DailyCommand(client);
                event.replyEmbeds(new EmbedBuilder().setColor(Color.cyan).setTitle("Daily Puzzle").setImage(dailyCommand.getPuzzle()).build()).addActionRow(net.dv8tion.jda.api.interactions.components.buttons.Button.link(dailyCommand.getPuzzleURL(), dailyCommand.getPuzzleSideToMove() + "| " + "Rating: " + dailyCommand.getRating()), net.dv8tion.jda.api.interactions.components.buttons.Button.primary("sol", "Solution"), net.dv8tion.jda.api.interactions.components.buttons.Button.success("hint", "Hint")).queue();
            }
            case "Play Chess" -> {
                event.reply("**⚔️ Please Pick Your Game's Mode **" + "\n\n").addActionRow(
                        net.dv8tion.jda.api.interactions.components.buttons.Button.success("casmode", "Casual"), net.dv8tion.jda.api.interactions.components.buttons.Button.danger("ratedmode", "Rated"), net.dv8tion.jda.api.interactions.components.buttons.Button.primary("enginemode", "Play BOT"), net.dv8tion.jda.api.interactions.components.buttons.Button.link("https://lichess.org/login", "Login/Register"), net.dv8tion.jda.api.interactions.components.buttons.Button.secondary("playhelp", "❓ Help")).queue();
            }
            case "View Lichess Broadcasts" -> {
                BroadcastLichess broadcast = new BroadcastLichess(client);
                event.replyEmbeds(broadcast.getBroadData().build()).queue();
            }
            case "Watch GMs" -> {
                WatchMaster watchMaster = new WatchMaster(client);
                event.deferReply(true).queue();
                event.getChannel().sendMessage(watchMaster.getMasterGames()).addActionRow(Button.link("https://lichess.org" + watchMaster.getGameId()[1], "Analyze")).queue();
            }
            case "Chess.com Daily Puzzle" -> {
                DailyCommandCC daily = new DailyCommandCC();
                event.deferReply(true).queue();
                event.getChannel().sendMessageEmbeds(new EmbedBuilder().setColor(Color.magenta).setTitle("Chess.com Daily Puzzle").setImage(daily.getPuzzle()).setFooter("Click on URL for solution").build()).addActionRow(Button.link(daily.getPuzzleURL(), daily.getPuzzleSideToMove())).queue();
            }
        }

    }


    @SneakyThrows
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event){
        String name = event.getName();

        this.ButtonClient = client;


        switch (name) {
            case "answer" -> {
                DailyCommand puzzleC = new DailyCommand(client);
                if (puzzleC.checkSolution(event.getOption("daily-puzzle-answer").getAsString())) {
                    event.reply("You have solved the daily puzzle!").queue();
                } else {
                    event.reply("You failed solving daily puzzle try again!").queue();
                }
            }
            case "resetboard" -> {
                this.board.loadFromFen(new Board().getFen());
                event.reply("board is reset!").queue();
            }
            case "move" -> {
                try {

                    LiseChessEngine engine = new LiseChessEngine(this.board);
                    String makemove = event.getOption("play-move").getAsString();
                    EmbedBuilder embedBuilder = new EmbedBuilder();

                    if (engine.gameOver()) {
                        event.reply("game over!").queue();
                        engine.resetBoard();
                        this.board = new Board();
                    }

                    this.board.doMove(makemove);

                    engine.playDiscordBotMoves();
                    embedBuilder.setColor(Color.green);
                    embedBuilder.setImage(engine.getImageOfCurrentBoard());
                    embedBuilder.setFooter("White to move");
                    event.getChannel().sendMessageEmbeds(embedBuilder.build()).queue();
                    event.replyEmbeds(embedBuilder.build()).addActionRow(Button.danger("bot-lose", "Resign"), Button.secondary("bot-draw", "Draw")).setEphemeral(true).queue();


                } catch (Exception e) {

                    event.reply("Not valid move! \n\n **If you are trying to castle use Captial letters (O-O & O-O-O)** \n\n (If you are running this command first time) The game is already on in other server, please reset the board with **/resetboard**! If you would like a to save your game, please join supprot server with **/invite**").setEphemeral(true).queue();

                }
            }
            case "suggest" -> {
                String sug = event.getOption("suggestid").getAsString();
                event.reply("thanks for feedback! Developer will look into it!").queue();
            }
            case "community" -> {
                EmbedBuilder embedBuilder = new EmbedBuilder();
                embedBuilder.setThumbnail("https://static-00.iconduck.com/assets.00/lichess-icon-512x512-q0oh5bwk.png");
                embedBuilder.setTitle("Best Chess Community To Learn/Play Chess");
                embedBuilder.setColor(Color.blue);
                embedBuilder.setDescription("**lichess.org**  [**Join**](https://discord.gg/lichess)" + "\n\n **Chess.com** [**Join**](https://discord.gg/chesscom)" +
                        "\n\n **The Pawn Zone**  [**Join**](https://discord.gg/6aKNP3t) \n\n **The Moon Club** [**Join**](https://discord.gg/hK8Ru57SKd)");
                event.replyEmbeds(embedBuilder.build()).queue();
            }
            case "broadcast" -> {
                BroadcastLichess broadcast = new BroadcastLichess(client);
                event.replyEmbeds(broadcast.getBroadData().build()).queue();
            }
            case "dailypuzzlecc" -> {
                DailyCommandCC daily = new DailyCommandCC();
                event.deferReply(true).queue();
                event.getChannel().sendMessageEmbeds(new EmbedBuilder().setColor(Color.magenta).setTitle("Chess.com Daily Puzzle").setImage(daily.getPuzzle()).setFooter("Click on URL for solution").build()).addActionRow(Button.link(daily.getPuzzleURL(), daily.getPuzzleSideToMove())).queue();
            }
            case "service" -> {
                EmbedBuilder embedBuildertos = new EmbedBuilder();
                embedBuildertos.setColor(Color.blue);
                embedBuildertos.setTitle("Terms Of Service And Privacy Policy");
                embedBuildertos.setThumbnail("https://static-00.iconduck.com/assets.00/lichess-icon-512x512-q0oh5bwk.png");
                embedBuildertos.setDescription("What is LISEBOT Terms Of Service?\n" +
                        "\n" +
                        "User agrees that they will have to use latest updated versions of LISEBOT, User also agrees that some commands may be deleted if developer does not want to maintain those commands in future. User is fully responsible for their discord server and LISEBOT does not have any access to the server information/ management. User also agrees to privacy policy which states that LISEBOT does not and will not store any private information \n\n What information does LISEBOT store about me? What is the privacy policy?\n" +
                        "\n" +
                        "LISEBOT Does not and will not store any private user information, all bot commands are Lichess related so auth commands need Lichess token to operate for those commands (which the bot does not store)");
                event.replyEmbeds(embedBuildertos.build()).queue();
            }

            case "watchmaster" -> {
                WatchMaster watchMaster = new WatchMaster(client);
                event.deferReply(true).queue();
                event.getChannel().sendMessage(watchMaster.getMasterGames()).addActionRow(Button.link("https://lichess.org" + watchMaster.getGameId()[1], "Analyze")).queue();
            }
            case "puzzle" -> {
                if (this.spam.checkSpam(event)) {
                    event.reply("Only 1 puzzle request within 5 mins! Please take your time to solve the puzzle!").setEphemeral(true).queue();
                } else {

                    try {
                        puzzle puzzle = new puzzle();
                        event.reply("Generating Puzzle..").setEphemeral(false).queue();
                        event.getChannel().sendMessageEmbeds(new EmbedBuilder().setColor(Color.green).setTitle("Chess.com Random Puzzle").setImage(puzzle.getPuzzle()).setFooter("Click on URL for solution").build()).addActionRow(Button.link(puzzle.getPuzzleURL(), puzzle.getPuzzleSideToMove())).queue();

                    } catch (Exception e) {
                        event.getChannel().sendMessage("An error occurred.. Please contact Dev, or wait for few mins to rerun the command").queue();
                    }

                }
            }

            case "help" -> {
                CommandInfo commandInfo = new CommandInfo();
                event.replyEmbeds(commandInfo.getPageOne().build()).addActionRow(Button.primary("next", "➡️"), Button.link("https://discord.gg/K2NKarM5KV", "Support Server")).queue();
            }
            case "playvariant" -> {
                event.reply("**⚔️ Please Pick Your Game's Mode ⚔️ **" + "\n\n").addActionRow(
                        Button.success("960", "⚙️ Chess 960"), Button.success("3c", "➕ 3 check"), Button.success("atomic", "\uD83D\uDCA3 Atomic")).queue();
            }
            case "play" -> {
                event.reply("**⚔️ Please Pick Your Game's Mode **" + "\n\n").addActionRow(
                        Button.success("casmode", "Casual"), Button.danger("ratedmode", "Rated"), Button.primary("enginemode", "Play BOT"), Button.link("https://lichess.org/login", "Login/Register"), Button.secondary("playhelp", "❓ Help")).queue();
            }
            case "profile" -> {
                TextInput ptext = TextInput.create("profileuser", "Input Lichess Username", TextInputStyle.SHORT)
                        .setPlaceholder("Input Lichess Username")
                        .setMinLength(2)
                        .setMaxLength(100)
                        .build();
                Modal pmodal = Modal.create("modalpro", "View Lichess Profiles!")
                        .addActionRows(ActionRow.of(ptext))
                        .build();
                event.replyModal(pmodal).queue();
            }
            case "profilecc" -> {
                TextInput ctext = TextInput.create("profileusercc", "Input Chess.com Username", TextInputStyle.SHORT)
                        .setPlaceholder("Input Chess.com Username")
                        .setMinLength(2)
                        .setMaxLength(100)
                        .build();
                Modal cmodal = Modal.create("modalproc", "View Chess.com Profiles!")
                        .addActionRows(ActionRow.of(ctext))
                        .build();
                event.replyModal(cmodal).queue();
            }
            case "streamers" -> {
                LiveStreamers liveStreamers = new LiveStreamers(client);
                event.replyEmbeds(liveStreamers.getTv().build()).queue();
            }
            case "dailypuzzle" -> {
                if (dailySpam.checkSpam(event)) {
                    event.reply("Check back after 24 hours for next daily puzzle!").setEphemeral(true).queue();
                } else {
                    DailyCommand dailyCommand = new DailyCommand(client);
                    event.replyEmbeds(new EmbedBuilder().setColor(Color.cyan).setTitle("Lichess Daily Puzzle").setImage(dailyCommand.getPuzzle()).setFooter("Use /answer to check your solution").build()).addActionRow(Button.link(dailyCommand.getPuzzleURL(), dailyCommand.getPuzzleSideToMove() + "| " + "Rating: " + dailyCommand.getRating()), Button.primary("sol", "View Solution"), Button.success("hint", "Hint")).queue();
                }
            }
            case "watch" -> {
                if (watchLimit.checkSpam(event)) {
                    event.reply("You have hit max limit! You can only send 24 games in 1 day, try again in 24 hours!").setEphemeral(true).queue();
                } else {

                    TextInput wtext = TextInput.create("watch_user_or_game", "Input Lichess Username Or Game", TextInputStyle.SHORT)
                            .setPlaceholder("Input Lichess Username Or Game")
                            .setMinLength(2)
                            .setMaxLength(100)
                            .setRequired(true)

                            .build();
                    Modal wmodal = Modal.create("modalwatch", "Watch Live Or Recent Lichess Games!")
                            .addActionRows(ActionRow.of(wtext))
                            .build();
                    event.replyModal(wmodal).queue();

                }
            }
            case "arena" -> {
                String arenaLink = event.getOption("arenaid").getAsString().trim();
                UserArena userArena = new UserArena(client, arenaLink);
                event.deferReply(true).queue();
                event.getChannel().sendMessageEmbeds(userArena.getUserArena().build()).queue();
            }
            case "invite" -> {
                InviteMe inviteMe = new InviteMe();
                event.replyEmbeds(inviteMe.getInviteInfo().build()).queue();
            }

        }


    }

    private boolean isLink(String link){
        return link.contains("/") && link.contains(".");
    }
    private String getValidGameId(String link){
        String[] spliter = link.split("/");
        String validGameId = "";

        if(spliter.length<=3)
            return null;

        if(spliter[3].length() == 12){
            validGameId += spliter[3].substring(0, spliter[3].length() - 4);
        }else{
            validGameId += spliter[3];
        }

        if(!(link.contains("https://lichess.org/") && client.games().byGameId(validGameId).isPresent())){
            return null;
        }

        return validGameId;
    }


    @Override
    public void onModalInteraction(@NotNull ModalInteractionEvent event) {
        Client client = Client.basic();
        String name = event.getModalId();

        switch (name) {
            case "modalwatch" -> {
                String userInput = event.getValue("watch_user_or_game").getAsString().trim();
                if (isLink(userInput)) {
                    String validGameId = getValidGameId(userInput);
                    if (validGameId != null) {
                        String gameGif = "https://lichess1.org/game/export/gif/" + validGameId + ".gif?theme=blue&piece=kosal";
                        event.reply(gameGif).queue();
                    } else {
                        event.reply("Please provide a valid Lichess game!").queue();
                    }
                } else if (client.users().byId(userInput).isPresent()) {
                    UserGame userGame = new UserGame(client, userInput);
                    String link = "https://lichess.org/@/" + userInput.toLowerCase() + "/all";
                    event.deferReply(true).queue();
                    event.getChannel().sendMessage(userGame.getUserGames()).queue();

                } else {
                    event.reply("Please Provide A Valid Lichess Username!").queue();
                }
            }
            case "modalpro" -> {
                String userID = event.getValue("profileuser").getAsString().trim();
                this.ButtonUserId = userID;
                UserProfile userProfile = new UserProfile(client, userID);
                event.deferReply(true).queue();
                event.getChannel().sendMessage(userProfile.getUserProfile()).addActionRow(Button.danger("userpuzzle", "\uD83C\uDF2A️"), Button.link("https://lichess.org/@/" + this.ButtonUserId, "View On Lichess")).queue();
            }
            case "modalproc" -> {
                String usercc = event.getValue("profileusercc").getAsString().trim();
                CCProfile ccProfile = new CCProfile(usercc);
                event.deferReply(true).queue();
                event.getChannel().sendMessageEmbeds(ccProfile.getCCProfile().build()).queue();
            }
            case "modal-3c" -> {
                try {


                    int min3c = Integer.parseInt(event.getValue("min-3c").getAsString());
                    int sec = Integer.parseInt(event.getValue("sec-3c").getAsString());
                    String bool = event.getValue("bool-3c").getAsString();
                    Game three = new Game();

                    if (bool.equalsIgnoreCase("y")) {
                        three.DifferentGameGenVar(min3c, sec, "r", 3);
                        event.reply(three.getRandom()).queue();
                    } else {
                        three.DifferentGameGenVar(min3c, sec, "c", 3);
                        event.reply(three.getRandom()).queue();
                    }


                } catch (Exception e) {
                    event.reply("error! Please provide valid parameters!").setEphemeral(true).queue();
                }
            }
            case "modal-9" -> {
                try {


                    int min9c = Integer.parseInt(event.getValue("min-9").getAsString());
                    int sec9 = Integer.parseInt(event.getValue("sec-9").getAsString());
                    String bool = event.getValue("bool-9").getAsString();
                    Game nine = new Game();

                    if (bool.equalsIgnoreCase("y")) {
                        nine.DifferentGameGenVar(min9c, sec9, "r", 9);
                        event.reply(nine.getRandom()).queue();
                    } else {
                        nine.DifferentGameGenVar(min9c, sec9, "c", 9);
                        event.reply(nine.getRandom()).queue();
                    }


                } catch (Exception e) {
                    event.reply("error! Please provide valid parameters!").setEphemeral(true).queue();
                }
            }
            case "modal-a" -> {
                try {


                    int min2c = Integer.parseInt(event.getValue("min-a").getAsString());
                    int sec2 = Integer.parseInt(event.getValue("sec-a").getAsString());
                    String bool = event.getValue("bool-a").getAsString();
                    Game two = new Game();

                    if (bool.equalsIgnoreCase("y")) {
                        two.DifferentGameGenVar(min2c, sec2, "r", 2);
                        event.reply(two.getRandom()).queue();
                    } else {
                        two.DifferentGameGenVar(min2c, sec2, "c", 2);
                        event.reply(two.getRandom()).queue();
                    }


                } catch (Exception e) {
                    event.reply("error! Please provide valid parameters!").setEphemeral(true).queue();
                }
            }
        }


    }



    @Override
    public void onButtonInteraction(@NotNull ButtonInteractionEvent event) {

        Client client = Client.basic();


        WatchMaster watchMaster = new WatchMaster(client);
        CommandInfo commandInfo = new CommandInfo();
        DailyCommand dailyCommand = new DailyCommand(client);

        switch (event.getComponentId()){
            case "bot-lose":
                this.board.loadFromFen(new Board().getFen());
                event.editMessageEmbeds(new EmbedBuilder().setDescription("You have resigned the game!").setColor(Color.red).build()).setActionRow(Button.danger("bot-lose", "Resgin").asDisabled(), Button.secondary("bot-draw", "Draw").asDisabled()).queue();
                break;
            case "bot-draw":
                this.board.loadFromFen(new Board().getFen());
                event.editMessageEmbeds(new EmbedBuilder().setDescription("Lise accepts the draw!").setColor(Color.yellow).build()).setActionRow(Button.danger("bot-lose", "Resgin").asDisabled(), Button.secondary("bot-draw", "Draw").asDisabled()).queue();
                break;
        }

        switch (event.getComponentId()){
            case "3c":
                TextInput wtext = TextInput.create("min-3c", "Input Time Mins [1 to 10]", TextInputStyle.SHORT)
                        .setPlaceholder("Input Time Mins [1 to 10]")
                        .setMinLength(1)
                        .setMaxLength(2)
                        .setRequired(true)
                        .build();
                TextInput stext = TextInput.create("sec-3c", "Input Time secs [1 to 10]", TextInputStyle.SHORT)
                        .setPlaceholder("Input Time secs [1 to 10]")
                        .setMinLength(1)
                        .setMaxLength(2)
                        .setRequired(true)
                        .build();
                TextInput btext = TextInput.create("bool-3c", "is Rated? [Y or N]", TextInputStyle.SHORT)
                        .setPlaceholder("is Rated? [Y or N]")
                        .setMinLength(1)
                        .setMaxLength(2)
                        .setRequired(true)
                        .build();

                Modal wmodal = Modal.create("modal-3c", "Create 3 check games")
                        .addActionRows(ActionRow.of(wtext))
                        .addActionRows(ActionRow.of(stext))
                        .addActionRows(ActionRow.of(btext))
                        .build();
                event.replyModal(wmodal).queue();
                break;

            case "atomic":
                TextInput awtext = TextInput.create("min-a", "Input Time Mins [1 to 10]", TextInputStyle.SHORT)
                        .setPlaceholder("Input Time Mins [1 to 10]")
                        .setMinLength(1)
                        .setMaxLength(2)
                        .setRequired(true)
                        .build();
                TextInput astext = TextInput.create("sec-a", "Input Time secs [1 to 10]", TextInputStyle.SHORT)
                        .setPlaceholder("Input Time secs [1 to 10]")
                        .setMinLength(1)
                        .setMaxLength(2)
                        .setRequired(true)
                        .build();
                TextInput abtext = TextInput.create("bool-a", "is Rated? [Y or N]", TextInputStyle.SHORT)
                        .setPlaceholder("is Rated? [Y or N]")
                        .setMinLength(1)
                        .setMaxLength(2)
                        .setRequired(true)
                        .build();

                Modal amodal = Modal.create("modal-a", "Create Atomic Games")
                        .addActionRows(ActionRow.of(awtext))
                        .addActionRows(ActionRow.of(astext))
                        .addActionRows(ActionRow.of(abtext))
                        .build();
                event.replyModal(amodal).queue();
                break;

            case "960":
                TextInput ninewtext = TextInput.create("min-9", "Input Time Mins [1 to 10]", TextInputStyle.SHORT)
                        .setPlaceholder("Input Time Mins [1 to 10]")
                        .setMinLength(1)
                        .setMaxLength(2)
                        .setRequired(true)
                        .build();
                TextInput ninestext = TextInput.create("sec-9", "Input Time secs [1 to 10]", TextInputStyle.SHORT)
                        .setPlaceholder("Input Time secs [1 to 10]")
                        .setMinLength(1)
                        .setMaxLength(2)
                        .setRequired(true)
                        .build();
                TextInput ninebtext = TextInput.create("bool-9", "is Rated? [Y or N]", TextInputStyle.SHORT)
                        .setPlaceholder("is Rated? [Y or N]")
                        .setMinLength(1)
                        .setMaxLength(2)
                        .setRequired(true)
                        .build();

                Modal nmodal = Modal.create("modal-9", "Create Chess960 Games")
                        .addActionRows(ActionRow.of(ninewtext))
                        .addActionRows(ActionRow.of(ninestext))
                        .addActionRows(ActionRow.of(ninebtext))
                        .build();
                event.replyModal(nmodal).queue();
                break;
        }


        if(event.getComponentId().equals("playhelp")){
            EmbedBuilder help = new EmbedBuilder();
            help.setThumbnail("https://static-00.iconduck.com/assets.00/lichess-icon-512x512-q0oh5bwk.png");
            help.setTitle("Guide for /play");
            help.setDescription("/play allows you to play LIVE chess with friends and BOTs!, to set up a **Casual game (friendly)/ Rated (gain/lose rating)**  all users need to do is click on **casual/rated** button\n" +
                    "After you will be prompted to select **Time control**, this option is timecontrol (how long game lasts). \n" +
                    "**Start the game**: One you have selected mode and time, Bot sends Lichess live URL, where you and your friend can click same time to start a **LIVE Chess game**." +
                    "\n\n **Login/Register** \n To play rated make sure to Login/Register on Lichess.org to get chess rating, otherwise just play casual games!"+
                    "\n **Play BOTS** " +
                    "\n To play BOTS click on **Play BOTS**, to play live computer click on **Stockfish** to play BOTS on Lichess click on other options!" +
                    "\n **Need more help?** \n Join our Support server and Developer will help you!");
            event.replyEmbeds(help.build()).addActionRow(Button.link("https://discord.gg/K2NKarM5KV", "Support Server")).setEphemeral(true).queue();
        }


        if(event.getComponentId().equals("casmode"))   {
            event.editMessage("**⏱️ Please Pick Your Time Control ⏱️**").setActionRow(
                    Button.primary("ultrafastc", "1/4+0"),
                    Button.primary("bulletfastc", "1+0"),
                    Button.primary("blitzfastc", "3+2"),
                    Button.primary("rapidfastc", "5+5"),
                    Button.success("loadc", "\uD83D\uDD04 Load More Time Controls")
            ).queue();

        }

        if(event.getComponentId().equals("ratedmode")){
            event.editMessage("**⏱️ Please Pick Your Time Control ⏱️**").setActionRow(
                    Button.danger("ultrafastr", "1/4+0"),
                    Button.danger("bulletfastr", "1+0"),
                    Button.danger("blitzfastr", "3+2"),
                    Button.danger("rapidfastr", "5+5"),
                    Button.success("loadr", "\uD83D\uDD04 Load More Time Controls")
            ).queue();

        }

        if(event.getComponentId().equals("enginemode")){
            event.editMessage("** Challenge This BOTs! **").setActionRow(
                    Button.link("https://listudy.org/en/play-stockfish", "Stockfish"),
                    Button.link("https://lichess.org/@/leela2200", "LeelaZero"),
                    Button.link("https://lichess.org/@/Dummyette", "Dummyette"),
                    Button.link("https://lichess.org/@/SimplerEval", "SimplerEval")

            ).queue();
        }

        switch (event.getComponentId()){
            case "loadr":
                event.editMessage("**⏱️ Please Pick Your Time Control ⏱️**").setActionRow(Button.danger("3+0r", "3+0")
                        , Button.danger("5+0r", "5+0")
                        , Button.danger("10+0r", "10+0")).queue();
                break;
            case "loadc":
                event.editMessage("**⏱️ Please Pick Your Time Control ⏱️**").setActionRow(Button.primary("3+0c", "3+0")
                        , Button.primary("5+0c", "5+0")
                        , Button.primary("10+0c", "10+0")).queue();
                break;
            case "3+0r":
                Game threeP0 = new Game();
                threeP0.DifferentGameGen(3,0,"r");
                event.editMessage(threeP0.getRandom()).setActionRow(Button.primary("3+0r", "Rematch")).queue();

                break;
            case "5+0r":
                Game fP0 = new Game();
                fP0.DifferentGameGen(5,0,"r");
                event.editMessage(fP0.getRandom()).setActionRow(Button.primary("5+0r", "Rematch")).queue();

                break;
            case "10+0r":
                Game tP0 = new Game();
                tP0.DifferentGameGen(10,0,"r");

                event.editMessage(tP0.getRandom()).setActionRow(Button.primary("10+0r", "Rematch")).queue();

                break;
            case "3+0c":
                Game threeP0c = new Game();
                threeP0c.DifferentGameGen(3,0,"c");

                event.editMessage(threeP0c.getRandom()).setActionRow(Button.primary("3+0c", "Rematch")).queue();

                break;
            case "5+0c":
                Game fP0c = new Game();
                fP0c.DifferentGameGen(5,0,"c");

                event.editMessage(fP0c.getRandom()).setActionRow(Button.primary("5+0c", "Rematch")).queue();

                break;
            case "10+0c":
                Game tP0c = new Game();
                tP0c.DifferentGameGen(10,0,"c");

                event.editMessage(tP0c.getRandom()).setActionRow(Button.primary("10+0c", "Rematch")).queue();

                break;
        }

        switch (event.getComponentId()){
            case "ultrafastc":
                Game ug = new Game();
                ug.DifferentGameGen(0,0,"c");
                event.editMessage(ug.getRandom()).setActionRow(Button.primary("ultrafastc", "Rematch")).queue();
            case "bulletfastc":
                Game bg = new Game();
                bg.DifferentGameGen(1, 0, "c");
                event.editMessage(bg.getRandom()).setActionRow(Button.primary("bulletfastc", "Rematch")).queue();

                break;
            case "blitzfastc":
                Game bcg = new Game();
                bcg.DifferentGameGen(3,2,"c");
                event.editMessage(bcg.getRandom()).setActionRow(Button.primary("blitzfastc", "Rematch")).queue();
                break;
            case "rapidfastc":
                Game rg = new Game();
                rg.DifferentGameGen(5,5, "c");
                event.editMessage(rg.getRandom()).setActionRow(Button.primary("rapidfastc", "Rematch")).queue();
                break;

        }

        switch (event.getComponentId()){
            case "ultrafastr":
                Game ug = new Game();
                ug.DifferentGameGen(0,0,"r");
                event.editMessage(ug.getRandom()).setActionRow(Button.primary("ultrafastr", "Rematch")).queue();
            case "bulletfastr":
                Game bg = new Game();
                bg.DifferentGameGen(1, 0, "r");
                event.editMessage(bg.getRandom()).setActionRow(Button.primary("bulletfastr", "Rematch")).queue();
                break;
            case "blitzfastr":
                Game bcg = new Game();
                bcg.DifferentGameGen(3,2,"r");
                event.editMessage(bcg.getRandom()).setActionRow(Button.primary("blitzfastr", "Rematch")).queue();
                break;
            case "rapidfastr":
                Game rg = new Game();
                rg.DifferentGameGen(5,5,"r");
                event.editMessage(rg.getRandom()).setActionRow(Button.primary("rapidfastr", "Rematch")).queue();
                break;

        }


        if(event.getComponentId().equals("userpuzzle")){
            UserDashboard userDashboard = new UserDashboard(this.ButtonClient, this.ButtonUserId);
            event.editMessageEmbeds(userDashboard.getUserDashboard().build()).setActionRow(Button.danger("userpuzzle","\uD83C\uDF2A️").asDisabled(), Button.primary("userstreaming", "\uD83C\uDF99️").asDisabled()).queue();
        }


        if(event.getComponentId().equals("next")){
            event.editMessageEmbeds(commandInfo.getPageTwo().build()).setActionRow(Button.primary("nexttwo", "➡️"), Button.link("https://discord.gg/K2NKarM5KV", "Support Server")).queue();
        }else if(event.getComponentId().equals("nexttwo")){
            event.editMessageEmbeds(commandInfo.getPageThree().build()).setActionRow(Button.primary("nextthree", "➡️").asDisabled(), Button.link("https://discord.gg/K2NKarM5KV", "Support Server")).queue();
        }

        if(event.getComponentId().equals("sol")){
            event.reply(dailyCommand.getSolution()).setEphemeral(true).queue();
        }



        if(event.getComponentId().equals("hint")){
            event.replyEmbeds(dailyCommand.getThemes().build()).setEphemeral(true).queue();
        }

    }







}
