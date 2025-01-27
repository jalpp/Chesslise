package runner;

import com.mongodb.client.MongoCollection;
import database.MongoConnect;
import discord.mainhandler.CommandBuilder;
import discord.mainhandler.CommandHandler;
import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bson.Document;

/**
 * Main class to run Chesslise
 */
public class Main extends ListenerAdapter {

    private static JDA jda;

    public static final Dotenv dotenv = Dotenv.load();

    private static MongoCollection<Document> networkPlayers;

    private static MongoCollection<Document> networkChallenges;

    public static void main(String[] args) {

        boolean IS_BETA = dotenv.get("ENV_BETA").equalsIgnoreCase("true");

        JDABuilder jdaBuilder = JDABuilder.createDefault(IS_BETA ? dotenv.get("DISCORD_BETA_TOKEN") : dotenv.get("DISCORD_PROD_TOKEN"));

        jdaBuilder.setStatus(OnlineStatus.ONLINE);

        jdaBuilder.addEventListeners(new Main());
        jdaBuilder.addEventListeners(new CommandHandler());

        try {
            jda = jdaBuilder.build();
            MongoConnect.main(args);
            networkChallenges = MongoConnect.getNetworkChallenges();
            networkPlayers = MongoConnect.getNetworkPlayers();
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }

        CommandBuilder builder = new CommandBuilder(jda.updateCommands());
        builder.register();

    }

    /**
     * Get the network challenges collection
     *
     * @return the network challenges collection
     */
    public static MongoCollection<Document> getNetworkChallenges() {
        return networkChallenges;
    }

    /**
     * Get the network players collection
     *
     * @return the network players collection
     */
    public static MongoCollection<Document> getNetworkPlayers() {
        return networkPlayers;
    }


}
