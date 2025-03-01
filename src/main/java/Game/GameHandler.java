package Game;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Updates;
import org.bson.Document;


public class GameHandler {

    private MongoCollection<Document> gamesCollection;

    public GameHandler(MongoCollection<Document> games){
        this.gamesCollection = games;
    }

    
    public void createGame(GameSchema game) throws NoGameException{
        if(this.gamesCollection.find(new Document("userid", game.getUserid())).first() == null){
            Document gameDoc = new Document("userid", game.getUserid())
                    .append("fen", game.getFen())
                    .append("depth", game.getDepth());

            this.gamesCollection.insertOne(gameDoc);
        }else {
           throw new NoGameException("Your game is already in progress! You can only play 1 game at a time");
        }
    }

   
    public GameSchema lookUpGame(String userid) throws NoGameException {
        Document game = this.gamesCollection.find(new Document("userid", userid)).first();

        if(game != null){
            return new GameSchema(userid, game.getString("fen"), game.getInteger("depth"));
        }

        throw new NoGameException("Game not created! Please use **/playengine <level>** to start a new game against the engine!");
    }

    
    public void updateFen(String userid, String fen) throws NoGameException {
       Document game = this.gamesCollection.find(new Document("userid", userid)).first();

       if(game != null){
           this.gamesCollection.updateOne(game, Updates.set("fen", fen));
       }else{
          throw new NoGameException("No game found!");
       }
    }

    
    public void updateDepth(String userid, Integer depth) throws NoGameException {
        Document game = this.gamesCollection.find(new Document("userid", userid)).first();

        if(game != null){
            this.gamesCollection.updateOne(game, Updates.set("depth", depth));
        }else{
            throw new NoGameException("No game found! Please create a game by running **/playengine <level>**");
        }
    }


}
