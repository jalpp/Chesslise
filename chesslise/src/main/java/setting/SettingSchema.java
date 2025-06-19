package setting;

import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.Optional;

public class SettingSchema {

    private final String userid;
    private final String boardTheme;
    private final String pieceType;
    private final String puzzleDifficulty;

    public SettingSchema(String boardTheme, String pieceType, String userid,String puzzleDifficulty) {
        this.boardTheme = Optional.ofNullable(boardTheme).orElse("blue");
        this.pieceType = Optional.ofNullable(pieceType).orElse("kosal");
        this.userid = userid;
        this.puzzleDifficulty = Optional.ofNullable(puzzleDifficulty).orElse("Medium");
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

    public String getPuzzleDifficulty(){return puzzleDifficulty;}

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
                .addChoice("xkcd", "xkcd")
                .addChoice("pixel", "pixel");
    }

    public static OptionData getPuzzleDifficultyData(){
        return new OptionData(OptionType.STRING,"puzzledifficultylevel","Select difficulty Level", true)
                .addChoice("Easy (Rating: < 1200)","Easy")
                .addChoice("Medium (Rating: 1200-2000)","Medium")
                .addChoice("Hard (Rating: 2000+)","Hard");
    }

}
