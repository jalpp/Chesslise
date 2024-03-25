package Discord.MainHandler;

import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;

public class CommandInfo {

    private EmbedBuilder embedBuilder;
    private final String logo = "https://cdn-icons-png.flaticon.com/512/3593/3593455.png";
    private final String[] Emojis = {"❓", "\uD83E\uDDE9", "\uD83D\uDC64", "\uD83C\uDFA4", "\uD83D\uDCDA", "\uD83C\uDFC6", "⚔️"};
    public CommandInfo(){
        this.embedBuilder = new EmbedBuilder();
    }



    public EmbedBuilder getPageOne(){
        this.embedBuilder.setColor(Color.blue);
        this.embedBuilder.setTitle("**Commands For LISEBOT**");
        this.embedBuilder.setThumbnail(this.logo);
        this.embedBuilder.setDescription("**" + Emojis[0] + " /help** \n to see command information for the LISEBOT" + "\n\n**" + Emojis[0] + "  /suggest** \n provide feedback to developer"  + "\n\n **"  + Emojis[2] + " /profilecc** \n view Chess.com profiles" +"\n\n **" + Emojis[2] +" /profile ** \n to see lichess profiles for given username"  + "\n\n **" + Emojis[3] +" /streamers** \n Watch current live streamers" + "\n\n [Join our Server ♟\uFE0F](https://discord.gg/uncmhknmYg)");


        return this.embedBuilder;
    }


    public EmbedBuilder getPageTwo(){
        this.embedBuilder.setColor(Color.blue);
        this.embedBuilder.setTitle("**Puzzles And Tournaments**");
        this.embedBuilder.setThumbnail(this.logo);
        this.embedBuilder.setDescription("\n\n **" + Emojis[1] + " /puzzle** \n Do Random Chess Puzzles/Daily Puzzles"  + "\n\n**" +  Emojis[5] +" /arena <Lichess arena URL>** \n see the standings and tournament information for given tournament link" + "\n\n **" +  Emojis[1] +" /analyze**\n Analyze a position with Stockfish, check puzzle answers" + "\n\n [Join our Server ♟\uFE0F](https://discord.gg/uncmhknmYg)");
        return this.embedBuilder;
    }



    public EmbedBuilder getPageThree(){
        this.embedBuilder.setColor(Color.blue);
        this.embedBuilder.setTitle("**Watch And Play Chess**");
        this.embedBuilder.setThumbnail(this.logo);
        this.embedBuilder.setDescription(  "\n\n **" +  Emojis[0] +" /invite** \n invite LiSEBot to your servers" + "\n\n **" +Emojis[6]+" /play ** \n Play chess with friends/BOTS on Lichess.org"  + "\n\n **"+ Emojis[6] + " /community ** \n view chess community"+ "\n\n **" +"\uD83D\uDCFA"+" /watch** \n watch latest Lichess game of the given user in gif!" + "\n\n **" +"\uD83D\uDCFA"+ " /watchmaster** \n watch random master games" + "\n\n **" +Emojis[5]+" /broadcast** \n view current ongoing master OTB/Online tournament"
        + "\n\n **" + Emojis[6] + "/move ** \n play chess with Stockfish chess engine for white side [use /resetboard to start/end game]" + "\n\n **" + Emojis[6] + "/moveblack ** \n play chess with Lise chess engine for black side [use /resetboard to start/end game]**"
                + "\n\n **" + Emojis[6] + "/learnchess ** \n Learn basic chess rules to get started with chess**" + "\n\n [Join our Server ♟\uFE0F](https://discord.gg/uncmhknmYg)");
        return this.embedBuilder;
    }


    public EmbedBuilder getPageFour(){
        this.embedBuilder.setColor(Color.blue);
        this.embedBuilder.setThumbnail(this.logo);
        this.embedBuilder.setDescription("Here are the basic moves of the chess pieces:\n\n");
        this.embedBuilder.appendDescription("**Rook:** Move any number of squares horizontally or vertically.\n\n" + "\n\n [Join our Server ♟\uFE0F](https://discord.gg/uncmhknmYg)");
        this.embedBuilder.setImage("https://images.chesscomfiles.com/uploads/v1/images_users/tiny_mce/pdrpnht/phpfyINI1.png");

        return this.embedBuilder;
    }

    public EmbedBuilder getPageFive(){
        this.embedBuilder.setColor(Color.blue);
        this.embedBuilder.setThumbnail(this.logo);
        this.embedBuilder.setDescription("Here are the basic moves of the Bishop:\n\n");
        this.embedBuilder.appendDescription("**Bishop:** Move any number of squares diagonally.\n\n" + "\n\n [Join our Server ♟\uFE0F](https://discord.gg/uncmhknmYg)");
        this.embedBuilder.setImage("https://images.chesscomfiles.com/uploads/v1/images_users/tiny_mce/PeterDoggers/phpdzgpdQ.png");
        this.embedBuilder.setFooter("Join our Server - https://discord.gg/uncmhknmYg ♟\uFE0F");
        return this.embedBuilder;
    }
    public EmbedBuilder getPageSix(){
        this.embedBuilder.setColor(Color.blue);
        this.embedBuilder.setThumbnail(this.logo);
        this.embedBuilder.setDescription("Here are the basic moves of the Knight:\n\n");
        this.embedBuilder.appendDescription("**Knight:** Move in an 'L' shape: two squares in one direction and then one square perpendicular.\n\n" + "\n\n [Join our Server ♟\uFE0F](https://discord.gg/uncmhknmYg)");
        this.embedBuilder.setImage("https://images.chesscomfiles.com/uploads/v1/images_users/tiny_mce/pdrpnht/phpVuLl4W.png");
        this.embedBuilder.setFooter("Join our Server - https://discord.gg/uncmhknmYg ♟\uFE0F");
        return this.embedBuilder;
    }
    public EmbedBuilder getPageSeven(){
        this.embedBuilder.setColor(Color.blue);
        this.embedBuilder.setThumbnail(this.logo);
        this.embedBuilder.setDescription("Here are the basic moves of the Queen:\n\n");
        this.embedBuilder.appendDescription("**Queen:** Move any number of squares horizontally, vertically, or diagonally.\n\n" + "\n\n [Join our Server ♟\uFE0F](https://discord.gg/uncmhknmYg)");
        this.embedBuilder.setImage("https://images.chesscomfiles.com/uploads/v1/images_users/tiny_mce/pdrpnht/phpCQgsYR.png");
        this.embedBuilder.setFooter("Join our Server - https://discord.gg/uncmhknmYg ♟\uFE0F");
        return this.embedBuilder;
    }





}
