import chariot.Client;
import chariot.model.UserStatus;
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

        List<UserStatus> live = client.users().liveStreamers().stream().toList();

        this.embedBuilder = new EmbedBuilder();

        String getLivePeople = "";



        for(int i = 0; i < 10; i++){

            String title = "";



            if(live.get(i).title().isPresent()){
                title += live.get(i).title().get();
            }else{
                title += "";
            }

            getLivePeople += title + " " + live.get(i).id() + " is Streaming! \n " + " [**Watch Stream**](https://lichess.org/@/" + live.get(i).id() + ") \n\n";


        }
        this.embedBuilder.setThumbnail("https://i.imgur.com/fHhSq9B.jpg");
        this.embedBuilder.setTitle("Current Live Streamers: ");
        this.embedBuilder.setColor(Color.orange);
        this.embedBuilder.setDescription(getLivePeople);

        return this.embedBuilder;


    }




}
