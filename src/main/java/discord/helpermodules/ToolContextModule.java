package discord.helpermodules;

import abstraction.ChessUtil;
import chesscom.DailyCommandCC;
import chesscom.puzzle;
import chessdb.ChessDBQuery;
import discord.mainhandler.AntiSpam;
import lichess.*;
import chariot.Client;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.MessageContextInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

public class ToolContextModule {

    public ToolContextModule() {

    }

    public void sendChessDBInfo(SlashCommandInteractionEvent event){
        ChessDBQuery query = new ChessDBQuery();
        ChessUtil chessUtil = new ChessUtil();

        event.deferReply().queue();
        String fen = event.getOption("paste-fen").getAsString();
        String info = query.getTop3BestMove(fen);

        EmbedBuilder builder = new EmbedBuilder();
        builder.setImage(chessUtil.getImageFromFEN(fen, fen.contains("b"), "green", "kosal"));
        builder.setTitle("ChessDB CN Analysis");
        builder.setDescription(info);
        builder.setFooter("Analysis by ChessDB CN see more here https://chessdb.cn/cloudbookc_info_en.html");

        event.getHook().sendMessageEmbeds(builder.build()).queue();
    }

    public void sendSlashLichesspuzzleCommand(SlashCommandInteractionEvent slashEvent, Client client, MessageContextInteractionEvent context, boolean isSlash) {
        DailyCommand dailyCommand = new DailyCommand(client);
        if (isSlash) {
            slashEvent.replyEmbeds(dailyCommand.defineCommandCard().build()).addActionRow(Button.primary("hint", "hint"), Button.link(dailyCommand.defineAnalysisBoard(dailyCommand.defineUtil(), dailyCommand.definePuzzleFen()), "Analysis Board")).queue();
        } else {
            context.replyEmbeds(dailyCommand.defineCommandCard().build()).addActionRow(Button.primary("hint", "hint"), Button.link(dailyCommand.defineAnalysisBoard(dailyCommand.defineUtil(), dailyCommand.definePuzzleFen()), "Analysis Board")).queue();
        }
    }

    public void sendDailyPuzzleChessComCommand(SlashCommandInteractionEvent slashEvent, MessageContextInteractionEvent context, boolean isSlash) {
        DailyCommandCC daily = new DailyCommandCC();
        if (isSlash) {
            slashEvent.replyEmbeds(daily.defineCommandCard().build()).addActionRow(Button.link(daily.defineAnalysisBoard(daily.defineUtil(), daily.definePuzzleFen()), "Analysis Board")).queue();
        } else {
            context.deferReply(false).queue();
            context.replyEmbeds(daily.defineCommandCard().build()).addActionRow(Button.link(daily.defineAnalysisBoard(daily.defineUtil(), daily.definePuzzleFen()), "Analysis Board")).queue();
        }
    }

    public void sendRandomPuzzleChessComCommand(SlashCommandInteractionEvent slashEvent, AntiSpam spam) {
        if (spam.checkSpam(slashEvent)) {
            slashEvent.reply("Only 1 Chesscom puzzle request within 5 mins! Please take your time to solve the Chesscom puzzle!").setEphemeral(true).queue();
        } else {
            try {
                puzzle puzzle = new puzzle();
                slashEvent.replyEmbeds(puzzle.defineCommandCard().build()).addActionRow(Button.link(puzzle.defineAnalysisBoard(puzzle.defineUtil(), puzzle.definePuzzleFen()), "Analysis Board")).queue();
            } catch (Exception e) {
                slashEvent.getChannel().sendMessage("An error occurred.. Please contact Dev, or wait for few mins to rerun the command").queue();
            }

        }
    }

    public void sendPuzzleMenuCommand(SlashCommandInteractionEvent slashEvent, Client client, MessageContextInteractionEvent context, AntiSpam spam) {
        switch (slashEvent.getOptionsByName("pick-puzzle").get(0).getAsString()) {
            case "lip" -> sendSlashLichesspuzzleCommand(slashEvent, client, context, true);

            case "cpp" -> sendDailyPuzzleChessComCommand(slashEvent, context, true);

            case "random" -> sendRandomPuzzleChessComCommand(slashEvent, spam);
        }
    }


    public void sendPlayChallengeCommand(SlashCommandInteractionEvent slashEvent, MessageContextInteractionEvent context, boolean isSlash) {
        if (isSlash) {
            slashEvent.reply("""
                    ## Please Pick Your Lichess Game's Mode ⚔️\s

                    """).addActionRow(
                    Button.success("casmode", "\uD83D\uDC4C Casual"), Button.danger("ratedmode", "\uD83E\uDD3A Rated"), Button.success("friend", "\uD83D\uDDE1️ Play Friend")).addActionRow(Button.link("https://lichess.org/login", "\uD83D\uDD12 Login/Register"), Button.secondary("playhelp", "❓ Help")).queue();
        } else {
            context.reply("""
                    **⚔️ Please Pick Your Lichess.Game's Mode **

                    """).addActionRow(
                    net.dv8tion.jda.api.interactions.components.buttons.Button.success("casmode", "Casual"), net.dv8tion.jda.api.interactions.components.buttons.Button.danger("ratedmode", "Rated"), net.dv8tion.jda.api.interactions.components.buttons.Button.primary("enginemode", "Play BOT"), net.dv8tion.jda.api.interactions.components.buttons.Button.link("https://lichess.org/login", "Login/Register"), net.dv8tion.jda.api.interactions.components.buttons.Button.secondary("playhelp", "❓ Help")).queue();
        }
    }



}