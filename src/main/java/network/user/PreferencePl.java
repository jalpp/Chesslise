package network.user;


import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

/**
 * PreferencePl enum to handle the platform preferences
 */
public enum PreferencePl {

    LICHESS,

    UNAUTH_LICHESS,

    ALL;

    /**
     * Build the platform preference
     *
     * @param tag the tag for the platform
     * @return the platform preference to MongoDb document field
     */
    public String toMongo() {
        switch (this) {
            case LICHESS -> {
                return "lichess";
            }

            case UNAUTH_LICHESS -> {
                return "unauthlichess";
            }

            case ALL -> {
                return "allplatform";
            }
        }

        return null;
    }

    /**
     * Get the platform preference
     *
     * @return the platform preference Discord OptionData
     */
    public static OptionData getOptionData() {
        return new OptionData(OptionType.STRING, "platform", "choose platform", true).addChoice("Lichess.org Authenticated", "lichess").addChoice("Lichess.org Anon", "unauthlichess").addChoice("All Platforms", "allplatform");
    }


}
