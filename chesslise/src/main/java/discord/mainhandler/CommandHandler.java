package discord.mainhandler;

import discord.handlermodules.*;
import lombok.SneakyThrows;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.EntitySelectInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;


public class CommandHandler extends ListenerAdapter {


    @SneakyThrows
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        new SlashContextModule(event).handleLogic();
    }


    @Override
    public void onModalInteraction(@NotNull ModalInteractionEvent event) {
        new ModalContextModule(event).handleLogic();
    }


    @Override
    public void onButtonInteraction(@NotNull ButtonInteractionEvent event) {
        new ButtonContextModule(event).handleLogic();
    }

    @Override
    public void onStringSelectInteraction(@NotNull StringSelectInteractionEvent event) {
        new SelectContextModule(event).handleLogic();
    }

    @Override
    public void onEntitySelectInteraction(@NotNull EntitySelectInteractionEvent event) {
        new EntityContextModule(event).handleLogic();
    }

    @Override
    public void onReady(ReadyEvent event) {
        JDA jda = event.getJDA();
        int guildCount = jda.getGuilds().size();

        jda.getPresence().setActivity(Activity.watching("V17 Servers: " + guildCount + " 5 million puzzles and 62 themes added!"));
    }


}



