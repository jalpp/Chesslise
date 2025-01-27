package network.challenge;

import com.mongodb.client.MongoCollection;
import database.Search;
import org.bson.Document;

/**
 * Finder class to handle the finding things in the network
 */
public class Finder {

    private final MongoCollection<Document> networkPlayers;
    ;
    private final Search search = new Search();

    public Finder(MongoCollection<Document> networkPlayers) {
        this.networkPlayers = networkPlayers;
    }

    /**
     * Find the platform preference of the player
     *
     * @param discordid the discord id
     * @return the preference
     */
    public String findPreferencePl(String discordid) {
        return search.search("id", discordid, this.networkPlayers, "pl");
    }

    /**
     * Find the tc preference of the player
     *
     * @param discordid the discord id
     * @return the preference
     */
    public String findPreferenceTc(String discordid) {
        return search.search("id", discordid, this.networkPlayers, "ptc");
    }

    /**
     * Find if the player is connected
     *
     * @param discordid the discord id
     * @return if the player is connected
     */
    public boolean findConnected(String discordid) {
        return !search.exists("id", discordid, this.networkPlayers);
    }

    /**
     * Find the player
     *
     * @param discordid the discord id
     * @return the player Document
     */
    public Document findPlayer(String discordid) {
        return this.networkPlayers.find(new Document("id", discordid)).first();
    }


}
