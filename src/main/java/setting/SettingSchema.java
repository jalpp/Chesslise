package setting;

import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

/**
 * The setting schema that represents how a setting looks like
 */
public class SettingSchema {

    private final String userid;
    private final String boardTheme;
    private final String pieceType;

    public SettingSchema(String boardTheme, String pieceType, String userid) {
        this.boardTheme = boardTheme;
        this.pieceType = pieceType;
        this.userid = userid;
    }

    /**
     * gets the user id
     * @return the user id
     */
    public String getUserid() {
        return userid;
    }

    /**
     * gets the board theme
     * @return the theme
     */
    public String getBoardTheme() {
        return boardTheme;
    }

    /**
     * gets the piece type
     * @return the peice type
     */
    public String getPieceType() {
        return pieceType;
    }

    /**
     * gets the board theme option Data
     * @return the Discord option data
     */
    public static OptionData getBoardThemeData(){
        return new OptionData(OptionType.STRING, "theme", "Select board theme", true)
                .addChoice("brown", "brown")
                .addChoice("blue", "blue")
                .addChoice("purple", "purple")
                .addChoice("green", "green");
    }

    /**
     * gets the piece type option data
     * @return the Discord option data
     */
    public static OptionData getPieceTypeData(){
        return new OptionData(OptionType.STRING, "piecetype", "Select piece type", true)
                .addChoice("kosal", "kosal")
                .addChoice("cburnett", "cburnett")
                .addChoice("horsey", "horsey")
                .addChoice("pixel", "pixel");
    }

}
