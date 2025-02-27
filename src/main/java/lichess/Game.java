package lichess;

import chariot.Client;

import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Game class to handle the game creation on Lichess
 */
public class Game {

    public Game() {

    }

    /**
     * generate the open ended Lichess game links
     * @param min the game time control
     * @param sec the game increment
     * @param isRated if the game is rated or not
     * @param client the chariot client
     * @return the game link
     */
    public static String generateOpenEndedChallengeURLs(int min, int sec, Boolean isRated, Client client) {
        AtomicReference<String> URL = new AtomicReference<>("");
        if (isRated) {

            if (min == 0) {
                int ultra = 15;
                var result = client.challenges().challengeOpenEnded(conf -> conf.clock(ultra, sec).name("LISEBOT Challenge Created! Please Join the Lichess Game!").rated(true));
                result.ifPresent(play -> URL.updateAndGet(v -> v + play.challenge().url()));
            } else {
                int clock = min * 60;
                var result = client.challenges().challengeOpenEnded(conf -> conf.clock(clock, sec).name("LISEBOT Challenge Created! Please Join the Lichess Game!").rated(true));
                result.ifPresent(play -> URL.updateAndGet(v -> v + play.challenge().url()));
            }
        } else {
            if (min == 0) {
                int ultraC = 15;
                var result = client.challenges().challengeOpenEnded(conf -> conf.clock(ultraC, sec).name("LISEBOT Challenge Created! Please Join the Lichess Game!").rated(false));
                result.ifPresent(play -> URL.updateAndGet(v -> v + play.challenge().url()));
            }
            {
                int clock = min * 60;
                var result = client.challenges().challengeOpenEnded(conf -> conf.clock(clock, sec).name("LISEBOT Challenge Created! Please Join the Lichess Game!").rated(false));
                result.ifPresent(play -> URL.updateAndGet(v -> v + play.challenge().url()));
            }
        }

        return "## Both Players Join The Live Chess Challenge  \n ↪️ " + URL.get() + " \n **Note:** Challenge expires after 24 hours!";
    }


    /**
     * generate game links for two username pairs
     * @param self_user the current user
     * @param target_user the friend target user
     * @param client the chariot client
     * @return the game link
     */
    public static String generateOpenChallengeForTwoUsers(String self_user, String target_user, Client client) {
        AtomicReference<String> URL = new AtomicReference<>("");
        int min = new Random().nextInt(1, 10) * 60;
        int sec = new Random().nextInt(1, 5);

        var result = client.challenges().challengeOpenEnded(conf -> conf.clock(min, sec).name("LISEBOT Challenge Created! \n " + self_user + " VS " + target_user).users(self_user, target_user).rated(false));
        result.ifPresent(play -> URL.updateAndGet(v -> v + play.challenge().url()));

        return "## Random Challenge Mode! \uD83C\uDFB2 \n \n" + self_user + " VS " + target_user + "\n ↪️ " + URL.get() + " \n **Note:** Challenge expires after 24 hours!";

    }

    /**
     * check if input is link or not
     * @param link the link
     * @return is link or not
     */
    public static boolean isLink(String link) {
        return link.contains("/") && link.contains(".");
    }

    /**
     * parse the Lichess game id from given game link
     * @param link the game link
     * @return the game id
     */
    public static String getValidGameId(String link) {
        String[] spliter = link.split("/");
        String validGameId = "";

        if (spliter.length <= 3)
            return null;

        if (spliter[3].length() == 12) {
            validGameId += spliter[3].substring(0, spliter[3].length() - 4);
        } else {
            validGameId += spliter[3];
        }

        if (!(link.contains("https://lichess.org/") && Client.basic().games().byGameId(validGameId).isPresent())) {
            return null;
        }

        return validGameId;
    }


}