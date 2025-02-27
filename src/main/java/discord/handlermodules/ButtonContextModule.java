package discord.handlermodules;

import discord.helpermodules.ButtonHelperModule;
import chariot.Client;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;

/**
 * ButtonContextModule class to handle the button context
 */
public class ButtonContextModule {

    /**
     * handles logic for button event
     * @param buttonEvent the button event
     * @param client the chariot client
     */
    public void handleLogic(ButtonInteractionEvent buttonEvent, Client client) {

        ButtonHelperModule buttonTool = new ButtonHelperModule(buttonEvent, client);

        buttonTool.sendLearnCommand();

        buttonTool.sendPuzzleButtons();

        buttonTool.sendPlayCommandUI();

        buttonTool.sendPlayCommandFlow();

        buttonTool.sendMoreTimeControls();

        buttonTool.sendPlayingEngineFlow();

        buttonTool.sendChessDBButtonView();

        buttonTool.sendPuzzleDBTheme();

    }


}
