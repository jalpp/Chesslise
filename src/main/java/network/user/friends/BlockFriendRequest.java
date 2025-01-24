package network.user.friends;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Updates;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.bson.Document;
// TODO: test blocking of a friend
public class BlockFriendRequest extends Request{

    private RemoveFriendRequest removeFriendRequest;

    public BlockFriendRequest(MongoCollection<Document> players){
        super(players);
        this.removeFriendRequest = new RemoveFriendRequest(players);
    }

    public void blockFriendRequest(SlashCommandInteractionEvent event){
        event.deferReply(true).queue();

        String blocked = blockMf(event.getUser().getId(), event.getOption("blockid").getAsString());

        event.getHook().sendMessage(blocked).queue();
    }

   public String blockMf(String discordid, String blockedid){
        Document current = this.getNetworkPlayers().find(new Document("id", discordid)).first();
        Document blocked = this.getNetworkPlayers().find(new Document("username", blockedid)).first();

       if(current == null){
           return "You must connect your account before blocking a friend! Run /connect to connect your account!";
       }

       if(blocked == null){
           return "The given discord id is not valid! Make sure its valid by viewing the requests in /viewfriends <option> ";
       }

       if(!current.getList("friends", String.class).contains(blocked.getString("id"))){
           return "The following player isn't your friend and can't be blocked!";
       }

       this.getNetworkPlayers().updateOne(current, Updates.push("blocked", blocked.getString("id")));
       String removed = this.removeFriendRequest.removeFriend(discordid, blocked.getString("username"));

       return "Successfully blocked the player " + blocked.getString("username") + " and " + removed;

   }



}
