package Discord.HelperModules;

import Abstraction.ChessUtil;
import Engine.StockFish;
import com.github.bhlangonijr.chesslib.Board;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;


import java.util.HashMap;
import java.util.Objects;

public class PuzzleSolverContextModule {

    public static HashMap<String, String> getUserPuzzleFEN= new HashMap<>();
    public static HashMap<String, Integer> getUserFinishLine = new HashMap<>();
    public static HashMap<String, Integer> getUserPuzzleHitRate = new HashMap<>();


    public PuzzleSolverContextModule(String userID, String FEN){
        getUserPuzzleFEN.put(userID, FEN);
        getUserFinishLine.put(userID, 0);
        getUserPuzzleHitRate.put(userID, 1);
    }


    public void getPuzzleSolverCard(SlashCommandInteractionEvent slashEvent){
        ChessUtil util = new ChessUtil();
        EmbedBuilder puzzleBuilder = new EmbedBuilder();
        puzzleBuilder.setTitle(slashEvent.getUser().getName() + "'s Puzzle Challenge!");
        puzzleBuilder.setDescription("Its **" + util.getWhichSideToMove(getUserPuzzleFEN.get(slashEvent.getUser().getId())) +
                "** Find the best move! ** use /solve [move ex: e4]** to solve the puzzle! \uD83E\uDDE9 ");
        puzzleBuilder.setImage(util.getImageFromFEN(getUserPuzzleFEN.get(slashEvent.getUser().getId()), false, "green", "alpha"));
        slashEvent.reply("thinking..").setEphemeral(true).queue();
        slashEvent.getChannel().sendMessageEmbeds(puzzleBuilder.build()).queue();
    }

    public void determineTheFinishLine(SlashCommandInteractionEvent slashEvent){
        Board hookBoard = new Board();
        hookBoard.loadFromFen(getUserPuzzleFEN.get(slashEvent.getUser().getId()));

        double eval = Double.parseDouble(StockFish.getEvalForFEN(15, getUserPuzzleFEN.get(slashEvent.getUser().getId())));

        if(eval >= -0.99 && eval <= 0.99){
            getUserPuzzleHitRate.put(slashEvent.getUser().getId(), 4);
            getUserFinishLine.put(slashEvent.getUser().getId(), getUserFinishLine.get(slashEvent.getUser().getId()) + 1);
        }else if (eval >= -2.99 && eval <= 2.99){
            getUserFinishLine.put(slashEvent.getUser().getId(), getUserFinishLine.get(slashEvent.getUser().getId()) + 1);
            getUserPuzzleHitRate.put(slashEvent.getUser().getId(), 2);
        }else{
            getUserFinishLine.put(slashEvent.getUser().getId(), getUserFinishLine.get(slashEvent.getUser().getId()) + 1);
            getUserPuzzleHitRate.put(slashEvent.getUser().getId(), 1);
        }

        
    }

    public void getSolverCard(SlashCommandInteractionEvent slashEvent){

       try{
          if(getUserFinishLine.get(slashEvent.getUser().getId()) < getUserPuzzleHitRate.get(slashEvent.getUser().getId())){
              String userAnswer = Objects.requireNonNull(slashEvent.getOption("sol-answer")).getAsString();

              Board checker = new Board();

              checker.loadFromFen(getUserPuzzleFEN.get(slashEvent.getUser().getId()));

              checker.doMove(userAnswer);

              Board realAnswer = new Board();

              realAnswer.loadFromFen(getUserPuzzleFEN.get(slashEvent.getUser().getId()));

              String bestmove = StockFish.getBestMove(13, getUserPuzzleFEN.get(slashEvent.getUser().getId()));

              realAnswer.doMove(bestmove);


              if(checker.getFen().equalsIgnoreCase(realAnswer.getFen())){
                  if(checker.isMated() || checker.isStaleMate() || checker.isDraw() || checker.isRepetition()){
                      getUserFinishLine.put(slashEvent.getUser().getId(), 0);
                      slashEvent.reply("You have solved the puzzle!").queue();
                  }else{
                      getUserPuzzleFEN.put(slashEvent.getUser().getId(), realAnswer.getFen());
                      String nextMove = StockFish.getBestMove(13, getUserPuzzleFEN.get(slashEvent.getUser().getId()));
                      realAnswer.doMove(nextMove);
                      getUserPuzzleFEN.put(slashEvent.getUser().getId(),realAnswer.getFen());
                      determineTheFinishLine(slashEvent);
                      getPuzzleSolverCard(slashEvent);
                  }
              }else{
                  slashEvent.reply("Incorrect move! Please try again ").queue();
              }
          }else{
              getUserPuzzleFEN.put(slashEvent.getUser().getId(), "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
              getUserFinishLine.put(slashEvent.getUser().getId(), 0);
              getUserPuzzleHitRate.put(slashEvent.getUser().getId(), 1);
              slashEvent.reply("You have solved the puzzle!").queue();
          }


       }catch (Exception e){
           slashEvent.reply("You entered an illegal move! Please try again!").queue();
           System.out.println(e.getMessage());
       }





    }




















}
