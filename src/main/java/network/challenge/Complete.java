package network.challenge;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Updates;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.bson.Document;

/**
 * Complete class to handle the complete challenge
 */
public class Complete extends Action {


    public Complete(MongoCollection<Document> networkChallenges, MongoCollection<Document> networkPlayers) {
        super(networkChallenges, networkPlayers);
    }

    /**
     * Complete the challenge
     *
     * @param event the slash command event
     */
    public void complete(SlashCommandInteractionEvent event) {
        event.deferReply(true).queue();

        if (getFinder().findConnected(event.getUser().getId())) {
            event.getHook().sendMessage("You must connect your account before canceling a challenge! Run /connect to connect your account!").queue();
        }

        event.getHook().sendMessage(this.completeChallenge(event.getOption("cchallid").getAsString())).queue();
    }

    /**
     * Complete the challenge
     *
     * @param challengeid the challenge id
     * @return the message
     */
    public String completeChallenge(String challengeid) {
        Document query = new Document("challengeId", challengeid);

        Document finder = this.getNetworkChallenges().find(query).first();
        if (finder != null) {
            String status = finder.getString("status");
            if (status.equalsIgnoreCase("accepted")) {
                this.getNetworkChallenges().updateOne(query, Updates.set("status", "completed"));
                return "Successfully completed the challenge!";
            } else {
                return "Can not complete challenge status of " + status + "complete! Only accepted challenges can be completed!";
            }

        }

        return "Invalid challenge id, view challenges in /mychallenges!";
    }


}
