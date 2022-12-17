import chariot.Client;
import chariot.model.Enums;
import chariot.model.One;
import chariot.model.PerfStat;
import chariot.model.User;
import net.dv8tion.jda.api.EmbedBuilder;
import java.awt.*;
import java.util.Optional;

public class UserProfile extends UserObject{


    private String sayProfile = "";

    public UserProfile(Client client, String userParsing){
        super(client, userParsing);

    }



    public String getBlitzRatings(){
        One<PerfStat> userBlitz = this.getClient().users().performanceStatisticsByIdAndType(this.getUserID(), Enums.PerfType.blitz);


        String blitzRating = " \uD83D\uDD25 **Blitz**: ?";

        if(userBlitz.isPresent() && !userBlitz.get().perf().glicko().provisional()){
            return blitzRating = " \uD83D\uDD25 **Blitz**:  " + userBlitz.get().perf().glicko().rating().intValue();
        }

        return blitzRating;

    }

    public String getRapidRatings(){
        One<PerfStat> userRapid = this.getClient().users().performanceStatisticsByIdAndType(this.getUserID(), Enums.PerfType.rapid);

        String rapidRating = " \uD83D\uDC07 **Rapid**: ?";

        if(userRapid.isPresent() && !userRapid.get().perf().glicko().provisional()){
            return rapidRating = "\uD83D\uDC07 **Rapid**:  " + userRapid.get().perf().glicko().rating().intValue();
        }

        return rapidRating;

    }

    public String getBulletRatings(){
        One<PerfStat> userBullet = this.getClient().users().performanceStatisticsByIdAndType(this.getUserID(), Enums.PerfType.bullet);

        String bulletRating = "\uD83D\uDD2B **Bullet**: ?";

        if(userBullet.isPresent() && !userBullet .get().perf().glicko().provisional()){
            return bulletRating = "\uD83D\uDD2B **Bullet**:  " + userBullet .get().perf().glicko().rating().intValue();
        }

        return bulletRating;

    }

    public String getClassicalRatings(){
        One<PerfStat> usercal = this.getClient().users().performanceStatisticsByIdAndType(this.getUserID(), Enums.PerfType.classical);

        String calRating = "\uD83D\uDC22 **Classical**: ?";

        if(usercal.isPresent() && !usercal.get().perf().glicko().provisional()){
            return calRating = "\uD83D\uDC22 **Classical**:  " +usercal.get().perf().glicko().rating().intValue();
        }

        return calRating;

    }






    public String getUserProfile(){

        try {

            One<User> userResult = this.getClient().users().byId(this.getUserID(), params -> params.withTrophies(true));



            boolean userPresent = userResult.isPresent();



            if (userPresent == true) {  // checking if the user is present in the lichess
                User user = userResult.get();
                String StatusEmoji = "\uD83D\uDD34";
                boolean checkOnline = this.getClient().users().statusByIds(this.getUserID()).stream().toList().get(0).online();
                if(checkOnline){
                    StatusEmoji = "\uD83D\uDFE2";
                }




                boolean cheater = user.tosViolation();

                boolean closedaccount = user.closed();

                if (cheater == true) { // check if the user is cheater
                   return " This user has violated Lichess Terms of Service";

                }
                if (closedaccount == true) { // check if user is clossed account
                   return "This account is closed";

                }


                if (cheater == false && closedaccount == false) {

                    Optional<User.Profile> profile = user.profile();

                    if(profile.isPresent()) {

                        String name = user.id();

                        String bio = profile.get().bio();

                        int wins = user.count().win();

                        int lose = user.count().loss();

                        int all = user.count().all();

                        int draw = user.count().draw();

                        int playing = user.count().playing();

                        String userUrl = user.url();

                        boolean pat = user.patron();

                        String patWings = "";

                        if (user.profile().isEmpty()) {

                          return "can't generate profiles for user, not enough profile data";
                        }



                        String sayTitle = "";

                        Optional<String> titledPlayer = user.title();

                        Boolean hasTitle;


                        if (titledPlayer.isPresent()) {
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
                        String embedRewards = "";


                        for (chariot.model.Trophy trophy : user.trophies()) {

                            UserTrophy userTrophy = new UserTrophy(trophy);
                            sayrewards += userTrophy.getImageLink() + "\n";
                        }

                        if(!user.trophies().isEmpty()) {

                            embedRewards += "\n\n ** \uD83D\uDCA0 User Trophies:** \n\n" + sayrewards;

                        }else{
                            embedRewards += "";
                        }


                        this.sayProfile +=  sayTitle + " " + name + " " +StatusEmoji + "\n" +"**All Games**: " + all + "\n" + "** ⚔️ Won:** " +   wins + " ** \uD83D\uDE14 Loss:** " + lose + " ** \uD83E\uDD1D Draw:** " + draw + "\n** ♗ Playing:** " + playing + "\n \uD83D\uDCB9 **Ratings**: \n" + this.getBlitzRatings() + "\n" + this.getRapidRatings() + "\n" + this.getBulletRatings() + "\n" + this.getClassicalRatings()+ embedRewards ;
                    }else{
                        return "Please add Bio to Your Profile!";
                    }

                }
            }
            if (userPresent == false) {
                return "User Not Present, Please try again";

            }

        }catch(Exception e){
            e.printStackTrace();
            return "Unknown Error..";
        }

        return sayProfile;


    }







}
