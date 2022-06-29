
import chariot.Client;
import chariot.ClientAuth;

import chariot.model.Ack;
import chariot.model.Result;
import chariot.model.User;
import net.dv8tion.jda.api.EmbedBuilder;
import java.awt.*;


public class AdminLoginChallenge extends AdminActionsManager{

 private EmbedBuilder embedBuilder;

 public AdminLoginChallenge(Client client, String LichessToken, String userID) {
  super(client, LichessToken, userID);
 }

 public EmbedBuilder getChallenge(){
  ClientAuth clientAuth = Client.auth(this.getLichessToken());
  try {
   this.embedBuilder = new EmbedBuilder();

   Result<User> userResult = this.getBasicClient().users().byId(this.getUserID(), true);

   boolean userPresent = userResult.isPresent();


   if (userPresent && !userResult.get().closed() && !userResult.get().tosViolation()) {
    String url = clientAuth.challenges().challenge(this.getUserID(), conf -> conf.clock(300, 3).color(c -> c.white())).get().url();
    this.embedBuilder.setColor(Color.green);
    this.embedBuilder.setTitle("Successfully Created Challenge against: " + this.getUserID());
    this.embedBuilder.setDescription("Please Start your game here: " + "[Play Here](" + url + ")");
    Result<Ack> deleteToken = clientAuth.account().revokeToken();
    return this.embedBuilder;
   } else {
    this.embedBuilder.setColor(Color.red);
    this.embedBuilder.setTitle("Challenge Failed!");
    this.embedBuilder.setDescription("Invalid Input, Please input proper Lichess username!");
    Result<Ack> deleteToken = clientAuth.account().revokeToken();
    return this.embedBuilder;
   }


  }catch (Exception e){
   this.embedBuilder = new EmbedBuilder();
   Result<Ack> deleteToken = clientAuth.account().revokeToken();
   return this.embedBuilder.setDescription("Error Occurred, please provide proper input!");
  }




}



}




