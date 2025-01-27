package database;

import com.mongodb.client.MongoCollection;
import org.bson.Document;

/**
 * The class to search the MongoDB database
 */
public class Search {

    /**
     * Get general search based on params string.
     *
     * @param targetSearch the target search
     * @param targetID     the target id
     * @param collection   the collection
     * @param returnId     the return id
     * @return the string
     */
    public String search(String targetSearch, String targetID, MongoCollection<Document> collection, String returnId) {
        Document query = new Document(targetSearch, targetID);

        Document result = collection.find(query).first();

        if (result != null) {

            return result.getString(returnId);

        } else {
            return null;
        }
    }

    /**
     * Check if the document exists in the collection
     *
     * @param targetSearch the target search field
     * @param targetID     the target ID value
     * @param collection   the collection to search
     * @return true if the document exists, false otherwise
     */
    public boolean exists(String targetSearch, String targetID, MongoCollection<Document> collection) {
        Document query = new Document(targetSearch, targetID);

        Document result = collection.find(query).first();

        return result != null;
    }
}
