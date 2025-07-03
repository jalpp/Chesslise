package discord.mainhandler;

import net.dv8tion.jda.api.interactions.IntegrationType;
import net.dv8tion.jda.api.interactions.InteractionContextType;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;
import network.user.PreferenceFr;
import network.user.PreferencePl;
import network.user.PreferenceTc;
import setting.SettingSchema;
import java.util.ArrayList;
import java.util.HashMap;

public class CommandBuilder {

    private final CommandListUpdateAction action;

    private static final String[] COMMANDS = { "help", "play", "profilecc", "watch", "resetboard", "learnchess",
            "disconnect", "pairchallenge", "pairchallengenetwork", "seekchallenge", "findfriend", "ladderplayerinfo" };

    private static final String[] COMMANDS_DESC = { "View Chesslise command info",
            "Play live chess games",
            "View Chess.com profile",
            "Watch Lichess games",
            "Reset chess engine board for use",
            "Learn chess rules",
            "Disconnect from CSSN",
            "Attempt to find a challenge in Chesslise network",
            "Attempt to send a challenge in your friend network",
            "Create a challenge and seek for others to accept it",
            "Find a new friend within your network or globally",
            "View ladder player info"
    };

    private static final String[][] COMMAND_SINGLE_OPTION = {
            { "chessdb", "View chessdb eval of a position useful for openings/middelgame fens", "paste-fen",
                    "Enter fen to be analyzed" },
            { "move", "make a move against engine", "play-move", "input chess move" },
            { "fen", "view a chess position by providing the FEN", "input-fen", "Input chess fen" },
            { "cancelchallenge", "cancel a challenge with id", "challid", "provide the challenge id" },
            { "completechallenge", "complete a challenge with id", "cchallid", "provide completed challenge id" },
            { "sendfriendrequest", "Send friend request by providing target username", "frienduser",
                    "provide target username" },
            { "acceptfriendrequest", "Accept friend request by providing target friend id", "friendid",
                    "provide target id" },
            { "cancelfriendreqeust", "Cancel an incoming friend request by providing discord id", "cancelid",
                    "provide target id" },
            { "removefriend", "Remove a friend from friend list by providing discord id", "removeid",
                    "provide friend discord username" },
            { "blockfriend", "Block a friend who is not being friendly by providing id", "blockid",
                    "provide friend discord username" },
          };

    private static final HashMap<String, ArrayList<String>> COMMAND_MULTIPLE_OPTIONS = new HashMap<>();

    public CommandBuilder(CommandListUpdateAction action) {
        this.action = action;
    }

    public void buildSlashNoOptionCommand(String name, String desc) {
        action.addCommands(Commands.slash(name, desc).setContexts(InteractionContextType.ALL)
                .setIntegrationTypes(IntegrationType.ALL));
    }

    public void buildSlashOneOption(String name, String desc, String inputid, String inputDesc) {
        action.addCommands(Commands.slash(name, desc).addOption(OptionType.STRING, inputid, inputDesc, true)
                .setContexts(InteractionContextType.ALL).setIntegrationTypes(IntegrationType.ALL));
    }

    public void buildSlashMultipleOption(String name, String desc, OptionData... data) {
        COMMAND_MULTIPLE_OPTIONS.put(name, new ArrayList<>());
        COMMAND_MULTIPLE_OPTIONS.get(name).add(name);
        COMMAND_MULTIPLE_OPTIONS.get(name).add(desc);
        action.addCommands(Commands.slash(name, desc).addOptions(data).setContexts(InteractionContextType.ALL)
                .setIntegrationTypes(IntegrationType.ALL));
    }

    public void registerSlashMultipleOptionCommand() {
        buildSlashMultipleOption("profile", "see Lichess profile for given user",
                new OptionData(OptionType.STRING, "search-user", "Search Lichess username", true));

        buildSlashMultipleOption("puzzle", "do random/daily puzzles",
                new OptionData(OptionType.STRING, "pick-puzzle", "pick type of puzzles", true)
                        .addChoice("Lichess daily puzzle", "lip").addChoice("Chess.com daily puzzle", "cpp")
                        .addChoice("Chess.com random puzzle", "random").addChoice("LichessDB theme puzzle", "lidb"));

        buildSlashMultipleOption("playengine", "play engine by choosing the difficulty level",
                new OptionData(OptionType.STRING, "difficulty", "pick the engine difficulty", true)
                        .addChoice("Easy", "5").addChoice("Medium", "10").addChoice("Hard", "15"));

        buildSlashMultipleOption("lichessladder", "View Lichess Ladders Info",
                new OptionData(OptionType.STRING, "laddertype", "pick the ladder type", true)
                        .addChoice("30+30", "l1").addChoice("60+30", "l2"));

        buildSlashMultipleOption("setengine", "set the engine difficulty",
                new OptionData(OptionType.STRING, "difficulty", "pick the engine difficulty", true)
                        .addChoice("Easy", "5").addChoice("Medium", "10").addChoice("Hard", "15"));

        buildSlashMultipleOption("setting", "set Chesslise settings like board theme, piece type and puzzle difficulty level",
                SettingSchema.getBoardThemeData(), SettingSchema.getPieceTypeData(), SettingSchema.getPuzzleDifficultyData());

        buildSlashMultipleOption("connect", "join the Chesslise network to find and challenge players",
                PreferencePl.getOptionData(), PreferenceTc.getOptionData(), PreferenceFr.getOpeningOptionData(),
                PreferenceFr.getPlayerOptionData(), PreferenceFr.getPieceOptionData(),
                PreferenceFr.getStyleOptionData());

        buildSlashMultipleOption("setpreference", "join the Chesslise network to find players and friends",
                PreferencePl.getOptionData(),
                PreferenceTc.getOptionData(),
                PreferenceFr.getPlayerOptionData(),
                PreferenceFr.getOpeningOptionData(),
                PreferenceFr.getPieceOptionData(),
                PreferenceFr.getStyleOptionData());

        buildSlashMultipleOption("mychallenges", "View your own challenges in the Chesslise servers",
                new OptionData(OptionType.STRING, "chalstatus", "Select the view configuration of the status", true)
                        .addChoice("pending", "pending")
                        .addChoice("accepted", "accepted").addChoice("cancelled", "cancelled")
                        .addChoice("completed", "completed"));

        buildSlashMultipleOption("viewfriends", "View various friend requests and friend list",
                new OptionData(OptionType.STRING, "requesttype", "Select request type", true)
                        .addChoice("friendlist", "flist")
                        .addChoice("incomming", "fin").addChoice("outgoing", "fout"));
    }

    public void registerSlashNoOptionCommand() {
        for (int i = 0; i < COMMANDS.length; i++) {
            buildSlashNoOptionCommand(COMMANDS[i], COMMANDS_DESC[i]);
        }
    }

    public void registerSlashSingleOptionCommands() {
        for (String[] strings : COMMAND_SINGLE_OPTION) {
            buildSlashOneOption(strings[0], strings[1], strings[2], strings[3]);
        }
    }

    public void register() {
        registerSlashMultipleOptionCommand();
        registerSlashNoOptionCommand();
        registerSlashSingleOptionCommands();
        this.action.queue();
    }

    public static String printCommand() {
        StringBuilder builder = new StringBuilder();
        builder.append("Chesslise Slash Commands").append("\n\n");
        for (int i = 0; i < COMMANDS.length; i++) {
            builder.append("**").append("/").append(COMMANDS[i])
                    .append("**").append("\n")
                    .append(COMMANDS_DESC[i])
                    .append("\n");
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
                    .append("\n");

        }

        builder.append("\n");
        builder.append("Chesslise Multiple Option Commands").append("\n\n");

        for (ArrayList<String> commands : COMMAND_MULTIPLE_OPTIONS.values()) {
            builder.append("**").append("/")
                    .append(commands.get(0))
                    .append("**")
                    .append("\n")
                    .append(commands.get(1))
                    .append("\n");
        }

        builder.append("\n");

        return builder.toString();
    }
}