
import chariot.Client;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;
import org.jetbrains.annotations.NotNull;

import javax.security.auth.login.LoginException;


public class Main extends ListenerAdapter {

    private static JDABuilder jdaBuilder;
    private static JDA jda;



    public static void main(String[] args) {

        String TOKEN;

        jdaBuilder = JDABuilder.createDefault(TOKEN);// string token

        jdaBuilder.setStatus(OnlineStatus.ONLINE);
        jdaBuilder.setActivity(Activity.watching("Playing Lichess"));
        jdaBuilder.addEventListeners(new Main());


        try {
            jda = jdaBuilder.build();

        } catch (LoginException exception) {
            exception.printStackTrace();
        }

        CommandListUpdateAction action = jda.updateCommands();

         action.addCommands(new CommandData("play", "Start a new Lichess Game").addOption(OptionType.STRING, "variant", "choose mode blitz, rapid etc", true).addOption(OptionType.STRING, "challengetype", "rated/casual", true)).complete();
        action.addCommands(new CommandData("help", "See Commands Info!")).complete();
        action.addCommands(new CommandData("profile", "See Lichess Profile of given User").addOption(OptionType.STRING, "username", "input Lichess username", true)).complete();
        action.addCommands(new CommandData("livestreaming", "See Lichess LiveStreams for given User").addOption(OptionType.STRING, "streamername", "input Lichess username", true)).complete();
        action.addCommands(new CommandData("streamers", "See current Live Streamers")).complete();
        action.addCommands(new CommandData("dailypuzzle", "Do daily chess puzzle")).complete();
        action.addCommands(new CommandData("watch", "watch games of a particular Lichess User").addOption(OptionType.STRING, "watchuser", "input Lichess username", true)).complete();
        action.addCommands(new CommandData("gamereview", "review Stockfish analyzed games").addOption(OptionType.STRING, "gamelook", "input Lichess gameID", true)).complete();
        action.addCommands(new CommandData("top10", "see top 10 list for given variant(blitz classical etc) ").addOption(OptionType.STRING, "top", "input Lichess variant", true)).complete();
        action.addCommands(new CommandData("arena", "see arena leaderboard list").addOption(OptionType.STRING, "arenaid", "input Lichess arena link", true)).complete();
        action.addCommands(new CommandData("team", "view Lichess team board").addOption(OptionType.STRING, "teamboard", "input Lichess team name", true)).complete();
        action.addCommands(new CommandData("challengeauth", "Send direct Lichess Challenge with Personal Token Login").addOption(OptionType.STRING, "logincha", "input your Lichess Personal API Token").addOption(OptionType.STRING, "userlog", "input opponent's username")).complete();
        action.addCommands(new CommandData("chatauth", "Send direct chat message to a user").addOption(OptionType.STRING, "logicidchat", "input your Lichess Personal API Token", true).addOption(OptionType.STRING, "messagereceiver", "input receiver's username", true).addOption(OptionType.STRING, "messageid", "input your message", true)).complete();
        action.addCommands(new CommandData("scheduletournament", "schedule Lichess arena from Discord").addOption(OptionType.STRING, "apipassword", "Input your Lichess Personal API Token", true).addOption(OptionType.STRING, "timeformat", "Input tournament's variant: blitz, classical etc.", true)).complete();
        action.addCommands(new CommandData("invite", "Invite me to your servers!")).complete();
        action.addCommands(new CommandData("stormdash" , "See storm dashboard for given user!").addOption(OptionType.STRING, "storm", "Input Lichess Username")).complete();
    }

       public void onSlashCommand(SlashCommandEvent event){
        String name = event.getName();
        Client client = Client.basic();


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
                String[] insert = {"", userID};
                UserProfile userProfile = new UserProfile(client, insert);
                event.replyEmbeds(userProfile.getUserProfile().build()).queue();
                break;
            case "livestreaming":
                String streamerID = event.getOption("streamername").getAsString();
                String[] addStreamer = {"", streamerID};
                UserStreaming userStreaming = new UserStreaming(client, addStreamer);

                event.replyEmbeds(userStreaming.getStreamingStatus().build()).queue();
                break;
            case "streamers":
                LiveStreamers liveStreamers = new LiveStreamers(client);
                event.replyEmbeds(liveStreamers.getTv().build()).queue();
                break;
            case "dailypuzzle":
                DailyCommand dailyCommand = new DailyCommand(client);
                event.replyEmbeds(dailyCommand.getPuzzleData().build()).queue();
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

                    event.getChannel().sendMessage(lichessBoard.getBlitzBoard().build()).queue();

                }else if ((variantID.equals("classical"))) {


                    event.getChannel().sendMessage(lichessBoard.getClassicalBoard().build()).queue();

                }else if ((variantID.equals("rapid"))) {

                    event.getChannel().sendMessage(lichessBoard.getRapidBoard().build()).queue();

                }

                else if (variantID.equals("bullet")) {

                    event.getChannel().sendMessage(lichessBoard.getBulletBoard().build()).queue();
                }

                else if (variantID.equals("ultrabullet")) {

                    event.getChannel().sendMessage(lichessBoard.getUltraBoard().build()).queue();

                }
                break;

            case "arena":
                 String arenaLink = event.getOption("arenaid").getAsString();
                 UserArena userArena = new UserArena(client,arenaLink);
                 event.replyEmbeds(userArena.getUserArena().build()).queue();
                break;

            case "team":
                String teamLink = event.getOption("teamboard").getAsString();
                String[] teamName = {"", teamLink};
                UserTeam userTeam = new UserTeam(client, teamName);
                event.replyEmbeds(userTeam.getUserTeam().build()).queue();
                break;

            case "challengeauth":
               final String password = event.getOption("loginch").getAsString();
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
                event.getChannel().sendMessage(chat.getDelayMessage().build()).queueAfter(20, TimeUnit.SECONDS);
                event.getChannel().sendMessage(chat.getChatStatus().build()).queueAfter(42, TimeUnit.SECONDS);

                break;
                
                
           case "scheduletournament":
                final String apiPassword = event.getOption("apipassword").getAsString();
                String timeFormat = event.getOption("timeformat").getAsString();
                AdminLoginCreateTournament createTournament = new AdminLoginCreateTournament(apiPassword, timeFormat);
                event.reply("Processing your Request...").queue();
                event.getChannel().sendMessage("connecting to lichess..").queueAfter(10, TimeUnit.SECONDS);
                event.getChannel().sendMessage(createTournament.getCreatedTournament().build()).queueAfter(20, TimeUnit.SECONDS);
                break;  
                
                
            case "stormdash":
                String stormUser = event.getOption("storm").getAsString();
                String[] userArray = {"stormCommand", stormUser};
                UserDashboard userDashboard = new UserDashboard(client, userArray);
                event.replyEmbeds(userDashboard.getUserDashboard().build()).queue();
                break;      

            default:

        }






    }

  
    
    

   




}



































