package setting;

import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

public class SettingSchema {

    private final String userid;
    private final String boardTheme;
    private final String pieceType;

    public SettingSchema(String boardTheme, String pieceType, String userid) {
        this.boardTheme = boardTheme;
        this.pieceType = pieceType;
        this.userid = userid;
    }

    public String getUserid() {
        return userid;
    }

    public String getBoardTheme() {
        return boardTheme;
    }

    public String getPieceType() {
        return pieceType;
    }

    public static OptionData getBoardThemeData() {
        return new OptionData(OptionType.STRING, "theme", "Select board theme", true)
                .addChoice("brown", "brown")
                .addChoice("blue", "blue")
                .addChoice("purple", "purple")
                .addChoice("green", "green");
    }

    public static OptionData getPieceTypeData() {
        return new OptionData(OptionType.STRING, "piecetype", "Select piece type", true)
                .addChoice("kosal", "kosal")
                .addChoice("cburnett", "cburnett")
                .addChoice("horsey", "horsey")
                .addChoice("pixel", "pixel");
    }

}
