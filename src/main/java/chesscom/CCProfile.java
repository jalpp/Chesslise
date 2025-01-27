package chesscom;

import abstraction.Player.UserObject;
import io.github.sornerol.chess.pubapi.domain.player.stats.PlayerStats;
import io.github.sornerol.chess.pubapi.domain.player.stats.PuzzleRushStats;
import io.github.sornerol.chess.pubapi.domain.player.stats.RatingPoolStats;
import io.github.sornerol.chess.pubapi.exception.ChessComPubApiException;
import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;
import java.io.IOException;

/**
 * Chess.com Profile class to get user information
 */
public class CCProfile extends UserObject {

    public CCProfile(String username) {
        super(username);
    }

    /**
     * Get the Chess.com profile for the user
     *
     * @return the embed builder containing the information
     */
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

    /**
     * Get the Chess.com stats for the user
     *
     * @param rating the rating pool stats
     * @return the rating or "?"
     */
    private String getRatingOrDefault(RatingPoolStats rating) {
        return rating != null ? rating.getLast().getRating().toString() : "?";
    }

    /**
     * Get the Chess.com stats for the user
     *
     * @param stats the puzzle rush stats
     * @return the score or "?"
     */
    private String getStatsOrDefault(PuzzleRushStats stats) {
        return stats != null ? stats.getBest().getScore().toString() : "?";
    }
}
