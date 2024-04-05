package Engine;

public enum Liquid_Levels {


    BEAST,


    STRONG,


    NOVICE,


    BEGINNER;


    @Override
    public String toString() {
        switch (this) {

            case BEAST -> {
                return "Beast";
            }

            case STRONG -> {
                return "Strong";
            }

            case NOVICE -> {
                return "Novice";
            }

            case BEGINNER -> {
                return "Beginner";
            }

        }

        return "error!";
    }
}
