package discord.mainhandler;

import discord.helpermodules.ButtonHelperModule;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import abstraction.CommandTrigger;
import java.awt.*;

public class CommandInfo implements CommandTrigger {

    private final SlashCommandInteractionEvent event;
    private final EmbedBuilder embedBuilder;

    public CommandInfo(SlashCommandInteractionEvent event) {
        this.event = event;
        this.embedBuilder = new EmbedBuilder();
    }

    public EmbedBuilder getPageOne() {
        this.embedBuilder.setColor(Color.blue);
        this.embedBuilder.setTitle("**Commands For Chesslise**");
        this.embedBuilder.setThumbnail(Thumbnail.getChessliseLogo());
        this.embedBuilder.setFooter(
                "Chesslise will stay free forever, so thank you for installing the bot! Chesslise is made by @nmp123");
        this.embedBuilder.setDescription(CommandBuilder.printCommand());
        this.embedBuilder.appendDescription(
                """
                        [**View ChessLise TOS/Privacy Policy**](https://github.com/jalpp/Chesslise/blob/master/tos-privacy-policy.md)
                        [**Feature Request? Join our support server**](https://discord.gg/ez3QVsNmuy)
                        [**Source Code**](https://github.com/jalpp/Chesslise)


                        **Version: 17**
                        """);
        return this.embedBuilder;
    }

    public void sendInfoCommand() {
        event.replyEmbeds(getPageOne().build()).addActionRow(Button.success("cssnhelp", "❓ CSSN Network Help"))
                .setEphemeral(true).queue();
    }

    public void sendLearnCommand() {
        event.replyEmbeds(ButtonHelperModule.getLearnChessCard("Rook").build())
                .addActionRow(Button.primary("Bishop", "♝")).setEphemeral(true).queue();
    }

    @Override
    public void trigger(String commandName) {
        switch (commandName) {
            case "help" -> sendInfoCommand();

            case "learnchess" -> sendLearnCommand();
        }
    }

}
