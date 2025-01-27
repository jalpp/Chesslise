package database;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;
import runner.Main;

/**
 * Class to connect to the MongoDB database
 */
public class MongoConnect {


    private static MongoCollection<Document> networkPlayers;

    private static MongoCollection<Document> networkChallenges;


    /**
     * Instantiates a new Mongo connect.
     */
    public MongoConnect() {
    }

    /**
     * start the connection to the MongoDB database
     */
    public static void connect() {
        String connectionString = Main.dotenv.get("CONNECTION_STRING");
        String dbname = Main.dotenv.get("DB_NAME");
        String playerColName = Main.dotenv.get("DB_PLAYER_COL");
        String challengeColName = Main.dotenv.get("DB_CHALL_COL");
        ServerApi serverApi = ServerApi.builder()
                .version(ServerApiVersion.V1)
                .build();

        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(connectionString))
                .serverApi(serverApi)
                .build();

        MongoClient mongoClient = MongoClients.create(settings);

        MongoDatabase database = mongoClient.getDatabase(dbname);

        networkChallenges = database.getCollection(Main.dotenv.get("ENV_BETA").equalsIgnoreCase("true") ? challengeColName + "beta" : challengeColName);

        networkPlayers = database.getCollection(Main.dotenv.get("ENV_BETA").equalsIgnoreCase("true") ? playerColName + "beta" : playerColName);
    }

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        connect();
    }

    /**
     * Get the network players collection
     *
     * @return the network players collection
     */
    public static MongoCollection<Document> getNetworkPlayers() {
        return networkPlayers;
    }

    /**
     * Get the network challenges collection
     *
     * @return the network challenges collection
     */
    public static MongoCollection<Document> getNetworkChallenges() {
        return networkChallenges;
    }
}

