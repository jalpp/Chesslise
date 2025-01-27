package network.challenge;

import com.mongodb.client.MongoCollection;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.bson.Document;

import java.util.UUID;

/**
 * Create class to handle the create challenge
 */
public class Create extends Action {


    public Create(MongoCollection<Document> networkChallenges, MongoCollection<Document> networkPlayers) {
        super(networkChallenges, networkPlayers);
    }

    /**
     * Create the challenge
     *
     * @param event the slash command event
     */
    public void create(SlashCommandInteractionEvent event) {
        event.deferReply(true).queue();

        if (getFinder().findConnected(event.getUser().getId())) {
            event.getHook().sendMessage("You must connect your account before creating a challenge! Run /connect to connect your account!").queue();
        } else {
            this.createChallenge(event.getUser().getId(), event.getUser().getName(), getFinder().findPreferenceTc(event.getUser().getId()), getFinder().findPreferencePl(event.getUser().getId()), "pending");
        }

        event.getHook().sendMessage("Challenge created! View status of your challenges by running /mychallenges").queue();
    }

    /**
     * Create the challenge
     *
     * @param challengediscordid       the discord id of the challenge
     * @param challengediscordusername the discord username of the challenge
     * @param tc                       the tc of the challenge
     * @param pl                       the pl of the challenge
     * @param status                   the status of the challenge
     */
    public void createChallenge(String challengediscordid, String challengediscordusername, String tc, String pl, String status) {
        Document challenge = new Document("challengeId", UUID.randomUUID().toString())
                .append("discordId", challengediscordid)
                .append("username", challengediscordusername)
                .append("pl", pl)
                .append("ptc", tc).append("status", status)
                .append("oppId", "null")
                .append("oppUsername", "null");
        this.getNetworkChallenges().insertOne(challenge);
    }


}
