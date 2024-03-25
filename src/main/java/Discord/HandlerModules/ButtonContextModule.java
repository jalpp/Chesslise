package Discord.HandlerModules;

import Abstraction.ChessUtil;
import Abstraction.Context.ContextHandler;
import Chesscom.DailyCommandCC;
import Discord.MainHandler.AntiSpam;
import Discord.MainHandler.CommandInfo;
import Engine.StockFish;
import Lichess.DailyCommand;
import Lichess.Game;
import chariot.Client;
import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.Side;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.MessageContextInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import net.dv8tion.jda.api.interactions.modals.Modal;

import java.awt.*;

public class ButtonContextModule implements ContextHandler {
    public ButtonContextModule(){

    }
    @Override
    public void handleLogic(MessageContextInteractionEvent context, SlashCommandInteractionEvent slashEvent, ButtonInteractionEvent buttonEvent, ModalInteractionEvent eventModal, Client client, Board board, Board blackboard, AntiSpam spam, AntiSpam dailyspam, AntiSpam watchlimit) {


        CommandInfo commandInfo = new CommandInfo();
        DailyCommand dailyCommand = new DailyCommand(client);
        Game generateChallenge = new Game();

        if(buttonEvent.getComponentId().equalsIgnoreCase("re-load")){
            ChessUtil util = new ChessUtil();
            buttonEvent.replyEmbeds(new EmbedBuilder().setColor(Color.cyan).setTitle("Lichess Daily Puzzle").setImage(util.getImageFromFEN(DailyCommand.getDailyPuzzleFEN(client), DailyCommand.getDailyPuzzleFEN(client).contains("b"), "blue", "chessnut")).setFooter("Use /answer to check your solution").build()).setEphemeral(true).queue();

        }

        if(buttonEvent.getComponentId().equalsIgnoreCase("puzzlecc")){
            DailyCommandCC daily = new DailyCommandCC();
            buttonEvent.replyEmbeds(new EmbedBuilder().setColor(Color.magenta).setTitle("Chess.com Daily Puzzle").setImage(daily.getPuzzle()).setFooter("Click on URL for solution").build()).addActionRow(net.dv8tion.jda.api.interactions.components.buttons.Button.link(daily.getPuzzleURL(), daily.getPuzzleSideToMove())).setEphemeral(true).queue();
        }

        switch (buttonEvent.getComponentId()){
            case "bot-lose":
                board.loadFromFen(new Board().getFen());
                buttonEvent.editMessageEmbeds(new EmbedBuilder().setDescription("You have resigned the game!").setColor(Color.red).build()).setActionRow(net.dv8tion.jda.api.interactions.components.buttons.Button.danger("bot-lose", "Resgin").asDisabled(), net.dv8tion.jda.api.interactions.components.buttons.Button.secondary("bot-draw", "Draw").asDisabled()).queue();
                break;
            case "bot-lose-black":
                blackboard.loadFromFen(new Board().getFen());
                buttonEvent.editMessageEmbeds(new EmbedBuilder().setDescription("You have resigned the game!").setColor(Color.red).build()).setActionRow(net.dv8tion.jda.api.interactions.components.buttons.Button.danger("bot-lose-black", "Resgin").asDisabled(), net.dv8tion.jda.api.interactions.components.buttons.Button.secondary("bot-draw-black", "Draw").asDisabled()).queue();
                break;
            case "bot-draw":
                board.loadFromFen(new Board().getFen());
                buttonEvent.editMessageEmbeds(new EmbedBuilder().setDescription("Lise accepts the draw!").setColor(Color.yellow).build()).setActionRow(net.dv8tion.jda.api.interactions.components.buttons.Button.danger("bot-lose", "Resgin").asDisabled(), net.dv8tion.jda.api.interactions.components.buttons.Button.secondary("bot-draw", "Draw").asDisabled()).queue();
                break;
            case "bot-draw-black":
                board.loadFromFen(new Board().getFen());
                buttonEvent.editMessageEmbeds(new EmbedBuilder().setDescription("Lise accepts the draw!").setColor(Color.yellow).build()).setActionRow(net.dv8tion.jda.api.interactions.components.buttons.Button.danger("bot-lose-black", "Resgin").asDisabled(), net.dv8tion.jda.api.interactions.components.buttons.Button.secondary("bot-draw-black", "Draw").asDisabled()).queue();
                break;

            case "white-side":
                board.setSideToMove(Side.WHITE);
                buttonEvent.reply("Set to White Side!").queue();
                break;
            case "black-side":
                board.setSideToMove(Side.BLACK);
                buttonEvent.reply("Set to Black Side!").queue();
                break;
        }

        switch (buttonEvent.getComponentId()){
            case "3c":
                TextInput wtext = TextInput.create("min-3c", "Input Time Mins [1 to 10]", TextInputStyle.SHORT)
                        .setPlaceholder("Input Time Mins [1 to 10]")
                        .setMinLength(1)
                        .setMaxLength(2)
                        .setRequired(true)
                        .build();
                TextInput stext = TextInput.create("sec-3c", "Input Time secs [1 to 10]", TextInputStyle.SHORT)
                        .setPlaceholder("Input Time secs [1 to 10]")
                        .setMinLength(1)
                        .setMaxLength(2)
                        .setRequired(true)
                        .build();
                TextInput btext = TextInput.create("bool-3c", "is Rated? [Y or N]", TextInputStyle.SHORT)
                        .setPlaceholder("is Rated? [Y or N]")
                        .setMinLength(1)
                        .setMaxLength(2)
                        .setRequired(true)
                        .build();

                Modal wmodal = Modal.create("modal-3c", "Create 3 check games")
                        .addActionRows(ActionRow.of(wtext))
                        .addActionRows(ActionRow.of(stext))
                        .addActionRows(ActionRow.of(btext))
                        .build();
                buttonEvent.replyModal(wmodal).queue();
                break;

            case "atomic":
                TextInput awtext = TextInput.create("min-a", "Input Time Mins [1 to 10]", TextInputStyle.SHORT)
                        .setPlaceholder("Input Time Mins [1 to 10]")
                        .setMinLength(1)
                        .setMaxLength(2)
                        .setRequired(true)
                        .build();
                TextInput astext = TextInput.create("sec-a", "Input Time secs [1 to 10]", TextInputStyle.SHORT)
                        .setPlaceholder("Input Time secs [1 to 10]")
                        .setMinLength(1)
                        .setMaxLength(2)
                        .setRequired(true)
                        .build();
                TextInput abtext = TextInput.create("bool-a", "is Rated? [Y or N]", TextInputStyle.SHORT)
                        .setPlaceholder("is Rated? [Y or N]")
                        .setMinLength(1)
                        .setMaxLength(2)
                        .setRequired(true)
                        .build();

                Modal amodal = Modal.create("modal-a", "Create Atomic Games")
                        .addActionRows(ActionRow.of(awtext))
                        .addActionRows(ActionRow.of(astext))
                        .addActionRows(ActionRow.of(abtext))
                        .build();
                buttonEvent.replyModal(amodal).queue();
                break;

            case "960":
                TextInput ninewtext = TextInput.create("min-9", "Input Time Mins [1 to 10]", TextInputStyle.SHORT)
                        .setPlaceholder("Input Time Mins [1 to 10]")
                        .setMinLength(1)
                        .setMaxLength(2)
                        .setRequired(true)
                        .build();
                TextInput ninestext = TextInput.create("sec-9", "Input Time secs [1 to 10]", TextInputStyle.SHORT)
                        .setPlaceholder("Input Time secs [1 to 10]")
                        .setMinLength(1)
                        .setMaxLength(2)
                        .setRequired(true)
                        .build();
                TextInput ninebtext = TextInput.create("bool-9", "is Rated? [Y or N]", TextInputStyle.SHORT)
                        .setPlaceholder("is Rated? [Y or N]")
                        .setMinLength(1)
                        .setMaxLength(2)
                        .setRequired(true)
                        .build();

                Modal nmodal = Modal.create("modal-9", "Create Chess960 Games")
                        .addActionRows(ActionRow.of(ninewtext))
                        .addActionRows(ActionRow.of(ninestext))
                        .addActionRows(ActionRow.of(ninebtext))
                        .build();
                buttonEvent.replyModal(nmodal).queue();
                break;
        }


        if(buttonEvent.getComponentId().equals("playhelp")){
            EmbedBuilder help = new EmbedBuilder();
            help.setThumbnail("https://static-00.iconduck.com/assets.00/lichess-icon-512x512-q0oh5bwk.png");
            help.setTitle("Guide for /play");
            help.setDescription("/play allows you to play LIVE chess with friends and BOTs!, to set up a **Casual game (friendly)/ Rated (gain/lose rating)**  all users need to do is click on **casual/rated** button\n" +
                    "After you will be prompted to select **Time control**, this option is timecontrol (how long game lasts). \n" +
                    "**Start the game**: One you have selected mode and time, Bot sends Lichess live URL, where you and your friend can click same time to start a **LIVE Chess game**." +
                    "\n\n **Login/Register** \n To play rated make sure to Login/Register on Lichess.org to get chess rating, otherwise just play casual games!"+
                    "\n **Play BOTS** " +
                    "\n To play BOTS click on **Play BOTS**, to play live computer click on **Stockfish** to play BOTS on Lichess click on other options!" +
                    "\n **Need more help?** \n Join our Support server and Developer will help you!");
            buttonEvent.replyEmbeds(help.build()).addActionRow(net.dv8tion.jda.api.interactions.components.buttons.Button.link("https://discord.gg/uncmhknmYg", "Join our server")).setEphemeral(true).queue();
        }


        if(buttonEvent.getComponentId().equals("casmode"))   {
            buttonEvent.editMessage("## Please Pick Your Time Control ⏱️").setActionRow(
                    net.dv8tion.jda.api.interactions.components.buttons.Button.primary("ultrafastc", "1/4+0"),
                    net.dv8tion.jda.api.interactions.components.buttons.Button.primary("bulletfastc", "1+0"),
                    net.dv8tion.jda.api.interactions.components.buttons.Button.primary("blitzfastc", "3+2"),
                    net.dv8tion.jda.api.interactions.components.buttons.Button.primary("rapidfastc", "5+5"),
                    net.dv8tion.jda.api.interactions.components.buttons.Button.success("loadc", "\uD83D\uDD04 Load More Time Controls")
            ).queue();

        }

        if(buttonEvent.getComponentId().equals("ratedmode")){
            buttonEvent.editMessage("## Please Pick Your Time Control ⏱️").setActionRow(
                    net.dv8tion.jda.api.interactions.components.buttons.Button.danger("ultrafastr", "1/4+0"),
                    net.dv8tion.jda.api.interactions.components.buttons.Button.danger("bulletfastr", "1+0"),
                    net.dv8tion.jda.api.interactions.components.buttons.Button.danger("blitzfastr", "3+2"),
                    net.dv8tion.jda.api.interactions.components.buttons.Button.danger("rapidfastr", "5+5"),
                    net.dv8tion.jda.api.interactions.components.buttons.Button.success("loadr", "\uD83D\uDD04 Load More Time Controls")
            ).queue();

        }

        if(buttonEvent.getComponentId().equals("enginemode")){
            buttonEvent.editMessage("** Challenge This BOTs! **").setActionRow(
                    net.dv8tion.jda.api.interactions.components.buttons.Button.link("https://listudy.org/en/play-stockfish", "Stockfish"),
                    net.dv8tion.jda.api.interactions.components.buttons.Button.link("https://lichess.org/@/leela2200", "LeelaZero"),
                    net.dv8tion.jda.api.interactions.components.buttons.Button.link("https://lichess.org/@/Dummyette", "Dummyette"),
                    net.dv8tion.jda.api.interactions.components.buttons.Button.link("https://lichess.org/@/SimplerEval", "SimplerEval")
                    , Button.success("load-again", "↩\uFE0F Home")

            ).queue();
        }

        switch (buttonEvent.getComponentId()){
            case "load-again":
                buttonEvent.editMessage("## Please Pick Your Lichess Game's Mode ⚔️ " + "\n\n").setActionRow(
                        Button.success("casmode", "Casual"), Button.danger("ratedmode", "Rated"), Button.success("enginemode", "Play BOT"), Button.link("https://lichess.org/login", "Login/Register"), Button.secondary("playhelp", "❓ Help")).queue();
                break;
            case "loadr":
                buttonEvent.editMessage(" ## Please Pick Your Time Control ⏱️").setActionRow(net.dv8tion.jda.api.interactions.components.buttons.Button.danger("3+0r", "3+0")
                        , net.dv8tion.jda.api.interactions.components.buttons.Button.danger("5+0r", "5+0")
                        , net.dv8tion.jda.api.interactions.components.buttons.Button.danger("10+0r", "10+0"), Button.success("load-again", "↩\uFE0F Home")).queue();
                break;
            case "loadc":
                buttonEvent.editMessage(" ## Please Pick Your Time Control ⏱️").setActionRow(net.dv8tion.jda.api.interactions.components.buttons.Button.primary("3+0c", "3+0")
                        , net.dv8tion.jda.api.interactions.components.buttons.Button.primary("5+0c", "5+0")
                        , net.dv8tion.jda.api.interactions.components.buttons.Button.primary("10+0c", "10+0") , Button.success("load-again", "↩\uFE0F Home")).queue();
                break;
            case "3+0r":
                buttonEvent.editMessage(generateChallenge.generateOpenEndedChallengeURLs(3, 0, true, client)).setActionRow(net.dv8tion.jda.api.interactions.components.buttons.Button.primary("3+0r", "Rematch"), Button.success("load-again", "↩\uFE0F Home")).queue();
                break;
            case "5+0r":
                buttonEvent.editMessage(generateChallenge.generateOpenEndedChallengeURLs(5, 0, true, client)).setActionRow(net.dv8tion.jda.api.interactions.components.buttons.Button.primary("5+0r", "Rematch"), Button.success("load-again", "↩\uFE0F Home")).queue();
                break;
            case "10+0r":
                buttonEvent.editMessage(generateChallenge.generateOpenEndedChallengeURLs(10, 0, true, client)).setActionRow(net.dv8tion.jda.api.interactions.components.buttons.Button.primary("10+0r", "Rematch"), Button.success("load-again", "↩\uFE0F Home")).queue();
                break;
            case "3+0c":
                buttonEvent.editMessage(generateChallenge.generateOpenEndedChallengeURLs(3,0,false, client)).setActionRow(net.dv8tion.jda.api.interactions.components.buttons.Button.primary("3+0c", "Rematch"), Button.success("load-again", "↩\uFE0F Home")).queue();
                break;
            case "5+0c":
                buttonEvent.editMessage(generateChallenge.generateOpenEndedChallengeURLs(5,0,false, client)).setActionRow(net.dv8tion.jda.api.interactions.components.buttons.Button.primary("5+0c", "Rematch"), Button.success("load-again", "↩\uFE0F Home")).queue();
                break;
            case "10+0c":
                buttonEvent.editMessage(generateChallenge.generateOpenEndedChallengeURLs(10, 0, false, client)).setActionRow(net.dv8tion.jda.api.interactions.components.buttons.Button.primary("10+0c", "Rematch"), Button.success("load-again", "↩\uFE0F Home")).queue();
                break;
        }

        switch (buttonEvent.getComponentId()){
            case "ultrafastc":
                buttonEvent.editMessage(generateChallenge.generateOpenEndedChallengeURLs(0,0,false,client)).setActionRow(net.dv8tion.jda.api.interactions.components.buttons.Button.primary("ultrafastc", "Rematch"), Button.success("load-again", "↩\uFE0F Home")).queue();
            case "bulletfastc":
                buttonEvent.editMessage(generateChallenge.generateOpenEndedChallengeURLs(1,0,false,client)).setActionRow(net.dv8tion.jda.api.interactions.components.buttons.Button.primary("bulletfastc", "Rematch"), Button.success("load-again", "↩\uFE0F Home")).queue();
                break;
            case "blitzfastc":
                buttonEvent.editMessage(generateChallenge.generateOpenEndedChallengeURLs(3,2,false,client)).setActionRow(net.dv8tion.jda.api.interactions.components.buttons.Button.primary("blitzfastc", "Rematch"), Button.success("load-again", "↩\uFE0F Home")).queue();
                break;
            case "rapidfastc":
                buttonEvent.editMessage(generateChallenge.generateOpenEndedChallengeURLs(5,5,false,client)).setActionRow(net.dv8tion.jda.api.interactions.components.buttons.Button.primary("rapidfastc", "Rematch"), Button.success("load-again", "↩\uFE0F Home")).queue();
                break;

        }

        switch (buttonEvent.getComponentId()){
            case "ultrafastr":
                buttonEvent.editMessage(generateChallenge.generateOpenEndedChallengeURLs(0,0,true,client)).setActionRow(net.dv8tion.jda.api.interactions.components.buttons.Button.primary("ultrafastr", "Rematch"), Button.success("load-again", "↩\uFE0F Home")).queue();
            case "bulletfastr":
                buttonEvent.editMessage(generateChallenge.generateOpenEndedChallengeURLs(1,0,true,client)).setActionRow(net.dv8tion.jda.api.interactions.components.buttons.Button.primary("bulletfastr", "Rematch"), Button.success("load-again", "↩\uFE0F Home")).queue();
                break;
            case "blitzfastr":
                buttonEvent.editMessage(generateChallenge.generateOpenEndedChallengeURLs(3,2,true,client)).setActionRow(net.dv8tion.jda.api.interactions.components.buttons.Button.primary("blitzfastr", "Rematch"), Button.success("load-again", "↩\uFE0F Home")).queue();
                break;
            case "rapidfastr":
                buttonEvent.editMessage(generateChallenge.generateOpenEndedChallengeURLs(5,5,true,client)).setActionRow(net.dv8tion.jda.api.interactions.components.buttons.Button.primary("rapidfastr", "Rematch"), Button.success("load-again", "↩\uFE0F Home")).queue();
                break;
            case "sf":
                getStockfishSearch(StockFish.getUserFen.get(buttonEvent.getUser().getId()), buttonEvent);
                break;

        }

        if(buttonEvent.getComponentId().equals("next")){
            buttonEvent.editMessageEmbeds(commandInfo.getPageTwo().build()).setActionRow(net.dv8tion.jda.api.interactions.components.buttons.Button.primary("nexttwo", "➡️"), net.dv8tion.jda.api.interactions.components.buttons.Button.link("https://discord.gg/K2NKarM5KV", "Support Server")).queue();
        }else if(buttonEvent.getComponentId().equals("nexttwo")){
            buttonEvent.editMessageEmbeds(commandInfo.getPageThree().build()).setActionRow(net.dv8tion.jda.api.interactions.components.buttons.Button.primary("nextthree", "➡️").asDisabled(), Button.link("https://discord.gg/K2NKarM5KV", "Support Server")).queue();
        }else if(buttonEvent.getComponentId().equals("Bishop")){
            buttonEvent.editMessageEmbeds(commandInfo.getPageFive().build()).setActionRow(net.dv8tion.jda.api.interactions.components.buttons.Button.primary("Rook", "⬅️"),net.dv8tion.jda.api.interactions.components.buttons.Button.primary("Knight", "♞"), Button.link("https://discord.gg/K2NKarM5KV", "Support Server")).queue();
        }else if(buttonEvent.getComponentId().equals("Knight")){
            buttonEvent.editMessageEmbeds(commandInfo.getPageSix().build()).setActionRow(net.dv8tion.jda.api.interactions.components.buttons.Button.primary("Bishop", "⬅️"),net.dv8tion.jda.api.interactions.components.buttons.Button.primary("Queen", "♛"), Button.link("https://discord.gg/K2NKarM5KV", "Support Server")).queue();
        }else if(buttonEvent.getComponentId().equals("Queen")){
            buttonEvent.editMessageEmbeds(commandInfo.getPageSeven().build()).setActionRow(net.dv8tion.jda.api.interactions.components.buttons.Button.primary("Knight", "⬅️"), Button.link("https://discord.gg/K2NKarM5KV", "Support Server")).queue();
        }else if(buttonEvent.getComponentId().equals("Rook")){
            buttonEvent.editMessageEmbeds(commandInfo.getPageFour().build()).setActionRow(net.dv8tion.jda.api.interactions.components.buttons.Button.primary("Bishop", "♝"), Button.link("https://discord.gg/K2NKarM5KV", "Support Server")).queue();
        }



        if(buttonEvent.getComponentId().equals("hint")){
            buttonEvent.replyEmbeds(dailyCommand.getThemes().build()).setEphemeral(true).queue();
        }


    }


    public void getStockfishSearch(String fen, ButtonInteractionEvent buttonEvent){
        ChessUtil util = new ChessUtil();
        EmbedBuilder sf = new EmbedBuilder();
        Board b = new Board();
        b.loadFromFen(fen);
            sf.setThumbnail("https://stockfishchess.org/images/logo/icon_512x512@2x.png");
            sf.setImage(util.getImageFromFEN(fen, !fen.contains("w"), "brown", "kosal"));
            sf.setDescription(StockFish.getStockFishTextExplanation(15, fen) + "\n\n [Join our Server ♟\uFE0F](https://discord.gg/uncmhknmYg)");
            sf.setColor(Color.green);
            b.doMove(StockFish.getBestMove(15, fen));
            StockFish.getUserFen.put(buttonEvent.getUser().getId(), b.getFen());
            buttonEvent.editMessageEmbeds(sf.build()).setActionRow(Button.secondary("sf", "Play move")).queue();
    }

}
