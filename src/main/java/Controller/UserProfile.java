import chariot.Client;
import chariot.model.Enums;
import chariot.model.One;
import chariot.model.PerformanceStatistics;
import chariot.model.ProvidedProfile;
import chariot.model.Some;
import chariot.model.User;

import java.util.List;
import java.util.Optional;

public class UserProfile extends UserObject{


    private String sayProfile = new String("");

    public UserProfile(Client client, String userParsing){
        super(client, userParsing);

    }




    public String getBlitzRatings(){
        One<PerformanceStatistics> userBlitz = this.getClient().users().performanceStatisticsByIdAndType(this.getUserID(), Enums.PerfType.blitz);


        String blitzRating = " \uD83D\uDD25 **Blitz**: ?";

        if(userBlitz.isPresent() && !userBlitz.get().perf().glicko().provisional()){
            return blitzRating = " \uD83D\uDD25 **Blitz**:  " + userBlitz.get().perf().glicko().rating().intValue();
        }

        return blitzRating;

    }

    public String getRapidRatings(){
        One<PerformanceStatistics> userRapid = this.getClient().users().performanceStatisticsByIdAndType(this.getUserID(), Enums.PerfType.rapid);

        String rapidRating = " \uD83D\uDC07 **Rapid**: ?";

        if(userRapid.isPresent() && !userRapid.get().perf().glicko().provisional()){
            return rapidRating = "\uD83D\uDC07 **Rapid**:  " + userRapid.get().perf().glicko().rating().intValue();
        }

        return rapidRating;

    }

    public String getBulletRatings(){
        One<PerformanceStatistics> userBullet = this.getClient().users().performanceStatisticsByIdAndType(this.getUserID(), Enums.PerfType.bullet);

        String bulletRating = "\uD83D\uDD2B **Bullet**: ?";

        if(userBullet.isPresent() && !userBullet .get().perf().glicko().provisional()){
            return bulletRating = "\uD83D\uDD2B **Bullet**:  " + userBullet .get().perf().glicko().rating().intValue();
        }

        return bulletRating;

    }

    public String getClassicalRatings(){
        One<PerformanceStatistics> usercal = this.getClient().users().performanceStatisticsByIdAndType(this.getUserID(), Enums.PerfType.classical);

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



            if (userPresent == true) {  
                User user = userResult.get();
                String StatusEmoji = "\uD83D\uDD34";
                boolean checkOnline = this.getClient().users().statusByIds(this.getUserID()).stream().toList().get(0).online();
                if(checkOnline){
                    StatusEmoji = "\uD83D\uDFE2";
                }




                boolean cheater = user.tosViolation();

                boolean closedaccount = user.disabled();

                if (cheater == true) {
                   return " This user has violated Lichess Terms of Service";

                }
                if (closedaccount == true) { 
                   return "This account is closed";

                }


                if (cheater == false && closedaccount == false) {

                    ProvidedProfile profile = user.profile();

                    String name = user.id();

                    String bio = profile.bio().orElse("");

                        int wins = user.accountStats().win();

                        int lose = user.accountStats().loss();

                        int all = user.accountStats().all();

                        int draw = user.accountStats().draw();

                        int playing = user.accountStats().playing();

                        String userUrl = user.url().toURL().toString();



                        boolean pat = user.patron();

                        String patWings = "";

                        String sayTitle = user.title().orElse("");

                        Boolean hasTitle = user.title() instanceof Some<String>;

                        String sayrewards = "";
                        String embedRewards = "";

                        List<chariot.model.Trophy> trophies = user.trophies().orElse(List.of());

                        for (chariot.model.Trophy trophy : trophies) {

                            UserTrophy userTrophy = new UserTrophy(trophy);
                            sayrewards += userTrophy.getImageLink() + "\n";
                        }

                        if(!trophies.isEmpty()) {

                            embedRewards += "\n\n ** \uD83D\uDCA0 Trophies:** \n\n" + sayrewards;

                        }else{
                            embedRewards += "";
                        }


                        this.sayProfile +=  sayTitle + " " + name + " " +StatusEmoji + "\n" +"**All Games**: " + all + "\n" + "** ⚔️ Won:** " +   wins + " ** \uD83D\uDE14 Loss:** " + lose + " ** \uD83E\uDD1D Draw:** " + draw + "\n** ♗ Playing:** " + playing + "\n \uD83D\uDCB9 **Ratings**: \n" + this.getBlitzRatings() + "\n" + this.getRapidRatings() + "\n" + this.getBulletRatings() + "\n" + this.getClassicalRatings()+ embedRewards ;
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
