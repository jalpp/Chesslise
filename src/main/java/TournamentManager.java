import net.dv8tion.jda.api.EmbedBuilder;
import java.awt.*;

public class TournamentManager {


    private String AdminToken;
    private EmbedBuilder embedBuilder;

    public TournamentManager(String AdminToken){
        this.AdminToken = AdminToken;

    }


    public EmbedBuilder sayStatus(){

        this.embedBuilder = new EmbedBuilder();
        this.embedBuilder.setTitle("Manage Your Tournaments!");
        this.embedBuilder.setColor(Color.blue);
        this.embedBuilder.setDescription("**Your Tournament Manager**: \n\n **Create Tournament** \n Click here for creating a tournament \n\n **Create Monthly Tournament** \n Click here for creating Monthly Tournament");


        return this.embedBuilder;
    }

    public String getMonthlyTournamentStatus(){
        MonthlyTournament monthlyTournament = new MonthlyTournament(this.AdminToken);
        return monthlyTournament.getStatus();
    }

    public EmbedBuilder getBlitzTournamentCreated(){
        AdminLoginCreateTournament create = new AdminLoginCreateTournament(this.AdminToken, "blitz");
        return create.getCreatedTournament();
    }

    public EmbedBuilder getBulletTournamentCreated(){
        AdminLoginCreateTournament create = new AdminLoginCreateTournament(this.AdminToken, "bullet");
        return create.getCreatedTournament();
    }

    public EmbedBuilder getRapidTournamentCreated(){
        AdminLoginCreateTournament create = new AdminLoginCreateTournament(this.AdminToken, "rapid");
        return create.getCreatedTournament();
    }






}
