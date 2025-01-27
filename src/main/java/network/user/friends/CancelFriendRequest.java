package network.user.friends;

import com.mongodb.client.MongoCollection;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.bson.Document;

/**
 * CancelFriendRequest class to handle the cancel friend request
 */
public class CancelFriendRequest extends Request {


    public CancelFriendRequest(MongoCollection<Document> players) {
        super(players);

    }

    /**
     * Cancel the friend request
     *
     * @param event the slash command event
     */
    public void cancelFriendRequest(SlashCommandInteractionEvent event) {
        event.deferReply(true).queue();

        String cancelFked = cancelFriend(event.getUser().getId(), event.getOption("cancelid").getAsString());

        event.getHook().sendMessage(cancelFked).queue();
    }

    /**
     * Cancel the friend request
     *
     * @param discordid              the discord id
     * @param incommingFriendRequest the incoming friend request
     * @return the message for canceling the friend
     */
    public String cancelFriend(String discordid, String incommingFriendRequest) {
        Document current = this.getNetworkPlayers().find(new Document("id", discordid)).first();
        Document cancel = this.getNetworkPlayers().find(new Document("id", incommingFriendRequest)).first();

        if (current == null) {
            return "You must connect your account before canceling a friend request! Run /connect to connect your account!";
        }

        if (cancel == null) {
            return "The given discord id is not valid! Make sure its valid by viewing the requests in /viewfriends <option> ";
        }

        this.getNetworkPlayers().updateOne(current, new Document("$pull", new Document("requestin", cancel.getString("id"))));
        this.getNetworkPlayers().updateOne(cancel, new Document("$pull", new Document("requestout", current.getString("id"))));

        return "Successfully canceled the friend request from " + incommingFriendRequest;

    }

}
