import chariot.Client;
import chariot.model.Result;
import chariot.model.Tournament;
import chariot.model.TournamentStatus;
import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;
import java.util.List;

public class currentTournament {

    private EmbedBuilder embedBuilder;
    private Client client;

    public currentTournament(Client client){
        this.client = client;
    }

    public EmbedBuilder getTournaments(){
        Result<TournamentStatus> result = this.client.tournaments().currentTournaments();
        String startedTournament = "";
        this.embedBuilder = new EmbedBuilder();
        this.embedBuilder.setTitle("Current Lichess Tournaments");
        this.embedBuilder.setThumbnail("https://images.prismic.io/lichess/25a60c33-96ad-4fd1-b9b2-dca8e289961f_lichesslogo.png?auto=compress,format");


        if(result.isPresent()){
            List<TournamentStatus> tournamentStatuses = result.stream().toList();

            for(int i = 0; i < tournamentStatuses.size(); i++){
                List<Tournament> tour =  tournamentStatuses.get(i).started();

                for(int j =0; j < tour.size(); j++){
                    startedTournament +="\uD83C\uDF96️ " + "**" + tour.get(j).fullName() + "**" + " [Join Now](" + "https://lichess.org/tournament/" + tour.get(j).id() + ")" + "\n\n";
                }

            }

           this.embedBuilder.setDescription(startedTournament);
           this.embedBuilder.setColor(Color.cyan);



        }

        return this.embedBuilder;

    }




}
