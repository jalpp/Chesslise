package network.challenge;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import network.user.PreferenceBuilder;
import org.bson.Document;

import java.awt.*;


public class View extends Action {


    public View(MongoCollection<Document> networkChallenges, MongoCollection<Document> networkPlayers) {
        super(networkChallenges, networkPlayers);
    }

    
    public void view(SlashCommandInteractionEvent event) {
        event.deferReply(true).queue();

        if (getFinder().findConnected(event.getUser().getId())) {
            event.getHook().sendMessage("You must connect your account before you can view your challenge! Run /connect to connect your account!").queue();
        }
        String display = this.viewChallenges(event.getUser().getId(), PreferenceBuilder.statusBuilder(event.getOptionsByName("chalstatus").get(0).getAsString()));
        EmbedBuilder builder = new EmbedBuilder();
        builder.setTitle("My challenges");
        builder.setDescription(display.toLowerCase().contains("no") ? display : "(username) (oppUsername) (status) (challengeID) \n" + display);
        builder.setColor(Color.BLUE);

        event.getHook().sendMessageEmbeds(builder.build()).setEphemeral(true).queue();

    }

    
    public String viewChallenges(String discordid, Status status) {
        return viewBuilder("discordId", discordid, status) + "\n" + viewBuilder("oppId", discordid, status);
    }

    
    private String viewBuilder(String currentField, String discordID, Status status) {
        Document query = new Document(currentField, discordID).append("status", status.toMongo());
        FindIterable<Document> challenges = this.getNetworkChallenges().find(query);
        StringBuilder builder = new StringBuilder();
        int challengeCount = 0;

        for (Document challenge : challenges) {
            if (challenge == null) {
                continue;
            }
            challengeCount++;
            builder.append(challenge.getString("username")).append(" vs ").append(challenge.getString("oppUsername").equalsIgnoreCase("null") ? "waiting" : challenge.getString("oppUsername")).append(" ").append(challenge.getString("status")).append(" ").append(challenge.getString("challengeId")).append("\n");
        }

        if (challengeCount == 0) {
            return "";
        }

        return builder.toString();
    }


}
