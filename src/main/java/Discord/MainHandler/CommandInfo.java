package Discord.MainHandler;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import java.awt.*;

public class CommandInfo {

    private final String logo = "https://github.com/jalpp/Chesslise/blob/master/src/main/java/Images/liseanime.png?raw=true";
    private final EmbedBuilder embedBuilder;

    public CommandInfo() {
        this.embedBuilder = new EmbedBuilder();
    }

    public EmbedBuilder getPageOne() {
        this.embedBuilder.setColor(Color.blue);
        this.embedBuilder.setTitle("**Commands For Chesslise**");
        this.embedBuilder.setThumbnail(this.logo);
        this.embedBuilder.setFooter("Chesslise will stay free forever, so thank you for installing the bot! Chesslise is made by @nmp, if you would like to donate to support\n" +
                "lise's development feel free to join support server and ping @nmp");
        this.embedBuilder.setDescription("""
                
                **/help** View command info
                
                **/learnchess** learn basic chess rules
                
                **/puzzle** solve Lichess.org/Chess.com live puzzles in Discord or post them to community
                
                **/solve** solve the chess puzzle live by entering chess notation (UCI or SAN)
                
                **/puzzlesolve** create puzzle challenge for unique chess fen
               
                **/play** play Live in with your friends on Lichess.org
                
                **/profile** view user Lichess profile from given username search
                
                **/profilecc** View user Chess.com profile from given username search
                
                **/watch** View a user's newest Lichess game in Live GIF format\s
                
                **/move** start a game against Stockfish Chess engine, and make a move by entering a move in chess notation (UCI or SAN
                
                **/resetboard** Reset the game against the Engine, so others can play the engine\s
                
                [**View ChessLise TOS/Privacy Policy**](https://github.com/jalpp/Chesslise/blob/master/tos-privacy-policy.md)
                [**Feature Request? Join our support server**](https://discord.gg/ez3QVsNmuy)
                  
                **Version: 14**
                """);
        return this.embedBuilder;
    }


    public EmbedBuilder getPageFour() {
        this.embedBuilder.setColor(Color.blue);
        this.embedBuilder.setThumbnail(this.logo);
        this.embedBuilder.setDescription("Here are the basic moves of the chess pieces:\n\n");
        this.embedBuilder.appendDescription("""
                **Rook:** Move any number of squares horizontally or vertically.


                 [Join our Server ♟️](https://discord.gg/uncmhknmYg)""");
        this.embedBuilder.setImage("https://images.chesscomfiles.com/uploads/v1/images_users/tiny_mce/pdrpnht/phpfyINI1.png");

        return this.embedBuilder;
    }

    public EmbedBuilder getPageFive() {
        this.embedBuilder.setColor(Color.blue);
        this.embedBuilder.setThumbnail(this.logo);
        this.embedBuilder.setDescription("Here are the basic moves of the Bishop:\n\n");
        this.embedBuilder.appendDescription("""
                **Bishop:** Move any number of squares diagonally.

                 [Join our Server ♟️](https://discord.gg/uncmhknmYg)""");
        this.embedBuilder.setImage("https://images.chesscomfiles.com/uploads/v1/images_users/tiny_mce/PeterDoggers/phpdzgpdQ.png");
        this.embedBuilder.setFooter("Join our Server - https://discord.gg/uncmhknmYg ♟️");
        return this.embedBuilder;
    }

    public EmbedBuilder getPageSix() {
        this.embedBuilder.setColor(Color.blue);
        this.embedBuilder.setThumbnail(this.logo);
        this.embedBuilder.setDescription("Here are the basic moves of the Knight:\n\n");
        this.embedBuilder.appendDescription("""
                **Knight:** Move in an 'L' shape: two squares in one direction and then one square perpendicular.

                 [Join our Server ♟️](https://discord.gg/uncmhknmYg)""");
        this.embedBuilder.setImage("https://images.chesscomfiles.com/uploads/v1/images_users/tiny_mce/pdrpnht/phpVuLl4W.png");
        this.embedBuilder.setFooter("Join our Server - https://discord.gg/uncmhknmYg ♟️");
        return this.embedBuilder;
    }

    public EmbedBuilder getPageSeven() {
        this.embedBuilder.setColor(Color.blue);
        this.embedBuilder.setThumbnail(this.logo);
        this.embedBuilder.setDescription("Here are the basic moves of the Queen:\n\n");
        this.embedBuilder.appendDescription("""
                **Queen:** Move any number of squares horizontally, vertically, or diagonally.

                 [Join our Server ♟️](https://discord.gg/uncmhknmYg)""");
        this.embedBuilder.setImage("https://images.chesscomfiles.com/uploads/v1/images_users/tiny_mce/pdrpnht/phpCQgsYR.png");
        this.embedBuilder.setFooter("Join our Server - https://discord.gg/uncmhknmYg ♟️");
        return this.embedBuilder;
    }

    public EmbedBuilder getPageEight() {
        this.embedBuilder.setColor(Color.blue);
        this.embedBuilder.setThumbnail(this.logo);
        this.embedBuilder.setDescription("Here are the basic moves of the King:\n\n");
        this.embedBuilder.appendDescription("""
                **King:** Move 1 square at a time horizontally, vertically, or diagonally.
                """);
        this.embedBuilder.setImage("https://images.chesscomfiles.com/uploads/v1/images_users/tiny_mce/pdrpnht/phpmVRKYr.png");
        this.embedBuilder.setFooter("Join our Server - https://discord.gg/uncmhknmYg ♟️");
        return this.embedBuilder;
    }

    public EmbedBuilder getPageNine() {
        this.embedBuilder.setColor(Color.blue);
        this.embedBuilder.setThumbnail(this.logo);
        this.embedBuilder.setDescription("King Special move: castling, when king and rook gets space between his king and the rooks and no piece are present he can castle that way:\n\n");
        this.embedBuilder.setImage("https://www.chessbazaar.com/blog/wp-content/uploads/2014/11/castling.gif");
        this.embedBuilder.setFooter("Join our Server - https://discord.gg/uncmhknmYg ♟️");
        return this.embedBuilder;
    }

    public EmbedBuilder getPageTen() {
        this.embedBuilder.setColor(Color.blue);
        this.embedBuilder.setThumbnail(this.logo);
        this.embedBuilder.setDescription("Pawn Move: can only go up the board and capture on the flank side:\n\n");
        this.embedBuilder.setImage("https://images.chesscomfiles.com/uploads/v1/images_users/tiny_mce/pdrpnht/php8nbVYg.gif");
        this.embedBuilder.setFooter("Join our Server - https://discord.gg/uncmhknmYg ♟️");
        return this.embedBuilder;
    }

    public EmbedBuilder getPageEleven() {
        this.embedBuilder.setColor(Color.blue);
        this.embedBuilder.setThumbnail(this.logo);
        this.embedBuilder.setDescription("Pawn Special Move: en-passant capture when opposing side moves side pawn beside your pawn :\n\n");
        this.embedBuilder.setImage("https://images.chesscomfiles.com/uploads/v1/images_users/tiny_mce/pdrpnht/phpZmdTyW.gif");
        this.embedBuilder.setFooter("Join our Server - https://discord.gg/uncmhknmYg ♟️");
        return this.embedBuilder;
    }

    public EmbedBuilder getPage12() {
        this.embedBuilder.setColor(Color.blue);
        this.embedBuilder.setThumbnail(this.logo);
        this.embedBuilder.setDescription("Pawn Special Move: Promotion, when your pawn reaches the 8th rank you can promote to queen,rook,bishop, or a night :\n\n");
        this.embedBuilder.setImage("https://images.chesscomfiles.com/uploads/v1/images_users/tiny_mce/PedroPinhata/phpFSZHst.gif");
        this.embedBuilder.setFooter("Join our Server - https://discord.gg/uncmhknmYg ♟️");
        return this.embedBuilder;
    }



    public void sendInfoCommand(SlashCommandInteractionEvent slashEvent) {
        slashEvent.replyEmbeds(getPageOne().build()).addActionRow(Button.link("https://discord.gg/uncmhknmYg", "Join our server")).setEphemeral(true).queue();
    }

    public void sendLearnCommand(SlashCommandInteractionEvent slashEvent) {
        slashEvent.replyEmbeds(getPageFour().build()).addActionRow(Button.primary("Bishop", "♝"), Button.link("https://discord.gg/uncmhknmYg", "Join our server")).setEphemeral(true).queue();
    }


}
