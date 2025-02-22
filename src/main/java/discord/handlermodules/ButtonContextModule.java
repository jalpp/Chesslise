package discord.handlermodules;

import discord.helpermodules.ButtonHelperModule;
import chariot.Client;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;

/**
 * ButtonContextModule class to handle the button context
 */
public class ButtonContextModule {

    public ButtonContextModule() {

    }

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
