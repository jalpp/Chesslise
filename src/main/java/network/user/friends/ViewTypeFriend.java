package network.user.friends;

/**
 * ViewTypeFriend enum to handle the view type of the friend
 */
public enum ViewTypeFriend {


    INCOMMING_FRIEND,

    OUTGOING_FRIEND,

    LIST_FRIEND;

    /**
     * Convert the view type to a string
     *
     * @return the string for Discord option
     */
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

    /**
     * Get the type of the view
     *
     * @param discord the discord option
     * @return the view type
     */
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
