package Tests;

import Abstraction.ChessPuzzle;
import Abstraction.Puzzle;
import Chesscom.CCProfile;
import Chesscom.DailyCommandCC;
import Chesscom.puzzle;
import io.github.sornerol.chess.pubapi.client.DailyPuzzleClient;
import io.github.sornerol.chess.pubapi.client.PlayerClient;
import io.github.sornerol.chess.pubapi.exception.ChessComPubApiException;
import net.dv8tion.jda.api.EmbedBuilder;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class ChesscomTests {


    @Test
    public void testDailyPuzzleImage(){

        DailyCommandCC cc = new DailyCommandCC();

        String getPuzzle = cc.getPuzzle();

        assertEquals(cc.getPuzzle(), getPuzzle);

    }

    @Test
    public void testDailyPuzzleFEN(){

        DailyCommandCC cc = new DailyCommandCC();

        String fen = cc.getFEN();

        assertEquals(cc.getFEN(), fen);

    }


    @Test
    public void testDailyPuzzleSide(){

        DailyCommandCC cc = new DailyCommandCC();

        String side = cc.getPuzzleSideToMove();

        assertEquals(cc.getPuzzleSideToMove(), side);

    }


    @Test
    public void testRandomPuzzle(){
        puzzle puzzle = new puzzle();

        String img = puzzle.getPuzzle();

        assertEquals(puzzle.getPuzzle(), img);
    }


    @Test
    public void testRandomPuzzleSide(){
        puzzle p = new puzzle();

        String side = p.getPuzzleSideToMove();

        assertEquals(p.getPuzzleSideToMove(), side);
    }


    @Test
    public void testPuzzleClient() throws ChessComPubApiException, IOException {

        ChessPuzzle p = new puzzle();

        DailyPuzzleClient client = p.getDailyPuzzleClient();

        assertEquals(new DailyPuzzleClient().getTodaysDailyPuzzle().toString(), client.getTodaysDailyPuzzle().toString() );


    }
    
    
    @Test
    public void testUserProfile() throws ChessComPubApiException, IOException {
        CCProfile pro = new CCProfile("hikaru");

        String check = new PlayerClient().getPlayerByUsername("hikaru").toString();
        
        assertEquals(check, pro.getPlayerClient().getPlayerByUsername(pro.getUserID()).toString());
        
    }
    
    
    @Test
    public void testUserProfileTwo(){
        
        CCProfile pro = new CCProfile("hikaru");
        
        String name = "hikaru";
        
        assertEquals(name, pro.getUserID());
        
    }
    
    
    
    
    
    
    
    
    
}
