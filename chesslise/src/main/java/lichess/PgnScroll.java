package lichess;

import abstraction.ChessUtil;
import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.game.TimeControl;
import com.github.bhlangonijr.chesslib.pgn.PgnHolder;
import com.github.bhlangonijr.chesslib.game.Game;
import com.github.bhlangonijr.chesslib.move.Move;
import discord.mainhandler.Thumbnail;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.interactions.components.ItemComponent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import setting.SettingSchema;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PgnScroll {


    public static String getFenForMoveIndex(String moveList, int index){

        if(index == 0){
            return "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
        }
        String[] moves = moveList.replace("**>", "").replace("**", "").split(" ");
        Board board = new Board();

        for(int i = 0; i < index; i++){
            board.doMove(moves[i]);
        }

        return board.getFen();
    }

    public static String highlightCurrentMove(String moveList, int index){

        String[] moves = moveList.replace("**>","").replace("**", "").split(" ");

        if(index < 0 || index > moves.length){
            return String.join(" ", moves);
        }
        moves[index] = "**>"  + moves[index] + "**";

        return String.join(" ", moves);
    }


    public static EmbedBuilder PgnViewBuilder(String moveList, Integer nextMove, String side, SettingSchema setting, List<MessageEmbed.Field> gameInfo){
        EmbedBuilder builder = new EmbedBuilder();
        ChessUtil util = new ChessUtil();

        builder.setTitle("View PGN");
        builder.setThumbnail(Thumbnail.getChessliseLogo());
        builder.setDescription(highlightCurrentMove(Objects.requireNonNull(moveList), nextMove - 1));
        builder.addField("move#", String.valueOf(nextMove), true);
        builder.addField("side", Objects.requireNonNull(side), true);

        for(MessageEmbed.Field field: gameInfo){
            builder.addField(field);
        }

        String s = util.getPgnView(getFenForMoveIndex(Objects.requireNonNull(moveList), nextMove), side, setting.getBoardTheme(), setting.getPieceType());
        builder.setImage(s);

        return builder;
    }

    public static ItemComponent[] sendPgnActionsOptions(String moveList, int nextMove){
        ItemComponent[] components = new ItemComponent[5];

        components[0] = nextMove - 2 <= 0 ? Button.secondary("pgn-prev-2x", "⏮\uFE0F").asDisabled() : Button.secondary("pgn-prev-2x", "⏮\uFE0F").asEnabled();
        components[1] = nextMove <= 0 ? Button.secondary("pgn-prev", "◀\uFE0F").asDisabled() : Button.secondary("pgn-prev", "◀\uFE0F").asEnabled();
        components[2] = nextMove >= moveList.split(" ").length ? Button.success("pgn-next", "▶\uFE0F").asDisabled() : Button.success("pgn-next", "▶\uFE0F").asEnabled();
        components[3] = nextMove + 2 >= moveList.split(" ").length ? Button.success("pgn-next-2x", "⏭\uFE0F").asDisabled() : Button.success("pgn-next-2x", "⏭\uFE0F").asEnabled();
        components[4] = Button.primary("pgn-flip", "Flip board");

        return components;
    }


    public static String formatPGN(String pgnString) {
        if (pgnString == null || pgnString.trim().isEmpty()) {
            return "";
        }

        StringBuilder formatted = new StringBuilder();

        // Pattern to match PGN headers [Key "Value"]
        Pattern headerPattern = Pattern.compile("\\[([^\\]]+)\\]");

        // Split the input into tokens while preserving headers
        String[] tokens = pgnString.split("\\s+");

        boolean inHeader = false;
        StringBuilder currentHeader = new StringBuilder();

        for (String token : tokens) {
            if (token.startsWith("[")) {
                // Start of a header
                if (inHeader) {
                    // Close previous header if it wasn't closed properly
                    formatted.append(currentHeader.toString().trim()).append("\n");
                }
                inHeader = true;
                currentHeader = new StringBuilder();
                currentHeader.append(token);

                if (token.endsWith("]")) {
                    // Complete header in one token
                    formatted.append(currentHeader.toString()).append("\n");
                    inHeader = false;
                    currentHeader = new StringBuilder();
                } else {
                    currentHeader.append(" ");
                }
            } else if (inHeader) {
                // Continue building the header
                currentHeader.append(token);
                if (token.endsWith("]")) {
                    // End of header
                    formatted.append(currentHeader.toString()).append("\n");
                    inHeader = false;
                    currentHeader = new StringBuilder();
                } else {
                    currentHeader.append(" ");
                }
            } else {
                // We're in the moves section
                if (formatted.length() > 0 && !formatted.toString().endsWith("\n\n")) {
                    // Add blank line after headers before moves
                    formatted.append("\n");
                }
                break;
            }
        }

        // Handle any remaining header
        if (inHeader && currentHeader.length() > 0) {
            formatted.append(currentHeader.toString().trim()).append("\n");
        }

        // Now process the moves part
        String remainingText = pgnString;

        // Remove all headers from the remaining text
        Matcher headerMatcher = headerPattern.matcher(remainingText);
        while (headerMatcher.find()) {
            remainingText = remainingText.replace(headerMatcher.group(), "");
        }

        // Clean up the moves text
        remainingText = remainingText.trim().replaceAll("\\s+", " ");

        // Add the moves with proper line wrapping (80 characters per line)
        if (!remainingText.isEmpty()) {
            formatted.append(wrapText(remainingText, 80));
        }

        return formatted.toString();
    }


    private static String wrapText(String text, int lineLength) {
        if (text.length() <= lineLength) {
            return text + "\n";
        }

        StringBuilder wrapped = new StringBuilder();
        String[] words = text.split("\\s+");
        int currentLineLength = 0;

        for (String word : words) {
            if (currentLineLength + word.length() + 1 > lineLength && currentLineLength > 0) {
                wrapped.append("\n");
                currentLineLength = 0;
            }

            if (currentLineLength > 0) {
                wrapped.append(" ");
                currentLineLength++;
            }

            wrapped.append(word);
            currentLineLength += word.length();
        }

        wrapped.append("\n");
        return wrapped.toString();
    }

    public static String getMovesString(String Pgn){

        StringBuilder builder = new StringBuilder();
        PgnHolder pgnHolder = new PgnHolder(null);
        pgnHolder.loadPgn(formatPGN(Pgn));

        Game game = pgnHolder.getGames().get(0);

        List<Move> moves = game.getHalfMoves();

        for (Move move : moves) {
            builder.append(move.getSan()).append(" ");
        }

        return builder.toString();
    }

    public static String getPgnTimeControl(TimeControl tcInfo){
        String tcInfos = tcInfo != null ? tcInfo.toPGNString() : "N/A";

        if(tcInfos.contains("\\+")){
            String[] tcInfolist = tcInfos.split("\\+");
            return Integer.parseInt(tcInfolist[0]) / 60 + "+" + tcInfolist[1];
        }

        if(!tcInfos.equalsIgnoreCase("n/a")){
            return Integer.parseInt(tcInfos) / 60 + "+" + "0";
        }

        return "N/A";
    }


    public static HashMap<String, String> getPgnHeaders(String Pgn){
        try{
            PgnHolder pgnHolder = new PgnHolder(null);
            pgnHolder.loadPgn(formatPGN(Pgn));

            Game game = pgnHolder.getGames().getFirst();
            HashMap<String, String> map = new HashMap<>();


            String whitePlayer = game.getWhitePlayer() != null ? game.getWhitePlayer().getName() : "Anon";
            String blackPlayer = game.getBlackPlayer() != null ? game.getBlackPlayer().getName() : "Anon";
            String players = whitePlayer + " vs " + blackPlayer;

            map.put("Event:", Objects.requireNonNullElse(game.getRound().getEvent().getName(), "N/A"));
            map.put("Opening:", Objects.requireNonNullElse(game.getOpening(), "N/A"));
            map.put("TimeControl:", getPgnTimeControl(game.getRound().getEvent().getTimeControl()));
            map.put("Players:", players);
            map.put("Result:", Objects.requireNonNullElse(game.getResult().getDescription(), "Unknown"));

            return map;

        }catch (Exception e){
            System.out.println(e.getMessage());
            return new HashMap<>();
        }

    }


}