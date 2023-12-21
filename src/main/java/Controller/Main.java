

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;


public class Main extends ListenerAdapter {

    private static JDABuilder jdaBuilder;
    private static JDA jda;


    public static void main(String[] args) {


        jdaBuilder = JDABuilder.createDefault(System.getenv("discord_bot_token"));

        jdaBuilder.setStatus(OnlineStatus.ONLINE);

        jdaBuilder.setActivity(Activity.playing("/help"));

        jdaBuilder.addEventListeners(new Main());
        jdaBuilder.addEventListeners(new CommandHandler());


        try {
            jda = jdaBuilder.build();

        } catch (Exception exception) {
            exception.printStackTrace();
        }

        CommandListUpdateAction commands = jda.updateCommands();
        commands.addCommands(Commands.slash("dailypuzzlecc", "do chess.com daily puzzles"));
        commands.addCommands(Commands.slash("community", "Best chess Discord communities!"));
        commands.addCommands(Commands.slash("broadcast", "View Latest OTB/Online Master tournament"));
        commands.addCommands(Commands.slash("profilecc", "view cc profiles"));
        commands.addCommands(Commands.slash("service", "Read our Terms of Service"));
        commands.addCommands(Commands.slash("suggest", "provide suggestion").addOption(OptionType.STRING, "suggestid", "provide feedback/feature request", true));
        commands.addCommands(Commands.slash("dailypuzzle", "Daily Lichess Puzzles"));
        commands.addCommands(Commands.slash("arena", "See Swiss/Arena standings").addOption(OptionType.STRING, "arenaid", "Input Lichess arena link", true));
        commands.addCommands(Commands.slash("watchmaster", "Watch GM Games in gif"));
        commands.addCommands(Commands.slash("profile", "See Lichess Profile of given user"));
        commands.addCommands(Commands.slash("streamers", "See current Live Streamers"));
        commands.addCommands(Commands.slash("puzzle", "do puzzles"));
        commands.addCommands(Commands.slash("help", "View LISEBOT Commands"));
        commands.addCommands(Commands.slash("play", "Play Live Chess Games"));
        commands.addCommands(Commands.slash("playvariant", "Play variant chess (3check, chess960, atomic)"));
        commands.addCommands(Commands.slash("watch", "Watch Lichess games for given user"));
        commands.addCommands(Commands.slash("invite", "Invite me to your servers!"));
        commands.addCommands(Commands.slash("move", "make a move").addOption(OptionType.STRING, "play-move", "input chess move", true));
        commands.addCommands(Commands.slash("resetboard", "reset the board"));
        commands.addCommands(Commands.slash("answer", "check solution for daily puzzle").addOption(OptionType.STRING, "daily-puzzle-answer", "Answer daily puzzle with chess notation", true));



        commands.addCommands(Commands.context(Command.Type.MESSAGE, "Lichess Daily Puzzle"));
        commands.addCommands(Commands.context(Command.Type.MESSAGE, "Play Chess"));
        commands.addCommands(Commands.context(Command.Type.MESSAGE, "View Lichess Broadcasts"));
        commands.addCommands(Commands.context(Command.Type.MESSAGE, "Watch GMs"));
        commands.addCommands(Commands.context(Command.Type.MESSAGE, "Chess.com Daily Puzzle"));


        commands.queue();

        LichessBotRunner.main(args);



    }




}
