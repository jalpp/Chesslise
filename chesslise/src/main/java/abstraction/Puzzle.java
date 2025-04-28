package abstraction;

import net.dv8tion.jda.api.EmbedBuilder;
import setting.SettingSchema;

import java.awt.*;

public interface Puzzle {

    String definePuzzleFen();

    EmbedBuilder defineCommandCard(SettingSchema schema);

    String definePuzzleLogo();

    String definePuzzleTitle();

    Color defineEmbedColor();

    String definePuzzleDescription();

}
