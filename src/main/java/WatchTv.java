import chariot.Client;
import chariot.model.Result;
import chariot.model.TVChannels;
import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;

public class WatchTv {

    private Client client;
    private EmbedBuilder embedBuilder;
    private Result<TVChannels> tv;

    public WatchTv(Client client){
        this.client = client;
        this.tv = this.client.games().tvChannels();
    }


    public EmbedBuilder getTV(){
       this.embedBuilder = new EmbedBuilder();
       this.embedBuilder.setColor(Color.black);
       this.embedBuilder.setTitle("Watch Lichess TV");
       this.embedBuilder.setDescription("**Watch Lichess TV** \n\n ** ⚡ Blitz** \n View Blitz Games \n\n ** \uD83D\uDE85 Bullet** \n View Bullet Games \n\n **\uD83D\uDC3F️ Rapid** \n View Rapid Games \n\n");
       this.embedBuilder.setThumbnail("https://www.kindpng.com/picc/m/129-1298144_tv-clipart-look-at-clip-art-images-transparent.png");
       return this.embedBuilder;
    }

    public String getBlitz(){
        String watchBlitz = new String("");

            watchBlitz += "https://lichess1.org/game/export/gif/" + tv.get().blitz().gameId() + ".gif";
            return watchBlitz;

    }

    public String getBullet(){
        String watchBullet = "";
        watchBullet += "https://lichess1.org/game/export/gif/" + tv.get().blitz().gameId() + ".gif";
        return watchBullet;

    }

    public String getRapid(){
        String watchRapid = "";
        watchRapid += "https://lichess1.org/game/export/gif/" + tv.get().rapid().gameId() + ".gif";
        return watchRapid;
    }





}
