package network.challenge;

import com.mongodb.client.MongoCollection;
import org.bson.Document;

public class Action {

    private final MongoCollection<Document> networkChallenges;
    private final MongoCollection<Document> networkPlayers;
    private final Finder finder;


    public Action(MongoCollection<Document> networkChallenges, MongoCollection<Document> networkPlayers) {
        this.networkChallenges = networkChallenges;
        this.networkPlayers = networkPlayers;
        this.finder = new Finder(networkPlayers);
    }


    public MongoCollection<Document> getNetworkChallenges() {
        return networkChallenges;
    }

    public MongoCollection<Document> getNetworkPlayers() {
        return networkPlayers;
    }

    public Finder getFinder() {
        return finder;
    }
}
