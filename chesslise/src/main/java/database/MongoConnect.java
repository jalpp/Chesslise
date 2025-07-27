package database;

import org.bson.Document;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import runner.Main;

public class MongoConnect {

    private static MongoCollection<Document> networkPlayers;

    private static MongoCollection<Document> networkChallenges;

    private static MongoCollection<Document> gamesCollection;

    private static MongoCollection<Document> settingCollection;

    private static MongoCollection<Document> puzzleCollection;

    public MongoConnect() {
    }

    public static void connect() {
        boolean isBeta = Main.dotenv.get("ENV_BETA").equalsIgnoreCase("true");
        String connectionString = Main.dotenv.get("CONNECTION_STRING");
        String dbname = Main.dotenv.get("DB_NAME");
        String playerColName = Main.dotenv.get("DB_PLAYER_COL");
        String challengeColName = Main.dotenv.get("DB_CHALL_COL");
        String gameColName = Main.dotenv.get("DB_GAMES_COL");
        String settingColName = Main.dotenv.get("DB_SETTING_COL");
        String puzzleColName = Main.dotenv.get("DB_PUZZLE_SOLVE_COL");
        ServerApi serverApi = ServerApi.builder()
                .version(ServerApiVersion.V1)
                .build();

        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(connectionString))
                .serverApi(serverApi)
                .build();

        MongoClient mongoClient = MongoClients.create(settings);

        MongoDatabase database = mongoClient.getDatabase(dbname);

        networkChallenges = database.getCollection(isBeta ? challengeColName + "beta" : challengeColName);

        networkPlayers = database.getCollection(isBeta ? playerColName + "beta" : playerColName);

        gamesCollection = database.getCollection(isBeta ? gameColName + "beta" : gameColName);

        settingCollection = database.getCollection(isBeta ? settingColName + "beta" : settingColName);

        puzzleCollection = database.getCollection(isBeta ? puzzleColName + "beta" : puzzleColName);

    }

    public static void main(String[] args) {
        connect();
    }

    public static MongoCollection<Document> getNetworkPlayers() {
        return networkPlayers;
    }

    public static MongoCollection<Document> getNetworkChallenges() {
        return networkChallenges;
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
