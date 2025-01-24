package runner;

import com.mongodb.client.MongoCollection;
import database.MongoConnect;
import discord.mainhandler.CommandHandler;
import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.IntegrationType;
import net.dv8tion.jda.api.interactions.InteractionContextType;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;
import network.user.PreferenceFr;
import network.user.PreferencePl;
import network.user.PreferenceTc;
import org.bson.Document;


public class Main extends ListenerAdapter {

    private static JDA jda;

    public static final Dotenv dotenv = Dotenv.load();

    private static MongoCollection<Document> networkPlayers;

    private static MongoCollection<Document> networkChallenges;

    public static void main(String[] args) {

        JDABuilder jdaBuilder = JDABuilder.createDefault(dotenv.get("ENV_BETA").equalsIgnoreCase("true") ? dotenv.get("DISCORD_BETA_TOKEN") : dotenv.get("DISCORD_PROD_TOKEN"));

        jdaBuilder.setStatus(OnlineStatus.ONLINE);


        jdaBuilder.addEventListeners(new Main());
        jdaBuilder.addEventListeners(new CommandHandler());


        try {
            jda = jdaBuilder.build();
            MongoConnect.main(args);
            networkChallenges = MongoConnect.getNetworkChallenges();
            networkPlayers = MongoConnect.getNetworkPlayers();
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        CommandListUpdateAction commands = jda.updateCommands();
        // <--------- SLASH COMMANDS -------------->
        commands.addCommands(Commands.slash("profilecc", "view cc profiles").setContexts(InteractionContextType.ALL).setIntegrationTypes(IntegrationType.ALL));
        commands.addCommands(Commands.slash("profile", "See Lichess Profile of given user").setContexts(InteractionContextType.ALL).setIntegrationTypes(IntegrationType.ALL).addOptions(new OptionData(OptionType.STRING, "search-user", "Search Lichess username", true).setAutoComplete(true).setRequired(true)));
        commands.addCommands(Commands.slash("puzzle", "do random/daily puzzles").setContexts(InteractionContextType.ALL).setIntegrationTypes(IntegrationType.ALL).addOptions(new OptionData(OptionType.STRING, "pick-puzzle", "pick type of puzzles", true).addChoice("Lichess daily puzzle", "lip").addChoice("Chess.com daily puzzle", "cpp").addChoice("Chess.com random puzzle", "random")));
        commands.addCommands(Commands.slash("help", "View LISEBOT Commands").setContexts(InteractionContextType.ALL).setIntegrationTypes(IntegrationType.ALL));
        commands.addCommands(Commands.slash("play", "Play Live Chess Games").setContexts(InteractionContextType.ALL).setIntegrationTypes(IntegrationType.ALL));
        commands.addCommands(Commands.slash("watch", "Watch Lichess games for given user").setContexts(InteractionContextType.ALL).setIntegrationTypes(IntegrationType.ALL));
        commands.addCommands(Commands.slash("move", "make a move").addOption(OptionType.STRING, "play-move", "input chess move", true).setContexts(InteractionContextType.ALL).setIntegrationTypes(IntegrationType.ALL));
        commands.addCommands(Commands.slash("resetboard", "reset the board"));
        commands.addCommands(Commands.slash("learnchess", "Learn basic chess moves").setContexts(InteractionContextType.ALL).setIntegrationTypes(IntegrationType.ALL));
        commands.addCommands(Commands.slash("chessdb", "View chessdb eval of a position useful for openings/middelgame fens").setContexts(InteractionContextType.ALL).setIntegrationTypes(IntegrationType.ALL).addOption(OptionType.STRING, "paste-fen", "Enter fen to be analyzed", true));

        // <--------- Slash Network Commands -------------->


       
        commands.addCommands(Commands.slash("connect", "join the Chesslise network to find players")
                .setContexts(InteractionContextType.ALL).setIntegrationTypes(IntegrationType.ALL)
                .addOptions(PreferencePl.getOptionData())
                .addOptions(PreferenceTc.getOptionData())
                .addOptions(PreferenceFr.getPlayerOptionData())
                .addOptions(PreferenceFr.getOpeningOptionData())
                .addOptions(PreferenceFr.getPieceOptionData())
                .addOptions(PreferenceFr.getStyleOptionData())
        );

        commands.addCommands(Commands.slash("disconnect", "disconnect from Chesslise network and not get paired")
                .setContexts(InteractionContextType.ALL).setIntegrationTypes(IntegrationType.ALL));

        commands.addCommands(Commands.slash("setpreference", "join the Chesslise network to find players and friends")
                .setContexts(InteractionContextType.ALL).setIntegrationTypes(IntegrationType.ALL)
                .addOptions(PreferencePl.getOptionData())
                .addOptions(PreferenceTc.getOptionData())
                .addOptions(PreferenceFr.getPlayerOptionData())
                .addOptions(PreferenceFr.getOpeningOptionData())
                .addOptions(PreferenceFr.getPieceOptionData())
                .addOptions(PreferenceFr.getStyleOptionData())
        );

        commands.addCommands(Commands.slash("mychallenges", "View your challenges in the Chesslise network")
                .setContexts(InteractionContextType.ALL).setIntegrationTypes(IntegrationType.ALL)
                .addOptions(new OptionData(OptionType.STRING, "chalstatus", "Select the view configuration of the status", true).addChoice("pending", "pending")
                .addChoice("accepted", "accepted").addChoice("cancelled", "cancelled").addChoice("completed", "completed")));
        commands.addCommands(Commands.slash("pairchallenge", "Attempt to find a challenge in Chesslise network")
                .setContexts(InteractionContextType.ALL).setIntegrationTypes(IntegrationType.ALL));
        commands.addCommands(Commands.slash("pairchallengenetwork", "Attempt to send a challenge in your friend network")
                .setContexts(InteractionContextType.ALL).setIntegrationTypes(IntegrationType.ALL));
        commands.addCommands(Commands.slash("seekchallenge", "Create a challenge and seek for others to accept it")
                .setContexts(InteractionContextType.ALL).setIntegrationTypes(IntegrationType.ALL));
        commands.addCommands(Commands.slash("cancelchallenge", "Cancel a challenge by challenge ID")
                .setContexts(InteractionContextType.ALL).setIntegrationTypes(IntegrationType.ALL)
                .addOption(OptionType.STRING, "challid", "provide the challenge id", true));
        commands.addCommands(Commands.slash("completechallenge", "Complete a challenge by challenge ID")
                .setContexts(InteractionContextType.ALL).setIntegrationTypes(IntegrationType.ALL)
                .addOption(OptionType.STRING, "cchallid", "provide the challenge id", true));
        commands.addCommands(Commands.slash("findfriend", "find a new friend within your network or globally")
                .setContexts(InteractionContextType.ALL).setIntegrationTypes(IntegrationType.ALL));
        commands.addCommands(Commands.slash("sendfriendrequest", "Send friend request by providing target username")
                .setContexts(InteractionContextType.ALL).setIntegrationTypes(IntegrationType.ALL)
                .addOption(OptionType.STRING, "frienduser", "provide target username", true));
        commands.addCommands(Commands.slash("acceptfriendrequest", "Accept friend request by providing target friend discord id")
                .setContexts(InteractionContextType.ALL).setIntegrationTypes(IntegrationType.ALL)
                .addOption(OptionType.STRING, "friendid", "provide target discord id", true));
        commands.addCommands(Commands.slash("cancelfriendrequest", "Cancel an incomming friend request by providing discord id")
                .setContexts(InteractionContextType.ALL).setIntegrationTypes(IntegrationType.ALL)
                .addOption(OptionType.STRING, "cancelid", "provide target discord id", true));
        commands.addCommands(Commands.slash("removefriend", "Remove a friend from friend list by providing discord id")
                .setContexts(InteractionContextType.ALL).setIntegrationTypes(IntegrationType.ALL)
                .addOption(OptionType.STRING, "removeid", "provide friend discord username", true)
                .setContexts(InteractionContextType.ALL).setIntegrationTypes(IntegrationType.ALL));
        commands.addCommands(Commands.slash("blockfriend", "Block a friend who has not being friendly by providing discord id")
                .setContexts(InteractionContextType.ALL).setIntegrationTypes(IntegrationType.ALL)
                .addOption(OptionType.STRING, "blockid", "provide friend discord username", true));
        commands.addCommands(Commands.slash("viewfriends", "View various friend requests and friend list")
                .setContexts(InteractionContextType.ALL).setIntegrationTypes(IntegrationType.ALL)
                .addOptions(new OptionData(OptionType.STRING, "requesttype", "Select request type", true).addChoice("friendlist", "flist")
                .addChoice("incomming", "fin").addChoice("outgoing", "fout")));




        // <------- MESSAGE COMMANDS ---------->
        commands.addCommands(Commands.context(Command.Type.MESSAGE, "Lichess Daily Puzzle"));
        commands.addCommands(Commands.context(Command.Type.MESSAGE, "Play Chess"));
        commands.addCommands(Commands.context(Command.Type.MESSAGE, "View Lichess Broadcasts"));
        commands.addCommands(Commands.context(Command.Type.MESSAGE, "Watch GMs"));
        commands.addCommands(Commands.context(Command.Type.MESSAGE, "Chess.com Daily Puzzle"));


        commands.queue();


        // LichessBotRunner.main(args); // sun set of Liquid Chess Engine (turn it on if you want to run locally) and import the Engine module


    }

    public static MongoCollection<Document> getNetworkChallenges() {
        return networkChallenges;
    }

    public static MongoCollection<Document> getNetworkPlayers() {
        return networkPlayers;
    }

    @Override
    public void onReady(ReadyEvent event) {
        JDA jda = event.getJDA();
        int guildCount = jda.getGuilds().size();

        jda.getPresence().setActivity(Activity.watching("V16 Servers: " + guildCount + " Join CSSN!"));
    }


}
