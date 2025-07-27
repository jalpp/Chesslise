package discord.helpermodules;

import java.util.List;

import abstraction.CommandTrigger;
import chariot.Client;
import chesscom.DailyCommandCC;
import chesscom.puzzle;
import discord.helpermodules.chess.ChessMoveConverter;
import discord.helpermodules.chess.PgnParser;
import discord.mainhandler.AntiSpam;
import lichess.DailyCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import setting.SettingSchema;
import setting.SettingSchemaModule;

public class PuzzleContextHelperModule extends SettingSchemaModule implements CommandTrigger {

    private final SlashCommandInteractionEvent slashEvent;
    private final AntiSpam spam = new AntiSpam(300000, 1);
    private static final Client client = Client.basic(conf -> conf.retries(0));
    private final SettingSchema setting = getSettingSchema();

    public PuzzleContextHelperModule(SlashCommandInteractionEvent slashEvent) {
        super(slashEvent.getUser().getId());
        this.slashEvent = slashEvent;
    }

    public void sendSlashLichesspuzzleCommand() {
        slashEvent.deferReply(false).queue();
        DailyCommand dailyCommand = new DailyCommand(client);
        System.out.println("Fetching today's puzzle from Lichess: " + dailyCommand.getDailyPuzzle().toString());
        PuzzleSolutionHelperModule puzzleSolutionHelperModule = new PuzzleSolutionHelperModule(this.slashEvent);
        puzzleSolutionHelperModule.saveGeneratedPuzzleData((String) dailyCommand.getDailyPuzzle().get("fen"),
                (List<String>) dailyCommand.getDailyPuzzle().get("solution"));

        slashEvent.getHook().sendMessageEmbeds(dailyCommand.defineCommandCard(setting).build())
                .addActionRow(Button.primary("hint", "hint"),
                        Button.link(dailyCommand.defineAnalysisBoard(dailyCommand.definePuzzleFen()), "Analysis Board"))
                .queue();
    }

    public void sendDailyPuzzleChessComCommand() {
        slashEvent.deferReply(false).queue();
        DailyCommandCC daily = new DailyCommandCC();
        String puzzlePGN = daily.definePuzzlePGN();
        List<String> moves = PgnParser.extractMoves(puzzlePGN);
        System.out.println("Moves: " + moves.toString());
        List<String> uciMove = ChessMoveConverter.convertSanToUci(daily.definePuzzleFen(), moves);
        System.out.println("uciMove: " + uciMove.toString());
        PuzzleSolutionHelperModule puzzleSolutionHelperModule = new PuzzleSolutionHelperModule(this.slashEvent);
        puzzleSolutionHelperModule.saveGeneratedPuzzleData(daily.definePuzzleFen(), uciMove);
        slashEvent.getHook().sendMessageEmbeds(daily.defineCommandCard(setting).build())
                .addActionRow(Button.link(daily.defineAnalysisBoard(daily.definePuzzleFen()), "Analysis Board"))
                .queue();
    }

    public void sendRandomPuzzleChessComCommand() {
        slashEvent.deferReply(false).queue();
        if (spam.checkSpam(slashEvent)) {
            slashEvent.getHook().sendMessage(
                    "Only 1 Chesscom puzzle request within 5 mins! Please take your time to solve the Chesscom puzzle!")
                    .setEphemeral(true)
                    .queue();
        } else {
            try {
                puzzle puzzle = new puzzle();
                String puzzlePGN = puzzle.definePGN();
                System.out.println("puzzlePGN: " + puzzlePGN);
                List<String> moves = PgnParser.extractMoves(puzzlePGN);
                System.out.println("Moves: " + moves.toString());
                List<String> uciMove = ChessMoveConverter.convertSanToUci(puzzle.definePuzzleFen(), moves);
                System.out.println("uciMove: " + uciMove.toString());
                PuzzleSolutionHelperModule puzzleSolutionHelperModule = new PuzzleSolutionHelperModule(this.slashEvent);
                puzzleSolutionHelperModule.saveGeneratedPuzzleData(puzzle.definePuzzleFen(), uciMove);
                slashEvent.getHook().sendMessageEmbeds(puzzle.defineCommandCard(setting).build())
                        .addActionRow(
                                Button.link(puzzle.defineAnalysisBoard(puzzle.definePuzzleFen()), "Analysis Board"))
                        .queue();
            } catch (Exception e) {
                slashEvent.getHook()
                        .sendMessage(
                                "An error occurred.. Please contact Dev, or wait for few mins to rerun the command")
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
        slashEvent.getHook()
                .sendMessageEmbeds(new EmbedBuilder().setDescription("Please select the puzzle theme!").build())
                .addActionRow(
                        Button.success("mate", "Mate"),
                        Button.success("opening", "Opening"),
                        Button.success("middlegame", "Middlegame"),
                        Button.success("endgame", "Endgame"),
                        Button.danger("loadpuzzles", "Load more"))
                .queue();
    }

    @Override
    public void trigger(String commandName) {
        if (commandName.equals("puzzle")) {
            sendPuzzleMenuCommand();
        }
    }
}
