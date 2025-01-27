package network.user;


import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Updates;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import network.challenge.Action;

import org.bson.Document;

/**
 * Disconnect class to handle the disconnect request
 */
public class Disconnect extends Action {

    public Disconnect(MongoCollection<Document> networkChallenges, MongoCollection<Document> networkPlayers) {
        super(networkChallenges, networkPlayers);
    }

    /**
     * Disconnect the player
     *
     * @param event the slash command event
     */
    public void disconnect(SlashCommandInteractionEvent event) {
        event.deferReply(true).queue();

        String goOffline = goOffline(event.getUser().getId());

        event.getHook().sendMessage(goOffline).queue();
    }

    /**
     * Disconnect the player
     *
     * @param discordid the discord id
     * @return the message for disconnecting the player
     */
    public String goOffline(String discordid) {

        Document current = new Document("id", discordid);

        Document user = getNetworkPlayers().find(current).first();

        if (user == null) {
            return "You have not connected your account! Only connected users can disconnect their accounts and go offline";
        }

        if (user.getBoolean("offline")) {
            return "You are already in offline mode, if you want to reconnect run /connect";
        }

        this.getNetworkPlayers().updateOne(user, Updates.set("offline", true));

        return "Successfully made your account offline! You won't see any challenges or friend pairings, to go online please run /connect";

    }


}
