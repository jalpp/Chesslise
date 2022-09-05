
import chariot.Client;
import lombok.SneakyThrows;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.Modal;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;
import org.jetbrains.annotations.NotNull;
import javax.security.auth.login.LoginException;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class Main extends ListenerAdapter {

    private static JDABuilder jdaBuilder;
    private static JDA jda;
    private String ButtonUserId;
    private Client ButtonClient;
    private String APIPASSWORD;
    private static Client client = Client.basic(conf -> conf.retries(0));




    public static void main(String[] args) {

       String TOKEN= "insert Token here!";


        jdaBuilder = JDABuilder.createDefault(TOKEN);// string token

        jdaBuilder.setStatus(OnlineStatus.ONLINE);
        jdaBuilder.setActivity(Activity.playing("/help -> for more info!"));
        jdaBuilder.addEventListeners(new Main());



        try {
            jda = jdaBuilder.build();

        } catch (LoginException exception) {
            exception.printStackTrace();
        }

        CommandListUpdateAction commands = jda.updateCommands();
          commands.addCommands(Commands.slash("view", "View Games").addOption(OptionType.STRING, "gameurl", "enter lichess game link", true));
          commands.addCommands(Commands.slash("service", "Read our Terms of Service"));
          commands.addCommands(Commands.slash("openingdb", "View Chess Openings"));
          commands.addCommands(Commands.slash("board", "display moves").addOption(OptionType.STRING, "moves", "play moves to display on board ex. e4", true));
          commands.addCommands(Commands.slash("puzzleracer", "Play Puzzle Racer"));
          commands.addCommands(Commands.slash("dailypuzzle", "Daily Lichess Puzzles"));
          commands.addCommands(Commands.slash("arena", "See Swiss/Arena standings").addOption(OptionType.STRING, "arenaid", "Input Lichess arena link", true));
          commands.addCommands(Commands.slash("watchmaster", "Watch GM Games in gif"));
          commands.addCommands(Commands.slash("profile", "See Lichess Profile of given user"));
          commands.addCommands(Commands.slash("streamers", "See current Live Streamers"));
          commands.addCommands(Commands.slash("puzzle", "do puzzles"));
          commands.addCommands(Commands.slash("tourney", "Join Current Lichess Tournaments"));
          commands.addCommands(Commands.slash("liga", "view Liga Leaderboard based on your favorite Lichess team").addOption(OptionType.STRING, "teamname", "Enter lichess team name", true));
          commands.addCommands(Commands.slash("help", "View LISEBOT Commands"));
          commands.addCommands(Commands.slash("play", "Play Live Chess Games"));
          commands.addCommands(Commands.slash("scheduletournament", "schedule Lichess arena from Discord"));
          commands.addCommands(Commands.slash("tv", "Watch Lichess TV"));
          commands.addCommands(Commands.slash("tourneymanager", "Create and Manage Your Lichess Tournament"));
          commands.addCommands(Commands.slash("blog", "Read Lichess Blogs"));
          commands.addCommands(Commands.slash("watch", "Watch Lichess games for given user"));
          commands.addCommands(Commands.slash("top10", "see top 10 list for given variant(blitz classical etc) ").addOption(OptionType.STRING, "top", "input Lichess variant", true));
          commands.addCommands(Commands.slash("challengeauth", "Send direct Lichess Challenge with Personal Token Login"));
          commands.addCommands(Commands.slash("invite", "Invite me to your servers!"));


          commands.queue();



    }


    @SneakyThrows
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event){
        String name = event.getName();

        this.ButtonClient = client;

        switch(name) {


            case "service":
                EmbedBuilder embedBuildertos = new EmbedBuilder();
                embedBuildertos.setColor(Color.blue);
                embedBuildertos.setTitle("Terms Of Service And Privacy Policy");
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
            case "view":
                String gameid = event.getOption("gameurl").getAsString();
                ViewGame viewGame = new ViewGame(client, gameid);
                event.replyEmbeds(viewGame.getViewGame().build()).queue();
                break;
            case "board":
                String m = event.getOption("moves").getAsString();
                Board b = new Board(m);
                event.replyEmbeds(b.getView().build()).queue();
                break;
            case "puzzleracer":
                TextInput puzzletext = TextInput.create("racerauth", "Token Input", TextInputStyle.SHORT)
                        .setPlaceholder("Input Your Lichess Token")
                        .setMinLength(10)
                        .setMaxLength(100)
                        .build();
                Modal modalpuzzle = Modal.create("modalpuzzle", "Play Puzzle Racer")
                        .addActionRows(ActionRow.of(puzzletext))
                        .build();
                event.replyModal(modalpuzzle).queue();
                break;
            case "watchmaster":
                WatchMaster watchMaster = new WatchMaster(client);
                event.deferReply(true).queue();
                event.getChannel().sendMessage(watchMaster.getMasterGames()).addActionRow(Button.link("https://lichess.org" + watchMaster.getGameId()[1], "Analyze")).queue();
                break;
            case "puzzle":
                puzzle puzzle = new puzzle();
                event.reply("Generating Puzzles..").setEphemeral(true).queue();
                event.getChannel().sendMessage(puzzle.getRandom()).addActionRow(Button.link(puzzle.getSolLink(), puzzle.getMoveSay())).queue();

                break;
            case "tourney":
                currentTournament currentTournament = new currentTournament(client);
                event.replyEmbeds(currentTournament.getTournaments().build()).queue();
                break;
            case "help":
                CommandInfo commandInfo = new CommandInfo();
                event.replyEmbeds(commandInfo.getPageOne().build()).addActionRow(Button.primary("next", "➡️")).queue();
                break;
            /**
             * Blitz 3+2
             * Rapid 5+5
             * Bullet 1+0
             * UltraBullet 1/4 + 0
             * Classical 30+20
             */
            case "play":
                event.reply("**⚔️ Please Pick Your Game's Mode ⚔️ **").addActionRow(
                        Button.success("casmode", "Casual"), Button.danger("ratedmode", "Rated"), Button.link("https://lichess.org/", "Create Custom Challenge")).queue();
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
            case "streamers":
                LiveStreamers liveStreamers = new LiveStreamers(client);
                event.replyEmbeds(liveStreamers.getTv().build()).queue();
                break;
            case "dailypuzzle":
                DailyCommand dailyCommand = new DailyCommand(client);
                event.reply(dailyCommand.getPuzzleData()).addActionRow(Button.link("https://lichess.org/training/daily", dailyCommand.getMoveSay() + "| " + "Rating: " + dailyCommand.getRating()), Button.primary("sol", "Solution"), Button.success("hint", "Hint")).queue();
                break;
            case "watch":

                TextInput wtext = TextInput.create("watchuser", "Input Lichess Username", TextInputStyle.SHORT)
                        .setPlaceholder("Input Lichess Username")
                        .setMinLength(2)
                        .setMaxLength(100)
                        .build();
                Modal wmodal = Modal.create("modalwatch", "Watch Live Or Recent Lichess Games!")
                        .addActionRows(ActionRow.of(wtext))
                        .build();
                event.replyModal(wmodal).queue();

                break;

            case "top10":
                String variantID = event.getOption("top").getAsString();
                leaderBoard lichessBoard = new leaderBoard(client);
                event.deferReply(true).queue();
                if (variantID.equals("blitz")) {

                    event.getChannel().sendMessageEmbeds(lichessBoard.getBlitzBoard().build()).queue();

                }else if ((variantID.equals("classical"))) {


                    event.getChannel().sendMessageEmbeds(lichessBoard.getClassicalBoard().build()).queue();

                }else if ((variantID.equals("rapid"))) {

                    event.getChannel().sendMessageEmbeds(lichessBoard.getRapidBoard().build()).queue();

                }

                else if (variantID.equals("bullet")) {

                    event.getChannel().sendMessageEmbeds(lichessBoard.getBulletBoard().build()).queue();
                }

                else if (variantID.equals("ultrabullet")) {

                    event.getChannel().sendMessageEmbeds(lichessBoard.getUltraBoard().build()).queue();

                }
                break;

            case "arena":
                 String arenaLink = event.getOption("arenaid").getAsString();
                 UserArena userArena = new UserArena(client,arenaLink);
                 event.deferReply(true).queue();
                 event.getChannel().sendMessageEmbeds(userArena.getUserArena().build()).queue();
                break;
            case "challengeauth":
                TextInput subject = TextInput.create("challengeauth", "Token Input", TextInputStyle.SHORT)
                        .setPlaceholder("Input Your Lichess Token")
                        .setMinLength(10)
                        .setMaxLength(100)
                        .build();
                TextInput subjectuser = TextInput.create("challengeauthuser", "Input Lichess Username", TextInputStyle.SHORT)
                        .setPlaceholder("Input Your opponent Lichess Username")
                        .setMinLength(5)
                        .setMaxLength(28)
                        .build();

                Modal modal = Modal.create("modalplay", "Send Lichess Challenge!")
                        .addActionRows(ActionRow.of(subject), ActionRow.of(subjectuser))
                        .build();
                event.replyModal(modal).queue();
               break;

            case "scheduletournament":
                TextInput tourtoken = TextInput.create("tourauth", "Token Input", TextInputStyle.SHORT)
                        .setPlaceholder("Input Your Lichess Token")
                        .setMinLength(10)
                        .setMaxLength(100)
                        .build();
                TextInput tourtime = TextInput.create("timeformat", "Input Tournament's Time", TextInputStyle.SHORT)
                        .setPlaceholder("Input Your Tournaments Time Control Ex. blitz")
                        .setMinLength(5)
                        .setMaxLength(20)
                        .build();

                Modal modaltour = Modal.create("modaltour", "Schedule A Lichess Tournament")
                        .addActionRows(ActionRow.of(tourtoken), ActionRow.of(tourtime))
                        .build();
                event.replyModal(modaltour).queue();
                break;
            case "stormdash":
                String stormUser = event.getOption("storm").getAsString();
                UserDashboard userDashboard = new UserDashboard(client,stormUser);
                event.replyEmbeds(userDashboard.getUserDashboard().build()).queue();
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
            case "tourneymanager":
                TextInput tourmanager = TextInput.create("managerauth", "Token Input", TextInputStyle.SHORT)
                        .setPlaceholder("Input Your Lichess Token")
                        .setMinLength(10)
                        .setMaxLength(100)
                        .build();
                Modal modalmanager = Modal.create("modalmanager", "Your Tournament Manager")
                        .addActionRows(ActionRow.of(tourmanager))
                        .build();
                event.replyModal(modalmanager).queue();
                break;
            case "tv":
                WatchTv watchTv = new WatchTv(client);
                event.replyEmbeds(watchTv.getTV().build()).addActionRow(Button.primary("blitztv", "⚡"), Button.primary("bullettv", "\uD83D\uDE85"), Button.primary("rapidtv", "\uD83D\uDC3F")).queue();
                break;
            case "liga":
                 String team = event.getOption("teamname").getAsString();
                 LigaEmbed ligaEmbed = new LigaEmbed(client, team);
                 event.reply("Generating Liga Results..").queue();
                 event.getChannel().sendMessageEmbeds(ligaEmbed.getLigaEmbed().build()).queue();
                break;
            default:

        }


    }


    @Override
    public void onModalInteraction(@NotNull ModalInteractionEvent event) {
        Client client = Client.basic();
        String name = event.getModalId();

        switch (name){
            case "modalplay":
                AdminLoginChallenge adminLogin = new AdminLoginChallenge(client, event.getValue("challengeauth").getAsString() , event.getValue("challengeauthuser").getAsString());
                event.replyEmbeds(adminLogin.getChallenge().build()).queue();
                break;
            case "modaltour":
                AdminLoginCreateTournament createTournament = new AdminLoginCreateTournament(event.getValue("tourauth").getAsString(), event.getValue("timeformat").getAsString());
                event.deferReply(true).queue();
                event.getMessageChannel().sendMessageEmbeds(createTournament.getCreatedTournament().build()).queueAfter(20, TimeUnit.SECONDS);
                break;
            case "modalmanager":
                  String passwordTournament = event.getValue("managerauth").getAsString();
                  this.APIPASSWORD = passwordTournament;
                  TournamentManager manager = new TournamentManager(passwordTournament);
                  event.replyEmbeds(manager.sayStatus().build()).addActionRow(Button.primary("createone", "Create Arenas"), Button.primary("createtwo", "Create Monthly Arenas")).queue();
                break;
            case "modalpuzzle":
                PuzzleRacer puzzleRacer = new PuzzleRacer(event.getValue("racerauth").getAsString());
                event.deferReply(true).queue();
                event.getMessageChannel().sendMessageEmbeds(puzzleRacer.getPuzzleRacerLinks().build()).queue();
                break;
            case "modalwatch":

                  String gameUserID = event.getValue("watchuser").getAsString();
                if(client.users().byId(gameUserID).isPresent()) {
                    UserGame userGame = new UserGame(client, gameUserID);
                    String link = "https://lichess.org/@/" + gameUserID.toLowerCase() + "/all";
                    event.deferReply(true).queue();
                    event.getChannel().sendMessage(userGame.getUserGames()).addActionRow(Button.link(link, "@" +gameUserID + "'s Games")).queue();
                }else{
                    event.reply("Please Provide A Valid Lichess Username!").queue();
                }
                break;

            case "modalpro":
                String userID = event.getValue("profileuser").getAsString();
                this.ButtonUserId = userID;
                UserProfile userProfile = new UserProfile(client,userID);
                event.deferReply(true).queue();
                event.getChannel().sendMessage(userProfile.getUserProfile()).addActionRow(Button.danger("userpuzzle","\uD83C\uDF2A️"), Button.link("https://lichess.org/@/" + this.ButtonUserId, "View On Lichess")).queue();
                break;


        }


    }



    @Override
    public void onButtonInteraction(@NotNull  ButtonInteractionEvent event) {

        Client client = Client.basic();
        TournamentManager tournamentManager = new TournamentManager(this.APIPASSWORD);
        WatchTv tv = new WatchTv(this.ButtonClient);
        WatchMaster watchMaster = new WatchMaster(client);
        CommandInfo commandInfo = new CommandInfo();


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
             event.editMessage("3+0 Rated loaded!").setActionRow(Button.link(threeP0.getRandom(), "Join The Game!")).queue();
             break;
         case "5+0r":
             Game fP0 = new Game();
             fP0.DifferentGameGen(5,0,"r");
             event.editMessage("5+0 Rated loaded!").setActionRow(Button.link(fP0.getRandom(), "Join The Game!")).queue();
             break;
         case "10+0r":
             Game tP0 = new Game();
             tP0.DifferentGameGen(10,0,"r");
             event.editMessage("10+0 Rated loaded!").setActionRow(Button.link(tP0.getRandom(), "Join The Game!")).queue();
             break;
         case "3+0c":
             Game threeP0c = new Game();
             threeP0c.DifferentGameGen(3,0,"c");
             event.editMessage("3+0 Casual loaded!").setActionRow(Button.link(threeP0c.getRandom(), "Join The Game!")).queue();
             break;
         case "5+0c":
             Game fP0c = new Game();
             fP0c.DifferentGameGen(5,0,"c");
             event.editMessage("5+0 Casual loaded!").setActionRow(Button.link(fP0c.getRandom(), "Join The Game!")).queue();
             break;
         case "10+0c":
             Game tP0c = new Game();
             tP0c.DifferentGameGen(10,0,"c");
             event.editMessage("10+0 Casual loaded!").setActionRow(Button.link(tP0c.getRandom(), "Join The Game!")).queue();
             break;
     }

     switch (event.getComponentId()){
         case "ultrafastc":
             Game ug = new Game(client, "ultrabullet", "casual");
             ug.getNewGame();
             event.editMessage("**Ultra Casual Challenge Loaded!**").setActionRow(Button.link(ug.getRandom(), "Join The Game!")).queue();
         case "bulletfastc":
             Game bg = new Game(client, "bullet", "casual");
             bg.getNewGame();
             event.editMessage("**Bullet Casual Challenge Loaded!**").setActionRow(Button.link(bg.getRandom(), "Join The Game!")).queue();
             break;
         case "blitzfastc":
             Game bcg = new Game(client, "blitz", "casual");
             bcg.getNewGame();
             event.editMessage("**Blitz Casual Challenge Loaded!**").setActionRow(Button.link(bcg.getRandom(), "Join The Game!")).queue();
             break;
         case "rapidfastc":
             Game rg = new Game(client, "rapid", "casual");
             rg.getNewGame();
             event.editMessage("**Rapid Casual Challenge Loaded!**").setActionRow(Button.link(rg.getRandom(), "Join The Game!")).queue();
             break;
         case "classicalfastc":
             Game cg = new Game(client, "classical", "casual");
             cg.getNewGame();
             event.editMessage("**Classical Casual Challenge Loaded!**").setActionRow(Button.link(cg.getRandom(), "Join The Game!")).queue();
             break;

     }

        switch (event.getComponentId()){
            case "ultrafastr":
                Game ug = new Game(client, "ultrabullet", "rated");
                ug.getNewGame();
                event.editMessage("**Ultra Rated Challenge Loaded!**").setActionRow(Button.link(ug.getRandom(), "Join The Game!")).queue();
            case "bulletfastcr":
                Game bg = new Game(client, "bullet", "rated");
                bg.getNewGame();
                event.editMessage("**Bullet Rated Challenge Loaded!**").setActionRow(Button.link(bg.getRandom(), "Join The Game!")).queue();
                break;
            case "blitzfastr":
                Game bcg = new Game(client, "blitz", "rated");
                bcg.getNewGame();
                event.editMessage("**Blitz Rated Challenge Loaded!**").setActionRow(Button.link(bcg.getRandom(), "Join The Game!")).queue();
                break;
            case "rapidfastr":
                Game rg = new Game(client, "rapid", "rated");
                rg.getNewGame();
                event.editMessage("**Rapid Rated Challenge Loaded!**").setActionRow(Button.link(rg.getRandom(), "Join The Game!")).queue();
                break;
            case "classicalfastr":
                Game cg = new Game(client, "classical", "rated");
                cg.getNewGame();
                event.editMessage("**Classical Rated Challenge Loaded!**").setActionRow(Button.link(cg.getRandom(), "Join The Game!")).queue();
                break;

        }


     if(event.getComponentId().equals("openingdata")){
         EmbedBuilder openingbase = new EmbedBuilder();
         openingbase.setColor(Color.green);
         openingbase.setTitle("Opening DataBase");
         openingbase.setDescription("1️⃣ **Italian Opening ~ e4 e5 Nf3 Nf6 Bc4** \n\n 2️⃣ **Queen's Gambit ~ d4 d5 c4** \n\n 3️⃣ **English Opening ~ c4** \n\n 4️⃣ **Zukertort Opening ~ Nf3** \n\n 5️⃣ **Sicilian Defence ~ e4 c5** \n\n ");
         event.editMessageEmbeds(openingbase.build()).setActionRow(Button.primary("oneopening", "1️⃣"), Button.primary("twoopening", "2️⃣"), Button.primary("threeopening", "3️⃣"), Button.primary("fouropening", "4️⃣"), Button.primary("fiveopening", "5️⃣")).queue();

     }

      if(event.getComponentId().equals("profile")){
          UserProfile userProfile = new UserProfile(client,this.ButtonUserId);
          event.editMessage(userProfile.getUserProfile()).setActionRow(Button.primary("userwatch", "\uD83D\uDCFA"), Button.danger("userpuzzle","\uD83C\uDF2A️"), Button.primary("userstreaming", "\uD83C\uDF99️"), Button.link("https://lichess.org/@/" + this.ButtonUserId, "♞")).queue();

      }



       if(event.getComponentId().equals("userpuzzle")){
           UserDashboard userDashboard = new UserDashboard(this.ButtonClient, this.ButtonUserId);
           event.editMessageEmbeds(userDashboard.getUserDashboard().build()).setActionRow(Button.primary("userwatch", "\uD83D\uDCFA").asDisabled(), Button.danger("userpuzzle","\uD83C\uDF2A️").asDisabled(), Button.primary("userstreaming", "\uD83C\uDF99️").asDisabled()).queue();
       }

       if(event.getComponentId().equals("userstreaming")){
           UserStreaming userStreaming = new UserStreaming(this.ButtonClient, this.ButtonUserId);
           event.editMessageEmbeds(userStreaming.getStreamingStatus().build()).setActionRow(Button.primary("userwatch", "\uD83D\uDCFA").asDisabled(), Button.danger("userpuzzle","\uD83C\uDF2A️").asDisabled(), Button.primary("userstreaming", "\uD83C\uDF99️").asDisabled()).queue();
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

        if(event.getComponentId().equals("createtwo")){
            event.deferReply(true).queue();
            event.reply(tournamentManager.getMonthlyTournamentStatus()).queueAfter(60, TimeUnit.SECONDS);
        }

        if(event.getComponentId().equals("bulletarena")){
            event.deferReply(true).queue();
            event.replyEmbeds(tournamentManager.getBulletTournamentCreated().build()).queueAfter(60, TimeUnit.SECONDS);
        }else if(event.getComponentId().equals("blitzarena")){
            event.deferReply(true).queue();
            event.replyEmbeds(tournamentManager.getBlitzTournamentCreated().build()).queueAfter(60, TimeUnit.SECONDS);
        }else if(event.getComponentId().equals("rapidarena")){
            event.deferReply(true).queue();
            event.replyEmbeds(tournamentManager.getRapidTournamentCreated().build()).queueAfter(60, TimeUnit.SECONDS);
        }


        if(event.getComponentId().equals("blitztv")){
            event.deferReply(true).queue();
            event.getChannel().sendMessage(tv.getBlitz()).queueAfter(20, TimeUnit.SECONDS);
        }else if(event.getComponentId().equals("bullettv")){
            event.deferReply(true).queue();
            event.getChannel().sendMessage(tv.getBullet()).queueAfter(20, TimeUnit.SECONDS);
        }else if(event.getComponentId().equals("rapidtv")){
            event.deferReply(true).queue();
            event.getChannel().sendMessage(tv.getRapid()).queueAfter(20, TimeUnit.SECONDS);
        }

        if(event.getComponentId().equals("next")){
            event.editMessageEmbeds(commandInfo.getPageTwo().build()).setActionRow(Button.primary("nexttwo", "➡️")).queue();
        }else if(event.getComponentId().equals("nexttwo")){
            event.editMessageEmbeds(commandInfo.getPageThree().build()).setActionRow(Button.primary("nextthree", "➡️")).queue();
        }else if(event.getComponentId().equals("nextthree")){
            event.editMessageEmbeds(commandInfo.getPageFour().build()).setActionRow(Button.primary("nextthree", "➡️").asDisabled()).queue();
        }

        if(event.getComponentId().equals("sol")){
            DailyCommand dailyCommand = new DailyCommand(client);
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
            DailyCommand dailyCommand = new DailyCommand(client);
            event.replyEmbeds(dailyCommand.getThemes().build()).setEphemeral(true).queue();
        }







    }

    


}


