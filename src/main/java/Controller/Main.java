
import chariot.Client;
import chariot.model.Event;
import chariot.model.GameEvent;
import com.github.bhlangonijr.chesslib.*;
import com.github.bhlangonijr.chesslib.move.Move;
import lombok.SneakyThrows;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;


import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.components.selections.StringSelectMenu;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import net.dv8tion.jda.api.interactions.modals.Modal;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;
import org.jetbrains.annotations.NotNull;
import java.awt.*;


public class Main extends ListenerAdapter {

    private static JDABuilder jdaBuilder;
    public static boolean boardOriginal = false;
    private static JDA jda;
    private AntiSpam spam = new AntiSpam(300000, 1);
    private AntiSpam dailySpam = new AntiSpam(86400000, 1);

    private AntiSpam watchLimit = new AntiSpam(86400000, 24);
    private String ButtonUserId;
    private long currentTime = System.currentTimeMillis();


    private Board board = new Board();
    private Client ButtonClient;
    private String APIPASSWORD;
    private static Client client = Client.basic(conf -> conf.retries(0));



    public static void main(String[] args) {


        jdaBuilder = JDABuilder.createDefault(System.getenv("discord_bot_token"));

        jdaBuilder.setStatus(OnlineStatus.ONLINE);

        jdaBuilder.setActivity(Activity.playing("/help"));

        jdaBuilder.addEventListeners(new Main());


        try {
            jda = jdaBuilder.build();

        } catch (Exception exception) {
            exception.printStackTrace();
        }

        CommandListUpdateAction commands = jda.updateCommands();
        commands.addCommands(Commands.slash("watchgame", "watch Lichess game for game link").addOption(OptionType.STRING, "lichess-game-link", "Input Lichess link", true));
        commands.addCommands(Commands.slash("dailypuzzlecc", "do chess.com daily puzzles"));
        commands.addCommands(Commands.slash("community", "Best chess Discord communities!"));
        commands.addCommands(Commands.slash("broadcast", "View Latest OTB/Online Master tournament"));
        commands.addCommands(Commands.slash("profilecc", "view cc profiles"));
        commands.addCommands(Commands.slash("service", "Read our Terms of Service"));
        commands.addCommands(Commands.slash("openingdb", "View Chess Openings"));
        commands.addCommands(Commands.slash("dailypuzzle", "Daily Lichess Puzzles"));
        commands.addCommands(Commands.slash("arena", "See Swiss/Arena standings").addOption(OptionType.STRING, "arenaid", "Input Lichess arena link", true));
        commands.addCommands(Commands.slash("watchmaster", "Watch GM Games in gif"));
        commands.addCommands(Commands.slash("profile", "See Lichess Profile of given user"));
        commands.addCommands(Commands.slash("streamers", "See current Live Streamers"));
        commands.addCommands(Commands.slash("puzzle", "do puzzles"));
        commands.addCommands(Commands.slash("tourney", "Join Current Lichess Tournaments"));
        commands.addCommands(Commands.slash("help", "View LISEBOT Commands"));
        commands.addCommands(Commands.slash("play", "Play Live Chess Games"));
        commands.addCommands(Commands.slash("blog", "Read Lichess Blogs"));
        commands.addCommands(Commands.slash("watch", "Watch Lichess games for given user"));
        commands.addCommands(Commands.slash("invite", "Invite me to your servers!"));
        commands.addCommands(Commands.slash("move", "make a move").addOption(OptionType.STRING, "play-move", "input chess move", true));
        commands.addCommands(Commands.slash("resetboard", "reset the board"));


        commands.queue();


        var client = Client.auth(System.getenv("lichess_bot_token"));
        var bot = client.bot();
        var events = bot.connect().stream();
        var username = client.account().profile().get().username().toLowerCase();


        events.forEach(event -> {


            switch (event.type()) {

                case challenge:

                
                    var challenge = (Event.ChallengeEvent) event;
                    boolean std = challenge.challenge().variant().name().equalsIgnoreCase("Standard");
                    boolean non_rated = challenge.challenge().rated();
                    boolean isCoores = challenge.challenge().speed().equalsIgnoreCase("correspondence");
                    if(std && !non_rated && !isCoores){
                        bot.acceptChallenge(event.id());
                    }else if(non_rated){
                        bot.declineChallenge(event.id(), provider -> provider.casual());

                    }else if(isCoores) {
                        bot.declineChallenge(event.id(), provider -> provider.timeControl());

                    }else{
                        bot.declineChallenge(event.id(), provider -> provider.variant());
                    }
                    break;


                case challengeDeclined:
                    bot.abort(event.id());

                    break;

                case challengeCanceled:
                    bot.abort(event.id());

                    break;

                case gameFinish:
                    bot.chat(event.id(), "Thanks for playing me! ggs", provider -> provider.player());
                    bot.chat(event.id(), "Thanks for watching!", provider -> provider.spectator());

                    break;


                case gameStart:

                    

                        var gameEvents = bot.connectToGame(event.id()).stream();
                        var board = new Board();
                        var engine = new LiseChessEngine(board);
                        boolean[] isWhite = {false};

                        bot.chat(event.id(), "omg you are very strong.. I'm scared but hey good luck!!", provider -> provider.player());


                        gameEvents.forEach(gameEvent -> {



                            switch (gameEvent.type()) {


                                case opponentGone:
                                    bot.abort(event.id());

                                    break;


                                case gameFull:

                                      try {

                                          isWhite[0] = ((GameEvent.Full) gameEvent).white().name().toLowerCase().equals(username);
                                          if (isWhite[0]) {
                                              try {


                                                      var move = engine.generateMoveFromIndexLookUp(board);
                                                      bot.move(event.id(), move.toString());
                                                      board.doMove(move);


                                              } catch (Exception e) {
                                                     bot.resign(event.id());
                                              }
                                          }

                                      }catch (Exception e){
                                          bot.resign(event.id());
                                          System.out.println(e.getMessage());
                                      }

                                    break;

                                case gameState:

                                    try {

                                        var names = ((GameEvent.State) gameEvent).moves().split(" ");
                                        var whiteTurn = names.length % 2 == 0;

                                        if (isWhite[0] == whiteTurn) {
                                            try {
                                                var name = names[names.length - 1];
                                                var from = Square.fromValue(name.substring(0, 2).toUpperCase());
                                                var to = Square.fromValue(name.substring(2, 4).toUpperCase());
                                                if (names.length == 5) {
                                                    var type = PieceType.fromSanSymbol(name.substring(4).toUpperCase());
                                                    var piece = Piece.make(whiteTurn ? Side.WHITE : Side.BLACK, type);
                                                    var move = new Move(from, to, piece);
                                                    board.doMove(move);
                                                } else {
                                                    var move = new Move(from, to);
                                                    board.doMove(move);
                                                }
                                            }catch (Exception e){
                                                bot.resign(event.id());
                                            }

                                                var move = engine.generateMoveFromIndexLookUp(board);
                                                bot.move(event.id(), move.toString());
                                                board.doMove(move);

                                        }

                                    }catch (Exception e){
                                        bot.resign(event.id());

                                    }
                                    break;
                            }
                        });



                    break;
            }
        });



    }



    @SneakyThrows
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event){
        String name = event.getName();

        this.ButtonClient = client;


        switch(name) {

            case "watchgame":

                String link = event.getOption("lichess-game-link").getAsString();

                if(!link.contains("https://lichess.org/")){
                    event.reply("Please provide a Lichess game URL!").queue();
                }

                String[] spliter = link.split("/");

                String validGameId = "";

                if(spliter[3].length() == 12){
                    validGameId += spliter[3].substring(0, spliter[3].length() - 4);
                }else{
                    validGameId += spliter[3];
                }

                System.out.println(validGameId);

                System.out.println(spliter[3]);

                if(link.contains("https://lichess.org/") && client.games().byGameId(validGameId).isPresent()){

                    String gameGif = "https://lichess1.org/game/export/gif/" + validGameId + ".gif?theme=blue&piece=kosal";
                    event.reply(gameGif).queue();
                }else{
                    event.reply("Please provide a valid Lichess game!").queue();
                }


                break;


            case "resetboard":

                this.board.loadFromFen(new Board().getFen());
                event.reply("board is reset!").queue();

             break;

            case "move":


                try{



                    LiseChessEngine engine = new LiseChessEngine(this.board);
                    String makemove = event.getOption("play-move").getAsString();


                    if(engine.gameOver()){
                        event.reply("game over!").queue();
                        engine.resetBoard();
                        this.board = new Board();
                    }



                    this.board.doMove(makemove);

                    event.reply("thinking..").setEphemeral(true).queue();
                    engine.playRandomLegalMoves();


                    event.getChannel().sendMessage(engine.getImageOfCurrentBoard()).queue();
                    event.getChannel().sendMessage("** Your Turn!** ").queue();



                }catch (Exception e){

                    event.reply("Not valid move! \n\n **If you are trying to castle use Captial letters (O-O & O-O-O)** \n\n (If you are running this command first time) The game is already on in other server, please reset the board with **/resetboard**! If you would like a to save your game, please join supprot server with **/invite**").setEphemeral(true).queue();

                }



                break;


    

            case "community":

                EmbedBuilder embedBuilder = new EmbedBuilder();
                embedBuilder.setThumbnail("https://static-00.iconduck.com/assets.00/lichess-icon-512x512-q0oh5bwk.png");
                embedBuilder.setTitle("Best Chess Community To Learn/Play Chess");
                embedBuilder.setColor(Color.blue);
                embedBuilder.setDescription("**lichess.org**  [**Join**](https://discord.gg/lichess)" + "\n\n **Chess.com** [**Join**](https://discord.gg/chesscom)" +
                        "\n\n **The Pawn Zone**  [**Join**](https://discord.gg/6aKNP3t) \n\n **The Moon Club** [**Join**](https://discord.gg/hK8Ru57SKd)");
                event.replyEmbeds(embedBuilder.build()).queue();

                break;

            case "broadcast":
                BroadcastLichess broadcast = new BroadcastLichess(client);
                event.replyEmbeds(broadcast.getBroadData().build()).queue();

                break;
            case "dailypuzzlecc":
                DailyCommandCC daily = new DailyCommandCC(boardOriginal);
                event.deferReply(true).queue();
                event.getChannel().sendMessage(daily.getpuzzleImg()).queue();

                break;
            case "service":
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
                break;
            case "openingdb":
                event.reply( "1️⃣ **Italian Opening ~ e4 e5 Nf3 Nf6 Bc4** \n\n 2️⃣ **Queen's Gambit ~ d4 d5 c4** \n\n 3️⃣ **English Opening ~ c4** \n\n 4️⃣ **Zukertort Opening ~ Nf3** \n\n 5️⃣ **Sicilian Defence ~ e4 c5** \n\n ").addActionRow(Button.primary("oneopening", "1️⃣"), Button.primary("twoopening", "2️⃣"), Button.primary("threeopening", "3️⃣"), Button.primary("fouropening", "4️⃣"), Button.primary("fiveopening", "5️⃣")).queue();
                break;
            case "watchmaster":
                WatchMaster watchMaster = new WatchMaster(client);
                event.deferReply(true).queue();
                event.getChannel().sendMessage(watchMaster.getMasterGames()).addActionRow(Button.link("https://lichess.org" + watchMaster.getGameId()[1], "Analyze")).queue();
                break;
            case "puzzle":

                    if(this.spam.checkSpam(event)){
                        event.reply("Only 1 puzzle request within 5 mins! Please take your time to solve the puzzle!").setEphemeral(true).queue();
                    }else {

                        try {
                            puzzle puzzle = new puzzle();
                            event.reply("Generating Puzzle..").setEphemeral(false).queue();
                            event.getChannel().sendMessage(puzzle.getRandom()).addActionRow(Button.link(puzzle.getSolLink(), puzzle.getMoveSay())).queue();
                        

                        } catch (Exception e) {
                            event.getChannel().sendMessage("An error occurred.. Please contact Dev, or wait for few mins to rerun the command").queue();
                        }

                    }

                break;
            case "tourney":
                currentTournament currentTournament = new currentTournament(client);
                event.replyEmbeds(currentTournament.getTournaments().build()).queue();
                break;
            case "help":
                CommandInfo commandInfo = new CommandInfo();
                event.getJDA().getGuildById("965333503367589968").getTextChannelById("1057353653926764554").sendMessage("I joined " + event.getGuild().getName() + " who have members of " + event.getGuild().getMemberCount() + " !" + "**HELP COMMAND RUN BY!" + event.getUser().getName()).queue();
                event.replyEmbeds(commandInfo.getPageOne().build()).addActionRow(Button.primary("next", "➡️"), Button.link("https://discord.gg/K2NKarM5KV", "Support Server")).queue();
                break;
          
            case "play":
                event.reply("**⚔️ Please Pick Your Game's Mode **" + "\n\n" ).addActionRow(
                        Button.success("casmode", "Casual"), Button.danger("ratedmode", "Rated"), Button.primary("enginemode", "Play BOT"),Button.link("https://lichess.org/login", "Login/Register"), Button.secondary("playhelp", "❓ Help")).queue();
                break;
            case "profile":
                TextInput ptext = TextInput.create("profileuser", "Input Lichess Username", TextInputStyle.SHORT)
                        .setPlaceholder("Input Lichess Username")
                        .setMinLength(2)
                        .setMaxLength(100)
                        .build();

                Modal pmodal = Modal.create("modalpro", "View Lichess Profiles!")
                        .addActionRows(ActionRow.of(ptext))
                        .build();
                event.replyModal(pmodal).queue();
                break;

            case "profilecc":
                TextInput ctext = TextInput.create("profileusercc", "Input Chess.com Username", TextInputStyle.SHORT)
                        .setPlaceholder("Input Chess.com Username")
                        .setMinLength(2)
                        .setMaxLength(100)
                        .build();

                Modal cmodal = Modal.create("modalproc", "View Chess.com Profiles!")
                        .addActionRows(ActionRow.of(ctext))
                        .build();
                event.replyModal(cmodal).queue();

                break;

            case "streamers":
                LiveStreamers liveStreamers = new LiveStreamers(client);
                event.replyEmbeds(liveStreamers.getTv().build()).queue();
                break;

            case "dailypuzzle":

                if(dailySpam.checkSpam(event)){
                    event.reply("Check back after 24 hours for next daily puzzle!").setEphemeral(true).queue();
                }else{
                    DailyCommand dailyCommand = new DailyCommand(client);
                    event.reply(dailyCommand.getPuzzleData()).addActionRow(Button.link(dailyCommand.getPuzzleLink(), dailyCommand.getMoveSay() + "| " + "Rating: " + dailyCommand.getRating()), Button.primary("sol", "Solution"), Button.success("hint", "Hint")).queue();
                }
                break;

            case "watch":

                if(watchLimit.checkSpam(event)){
                    event.reply("You have hit max limit! You can only send 24 games in 1 day, try again in 24 hours!").setEphemeral(true).queue();
                }else {

                    TextInput wtext = TextInput.create("watchuser", "Input Lichess Username", TextInputStyle.SHORT)
                            .setPlaceholder("Input Lichess Username")
                            .setMinLength(2)
                            .setMaxLength(100)
                            .setRequired(true)

                            .build();
                    Modal wmodal = Modal.create("modalwatch", "Watch Live Or Recent Lichess Games!")
                            .addActionRows(ActionRow.of(wtext))
                            .build();
                    event.replyModal(wmodal).queue();

                }
                break;



            case "arena":

                String arenaLink = event.getOption("arenaid").getAsString().trim();
                UserArena userArena = new UserArena(client,arenaLink);
                event.deferReply(true).queue();
                event.getChannel().sendMessageEmbeds(userArena.getUserArena().build()).queue();
                break;

            case "invite":
                InviteMe inviteMe = new InviteMe();
                event.replyEmbeds(inviteMe.getInviteInfo().build()).queue();
                break;
            case "blog":
                EmbedBuilder BlogEmbed = new EmbedBuilder();
                BlogEmbed.setColor(Color.orange);
                BlogEmbed.setTitle("Read Lichess Blogs");
                BlogEmbed.setThumbnail("https://www.pngitem.com/pimgs/m/17-174815_writing-hand-emoji-png-transparent-png.png");
                BlogEmbed.setDescription("Read Lichess Blogs by clicking on topics of your interest!");
                event.replyEmbeds(BlogEmbed.build()).addActionRow(Button.success("newblog", "Latest Lichess Blog"), Button.primary("comblog", "Community Blogs"), Button.primary("chessblog", "Chess Blogs")).queue();
                break;



        }


    }


    @Override
    public void onModalInteraction(@NotNull ModalInteractionEvent event) {
        Client client = Client.basic();
        String name = event.getModalId();

        switch (name){
            case "modalwatch":
                String gameUserID = event.getValue("watchuser").getAsString().trim();
                if(client.users().byId(gameUserID).isPresent()) {
                    UserGame userGame = new UserGame(client, gameUserID);
                    String link = "https://lichess.org/@/" + gameUserID.toLowerCase() + "/all";
                    event.deferReply(true).queue();
                    event.getChannel().sendMessage(userGame.getUserGames()).addActionRow(Button.link(link, "♟️" + userGame.getOpeningName()), Button.link(link, "\uD83C\uDFC6 " + userGame.getWinner()), Button.link(link, "⏰ " + userGame.getSpeed())
                    ).setActionRow(StringSelectMenu.create("watch-id").addOption("Master Games", "master-id").addOption("Opening Explorer", "op-id").build()).queue();

                }else{
                    event.reply("Please Provide A Valid Lichess Username!").queue();
                }
                break;

            case "modalpro":
                String userID = event.getValue("profileuser").getAsString().trim();
                this.ButtonUserId = userID;
                UserProfile userProfile = new UserProfile(client,userID);
                event.deferReply(true).queue();
                event.getChannel().sendMessage(userProfile.getUserProfile()).addActionRow(Button.danger("userpuzzle","\uD83C\uDF2A️"), Button.link("https://lichess.org/@/" + this.ButtonUserId, "View On Lichess")).queue();
                break;
            case "modalproc":

                String usercc = event.getValue("profileusercc").getAsString().trim();
                CCProfile ccProfile = new CCProfile(usercc);
                event.deferReply(true).queue();
                event.getChannel().sendMessageEmbeds(ccProfile.getCCProfile().build()).queue();

                break;


        }


    }


    @Override
    public void onStringSelectInteraction(StringSelectInteractionEvent event) {

        String name = event.getComponentId();
        String val = event.getValues().get(0);
        WatchMaster watchMaster = new WatchMaster(client);
       // ExploreResult.OpeningDB.
        switch (name){
            case "watch-id":

                switch (val){
                    case "master-id":
                        event.editMessage(watchMaster.getMasterGames()).queue();
                        break;
                    case "op-id":

                        event.editMessage("1️⃣ **Italian Opening ~ e4 e5 Nf3 Nf6 Bc4** \n\n 2️⃣ **Queen's Gambit ~ d4 d5 c4** \n\n 3️⃣ **English Opening ~ c4** \n\n 4️⃣ **Zukertort Opening ~ Nf3** \n\n 5️⃣ **Sicilian Defence ~ e4 c5** \n\n").setActionRow(Button.primary("oneopening", "1️⃣"), Button.primary("twoopening", "2️⃣"), Button.primary("threeopening", "3️⃣"), Button.primary("fouropening", "4️⃣"), Button.primary("fiveopening", "5️⃣")).queue();
                        break;

                }

                break;


        }

    }

    @Override
    public void onButtonInteraction(@NotNull  ButtonInteractionEvent event) {

        Client client = Client.basic();


        WatchMaster watchMaster = new WatchMaster(client);
        CommandInfo commandInfo = new CommandInfo();
        DailyCommand dailyCommand = new DailyCommand(client);



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
                Game ug = new Game(client, "ultrabullet", "casual");
                ug.getNewGame();

                event.editMessage(ug.getRandom()).setActionRow(Button.primary("ultrafastc", "Rematch")).queue();
            case "bulletfastc":
                Game bg = new Game(client, "bullet", "casual");
                bg.getNewGame();
                event.editMessage(bg.getRandom()).setActionRow(Button.primary("bulletfastc", "Rematch")).queue();


                break;
            case "blitzfastc":
                Game bcg = new Game(client, "blitz", "casual");
                bcg.getNewGame();
                event.editMessage(bcg.getRandom()).setActionRow(Button.primary("blitzfastc", "Rematch")).queue();

                break;
            case "rapidfastc":
                Game rg = new Game(client, "rapid", "casual");
                rg.getNewGame();
                event.editMessage(rg.getRandom()).setActionRow(Button.primary("rapidfastc", "Rematch")).queue();

                break;
            case "classicalfastc":
                Game cg = new Game(client, "classical", "casual");
                cg.getNewGame();

                event.editMessage(cg.getRandom()).setActionRow(Button.primary("classicalfastc", "Rematch")).queue();

                break;

        }

        switch (event.getComponentId()){
            case "ultrafastr":
                Game ug = new Game(client, "ultrabullet", "rated");
                ug.getNewGame();
                event.editMessage(ug.getRandom()).setActionRow(Button.primary("ultrafastr", "Rematch")).queue();
            case "bulletfastr":
                Game bg = new Game(client, "bullet", "rated");
                bg.getNewGame();
                event.editMessage(bg.getRandom()).setActionRow(Button.primary("bulletfastr", "Rematch")).queue();
                break;
            case "blitzfastr":
                Game bcg = new Game(client, "blitz", "rated");
                bcg.getNewGame();
                event.editMessage(bcg.getRandom()).setActionRow(Button.primary("blitzfastr", "Rematch")).queue();
                break;
            case "rapidfastr":
                Game rg = new Game(client, "rapid", "rated");
                rg.getNewGame();
                event.editMessage(rg.getRandom()).setActionRow(Button.primary("rapidfastr", "Rematch")).queue();
                break;
            case "classicalfastr":
                Game cg = new Game(client, "classical", "rated");
                cg.getNewGame();
                event.editMessage(cg.getRandom()).setActionRow(Button.primary("classicalfastr", "Rematch")).queue();
                break;

        }


        if(event.getComponentId().equals("openingdata")){
            event.editMessage("1️⃣ **Italian Opening ~ e4 e5 Nf3 Nf6 Bc4** \n\n 2️⃣ **Queen's Gambit ~ d4 d5 c4** \n\n 3️⃣ **English Opening ~ c4** \n\n 4️⃣ **Zukertort Opening ~ Nf3** \n\n 5️⃣ **Sicilian Defence ~ e4 c5** \n\n ").setActionRow(Button.primary("oneopening", "1️⃣"), Button.primary("twoopening", "2️⃣"), Button.primary("threeopening", "3️⃣"), Button.primary("fouropening", "4️⃣"), Button.primary("fiveopening", "5️⃣")).queue();

        }


        if(event.getComponentId().equals("userpuzzle")){
            UserDashboard userDashboard = new UserDashboard(this.ButtonClient, this.ButtonUserId);
            event.editMessageEmbeds(userDashboard.getUserDashboard().build()).setActionRow(Button.danger("userpuzzle","\uD83C\uDF2A️").asDisabled(), Button.primary("userstreaming", "\uD83C\uDF99️").asDisabled()).queue();
        }

        if(event.getComponentId().equals("userstreaming")){
            UserStreaming userStreaming = new UserStreaming(this.ButtonClient, this.ButtonUserId);
            event.editMessageEmbeds(userStreaming.getStreamingStatus().build()).setActionRow(Button.danger("userpuzzle","\uD83C\uDF2A️").asDisabled(), Button.primary("userstreaming", "\uD83C\uDF99️").asDisabled()).queue();
        }


        Blog Chessblog = new Blog("Chess");
        Blog CommunityBlog = new Blog("Community");

        if(event.getComponentId().equals("newblog")){
            event.reply(CommunityBlog.getLatestBlog()).setEphemeral(true).queue();
        }else if(event.getComponentId().equals("comblog")){
            event.reply(CommunityBlog.getCommunityBlogs()).setEphemeral(true).queue();
        }else if(event.getComponentId().equals("chessblog")){
            event.reply(Chessblog.getBlogsByTopic()).setEphemeral(true).queue();
        }

        if(event.getComponentId().equals("createone")){
            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder.setColor(Color.yellow);
            embedBuilder.setTitle("Select Your Arena's Mode");
            event.replyEmbeds(embedBuilder.build()).addActionRow(Button.primary("bulletarena", "Create Bullet Arena"), Button.primary("blitzarena", "Create Blitz Arena"), Button.primary("rapidarena", "Create Rapid Arena")).queue();
        }



        if(event.getComponentId().equals("next")){
            event.editMessageEmbeds(commandInfo.getPageTwo().build()).setActionRow(Button.primary("nexttwo", "➡️"), Button.link("https://discord.gg/K2NKarM5KV", "Support Server")).queue();
        }else if(event.getComponentId().equals("nexttwo")){
            event.editMessageEmbeds(commandInfo.getPageThree().build()).setActionRow(Button.primary("nextthree", "➡️").asDisabled(), Button.link("https://discord.gg/K2NKarM5KV", "Support Server")).queue();
        }

        if(event.getComponentId().equals("sol")){
            event.reply(dailyCommand.getSolution()).setEphemeral(true).queue();
        }

        if(event.getComponentId().equals("oneopening")){
            OpeningObject italian = new OpeningObject("r1bqkbnr/pppp1ppp/2n5/4p3/2B1P3/5N2/PPPP1PPP/RNBQK2R");
            event.editMessage(italian.getImage()).setActionRow(Button.primary("watchitl", "Master Games")).queue();
        }

        if(event.getComponentId().equals("watchitl")){
            event.editMessage(watchMaster.getOpenings("e2e4,e7e5,g1f3,b8c6,f1c4")).setActionRow(Button.link("https://lichess.org"+watchMaster.getOpeningId()[1], "Analyze")).queue();
        }

        if(event.getComponentId().equals("twoopening")){
            OpeningObject queensGambit = new OpeningObject("rnbqkbnr/ppp1pppp/8/3p4/2PP4/8/PP2PPPP/RNBQKBNR");
            event.editMessage(queensGambit.getImage()).setActionRow(Button.primary("watchqueen", "Master Games")).queue();
        }

        if(event.getComponentId().equals("watchqueen")){
            event.editMessage(watchMaster.getOpenings("d2d4,d7d5,c2c4")).setActionRow(Button.link("https://lichess.org"+watchMaster.getOpeningId()[1], "Analyze")).queue();
        }

        if(event.getComponentId().equals("threeopening")){
            OpeningObject english = new OpeningObject( "rnbqkbnr/pppppppp/8/8/2P5/8/PP1PPPPP/RNBQKBNR");
            event.editMessage(english.getImage()).setActionRow(Button.primary("watcheng", "Master Games")).queue();
        }

        if(event.getComponentId().equals("watcheng")){
            event.editMessage(watchMaster.getOpenings("c2c4")).setActionRow(Button.link("https://lichess.org"+watchMaster.getOpeningId()[1], "Analyze")).queue();
        }

        if(event.getComponentId().equals("fouropening")){
            OpeningObject zugOpening = new OpeningObject("rnbqkbnr/pppppppp/8/8/8/5N2/PPPPPPPP/RNBQKB1R");
            event.editMessage(zugOpening.getImage()).setActionRow(Button.primary("watchzug", "Master Games")).queue();
        }

        if(event.getComponentId().equals("watchzug")){
            event.editMessage(watchMaster.getOpenings("g1f3")).setActionRow(Button.link("https://lichess.org"+watchMaster.getOpeningId()[1],"Analyze")).queue();
        }

        if(event.getComponentId().equals("fiveopening")){
            OpeningObject sclOpening = new OpeningObject( "rnbqkbnr/pp1ppppp/8/2p5/4P3/8/PPPP1PPP/RNBQKBNR");
            event.editMessage(sclOpening.getImage()).setActionRow(Button.primary("watchsd", "Master Games")).queue();
        }

        if(event.getComponentId().equals("watchsd")){
            event.editMessage(watchMaster.getOpenings("e2e4,c7c5")).setActionRow(Button.link("https://lichess.org"+watchMaster.getOpeningId()[1],"Analyze")).queue();
        }

        if(event.getComponentId().equals("hint")){
            event.replyEmbeds(dailyCommand.getThemes().build()).setEphemeral(true).queue();
        }

    }


}


