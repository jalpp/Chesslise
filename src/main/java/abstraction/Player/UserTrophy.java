package abstraction.Player;

import chariot.model.Trophy;

import java.util.HashMap;

/**
 * UserTrophy class to store user trophies for Lichess user
 */
public class UserTrophy {

    private final String trophyName;

    public UserTrophy(Trophy trophy) {
        this.trophyName = trophy.name();
    }

    /**
     * prints the trophy alongside the trophy emoji
     *
     * @return the trophy name with the trophy emoji
     */
    public String getImageLink() {
        HashMap<String, String> getLink = new HashMap<>();
        getLink.put("Marathon Winner", "\uD83D\uDD2E");
        getLink.put("Marathon Top 10", "\uD83C\uDF15");
        getLink.put("Other", "\uD83C\uDFC6");
        getLink.put("Verified account", "✅");
        getLink.put("Lichess moderator", "\uD83D\uDC41️");
        getLink.put("Lichess content team", "✍️");
        getLink.put("Lichess developer", "\uD83D\uDEE0️");

        String imageLink = getLink.getOrDefault(this.trophyName, getLink.get("Other"));
        return imageLink + " " + this.trophyName;
    }
}