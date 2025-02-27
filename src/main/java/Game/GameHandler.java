package Game;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Updates;
import org.bson.Document;

/**
 * handler for updating and creating user games against the engine
 */
public class GameHandler {

    private MongoCollection<Document> gamesCollection;

    public GameHandler(MongoCollection<Document> games){
        this.gamesCollection = games;
    }

    /**
     * create the game for given game schema
     * @param game the game schema
     * @throws GameException weather user has hit more than 1 game
     */
    public void createGame(GameSchema game) throws GameException{
        if(this.gamesCollection.find(new Document("userid", game.getUserid())).first() == null){
            Document gameDoc = new Document("userid", game.getUserid())
                    .append("fen", game.getFen())
                    .append("depth", game.getDepth());

            this.gamesCollection.insertOne(gameDoc);
        }else {
           throw new GameException("Your game is already in progress! You can only play 1 game at a time");
        }
    }

    /**
     * look up the user game
     * @param userid the user id
     * @return the game for the given user id
     * @throws GameException game not found
     */
    public GameSchema lookUpGame(String userid) throws GameException {
        Document game = this.gamesCollection.find(new Document("userid", userid)).first();

        if(game != null){
            return new GameSchema(userid, game.getString("fen"), game.getInteger("depth"));
        }

        throw new GameException("Game not created! Please use **/playengine <level>** to start a new game against the engine!");
    }

    /**
     * update the game's fen for given user id
     * @param userid the user id
     * @param fen the fen to be updated
     * @throws GameException no game found
     */
    public void updateFen(String userid, String fen) throws GameException {
       Document game = this.gamesCollection.find(new Document("userid", userid)).first();

       if(game != null){
           this.gamesCollection.updateOne(game, Updates.set("fen", fen));
       }else{
          throw new GameException("No game found!");
       }
    }

    /**
     * update the engine depth
     * @param userid the user id
     * @param depth the depth to be updated
     * @throws GameException no game found
     */
    public void updateDepth(String userid, Integer depth) throws GameException {
        Document game = this.gamesCollection.find(new Document("userid", userid)).first();

        if(game != null){
            this.gamesCollection.updateOne(game, Updates.set("depth", depth));
        }else{
            throw new GameException("No game found! Please create a game by running **/playengine <level>**");
        }
    }




}
