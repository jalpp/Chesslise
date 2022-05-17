import chariot.Client;
import chariot.model.Analysis;

import chariot.model.Result;
import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;
import java.util.List;


public class EngineEval {


    private Client client;
    private EmbedBuilder embedBuilder;
    private String fenData;




    public EngineEval(Client client, String fen){
        this.client = client;
        this.fenData = fen;
    }


    public EmbedBuilder getEval(){
        this.embedBuilder = new EmbedBuilder();
        Result<Analysis> gameEngine = this.client.analysis().cloudEval(this.fenData);


        if(gameEngine.isPresent()){

            List<Analysis.PV> getEngineData = gameEngine.get().pvs();
            String getStringdata = "";
            for(int i = 0; i < getEngineData.size(); i++){
                getStringdata += getEngineData.get(i).cp() + "\n moves: " + getEngineData.get(i).moves();
            }

            this.embedBuilder.setTitle("Engine Eval: ");
            this.embedBuilder.setColor(Color.green);
            this.embedBuilder.setDescription(getStringdata);

            return this.embedBuilder;




        }else{
            this.embedBuilder.setDescription("Can't load up the engine Because: " + gameEngine.error());
            return this.embedBuilder;
        }


    }




}
