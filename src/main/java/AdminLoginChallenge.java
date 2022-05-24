
import chariot.Client;
import chariot.ClientAuth;
import chariot.api.ChallengesAuth;
import chariot.model.Arena;
import chariot.model.BulkPairing;
import chariot.model.Result;
import chariot.model.User;
import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;
import java.time.*;
import java.util.function.Consumer;


public class AdminLoginChallenge {


 private String AdminToken;
 private String userID;
 private EmbedBuilder embedBuilder;
 private Client basicClient;


 public AdminLoginChallenge(Client client, String LichessToken, String userID){
  this.basicClient = client;
  this.AdminToken = LichessToken;
  this.userID = userID;

 }

 public EmbedBuilder getChallenge(){
  this.embedBuilder = new EmbedBuilder();
  ClientAuth clientAuth = Client.auth(this.AdminToken);

  Result<User> userResult = this.basicClient.users().byId(this.userID, true);

  boolean userPresent = userResult.isPresent();

  if(userPresent){
   clientAuth.challenges().challenge(this.userID, conf -> conf.clock(300, 3).color(c -> c.white()));
   this.embedBuilder.setColor(Color.green);
   this.embedBuilder.setTitle("Successfully Created Challenge against: " + this.userID);
   this.embedBuilder.setDescription("Please Start your game here: " + "[Play Here](https://lichess.org/@/" + this.userID +")");
   return this.embedBuilder;
  }else{

   this.embedBuilder.setColor(Color.red);
   this.embedBuilder.setTitle("Challenge Failed!");
   this.embedBuilder.setDescription("Invalid Input, Please input proper Lichess username!");

   return this.embedBuilder;
  }




}


}
