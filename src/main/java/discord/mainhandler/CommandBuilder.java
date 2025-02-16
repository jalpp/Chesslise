package discord.mainhandler;

import net.dv8tion.jda.api.interactions.IntegrationType;
import net.dv8tion.jda.api.interactions.InteractionContextType;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;
import network.user.PreferenceFr;
import network.user.PreferencePl;
import network.user.PreferenceTc;

/**
 * CommandBuilder class to handle the building of commands on JDA
 */
public class CommandBuilder {

    private final CommandListUpdateAction action;

    private static final String[] COMMANDS = {"help", "play", "profilecc", "watch", "resetboard", "learnchess", "disconnect", "pairchallenge", "pairchallengenetwork", "seekchallenge", "findfriend"};

    private static final String[] COMMANDS_DESC = {"View Chesslise command info",
            "Play live chess games",
            "View Chess.com profile",
            "Watch Lichess games",
            "Reset chess engine board for use",
            "Learn chess rules",
            "Disconnect from CSSN",
            "Attempt to find a challenge in Chesslise network",
            "Attempt to send a challenge in your friend network",
            "Create a challenge and seek for others to accept it",
            "Find a new friend within your network or globally"};

    private static final String[][] COMMAND_SINGLE_OPTION = {
            {"chessdb", "View chessdb eval of a position useful for openings/middelgame fens", "paste-fen", "Enter fen to be analyzed"},
            {"move", "make a move against engine", "play-move", "input chess move"},
            {"fen", "view a chess position by providing the FEN", "input-fen", "Input chess fen"},
            {"cancelchallenge", "cancel a challenge with id", "challid", "provide the challenge id"},
            {"completechallenge", "complete a challenge with id", "cchallid", "provide completed challenge id"},
            {"sendfriendrequest", "Send friend request by providing target username", "frienduser", "provide target username"},
            {"acceptfriendrequest", "Accept friend request by providing target friend id", "friendid", "provide target id"},
            {"cancelfriendreqeust", "Cancel an incoming friend request by providing discord id", "cancelid", "provide target id"},
            {"removefriend", "Remove a friend from friend list by providing discord id", "removeid", "provide friend discord username"},
            {"blockfriend", "Block a friend who is not being friendly by providing id", "blockid", "provide friend discord username"}};

    private final String[] COMMAND_MESSAGE = {
            "Lichess Daily Puzzle",
            "Play Chess",
            "Chess.com Daily Puzzle"
    };

    public CommandBuilder(CommandListUpdateAction action) {
        this.action = action;
    }

    /**
     * Build the slash command with no option
     *
     * @param name the name of command
     * @param desc the desc of command
     */
    public void buildSlashNoOptionCommand(String name, String desc) {
        action.addCommands(Commands.slash(name, desc).setContexts(InteractionContextType.ALL).setIntegrationTypes(IntegrationType.ALL));
    }

    /**
     * Build the slash command with one option
     *
     * @param name      the name of command
     * @param desc      the desc of command
     * @param inputid   the input id
     * @param inputDesc the input desc
     */
    public void buildSlashOneOption(String name, String desc, String inputid, String inputDesc) {
        action.addCommands(Commands.slash(name, desc).addOption(OptionType.STRING, inputid, inputDesc, true).setContexts(InteractionContextType.ALL).setIntegrationTypes(IntegrationType.ALL));
    }

    /**
     * Build the slash command with multiple options
     *
     * @param name the name of command
     * @param desc the desc of command
     * @param data the option data
     */
    public void buildSlashMultipleOption(String name, String desc, OptionData... data) {
        action.addCommands(Commands.slash(name, desc).addOptions(data).setContexts(InteractionContextType.ALL).setIntegrationTypes(IntegrationType.ALL));
    }

    /**
     * Build the message command
     *
     * @param name the name of command
     */
    public void buildMessageCommand(String name) {
        action.addCommands(Commands.context(Command.Type.MESSAGE, name));
    }

    /**
     * Register the slash commands with multiple options
     */
    public void registerSlashMultipleOptionCommand() {
        buildSlashMultipleOption("profile", "see Lichess profile for given user", new OptionData(OptionType.STRING, "search-user", "Search Lichess username", true));

        buildSlashMultipleOption("puzzle", "do random/daily puzzles", new OptionData(OptionType.STRING, "pick-puzzle", "pick type of puzzles", true).addChoice("Lichess daily puzzle", "lip").addChoice("Chess.com daily puzzle", "cpp").addChoice("Chess.com random puzzle", "random").addChoice("LichessDB theme puzzle", "lidb"));

        buildSlashMultipleOption("connect", "join the Chesslise network to find and challenge players", PreferencePl.getOptionData(), PreferenceTc.getOptionData(), PreferenceFr.getOpeningOptionData(), PreferenceFr.getPlayerOptionData(), PreferenceFr.getPieceOptionData(), PreferenceFr.getStyleOptionData());

        buildSlashMultipleOption("setpreference", "join the Chesslise network to find players and friends", PreferencePl.getOptionData(),
                PreferenceTc.getOptionData(),
                PreferenceFr.getPlayerOptionData(),
                PreferenceFr.getOpeningOptionData(),
                PreferenceFr.getPieceOptionData(),
                PreferenceFr.getStyleOptionData());

        buildSlashMultipleOption("mychallenges", "View your own challenges in the Chesslise servers", new OptionData(OptionType.STRING, "chalstatus", "Select the view configuration of the status", true).addChoice("pending", "pending")
                .addChoice("accepted", "accepted").addChoice("cancelled", "cancelled").addChoice("completed", "completed"));

        buildSlashMultipleOption("viewfriends", "View various friend requests and friend list", new OptionData(OptionType.STRING, "requesttype", "Select request type", true).addChoice("friendlist", "flist")
                .addChoice("incomming", "fin").addChoice("outgoing", "fout"));
    }

    /**
     * Register the slash commands with no options
     */
    public void registerSlashNoOptionCommand() {
        for (int i = 0; i < COMMANDS.length; i++) {
            buildSlashNoOptionCommand(COMMANDS[i], COMMANDS_DESC[i]);
        }
    }

    /**
     * Register the slash commands with single options
     */
    public void registerSlashSingleOptionCommands() {
        for (String[] strings : COMMAND_SINGLE_OPTION) {
            buildSlashOneOption(strings[0], strings[1], strings[2], strings[3]);
        }
    }

    /**
     * Register the message commands
     */
    public void registerMessageCommand() {
        for (String command : COMMAND_MESSAGE) {
            buildMessageCommand(command);
        }
    }

    /**
     * Register all the commands
     */
    public void register() {
        registerSlashMultipleOptionCommand();
        registerMessageCommand();
        registerSlashNoOptionCommand();
        registerSlashSingleOptionCommands();
        this.action.queue();
    }


    /**
     * get the command information in a string format
     *
     * @return the command information
     */
    public static String printCommand() {
        StringBuilder builder = new StringBuilder();
        builder.append("Chesslise Slash Commands").append("\n\n");
        for (int i = 0; i < COMMANDS.length; i++) {
            builder.append("**").append("/").append(COMMANDS[i])
                    .append("**").append("\n")
                    .append(COMMANDS_DESC[i])
                    .append("\n")
            ;
        }

        builder.append("\n");
        builder.append("Chesslise Options Commands").append("\n\n");

        for (String[] commands : COMMAND_SINGLE_OPTION) {
            builder.append("**").append("/")
                    .append(commands[0])
                    .append("**")
                    .append(" ")
                    .append("[")
                    .append(commands[2])
                    .append("]")
                    .append("\n")
                    .append(commands[1])
                    .append("\n")
            ;

        }

        builder.append("\n");

        return builder.toString();
    }
}
