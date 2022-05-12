import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;

public class CommandInfo {

    private EmbedBuilder embedBuilder;

    public CommandInfo(){
        this.embedBuilder = new EmbedBuilder();
    }


    public EmbedBuilder getCommandInfo(){


        this.embedBuilder.setColor(Color.white);
        this.embedBuilder.setTitle("Commands for LiSEBot");
        this.embedBuilder.setDescription("**,help** \n to see command information for the LiSEBot" + "\n\n **,profile <Lichess username>** \n to see lichess profiles for given username" + "\n\n **,top10 <Lichess variant>** \n see the top 10 players list in the variant provided, includes blitz, rapid, classical, bullet, ultrabullet, horde, racingkings, koh, atomic, chess960" + "\n\n **,streaming? <Lichess username>** Check if your favorite streamer is streaming!" + "\n\n**,team <Team name>** See team information based on team named provided. **Note: if the team has spaces include - instead of space, so the house discord server will be the-house-discord-server**" + "\n\n**,daily** see the daily Lichess puzzle and try to solve it!" + "\n\n **,arena <Lichess arena URL>** see the standings and tournament information for given tournament link" + "\n\n **,stormdash <Lichess username>** see the storm Dashboard of the given user" + "\n\n **,invite** invite LiSEBot to your servers" + "\n\n **,play <variant> <rated/casual> ** created opnended challenge for users to play with, given variant and rated/casual <variant = blitz, rapid, classical, bullet, ultrabullet>");


        return this.embedBuilder;
    }



}
