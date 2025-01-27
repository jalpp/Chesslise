package network.user;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Updates;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import network.challenge.Finder;
import network.user.friends.FriendPrefBuilder;
import network.user.friends.Request;
import org.bson.Document;

import java.util.ArrayList;

/**
 * Connect class to handle the connect request
 */
public class Connect extends Request {

    private final Finder finder;

    public Connect(MongoCollection<Document> networkPlayers) {
        super(networkPlayers);
        this.finder = new Finder(networkPlayers);
    }

    /**
     * Connect the player
     *
     * @param event the slash command event
     */
    public void connect(SlashCommandInteractionEvent event) {

        PreferenceFr player = PreferenceBuilder.playerBuilder(event.getOptionsByName("player").get(0).getAsString());
        PreferenceFr piece = PreferenceBuilder.pieceBuilder(event.getOptionsByName("piece").get(0).getAsString());
        PreferenceFr opening = PreferenceBuilder.openingBuilder(event.getOptionsByName("opening").get(0).getAsString());
        PreferenceFr style = PreferenceBuilder.styleBuilder(event.getOptionsByName("style").get(0).getAsString());
        FriendPrefBuilder builder = new FriendPrefBuilder(player, piece, opening, style);
        PreferencePl pl = PreferenceBuilder.platformBuilder(event.getOptionsByName("platform").get(0).getAsString());
        PreferenceTc tc = PreferenceBuilder.tcBuilder(event.getOptionsByName("tc").get(0).getAsString());
        String discordid = event.getUser().getId();
        String discordusername = event.getUser().getName().toLowerCase();


        event.deferReply(true).queue();
        String msg = this.insertEntry(discordid, discordusername, pl, tc, builder);
        event.getHook().sendMessage(msg).queue();
    }

    /**
     * Insert the user entry in the collection
     *
     * @param discordid       the discord id
     * @param discordusername the discord username
     * @param pl              the platform
     * @param tc              the time control
     * @param builder         the friend preference builder
     * @return the message for inserting the entry
     */
    public String insertEntry(String discordid, String discordusername, PreferencePl pl, PreferenceTc tc, FriendPrefBuilder builder) {
        Document find = finder.findPlayer(discordid);

        if (find != null) {
            if (find.getBoolean("offline")) {
                this.getNetworkPlayers().updateOne(find, Updates.set("offline", false));
                return "You are back to online mode and you can find pairings!";
            } else {
                return "You have already connected your account!";
            }
        }

        Document networkPlayer = new Document("id", discordid)
                .append("username", discordusername)
                .append("friends", new ArrayList<String>())
                .append("friendsusers", new ArrayList<String>())
                .append("requestin", new ArrayList<String>())
                .append("requestout", new ArrayList<String>())
                .append("offline", false)
                .append("blocked", new ArrayList<String>())
                .append("pl", pl.toMongo())
                .append("ptc", tc.toMongo())
                .append("favplayer", builder.getPlayer().toMongoPlayer())
                .append("favpiece", builder.getPiece().toMongoPiece())
                .append("favopening", builder.getOpening().toMongoOpening())
                .append("favstyle", builder.getStyle().toMongoStyle());

        this.getNetworkPlayers().insertOne(networkPlayer);

        return discordusername + " " + "joined the network!";
    }


}
