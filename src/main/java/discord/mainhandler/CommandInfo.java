package discord.mainhandler;

import discord.helpermodules.ButtonHelperModule;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import java.awt.*;

/**
 * CommandInfo class to handle the command information
 */
public class CommandInfo {


    private final SlashCommandInteractionEvent event;
    private final EmbedBuilder embedBuilder;

    public CommandInfo(SlashCommandInteractionEvent event) {
        this.event = event;
        this.embedBuilder = new EmbedBuilder();
    }

    /**
     * Get the the Embed for /help command
     *
     * @return the embed builder for the /help command
     */
    public EmbedBuilder getPageOne() {
        this.embedBuilder.setColor(Color.blue);
        this.embedBuilder.setTitle("**Commands For Chesslise**");
        this.embedBuilder.setThumbnail(Thumbnail.getChessliseLogo());
        this.embedBuilder.setFooter("Chesslise will stay free forever, so thank you for installing the bot! Chesslise is made by @nmp123"
        );
        this.embedBuilder.setDescription(CommandBuilder.printCommand());
        this.embedBuilder.appendDescription("""
                [**View ChessLise TOS/Privacy Policy**](https://github.com/jalpp/Chesslise/blob/master/tos-privacy-policy.md)
                [**Feature Request? Join our support server**](https://discord.gg/ez3QVsNmuy)
                [**Source Code**](https://github.com/jalpp/Chesslise)
                [**Donate link**](https://buy.stripe.com/00g15p6ID7wOcykfZ6)
                
                **Version: 16**
                """);
        return this.embedBuilder;
    }

    /**
     * Send the info command to the user
     */
    public void sendInfoCommand() {
        event.replyEmbeds(getPageOne().build()).addActionRow(Button.success("cssnhelp", "❓ CSSN Network Help")).setEphemeral(true).queue();
    }

    /**
     * Send the learn command to the user
     */
    public void sendLearnCommand() {
        event.replyEmbeds(ButtonHelperModule.getLearnChessCard("Rook").build()).addActionRow(Button.primary("Bishop", "♝")).setEphemeral(true).queue();
    }


}