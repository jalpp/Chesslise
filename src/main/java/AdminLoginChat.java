import chariot.Client;
import chariot.ClientAuth;
import chariot.model.Ack;
import chariot.model.Result;
import chariot.model.TokenBulkResult;
import chariot.model.User;
import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;
import java.util.Set;

public class AdminLoginChat extends AdminLoginChallenge{

    private EmbedBuilder embedBuilder;
    private String message;

    public AdminLoginChat(Client client, String LichessToken, String userID, String message) {
        super(client, LichessToken, userID);
        this.message = message;
    }


   public EmbedBuilder getChatStatus(){
       ClientAuth clientAuth = Client.auth(this.getLichessToken());

        try {
            this.embedBuilder = new EmbedBuilder();


            Result<User> userResult = this.getBasicClient().users().byId(this.getUserID());

            if (userResult.isPresent() && !userResult.get().tosViolation() && !userResult.get().closed()) {


                clientAuth.users().sendMessageToUser(this.getUserID(), this.message);
                this.embedBuilder.setColor(Color.green);
                this.embedBuilder.setDescription("Message Successfully send!");
                Result<Ack> deleteToken = clientAuth.account().revokeToken();

                return embedBuilder;


            } else {
                this.embedBuilder.setColor(Color.red);
                this.embedBuilder.setDescription("Invalid Input, Please try again");
                Result<Ack> deleteToken = clientAuth.account().revokeToken();
                return this.embedBuilder;
            }

        }catch (Exception e){
            e.printStackTrace();
            this.embedBuilder = new EmbedBuilder();
            Result<Ack> deleteToken = clientAuth.account().revokeToken();
            return this.embedBuilder.setDescription("Error Occurred, please provide proper input!");
        }



   }

   public EmbedBuilder getDelayMessage(){
        this.embedBuilder = new EmbedBuilder();
        this.embedBuilder.setColor(Color.green);
        this.embedBuilder.setThumbnail("https://c.tenor.com/FBeNVFjn-EkAAAAC/ben-redblock-loading.gif");
        this.embedBuilder.setDescription("Sending your message, please wait");
        return this.embedBuilder;
   }


}
