import chariot.Client;
import chariot.ClientAuth;
import chariot.api.Users;
import chariot.model.Result;
import chariot.model.Team;
import chariot.model.User;
import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;
import java.util.List;

public class AdminManagerMembers extends AdminTeamManager{

    private EmbedBuilder embedBuilder;



    public AdminManagerMembers(ClientAuth clientAuth, Client client, String teamID, String userID, String token) {
        super(clientAuth, client, teamID, userID, token);
    }

    public boolean isLeader(){
        Result<chariot.model.Team> res = this.getClientAuth().teams().byTeamId(this.getTeamID());

        if(res.isPresent()){
            Team team = res.get();
            List<String> leaders = team.leaders().stream().map(user -> user.id()).toList();
            if (leaders.contains(this.getUserID())) {
                return true;
            }
        }


        return false;

    }


    public boolean isNotInTeam(){
     return false; // still to make
    }



    public EmbedBuilder getKickedMembersStatus(){

        this.embedBuilder = new EmbedBuilder();

        Client client = Client.basic();

        Result<User> usersResult = client.users().byId(this.getUserID());

        if(usersResult.isPresent() && !usersResult.get().tosViolation() && !usersResult.get().closed()){


            this.getClientAuth().teams().kickFromTeam(this.getTeamID(), this.getUserID());



            this.embedBuilder.setColor(Color.orange);
            this.embedBuilder.setTitle("Team Admin Page");
            this.embedBuilder.setDescription("Successfully Kicked the given user.");


        }



        return this.embedBuilder.setDescription("Error, User not present");


    }




}
