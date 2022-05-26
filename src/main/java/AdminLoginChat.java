import chariot.Client;
import chariot.ClientAuth;
import chariot.model.Result;
import chariot.model.User;
import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;

public class AdminLoginChat extends AdminLoginChallenge{

    private EmbedBuilder embedBuilder;
    private String message;

    public AdminLoginChat(Client client, String LichessToken, String userID, String message) {
        super(client, LichessToken, userID);
        this.message = message;
    }


   public EmbedBuilder getChatStatus(){

        this.embedBuilder = new EmbedBuilder();

        Result<User> userResult = this.getBasicClient().users().byId(this.getUserID());

        if(userResult.isPresent() && !userResult.get().tosViolation() && !userResult.get().closed()){
            ClientAuth clientAuth = Client.auth(this.getLichessToken());

            clientAuth.users().sendMessageToUser(this.getUserID(), this.message);
            this.embedBuilder.setColor(Color.green);
            this.embedBuilder.setDescription("Message Successfully send!");

            return embedBuilder;
        }else{
            this.embedBuilder.setColor(Color.red);
            this.embedBuilder.setDescription("The given user does not exist in Lichess, please supply a proper username");

            return this.embedBuilder;
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
