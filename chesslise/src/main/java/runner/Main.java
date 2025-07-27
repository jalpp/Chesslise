package runner;

import org.bson.Document;

import com.mongodb.client.MongoCollection;

import database.MongoConnect;
import discord.mainhandler.CommandBuilder;
import discord.mainhandler.CommandHandler;
import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Main extends ListenerAdapter {

    private static JDA jda;

    public static final Dotenv dotenv = Dotenv.load();

    private static MongoCollection<Document> networkPlayers;

    private static MongoCollection<Document> networkChallenges;

    private static MongoCollection<Document> gamesCollection;

    private static MongoCollection<Document> settingCollection;

    private static MongoCollection<Document> puzzleCollection;

    public static void main(String[] args) {

        boolean IS_BETA = dotenv.get("ENV_BETA").equalsIgnoreCase("true");

        JDABuilder jdaBuilder = JDABuilder
                .createDefault(IS_BETA ? dotenv.get("DISCORD_BETA_TOKEN") : dotenv.get("DISCORD_PROD_TOKEN"));

        jdaBuilder.setStatus(OnlineStatus.ONLINE);

        jdaBuilder.addEventListeners(new Main());
        jdaBuilder.addEventListeners(new CommandHandler());

        try {
            System.out.println("[Chesslise Status]: Logging in " + (IS_BETA ? "Beta" : "Production") + " Instance...");
            jda = jdaBuilder.build();
            System.out
                    .println("[Chesslise Status]: Connecting to " + (IS_BETA ? "Beta" : "Production") + " Database...");
            MongoConnect.main(args);
            networkChallenges = MongoConnect.getNetworkChallenges();
            networkPlayers = MongoConnect.getNetworkPlayers();
            gamesCollection = MongoConnect.getGamesCollection();
            settingCollection = MongoConnect.getSettingCollection();
            puzzleCollection = MongoConnect.getPuzzleCollection();

            System.out.println("[Chesslise Status]: " + (IS_BETA ? "Beta" : "Production") + " Successfully Running");
            System.out.println("[Chesslise Status]: Successfully Connected To Database");
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }

        CommandBuilder builder = new CommandBuilder(jda.updateCommands());
        builder.register();

    }

    public static MongoCollection<Document> getNetworkChallenges() {
        return networkChallenges;
    }

    public static MongoCollection<Document> getNetworkPlayers() {
        return networkPlayers;
    }

    public static MongoCollection<Document> getGamesCollection() {
        return gamesCollection;
    }

    public static MongoCollection<Document> getSettingCollection() {
        return settingCollection;
    }

    public static MongoCollection<Document> getPuzzleCollection() {
        return puzzleCollection;
    }

}
