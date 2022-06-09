import chariot.Client;
import chariot.model.Arena;
import chariot.model.Result;
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


    /**
     *
     * ,arena command to see arena page of given link
     *
     * input: Lichess link for that arena
     *
     * output: The whole arena page including, the tournament name, time duration, variant, number of players,
     * and of course the player standings, also provide the team name if the tournament is a team battle
     *
     *
     *
     */


    public EmbedBuilder getUserArena() {

        try {

            String[] spliturl = this.arenaID.split("tournament/");
            this.embedBuilder = new EmbedBuilder();

            String touryID = "";

            for (String a : spliturl) {

                touryID = a;

            }


            Result<Arena> arenaResult1 = client.tournaments().arenaById(touryID);

            if (arenaResult1.isPresent()) {

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

                    stand += players.get(i).rank() + " " + players.get(i).name() + "  " + players.get(i).rating() + "  " + players.get(i).score() + " " + players.get(i).team() + "\n ------------------------------------------------------------------- \n ";


                }


                this.embedBuilder = new EmbedBuilder();
                this.embedBuilder.setColor(Color.white);
                this.embedBuilder.setTitle(name);
                this.embedBuilder.setDescription("**Tournament Name:** " + name + "\n\n**Variant:** " + perfname + "\n" + "\n\n **Time Duration :** " + timeLeft + " mins" + "\n **Total Players:** " + numPlayers + "\n\n **Standings:**" + "\n \n **Rank:**  **Username**  **Rating:**  **Score** \n \n " + stand + "\n\n" + "[View on Lichess](" + this.arenaID + ")");

            }

        }catch (Exception e){
            e.printStackTrace();
            this.embedBuilder = new EmbedBuilder();
            return this.embedBuilder.setDescription("Error occurred, please provide valid Lichess arena url");
        }

        return this.embedBuilder;


    }


}
