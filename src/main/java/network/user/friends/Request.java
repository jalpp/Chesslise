package network.user.friends;

import com.mongodb.client.MongoCollection;
import network.challenge.Finder;
import org.bson.Document;

/**
 * Request class to handle the request
 */
public class Request {


    private final MongoCollection<Document> networkPlayers;
    private final Finder finder;

    public Request(MongoCollection<Document> networkPlayers) {
        this.networkPlayers = networkPlayers;
        this.finder = new Finder(networkPlayers);
    }

    /**
     * Get the network players
     *
     * @return the network players
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
        return this.finder;
    }


}
