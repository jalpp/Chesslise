package network.user;

import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

public enum PreferenceTc {

    CLASSICAL,

    RAPID,

    BLITZ,

    BULLET; // adding this for bullet addicts

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

    public static OptionData getOptionData() {
        return new OptionData(OptionType.STRING, "tc", "choose timecontrol", true).addChoice("classical", "classical")
                .addChoice("rapid", "rapid")
                .addChoice("blitz", "blitz").addChoice("bullet", "bullet");
    }

}
