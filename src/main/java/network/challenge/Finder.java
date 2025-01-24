package network.challenge;

import com.mongodb.client.MongoCollection;
import database.Search;
import org.bson.Document;

public class Finder {

    private final MongoCollection<Document> networkPlayers;;
    private final Search search = new Search();

    public Finder(MongoCollection<Document> networkPlayers) {
        this.networkPlayers = networkPlayers;
    }

    public String findPreferencePl(String discordid){
        return search.search("id", discordid, this.networkPlayers, "pl");
    }

    public String findPreferenceTc(String discordid){
        return search.search("id", discordid, this.networkPlayers, "ptc");
    }

    public boolean findConnected(String discordid){
      return !search.exists("id", discordid, this.networkPlayers);
    }

    public Document findPlayer(String discordid){
        return this.networkPlayers.find(new Document("id", discordid)).first();
    }



}
