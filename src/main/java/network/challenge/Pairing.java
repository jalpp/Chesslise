package network.challenge;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import network.user.friends.SendFriendRequest;
import org.bson.Document;

import java.util.*;


public class Pairing extends Action {

    private final PairingNetworkType type;
    private final SendFriendRequest sendRequest;

    public Pairing(MongoCollection<Document> networkChallenges, MongoCollection<Document> networkPlayers, PairingNetworkType type) {
        super(networkChallenges, networkPlayers);
        this.type = type;
        this.sendRequest = new SendFriendRequest(networkPlayers);
    }

    public void pairOpenNetwork(SlashCommandInteractionEvent event) {
        event.deferReply(true).queue();

        if (getFinder().findConnected(event.getUser().getId())) {
            event.getHook().sendMessage("You must connect your account before creating a pairing request! Run /connect to connect your account!").queue();
        }

        String pairingMsg = pairPlayer(event.getUser().getId(), event.getUser().getName());

        event.getHook().sendMessage(pairingMsg).queue();
    }

    public void pairSelfNetwork(SlashCommandInteractionEvent event) {
        event.deferReply(true).queue();

        if (getFinder().findConnected(event.getUser().getId())) {
            event.getHook().sendMessage("You must connect your account before creating a pairing request! Run /connect to connect your account!").queue();
        }

        String pairingMsg = bfspairing(event.getUser().getId(), event.getUser().getName());

        event.getHook().sendMessage(pairingMsg).queue();

    }


    public String pairPlayer(String discordId, String discordusername) {

        Document query = new Document("status", "pending").append("oppId", "null").append("pl", getFinder().findPreferencePl(discordId)).append("ptc", getFinder().findPreferenceTc(discordId));
        FindIterable<Document> challenges = getNetworkChallenges().find(query);
        Document current = new Finder(getNetworkPlayers()).findPlayer(discordId);

        if (current == null) {
            return "Please connect your account!";
        }
        List<String> blockedIds = current.getList("blocked", String.class);

        for (Document challenge : challenges) {

            if (!challenge.getString("discordId").equalsIgnoreCase(discordId) && !blockedIds.contains(challenge.getString("discordId"))) {
                return updatePairingInfoAndGetGreeting(discordId, discordusername, challenge);
            }
        }

        return "Challenge not found! Check back later";
    }

   
    private String updatePairingInfoAndGetGreeting(String discordId, String discordUsername, Document challenge) {
        Document updateQuery = new Document("$set", new Document("status", "accepted").append("oppId", discordId).append("oppUsername", discordUsername));
        getNetworkChallenges().updateOne(challenge, updateQuery);
        return "Challenge found! " + discordUsername + " vs " + challenge.getString("username") + " You can send a Discord friend request and start playing!";
    }

    
    private String sendFriendRequestAndGetGreeting(String discordID, Document friendDoc) {
        String msg = this.sendRequest.addFriend(discordID, friendDoc.getString("username"));
        return "Successfully found friend " + friendDoc.getString("username") + " \n" + msg;
    }

   
    private boolean foundPairing(Document start, Document current) {

        if (start.getString("id").equalsIgnoreCase(current.getString("id"))) {
            return false;
        }

        if (start.getList("blocked", String.class).contains(current.getString("id")) || current.getList("blocked", String.class).contains(start.getString("id"))) {
            return false;
        }

        if (start.getBoolean("offline") || current.getBoolean("offline")) {
            return false;
        }

        if (this.type == PairingNetworkType.PAIR_NETWORK_CHALLENGE) {
            if (this.getNetworkChallenges().find(new Document("discordId", current.getString("id")).append("oppId", "null")).first() == null) {
                return false;
            }
        } else if (this.type == PairingNetworkType.PAIR_NETWORK_FRIEND) {
            if (start.getList("friends", String.class).contains(current.getString("id"))) {
                return false;
            }

            return start.getString("favplayer").equalsIgnoreCase(current.getString("favplayer")) || start.getString("favpiece").equalsIgnoreCase(current.getString("favpiece")) || start.getString("favopening").equalsIgnoreCase(current.getString("favopening")) || start.getString("favstyle").equalsIgnoreCase(current.getString("favstyle"));
        }


        return start.getString("pl").equalsIgnoreCase(current.getString("pl")) && start.getString("ptc").equalsIgnoreCase(current.getString("ptc"));
    }

    
    private Document getTargetedFriendChallengeDoc(Document current) {
        String id = current.getString("id");
        Document query = new Document("status", "pending").append("oppId", "null").append("discordId", id);
        return getNetworkChallenges().find(query).first();
    }


   
    private ArrayList<Document> getAdjlist(Document start) {
        ArrayList<Document> friendDocs = new ArrayList<>();
        List<String> friends = start.getList("friends", String.class);
        for (String friend : friends) {
            Document query = new Document("id", friend);
            Document friendDoc = getNetworkPlayers().find(query).first();

            if (friendDoc != null) {
                friendDocs.add(friendDoc);
            }
        }

        return friendDocs;
    }

    
    public String bfspairing(String discordIdStart, String discordUsernameStart) {

        Document start = getFinder().findPlayer(discordIdStart);
        HashMap<String, Boolean> visited = new HashMap<>();

        if (type == PairingNetworkType.PAIR_NETWORK_FRIEND && start.getList("friends", String.class).isEmpty()) {
            Document find = getNetworkPlayers().find(new Document("favplayer", start.getString("favplayer"))).first();
            if (find == null) {
                return "No friend found in your given preferences! Check back later";
            } else {
                return this.sendRequest.addFriend(discordIdStart, find.getString("username"));
            }
        }

        if (start != null) {
            Queue<Document> queue = new LinkedList<>();
            visited.put(discordIdStart, true);
            ArrayList<Document> adjlist = getAdjlist(start);

            if (adjlist.isEmpty()) {
                return "No challenge found! You need to add more friends to your network so I can search in the network ;)";
            }
            queue.offer(start);

            while (!queue.isEmpty()) {
                Document u = queue.poll();
                System.out.println(u.getString("username"));
                if (foundPairing(start, u)) {
                    if (this.type == PairingNetworkType.PAIR_NETWORK_CHALLENGE) {
                        return updatePairingInfoAndGetGreeting(discordIdStart, discordUsernameStart, getTargetedFriendChallengeDoc(u));
                    } else {
                        return sendFriendRequestAndGetGreeting(discordIdStart, u);
                    }
                }
                for (Document v : getAdjlist(u)) {
                    String vid = v.getString("id");
                    if (!visited.containsKey(vid)) {
                        visited.put(vid, true);
                        queue.offer(v);
                    }
                }
            }

            return this.type == PairingNetworkType.PAIR_NETWORK_CHALLENGE ? "No challenge found! Check back later" : "No friend found! Check back later";
        } else {
            return "You have not connected your account to Chesslise network! Use /connect and add friends and you will be able to use this command";
        }
    }


}
