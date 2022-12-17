import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;

public class CommandInfo {

    private EmbedBuilder embedBuilder;
    private final String logo = "https://static-00.iconduck.com/assets.00/lichess-icon-512x512-q0oh5bwk.png";
    private final String[] Emojis = {"❓", "\uD83E\uDDE9", "\uD83D\uDC64", "\uD83C\uDFA4", "\uD83D\uDCDA", "\uD83C\uDFC6", "⚔️"};
    public CommandInfo(){
        this.embedBuilder = new EmbedBuilder();
    }

    public EmbedBuilder getPageOne(){
        this.embedBuilder.setColor(Color.blue);
        this.embedBuilder.setTitle("**Commands For LISEBOT**");
        this.embedBuilder.setThumbnail(this.logo);
        this.embedBuilder.setDescription("**" + Emojis[0] + " /help** \n to see command information for the LISEBOT" + "**" + Emojis[0] + " /suggest** \n provide feedback to developer"+"\n\n ** " + Emojis[1] +" /dailypuzzlecc** \n view Chess.com daily puzzles" + "\n\n **"  + Emojis[2] + " /profilecc** \n view Chess.com profiles" +"\n\n **" + Emojis[2] +" /profile ** \n to see lichess profiles for given username"  + "\n\n **" + Emojis[3] +" /streamers** \n Watch current live streamers" + "\n\n **" + Emojis[4] + " /openingdb** View chess openings and their master games!");
        this.embedBuilder.setFooter("Page 1/3", this.logo);


        return this.embedBuilder;
    }

    public EmbedBuilder getPageTwo(){
        this.embedBuilder.setColor(Color.blue);
        this.embedBuilder.setTitle("**Puzzles And Tournaments**");
        this.embedBuilder.setThumbnail(this.logo);
        //this.embedBuilder.setThumbnail("https://images.prismic.io/lichess/25a60c33-96ad-4fd1-b9b2-dca8e289961f_lichesslogo.png?auto=compress,format");
        this.embedBuilder.setDescription("\n\n**" + Emojis[1] +  " /dailypuzzle** \n see the daily Lichess puzzle and try to solve it!" + "\n\n **" + Emojis[1] + " /puzzle** \n Do Random Chess Puzzles"  + "\n\n**" +  Emojis[5] +" /arena <Lichess arena URL>** \n see the standings and tournament information for given tournament link" + "\n\n **" +  Emojis[5] +" /tourney**\n View Current Lichess Tournaments" + "\n\n **" + Emojis[5]+ " /blog** \n Read Lichess blogs");
        this.embedBuilder.setFooter("Page 2/3", this.logo);
        return this.embedBuilder;
    }

    public EmbedBuilder getPageThree(){
        this.embedBuilder.setColor(Color.blue);
        this.embedBuilder.setTitle("**Watch And Play Chess**");
        this.embedBuilder.setThumbnail(this.logo);
        //this.embedBuilder.setThumbnail("https://images.prismic.io/lichess/25a60c33-96ad-4fd1-b9b2-dca8e289961f_lichesslogo.png?auto=compress,format");
        this.embedBuilder.setDescription(  "\n\n **" +  Emojis[0] +" /invite** \n invite LiSEBot to your servers" + "\n\n **" +Emojis[6]+" /play ** \n Play chess with friends/BOTS on Lichess.org" + "\n\n **" + Emojis[6] + " /community ** \n view chess community"+ "\n\n **" +"\uD83D\uDCFA"+" /watch** \n watch latest Lichess game of the given user in gif!" + "\n\n **" +"\uD83D\uDCFA"+ " /watchmaster** \n watch random master games" +"\n\n **" + "\uD83D\uDCFA"+" /agadmator** \n Watch recent/popular chess videos \n\n** " +"\uD83D\uDCFA"+" /kingscrusher** \n Watch and learn chess from CM Kingscrusher! "+ "\n\n **" +Emojis[5]+" /broadcast** \n view current ongoing master OTB/Online tournament");
        this.embedBuilder.setFooter("Page 3/3", this.logo);
        return this.embedBuilder;
    }

//    public EmbedBuilder getPageFour(){
//        this.embedBuilder.setColor(Color.blue);
//        this.embedBuilder.setTitle("** Access Lichess From Discord Via Auth Commands**");
//        this.embedBuilder.setThumbnail(this.logo);
//        //this.embedBuilder.setThumbnail("https://images.prismic.io/lichess/25a60c33-96ad-4fd1-b9b2-dca8e289961f_lichesslogo.png?auto=compress,format");
//        this.embedBuilder.setDescription(" \n\n **/challengeauth ** \n challenge anyone directly from Discord"  +  "\n\n **/scheduletournament ** \n Schedule a pre-set variant tournament right from Discord!" + "\n\n **/tourneymanager ** \n Manage and create your tournaments! \n\n **/puzzleracer** \n play puzzle racer!");
//        this.embedBuilder.setFooter("Page 4/4", this.logo);
//        return this.embedBuilder;
//    }



}
