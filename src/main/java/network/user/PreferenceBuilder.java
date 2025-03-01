package network.user;

import network.challenge.Status;


public class PreferenceBuilder {

    
    public static PreferencePl platformBuilder(String tag) {
        switch (tag) {
            case "lichess" -> {
                return PreferencePl.LICHESS;
            }

            case "unauthlichess" -> {
                return PreferencePl.UNAUTH_LICHESS;
            }

            case "allplatform" -> {
                return PreferencePl.ALL;
            }
        }

        return null;
    }

    
    public static Status statusBuilder(String tag) {
        switch (tag) {
            case "pending" -> {
                return Status.PENDING;
            }
            case "accepted" -> {
                return Status.ACCEPTED;
            }
            case "cancelled" -> {
                return Status.CANCELLED;
            }
            case "completed" -> {
                return Status.COMPLETED;
            }
        }
        return null;
    }

    public static PreferenceTc tcBuilder(String tag) {
        switch (tag) {
            case "classical" -> {
                return PreferenceTc.CLASSICAL;
            }
            case "rapid" -> {
                return PreferenceTc.RAPID;
            }
            case "blitz" -> {
                return PreferenceTc.BLITZ;
            }
            case "bullet" -> {
                return PreferenceTc.BULLET;
            }
        }
        return null;
    }

    
    public static PreferenceFr playerBuilder(String tag) {
        switch (tag) {
            case "mag" -> {
                return PreferenceFr.MAGNUS; // the goat
            }
            case "fab" -> {
                return PreferenceFr.FABI;
            }
            case "din" -> {
                return PreferenceFr.DING;
            }
            case "guk" -> {
                return PreferenceFr.GUKESH; 
            }
        }
        return null;
    }

    
    public static PreferenceFr pieceBuilder(String tag) {
        switch (tag) {
            case "bis" -> {
                return PreferenceFr.BISHOP;
            }
            case "paw" -> {
                return PreferenceFr.PAWN;
            }
            case "kni" -> {
                return PreferenceFr.KNIGHT;
            }
            case "roo" -> {
                return PreferenceFr.ROOK;
            }
            case "kin" -> {
                return PreferenceFr.KING;
            }
            case "que" -> {
                return PreferenceFr.QUEEN;
            }
        }
        return null;
    }

    // note this openings are not my opening rep so please don't spy here to prep against me, even if you do you would lose 
    public static PreferenceFr openingBuilder(String tag) {
        switch (tag) {
            case "qge" -> {
                return PreferenceFr.QUEENS_GAMBIT;
            }
            case "kge" -> {
                return PreferenceFr.KINGS_GAMBIT;
            }
            case "sic" -> {
                return PreferenceFr.SICILIAN;
            }
            case "kid" -> {
                return PreferenceFr.KID;
            }
        }

        return null;
    }

    
    public static PreferenceFr styleBuilder(String tag) {
        switch (tag) {
            case "agg" -> {
                return PreferenceFr.AGGRESSIVE; // fyi my style, good luck against my f4 breaks
            }
            case "pos" -> {
                return PreferenceFr.POSITIONAL;
            }
            case "tac" -> {
                return PreferenceFr.TACTICAL;
            }
            case "def" -> {
                return PreferenceFr.DEFENSIVE;
            }
        }
        return null;
    }


}
