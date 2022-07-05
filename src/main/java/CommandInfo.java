import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;

public class CommandInfo {

    private EmbedBuilder embedBuilder;

    public CommandInfo(){
        this.embedBuilder = new EmbedBuilder();
    }

    public EmbedBuilder getPageOne(){
        this.embedBuilder.setColor(Color.blue);
        this.embedBuilder.setTitle("**Commands For LISEBOT**");
        this.embedBuilder.setThumbnail("https://images.prismic.io/lichess/25a60c33-96ad-4fd1-b9b2-dca8e289961f_lichesslogo.png?auto=compress,format");
        this.embedBuilder.setDescription("**/help** \n to see command information for the LISEBOT" + "\n\n **/profile <Lichess username>** \n to see lichess profiles for given username" + "\n\n **/top10 <Lichess variant>** \n see the top 10 players list in the variant provided, includes blitz, rapid, classical, bullet, ultrabullet" + "\n\n **/streamers** \n Watch current live streamers");
        this.embedBuilder.setFooter("Page 1/4");

        return this.embedBuilder;
    }

    public EmbedBuilder getPageTwo(){
        this.embedBuilder.setColor(Color.blue);
        this.embedBuilder.setTitle("**Puzzles And Tournaments**");
        this.embedBuilder.setThumbnail("https://images.prismic.io/lichess/25a60c33-96ad-4fd1-b9b2-dca8e289961f_lichesslogo.png?auto=compress,format");
        this.embedBuilder.setDescription("\n\n**/dailypuzzle** \n see the daily Lichess puzzle and try to solve it!" + "\n\n **/puzzle** \n Do Random Chess Puzzles"  + "\n\n **/liga <Lichess Team>** \n View Liga Leaderboard " + "\n\n**/arena <Lichess arena URL>** \n see the standings and tournament information for given tournament link" + "\n\n **/tourney**\n View Current Lichess Tournaments");
        this.embedBuilder.setFooter("Page 2/4");
        return this.embedBuilder;
    }

    public EmbedBuilder getPageThree(){
        this.embedBuilder.setColor(Color.blue);
        this.embedBuilder.setTitle("**Watch And Play Chess**");
        this.embedBuilder.setThumbnail("https://images.prismic.io/lichess/25a60c33-96ad-4fd1-b9b2-dca8e289961f_lichesslogo.png?auto=compress,format");
        this.embedBuilder.setDescription( "\n\n **/tv** \n Watch Lichess TV!" +"\n\n **/invite** \n invite LiSEBot to your servers" + "\n\n **/play <variant> <rated/casual>** \n created opnended challenge for users to play with, given variant and rated/casual <variant = blitz, rapid, etc>" + "\n\n **/watch <Lichess Username>** \n watch latest Lichess game of the given user in gif!" + "\n\n **/watchmaster** \n watch random master games");
        this.embedBuilder.setFooter("3/4");
        return this.embedBuilder;
    }

    public EmbedBuilder getPageFour(){
        this.embedBuilder.setColor(Color.blue);
        this.embedBuilder.setTitle("** Access Lichess From Discord Via Auth Commands**");
        this.embedBuilder.setThumbnail("https://images.prismic.io/lichess/25a60c33-96ad-4fd1-b9b2-dca8e289961f_lichesslogo.png?auto=compress,format");
        this.embedBuilder.setDescription(" \n\n **/challengeauth ** \n challenge anyone directly from Discord" + "\n\n **/chatauth ** \n Message a Lichess user from discord!" +  "\n\n **/scheduletournament ** \n Schedule a pre-set variant tournament right from Discord!" + "\n\n **/tourneymanager ** \n Manage and create your tournaments! \n\n **/puzzleracer** \n play puzzle racer! \n\n **Note** All Lichess tokens get deleted after use.");
        this.embedBuilder.setFooter("4/4");
        return this.embedBuilder;
    }



}
