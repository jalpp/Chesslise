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

    public UserDashboard(Client client, String[] userParsing) {
        super(client, userParsing);
    }




    /**
     *
     * ,stormdash command to see the storm score for given user
     *
     *
     * input: username
     *
     * output the score
     *
     *
     *
     */



    public EmbedBuilder getUserDashboard(){

        Result<User> userResult = this.getClient().users().byId(this.getUserID());
        this.embedBuilder = new EmbedBuilder();



        if(userResult.isPresent()){

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

            String highestscore = "";

            if (dash.isPresent()) {

                StormDashboard dashboard = dash.get();

                List<StormDashboard.Day> day = dashboard.days();
                StormDashboard.High high = dashboard.high();

                int allTime = high.allTime();

                int month = high.month();

                int week = high.week();

                int today = high.day();

                String link = "https://lichess.org/storm/dashboard/" + this.getUserID();


                for(int i = 0; i < 1; i++){

                    dashs += "  Date: " + day.get(i)._id() + "  Runs: " + day.get(i).runs() + " Score: " + day.get(i).score() + " Best Puzzle Solved: " + day.get(i).highest() + "\n";


                }



                this.embedBuilder = new EmbedBuilder();
                this.embedBuilder.setColor(Color.white);
                this.embedBuilder.setThumbnail("https://www.linkpicture.com/q/storm-yellow.png");
                this.embedBuilder.setTitle("StormDashboard for: " + title + " " + this.getUserID());
                this.embedBuilder.setDescription(" All Time High score: **" + allTime + "** \n \n This Month: **" + month + "\n\n **This Week: **" + week + "** \n \n **Today:** " + today+ "\n\n **Storm History:** \n \n" + dashs +  "\n\n [View on Lichess](" + link + ")");




            }

        }else{
            this.embedBuilder.setDescription("User not present ");
        }


        return this.embedBuilder;

    }



}

