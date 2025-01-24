package chesscom;

import abstraction.Player.UserObject;
import io.github.sornerol.chess.pubapi.domain.player.stats.PlayerStats;
import io.github.sornerol.chess.pubapi.domain.player.stats.PuzzleRushStats;
import io.github.sornerol.chess.pubapi.domain.player.stats.RatingPoolStats;
import io.github.sornerol.chess.pubapi.exception.ChessComPubApiException;
import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;
import java.io.IOException;

public class CCProfile extends UserObject {

    public CCProfile(String username) {
        super(username);
    }

    public EmbedBuilder getCCProfile() {
        EmbedBuilder embedBuilder = null;
        try {

            embedBuilder = new EmbedBuilder();
            String proSay = "";

            PlayerStats player = this.getChessComStats();

            proSay += "**Bullet**: " +
                    getRatingOrDefault(player.getChessBullet()) +
                    "\n **Rapid:**  " +
                    getRatingOrDefault(player.getChessRapid()) +
                    "\n **Blitz:** " +
                    getRatingOrDefault(player.getChessBlitz()) +
                    "\n **PuzzleRush:** " +
                    getStatsOrDefault(player.getPuzzleRush());
            embedBuilder.setThumbnail(this.getPlayerClient().getPlayerByUsername(getUserID()).getAvatarUrl()).setTitle(this.getUserID() + "'s Chess.com Profile").setDescription(proSay).setColor(Color.green);

        } catch (IOException e) {
            return embedBuilder.setDescription("error! Please provide a valid username!").setColor(Color.red);
        } catch (ChessComPubApiException e) {
            return embedBuilder.setDescription("loading!");
        }

        return embedBuilder;
    }

    private String getRatingOrDefault(RatingPoolStats rating) {
        return rating != null ? rating.getLast().getRating().toString() : "?";
    }

    private String getStatsOrDefault(PuzzleRushStats stats) {
        return stats != null ? stats.getBest().getScore().toString() : "?";
    }
}
