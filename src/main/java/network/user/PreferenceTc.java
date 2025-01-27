package network.user;

import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

/**
 * PreferenceTc enum to handle the time control preferences
 */
public enum PreferenceTc {

    CLASSICAL,

    RAPID,

    BLITZ,

    BULLET;

    /**
     * Build the time control preference
     *
     * @param tag the tag for the time control
     * @return the time control preference to MongoDb document field
     */
    public String toMongo() {
        switch (this) {
            case CLASSICAL -> {
                return "classical";
            }

            case RAPID -> {
                return "rapid";
            }

            case BLITZ -> {
                return "blitz";
            }

            case BULLET -> {
                return "bullet";
            }

        }
        return null;
    }

    /**
     * Get the time control preference
     *
     * @return the time control preference Discord OptionData
     */
    public static OptionData getOptionData() {
        return new OptionData(OptionType.STRING, "tc", "choose timecontrol", true).addChoice("classical", "classical").addChoice("rapid", "rapid")
                .addChoice("blitz", "blitz").addChoice("bullet", "bullet");
    }


}
