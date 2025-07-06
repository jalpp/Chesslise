package discord.helpermodules;

import abstraction.CommandTrigger;
import chariot.Client;
import lichess.Game;
import lichess.ThemePuzzle;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import setting.SettingSchema;
import setting.SettingSchemaModule;

public class SelectHelperModule extends SettingSchemaModule implements CommandTrigger {

    private final StringSelectInteractionEvent stringSelect;
    private final SettingSchema setting = getSettingSchema();

    public SelectHelperModule(StringSelectInteractionEvent stringSelect) {
        super(stringSelect.getUser().getId());
        this.stringSelect = stringSelect;
    }

  // commit this tmr
    private void sendPuzzleThemeCard(String theme){
        ThemePuzzle puzzle = new ThemePuzzle(theme, getSettingSchema().getUserid());
        stringSelect.editMessageEmbeds(puzzle.defineCommandCard(setting).build()).setActionRow(Button.link(puzzle.defineAnalysisBoard(puzzle.definePuzzleFen()), "Analysis Board")).setActionRow(Button.link(puzzle.getGameURL(), "Game")).queue();
    }

    private void sendGameURL(String timeKey, boolean isRated){
         int[] value = Game.GAME_TC.get(timeKey);
         stringSelect.editMessage(Game.generateOpenEndedChallengeURLs(value[0], value[1], isRated, Client.basic())).setActionRow(Button.success("load-again", "↩️ Home")).queue();
    }

    private void sendCoresGameUrl(String timeKey){
        stringSelect.editMessage(Game.generateOpenEndedCoresChallengeURLs(Integer.parseInt(timeKey), Client.basic())).setActionRow(Button.success("load-again", "↩️ Home")).queue();
    }

    @Override
    public void trigger(String commandName) {
       switch (commandName){
           case "puzzle-theme-menu-1", "puzzle-theme-menu-2", "puzzle-theme-menu-3" -> {
               System.out.println(stringSelect.getValues().getFirst());
               sendPuzzleThemeCard(stringSelect.getValues().getFirst());
           }
           case "casmode-game-menu" -> {
               sendGameURL(stringSelect.getValues().getFirst(), false);
           }
           case "ratedmode-game-menu" -> {
               sendGameURL(stringSelect.getValues().getFirst(), true);
           }
           case "cores-game-menu" -> {
               sendCoresGameUrl(stringSelect.getValues().getFirst());
           }
       }
    }
}
