package Tests;

import Engine.LiseChessEngine;
import com.github.bhlangonijr.chesslib.Board;
import org.testng.annotations.Test;

import java.util.Random;

public class LiseEngineTests {



    @Test
    void playLiseWithRandomMover(){

        Board b = new Board();
        LiseChessEngine engine = new LiseChessEngine(b);

        while(b.isMated() || b.isDraw() || b.isStaleMate()){
            b.doMove(engine.findBestMove());
            b.doMove(b.legalMoves().get(new Random().nextInt(b.legalMoves().size())));
            System.out.println(b);
        }



    }

















}
