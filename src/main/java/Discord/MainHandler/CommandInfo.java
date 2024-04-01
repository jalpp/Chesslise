
package Discord.MainHandler;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

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


    public void sendCommunityCommand(SlashCommandInteractionEvent slashEvent){
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setThumbnail("https://static-00.iconduck.com/assets.00/lichess-icon-512x512-q0oh5bwk.png");
        embedBuilder.setTitle("Best Chess Community To Learn/Play Chess");
        embedBuilder.setColor(Color.blue);
        embedBuilder.setDescription("**lichess.org**  [**Join**](https://discord.gg/lichess)" + "\n\n **Chess.com** [**Join**](https://discord.gg/chesscom)" +
                "\n\n **The Pawn Zone**  [**Join**](https://discord.gg/6aKNP3t) \n\n **The Moon Club** [**Join**](https://discord.gg/hK8Ru57SKd)" + "\n\n [Join our Server ♟\uFE0F](https://discord.gg/uncmhknmYg)");
        slashEvent.replyEmbeds(embedBuilder.build()).queue();
    }


    public void sendServiceCommand(SlashCommandInteractionEvent slashEvent){
        EmbedBuilder embedBuildertos = new EmbedBuilder();
        embedBuildertos.setColor(Color.blue);
        embedBuildertos.setTitle("Terms Of Service And Privacy Policy");
        embedBuildertos.setThumbnail("https://static-00.iconduck.com/assets.00/lichess-icon-512x512-q0oh5bwk.png");
        embedBuildertos.setDescription("What is Chesslise Terms Of Service?\n" +
                "\n" +
                "User agrees that they will have to use latest updated versions of Chesslise, User also agrees that some commands may be deleted if developer does not want to maintain those commands in future. User is fully responsible for their discord server and Chesslise does not have any access to the server information/ management. User also agrees to privacy policy which states that Chesslise does not and will not store any private information \n\n What information does Chesslise store about me? What is the privacy policy?\n" +
                "\n" +
                "Chesslise Does not and will not store any private user information." + "\n\n [Join our Server ♟\uFE0F](https://discord.gg/uncmhknmYg)");
        slashEvent.replyEmbeds(embedBuildertos.build()).queue();
    }


    public void sendInfoCommand(SlashCommandInteractionEvent slashEvent){
        slashEvent.replyEmbeds(getPageOne().build()).addActionRow(net.dv8tion.jda.api.interactions.components.buttons.Button.primary("next", "➡️"), Button.link("https://discord.gg/uncmhknmYg", "Join our server")).setEphemeral(true).queue();
    }

    public void sendLearnCommand(SlashCommandInteractionEvent slashEvent){
        slashEvent.replyEmbeds(getPageFour().build()).addActionRow(Button.primary("Bishop", "♝"), Button.link("https://discord.gg/uncmhknmYg", "Join our server")).setEphemeral(true).queue();
    }

    public void sendInviteMeCommand(SlashCommandInteractionEvent slashEvent){
        slashEvent.replyEmbeds(new EmbedBuilder().setTitle("Invite me").setDescription("\uD83D\uDC4B [Click here for invite me](https://discord.com/api/oauth2/authorize?client_id=930544707300393021&permissions=277025704000&scope=bot%20applications.commands) \n\n \uD83D\uDC4D [Vote me on top.gg](https://top.gg/bot/930544707300393021/vote) \n\n \uD83D\uDEE0️ \n  [Join our Server](https://discord.gg/uncmhknmYg)").build()).queue();
    }





}