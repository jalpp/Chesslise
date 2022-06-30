import chariot.Client;
import chariot.model.StreamerStatus;
import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;
import java.util.List;

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


        for(int i = 0; i < 10; i++){

            String title = "";

            if(live.get(i).title().isPresent()){
                title += live.get(i).title().get();
            }else{
                title += "";
            }

            getLivePeople +=" \uD83C\uDF99️ " + title +  " " +  live.get(i).id() + " is Streaming! \n " + " [**Profile**](https://lichess.org/@/" + live.get(i).id() + ") \n";


        }
        this.embedBuilder.setThumbnail("https://cdn5.vectorstock.com/i/1000x1000/14/24/twitch-and-youtube-logo-with-background-ima-vector-19461424.jpg");
        this.embedBuilder.setTitle("Current Live Streamers: ");
        this.embedBuilder.setColor(Color.pink);
        this.embedBuilder.setDescription(getLivePeople);

        return this.embedBuilder;


    }




}
