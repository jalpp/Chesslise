package network.user.friends;

import com.mongodb.client.MongoCollection;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.bson.Document;

/**
 * SendFriendRequest class to handle the send friend request
 */
public class SendFriendRequest extends Request {


    public SendFriendRequest(MongoCollection<Document> players) {
        super(players);

    }

    /**
     * Send the friend request
     *
     * @param event the slash command event
     */
    public void sendFriendReq(SlashCommandInteractionEvent event) {
        event.deferReply(true).queue();

        String friendRequest = addFriend(event.getUser().getId(), event.getOption("frienduser").getAsString().toLowerCase());

        event.getHook().sendMessage(friendRequest).queue();
    }

    /**
     * Add the friend
     *
     * @param discordId            the discord id
     * @param targetFriendusername the target friend username
     * @return the message for adding the friend
     */
    public String addFriend(String discordId, String targetFriendusername) {
        Document current = this.getNetworkPlayers().find(new Document("id", discordId)).first();
        Document friend = this.getNetworkPlayers().find(new Document("username", targetFriendusername)).first();

        if (current == null) {
            return "You must connect your account before sending a friend request! Run /connect to connect your account!";
        }

        if (friend == null) {
            return "Targeted user not found in Chesslise network! Please ask them to run install Chesslise from app store [here](https://discord.com/discovery/applications/930544707300393021) and run /connect, finally they can accept your friend request running /acceptfriendrequest " + discordId;
        }

        if (current.getString("username").equalsIgnoreCase(friend.getString("username"))) {
            return "You can't send friend request to yourself!";
        }

        this.getNetworkPlayers().updateOne(current, new Document("$push", new Document("requestout", friend.getString("id"))));
        this.getNetworkPlayers().updateOne(friend, new Document("$push", new Document("requestin", current.getString("id"))));

        return "Successfully send friend request to " + targetFriendusername + " run /viewfriends to see the status of friendship! You can also send friend request via Discord to connect and start playing (recommended)";
    }

}
