package discord.handlermodules;

import abstraction.Context.ContextHandler;
import chesscom.DailyCommandCC;
import chesscom.puzzle;
import discord.helpermodules.BotContextModule;
import discord.helpermodules.ButtonHelperContextModule;
import discord.mainhandler.AntiSpam;
import discord.mainhandler.CommandInfo;
import lichess.DailyCommand;
import lichess.Game;
import chariot.Client;
import com.github.bhlangonijr.chesslib.Board;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.MessageContextInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;


public class ButtonContextModule implements ContextHandler {

    public ButtonContextModule() {

    }

    @Override
    public void handleLogic(MessageContextInteractionEvent context, SlashCommandInteractionEvent slashEvent, ButtonInteractionEvent buttonEvent, ModalInteractionEvent eventModal, CommandAutoCompleteInteractionEvent autoEvent, Client client, Board board, Board blackboard, AntiSpam spam, AntiSpam dailyspam, AntiSpam watchlimit) {
        BotContextModule fish = new BotContextModule();

        CommandInfo commandInfo = new CommandInfo();

        DailyCommand dailyCommand = new DailyCommand(client);

        DailyCommandCC commandCC = new DailyCommandCC();

        puzzle Random_puzzle = new puzzle();

        Game generateChallenge = new Game();

        ButtonHelperContextModule helper = new ButtonHelperContextModule();

        helper.handleLearnCommand(buttonEvent, commandInfo);

        helper.handlePuzzleButtons(buttonEvent, dailyCommand, commandCC, Random_puzzle, fish, client);

        helper.handlePlayCommandUI(buttonEvent);

        helper.handlePlayCommandFlow(buttonEvent, generateChallenge, client);

        helper.handleMoreTimeControls(buttonEvent, generateChallenge, client);

        helper.handlePlayingEngineFlow(buttonEvent, board, blackboard);

    }


}
