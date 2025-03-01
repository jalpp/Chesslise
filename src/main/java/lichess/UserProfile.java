package lichess;

import abstraction.Player.UserObject;
import abstraction.Player.UserTrophy;
import chariot.Client;
import chariot.model.Enums;
import chariot.model.One;
import chariot.model.PerformanceStatistics;
import chariot.model.User;

import java.util.List;

/**
 * UserProfile class to handle the user profile for Lichess
 */
public class UserProfile extends UserObject {

    public UserProfile(Client client, String userParsing) {
        super(client, userParsing);
    }

    /**
     * Get the rating for the user
     *
     * @param perfType the performance type
     * @param emoji    the emoji
     * @param typeName the type name
     * @return the rating
     */
    private String getRating(Enums.PerfType perfType, String emoji, String typeName) {
        One<PerformanceStatistics> performance = this.getClient().users().performanceStatisticsByIdAndType(this.getUserID(), perfType);

        if (performance.isPresent() && !performance.get().perf().glicko().provisional()) {
            return emoji + " **" + typeName + "**: " + performance.get().perf().glicko().rating().intValue();
        }
        return emoji + " **" + typeName + "**: ?";
    }

    /**
     * Get the blitz ratings
     *
     * @return the blitz ratings
     */
    public String getBlitzRatings() {
        return getRating(Enums.PerfType.blitz, "🔥", "Blitz");
    }

    /**
     * Get the rapid ratings
     *
     * @return the rapid ratings
     */
    public String getRapidRatings() {
        return getRating(Enums.PerfType.rapid, "🐇", "Rapid");
    }

    /**
     * Get the bullet ratings
     *
     * @return the bullet ratings
     */
    public String getBulletRatings() {
        return getRating(Enums.PerfType.bullet, "🔫", "Bullet");
    }

    /**
     * Get the classical ratings
     *
     * @return the classical ratings
     */
    public String getClassicalRatings() {
        return getRating(Enums.PerfType.classical, "🐢", "Classical");
    }

    /**
     * Get the user profile
     *
     * @return the user profile
     */
    public String getUserProfile() {
        try {
            One<User> userResult = this.getClient().users().byId(this.getUserID(), params -> params.withTrophies(true));
            if (!userResult.isPresent()) {
                return "User Not Present, Please try again.";
            }

            User user = userResult.get();

            // Check account status
            if (user.tosViolation()) {
                return "🚨 This user has violated Lichess Terms of Service.";
            }
            if (user.disabled()) {
                return "🚫 This account is closed.";
            }

            // User Info
            String statusEmoji = this.getClient().users().statusByIds(this.getUserID())
                    .stream()
                    .findFirst()
                    .map(status -> status.online() ? "🟢" : "🔴")
                    .orElse("🔴");

            String title = user.title().orElse("");
            String username = user.id();
            int wins = user.accountStats().win();
            int losses = user.accountStats().loss();
            int draws = user.accountStats().draw();
            int gamesPlayed = user.accountStats().all();
            int currentlyPlaying = user.accountStats().playing();

            // Trophies
            StringBuilder trophiesBuilder = new StringBuilder();
            user.trophies().orElse(List.of()).forEach(trophy -> {
                UserTrophy userTrophy = new UserTrophy(trophy);
                trophiesBuilder.append(userTrophy.getTrophyName()).append("\n");
            });

            String trophies = trophiesBuilder.length() > 0
                    ? "\n\n🎖️ **Trophies:**\n" + trophiesBuilder
                    : "";


            return String.format(
                    "%s %s %s\n" +
                            "**All Games:** %d\n" +
                            "**⚔️ Won:** %d **😞 Loss:** %d **🤝 Draw:** %d\n" +
                            "**♗ Playing:** %d\n\n" +
                            "💰 **Ratings:**\n" +
                            "%s\n" +
                            "%s\n" +
                            "%s\n" +
                            "%s%s",
                    title, username, statusEmoji,
                    gamesPlayed, wins, losses, draws, currentlyPlaying,
                    getBlitzRatings(),
                    getRapidRatings(),
                    getBulletRatings(),
                    getClassicalRatings(),
                    trophies
            );

        } catch (Exception e) {
            e.printStackTrace();
            return "❌ Unknown Error occurred. Please try again.";
        }
    }
}
