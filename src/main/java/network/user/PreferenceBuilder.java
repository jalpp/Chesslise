package network.user;

import network.challenge.Status;

/**
 * PreferenceBuilder class to build the preferences
 */
public class PreferenceBuilder {

    /**
     * Build the platform preference
     *
     * @param tag the tag for the platform
     * @return the platform preference
     */
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

    /**
     * Build the status preference
     *
     * @param tag the tag for the status
     * @return the status preference
     */
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

    /**
     * Build the time control preference
     *
     * @param tag the tag for the time control
     * @return the time control preference
     */
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

    /**
     * Build the player preference
     *
     * @param tag the tag for the player
     * @return the player preference
     */
    public static PreferenceFr playerBuilder(String tag) {
        switch (tag) {
            case "mag" -> {
                return PreferenceFr.MAGNUS;
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

    /**
     * Build the piece preference
     *
     * @param tag the tag for the piece
     * @return the piece preference
     */
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

    /**
     * Build the opening preference
     *
     * @param tag the tag for the opening
     * @return the opening preference
     */
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

    /**
     * Build the style preference
     *
     * @param tag the tag for the style
     * @return the style preference
     */
    public static PreferenceFr styleBuilder(String tag) {
        switch (tag) {
            case "agg" -> {
                return PreferenceFr.AGGRESSIVE;
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
