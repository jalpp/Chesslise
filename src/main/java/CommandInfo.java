mport net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;

public class CommandInfo {

    private EmbedBuilder embedBuilder;

    public CommandInfo(){
        this.embedBuilder = new EmbedBuilder();
    }


    public EmbedBuilder getCommandInfo(){


        this.embedBuilder.setColor(Color.yellow);
        this.embedBuilder.setTitle("Commands for LiSEBot");
        this.embedBuilder.setDescription("**/help** \n to see command information for the LiSEBot" + "\n\n **/profile <Lichess username>** \n to see lichess profiles for given username" + "\n\n **/top10 <Lichess variant>** \n see the top 10 players list in the variant provided, includes blitz, rapid, classical, bullet, ultrabullet" + "\n\n **/livestreaming <Lichess username>** Check if your favorite streamer is streaming! \n\n **/streamers** Watch current live streamers "  + "\n\n**/dailypuzzle** see the daily Lichess puzzle and try to solve it!" +"\n\n /gamereview <Lichess Username> see given user's latest game's analysis, must analysis the game before!" + "\n\n **/arena <Lichess arena URL>** see the standings and tournament information for given tournament link" + "\n\n **/stormdash <Lichess username>** see the storm Dashboard of the given user" + "\n\n **/invite** invite LiSEBot to your servers" + "\n\n **/play <variant> <rated/casual> ** created opnended challenge for users to play with, given variant and rated/casual <variant = blitz, rapid, etc>" + "\n\n **/watch** <Lichess Username>** watch latest lichess game of the given user in gif!"  + "\n\n ** Lichess Auth Commands** \n\n **/challengeauth <Lichess Personal Token> <Opponent username>** challenge anyone directly from Discord \n\n **/chatauth <Lichess Personal Token> <message Receiver username> <message content>** Message a Lichess user from discord! \n\n **/scheduletournament <Lichess Personal Token> <Tournament variant (blitz, etc)>** Schedule a pre-set variant tournament right from Discord!");


        return this.embedBuilder;
    }



}

