package network.user.friends;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Updates;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.bson.Document;

/**
 * RemoveFriendRequest class to handle the remove friend request
 */
public class RemoveFriendRequest extends Request {


    public RemoveFriendRequest(MongoCollection<Document> players) {
        super(players);
    }

    /**
     * Remove the friend request
     *
     * @param event the slash command event
     */
    public void removeFriendRequest(SlashCommandInteractionEvent event) {
        event.deferReply(true).queue();

        String removedFked = removeFriend(event.getUser().getId(), event.getOption("removeid").getAsString());

        event.getHook().sendMessage(removedFked).queue();
    }

    /**
     * Remove the friend
     *
     * @param discordid             the discord id
     * @param incomingFriendRequest the incoming friend request
     * @return the message for removing the friend
     */
    public String removeFriend(String discordid, String incomingFriendRequest) {
        Document current = this.getNetworkPlayers().find(new Document("id", discordid)).first();
        Document remove = this.getNetworkPlayers().find(new Document("username", incomingFriendRequest)).first();

        if (current == null) {
            return "You must connect your account before removing a friend! Run /connect to connect your account!";
        }

        if (remove == null) {
            return "The given discord username is not valid! Make sure its valid by viewing the requests in /viewfriends <option> ";
        }

        if (!current.getList("friends", String.class).contains(remove.getString("id"))) {
            return "The following player isn't your friend and can't be removed!";
        }

        this.getNetworkPlayers().updateOne(current, Updates.combine(Updates.pull("friends", remove.getString("id")),
                Updates.pull("friendsusers", remove.getString("username"))));
        this.getNetworkPlayers().updateOne(remove, Updates.combine(Updates.pull("friends", current.getString("id")), Updates.pull("friendsusers", current.getString("username"))));

        return "Successfully removed the player " + remove.getString("username") + " from your friend list!";
    }


}
