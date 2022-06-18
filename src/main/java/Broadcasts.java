import chariot.Client;
import chariot.model.Broadcast;
import chariot.model.Result;
import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;
import java.util.List;

public class Broadcasts {

    private Client client;
    private EmbedBuilder embedBuilder;
    public Broadcasts(Client client){
        this.client = client;
    }

    public EmbedBuilder getBroadcastData(){

        Result<Broadcast> result = this.client.broadcasts().official();

        if(result.isPresent()){

            List<Broadcast.Round> rounds = result.get().rounds();




            this.embedBuilder = new EmbedBuilder();
            this.embedBuilder.setTitle("Current Broadcasts");
            this.embedBuilder.setColor(Color.green);

            String display = "";
            for(int i = 0; i < rounds.size(); i++){
                display += "[" + rounds.get(i).name() + "]" + " (" + rounds.get(i).url() + ")";
            }
            this.embedBuilder.setDescription(display + "\n\n ");


        }


        return this.embedBuilder;


    }





}
