package discord.helpermodules;


import chariot.Client;
import chesscom.DailyCommandCC;
import chesscom.puzzle;
import discord.mainhandler.AntiSpam;
import lichess.DailyCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import setting.SettingSchema;
import setting.SettingSchemaModule;

/**
 * SlashContextHelperModule class to handle the slash command context
 */
public class PuzzleContextHelperModule extends SettingSchemaModule {

    private final SlashCommandInteractionEvent slashEvent;
    private final AntiSpam antispam;
    private final Client client;
    private final SettingSchema setting = getSettingSchema();


    public PuzzleContextHelperModule(SlashCommandInteractionEvent slashEvent, AntiSpam antispam, Client client) {
        super(slashEvent.getUser().getId());
        this.slashEvent = slashEvent;
        this.antispam = antispam;
        this.client = client;
    }

    public void sendSlashLichesspuzzleCommand() {
        slashEvent.deferReply(false).queue();
        DailyCommand dailyCommand = new DailyCommand(client);
        slashEvent.getHook().sendMessageEmbeds(dailyCommand.defineCommandCard(setting).build())
                .addActionRow(Button.primary("hint", "hint"), Button.link(dailyCommand.defineAnalysisBoard(dailyCommand.definePuzzleFen()), "Analysis Board"))
                .queue();
    }

    public void sendDailyPuzzleChessComCommand() {
        slashEvent.deferReply(false).queue();
        DailyCommandCC daily = new DailyCommandCC();
        slashEvent.getHook().sendMessageEmbeds(daily.defineCommandCard(setting).build())
                .addActionRow(Button.link(daily.defineAnalysisBoard(daily.definePuzzleFen()), "Analysis Board"))
                .queue();
    }

    public void sendRandomPuzzleChessComCommand() {
        slashEvent.deferReply(false).queue();
        if (antispam.checkSpam(slashEvent)) {
            slashEvent.getHook().sendMessage("Only 1 Chesscom puzzle request within 5 mins! Please take your time to solve the Chesscom puzzle!")
                    .setEphemeral(true)
                    .queue();
        } else {
            try {
                puzzle puzzle = new puzzle();
                slashEvent.getHook().sendMessageEmbeds(puzzle.defineCommandCard(setting).build())
                        .addActionRow(Button.link(puzzle.defineAnalysisBoard(puzzle.definePuzzleFen()), "Analysis Board"))
                        .queue();
            } catch (Exception e) {
                slashEvent.getHook().sendMessage("An error occurred.. Please contact Dev, or wait for few mins to rerun the command")
                        .queue();
            }
        }
    }

    public void sendPuzzleMenuCommand() {
        switch (slashEvent.getOptionsByName("pick-puzzle").get(0).getAsString()) {
            case "lip" -> sendSlashLichesspuzzleCommand();
            case "cpp" -> sendDailyPuzzleChessComCommand();
            case "random" -> sendRandomPuzzleChessComCommand();
            case "lidb" -> sendThemePuzzle();
        }
    }

    public void sendThemePuzzle() {
        slashEvent.deferReply(false).queue();
        slashEvent.getHook().sendMessageEmbeds(new EmbedBuilder().setDescription("Please select the puzzle theme!").build())
                .addActionRow(
                        Button.success("mate", "Mate"),
                        Button.success("opening", "Opening"),
                        Button.success("middlegame", "Middlegame"),
                        Button.success("endgame", "Endgame"),
                        Button.danger("loadpuzzles", "Load more"))
                .queue();
    }
}