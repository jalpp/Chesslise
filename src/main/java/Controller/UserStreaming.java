import chariot.Client;
import chariot.model.One;
import chariot.model.User;
import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;

public class UserStreaming extends UserProfile{



    private EmbedBuilder embedBuilder;



    public UserStreaming(Client client, String parsingUser){
        super(client, parsingUser);


    }




    public EmbedBuilder getStreamingStatus(){

        try {

            One<User> result = this.getClient().users().byId(this.getUserID());
            this.embedBuilder = new EmbedBuilder();

            if (result.isPresent()) { // checking if the user is present

                User user = result.get();

                String sayTitle = "";

                if (user.title().isPresent()) {
                    sayTitle += user.title().get();
                } else {
                    sayTitle += "";
                }


                if (user.tosViolation() == true) { // check to see if the user is a cheater
                    this.embedBuilder.setDescription(this.getUserID() + "  has violated Lichess terms of service ");
                }

                if (user.closed() == true) { // check to see if the user is a closed account
                    this.embedBuilder.setDescription(this.getUserID() + " is a closed account");
                }

                if (result.get().count().all() > 20) {

                    if (user.streaming() == true && user.tosViolation() == false && user.closed() == false) { // displaying the streaming status

                        this.embedBuilder.setColor(Color.green);
                        String stream = user.url();

                        this.embedBuilder.setDescription(sayTitle + " " + this.getUserID() + " Is Streaming! " + "✅" + " View the Stream [Here in the Lichess profile](" + stream + ")");

                    }
                    if (user.streaming() == false && user.tosViolation() == false && user.closed() == false) { // displaying the non-stream status
                        this.embedBuilder.setColor(Color.red);
                        this.embedBuilder.setDescription(sayTitle + " " + this.getUserID() + " Is not Streaming " + "❌" + " Check back later!");

                    }
                } else {
                    this.embedBuilder = new EmbedBuilder();
                    return this.embedBuilder.setDescription("User is not active please play more games to live-stream");
                }


            } else {
                this.embedBuilder.setDescription("User not present, Please try again");
            }

        }catch(Exception e){
            e.printStackTrace();
            this.embedBuilder = new EmbedBuilder();
            return this.embedBuilder.setDescription("Error occurred please try again");
        }

        return this.embedBuilder;

    }



}
