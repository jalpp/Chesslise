package discord.handlermodules;

import abstraction.HandleContext;
import discord.helpermodules.*;
import discord.mainhandler.CommandInfo;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;


public class SlashContextModule implements HandleContext {

    private final SlashCommandInteractionEvent slashEvent;

    public SlashContextModule(SlashCommandInteractionEvent slashEvent) {
        this.slashEvent = slashEvent;
    }

    @Override
    public void handleLogic() {
        String name = slashEvent.getName();
        EngineHelperModule engineTool = new EngineHelperModule(slashEvent);
        CommandInfo infoTool = new CommandInfo(slashEvent);
        ChessSlashHelperModule chessTool = new ChessSlashHelperModule(slashEvent);
        NetworkHelperModule networkTool = new NetworkHelperModule(slashEvent);
        PuzzleContextHelperModule puzzleTool = new PuzzleContextHelperModule(slashEvent);

        engineTool.trigger(name);
        infoTool.trigger(name);
        chessTool.trigger(name);
        networkTool.trigger(name);
        puzzleTool.trigger(name);

    }

}
