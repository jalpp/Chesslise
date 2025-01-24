package discord.mainhandler;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import java.awt.*;

public class CommandInfo {

    private final String logo = "https://raw.githubusercontent.com/jalpp/DojoIcons/dd7365ea7d768fe17056d9b14ee6740c2bf4e261/oldIcons/Black%20Blue%20White%20Tactical%20eSports%20Discord%20Logo.png";
    private final EmbedBuilder embedBuilder;

    public CommandInfo() {
        this.embedBuilder = new EmbedBuilder();
    }

    public EmbedBuilder getPageOne() {
        this.embedBuilder.setColor(Color.blue);
        this.embedBuilder.setTitle("**Commands For Chesslise**");
        this.embedBuilder.setThumbnail(this.logo);
        this.embedBuilder.setFooter("Chesslise will stay free forever, so thank you for installing the bot! Chesslise is made by @nmp , if you like to support us, feel free to donate as little as $2 to cover lise server costs\n"
        );
        this.embedBuilder.setDescription("""
                
                **/help** View command info
                
                **/learnchess** learn basic chess rules
                
                **/puzzle** solve Lichess.org/Chess.com live puzzles in Discord or post them to community
                
                **/play** play Live in with your friends on Lichess.org
                
                **/profile** view user Lichess profile from given username search
                
                **/profilecc** View user Chess.com profile from given username search
                
                **/watch** View a user's newest Lichess game in Live GIF format\s
                
                **/chessdb** Analysis a given fen from chessdb cn's cloud eval\s
                
                **/move** start a game against Stockfish Chess engine, and make a move by entering a move in chess notation (UCI or SAN
                
                **/resetboard** Reset the game against the Engine, so others can play the engine\s
                
                  **/connect**
                connect into Chesslise Social Network Server, or connect back if you went offline
                
                **/disconnect**
                go offline and not receive auto friend requests and challenges
                
                **/setpreference**
                change friend finding preferences and challenge finding preferences
                
                **/mychallenges**
                view your challenges in the network
                
                **/pairchallenge**
                attempt to find a challenge in global pool
                
                **/pairchallengenetwork**
                attempt to send a challenge in your friend network
                
                **/seekchallenge**
                create a challenge and seek for others to accept it
                
                **/cancelchallenge**
                cancel a challenge by challenge ID
                
                **/completechallenge**
                complete a challenge by challenge ID
                
                **/findfriend**
                find a new friend within your network or globally
                
                **/sendfriendrequest**
                send friend request by providing target username
                
                **/acceptfriendrequest**
                accept friend request by providing target friend discord id
                
                **/cancelfriendrequest**
                cancel an incomming friend request by providing discord id
                
                **/removefriend**
                remove a friend from friend list by providing discord username
                
                **/blockfriend**
                block a friend who has not being friendly by providing discord username
                
                **/viewfriends**
                view various friend requests and friend list
                
                **/networkhelp**
                view Chesslise network commands
                
                
                [**View ChessLise TOS/Privacy Policy**](https://github.com/jalpp/Chesslise/blob/master/tos-privacy-policy.md)
                [**Feature Request? Join our support server**](https://discord.gg/ez3QVsNmuy)
                [**Source Code**](https://github.com/jalpp/Chesslise)
                [**Donate link**](https://buy.stripe.com/00g15p6ID7wOcykfZ6)
                  
                **Version: 16**
                """);
        return this.embedBuilder;
    }


    public EmbedBuilder getPageFour() {
        this.embedBuilder.setColor(Color.blue);
        this.embedBuilder.setThumbnail(this.logo);
        this.embedBuilder.setDescription("Here are the basic moves of the chess pieces:\n\n");
        this.embedBuilder.appendDescription("""
                **Rook:** Move any number of squares horizontally or vertically.


                 """);
        this.embedBuilder.setImage("https://images.chesscomfiles.com/uploads/v1/images_users/tiny_mce/pdrpnht/phpfyINI1.png");

        return this.embedBuilder;
    }

    public EmbedBuilder getPageFive() {
        this.embedBuilder.setColor(Color.blue);
        this.embedBuilder.setThumbnail(this.logo);
        this.embedBuilder.setDescription("Here are the basic moves of the Bishop:\n\n");
        this.embedBuilder.appendDescription("""
                **Bishop:** Move any number of squares diagonally.

                 """);
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

                 """);
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

                 """);
        this.embedBuilder.setImage("https://images.chesscomfiles.com/uploads/v1/images_users/tiny_mce/pdrpnht/phpCQgsYR.png");

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
        return this.embedBuilder;
    }

    public EmbedBuilder getPageNine() {
        this.embedBuilder.setColor(Color.blue);
        this.embedBuilder.setThumbnail(this.logo);
        this.embedBuilder.setDescription("King Special move: castling, when king and rook gets space between his king and the rooks and no piece are present he can castle that way:\n\n");
        this.embedBuilder.setImage("https://www.chessbazaar.com/blog/wp-content/uploads/2014/11/castling.gif");
        return this.embedBuilder;
    }

    public EmbedBuilder getPageTen() {
        this.embedBuilder.setColor(Color.blue);
        this.embedBuilder.setThumbnail(this.logo);
        this.embedBuilder.setDescription("Pawn Move: can only go up the board and capture on the flank side:\n\n");
        this.embedBuilder.setImage("https://images.chesscomfiles.com/uploads/v1/images_users/tiny_mce/pdrpnht/php8nbVYg.gif");
        return this.embedBuilder;
    }

    public EmbedBuilder getPageEleven() {
        this.embedBuilder.setColor(Color.blue);
        this.embedBuilder.setThumbnail(this.logo);
        this.embedBuilder.setDescription("Pawn Special Move: en-passant capture when opposing side moves side pawn beside your pawn :\n\n");
        this.embedBuilder.setImage("https://images.chesscomfiles.com/uploads/v1/images_users/tiny_mce/pdrpnht/phpZmdTyW.gif");
        return this.embedBuilder;
    }

    public EmbedBuilder getPage12() {
        this.embedBuilder.setColor(Color.blue);
        this.embedBuilder.setThumbnail(this.logo);
        this.embedBuilder.setDescription("Pawn Special Move: Promotion, when your pawn reaches the 8th rank you can promote to queen,rook,bishop, or a night :\n\n");
        this.embedBuilder.setImage("https://images.chesscomfiles.com/uploads/v1/images_users/tiny_mce/PedroPinhata/phpFSZHst.gif");
        return this.embedBuilder;
    }


    public void sendInfoCommand(SlashCommandInteractionEvent slashEvent) {
        slashEvent.replyEmbeds(getPageOne().build()).setEphemeral(true).queue();
    }

    public void sendLearnCommand(SlashCommandInteractionEvent slashEvent) {
        slashEvent.replyEmbeds(getPageFour().build()).addActionRow(Button.primary("Bishop", "♝")).setEphemeral(true).queue();
    }


}
