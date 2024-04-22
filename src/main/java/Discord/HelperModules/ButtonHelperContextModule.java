package Discord.HelperModules;

import Chesscom.DailyCommandCC;
import Chesscom.puzzle;
import Discord.MainHandler.CommandInfo;
import Lichess.DailyCommand;
import Lichess.Game;
import chariot.Client;
import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.Side;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import java.awt.*;

public class ButtonHelperContextModule {


    public ButtonHelperContextModule() {

    }


    public void handlePlayingEngineFlow(ButtonInteractionEvent buttonEvent, Board board, Board blackboard) {
        switch (buttonEvent.getComponentId()) {
            case "bot-lose" -> {
                board.loadFromFen(new Board().getFen());
                buttonEvent.editMessageEmbeds(new EmbedBuilder().setDescription("You have resigned the game!").setColor(Color.red).build()).setActionRow(net.dv8tion.jda.api.interactions.components.buttons.Button.danger("bot-lose", "Resgin").asDisabled(), net.dv8tion.jda.api.interactions.components.buttons.Button.secondary("bot-draw", "Draw").asDisabled()).queue();
            }
            case "bot-lose-black" -> {
                blackboard.loadFromFen(new Board().getFen());
                buttonEvent.editMessageEmbeds(new EmbedBuilder().setDescription("You have resigned the game!").setColor(Color.red).build()).setActionRow(net.dv8tion.jda.api.interactions.components.buttons.Button.danger("bot-lose-black", "Resgin").asDisabled(), net.dv8tion.jda.api.interactions.components.buttons.Button.secondary("bot-draw-black", "Draw").asDisabled()).queue();
            }
            case "bot-draw" -> {
                board.loadFromFen(new Board().getFen());
                buttonEvent.editMessageEmbeds(new EmbedBuilder().setDescription("Lise accepts the draw!").setColor(Color.yellow).build()).setActionRow(net.dv8tion.jda.api.interactions.components.buttons.Button.danger("bot-lose", "Resgin").asDisabled(), net.dv8tion.jda.api.interactions.components.buttons.Button.secondary("bot-draw", "Draw").asDisabled()).queue();
            }
            case "bot-draw-black" -> {
                board.loadFromFen(new Board().getFen());
                buttonEvent.editMessageEmbeds(new EmbedBuilder().setDescription("Lise accepts the draw!").setColor(Color.yellow).build()).setActionRow(net.dv8tion.jda.api.interactions.components.buttons.Button.danger("bot-lose-black", "Resgin").asDisabled(), Button.secondary("bot-draw-black", "Draw").asDisabled()).queue();
            }
            case "white-side" -> {
                board.setSideToMove(Side.WHITE);
                buttonEvent.reply("Set to White Side!").queue();
            }
            case "black-side" -> {
                board.setSideToMove(Side.BLACK);
                buttonEvent.reply("Set to Black Side!").queue();
            }
        }
    }

    public void handlePlayCommandFlow(ButtonInteractionEvent buttonEvent, Game generateChallenge, Client client) {
        ModalHelperContextModule modalHelper = new ModalHelperContextModule();

        switch (buttonEvent.getComponentId()) {
            case "load-again" ->
                    buttonEvent.reply("## Please Pick Your Lichess Game's Mode ⚔️ " + "\n\n").addActionRow(
                            Button.success("casmode", "\uD83D\uDC4C Casual"), Button.danger("ratedmode", "\uD83E\uDD3A Rated"), Button.success("friend", "\uD83D\uDDE1\uFE0F Play Friend")).addActionRow(Button.link("https://discord.gg/uncmhknmYg", "\uD83D\uDC4B Join our server!"), Button.link("https://lichess.org/login", "\uD83D\uDD12 Login/Register"), Button.secondary("playhelp", "❓ Help")).queue();
            case "loadr" ->
                    buttonEvent.editMessage(" ## Please Pick Your Time Control ⏱️").setActionRow(Button.danger("3+0r", "\uD83D\uDD25 3+0")
                            , Button.danger("5+0r", "\uD83D\uDD25 5+0")
                            , Button.danger("10+0r", "\uD83D\uDC07 10+0"), Button.success("load-again", "↩\uFE0F Home")).queue();
            case "loadc" ->
                    buttonEvent.editMessage(" ## Please Pick Your Time Control ⏱️").setActionRow(Button.primary("3+0c", " \uD83D\uDD253+0")
                            , Button.primary("5+0c", "\uD83D\uDD25 5+0")
                            , Button.primary("10+0c", "\uD83D\uDC07 10+0"), Button.success("load-again", "↩\uFE0F Home")).queue();
            case "3+0r" ->
                    buttonEvent.editMessage(generateChallenge.generateOpenEndedChallengeURLs(3, 0, true, client)).setActionRow(Button.primary("3+0r", "Rematch"), Button.success("load-again", "↩\uFE0F Home")).queue();
            case "5+0r" ->
                    buttonEvent.editMessage(generateChallenge.generateOpenEndedChallengeURLs(5, 0, true, client)).setActionRow(Button.primary("5+0r", "Rematch"), Button.success("load-again", "↩\uFE0F Home")).queue();
            case "10+0r" ->
                    buttonEvent.editMessage(generateChallenge.generateOpenEndedChallengeURLs(10, 0, true, client)).setActionRow(Button.primary("10+0r", "Rematch"), Button.success("load-again", "↩\uFE0F Home")).queue();
            case "3+0c" ->
                    buttonEvent.editMessage(generateChallenge.generateOpenEndedChallengeURLs(3, 0, false, client)).setActionRow(Button.primary("3+0c", "Rematch"), Button.success("load-again", "↩\uFE0F Home")).queue();
            case "5+0c" ->
                    buttonEvent.editMessage(generateChallenge.generateOpenEndedChallengeURLs(5, 0, false, client)).setActionRow(Button.primary("5+0c", "Rematch"), Button.success("load-again", "↩\uFE0F Home")).queue();
            case "10+0c" ->
                    buttonEvent.editMessage(generateChallenge.generateOpenEndedChallengeURLs(10, 0, false, client)).setActionRow(Button.primary("10+0c", "Rematch"), Button.success("load-again", "↩\uFE0F Home")).queue();
            case "playhelp" -> {
                EmbedBuilder help = new EmbedBuilder();
                help.setThumbnail("https://static-00.iconduck.com/assets.00/lichess-icon-512x512-q0oh5bwk.png");
                help.setTitle("Guide for /play");
                help.setDescription("/play allows you to play LIVE chess with friends and BOTs!, to set up a **Casual game (friendly)/ Rated (gain/lose rating)**  all users need to do is click on **casual/rated** button\n" +
                        "After you will be prompted to select **Time control**, this option is timecontrol (how long game lasts). \n" +
                        "**Start the game**: One you have selected mode and time, Bot sends Lichess live URL, where you and your friend can click same time to start a **LIVE Chess game**." +
                        "\n\n **Login/Register** \n To play rated make sure to Login/Register on Lichess.org to get chess rating, otherwise just play casual games!" +
                        "\n\n **Play BOTS** " +
                        "\n\n To play BOTS click on **Play BOTS**, to play live computer click on **Stockfish** to play BOTS on Lichess click on other options!" +
                        "\n\n **Challenge Friend** \n play your friend by entering your and your friend's Lichess user, ready for challenge? The time control is randomly generated for fun games!" +
                        "\n **Need more help?** \n Join our Support server and Developer will help you!");
                buttonEvent.replyEmbeds(help.build()).addActionRow(net.dv8tion.jda.api.interactions.components.buttons.Button.link("https://discord.gg/uncmhknmYg", "Join our server")).setEphemeral(true).queue();
            }
          case "friend" -> modalHelper.sendSelfUserInputForm(buttonEvent);
        }

    }

    public void handlePlayCommandUI(ButtonInteractionEvent buttonEvent) {

        switch (buttonEvent.getComponentId()){
            case "casmode" -> buttonEvent.editMessage("## Please Pick Your Time Control ⏱️").setActionRow(
                    Button.primary("ultrafastc", "\uD83D\uDE85 1/4+0"),
                    Button.primary("bulletfastc", "\uD83D\uDE85 1+0"),
                    Button.primary("blitzfastc", "\uD83D\uDD25 3+2"),
                    Button.primary("rapidfastc", "\uD83D\uDD25 5+5"),
                    Button.success("loadc", "\uD83D\uDD04 Load More Time Controls")
            ).queue();

            case "ratedmode" -> buttonEvent.editMessage("## Please Pick Your Time Control ⏱️").setActionRow(
                    net.dv8tion.jda.api.interactions.components.buttons.Button.danger("ultrafastr", "\uD83D\uDE85 1/4+0"),
                    net.dv8tion.jda.api.interactions.components.buttons.Button.danger("bulletfastr", "\uD83D\uDE85 1+0"),
                    net.dv8tion.jda.api.interactions.components.buttons.Button.danger("blitzfastr", "\uD83D\uDD25 3+2"),
                    net.dv8tion.jda.api.interactions.components.buttons.Button.danger("rapidfastr", "\uD83D\uDD25 5+5"),
                    net.dv8tion.jda.api.interactions.components.buttons.Button.success("loadr", "\uD83D\uDD04 Load More Time Controls")
            ).queue();

            case "enginemode" -> buttonEvent.editMessage("** Challenge This BOTs! **").setActionRow(
                    net.dv8tion.jda.api.interactions.components.buttons.Button.link("https://listudy.org/en/play-stockfish", "Stockfish"),
                    net.dv8tion.jda.api.interactions.components.buttons.Button.link("https://lichess.org/@/leela2200", "LeelaZero"),
                    net.dv8tion.jda.api.interactions.components.buttons.Button.link("https://lichess.org/@/Dummyette", "Dummyette"),
                    net.dv8tion.jda.api.interactions.components.buttons.Button.link("https://lichess.org/@/SimplerEval", "SimplerEval")
                    , Button.success("load-again", "↩\uFE0F Home")

            ).queue();

        }

    }


    public void handleMoreTimeControls(ButtonInteractionEvent buttonEvent, Game generateChallenge, Client client) {

        BotContextModule contextModule = new BotContextModule();

        switch (buttonEvent.getComponentId()) {
            case "ultrafastc" ->
                    buttonEvent.editMessage(generateChallenge.generateOpenEndedChallengeURLs(0, 0, false, client)).setActionRow(Button.primary("ultrafastc", "Rematch"), Button.success("load-again", "↩\uFE0F Home")).queue();
            case "bulletfastc" ->
                    buttonEvent.editMessage(generateChallenge.generateOpenEndedChallengeURLs(1, 0, false, client)).setActionRow(Button.primary("bulletfastc", "Rematch"), Button.success("load-again", "↩\uFE0F Home")).queue();
            case "blitzfastc" ->
                    buttonEvent.editMessage(generateChallenge.generateOpenEndedChallengeURLs(3, 2, false, client)).setActionRow(Button.primary("blitzfastc", "Rematch"), Button.success("load-again", "↩\uFE0F Home")).queue();
            case "rapidfastc" ->
                    buttonEvent.editMessage(generateChallenge.generateOpenEndedChallengeURLs(5, 5, false, client)).setActionRow(Button.primary("rapidfastc", "Rematch"), Button.success("load-again", "↩\uFE0F Home")).queue();
        }

        switch (buttonEvent.getComponentId()) {
            case "ultrafastr" ->
                    buttonEvent.editMessage(generateChallenge.generateOpenEndedChallengeURLs(0, 0, true, client)).setActionRow(Button.primary("ultrafastr", "Rematch"), Button.success("load-again", "↩\uFE0F Home")).queue();
            case "bulletfastr" ->
                    buttonEvent.editMessage(generateChallenge.generateOpenEndedChallengeURLs(1, 0, true, client)).setActionRow(Button.primary("bulletfastr", "Rematch"), Button.success("load-again", "↩\uFE0F Home")).queue();
            case "blitzfastr" ->
                    buttonEvent.editMessage(generateChallenge.generateOpenEndedChallengeURLs(3, 2, true, client)).setActionRow(Button.primary("blitzfastr", "Rematch"), Button.success("load-again", "↩\uFE0F Home")).queue();
            case "rapidfastr" ->
                    buttonEvent.editMessage(generateChallenge.generateOpenEndedChallengeURLs(5, 5, true, client)).setActionRow(Button.primary("rapidfastr", "Rematch"), Button.success("load-again", "↩\uFE0F Home")).queue();
            case "sf" -> contextModule.runAnalyzeButton(buttonEvent);
        }
    }


    public void handlePuzzleButtons(ButtonInteractionEvent buttonEvent, DailyCommand dailyCommand, DailyCommandCC commandCC, puzzle puzzle, BotContextModule fish, Client client) {

        switch (buttonEvent.getComponentId()){
            case "puzzlecc" -> buttonEvent.replyEmbeds(dailyCommand.defineCommandCard().build()).setEphemeral(true).queue();
            case "hint" ->  buttonEvent.replyEmbeds(dailyCommand.getThemes().build()).setEphemeral(true).queue();
            case "fishaz-li" -> fish.runAnalyzeOnPuzzleCommand(buttonEvent, dailyCommand);
            case "fishaz-cc" -> fish.runAnalyzeOnPuzzleCommand(buttonEvent, commandCC);
            case "fishaz-ccr" -> fish.runAnalyzeOnPuzzleCommand(buttonEvent, puzzle);

        }

    }


    public void handleLearnCommand(ButtonInteractionEvent buttonEvent, CommandInfo commandInfo) {


        switch (buttonEvent.getComponentId()) {
            case "next" -> buttonEvent.editMessageEmbeds(commandInfo.getPageTwo().build()).setActionRow(net.dv8tion.jda.api.interactions.components.buttons.Button.primary("nexttwo", "➡️"), net.dv8tion.jda.api.interactions.components.buttons.Button.link("https://discord.gg/K2NKarM5KV", "Support Server")).queue();
            case "nexttwo" -> buttonEvent.editMessageEmbeds(commandInfo.getPageThree().build()).setActionRow(net.dv8tion.jda.api.interactions.components.buttons.Button.primary("nextthree", "➡️").asDisabled(), Button.link("https://discord.gg/K2NKarM5KV", "Support Server")).queue();
            case "Bishop" -> buttonEvent.editMessageEmbeds(commandInfo.getPageFive().build()).setActionRow(net.dv8tion.jda.api.interactions.components.buttons.Button.primary("Rook", "⬅️"), net.dv8tion.jda.api.interactions.components.buttons.Button.primary("Knight", "♞"), Button.link("https://discord.gg/K2NKarM5KV", "Support Server")).queue();
            case "Knight" -> buttonEvent.editMessageEmbeds(commandInfo.getPageSix().build()).setActionRow(net.dv8tion.jda.api.interactions.components.buttons.Button.primary("Bishop", "⬅️"), net.dv8tion.jda.api.interactions.components.buttons.Button.primary("Queen", "♛"), Button.link("https://discord.gg/K2NKarM5KV", "Support Server")).queue();
            case "Queen" -> buttonEvent.editMessageEmbeds(commandInfo.getPageSeven().build()).setActionRow(net.dv8tion.jda.api.interactions.components.buttons.Button.primary("Knight", "⬅️"), Button.link("https://discord.gg/K2NKarM5KV", "Support Server")).queue();
            case "Rook" -> buttonEvent.editMessageEmbeds(commandInfo.getPageFour().build()).setActionRow(net.dv8tion.jda.api.interactions.components.buttons.Button.primary("Bishop", "♝"), Button.link("https://discord.gg/K2NKarM5KV", "Support Server")).queue();

        }


    }


}

