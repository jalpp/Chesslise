package network.challenge;

/**
 * Status enum to handle the status of the challenge
 */
public enum Status {


    PENDING,

    ACCEPTED,

    CANCELLED,

    COMPLETED;

    /**
     * Convert the status to a string
     *
     * @return the string for MongoDB Document field
     */
    public String toMongo() {
        switch (this) {
            case PENDING -> {
                return "pending";
            }

            case ACCEPTED -> {
                return "accepted";
            }

            case CANCELLED -> {
                return "cancelled";
            }

            case COMPLETED -> {
                return "completed";
            }
        }

        return null;
    }


}
