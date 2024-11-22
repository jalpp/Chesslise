package abstraction;

import net.dv8tion.jda.api.EmbedBuilder;

public interface Puzzle {

    String renderImage(ChessUtil util, String fen);


    String defineSideToMove(ChessUtil util, String fen);


    String defineAnalysisBoard(ChessUtil util, String fen);

    ChessUtil defineUtil();

    String definePuzzleFen();

    EmbedBuilder defineCommandCard();


}
