import chariot.Client;
import chariot.ClientAuth;
import chariot.model.PuzzleRace;
import chariot.model.Result;
import net.dv8tion.jda.api.EmbedBuilder;

public class PuzzleRacer {


    private String adminToken;
    private EmbedBuilder embedBuilder;



    public PuzzleRacer(String adminToken){
        this.adminToken = adminToken;

    }

    public EmbedBuilder getPuzzleRacerLinks(){


        this.embedBuilder = new EmbedBuilder();
        ClientAuth clientAuth = Client.auth(this.adminToken);

        this.embedBuilder.setTitle("\uD83D\uDE97 Play Puzzle Racer \uD83D\uDE97");
        Result<PuzzleRace> result = clientAuth.puzzles().createAndJoinRace();
        String join = "[Click Here To Play Puzzle Racer](";
        if(result.isPresent()){
            join += result.get().url() + ")";
            this.embedBuilder.setDescription(join);
            return this.embedBuilder;
        }else{
            this.embedBuilder.setTitle("Invalid Input!");
            return this.embedBuilder.setDescription("Error Occurred!");
        }

    }






}
