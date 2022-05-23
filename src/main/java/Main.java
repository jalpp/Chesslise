
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

        action.addCommands(new CommandData("play", "Start a new rapid Lichess Game")).complete();
        action.addCommands(new CommandData("help", "See Commands Info!")).complete();a


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

                 Game game = new Game(client, "rapid", "casual");
                 event.replyEmbeds(game.getNewGame().build()).queue();
                break;
            default:

        }






    }

    public void onButtonClick(ButtonClickEvent event){



    }
    
     public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        Client client = Client.basic();
         
         // help method note ,streaming is to check for invalid input


        String command = event.getMessage().getContentRaw();
        CommandInfo help = new CommandInfo();
        if (command.equals(",help") || command.equals(",streaming") || command.equals(",profile")) { 
          event.getChannel().sendMessage(help.getCommandInfo().build()).queue();
        }


        String inv = event.getMessage().getContentRaw();
        InviteMe inviteLink = new InviteMe();
        if (inv.equals(",invite")) {
           event.getChannel().sendMessage(inviteLink.getInviteInfo().build()).queue();
        }


        String puzzle = event.getMessage().getContentRaw();

        if (puzzle.equals(",daily")) {

                DailyCommand dailyCommand = new DailyCommand(client);
                event.getChannel().sendMessage(dailyCommand.getPuzzleData().build()).queue();

            }




        String[] commandtwo = event.getMessage().getContentRaw().split(" ");

        UserProfile userProfile = new UserProfile(client, commandtwo);

        if (commandtwo[0].equals(",profile")) {
            event.getChannel().sendMessage(userProfile.getUserProfile().build()).queue();

        }
         
        String[] gameCommand = event.getMessage().getContentRaw().split(" ");
        UserGame games = new UserGame(client, gameCommand[1]);
        if(gameCommand[0].equals(",watch")){
            event.getChannel().sendMessage(games.getUserGames()).queue();
        }
         
        String[] gameReview = event.getMessage().getContentRaw().split(" ");

        GameReview stockFishReview = new GameReview(client, gameReview[1]);
        if(gameReview[0].equals(",review")){
            event.getChannel().sendMessage(stockFishReview.getGameReviewData().build()).queue();
        }
 





        String[] toplist = event.getMessage().getContentRaw().split(" ");
        leaderBoard lichessBoard = new leaderBoard(client);

        if (toplist[0].equals(",top10")) {

            if (toplist[1].equals("blitz")) {

                // top 10 list for blitz some guys have 2900 lichess rating GODz
                event.getChannel().sendMessage(lichessBoard.getBlitzBoard().build()).queue();

            }else if (toplist[1].equals("classical")) {

                // top10 list for classical
                event.getChannel().sendMessage(lichessBoard.getClassicalBoard().build()).queue();

            } else if (toplist[1].equals("rapid")) {

                // top10 list for rapid you get it
               event.getChannel().sendMessage(lichessBoard.getRapidBoard().build()).queue();

            }

            else if (toplist[1].equals("bullet")) {

                event.getChannel().sendMessage(lichessBoard.getBulletBoard().build()).queue();

            }

            else if (toplist[1].equals("ultrabullet")) {


                event.getChannel().sendMessage(lichessBoard.getUltraBoard().build()).queue();


            }



        }



        String[] checkStatus = event.getMessage().getContentRaw().split(" ");

        UserStreaming userStreaming = new UserStreaming(client, checkStatus);
        LiveStreamers watch = new LiveStreamers(client);



            if (checkStatus[0].equals(",streaming")) {

                event.getChannel().sendMessage(userStreaming.getStreamingStatus().build()).queue();
                event.getChannel().sendMessage("\uD83C\uDF99️ Why not check out this streamers... \uD83C\uDF99️").queue();
                event.getChannel().sendMessage(watch.getTv().build()).queue();

            }






        String[] teams = event.getMessage().getContentRaw().split(" ");

        UserTeam userTeam = new UserTeam(client, teams);

        if (teams[0].equals(",team")) {

         event.getChannel().sendMessage(userTeam.getUserTeam().build()).queue();

        }



        String[] arenaResult = event.getMessage().getContentRaw().split(" ");
        UserArena userArena = new UserArena(client, arenaResult[1]);

        if (arenaResult[0].equals(",arena")) {
            event.getChannel().sendMessage(userArena.getUserArena().build()).queue();

        }



        String[] stormDash = event.getMessage().getContentRaw().split(" ");
        UserDashboard userDashboard = new UserDashboard(client, stormDash);

        if (stormDash[0].equals(",stormdash")) {

            event.getChannel().sendMessage(userDashboard.getUserDashboard().build()).queue();

        }



         String[] challenge1 = event.getMessage().getContentRaw().split(" ");
         Game Chessgame = new Game(client, challenge1[1], challenge1[2]);

         if(challenge1[0].equals(",play")){

             event.getChannel().sendMessage(Chessgame.getNewGame().build()).queue();

         }

         String[] engine = event.getMessage().getContentRaw().split(" ");
         EngineEval eval = new EngineEval(client, engine[1]);

         if(engine[0].equals(",engine")){
           event.getChannel().sendMessage(eval.getEval().build()).queue();


         }














    }



   




}



































