package Lichess;

import chariot.Client;
import chariot.model.LiveStreamer;
import chariot.model.Some;
import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;
import java.util.List;


public class LiveStreamers {


    private final Client client;

    public LiveStreamers(Client client) {
        this.client = client;
    }

    public EmbedBuilder getTv() {

        List<LiveStreamer> live = client.users().liveStreamers().stream().toList();

        EmbedBuilder embedBuilder = new EmbedBuilder();

        StringBuilder getLivePeople = new StringBuilder();


        for (int i = 0; i < 3; i++) {

            String title = live.get(i).user().title().orElse("");

            String presentlink = "";
            String TwitchLink = "";
            String YoutubeLink = "";

            if (live.get(i).stream().service().equals("twitch")) {
                if (live.get(i).streamer().twitch() instanceof Some<String> twitch) {
                    TwitchLink = twitch.value();
                    presentlink += "[**Twitch** \uD83D\uDD2E ](" + TwitchLink + ") " + "**" + live.get(i).streamer().headline() + "**" + "\n\n";
                }
            } else {
                if (live.get(i).streamer().youtube() instanceof Some<String> youtube) {
                    YoutubeLink = youtube.value();
                    presentlink += "[**Youtube** \uD83D\uDD3A ](" + YoutubeLink + ") " + "**" + live.get(i).streamer().headline() + "**" + "\n\n";
                }
            }


            getLivePeople.append(" \uD83C\uDF99️ ").append(title).append(" **").append(live.get(i).user().id()).append("**").append(" is **Live**! \n ").append(presentlink);


        }
        embedBuilder.setThumbnail("https://media2.giphy.com/media/McsDYx2ihXzztTFMap/giphy.gif");
        embedBuilder.setTitle("Current Live Streamers: ");
        embedBuilder.setColor(Color.pink);
        embedBuilder.setDescription(getLivePeople.toString());

        return embedBuilder;


    }


}