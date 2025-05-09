package lichess;

import chariot.Client;

import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;

public class Game {

    public Game() {

    }

    public static String generateOpenEndedChallengeURLs(int min, int sec, Boolean isRated, Client client) {
        AtomicReference<String> URL = new AtomicReference<>("");
        String gameMessage = "ChessLise Challenge Created! Please Join the Lichess Game!";
        if (isRated) {

            if (min == 0) {
                int ultra = 15;
                var result = client.challenges()
                        .challengeOpenEnded(conf -> conf.clock(ultra, sec).name(gameMessage).rated(true));
                result.ifPresent(play -> URL.updateAndGet(v -> v + play.challenge().url()));
            } else {
                int clock = min * 60;
                var result = client.challenges()
                        .challengeOpenEnded(conf -> conf.clock(clock, sec).name(gameMessage).rated(true));
                result.ifPresent(play -> URL.updateAndGet(v -> v + play.challenge().url()));
            }
        } else {
            if (min == 0) {
                int ultraC = 15;
                var result = client.challenges()
                        .challengeOpenEnded(conf -> conf.clock(ultraC, sec).name(gameMessage).rated(false));
                result.ifPresent(play -> URL.updateAndGet(v -> v + play.challenge().url()));
            }
            {
                int clock = min * 60;
                var result = client.challenges()
                        .challengeOpenEnded(conf -> conf.clock(clock, sec).name(gameMessage).rated(false));
                result.ifPresent(play -> URL.updateAndGet(v -> v + play.challenge().url()));
            }
        }

        return "## Both Players Join The Live Chess Challenge  \n ↪️ " + URL.get()
                + " \n **Note:** Challenge expires after 24 hours!";
    }

    public static String generateOpenChallengeForTwoUsers(String self_user, String target_user, Client client) {
        AtomicReference<String> URL = new AtomicReference<>("");
        int min = new Random().nextInt(1, 10) * 60;
        int sec = new Random().nextInt(1, 5);

        var result = client.challenges()
                .challengeOpenEnded(conf -> conf.clock(min, sec)
                        .name("LISEBOT Challenge Created! \n " + self_user + " VS " + target_user)
                        .users(self_user, target_user).rated(false));
        result.ifPresent(play -> URL.updateAndGet(v -> v + play.challenge().url()));

        return "## Random Challenge Mode! \uD83C\uDFB2 \n \n" + self_user + " VS " + target_user + "\n ↪️ " + URL.get()
                + " \n **Note:** Challenge expires after 24 hours!";

    }

    public static boolean isLink(String link) {
        return link.contains("/") && link.contains(".");
    }

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