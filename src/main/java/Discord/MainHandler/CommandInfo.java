package Discord.MainHandler;

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
        this.embedBuilder.setDescription("**" + Emojis[0] + " /help** \n to see command information for the LISEBOT" + "\n\n**" + Emojis[0] + "  /suggest** \n provide feedback to developer"  + "\n\n **"  + Emojis[2] + " /profilecc** \n view Chess.com profiles" +"\n\n **" + Emojis[2] +" /profile ** \n to see lichess profiles for given username"  + "\n\n **" + Emojis[3] +" /streamers** \n Watch current live streamers");
        this.embedBuilder.setFooter("LISEBOT V10, Date: Dec 20 2023 Page: 1/3", this.logo);


        return this.embedBuilder;
    }
    public EmbedBuilder getPageFour(){
        this.embedBuilder.setColor(Color.blue);
        this.embedBuilder.setTitle("**Commands For LISEBOT**");
        this.embedBuilder.setThumbnail(this.logo);
        this.embedBuilder.setDescription(
            "Chess is a two-player strategy board game played on an 8x8 grid. Here are the basics of each chess piece:\n\n" +
            "**1. King:** The king moves exactly one square horizontally, vertically, or diagonally.\n" +
            "![King](https://images.chesscomfiles.com/uploads/v1/images_users/tiny_mce/pdrpnht/phpmVRKYr.png)\n\n" +
        
            "**2. Queen:** The queen can move any number of squares along a rank, file, or diagonal.\n" +
            "![Queen](https://images.chesscomfiles.com/uploads/v1/images_users/tiny_mce/pdrpnht/phpmVRKYr.png)\n\n" +
        
            "**3. Rook:** The rook can move any number of squares horizontally or vertically.\n" +
            "![Rook](https://images.chesscomfiles.com/uploads/v1/images_users/tiny_mce/pdrpnht/phpfyINI1.png)\n\n" +
        
            "**4. Bishop:** The bishop can move any number of squares diagonally.\n" +
            "![Bishop](https://images.chesscomfiles.com/uploads/v1/images_users/tiny_mce/PeterDoggers/php4dzIxh.png)\n\n" +
        
            "**5. Knight:** The knight moves to any of the squares immediately adjacent in an 'L' shape.\n" +
            "![Knight](https://images.chesscomfiles.com/uploads/v1/images_users/tiny_mce/pdrpnht/phpVZb3tN.png)\n\n" +
        
            "**6. Pawn:** Pawns move forward but capture diagonally. On their first move, pawns have the option of moving two squares.\n\n" +
            "The objective of the game is to checkmate your opponent\'s king.\n" +
            "![Pawn](https://images.chesscomfiles.com/uploads/v1/images_users/tiny_mce/pdrpnht/phpEH1kWv.png)"
        );
        


        return this.embedBuilder;
    }


    public EmbedBuilder getPageTwo(){
        this.embedBuilder.setColor(Color.blue);
        this.embedBuilder.setTitle("**Puzzles And Tournaments**");
        this.embedBuilder.setThumbnail(this.logo);
        this.embedBuilder.setDescription("\n\n **" + Emojis[1] + " /puzzle** \n Do Random Chess Puzzles/Daily Puzzles"  + "\n\n**" +  Emojis[5] +" /arena <Lichess arena URL>** \n see the standings and tournament information for given tournament link" + "\n\n **" +  Emojis[1] +" /analyze**\n Analyze a position with Stockfish");
        this.embedBuilder.setFooter("LISEBOT V10, Date: Dec 20 2023 Page: 2/3", this.logo);
        return this.embedBuilder;
    }



    public EmbedBuilder getPageThree(){
        this.embedBuilder.setColor(Color.blue);
        this.embedBuilder.setTitle("**Watch And Play Chess**");
        this.embedBuilder.setThumbnail(this.logo);
        this.embedBuilder.setDescription(  "\n\n **" +  Emojis[0] +" /invite** \n invite LiSEBot to your servers" + "\n\n **" +Emojis[6]+" /play ** \n Play chess with friends/BOTS on Lichess.org"  + "\n\n **"+ Emojis[6] + " /community ** \n view chess community"+ "\n\n **" +"\uD83D\uDCFA"+" /watch** \n watch latest Lichess game of the given user in gif!" + "\n\n **" +"\uD83D\uDCFA"+ " /watchmaster** \n watch random master games" + "\n\n **" +Emojis[5]+" /broadcast** \n view current ongoing master OTB/Online tournament"
        + "\n\n **" + Emojis[6] + "/move ** \n play chess with Stockfish chess engine for white side [use /resetboard to start/end game]" + "\n\n **" + Emojis[6] + "/moveblack ** \n play chess with Lise chess engine for black side [use /resetboard to start/end game]**" );
        this.embedBuilder.setFooter("LISEBOT V10, Date: Dec 20 2023 Page: 3/3", this.logo);
        return this.embedBuilder;
    }





}
