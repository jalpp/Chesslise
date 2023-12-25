package Tests;

import Abstraction.ChessUtil;
import Abstraction.Player.UserObject;
import Abstraction.Player.UserTrophy;
import Lichess.DailyCommand;
import chariot.Client;
import com.github.bhlangonijr.chesslib.Board;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertTrue;

public class LichessTests {


    @Test
    void testUserObject(){
        UserObject userObject = new UserObject("magnus");

        String user = userObject.getUserID();

        assertEquals("magnus", user);

    }



    @Test
    void testUserObject2(){
        UserObject userObject = new UserObject(Client.basic(), "magnus");

        assertEquals(Client.basic().users().byId("noobmasterplayer123").get().id(), userObject.getClient().users().byId("noobmasterplayer123").get().id());
    }


    @Test
    void testChessUtil1(){
        ChessUtil util = new ChessUtil();

        String anzboard = util.getAnalysisBoard("1k3b1r/1nq3pp/1N1rpp2/2p5/1p3P2/1P5P/2PN2P1/R6K w - - 2 28");

        assertEquals("https://lichess.org/analysis/standard/1k3b1r/1nq3pp/1N1rpp2/2p5/1p3P2/1P5P/2PN2P1/R6K_w_-_-_2_28", anzboard);
    }


    @Test
    void testChessUtil2(){
        ChessUtil util = new ChessUtil();

        String another = util.getWhichSideToMove("1k3b1r/1nq3pp/1N1rpp2/2p5/1p3P2/1P5P/2PN2P1/R6K w - - 2 28");

        assertEquals("White to move", another);
    }


    @Test
    void testChessUtil3(){
        ChessUtil util = new ChessUtil();

        String img = util.getImageFromFEN("1k3b1r/1nq3pp/1N1rpp2/2p5/1p3P2/1P5P/2PN2P1/R6K w - - 2 28", false, "brown", "kosal");

        assertEquals("https://lichess1.org/export/fen.gif?fen=" + "1k3b1r/1nq3pp/1N1rpp2/2p5/1p3P2/1P5P/2PN2P1/R6K" + "&color=white&theme=" + "brown" + "&piece=" + "kosal", img);
    }


    @Test
    void testLiPuzzle1(){
        DailyCommand daily = new DailyCommand(Client.basic());

        boolean checksol = daily.checkSolution(daily.getClient().puzzles().dailyPuzzle().get().puzzle().solution().get(0));

        assertTrue(checksol);
    }


    @Test
    void testLiPuzzleFEN(){

        String fen = DailyCommand.getDailyPuzzleFEN(Client.basic());

        Board fenRender = new Board();
        for(String moves: Client.basic().puzzles().dailyPuzzle().get().game().pgn().split(" ")){
            fenRender.doMove(moves);
        }

        assertEquals(fenRender.getFen(), fen);

    }























}
