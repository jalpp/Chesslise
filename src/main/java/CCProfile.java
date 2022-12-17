import io.github.sornerol.chess.pubapi.client.PlayerClient;
import io.github.sornerol.chess.pubapi.client.StreamersClient;
import io.github.sornerol.chess.pubapi.client.TournamentClient;
import io.github.sornerol.chess.pubapi.domain.game.ArchiveGame;
import io.github.sornerol.chess.pubapi.domain.player.Player;
import io.github.sornerol.chess.pubapi.domain.player.stats.PlayerStats;
import io.github.sornerol.chess.pubapi.exception.ChessComPubApiException;
import net.dv8tion.jda.api.EmbedBuilder;
import java.time.LocalDate;
import java.awt.*;
import java.io.IOException;
import java.util.List;

public class CCProfile {

    private String username;
    private EmbedBuilder embedBuilder;
    private  PlayerClient playerClient;






    public CCProfile(String username){
        this.username = username.toLowerCase().trim();
    }



    public EmbedBuilder getCCProfile(){


        try {
            this.playerClient = new PlayerClient();
            this.embedBuilder = new EmbedBuilder();
            String proSay = "";
            String say = "";


            PlayerStats player = playerClient.getStatsForPlayer(this.username);

            proSay+= " ** Bullet**: " +
                     player.getChessBullet().getLast().getRating().intValue() +
                     "\n **Rapid:**  " +
                     player.getChessRapid().getLast().getRating().intValue() +
                    "\n **Blitz:** " +
                    player.getChessBlitz().getLast().getRating().intValue() +
                    "\n **PuzzleRush:** " +
                    player.getPuzzleRush().getBest().getScore().intValue();
           this.embedBuilder.setThumbnail("https://images.chesscomfiles.com/uploads/v1/images_users/tiny_mce/SamCopeland/phpmeXx6V.png").setTitle(username + "'s Chess.com Profile").setDescription(proSay).setColor(Color.green);




        } catch (IOException e) {
            return this.embedBuilder.setDescription("error! Please provide a valid username!").setColor(Color.red);
        } catch (ChessComPubApiException e) {
            return this.embedBuilder.setDescription("loading!");
        }

        return this.embedBuilder;

    }






}
