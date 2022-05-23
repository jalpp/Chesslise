import chariot.Client;
import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;

public class GameReview {

    private Client client;
    private EmbedBuilder embedBuilder;
    private String gameID;


    public GameReview(Client client, String gameID){
        this.client = client;
        this.gameID = gameID;
    }


    public EmbedBuilder getGameReviewData(){
        this.embedBuilder = new EmbedBuilder();
        ComputeEvalSummary computeEvalSummary = new ComputeEvalSummary();

        this.embedBuilder.setTitle("Game Review Report");
        this.embedBuilder.setColor(Color.white);
        this.embedBuilder.setDescription(computeEvalSummary.getEvalSummary(this.gameID));




        return this.embedBuilder;
    }




}
