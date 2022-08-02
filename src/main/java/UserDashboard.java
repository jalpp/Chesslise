import chariot.Client;
import chariot.model.Result;
import chariot.model.StormDashboard;
import chariot.model.User;
import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;
import java.util.List;
import java.util.Optional;

public class UserDashboard extends UserProfile{

    private EmbedBuilder embedBuilder;

    public UserDashboard(Client client, String userParsing) {
        super(client, userParsing);
    }



    public EmbedBuilder getUserDashboard(){

        Result<User> userResult = this.getClient().users().byId(this.getUserID());
        this.embedBuilder = new EmbedBuilder();



        if(userResult.isPresent() && !userResult.get().closed() && !userResult.get().tosViolation()){

            User user = userResult.get();

            String title = "";

            Optional<String> titleplayer = user.title();

            if(titleplayer.isPresent()){
                title += titleplayer.get();
            }else{
                title = "";
            }


            Result<StormDashboard> dash = this.getClient().puzzles().stormDashboard(this.getUserID());

            String dashs = "";

            if (dash.isPresent()) {

                StormDashboard dashboard = dash.get();

                List<StormDashboard.Day> day = dashboard.days();

                if(day.isEmpty()){
                    this.embedBuilder = new EmbedBuilder();
                    this.embedBuilder.setColor(Color.red);
                    return this.embedBuilder.setDescription("This User has not played enough storm puzzles to calculate the dashboard");
                }

                StormDashboard.High high = dashboard.high();

                int allTime = high.allTime();

                int month = high.month();

                int week = high.week();

                int today = high.day();

                String link = "https://lichess.org/storm/dashboard/" + this.getUserID();


                for(int i = 0; i < 1; i++){

                    dashs += "  Date: " + day.get(i)._id() + "  Runs: " + day.get(i).runs() + " Score: " + day.get(i).score() + " Best Puzzle Solved: " + day.get(i).highest() + "\n";


                }
                String todayAns = "";

                if(today == 0){
                    todayAns += "No stormdash was played today";
                }else{
                    todayAns += today;
                }

                this.embedBuilder = new EmbedBuilder();
                this.embedBuilder.setColor(Color.orange);
                this.embedBuilder.setThumbnail("https://prismic-io.s3.amazonaws.com/lichess/4689c1c3-092d-46f9-b554-ca47e91c7d81_storm-yellow.png");
                this.embedBuilder.setTitle("StormDashboard for: " + title + " " + this.getUserID());
                this.embedBuilder.setDescription(" All Time High score: **" + allTime + "** \n \n This Month: **" + month + "\n\n **This Week: **" + week + "** \n \n **Today:** " + todayAns + "\n\n **Storm History:** \n \n" + dashs +  "\n\n [View on Lichess](" + link + ")");




            }else{
                this.embedBuilder = new EmbedBuilder();
                this.embedBuilder.setColor(Color.red);
                return this.embedBuilder.setDescription("This User has not played enough storm puzzles to calculate the dashboard");
            }

        }else{
            this.embedBuilder = new EmbedBuilder();
            this.embedBuilder.setColor(Color.red);
            return this.embedBuilder.setDescription("User not present");
        }


        return this.embedBuilder;

    }



}
