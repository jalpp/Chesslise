import chariot.Client;
import chariot.model.Result;
import chariot.model.Swiss;
import chariot.model.SwissResult;
import net.dv8tion.jda.api.EmbedBuilder;

import java.util.List;

public class SwissResults {

    private Client client;
    private String link;
    private EmbedBuilder embedBuilder;
    public SwissResults(Client client, String link){
        this.client = client;
        this.link =  link;
    }


    public EmbedBuilder getLinkResults(){
        this.embedBuilder = new EmbedBuilder();
        String[] emojileaderboard = {"1️⃣", "2️⃣", "3️⃣", "4️⃣", "5️⃣", "6️⃣", "7️⃣", "8️⃣", "9️⃣", "\uD83D\uDD1F"};
        String res = "";
        String info = "";
        Result<SwissResult> swissResultResult = this.client.tournaments().resultsBySwissId(this.link);
        Result<Swiss> result = this.client.tournaments().swissById(this.link);
        if(swissResultResult.isPresent() && result.isPresent()){
            info += "**Total Players:** " + result.get().nbPlayers() + "\n" + "**Total Rounds:** " + result.get().nbRounds() + "\n **Name Rating Score Tiebreak**\n\n";
            List<SwissResult> list = swissResultResult.stream().toList();
            for(int i = 0; i < 10 ; i++){
                res += emojileaderboard[i] + " "+ list.get(i).username() + " " + list.get(i).rating() + " **" + list.get(i).points() + "** " + list.get(i).tieBreak() + "\n";
            }
            this.embedBuilder.setDescription( info + res );
            this.embedBuilder.setTitle(result.get().name());

        }

        return this.embedBuilder;


    } 



}
