
import chariot.Client;
import lombok.SneakyThrows;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.ItemComponent;
import net.dv8tion.jda.api.interactions.components.Modal;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;

import net.dv8tion.jda.internal.entities.UserImpl;
import org.jetbrains.annotations.NotNull;

import javax.security.auth.login.LoginException;
import java.awt.*;
import java.time.Duration;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class Main extends ListenerAdapter {

    private static JDABuilder jdaBuilder;
    private static JDA jda;
    private String ButtonUserId;
    private Client ButtonClient;
    private String APIPASSWORD;
    private List<User> userslist = new ArrayList<>();
    private boolean checker = true;







    public static void main(String[] args) {


        String Token = "Your Discord Token";
        

        jdaBuilder = JDABuilder.createDefault(Token);// string token

        jdaBuilder.setStatus(OnlineStatus.ONLINE);
        jdaBuilder.setActivity(Activity.playing("Chess"));
        jdaBuilder.addEventListeners(new Main());



        try {
            jda = jdaBuilder.build();

        } catch (LoginException exception) {
            exception.printStackTrace();
        }

        CommandListUpdateAction commands = jda.updateCommands();
          commands.addCommands(Commands.slash("openingdb", "View Chess Openings"));
          commands.addCommands(Commands.slash("setreminderoff", "Turn of notifications"));
          commands.addCommands(Commands.slash("setreminderon", "Turn on notifications"));
          commands.addCommands(Commands.slash("subscribe", "set notifications for watching Magnus Carlsen"));
          commands.addCommands(Commands.slash("board", "display moves").addOption(OptionType.STRING, "moves", "play moves to display on board ex. e4", true));
          commands.addCommands(Commands.slash("puzzleracer", "Play Puzzle Racer"));
          commands.addCommands(Commands.slash("answer", "View Daily Puzzle Answer"));
          commands.addCommands(Commands.slash("dailypuzzle", "Daily Lichess Puzzles"));
          commands.addCommands(Commands.slash("arena", "See Swiss/Arena standings").addOption(OptionType.STRING, "arenaid", "Input Lichess arena link", true));
          commands.addCommands(Commands.slash("watchmaster", "Watch GM Games in gif"));
          commands.addCommands(Commands.slash("profile", "See Lichess Profile of given user").addOption(OptionType.STRING, "username", "input Lichess username", true));
          commands.addCommands(Commands.slash("streamers", "See current Live Streamers"));
          commands.addCommands(Commands.slash("puzzle", "View Puzzles"));
          commands.addCommands(Commands.slash("tourney", "Join Current Lichess Tournaments"));
          commands.addCommands(Commands.slash("liga", "view Liga Leaderboard based on your favorite Lichess team").addOption(OptionType.STRING, "teamname", "Enter lichess team name", true));
          commands.addCommands(Commands.slash("help", "View LISEBOT Commands"));
          commands.addCommands(Commands.slash("play", "Play Live Chess Games").addOption(OptionType.STRING, "variant", "choose mode blitz, rapid etc", true).addOption(OptionType.STRING, "challengetype", "rated/casual", true));
          commands.addCommands(Commands.slash("scheduletournament", "schedule Lichess arena from Discord"));
          commands.addCommands(Commands.slash("tv", "Watch Lichess TV"));
          commands.addCommands(Commands.slash("tourneymanager", "Create and Manage Your Lichess Tournament"));
          commands.addCommands(Commands.slash("blog", "Read Lichess Blogs"));
          commands.addCommands(Commands.slash("watch", "Watch Lichess games for given user").addOption(OptionType.STRING,"watchuser", "Enter Lichess Username to watch their games", true));
          commands.addCommands(Commands.slash("top10", "see top 10 list for given variant(blitz classical etc) ").addOption(OptionType.STRING, "top", "input Lichess variant", true));
          commands.addCommands(Commands.slash("challengeauth", "Send direct Lichess Challenge with Personal Token Login"));
          commands.addCommands(Commands.slash("invite", "Invite me to your servers!"));


          commands.queue();



    }



    public void reminder(SlashCommandInteractionEvent event, String user, boolean disabled){

        if(disabled != false) {

            Client client = Client.basic();
            this.userslist.add(event.getUser());

            var ref = new Object() {
                private int c;


                public void setCounter(int c) {
                    this.c = c;
                }

                public int getCounter() {
                    return this.c;
                }

                public boolean check() {
                    if (this.getCounter() == 0) {
                        return true;
                    } else if (this.getCounter() == 1) {
                        return false;
                    }
                    return false;
                }


            };

               int counter = Collections.frequency(this.userslist, event.getUser());

               if(counter == 1) {


                   ZonedDateTime now = ZonedDateTime.now(ZoneId.of("America/New_York"));


                   ZonedDateTime nextmessage = now.withHour(1);


                   if (now.compareTo(nextmessage) > 0) {
                       nextmessage = nextmessage.withHour(1);
                   }


                   Duration durationUntilFirstmsg = Duration.between(now, nextmessage);

                   long initialDelayFirstmsg = durationUntilFirstmsg.getSeconds();


                   ScheduledExecutorService schedulermsg = Executors.newScheduledThreadPool(2);
                   schedulermsg.scheduleAtFixedRate(() -> {


                               boolean check = client.users().statusByIds(user).get().online();

                               if (check && ref.check()) {
                                   ref.setCounter(1);

                                   for(int i = 0; i < this.userslist.stream().distinct().toList().size(); i++) {
                                       this.userslist.stream().distinct().toList().get(i).openPrivateChannel().queue(privateChannel -> {
                                           EmbedBuilder embedBuilder = new EmbedBuilder();
                                           embedBuilder.setTitle("Magnus Carlsen Is Online!");
                                           embedBuilder.setDescription("Check out what is Magnus Calsen up to by [clicking here](https://lichess.org/@/DrNykterstein)");
                                           embedBuilder.setColor(Color.green);
                                           privateChannel.sendMessageEmbeds(embedBuilder.build()).queue();

                                       });

                                   }


                               } else {

                                   if (!check) {
                                       ref.setCounter(0);
                                   }

                               }


                           },
                           initialDelayFirstmsg,
                           TimeUnit.SECONDS.toSeconds(50),
                           TimeUnit.SECONDS);

               }else{
                   event.deferReply(true).queue();
                   event.getChannel().sendMessage("You have already singed up for the notifications!").queue();
               }

            }else{
            event.reply("Notifications are turned off, turn them on by running /setreminderon").setEphemeral(true).queue();
        }
    }

    @SneakyThrows
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event){
        String name = event.getName();
        Client client = Client.basic();
        this.ButtonClient = client;

        switch(name) {
            case "openingdb":
                EmbedBuilder openingbase = new EmbedBuilder();
                openingbase.setColor(Color.green);
                openingbase.setTitle("Opening DataBase");
                openingbase.setDescription("1️⃣ **Italian Opening ~ e4 e5 Nf3 Nf6 Bc4** \n\n 2️⃣ **Queen's Gambit ~ d4 d5 c4** \n\n 3️⃣ **English Opening ~ c4** \n\n 4️⃣ **Zukertort Opening ~ Nf3** \n\n 5️⃣ **Sicilian Defence ~ e4 c5** \n\n ");
                event.replyEmbeds(openingbase.build()).addActionRow(Button.primary("oneopening", "1️⃣"), Button.primary("twoopening", "2️⃣"), Button.primary("threeopening", "3️⃣"), Button.primary("fouropening", "4️⃣"), Button.primary("fiveopening", "5️⃣")).queue();
                break;
            case "setreminderon":
                if(this.checker == false){
                    this.checker = true;
                    event.reply("Please run /subscribe to turn them on!!").setEphemeral(true).queue();
                }else{
                    event.reply("Notifications are already on!").setEphemeral(true).queue();
                }
                break;

            case "setreminderoff":
                if(this.checker == true){
                    this.checker = false;
                    this.userslist.remove(event.getUser());
                    event.reply("Notifications are turned off!").setEphemeral(true).queue();
                }else{
                    event.reply("Notifications are turned off already!").setEphemeral(true).queue();
                }
                break;

                //DrNykterstein
                // Fixed User name error

            case "subscribe":
                reminder(event, "DrNykterstein", checker);
                event.reply("Notifications are turned on!").setEphemeral(true).queue();
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
            case "answer":
                DailyCommand dailyCommandsol = new DailyCommand(client);
                event.replyEmbeds(dailyCommandsol.getSolution().build()).setEphemeral(true).queue();
                break;
            case "watchmaster":
                WatchMaster watchMaster = new WatchMaster(client);
                EmbedBuilder embedBuilderw = new EmbedBuilder();
                embedBuilderw.setColor(Color.cyan);
                embedBuilderw.setImage(watchMaster.getMasterGames());
                event.replyEmbeds(embedBuilderw.build()).addActionRow(Button.link("https://lichess.org" + watchMaster.getGameId()[1], "Analyze")).queue();
                break;
            case "puzzle":
                puzzle puzzle = new puzzle();
                event.reply("\uD83E\uDDE9 **Chess Tactics** \uD83E\uDDE9").queue();
                event.getChannel().sendMessageEmbeds(puzzle.getRandom().build()).queue();
                break;
            case "tourney":
                currentTournament currentTournament = new currentTournament(client);
                event.replyEmbeds(currentTournament.getTournaments().build()).queue();
                break;
            case "help":
                CommandInfo commandInfo = new CommandInfo();
                event.replyEmbeds(commandInfo.getPageOne().build()).addActionRow(Button.primary("next", "➡️")).queue();
                break;
            case "play":
                 String variant = event.getOption("variant").getAsString();
                 String challengeOption = event.getOption("challengetype").getAsString();
                 Game game = new Game(client, variant, challengeOption);
                 event.replyEmbeds(game.getNewGame().build()).queue();
                break;
            case "profile":
                String userID = event.getOption("username").getAsString();
                this.ButtonUserId = userID;
                UserProfile userProfile = new UserProfile(client,userID);
                event.replyEmbeds(userProfile.getUserProfile().build()).addActionRow(Button.primary("userwatch", "\uD83D\uDCFA"), Button.danger("userpuzzle","\uD83C\uDF2A️"), Button.primary("userstreaming", "\uD83C\uDF99️"), Button.link("https://lichess.org/@/" + this.ButtonUserId, "♞")).queue();
                break;
            case "streamers":
                LiveStreamers liveStreamers = new LiveStreamers(client);
                event.replyEmbeds(liveStreamers.getTv().build()).queue();
                break;
            case "dailypuzzle":
                DailyCommand dailyCommand = new DailyCommand(client);
                event.replyEmbeds(dailyCommand.getPuzzleData().build()).addActionRow(Button.link("https://lichess.org/training/daily", "Analyze"), Button.primary("sol", "Solution")).queue();
                break;
            case "watch":
                String gameUserID = event.getOption("watchuser").getAsString();
                UserGame userGame = new UserGame(client,gameUserID);
                EmbedBuilder embedBuilder = new EmbedBuilder();
                embedBuilder.setColor(Color.green);
                embedBuilder.setImage(userGame.getUserGames());
                String link = "https://lichess.org/@/" + gameUserID.toLowerCase() + "/all";
                event.replyEmbeds(embedBuilder.build()).addActionRow(Button.link(link, "View All Games")).queue();
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
                event.getTextChannel().sendMessageEmbeds(createTournament.getCreatedTournament().build()).queueAfter(20, TimeUnit.SECONDS);
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
                event.getTextChannel().sendMessageEmbeds(puzzleRacer.getPuzzleRacerLinks().build()).queue();
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

       if(event.getComponentId().equals("userwatch")){
           UserGame userGame = new UserGame(this.ButtonClient, this.ButtonUserId);
           EmbedBuilder gameEmbed = new EmbedBuilder();
           EmbedBuilder resultEmbed = new EmbedBuilder();
           resultEmbed.setDescription("Game Information");
           gameEmbed.setColor(Color.green);
           gameEmbed.setImage(userGame.getUserGames());
           event.editMessageEmbeds(gameEmbed.build()).setActionRow(Button.primary("userwatch", "\uD83D\uDCFA").asDisabled(), Button.danger("userpuzzle","\uD83C\uDF2A️").asDisabled(), Button.primary("userstreaming", "\uD83C\uDF99️").asDisabled()).queue();

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
            event.reply(CommunityBlog.getLatestBlog()).queue();
        }else if(event.getComponentId().equals("comblog")){
            event.reply(CommunityBlog.getCommunityBlogs()).queue();
        }else if(event.getComponentId().equals("chessblog")){
            event.reply(Chessblog.getBlogsByTopic()).queue();
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
            event.replyEmbeds(dailyCommand.getSolution().build()).setEphemeral(true).queue();
        }

        if(event.getComponentId().equals("oneopening")){
            OpeningObject italian = new OpeningObject("Italian Opening","r1bqkbnr/pppp1ppp/2n5/4p3/2B1P3/5N2/PPPP1PPP/RNBQK2R");
            event.editMessageEmbeds((italian.getOpeningEmbed().build())).setActionRow(Button.primary("watchitl", "Master Games")).queue();
        }

        if(event.getComponentId().equals("watchitl")){

            EmbedBuilder newEmbed = new EmbedBuilder();
            newEmbed.setColor(Color.green);
            newEmbed.setImage(watchMaster.getOpenings("e2e4,e7e5,g1f3,b8c6,f1c4"));
            event.editMessageEmbeds(newEmbed.build()).setActionRow(Button.link("https://lichess.org"+watchMaster.getOpeningId()[1], "Analyze")).queue();
        }

        if(event.getComponentId().equals("twoopening")){
            OpeningObject queensGambit = new OpeningObject("Queen's Gambit","rnbqkbnr/ppp1pppp/8/3p4/2PP4/8/PP2PPPP/RNBQKBNR");
            event.editMessageEmbeds(queensGambit.getOpeningEmbed().build()).setActionRow(Button.primary("watchqueen", "Master Games")).queue();
        }

        if(event.getComponentId().equals("watchqueen")){
            EmbedBuilder embedBuilderqueen = new EmbedBuilder();
            embedBuilderqueen.setColor(Color.green);
            embedBuilderqueen.setImage(watchMaster.getOpenings("d2d4,d7d5,c2c4"));
            event.editMessageEmbeds(embedBuilderqueen.build()).setActionRow(Button.link("https://lichess.org"+watchMaster.getOpeningId()[1], "Analyze")).queue();
        }

        if(event.getComponentId().equals("threeopening")){
            OpeningObject english = new OpeningObject("English Opening", "rnbqkbnr/pppppppp/8/8/2P5/8/PP1PPPPP/RNBQKBNR");
            event.editMessageEmbeds(english.getOpeningEmbed().build()).setActionRow(Button.primary("watcheng", "Master Games")).queue();
        }

        if(event.getComponentId().equals("watcheng")){
            EmbedBuilder threeEmebed = new EmbedBuilder();
            threeEmebed.setColor(Color.green);
            threeEmebed.setImage(watchMaster.getOpenings("c2c4"));
            event.editMessageEmbeds(threeEmebed.build()).setActionRow(Button.link("https://lichess.org"+watchMaster.getOpeningId()[1], "Analyze")).queue();
        }

        if(event.getComponentId().equals("fouropening")){
            OpeningObject zugOpening = new OpeningObject("Zukertort Opening", "rnbqkbnr/pppppppp/8/8/8/5N2/PPPPPPPP/RNBQKB1R");
            event.editMessageEmbeds(zugOpening.getOpeningEmbed().build()).setActionRow(Button.primary("watchzug", "Master Games")).queue();
        }

        if(event.getComponentId().equals("watchzug")){
            EmbedBuilder zugEmbed = new EmbedBuilder();
            zugEmbed.setColor(Color.green);
            zugEmbed.setImage(watchMaster.getOpenings("g1f3"));
            event.editMessageEmbeds(zugEmbed.build()).setActionRow(Button.link("https://lichess.org"+watchMaster.getOpeningId()[1],"Analyze")).queue();
        }

        if(event.getComponentId().equals("fiveopening")){
            OpeningObject sclOpening = new OpeningObject("Sicilian Defence", "rnbqkbnr/pp1ppppp/8/2p5/4P3/8/PPPP1PPP/RNBQKBNR");
            event.editMessageEmbeds(sclOpening.getOpeningEmbed().build()).setActionRow(Button.primary("watchsd", "Master Games")).queue();
        }

        if(event.getComponentId().equals("watchsd")){
            EmbedBuilder sdEmbed = new EmbedBuilder();
            sdEmbed.setColor(Color.green);
            sdEmbed.setImage(watchMaster.getOpenings("e2e4,c7c5"));
            event.editMessageEmbeds(sdEmbed.build()).setActionRow(Button.link("https://lichess.org"+watchMaster.getOpeningId()[1],"Analyze")).queue();
        }



    }

}






