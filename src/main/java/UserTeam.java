import chariot.Client;
import chariot.model.LightUser;
import chariot.model.Result;
import chariot.model.Team;
import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;
import java.util.List;

public class UserTeam extends UserProfile{


    private EmbedBuilder embedBuilder;

    public UserTeam(Client client, String userParsing) {
        super(client, userParsing);
    }


    /**
     *
     * ,team command to see the team pages of lichess team
     *
     * input: the team name
     * note: if the team has spaces it must be included with - so Lichess swiss will be Lichess-swiss
     *
     * output: the team page of the given Lichess team (you can join teams also)
     *
     */

    public EmbedBuilder getUserTeam(){
        Result<Team> result = this.getClient().teams().byTeamId(this.getUserID());
        this.embedBuilder = new EmbedBuilder();

        if (result.isPresent()) { // check if the team is present
            Team team = result.get();

            List<LightUser> leader = team.leaders();

            String leadernames = "";


            for (int i = 0; i < leader.size(); i++) {
                leadernames += leader.get(i).title() + " " + leader.get(i).name() + " \n";
            }



            String url = "https://lichess.org/team/" + team.id();

            String tournaurl = url + "/tournaments";

            this.embedBuilder.setTitle(team.name() + " " + "Information");
            this.embedBuilder.setColor(Color.white);
            this.embedBuilder.setDescription("**Team name:** \n " + team.name() + "\n \n **Team Leaders:** \n" + leadernames + "\n\n **Team Members:** \n" + team.nbMembers() + "\n\n [Join team](" + url + ")" + "\n\n [Tournaments](" + tournaurl + ")");



        } else {
           return this.embedBuilder.setDescription("Team not found, Please try again!");
        }

        return this.embedBuilder;

    }





}
