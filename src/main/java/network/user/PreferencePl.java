package network.user;


import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

public enum PreferencePl {

    LICHESS,

    UNAUTH_LICHESS,

    ALL;


    public String toMongo(){
        switch (this){
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

    public static OptionData getOptionData(){
        return new OptionData(OptionType.STRING, "platform", "choose platform", true).addChoice( "Lichess.org Authenticated", "lichess").addChoice( "Lichess.org Anon", "unauthlichess").addChoice( "All Platforms", "allplatform");
    }





}
