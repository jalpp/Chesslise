package network.user.friends;

import network.user.PreferenceFr;

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

    public PreferenceFr getPlayer() {
        return player;
    }

    public PreferenceFr getPiece() {
        return piece;
    }

    public PreferenceFr getOpening() {
        return opening;
    }

    public PreferenceFr getStyle() {
        return style;
    }

}
