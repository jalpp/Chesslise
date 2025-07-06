package lichess;

import chariot.Client;
import net.dv8tion.jda.api.interactions.components.selections.SelectOption;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Game class to handle the game creation on Lichess
 */
public class Game {

    public Game() {

    }

    public static HashMap<String, int[]> GAME_TC = new HashMap<>(){{
        put("1/4+0", new int[]{0, 0});
        put("1+0", new int[]{1,0});
        put("3+0", new int[]{3,0});
        put("3+2", new int[]{3,2});
        put("5+0", new int[]{5,0});
        put("5+5", new int[]{5,5});
        put("10+0", new int[]{10,0});
        put("10+5", new int[]{10,5});
        put("15+0", new int[]{15,0});
        put("15+5", new int[]{15,5});
        put("30+0", new int[]{30,0});
        put("30+30", new int[]{30,30});
        put("45+30", new int[]{45,30});
        put("60+0", new int[]{60,0});
        put("60+30", new int[]{60,30});
        put("90+0", new int[]{90,0});
        put("90+30", new int[]{90, 30});
    }};

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
        String gameMessage = "ChessLise Challenge Created! Please Join the Lichess Game!";
        if (isRated) {

            if (min == 0) {
                int ultra = 15;
                var result = client.challenges().challengeOpenEnded(conf -> conf.clock(ultra, sec).name(gameMessage).rated(true));
                result.ifPresent(play -> URL.updateAndGet(v -> v + play.challenge().url()));
            } else {
                int clock = min * 60;
                var result = client.challenges().challengeOpenEnded(conf -> conf.clock(clock, sec).name(gameMessage).rated(true));
                result.ifPresent(play -> URL.updateAndGet(v -> v + play.challenge().url()));
            }
        } else {
            if (min == 0) {
                int ultraC = 15;
                var result = client.challenges().challengeOpenEnded(conf -> conf.clock(ultraC, sec).name(gameMessage).rated(false));
                result.ifPresent(play -> URL.updateAndGet(v -> v + play.challenge().url()));
            }
            {
                int clock = min * 60;
                var result = client.challenges().challengeOpenEnded(conf -> conf.clock(clock, sec).name(gameMessage).rated(false));
                result.ifPresent(play -> URL.updateAndGet(v -> v + play.challenge().url()));
            }
        }

        return "## Both Players Join The Live Chess Challenge  \n ↪️ " + URL.get() + " \n **Note:** Challenge expires after 24 hours!";
    }

    public static String generateOpenEndedCoresChallengeURLs(int daysPerTurn, Client client){
        AtomicReference<String> URL = new AtomicReference<>("");
        String gameMessage = "ChessLise Challenge Created! Please Join the Lichess Game!";

        var result = client.challenges().challengeOpenEnded(conf -> conf.daysPerTurn(daysPerTurn).name(gameMessage).rated(false));
        result.ifPresent(play -> URL.updateAndGet(v -> v + play.challenge().url()));

        return "## Both Players Join The Correspondence Challenge! Both of you have " + daysPerTurn + " Days per turn \n ↪️ " + URL.get();
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

        var result = client.challenges().challengeOpenEnded(conf -> conf.clock(min, sec).name("ChessLise Challenge Created! \n " + self_user + " VS " + target_user).users(self_user, target_user).rated(false));
        result.ifPresent(play -> URL.updateAndGet(v -> v + play.challenge().url()));

        return "## Random Challenge Mode! \uD83C\uDFB2 \n \n" + self_user + " VS " + target_user + "\n ↪️ " + URL.get() + " \n **Note:** Challenge expires after 24 hours!";

    }

    public static ArrayList<SelectOption> getGameSelectOption(){
        LinkedHashMap<String, int[]> sorted = new LinkedHashMap<>();
        GAME_TC.entrySet()
                .stream()
                .sorted(Comparator.comparingInt(entry -> entry.getValue()[0]))
                .forEachOrdered(entry -> sorted.put(entry.getKey(), entry.getValue()));

        ArrayList<SelectOption> options = new ArrayList<>();
        for (String key : sorted.keySet()){
            options.add(SelectOption.of(key, key));
        }

        return options;
    }

    public static ArrayList<SelectOption> getGameCorresSelectOption(){
        int[] daysPerMove = {1,2,3,5,7,10,14};
        ArrayList<SelectOption> gameOptions = new ArrayList<>();

        for(int key : daysPerMove){
            gameOptions.add(SelectOption.of(Integer.toString(key), Integer.toString(key)));
        }

        return gameOptions;
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