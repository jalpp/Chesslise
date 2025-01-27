package abstraction.Player;

import chariot.Client;
import io.github.sornerol.chess.pubapi.client.PlayerClient;
import io.github.sornerol.chess.pubapi.domain.player.stats.PlayerStats;
import io.github.sornerol.chess.pubapi.exception.ChessComPubApiException;


import java.io.IOException;

/**
 * UserObject class to store user information
 */
public class UserObject {

    private Client client = Client.basic();
    private final String userID;
    private final PlayerClient playerClient = new PlayerClient();


    public UserObject(Client client, String userParsing) {
        this.client = client;
        this.userID = userParsing.toLowerCase().trim();
    }


    public UserObject(String username) {
        this.userID = username.toLowerCase().trim();
    }

    /**
     * Get the client
     *
     * @return the chariot client
     */
    public Client getClient() {
        return this.client;
    }

    /**
     * Get the user ID
     *
     * @return the user ID
     */
    public String getUserID() {
        return this.userID;
    }

    /**
     * Get the player client
     *
     * @return the player Chess.com Client
     */
    public PlayerClient getPlayerClient() {
        return playerClient;
    }

    /**
     * Get the Chess.com stats for the user
     *
     * @return the player stats
     * @throws ChessComPubApiException if there is an error with the Chess.com API
     * @throws IOException             if there is an error with the Chess.com API
     */
    public PlayerStats getChessComStats() throws ChessComPubApiException, IOException {
        return getPlayerClient().getStatsForPlayer(this.userID);
    }


}
