package discord.helpermodules.chess;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utility class for parsing PGN (Portable Game Notation) text
 */
public class PgnParser {

    /**
     * Extract moves from PGN text using simple string manipulation
     */
    public static List<String> extractMoves(String pgnText) {
        // Remove headers like [White "..."], [Black "..."], etc.
        String cleaned = pgnText.replaceAll("\\[.*?\\]", "").trim();

        // Remove game result annotations like 1-0, 0-1, ½-½, *
        cleaned = cleaned.replaceAll("(1-0|0-1|1/2-1/2|½-½|\\*)", "").trim();

        // Remove move numbers including both "1." and "29..." formats
        cleaned = cleaned.replaceAll("\\d+\\.{1,3}", "").trim();

        // Split by whitespace and filter out empty strings
        List<String> moves = new ArrayList<>();
        String[] tokens = cleaned.split("\\s+");

        for (String token : tokens) {
            if (!token.isEmpty()) {
                moves.add(token);
            }
        }

        return moves;
    }

    /**
     * Extract moves from PGN text using regex pattern matching
     */
    public static List<String> extractMovesAlternative(String pgnText) {
        // Remove headers like [White "..."], [Black "..."], etc.
        String cleaned = pgnText.replaceAll("\\[.*?\\]", "").trim();

        // Remove game result annotations like 1-0, 0-1, ½-½, *
        cleaned = cleaned.replaceAll("(1-0|0-1|1/2-1/2|½-½|\\*)", "").trim();

        List<String> moves = new ArrayList<>();

        // Pattern to match move notation (handles check +, checkmate #, captures x,
        // etc.)
        Pattern movePattern = Pattern
                .compile("\\b([KQRBN]?[a-h]?[1-8]?x?[a-h][1-8](?:=[QRBN])?[+#]?|O-O(?:-O)?[+#]?)\\b");
        Matcher matcher = movePattern.matcher(cleaned);

        while (matcher.find()) {
            moves.add(matcher.group(1));
        }

        return moves;
    }
}
