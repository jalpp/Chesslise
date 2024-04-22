package Lichess;

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

    public SwissResults(Client client, String link) {
        this.client = client;
        this.link = link;
    }


    public EmbedBuilder getLinkResults() {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        String[] emojileaderboard = {"1️⃣", "2️⃣", "3️⃣", "4️⃣", "5️⃣", "6️⃣", "7️⃣", "8️⃣", "9️⃣", "\uD83D\uDD1F"};
        String[] podium = {"\uD83C\uDFC6", "\uD83E\uDD48", "\uD83E\uDD49"};
        StringBuilder res = new StringBuilder();
        String info = "";
        StringBuilder podiumres = new StringBuilder();


        Many<SwissResult> swissResultResult = this.client.tournaments().resultsBySwissId(this.link);
        One<Swiss> result = this.client.tournaments().swissById(this.link);

        if (result.isPresent()) {
            info += "**Total Players:** " + result.get().nbPlayers() + "\n" + "**Total Rounds:** " + result.get().nbRounds() + "\n **Name Rating Score Tiebreak**\n\n";
            List<SwissResult> list = swissResultResult.stream().toList();
            for (int i = 0; i < 3; i++) {
                podiumres.append(podium[i]).append(" ").append(list.get(i).username()).append(" ").append(list.get(i).rating()).append(" **").append(list.get(i).points()).append("** ").append(list.get(i).tieBreak()).append("\n");
            }

            for (int i = 0; i < 10; i++) {
                res.append(emojileaderboard[i]).append(" ").append(list.get(i).username()).append(" ").append(list.get(i).rating()).append(" **").append(list.get(i).points()).append("** ").append(list.get(i).tieBreak()).append("\n");
            }
            embedBuilder.setColor(Color.green);
            embedBuilder.setDescription(info + "\n\n **Podium** \n" + podiumres + "\n\n" + res);
            embedBuilder.setTitle(result.get().name());

        }

        return embedBuilder;


    }


}
