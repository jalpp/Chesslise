package discord.helpermodules;

import chariot.Client;
import chesscom.DailyCommandCC;
import chesscom.puzzle;
import discord.mainhandler.AntiSpam;
import lichess.DailyCommand;
import net.dv8tion.jda.api.events.interaction.command.MessageContextInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

/**
 * DoubleContextHelperModule class to handle the double context slash and Message context
 */
public class DoubleContextHelperModule {

    private final SlashCommandInteractionEvent slashEvent;
    private final MessageContextInteractionEvent context;
    private final AntiSpam antispam;
    private final Client client;
    private final boolean isSlash;

    public DoubleContextHelperModule(SlashCommandInteractionEvent slashEvent, MessageContextInteractionEvent context, AntiSpam antispam, Client client, boolean isSlash) {
        this.slashEvent = slashEvent;
        this.context = context;
        this.antispam = antispam;
        this.client = client;
        this.isSlash = isSlash;
    }

    /**
     * Send the Lichess Daily Puzzle Command
     */
    public void sendSlashLichesspuzzleCommand() {
        DailyCommand dailyCommand = new DailyCommand(client);
        if (isSlash) {
            slashEvent.replyEmbeds(dailyCommand.defineCommandCard().build()).addActionRow(Button.primary("hint", "hint"), Button.link(dailyCommand.defineAnalysisBoard(dailyCommand.definePuzzleFen()), "Analysis Board")).queue();
        } else {
            context.replyEmbeds(dailyCommand.defineCommandCard().build()).addActionRow(Button.primary("hint", "hint"), Button.link(dailyCommand.defineAnalysisBoard(dailyCommand.definePuzzleFen()), "Analysis Board")).queue();
        }
    }

    /**
     * Send the Chess.com Daily Puzzle Command
     */
    public void sendDailyPuzzleChessComCommand() {
        DailyCommandCC daily = new DailyCommandCC();
        if (isSlash) {
            slashEvent.replyEmbeds(daily.defineCommandCard().build()).addActionRow(Button.link(daily.defineAnalysisBoard(daily.definePuzzleFen()), "Analysis Board")).queue();
        } else {
            context.deferReply(false).queue();
            context.replyEmbeds(daily.defineCommandCard().build()).addActionRow(Button.link(daily.defineAnalysisBoard(daily.definePuzzleFen()), "Analysis Board")).queue();
        }
    }

    /**
     * Send the Random Puzzle Chess.com Command
     */
    public void sendRandomPuzzleChessComCommand() {
        if (antispam.checkSpam(slashEvent)) {
            slashEvent.reply("Only 1 Chesscom puzzle request within 5 mins! Please take your time to solve the Chesscom puzzle!").setEphemeral(true).queue();
        } else {
            try {
                puzzle puzzle = new puzzle();
                slashEvent.replyEmbeds(puzzle.defineCommandCard().build()).addActionRow(Button.link(puzzle.defineAnalysisBoard(puzzle.definePuzzleFen()), "Analysis Board")).queue();
            } catch (Exception e) {
                slashEvent.getChannel().sendMessage("An error occurred.. Please contact Dev, or wait for few mins to rerun the command").queue();
            }

        }
    }

    /**
     * Send the Puzzle Menu Command
     */
    public void sendPuzzleMenuCommand() {
        switch (slashEvent.getOptionsByName("pick-puzzle").get(0).getAsString()) {
            case "lip" -> sendSlashLichesspuzzleCommand();

            case "cpp" -> sendDailyPuzzleChessComCommand();

            case "random" -> sendRandomPuzzleChessComCommand();
        }
    }

    /**
     * Send the Play Challenge Command
     */
    public void sendPlayChallengeCommand() {
        if (isSlash) {
            slashEvent.reply("""
                    ## Please Pick Your Lichess Game's Mode ⚔️\s
                    
                    ⚔️ You can now join Chesslise's own chess server! Find new chess friends, new challenges,
                    read more by clicking on the ❓ **CSSN Network Help**
                    
                    """).addActionRow(
                    Button.success("casmode", "\uD83D\uDC4C Casual"), Button.danger("ratedmode", "\uD83E\uDD3A Rated"), Button.success("friend", "\uD83D\uDDE1️ Play Friend")).addActionRow(Button.link("https://lichess.org/login", "\uD83D\uDD12 Login/Register"), Button.secondary("playhelp", "❓ Help"), Button.success("cssnhelp", "❓ CSSN Network Help")).queue();
        } else {
            context.reply("""
                    **⚔️ Please Pick Your Lichess Game's Mode **
                    
                     ⚔️ You can now join Chesslise's own chess server! Find new chess friends, new challenges,
                    read more by clicking on the ❓ **CSSN Network Help**
                    
                    """).addActionRow(
                    Button.success("casmode", "Casual"), Button.danger("ratedmode", "Rated"), Button.link("https://lichess.org/login", "Login/Register"), Button.secondary("playhelp", "❓ Help"), Button.success("cssnhelp", "❓ CSSN Network Help")).queue();
        }
    }


}
