import chariot.Client;
import chariot.model.StreamerStatus;
import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;
import java.util.List;
import java.util.Optional;

public class LiveStreamers {


    private Client client;
    private EmbedBuilder embedBuilder;

    public LiveStreamers(Client client){
      this.client = client;
    }

    public EmbedBuilder getTv(){

        List<StreamerStatus> live = client.users().liveStreamers().stream().toList();

        this.embedBuilder = new EmbedBuilder();

        String getLivePeople = "";


        for(int i = 0; i < 3; i++){

            String title = "";

            if(live.get(i).title().isPresent()){
                title += live.get(i).title().get();
            }else{
                title += "";
            }

          String presentlink = "";
          String TwitchLink = "";
          String YoutubeLink = "";

            if(live.get(i).stream().service().equals("twitch")){
               Optional<String> twitchLinks = live.get(i).streamer().twitch();
               if(twitchLinks.isPresent()){
                   TwitchLink = twitchLinks.get();
                   presentlink += "[**Twitch** \uD83D\uDD2E ](" + TwitchLink + ") " + "**"+ live.get(i).streamer().headline() + "**"+ "\n\n";

               }
            }else{
               Optional<String> youtubelinks = live.get(i).streamer().youTube();
               if(youtubelinks.isPresent()){
                   YoutubeLink = youtubelinks.get();
                   presentlink += "[**Youtube** \uD83D\uDD3A ](" + YoutubeLink + ") " + "**"+ live.get(i).streamer().headline() + "**"+ "\n\n";
               }
            }



            getLivePeople +=" \uD83C\uDF99️ " + title +  " **" +  live.get(i).id() + "**" + " is **Live**! \n " + presentlink;


        }
        this.embedBuilder.setThumbnail("https://media2.giphy.com/media/McsDYx2ihXzztTFMap/giphy.gif");
        this.embedBuilder.setTitle("Current Live Streamers: ");
        this.embedBuilder.setColor(Color.pink);
        this.embedBuilder.setDescription(getLivePeople);

        return this.embedBuilder;


    }




}
