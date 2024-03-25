package Lichess;

import Abstraction.*;
import Engine.StockFish;
import chariot.Client;
import chariot.model.One;
import chariot.model.Puzzle;
import com.github.bhlangonijr.chesslib.Board;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.MessageContextInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.SlashCommandInteraction;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import java.awt.*;
import java.util.List;
import java.util.Objects;


public class DailyCommand extends ChessPuzzle implements Abstraction.Puzzle {


    private String moveSay = "";
    private int rating;
    private String solLink = "";


    public DailyCommand(Client client) {
        super(client, new Board());
    }


    @Override
    public String getPuzzle() {
        One<Puzzle> dailypuzzle = this.getClient().puzzles().dailyPuzzle();

        if (dailypuzzle.isPresent()) {

            String fen = getDailyPuzzleFEN(this.getClient());

            moveSay = defineSideToMove(this.getUtil(), fen);

            solLink = defineAnalysisBoard(this.getUtil(), fen);

            rating = dailypuzzle.get().puzzle().rating();

            return renderImage(this.getUtil(), fen);

        }else{
            return "loading..";
        }
    }

    @Override
    public String getSolution(){

          return null;
    }



    @Override
    public String getPuzzleURL() {
        return this.solLink;
    }

    @Override
    public EmbedBuilder getThemes(){
        EmbedBuilder themes = new EmbedBuilder();

        themes.setColor(Color.orange);

        One<Puzzle> dailypuzzle = this.getClient().puzzles().dailyPuzzle();

        StringBuilder themessay = new StringBuilder();

        for(int i = 0; i < dailypuzzle.get().puzzle().themes().size(); i++){
            themessay.append(dailypuzzle.get().puzzle().themes().get(i)).append(" ");
        }

        themes.setDescription( "**"+ themessay + "**");
        themes.setTitle("Some Hints...");
        return themes;
    }

    @Override
    public String getPuzzleSideToMove() {
        return moveSay;
    }

    @Override
    public int getRating(){
        return rating;
    }

    @Override
    public boolean checkSolution(String answer) {
        try {
            Board checker = new Board();
            Board moveChecker = new Board();
            Board finalMoveChecker = new Board();
            String[] moves = chariot.Client.basic().puzzles().dailyPuzzle().get().game().pgn().split(" ");
            for (String move : moves) {
                checker.doMove(move);
            }

            String startFenPuzzle = checker.getFen();
            moveChecker.loadFromFen(startFenPuzzle);
            finalMoveChecker.loadFromFen(startFenPuzzle);

            String[] aMove = answer.split(" ");
            for (String move : aMove) {
                checker.doMove(move);
            }

            String ansFEN = checker.getFen();

            Board checkSolutionBoard = new Board();
            checkSolutionBoard.loadFromFen(startFenPuzzle);

            List<String> chechSol = chariot.Client.basic().puzzles().dailyPuzzle().get().puzzle().solution();


            for(int j = 0; j < chechSol.size(); j++){
                if(j % 2 == 0 && answer.equalsIgnoreCase(chechSol.get(j))){
                    return true;
                }
            }

            for(int j = 0; j < chechSol.size(); j++){
                if(j % 2 == 0){
                    moveChecker.doMove(chechSol.get(j));
                    finalMoveChecker.doMove(answer);
                    if(moveChecker.getFen().equalsIgnoreCase(finalMoveChecker.getFen())){
                        return true;
                    }
                   // return true;
                }
            }

            for (String move : chechSol) {
                checkSolutionBoard.doMove(move);
            }

            String puzzleEND = checkSolutionBoard.getFen();

            System.out.println(puzzleEND);
            System.out.println(ansFEN);

            return puzzleEND.equalsIgnoreCase(ansFEN);

        }catch (Exception e){
            return false;
        }

    }



    public String getFullSoultion() {
        StringBuilder movesAns = new StringBuilder();

        for(String moves: this.getClient().puzzles().dailyPuzzle().get().puzzle().solution()){
            movesAns.append(moves).append(" ");
        }

        return movesAns.toString();
    }


    public static String getDailyPuzzleFEN(Client client){
        Board fenRender = new Board();
        for(String moves: client.puzzles().dailyPuzzle().get().game().pgn().split(" ")){
            fenRender.doMove(moves);
        }

        return fenRender.getFen();
    }



    public static void getLichessDailyPuzzle(Client client, SlashCommandInteraction slashEvent, MessageContextInteractionEvent msgEvent, boolean isSlashContext){

        String hostimg = "https://upload.wikimedia.org/wikipedia/commons/4/47/Lichess_logo_2019.png";

        if(isSlashContext){
            DailyCommand dailyCommand = new DailyCommand(client);
            String s = dailyCommand.getPuzzle();
            slashEvent.deferReply().setEphemeral(true).queue();
            slashEvent.getChannel().sendMessageEmbeds(new EmbedBuilder().setDescription(StockFish.getStockFishTextExplanation(15, getDailyPuzzleFEN(client)) + "\n\n [Join our Server ♟\uFE0F](https://discord.gg/uncmhknmYg)").setColor(Color.cyan).setTitle("Lichess Daily Puzzle").setFooter("use /analyze [fen] to further analyze/check your answer").setImage(s).setThumbnail(hostimg).build()).addActionRow(net.dv8tion.jda.api.interactions.components.buttons.Button.link(dailyCommand.getPuzzleURL(), dailyCommand.getPuzzleSideToMove() + "| " + "Rating: " + dailyCommand.getRating()), net.dv8tion.jda.api.interactions.components.buttons.Button.success("hint", "Hint")).addActionRow(net.dv8tion.jda.api.interactions.components.buttons.Button.success("re-load", " \uD83D\uDD04 Change View"), Button.primary("puzzlecc", "\uD83C\uDFC1 Bonus Puzzle")).queue();
        }else{
            DailyCommand dailyCommand = new DailyCommand(client);
            msgEvent.deferReply().setEphemeral(true).queue();
            Objects.requireNonNull(msgEvent.getChannel()).sendMessageEmbeds(new EmbedBuilder().setDescription(StockFish.getStockFishTextExplanation(15, getDailyPuzzleFEN(client)) + "\n\n [Join our Server ♟\uFE0F](https://discord.gg/uncmhknmYg)").setColor(Color.cyan).setTitle("Lichess Daily Puzzle").setFooter("use /analyze [fen] to further analyze/check your answer").setImage(dailyCommand.getPuzzle()).setThumbnail(hostimg).build()).addActionRow(net.dv8tion.jda.api.interactions.components.buttons.Button.link(dailyCommand.getPuzzleURL(), dailyCommand.getPuzzleSideToMove() + "| " + "Rating: " + dailyCommand.getRating()), net.dv8tion.jda.api.interactions.components.buttons.Button.success("hint", "Hint")).addActionRow(net.dv8tion.jda.api.interactions.components.buttons.Button.success("re-load", " \uD83D\uDD04 Change View"), net.dv8tion.jda.api.interactions.components.buttons.Button.primary("puzzlecc", "\uD83C\uDFC1 Bonus Puzzle")).queue();
        }


    }



    @Override
    public String renderImage(ChessUtil util, String fen) {
        return util.getImageFromFEN(fen, fen.contains("b"), "brown", "kosal");
    }

    @Override
    public String defineSideToMove(ChessUtil util, String fen) {
        return util.getWhichSideToMove(fen);
    }

    @Override
    public String defineAnalysisBoard(ChessUtil util, String fen) {
        return util.getAnalysisBoard(fen);
    }








}







