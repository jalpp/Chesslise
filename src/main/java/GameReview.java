import chariot.Client;
import chariot.model.Result;
import chariot.model.User;
import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;

public class GameReview extends UserGame {


    private EmbedBuilder embedBuilder;


    public GameReview(Client client, String userID) {
        super(client, userID);
    }


    public EmbedBuilder getGameReviewData(){


        Result<User> userResult = this.getClient().users().byId(this.getUserID(), true);

        if(userResult.isPresent()){

            this.embedBuilder = new EmbedBuilder();
            ComputeEvalSummary computeEvalSummary = new ComputeEvalSummary();

            this.embedBuilder.setTitle("Game Review Report");
            this.embedBuilder.setColor(Color.white);
            this.embedBuilder.setDescription(computeEvalSummary.getEvalSummary(this.getGameId()));
        }





        return this.embedBuilder;
    }




}
