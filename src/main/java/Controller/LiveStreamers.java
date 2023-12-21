import chariot.Client;
import chariot.model.*;
import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;
import java.util.List;
import java.util.Optional;

public class LiveStreamers {


    private final Client client;
    private EmbedBuilder embedBuilder;

    public LiveStreamers(Client client){
      this.client = client;
    }

    public EmbedBuilder getTv(){

        List<LiveStreamer> live = client.users().liveStreamers().stream().toList();

        this.embedBuilder = new EmbedBuilder();

        String getLivePeople = "";


        for(int i = 0; i < 3; i++){

            String title = live.get(i).user().title().orElse("");

          String presentlink = "";
          String TwitchLink = "";
          String YoutubeLink = "";

            if(live.get(i).stream().service().equals("twitch")){
               if (live.get(i).streamer().twitch() instanceof Some<String> twitch) {
                   TwitchLink = twitch.value();
                   presentlink += "[**Twitch** \uD83D\uDD2E ](" + TwitchLink + ") " + "**"+ live.get(i).streamer().headline() + "**"+ "\n\n";
               }
            }else{
               if (live.get(i).streamer().youtube() instanceof Some<String> youtube) {
                   YoutubeLink = youtube.value();
                   presentlink += "[**Youtube** \uD83D\uDD3A ](" + YoutubeLink + ") " + "**"+ live.get(i).streamer().headline() + "**"+ "\n\n";
               }
            }


            getLivePeople +=" \uD83C\uDF99️ " + title +  " **" +  live.get(i).user().id() + "**" + " is **Live**! \n " + presentlink;


        }
        this.embedBuilder.setThumbnail("https://media2.giphy.com/media/McsDYx2ihXzztTFMap/giphy.gif");
        this.embedBuilder.setTitle("Current Live Streamers: ");
        this.embedBuilder.setColor(Color.pink);
        this.embedBuilder.setDescription(getLivePeople);

        return this.embedBuilder;


    }




}
