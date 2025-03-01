package network.challenge;

public enum Status {


    PENDING,

    ACCEPTED,

    CANCELLED,

    COMPLETED;

    
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
