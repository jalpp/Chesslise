
import chariot.Client;

public class ComputeEvalSummary {

    // SOURCE CODE FROM: https://github.com/tors42/jbang-chariot/blob/main/scripts/evalsummary.java
    //JAVA 17+
    
    
      public String getEvalSummary(String gameID){
          String gameid = gameID;
          String result = "";
          var client = Client.basic();

          var game = client.games().byGameId(gameid).get();

          if (game.analysis().isEmpty()) {
              result += """
                The game has not been analyzed. Try with an analyzed game.

                White (%s)
                 Inaccuracies: ?
                 Mistakes:     ?
                 Blunders:     ?
                 ACPL:         ?

                Black (%s)
                 Inaccuracies: ?
                 Mistakes:     ?
                 Blunders:     ?
                 ACPL:         ?
                 """.formatted(
                      game.players().white().name(),
                      game.players().black().name());
              return result;
          }

          var wa = game.players().white().analysis().get();
          var ba = game.players().black().analysis().get();

          result +="""
                White (%s)
                 Inaccuracies: %2d
                 Mistakes:     %2d
                 Blunders:     %2d
                 ACPL:         %3d

                Black (%s)
                 Inaccuracies: %2d
                 Mistakes:     %2d
                 Blunders:     %2d
                 ACPL:         %3d
                """.formatted(
                  game.players().white().name(),
                  wa.inaccuracy(), wa.mistake(), wa.blunder(), wa.acpl(),
                  game.players().black().name(),
                  ba.inaccuracy(), ba.mistake(), ba.blunder(), ba.acpl()
          );
          
          return result;
      }

       
}
