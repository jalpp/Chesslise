package Discord.HandlerModules;

import Abstraction.Context.ContextHandler;
import Discord.HelperModules.ButtonHelperContextModule;
import Discord.MainHandler.AntiSpam;
import Discord.MainHandler.CommandInfo;
import Lichess.DailyCommand;
import Lichess.Game;
import chariot.Client;
import com.github.bhlangonijr.chesslib.Board;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.MessageContextInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;


public class ButtonContextModule implements ContextHandler {
    public ButtonContextModule() {

    }

    @Override
    public void handleLogic(MessageContextInteractionEvent context, SlashCommandInteractionEvent slashEvent, ButtonInteractionEvent buttonEvent, ModalInteractionEvent eventModal, Client client, Board board, Board blackboard, AntiSpam spam, AntiSpam dailyspam, AntiSpam watchlimit) {


        CommandInfo commandInfo = new CommandInfo();

        DailyCommand dailyCommand = new DailyCommand(client);

        Game generateChallenge = new Game();

        ButtonHelperContextModule helper = new ButtonHelperContextModule();

        helper.handleLearnCommand(buttonEvent, commandInfo);

        helper.handlePuzzleButtons(buttonEvent, client, dailyCommand);

        helper.handlePlayCommandUI(buttonEvent);

        helper.handlePlayCommandFlow(buttonEvent, generateChallenge, client);

        helper.handleMoreTimeControls(buttonEvent, generateChallenge, client);

        helper.handlePlayingEngineFlow(buttonEvent, board, blackboard);

        helper.handlePlayCommandFriendChallenge(buttonEvent);
    }


}

