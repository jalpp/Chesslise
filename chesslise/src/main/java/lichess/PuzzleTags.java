package lichess;

import net.dv8tion.jda.api.interactions.components.selections.SelectOption;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PuzzleTags {
    public static final Map<String, String> CHESS_TAGS = new HashMap<>() {{
        put("advancedPawn", "Advanced Pawn");
        put("advantage", "Advantage");
        put("anastasiaMate", "Anastasia's Mate");
        put("arabianMate", "Arabian Mate");
        put("attackingF2F7", "Attacking f2/f7");
        put("attraction", "Attraction");
        put("backRankMate", "Back Rank Mate");
        put("bishopEndgame", "Bishop Endgame");
        put("bodenMate", "Boden Mate");
        put("capturingDefender", "Capturing Defender");
        put("castling", "Castling");
        put("clearance", "Clearance");
        put("crushing", "Crushing");
        put("defensiveMove", "Defensive Move");
        put("deflection", "Deflection");
        put("discoveredAttack", "Discovered Attack");
        put("doubleBishopMate", "Double Bishop Mate");
        put("doubleCheck", "Double Check");
        put("dovetailMate", "Dovetail Mate");
        put("endgame", "Endgame");
        put("enPassant", "En Passant");
        put("equality", "Equality");
        put("exposedKing", "Exposed King");
        put("fork", "Fork");
        put("hangingPiece", "Hanging Piece");
        put("hookMate", "Hook Mate");
        put("interference", "Interference");
        put("intermezzo", "Intermezzo");
        put("killBoxMate", "Kill Box Mate");
        put("kingsideAttack", "Kingside Attack");
        put("knightEndgame", "Knight Endgame");
        put("long", "Long");
        put("master", "Master");
        put("masterVsMaster", "Master vs Master");
        put("mate", "Mate");
        put("mateIn1", "Mate In 1");
        put("mateIn2", "Mate In 2");
        put("mateIn3", "Mate In 3");
        put("mateIn4", "Mate In 4");
        put("mateIn5", "Mate In 5");
        put("middlegame", "Middlegame");
        put("oneMove", "One Move");
        put("opening", "Opening");
        put("pawnEndgame", "Pawn Endgame");
        put("pin", "Pin");
        put("promotion", "Promotion");
        put("queenEndgame", "Queen Endgame");
        put("queenRookEndgame", "Queen Rook Endgame");
        put("queensideAttack", "Queenside Attack");
        put("quietMove", "Quiet Move");
        put("rookEndgame", "Rook Endgame");
        put("sacrifice", "Sacrifice");
        put("short", "Short");
        put("skewer", "Skewer");
        put("smotheredMate", "Smothered Mate");
        put("superGM", "Super GM");
        put("trappedPiece", "Trapped Piece");
        put("underPromotion", "Under Promotion");
        put("veryLong", "Very Long");
        put("vukovicMate", "Vukovic's Mate");
        put("xRayAttack", "X Ray Attack");
        put("zugzwang", "Zugzwang");
    }};


    public static List<String> getTagNames(){
        return CHESS_TAGS.keySet().stream().toList();
    }

    public static Map<String, String> getPuzzleTags(){
        return CHESS_TAGS;
    }

    private static ArrayList<SelectOption> getBatchOptions(int start, int end){
        ArrayList<SelectOption> options = new ArrayList<>();
        for (int i = start; i < end; i++){
            options.add(SelectOption.of(getPuzzleTags().get(getTagNames().get(i)), getTagNames().get(i)).withDescription(getPuzzleTags().get(getTagNames().get(i))));
        }

        return options;
    }

    public static ArrayList<ArrayList<SelectOption>> getPuzzleSelectMenu(){
        ArrayList<ArrayList<SelectOption>> allOptions = new ArrayList<>();
        allOptions.add(getBatchOptions(0, 25));
        allOptions.add(getBatchOptions(25, 50));
        allOptions.add(getBatchOptions(50, 62));
        return allOptions;
    }
}