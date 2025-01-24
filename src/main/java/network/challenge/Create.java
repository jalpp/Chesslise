package network.challenge;

import com.mongodb.client.MongoCollection;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.bson.Document;

import java.util.UUID;
//TODO: maybe limits?! Not sure
public class Create extends Action{


    public Create(MongoCollection<Document> networkChallenges, MongoCollection<Document> networkPlayers) {
        super(networkChallenges, networkPlayers);
    }

    public void create(SlashCommandInteractionEvent event){
        event.deferReply(true).queue();

        if(getFinder().findConnected(event.getUser().getId())){
            event.getHook().sendMessage("You must connect your account before creating a challenge! Run /connect to connect your account!").queue();
        }

        this.createChallenge(event.getUser().getId(), event.getUser().getName(), getFinder().findPreferenceTc(event.getUser().getId()), getFinder().findPreferencePl(event.getUser().getId()), "pending");

        event.getHook().sendMessage("Challenge created! View status of your challenges by running /mychallenges").queue();
    }


    public void createChallenge(String challengediscordid, String challengediscordusername, String tc, String pl, String status){
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
