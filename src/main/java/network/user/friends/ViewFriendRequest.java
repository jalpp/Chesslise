package network.user.friends;

import com.mongodb.client.MongoCollection;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.bson.Document;

import java.util.List;


public class ViewFriendRequest extends Request {

    public ViewFriendRequest(MongoCollection<Document> players) {
        super(players);
    }

    
    public void viewFriendRequests(SlashCommandInteractionEvent event) {
        event.deferReply(true).queue();

        String viewres = viewRequests(event.getUser().getId(), ViewTypeFriend.getType(event.getOptionsByName("requesttype").get(0).getAsString()));

        event.getHook().sendMessage(viewres).queue();
    }

    
    public String viewRequests(String discordid, ViewTypeFriend type) {
        Document current = this.getNetworkPlayers().find(new Document("id", discordid)).first();

        if (current == null) {
            return "You must connect your account before viewing friends! Run /connect to connect your account!";
        }

        switch (type) {
            case LIST_FRIEND -> {
                return viewList(current);
            }
            case OUTGOING_FRIEND -> {
                return viewOutRequests(current);
            }
            case INCOMMING_FRIEND -> {
                return viewInRequests(current);
            }
        }

        return "invalid view type!";
    }


    
    public String viewInRequests(Document current) {
        return viewBoundRequests(current, "requestin", "incomming");
    }


    
    public String viewOutRequests(Document current) {
        return viewBoundRequests(current, "requestout", "outgoing");
    }

    
    private String viewBoundRequests(Document current, String bounded, String boundedWord) {
        StringBuilder boundBuilder = new StringBuilder();
        List<String> boundedrequest = current.getList(bounded, String.class);
        if (boundedrequest.isEmpty()) {
            return "You don't have any " + boundedWord + " friend requests";
        }

        boundBuilder.append("username: discordID:").append("\n");

        if (boundedrequest.size() <= 50) {
            for (String o : boundedrequest) {
                Document doc = this.getNetworkPlayers().find(new Document("id", o)).first();
                boundBuilder.append(doc.getString("username")).append(" ").append(o).append("\n");
            }
        } else {
            for (int i = 0; i < 50; i++) {
                Document doc = this.getNetworkPlayers().find(new Document("id", boundedrequest.get(i))).first();
                boundBuilder.append(doc.getString("username")).append(" ").append(boundedrequest.get(i)).append("\n");
            }
        }

        return boundBuilder.toString();

    }

  
    public String viewList(Document current) {
        StringBuilder friendList = new StringBuilder();
        List<String> friends = current.getList("friendsusers", String.class);

        if (friends.isEmpty()) {
            return "You don't have any friends in your network!";
        }

        friendList.append("username: ").append("\n");

        if (friends.size() <= 50) {
            for (String f : friends) {
                friendList.append(f).append("\n");
            }
        } else {
            for (int i = 0; i < 50; i++) {
                friendList.append(friends.get(i)).append("\n");
            }
        }

        return friendList.toString();

    }


}
