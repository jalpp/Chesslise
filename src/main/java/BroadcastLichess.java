import chariot.Client;
import chariot.model.Broadcast;
import chariot.model.Many;
import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;
import java.util.List;

public class BroadcastLichess {

    private Client client;
    private EmbedBuilder embedBuilder;
    private String broadURL;
    private String id;



    public BroadcastLichess(Client client){
        this.broadURL = "";
        this.client = client;

    }


    public EmbedBuilder getBroadData(){
        Many<chariot.model.Broadcast> broadcastMany = this.client.broadcasts().official();
        List<chariot.model.Broadcast> broadcasts = broadcastMany.stream().toList();
        String name = broadcasts.get(0).tour().name();
        String des = broadcasts.get(0).tour().description();
        this.id =
        this.broadURL = broadcasts.get(0).tour().url();
        this.embedBuilder = new EmbedBuilder();
        this.embedBuilder.setColor(Color.green);
        this.embedBuilder.setTitle(name);
        this.embedBuilder.setDescription(des);
        this.embedBuilder.addField("View Tournament:", this.broadURL, true);
        this.embedBuilder.setThumbnail("https://static-00.iconduck.com/assets.00/lichess-icon-512x512-q0oh5bwk.png");
        return embedBuilder;
    }










}
