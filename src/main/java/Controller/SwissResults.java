import chariot.Client;
import chariot.model.Many;
import chariot.model.One;
import chariot.model.Swiss;
import chariot.model.SwissResult;
import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;
import java.util.List;

public class SwissResults {

    private final Client client;
    private final String link;
  
    public SwissResults(Client client, String link){
        this.client = client;
        this.link =  link;
    }


    public EmbedBuilder getLinkResults(){
        EmbedBuilder embedBuilder = new EmbedBuilder();
        String[] emojileaderboard = {"1️⃣", "2️⃣", "3️⃣", "4️⃣", "5️⃣", "6️⃣", "7️⃣", "8️⃣", "9️⃣", "\uD83D\uDD1F"};
        String[] podium = {"\uD83C\uDFC6", "\uD83E\uDD48", "\uD83E\uDD49"};
        String res = "";
        String info = "";
        String podiumres = "";



        Many<SwissResult> swissResultResult = this.client.tournaments().resultsBySwissId(this.link);
        One<Swiss> result = this.client.tournaments().swissById(this.link);

        if(result.isPresent()){
            info += "**Total Players:** " + result.get().nbPlayers() + "\n" + "**Total Rounds:** " + result.get().nbRounds() + "\n **Name Rating Score Tiebreak**\n\n";
            List<SwissResult> list = swissResultResult.stream().toList();
            for(int i = 0; i < 3 ; i++){
                podiumres += podium[i] + " "+ list.get(i).username() + " " + list.get(i).rating() + " **" + list.get(i).points() + "** " + list.get(i).tieBreak() + "\n";
            }

            for(int i = 0; i < 10 ; i++){
                res += emojileaderboard[i] + " "+ list.get(i).username() + " " + list.get(i).rating() + " **" + list.get(i).points() + "** " + list.get(i).tieBreak() + "\n";
            }
            this.embedBuilder.setColor(Color.green);
            this.embedBuilder.setDescription( info + "\n\n **Podium** \n" + podiumres + "\n\n" + res );
            this.embedBuilder.setTitle(result.get().name());

        }

        return this.embedBuilder;


    }



}
