package network.user.friends;

import network.user.PreferenceFr;

/**
 * FriendPrefBuilder class to build the friend preferences
 */
public class FriendPrefBuilder {

    private final PreferenceFr player;
    private final PreferenceFr piece;
    private final PreferenceFr opening;
    private final PreferenceFr style;


    public FriendPrefBuilder(PreferenceFr player, PreferenceFr piece, PreferenceFr opening, PreferenceFr style) {
        this.player = player;
        this.piece = piece;
        this.opening = opening;
        this.style = style;
    }

    /**
     * Get the player preference
     *
     * @return the player preference
     */
    public PreferenceFr getPlayer() {
        return player;
    }

    /**
     * Get the piece preference
     *
     * @return the piece preference
     */
    public PreferenceFr getPiece() {
        return piece;
    }

    /**
     * Get the opening preference
     *
     * @return the opening preference
     */
    public PreferenceFr getOpening() {
        return opening;
    }

    /**
     * Get the style preference
     *
     * @return the style preference
     */
    public PreferenceFr getStyle() {
        return style;
    }


}
