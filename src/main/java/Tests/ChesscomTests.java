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
