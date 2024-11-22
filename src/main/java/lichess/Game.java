package lichess;

import chariot.Client;

import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;


public class Game {

    public Game() {

    }

    public String generateOpenEndedChallengeURLs(int min, int sec, Boolean isRated, Client client) {
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



    public String generateOpenChallengeForTwoUsers(String self_user, String target_user, Client client){
        AtomicReference<String> URL = new AtomicReference<>("");
        int min = new Random().nextInt(1, 10) * 60;
        int sec = new Random().nextInt(1, 5);

        var result = client.challenges().challengeOpenEnded(conf -> conf.clock(min, sec).name("LISEBOT Challenge Created! \n " + self_user + " VS " + target_user).users(self_user, target_user).rated(false));
        result.ifPresent(play -> URL.updateAndGet(v -> v + play.challenge().url()));

        return "## Random Challenge Mode! \uD83C\uDFB2 \n \n" + self_user + " VS " + target_user + "\n ↪️ " + URL.get() + " \n **Note:** Challenge expires after 24 hours!";

    }







}