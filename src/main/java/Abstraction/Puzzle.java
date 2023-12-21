package Abstraction;

import Abstraction.ChessUtil;

public interface Puzzle {

    public String renderImage(ChessUtil util, String fen);


    public String defineSideToMove(ChessUtil util, String fen);


    public String defineAnalysisBoard(ChessUtil util, String fen);


}
