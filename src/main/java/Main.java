
import chariot.Client;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.components.Button;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;
import org.jetbrains.annotations.NotNull;

import javax.security.auth.login.LoginException;
import java.awt.*;
import java.sql.Time;
import java.util.concurrent.TimeUnit;


public class Main extends ListenerAdapter {

    private static JDABuilder jdaBuilder;
    private static JDA jda;
    private String ButtonUserId;
    private Client ButtonClient;
    private String APIPASSWORD;



    public static void main(String[] args) {

       
        String Token= "Your Discord Token";


        jdaBuilder = JDABuilder.createDefault(Token);// string token

        jdaBuilder.setStatus(OnlineStatus.ONLINE);
        jdaBuilder.setActivity(Activity.playing("helping chess servers"));
        jdaBuilder.addEventListeners(new Main());


        try {
            jda = jdaBuilder.build();

        } catch (LoginException exception) {
            exception.printStackTrace();
        }

        CommandListUpdateAction action = jda.updateCommands();
        action.addCommands(new CommandData("tv", "Watch Lichess TV")).complete();
        action.addCommands(new CommandData("play", "Start a new Lichess Game").addOption(OptionType.STRING, "variant", "choose mode blitz, rapid etc", true).addOption(OptionType.STRING, "challengetype", "rated/casual", true)).complete();
        action.addCommands(new CommandData("help", "See Commands Info!")).complete();
        action.addCommands(new CommandData("tourneymanager", "Create and Manage Your Lichess Tournament").addOption(OptionType.STRING, "lichessapipassword", "Input Your Lichess API Token", true)).complete();
        action.addCommands(new CommandData("blog", "Read Lichess Blogs"));
        action.addCommands(new CommandData("profile", "See Lichess Profile of given User").addOption(OptionType.STRING, "username", "input Lichess username", true)).complete();
        action.addCommands(new CommandData("streamers", "See current Live Streamers")).complete();
        action.addCommands(new CommandData("dailypuzzle", "Do daily chess puzzle")).complete();
        action.addCommands(new CommandData("watch", "watch games of a particular Lichess User").addOption(OptionType.STRING, "watchuser", "Input Lichess username", true)).complete();
        action.addCommands(new CommandData("top10", "see top 10 list for given variant(blitz classical etc) ").addOption(OptionType.STRING, "top", "input Lichess variant", true)).complete();
        action.addCommands(new CommandData("arena", "see arena leaderboard list").addOption(OptionType.STRING, "arenaid", "Input Lichess arena link", true)).complete();
        action.addCommands(new CommandData("challengeauth", "Send direct Lichess Challenge with Personal Token Login").addOption(OptionType.STRING, "loginchat", "input your Lichess Personal API Token", true).addOption(OptionType.STRING, "userlog", "input opponent's username", true)).complete();
        action.addCommands(new CommandData("chatauth", "Send direct chat message to a user").addOption(OptionType.STRING, "logicidchat", "input your Lichess Personal API Token", true).addOption(OptionType.STRING, "messagereceiver", "input receiver's username", true).addOption(OptionType.STRING, "messageid", "input your message", true)).complete();
        action.addCommands(new CommandData("scheduletournament", "schedule Lichess arena from Discord").addOption(OptionType.STRING, "apipassword", "Input your Lichess Personal API Token", true).addOption(OptionType.STRING, "timeformat", "Input tournament's variant: blitz, classical etc.", true)).complete();
        action.addCommands(new CommandData("invite", "Invite me to your servers!")).complete();



    }

    public void onSlashCommand(SlashCommandEvent event){
        String name = event.getName();
        Client client = Client.basic();
        this.ButtonClient = client;


        switch(name) {
            case "help":
                CommandInfo commandInfo = new CommandInfo();
                event.replyEmbeds(commandInfo.getCommandInfo().build()).queue();
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
            case "livestreaming":
                String streamerID = event.getOption("streamername").getAsString();

                UserStreaming userStreaming = new UserStreaming(client, streamerID);

                event.replyEmbeds(userStreaming.getStreamingStatus().build()).queue();
                break;
            case "streamers":
                LiveStreamers liveStreamers = new LiveStreamers(client);
                event.replyEmbeds(liveStreamers.getTv().build()).queue();
                break;
            case "dailypuzzle":
                DailyCommand dailyCommand = new DailyCommand(client);
                event.replyEmbeds(dailyCommand.getPuzzleData().build()).addActionRow(Button.success("puzzlewatch", "\uD83D\uDCFA"), Button.link("https://lichess.org/training/daily", "View Solution")).queue();
                break;
            case "watch":
                String gameUserID = event.getOption("watchuser").getAsString();
                UserGame userGame = new UserGame(client,gameUserID);
                event.reply(userGame.getUserGames()).queue();
                break;

            case "top10":

                String variantID = event.getOption("top").getAsString();
                leaderBoard lichessBoard = new leaderBoard(client);

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
                 event.replyEmbeds(userArena.getUserArena().build()).queue();
                break;

            case "team":
                String teamLink = event.getOption("teamboard").getAsString();
                UserTeam userTeam = new UserTeam(client, teamLink);
                event.replyEmbeds(userTeam.getUserTeam().build()).queue();
                break;

            case "challengeauth":
               String password = event.getOption("loginchat").getAsString();
               String oppUserID = event.getOption("userlog").getAsString();
               AdminLoginChallenge adminLogin = new AdminLoginChallenge(client, password, oppUserID);
               event.replyEmbeds(adminLogin.getChallenge().build()).queue();
               break;

            case "chatauth":

                final String loginPassword = event.getOption("logicidchat").getAsString();
                String receiverUserID = event.getOption("messagereceiver").getAsString();
                String sendMessage = event.getOption("messageid").getAsString();
                AdminLoginChat chat = new AdminLoginChat(client,loginPassword, receiverUserID, sendMessage);
                event.reply("Processing your Request...").queue();
                event.getChannel().sendMessage("connecting to lichess..").queueAfter(10, TimeUnit.SECONDS);
                event.getChannel().sendMessageEmbeds(chat.getDelayMessage().build()).queueAfter(20, TimeUnit.SECONDS);
                event.getChannel().sendMessageEmbeds(chat.getChatStatus().build()).queueAfter(42, TimeUnit.SECONDS);
                break;

            case "scheduletournament":
                final String apiPassword = event.getOption("apipassword").getAsString();
                String timeFormat = event.getOption("timeformat").getAsString();
                AdminLoginCreateTournament createTournament = new AdminLoginCreateTournament(apiPassword, timeFormat);
                event.reply("Processing your Request...").queue();
                event.getChannel().sendMessage("connecting to lichess..").queueAfter(10, TimeUnit.SECONDS);
                event.getChannel().sendMessageEmbeds(createTournament.getCreatedTournament().build()).queueAfter(20, TimeUnit.SECONDS);
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
            case "gamereview":
                String gameuser = event.getOption("usergameid").getAsString();
                GameReview gameReview = new GameReview(client, gameuser);
                event.replyEmbeds(gameReview.getGameReviewData().build()).queue();
                break;
//            case "broadcast":
//                Broadcasts broadcasts = new Broadcasts(client);
//                event.replyEmbeds(broadcasts.getBroadcastData().build()).queue();
               // break;
            case "blog":
                EmbedBuilder BlogEmbed = new EmbedBuilder();
                BlogEmbed.setColor(Color.orange);
                BlogEmbed.setTitle("Read Lichess Blogs");
                BlogEmbed.setThumbnail("https://www.pngitem.com/pimgs/m/17-174815_writing-hand-emoji-png-transparent-png.png");
                BlogEmbed.setDescription("Read Lichess Blogs by clicking on topics of your interest!");
                event.replyEmbeds(BlogEmbed.build()).addActionRow(Button.success("newblog", "Latest Lichess Blog"), Button.primary("comblog", "Community Blogs"), Button.primary("chessblog", "Chess Blogs")).queue();
                break;
            case "tourneymanager":
                final String passwordTournament = event.getOption("lichessapipassword").getAsString();
                this.APIPASSWORD = passwordTournament;
                TournamentManager manager = new TournamentManager(passwordTournament);
                event.replyEmbeds(manager.sayStatus().build()).addActionRow(Button.primary("createone", "Create Arenas"), Button.primary("createtwo", "Create Monthly Arenas")).queue();
                break;
            case "tv":
                WatchTv watchTv = new WatchTv(client);
                event.replyEmbeds(watchTv.getTV().build()).addActionRow(Button.primary("blitztv", "⚡"), Button.primary("bullettv", "\uD83D\uDE85"), Button.primary("rapidtv", "\uD83D\uDC3F")).queue();

                break;
            default:

        }


    }


    @Override
    public void onButtonClick(@NotNull ButtonClickEvent event) {

        TournamentManager tournamentManager = new TournamentManager(this.APIPASSWORD);
        WatchTv tv = new WatchTv(this.ButtonClient);

       if(event.getComponentId().equals("userwatch")){
           UserGame userGame = new UserGame(this.ButtonClient, this.ButtonUserId);
           EmbedBuilder gameEmbed = new EmbedBuilder();
           gameEmbed.setColor(Color.green);
           gameEmbed.setDescription("Hope you enjoyed the game, You can get a game review report of the game, and request Stockfish Analysis!");
           event.getChannel().sendMessage(userGame.getUserGames()).queue();
           event.replyEmbeds(gameEmbed.build()).addActionRow(Button.danger("review", "Game Review Report"), Button.link("https://lichess.org/@/" + this.ButtonUserId, "Request Stockfish Analysis")).queue();

       }

       if(event.getComponentId().equals("userpuzzle")){
           UserDashboard userDashboard = new UserDashboard(this.ButtonClient, this.ButtonUserId);
           event.replyEmbeds(userDashboard.getUserDashboard().build()).queue();
       }

       if(event.getComponentId().equals("userstreaming")){
           UserStreaming userStreaming = new UserStreaming(this.ButtonClient, this.ButtonUserId);
           event.replyEmbeds(userStreaming.getStreamingStatus().build()).queue();
       }

       if(event.getComponentId().equals("review")){
           GameReview gameReview = new GameReview(this.ButtonClient, this.ButtonUserId);
           event.replyEmbeds(gameReview.getGameReviewData().build()).queue();
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
            event.getChannel().sendMessage("Connecting to Lichess...").queue();
            event.getChannel().sendMessage("Loading....").queueAfter(5, TimeUnit.SECONDS);
            event.reply(tournamentManager.getMonthlyTournamentStatus()).queueAfter(60, TimeUnit.SECONDS);
        }

        if(event.getComponentId().equals("bulletarena")){
            event.getChannel().sendMessage("Connecting to Lichess...").queue();
            event.getChannel().sendMessage("Loading....").queueAfter(5, TimeUnit.SECONDS);
            event.replyEmbeds(tournamentManager.getBulletTournamentCreated().build()).queueAfter(60, TimeUnit.SECONDS);
        }else if(event.getComponentId().equals("blitzarena")){
            event.getChannel().sendMessage("Connecting to Lichess...").queue();
            event.getChannel().sendMessage("Loading....").queueAfter(5, TimeUnit.SECONDS);
            event.replyEmbeds(tournamentManager.getBlitzTournamentCreated().build()).queueAfter(60, TimeUnit.SECONDS);
        }else if(event.getComponentId().equals("rapidarena")){
            event.getChannel().sendMessage("Connecting to Lichess...").queue();
            event.getChannel().sendMessage("Loading....").queueAfter(5, TimeUnit.SECONDS);
            event.replyEmbeds(tournamentManager.getRapidTournamentCreated().build()).queueAfter(60, TimeUnit.SECONDS);
        }

        if(event.getComponentId().equals("puzzlewatch")){
            DailyCommand dailypuzzle = new DailyCommand(this.ButtonClient);
            event.reply(dailypuzzle.getPuzzleGame()).queue();
        }

        if(event.getComponentId().equals("blitztv")){
            event.reply(tv.getBlitz()).queue();
        }else if(event.getComponentId().equals("bullettv")){
            event.reply(tv.getBullet()).queue();
        }else if(event.getComponentId().equals("rapidtv")){
            event.reply(tv.getRapid()).queue();
        }



    }



}
