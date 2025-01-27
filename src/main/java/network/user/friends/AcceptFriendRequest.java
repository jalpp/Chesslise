package network.user.friends;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Updates;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import org.bson.Document;

/**
 * AcceptFriendRequest class to handle the accept friend request
 */
public class AcceptFriendRequest extends Request {


    public AcceptFriendRequest(MongoCollection<Document> players) {
        super(players);
    }

    /**
     * Accept the friend request
     *
     * @param event the slash command event
     */
    public void acceptFriendRequest(SlashCommandInteractionEvent event) {
        event.deferReply(true).queue();

        String acceptFriendMessage = acceptFriend(event.getUser().getId(), event.getOption("friendid").getAsString());

        event.getHook().sendMessage(acceptFriendMessage).queue();
    }

    /**
     * Accept the friend request
     *
     * @param discordid             the discord id
     * @param incomingFriendRequest the incoming friend request
     * @return the message for accepting the friend
     */

    public String acceptFriend(String discordid, String incomingFriendRequest) {
        Document current = getFinder().findPlayer(discordid);
        Document incoming = getFinder().findPlayer(incomingFriendRequest);

        if (current == null) {
            return "You must connect your account before accepting friend requests! Run /connect to connect your account!";
        }

        if (incoming == null) {
            return "The given discord id is not valid! Make sure it's valid by viewing the requests in /viewfriends <option>";
        }


        this.getNetworkPlayers().updateOne(
                new Document("id", discordid),
                Updates.combine(
                        Updates.push("friends", incoming.getString("id")),
                        Updates.push("friendsusers", incoming.getString("username")),
                        Updates.pull("requestin", incoming.getString("id"))
                )
        );

        this.getNetworkPlayers().updateOne(
                new Document("id", incomingFriendRequest),
                Updates.combine(
                        Updates.push("friends", current.getString("id")),
                        Updates.push("friendsusers", current.getString("username")),
                        Updates.pull("requestout", discordid)
                )
        );

        return "Successfully accepted the friend " + incomingFriendRequest + "! You can also view the status of the friendship in /viewfriends, you can send them a friend request via Discord to connect and start playing (recommended)";
    }


    @Override
    public String toString() {
        return "accepting";
    }


}
