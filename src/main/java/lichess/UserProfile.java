package lichess;

import abstraction.Player.UserObject;
import abstraction.Player.UserTrophy;
import chariot.Client;
import chariot.model.Enums;
import chariot.model.One;
import chariot.model.PerformanceStatistics;
import chariot.model.User;

import java.util.List;

public class UserProfile extends UserObject {

    public UserProfile(Client client, String userParsing) {
        super(client, userParsing);
    }

    private String getRating(Enums.PerfType perfType, String emoji, String typeName) {
        One<PerformanceStatistics> performance = this.getClient().users().performanceStatisticsByIdAndType(this.getUserID(), perfType);

        if (performance.isPresent() && !performance.get().perf().glicko().provisional()) {
            return emoji + " **" + typeName + "**: " + performance.get().perf().glicko().rating().intValue();
        }
        return emoji + " **" + typeName + "**: ?";
    }

    public String getBlitzRatings() {
        return getRating(Enums.PerfType.blitz, "🔥", "Blitz");
    }

    public String getRapidRatings() {
        return getRating(Enums.PerfType.rapid, "🐇", "Rapid");
    }

    public String getBulletRatings() {
        return getRating(Enums.PerfType.bullet, "🔫", "Bullet");
    }

    public String getClassicalRatings() {
        return getRating(Enums.PerfType.classical, "🐢", "Classical");
    }

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
                trophiesBuilder.append(userTrophy.getImageLink()).append("\n");
            });

            String trophies = trophiesBuilder.length() > 0
                    ? "\n\n🎖️ **Trophies:**\n" + trophiesBuilder
                    : "";

            // Formatted Profile
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
