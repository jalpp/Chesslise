package network.user.friends;

public enum ViewTypeFriend {

    INCOMMING_FRIEND,

    OUTGOING_FRIEND,

    LIST_FRIEND;

    public String toDiscord() {
        switch (this) {
            case LIST_FRIEND -> {
                return "flist";
            }
            case INCOMMING_FRIEND -> {
                return "fin";
            }

            case OUTGOING_FRIEND -> {
                return "fout";
            }
        }
        return null;
    }

    public static ViewTypeFriend getType(String discord) {
        switch (discord) {
            case "flist" -> {
                return ViewTypeFriend.LIST_FRIEND;
            }
            case "fin" -> {
                return ViewTypeFriend.INCOMMING_FRIEND;
            }
            case "fout" -> {
                return ViewTypeFriend.OUTGOING_FRIEND;
            }
        }
        return null;
    }

}
