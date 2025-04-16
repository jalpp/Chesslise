package discord.handlermodules;

import discord.helpermodules.ButtonHelperModule;
import chariot.Client;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;

public class ButtonContextModule {

    public void handleLogic(ButtonInteractionEvent buttonEvent, Client client) {

        ButtonHelperModule buttonTool = new ButtonHelperModule(buttonEvent, client);

        buttonTool.sendLearnCommand();

        buttonTool.sendPuzzleButtons();

        buttonTool.deleteCurrentMessage();

        buttonTool.sendPlayCommandUI();

        buttonTool.sendPlayCommandFlow();

        buttonTool.sendMoreTimeControls();

        buttonTool.sendPlayingEngineFlow();

        buttonTool.sendChessDBButtonView();

        buttonTool.sendPuzzleDBTheme();

        buttonTool.sendFlipBoard();

    }

}
