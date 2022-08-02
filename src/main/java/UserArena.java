import chariot.Client;
import chariot.model.Arena;
import chariot.model.Result;
import chariot.model.Swiss;
import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;
import java.util.List;

public class UserArena {

    private Client client;
    private EmbedBuilder embedBuilder;
    private String arenaID;

    public UserArena(Client client, String arenaID) {
        this.client = client;
        this.arenaID = arenaID;
    }




    public EmbedBuilder getUserArena() {

        try {

            String[] spliturl = this.arenaID.split("tournament/");
            this.embedBuilder = new EmbedBuilder();
            String[] emojileaderboard = {"1️⃣", "2️⃣", "3️⃣", "4️⃣", "5️⃣", "6️⃣", "7️⃣", "8️⃣", "9️⃣", "\uD83D\uDD1F"};

            String touryID = "";

            for (String a : spliturl) {

                touryID = a;

            }


            Result<Arena> arenaResult1 = client.tournaments().arenaById(touryID);

            if (arenaResult1.isPresent() && spliturl[0].startsWith("http") && spliturl[0].contains("lichess")) {

                Arena arena = arenaResult1.get();



                String name = arena.fullName();

                int numPlayers = arena.nbPlayers();

                int timeLeft = arena.minutes();

                Arena.Perf perf = arena.perf();

                String perfname = perf.name();

                String stand = "";


                Arena.Standing standing = arena.standing();


                List<Arena.Standing.Player> players = standing.players();

                for (int i = 0; i < players.size(); i++) {

                    stand +=  emojileaderboard[i] + " " + players.get(i).name() + "  " + players.get(i).rating() + "  **" + players.get(i).score() + "** " + players.get(i).team() + "\n ";


                }


                this.embedBuilder = new EmbedBuilder();
                this.embedBuilder.setColor(Color.white);
                this.embedBuilder.setTitle( "\uD83C\uDF96️ " + name  + " \uD83C\uDF96️");
                this.embedBuilder.setDescription("** Tournament Name:** " + name + "\n **Variant:** " + perfname  + "\n **Time Duration :** " + timeLeft + " mins" + "\n **Total Players:** " + numPlayers + "\n **Standings:**" + "\n  **Rank:**  **Username**  **Rating:**  **Score** \n \n " + stand + "\n" + "[View on Lichess](" + this.arenaID + ")");

            }else{
                this.embedBuilder = new EmbedBuilder();
                String[] splitswiss = this.arenaID.split("swiss/");

                String touryIDSwiss = "";

                for (String a : splitswiss) {

                    touryIDSwiss = a;

                }
                Result<Swiss> swissResult = this.client.tournaments().swissById(touryIDSwiss);

                if(swissResult.isPresent()){
                    SwissResults swissResults = new SwissResults(client, touryIDSwiss);
                    return swissResults.getLinkResults();
                }

                return this.embedBuilder.setDescription("Error occurred, please provide valid Lichess URL");
            }

        }catch (Exception e){
            e.printStackTrace();
            this.embedBuilder = new EmbedBuilder();
            return this.embedBuilder.setDescription("Error occurred, please provide valid Lichess arena url");
        }

        return this.embedBuilder;


    }


}
