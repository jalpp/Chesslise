package network.challenge;

import com.mongodb.client.MongoCollection;
import org.bson.Document;

/**
 * Action class to handle the network action
 */
public class Action {

    private final MongoCollection<Document> networkChallenges;
    private final MongoCollection<Document> networkPlayers;
    private final Finder finder;


    public Action(MongoCollection<Document> networkChallenges, MongoCollection<Document> networkPlayers) {
        this.networkChallenges = networkChallenges;
        this.networkPlayers = networkPlayers;
        this.finder = new Finder(networkPlayers);
    }


    /**
     * Get the network challenges collection
     *
     * @return the network challenges collection
     */
    public MongoCollection<Document> getNetworkChallenges() {
        return networkChallenges;
    }

    /**
     * Get the network players collection
     *
     * @return the network players collection
     */
    public MongoCollection<Document> getNetworkPlayers() {
        return networkPlayers;
    }

    /**
     * Get the finder
     *
     * @return the finder
     */
    public Finder getFinder() {
        return finder;
    }
}
