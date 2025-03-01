package network.user;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Updates;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import network.user.friends.FriendPrefBuilder;
import org.bson.Document;


public class PreferenceUpdate {

    private final MongoCollection<Document> networkPlayers;

    public PreferenceUpdate(MongoCollection<Document> networkPlayers) {
        this.networkPlayers = networkPlayers;
    }

    
    public void update(SlashCommandInteractionEvent event) {

        PreferenceFr player = PreferenceBuilder.playerBuilder(event.getOptionsByName("player").get(0).getAsString());
        PreferenceFr piece = PreferenceBuilder.pieceBuilder(event.getOptionsByName("piece").get(0).getAsString());
        PreferenceFr opening = PreferenceBuilder.openingBuilder(event.getOptionsByName("opening").get(0).getAsString());
        PreferenceFr style = PreferenceBuilder.styleBuilder(event.getOptionsByName("style").get(0).getAsString());
        FriendPrefBuilder builder = new FriendPrefBuilder(player, piece, opening, style);
        PreferencePl pl = PreferenceBuilder.platformBuilder(event.getOptionsByName("platform").get(0).getAsString());
        PreferenceTc tc = PreferenceBuilder.tcBuilder(event.getOptionsByName("tc").get(0).getAsString());
        String discordid = event.getUser().getId();


        event.deferReply(true).queue();

        event.getHook().sendMessage(this.updatePreference(discordid, pl, tc, builder)).queue();

    }


    
    public String updatePreference(String discordid, PreferencePl pl, PreferenceTc tc, FriendPrefBuilder builder) {
        Document query = new Document("id", discordid);
        Document preferencedoc = this.networkPlayers.find(query).first();

        if (preferencedoc != null) {
            this.networkPlayers.updateOne(preferencedoc, Updates.combine(Updates.set("pl", pl.toMongo()), Updates.set("ptc", tc.toMongo()),
                    Updates.set("favplayer", builder.getPlayer().toMongoPlayer()), Updates.set("favpiece", builder.getPiece().toMongoPiece()),
                    Updates.set("favopening", builder.getOpening().toMongoOpening()), Updates.set("favstyle", builder.getStyle().toMongoStyle())));
            return "Successfully updated the preferences!";
        }

        return "Not account found connected! Please connect your account using /connect";
    }


}
