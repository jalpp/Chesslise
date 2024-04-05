package Chesscom;

import Abstraction.Player.UserObject;
import io.github.sornerol.chess.pubapi.domain.player.stats.PlayerStats;
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
                    player.getChessBullet().getLast().getRating() +
                    "\n **Rapid:**  " +
                    player.getChessRapid().getLast().getRating() +
                    "\n **Blitz:** " +
                    player.getChessBlitz().getLast().getRating() +
                    "\n **PuzzleRush:** " +
                    player.getPuzzleRush().getBest().getScore();
            embedBuilder.setThumbnail(this.getPlayerClient().getPlayerByUsername(getUserID()).getAvatarUrl()).setTitle(this.getUserID() + "'s Chess.com Profile").setDescription(proSay).setColor(Color.green);

        } catch (IOException e) {
            return embedBuilder.setDescription("error! Please provide a valid username!").setColor(Color.red);
        } catch (ChessComPubApiException e) {
            return embedBuilder.setDescription("loading!");
        }

        return embedBuilder;

    }


}
