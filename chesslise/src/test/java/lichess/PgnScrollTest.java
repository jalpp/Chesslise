package lichess;

import org.junit.jupiter.api.Test;
import java.util.HashMap;
import static org.junit.jupiter.api.Assertions.*;

public class PgnScrollTest {

    private final String sampleMoveList = "e4 e5 Nf3 Nc6 Bb5 a6";
    private final String samplePgn = "[Event \"Test Game\"]\n[White \"Player1\"]\n[Black \"Player2\"]\n[Result \"1-0\"]\n\n1. e4 e5 2. Nf3 Nc6 3. Bb5 a6 1-0";

    @Test
    void testGetFenForMoveIndex() {
        // Test starting position
        String startFen = PgnScroll.getFenForMoveIndex(sampleMoveList, 0);
        assertEquals("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1", startFen);

        // Test after first move
        String afterFirstMove = PgnScroll.getFenForMoveIndex(sampleMoveList, 1);
        assertNotNull(afterFirstMove);
        assertNotEquals(startFen, afterFirstMove);

        // Test with highlighted moves
        String highlightedMoves = "e4 **>e5** Nf3 Nc6";
        String fenWithHighlight = PgnScroll.getFenForMoveIndex(highlightedMoves, 2);
        assertNotNull(fenWithHighlight);
    }

    @Test
    void testHighlightCurrentMove() {
        // Test valid index
        String highlighted = PgnScroll.highlightCurrentMove(sampleMoveList, 1);
        assertTrue(highlighted.contains("**>e5**"));

        // Test first move
        String firstMove = PgnScroll.highlightCurrentMove(sampleMoveList, 0);
        assertTrue(firstMove.contains("**>e4**"));

        // Test out of bounds
        String outOfBounds = PgnScroll.highlightCurrentMove(sampleMoveList, 10);
        assertEquals("e4 e5 Nf3 Nc6 Bb5 a6", outOfBounds);

        // Test negative index
        String negative = PgnScroll.highlightCurrentMove(sampleMoveList, -1);
        assertEquals("e4 e5 Nf3 Nc6 Bb5 a6", negative);
    }


    @Test
    void testFormatPGN() {
        // Test valid PGN
        String formatted = PgnScroll.formatPGN(samplePgn);
        assertNotNull(formatted);
        assertTrue(formatted.contains("[Event \"Test Game\"]"));
        assertTrue(formatted.contains("1. e4 e5"));

        // Test null and empty inputs
        assertEquals("", PgnScroll.formatPGN(null));
        assertEquals("", PgnScroll.formatPGN(""));
        assertEquals("", PgnScroll.formatPGN("   "));

        // Test headers only
        String headersOnly = "[Event \"Test\"] [White \"Player1\"]";
        String headerResult = PgnScroll.formatPGN(headersOnly);
        assertTrue(headerResult.contains("[Event \"Test\"]"));
        assertTrue(headerResult.contains("[White \"Player1\"]"));

        // Test moves only
        String movesOnly = "1. e4 e5 2. Nf3 Nc6";
        String movesResult = PgnScroll.formatPGN(movesOnly);
        assertTrue(movesResult.contains("1. e4 e5"));
    }

    @Test
    void testGetMovesString() {
        // Test valid PGN
        String moves = PgnScroll.getMovesString(samplePgn);
        assertNotNull(moves);
        assertTrue(moves.contains("e4"));
        assertTrue(moves.contains("e5"));
        assertTrue(moves.trim().endsWith("a6"));


    }

    @Test
    void testGetPgnTimeControl() {
        // Test null time control
        assertEquals("N/A", PgnScroll.getPgnTimeControl(null));
    }

    @Test
    void testGetPgnHeaders() {
        // Test valid PGN
        HashMap<String, String> headers = PgnScroll.getPgnHeaders(samplePgn);
        assertNotNull(headers);
        assertTrue(headers.containsKey("Event:"));
        assertTrue(headers.containsKey("Players:"));
        assertTrue(headers.containsKey("Result:"));
        assertTrue(headers.get("Players:").contains("Player1"));
        assertTrue(headers.get("Players:").contains("Player2"));

        // Test invalid inputs

        assertTrue(PgnScroll.getPgnHeaders("").isEmpty());
        assertTrue(PgnScroll.getPgnHeaders(null).isEmpty());

        // Test minimal PGN
        String minimalPgn = "[Event \"Test\"]\n[White \"Player1\"]\n[Black \"Player2\"]\n\n1. e4 1-0";
        HashMap<String, String> minimalHeaders = PgnScroll.getPgnHeaders(minimalPgn);
        assertTrue(minimalHeaders.get("Event:").contains("Test"));
        assertTrue(minimalHeaders.get("Players:").contains("Player1 vs Player2"));

        // Test anonymous players
        String anonPgn = "[Event \"Test\"]\n\n1. e4 1-0";
        HashMap<String, String> anonHeaders = PgnScroll.getPgnHeaders(anonPgn);
        System.out.println(anonHeaders);
        assertTrue(anonHeaders.get("Players:").contains("null"));
    }

    @Test
    void testEdgeCases() {
        // Test special chess notation
        String specialMoves = "O-O O-O-O Nf3+ Qh5# Rxe8+";
        String result = PgnScroll.highlightCurrentMove(specialMoves, 2);
        assertTrue(result.contains("**>Nf3+**"));

        // Test very long move list
        StringBuilder longMoves = new StringBuilder();
        longMoves.append("e4 e5 ".repeat(20));
        String moves = longMoves.toString().trim();
        String highlighted = PgnScroll.highlightCurrentMove(moves, 10);
        assertNotNull(highlighted);
        assertTrue(highlighted.contains("**>"));

        // Test existing highlight removal
        String alreadyHighlighted = "e4 **>e5** Nf3 Nc6";
        String newHighlight = PgnScroll.highlightCurrentMove(alreadyHighlighted, 2);
        assertTrue(newHighlight.contains("**>Nf3**"));
        assertFalse(newHighlight.contains("**>e5**"));
    }
}