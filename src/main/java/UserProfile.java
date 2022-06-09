iimport chariot.Client;
import chariot.model.Result;
import chariot.model.Trophy;
import chariot.model.User;
import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;
import java.util.ArrayList;
import java.util.Optional;

public class UserProfile {

    private Client client;
    private  EmbedBuilder embedBuilder;
    private String userID;


    public UserProfile(Client client, String userParsing){
        this.client = client;
        this.userID = userParsing.toLowerCase();
    }



    /**
     * ,profile command to see people's lichess profiles
     * input: Lichess username
     * output the whole Lichess  profile
     */


    public EmbedBuilder getUserProfile(){

        try {

            Result<User> userResult = client.users().byId(this.userID, true);

            boolean userPresent = userResult.isPresent();

            this.embedBuilder = new EmbedBuilder();
            this.embedBuilder.setDescription("");


            if (userPresent == true) {  // checking if the user is present in the lichess
                User user = userResult.get();


                boolean cheater = user.tosViolation();

                boolean closedaccount = user.closed();

                if (cheater == true) { // check if the user is cheater
                    this.embedBuilder.setDescription(" This user has violated Lichess Terms of Service");
                    return this.embedBuilder;

                }
                if (closedaccount == true) { // check if user is clossed account
                    this.embedBuilder.setDescription("This account is closed");
                    return this.embedBuilder;
                }


                if (cheater == false && closedaccount == false) {


                    // List of variables


                    String name = user.id();

                    String bio = user.profile().get().bio();

                    int wins = user.count().win();

                    int lose = user.count().loss();

                    int all = user.count().all();

                    int draw = user.count().draw();

                    int playing = user.count().playing();

                    String userUrl = user.url();

                    boolean pat = user.patron();

                    String patWings = "";

                    if (user.profile().isEmpty()) {
                        this.embedBuilder = new EmbedBuilder();
                        return this.embedBuilder.setDescription("can't generate profiles for user, not enough profile data");
                    }


                    if (pat == true) {
                        patWings += "https://cdn.discordapp.com/emojis/900426733814165534.png?size=96";


                    } else {
                        patWings += " https://www.google.com/url?sa=i&url=https%3A%2F%2Flichess.fandom.com%2Fwiki%2FHorsey&psig=AOvVaw27c2RGn3iSXKXx26gOqKlo&ust=1634835688231000&source=images&cd=vfe&ved=0CAsQjRxqFwoTCIiPvvC72fMCFQAAAAAdAAAAABAJ";

                    }


                    String sayTitle = "";

                    Optional<String> titledPlayer = user.title();

                    Boolean hasTitle;


                    if (titledPlayer.isPresent()) { // check if the user is titled, I wish I was titled player :))
                        hasTitle = true;
                    } else {
                        hasTitle = false;
                    }

                    if (hasTitle == true) {
                        sayTitle += titledPlayer.get();
                    } else {
                        sayTitle += "";
                    }

                    String sayrewards = "";


                    // getting user profiles

                    for (chariot.model.Trophy trophy : user.trophies()) {

                        UserTrophy userTrophy = new UserTrophy(trophy);
                        sayrewards += userTrophy.getImageLink() + "\n";
                    }


                    // creating the Lichess style profile with embeds


                    this.embedBuilder = new EmbedBuilder();
                    this.embedBuilder.setColor(Color.white);
                    this.embedBuilder.setThumbnail(patWings);

                    this.embedBuilder.setTitle("Lichess Profile for: " + name);
                    this.embedBuilder.setDescription("**Username:** " + " " + sayTitle + "  " + name + "\n \n **User bio:** " + bio + "\n \n **Games** \n \n" + "**All Games**: " + all + "\n" + "**wins:** " + wins + "\n **Loses:** " + lose + "\n **draws:** " + draw + "\n **Playing:** " + playing + "\n **User Trophies:** \n" + sayrewards + "  \n \n[[See Stats on Lichess](" + userUrl + ")]");


                }
            }
            if (userPresent == false) {
                this.embedBuilder.setDescription("User Not Present, Please try again");
                return this.embedBuilder;
            }

        }catch(Exception e){
            e.printStackTrace();
            this.embedBuilder = new EmbedBuilder();
            return this.embedBuilder.setDescription("Given user is not active, can't create profile for inactive(Less than 20 games) user");
        }

        return this.embedBuilder;


    }


    public Client getClient(){
        return this.client;
    }

    public String getUserID(){
        return this.userID;
    }





}
