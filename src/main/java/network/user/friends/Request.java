package network.user.friends;

import com.mongodb.client.MongoCollection;
import network.challenge.Finder;
import org.bson.Document;

public class Request {


    private final MongoCollection<Document> networkPlayers;
    private final Finder finder;

    public Request(MongoCollection<Document> networkPlayers) {
        this.networkPlayers = networkPlayers;
        this.finder = new Finder(networkPlayers);
    }

    public MongoCollection<Document> getNetworkPlayers() {
        return networkPlayers;
    }

    public Finder getFinder(){
        return this.finder;
    }



}
