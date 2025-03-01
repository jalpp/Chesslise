package database;

import com.mongodb.client.MongoCollection;
import org.bson.Document;


public class Search {

    
    public String search(String targetSearch, String targetID, MongoCollection<Document> collection, String returnId) {
        Document query = new Document(targetSearch, targetID);

        Document result = collection.find(query).first();

        if (result != null) {

            return result.getString(returnId);

        } else {
            return null;
        }
    }

    public boolean exists(String targetSearch, String targetID, MongoCollection<Document> collection) {
        Document query = new Document(targetSearch, targetID);

        Document result = collection.find(query).first();

        return result != null;
    }
}
