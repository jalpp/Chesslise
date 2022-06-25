import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;

public class CommandInfo {

    private EmbedBuilder embedBuilder;

    public CommandInfo(){
        this.embedBuilder = new EmbedBuilder();
    }


    public EmbedBuilder getCommandInfo(){


        this.embedBuilder.setColor(Color.orange);
        this.embedBuilder.setTitle("Commands for LiSEBot");
        this.embedBuilder.setThumbnail("https://images.prismic.io/lichess/25a60c33-96ad-4fd1-b9b2-dca8e289961f_lichesslogo.png?auto=compress,format");
        this.embedBuilder.setDescription("**/help** \n to see command information for the LISEBOT" + "\n\n **/profile <Lichess username>** \n to see lichess profiles for given username" + "\n\n **/top10 <Lichess variant>** \n see the top 10 players list in the variant provided, includes blitz, rapid, classical, bullet, ultrabullet" + "\n\n **/streamers** \n Watch current live streamers "  + "\n\n**/dailypuzzle** \n see the daily Lichess puzzle and try to solve it!" + "\n\n **/arena <Lichess arena URL>** \n see the standings and tournament information for given tournament link" + "\n\n **/invite** \n invite LiSEBot to your servers" + "\n\n **/play <variant> <rated/casual>** \n created opnended challenge for users to play with, given variant and rated/casual <variant = blitz, rapid, etc>" + "\n\n **/watch <Lichess Username>** \n watch latest Lichess game of the given user in gif!"  + "\n\n **Lichess Auth Commands** \n\n **/challengeauth <Lichess Personal Token> <Opponent username>** \n challenge anyone directly from Discord" + "\n\n **/chatauth <Lichess Personal Token> <message Receiver username> <message content>** \n Message a Lichess user from discord!" +  "\n\n **/scheduletournament <Lichess Personal Token> <Tournament variant>** \n Schedule a pre-set variant tournament right from Discord!" + "\n\n **/tourneymanager <Lichess Personal Token>** \n Manage and create your tournaments!");
        return this.embedBuilder;
    }



}

