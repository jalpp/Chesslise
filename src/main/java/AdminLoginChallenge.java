
import chariot.Client;
import chariot.ClientAuth;

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
  this.embedBuilder = new EmbedBuilder();
  ClientAuth clientAuth = Client.auth(this.getLichessToken());

  Result<User> userResult = this.getBasicClient().users().byId(this.getUserID(), true);

  boolean userPresent = userResult.isPresent();


  if(userPresent){
   String url = clientAuth.challenges().challenge(this.getUserID(), conf -> conf.clock(300, 3).color(c -> c.white())).get().url();
   this.embedBuilder.setColor(Color.green);
   this.embedBuilder.setTitle("Successfully Created Challenge against: " + this.getUserID());
   this.embedBuilder.setDescription("Please Start your game here: " + "[Play Here](" + url + ")");
   return this.embedBuilder;
  }else{

   this.embedBuilder.setColor(Color.red);
   this.embedBuilder.setTitle("Challenge Failed!");
   this.embedBuilder.setDescription("Invalid Input, Please input proper Lichess username!");

   return this.embedBuilder;
  }




}



 }
