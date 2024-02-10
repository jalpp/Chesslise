package Discord.HandlerModules;

import Abstraction.ChessPuzzle;
import Abstraction.ChessUtil;
import Abstraction.Context.ContextHandler;
import Chesscom.DailyCommandCC;
import Chesscom.puzzle;
import Discord.MainHandler.AntiSpam;
import Discord.MainHandler.CommandInfo;
import Engine.LiseChessEngine;
import Engine.StockFish;
import Lichess.*;
import chariot.Client;
import com.github.bhlangonijr.chesslib.Board;
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
import java.util.Objects;

public class SlashContextModule implements ContextHandler {
    @Override
    public void handleLogic(MessageContextInteractionEvent context, SlashCommandInteractionEvent slashEvent, ButtonInteractionEvent buttonEvent, ModalInteractionEvent eventModal, Client client, Board board, Board blackboard, AntiSpam spam, AntiSpam dailyspam, AntiSpam watchlimit) {
        String name = slashEvent.getName();
        switch (name) {

            case "answer" -> {
                DailyCommand puzzleC = new DailyCommand(client);
                String s = puzzleC.getPuzzle();
                if (puzzleC.checkSolution(slashEvent.getOption("daily-Chesscom.puzzle-answer").getAsString())) {
                    slashEvent.reply("You have solved daily Chesscom.puzzle! for Full solution view **Analysis Board** Below!").addActionRow(Button.link( puzzleC.getPuzzleURL(), "View On Lichess")).setEphemeral(true).queue();
                } else {
                    slashEvent.reply("You failed solving daily Chesscom.puzzle try again!").addActionRow(Button.primary("sol", "View Solution")).setEphemeral(true).queue();
                }
            }
            case "resetboard" -> {
                board.loadFromFen(new Board().getFen());
                blackboard.loadFromFen(new Board().getFen());
                slashEvent.reply("board is reset!").setEphemeral(true).queue();
            }
            case "move" -> {
                try {

                    //LiseChessEngine engine = new LiseChessEngine(board);
                    String makemove = slashEvent.getOption("play-move").getAsString();
                    EmbedBuilder embedBuilder = new EmbedBuilder();
                    ChessUtil util = new ChessUtil();

                    if (board.isMated() || board.isDraw() || board.isStaleMate()) {
                        slashEvent.reply("game over!").queue();
                        board = new Board();
                    }

                    board.doMove(makemove);

                    board.doMove(StockFish.getBestMove(3,board.getFen()));
                    embedBuilder.setTitle("White to move");
                    embedBuilder.setColor(Color.green);
                    embedBuilder.setThumbnail("https://stockfishchess.org/images/logo/icon_512x512@2x.png");
                    embedBuilder.setImage(util.getImageFromFEN(board.getFen(), false, "brown", "kosal"));
                    embedBuilder.setFooter("Your playing Stockfish engine");
                    slashEvent.getChannel().sendMessageEmbeds(embedBuilder.build()).queue();
                    slashEvent.replyEmbeds(embedBuilder.build()).addActionRow(Button.danger("bot-lose", "Resign"), Button.secondary("bot-draw", "Draw")).setEphemeral(true).queue();


                } catch (Exception e) {

                    slashEvent.reply("Not valid move! \n\n **If you are trying to castle use Captial letters (O-O & O-O-O)** \n\n (If you are running this command first time) The game is already on in other server, please reset the board with **/resetboard**! If you want to play black side use **/moveblack**").setEphemeral(true).queue();

                }
            }

            case "moveblack" -> {

                try {

                    if(blackboard.getFen().equalsIgnoreCase("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1")){
                        LiseChessEngine engine = new LiseChessEngine(blackboard);
                        EmbedBuilder embedBuilder = new EmbedBuilder();

                        if (engine.gameOver()) {
                            slashEvent.reply("game over!").queue();
                            engine.resetBoard();
                            blackboard = new Board();
                        }

                        engine.playDiscordBotMoves();
                        embedBuilder.setColor(Color.green);
                        embedBuilder.setImage(engine.getImageOfCurrentBoard(true));
                        embedBuilder.setFooter("Black to move");
                        slashEvent.getChannel().sendMessageEmbeds(embedBuilder.build()).queue();
                        slashEvent.replyEmbeds(embedBuilder.build()).addActionRow(Button.danger("bot-lose-black", "Resign"), Button.secondary("bot-draw-black", "Draw")).setEphemeral(true).queue();
                    }

                    LiseChessEngine engine = new LiseChessEngine(blackboard);
                    String makemove = slashEvent.getOption("play-move").getAsString();
                    EmbedBuilder embedBuilder = new EmbedBuilder();

                    if (engine.gameOver()) {
                        slashEvent.reply("game over!").queue();
                        engine.resetBoard();
                        blackboard = new Board();
                    }
                    blackboard.doMove(makemove);

                    engine.playDiscordBotMoves();
                    embedBuilder.setColor(Color.green);
                    embedBuilder.setImage(engine.getImageOfCurrentBoard(true));
                    embedBuilder.setFooter("Black to move");
                    slashEvent.getChannel().sendMessageEmbeds(embedBuilder.build()).queue();
                    slashEvent.replyEmbeds(embedBuilder.build()).addActionRow(Button.danger("bot-lose-black", "Resign"), Button.secondary("bot-draw-black", "Draw")).setEphemeral(true).queue();


                } catch (Exception e) {

                    slashEvent.reply("Not valid move! \n\n **If you are trying to castle use Captial letters (O-O & O-O-O)** \n\n (If you are running this command first time) The game is already on in other server, please reset the board with **/resetboard**! If you want to play white side use **/move").setEphemeral(true).queue();

                }
            }


            case "suggest" -> {
                String sug = slashEvent.getOption("suggestid").getAsString();
                slashEvent.reply("thanks for feedback! Developer will look into it!").queue();
                slashEvent.getJDA().getGuildById("965333503367589968").getTextChannelById("1053087526971252806").sendMessage(sug).queue();
            }
            case "community" -> {
                EmbedBuilder embedBuilder = new EmbedBuilder();
                embedBuilder.setThumbnail("https://static-00.iconduck.com/assets.00/lichess-icon-512x512-q0oh5bwk.png");
                embedBuilder.setTitle("Best Chess Community To Learn/Play Chess");
                embedBuilder.setColor(Color.blue);
                embedBuilder.setDescription("**lichess.org**  [**Join**](https://discord.gg/lichess)" + "\n\n **Chess.com** [**Join**](https://discord.gg/chesscom)" +
                        "\n\n **The Pawn Zone**  [**Join**](https://discord.gg/6aKNP3t) \n\n **The Moon Club** [**Join**](https://discord.gg/hK8Ru57SKd)");
                slashEvent.replyEmbeds(embedBuilder.build()).queue();
            }

            case "analyze" -> getStockfishSearch(Objects.requireNonNull(slashEvent.getOption("fen")).getAsString(), slashEvent);

            case "broadcast" -> {
                BroadcastLichess broadcast = new BroadcastLichess(client);
                slashEvent.replyEmbeds(broadcast.getBroadData().build()).queue();
            }
            case "dailypuzzlecc" -> {
                slashEvent.reply("this command has moved to /puzzle with the new update!").setEphemeral(true).queue();

            }
            case "service" -> {
                EmbedBuilder embedBuildertos = new EmbedBuilder();
                embedBuildertos.setColor(Color.blue);
                embedBuildertos.setTitle("Terms Of Service And Privacy Policy");
                embedBuildertos.setThumbnail("https://static-00.iconduck.com/assets.00/lichess-icon-512x512-q0oh5bwk.png");
                embedBuildertos.setDescription("What is LISEBOT Terms Of Service?\n" +
                        "\n" +
                        "User agrees that they will have to use latest updated versions of LISEBOT, User also agrees that some commands may be deleted if developer does not want to maintain those commands in future. User is fully responsible for their discord server and LISEBOT does not have any access to the server information/ management. User also agrees to privacy policy which states that LISEBOT does not and will not store any private information \n\n What information does LISEBOT store about me? What is the privacy policy?\n" +
                        "\n" +
                        "LISEBOT Does not and will not store any private user information, all bot commands are Lichess related so auth commands need Lichess token to operate for those commands (which the bot does not store)");
                slashEvent.replyEmbeds(embedBuildertos.build()).queue();
            }

            case "watchmaster" -> {
                WatchMaster watchMaster = new WatchMaster(client);
                slashEvent.deferReply(true).queue();
                slashEvent.getChannel().sendMessage(watchMaster.getMasterGames()).addActionRow(Button.link("https://lichess.org" + watchMaster.getGameId()[1], "Analyze")).queue();
            }
            case "puzzle" -> {
                switch (slashEvent.getOptionsByName("pick-puzzle").get(0).getAsString()){
                    case "lip" -> {
                        if (dailyspam.checkSpam(slashEvent)) {
                            slashEvent.reply("Check back after 24 hours for next daily Chesscom.puzzle!").setEphemeral(true).queue();
                        } else {
                            DailyCommand.getLichessDailyPuzzle(client, slashEvent, context, true);
                        }
                    }

                    case "cpp" -> {
                        DailyCommandCC daily = new DailyCommandCC();
                        slashEvent.replyEmbeds(new EmbedBuilder().setColor(Color.magenta).setTitle("Chess.com Daily Puzzle").setDescription(StockFish.getStockFishTextExplanation(13, daily.getFEN()) + "\n\n " + daily.defineSideToMove(new ChessUtil(), daily.getFEN())).setImage(daily.getPuzzle()).setFooter("run /analyze [fen] to view the moves in action!").build()).queue();
                    }

                    case "random" -> {
                        if (spam.checkSpam(slashEvent)) {
                    slashEvent.reply("Only 1 Chesscom puzzle request within 5 mins! Please take your time to solve the Chesscom puzzle!").setEphemeral(true).queue();
                } else {
                    try {
                        puzzle puzzle = new puzzle();
                        String s = puzzle.getPuzzle();
                        String fen = puzzle.getFEN();
                        slashEvent.replyEmbeds(new EmbedBuilder().setColor(Color.green).setTitle("Chess.com Random Puzzle").setImage(s).setDescription(StockFish.getStockFishTextExplanation(13, fen) + "\n\n " + puzzle.defineSideToMove(new ChessUtil(), fen)).setFooter("run /analyze [fen] to view the moves in action!").build()).queue();
                    } catch (Exception e) {
                        slashEvent.getChannel().sendMessage("An error occurred.. Please contact Dev, or wait for few mins to rerun the command").queue();
                    }

                }
                    }
                }

            }

            case "help" -> {
                CommandInfo commandInfo = new CommandInfo();
                slashEvent.replyEmbeds(commandInfo.getPageOne().build()).addActionRow(Button.primary("next", "➡️"), Button.link("https://discord.gg/K2NKarM5KV", "Support Server")).setEphemeral(true).queue();
            }
//////////////////////
                case "tech" -> {
                    CommandInfo commandInfo = new CommandInfo();
                    slashEvent.replyEmbeds(commandInfo.getPageFour().build()).addActionRow(Button.primary("Rook", "➡️"), Button.link("https://discord.gg/K2NKarM5KV", "Support Server")).setEphemeral(true).queue();
                }
            case "play" -> {
                slashEvent.reply("## Please Pick Your Lichess Game's Mode ⚔️ " + "\n\n").addActionRow(
                        Button.success("casmode", "Casual"), Button.danger("ratedmode", "Rated"), Button.success("enginemode", "Play BOT"), Button.link("https://lichess.org/login", "Login/Register"), Button.secondary("playhelp", "❓ Help")).queue();
            }
            case "profile" -> {
                TextInput ptext = TextInput.create("profileuser", "Input Lichess Username", TextInputStyle.SHORT)
                        .setPlaceholder("Input Lichess Username")
                        .setMinLength(2)
                        .setMaxLength(100)
                        .build();
                Modal pmodal = Modal.create("modalpro", "View Lichess Profiles!")
                        .addActionRows(ActionRow.of(ptext))
                        .build();
                slashEvent.replyModal(pmodal).queue();
            }
            case "profilecc" -> {
                TextInput ctext = TextInput.create("profileusercc", "Input Chess.com Username", TextInputStyle.SHORT)
                        .setPlaceholder("Input Chess.com Username")
                        .setMinLength(2)
                        .setMaxLength(100)
                        .build();
                Modal cmodal = Modal.create("modalproc", "View Chess.com Profiles!")
                        .addActionRows(ActionRow.of(ctext))
                        .build();
                slashEvent.replyModal(cmodal).queue();
            }
            case "streamers" -> {
                LiveStreamers liveStreamers = new LiveStreamers(client);
                slashEvent.replyEmbeds(liveStreamers.getTv().build()).queue();
            }
            case "dailypuzzle" -> {
                slashEvent.reply("this command has moved to /puzzle with the new update!").setEphemeral(true).queue();


            }
            case "watch" -> {
                if (watchlimit.checkSpam(slashEvent)) {
                    slashEvent.reply("You have hit max limit! You can only send 24 games in 1 day, try again in 24 hours!").setEphemeral(true).queue();
                } else {

                    TextInput wtext = TextInput.create("watch_user_or_game", "Input Lichess Username Or Lichess.Game", TextInputStyle.SHORT)
                            .setPlaceholder("Input Lichess Username Or Lichess.Game")
                            .setMinLength(2)
                            .setMaxLength(100)
                            .setRequired(true)

                            .build();
                    Modal wmodal = Modal.create("modalwatch", "Watch Live Or Recent Lichess Games!")
                            .addActionRows(ActionRow.of(wtext))
                            .build();
                    slashEvent.replyModal(wmodal).queue();

                }
            }
            case "arena" -> {
                String arenaLink = slashEvent.getOption("arenaid").getAsString().trim();
                UserArena userArena = new UserArena(client, arenaLink);
                slashEvent.deferReply(true).queue();
                slashEvent.getChannel().sendMessageEmbeds(userArena.getUserArena().build()).queue();
            }
            case "invite" -> {
                slashEvent.replyEmbeds(new EmbedBuilder().setTitle("Invite me").setDescription("\uD83D\uDC4B [Click here for invite me](https://discord.com/api/oauth2/authorize?client_id=930544707300393021&permissions=277025704000&scope=bot%20applications.commands) \n\n \uD83D\uDC4D [Vote me on top.gg](https://top.gg/bot/930544707300393021/vote) \n\n \uD83D\uDEE0️ [Join Support Server](https://discord.com/invite/6GdGqwxBdW) ").build()).queue();
            }

        }
    }



    public void getStockfishSearch(String fen, SlashCommandInteractionEvent slashEvent){
        ChessUtil util = new ChessUtil();
        EmbedBuilder sf = new EmbedBuilder();
        Board b = new Board();
        b.loadFromFen(fen);
        sf.setThumbnail("https://stockfishchess.org/images/logo/icon_512x512@2x.png");
        sf.setImage(util.getImageFromFEN(fen, !fen.contains("w"), "brown", "kosal"));
        sf.setDescription(StockFish.getStockFishTextExplanation(13, fen));
        sf.setColor(Color.green);
        b.doMove(StockFish.getBestMove(13, fen));

        StockFish.getUserFen.put(slashEvent.getUser().getId(), b.getFen());
        slashEvent.replyEmbeds(sf.build()).addActionRow(Button.secondary("sf", "Play move")).queue();
    }




}
