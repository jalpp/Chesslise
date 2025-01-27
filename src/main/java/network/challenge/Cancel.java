package network.challenge;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Updates;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.bson.Document;

/**
 * Cancel class to handle the cancel challenge
 */
public class Cancel extends Action {

    public Cancel(MongoCollection<Document> networkChallenges, MongoCollection<Document> networkPlayers) {
        super(networkChallenges, networkPlayers);
    }

    /**
     * Cancel the challenge
     *
     * @param event the slash command event
     */
    public void cancel(SlashCommandInteractionEvent event) {
        event.deferReply(true).queue();

        if (this.getFinder().findConnected(event.getUser().getId())) {
            event.getHook().sendMessage("You must connect your account before canceling a challenge! Run /connect to connect your account!").queue();
        }

        event.getHook().sendMessage(this.cancelChallenge(event.getOption("challid").getAsString())).queue();
    }

    /**
     * Cancel the challenge
     *
     * @param challengeid the challenge id
     * @return the message
     */
    public String cancelChallenge(String challengeid) {
        Document query = new Document("challengeId", challengeid);
        Document finder = this.getNetworkChallenges().find(query).first();

        if (finder != null) {
            String status = finder.getString("status");
            if (status.equalsIgnoreCase("completed")) {
                return "Can not cancel completed challenge!";
            }
            this.getNetworkChallenges().updateOne(finder, Updates.set("status", "cancel"));
            return "Successfully canceled the challenge!";
        }

        return "Invalid challenge id, view challenges in /mychallenges!";
    }


}
