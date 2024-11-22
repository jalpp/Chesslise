package runner;

import discord.mainhandler.CommandHandler;
import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.IntegrationType;
import net.dv8tion.jda.api.interactions.InteractionContextType;
import net.dv8tion.jda.api.interactions.InteractionType;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;


public class Main extends ListenerAdapter {

    private static JDA jda;

    public static void main(String[] args) {

        Dotenv dotenv = Dotenv.load();

        JDABuilder jdaBuilder = JDABuilder.createDefault(dotenv.get("ENV_BETA").equalsIgnoreCase("true") ? dotenv.get("DISCORD_BETA_TOKEN") : dotenv.get("DISCORD_PROD_TOKEN") );

        jdaBuilder.setStatus(OnlineStatus.ONLINE);


        jdaBuilder.addEventListeners(new Main());
        jdaBuilder.addEventListeners(new CommandHandler());


        try {
            jda = jdaBuilder.build();

        } catch (Exception exception) {
            exception.printStackTrace();
        }

        CommandListUpdateAction commands = jda.updateCommands();
        // <--------- SLASH COMMANDS -------------->
        commands.addCommands(Commands.slash("profilecc", "view cc profiles").setContexts(InteractionContextType.ALL).setIntegrationTypes(IntegrationType.ALL));
        commands.addCommands(Commands.slash("profile", "See Lichess Profile of given user").setContexts(InteractionContextType.ALL).setIntegrationTypes(IntegrationType.ALL).addOptions(new OptionData(OptionType.STRING, "search-user", "Search Lichess username", true).setAutoComplete(true).setRequired(true)));
        commands.addCommands(Commands.slash("puzzle", "do random/daily puzzles").setContexts(InteractionContextType.ALL).setIntegrationTypes(IntegrationType.ALL).addOptions(new OptionData(OptionType.STRING, "pick-puzzle", "pick type of puzzles", true).addChoice("Lichess daily puzzle", "lip").addChoice("Chess.com daily puzzle", "cpp").addChoice("Chess.com random puzzle", "random")));
        commands.addCommands(Commands.slash("help", "View LISEBOT Commands").setContexts(InteractionContextType.ALL).setIntegrationTypes(IntegrationType.ALL));
        commands.addCommands(Commands.slash("play", "Play Live Chess Games").setContexts(InteractionContextType.ALL).setIntegrationTypes(IntegrationType.ALL));
        commands.addCommands(Commands.slash("watch", "Watch Lichess games for given user").setContexts(InteractionContextType.ALL).setIntegrationTypes(IntegrationType.ALL));
        commands.addCommands(Commands.slash("move", "make a move").addOption(OptionType.STRING, "play-move", "input chess move", true).setContexts(InteractionContextType.ALL).setIntegrationTypes(IntegrationType.ALL));
        commands.addCommands(Commands.slash("resetboard", "reset the board"));
        commands.addCommands(Commands.slash("learnchess", "Learn basic chess moves").setContexts(InteractionContextType.ALL).setIntegrationTypes(IntegrationType.ALL));
        commands.addCommands(Commands.slash("chessdb", "View chessdb eval of a position useful for openings/middelgame fens").setContexts(InteractionContextType.ALL).setIntegrationTypes(IntegrationType.ALL).addOption(OptionType.STRING, "paste-fen", "Enter fen to be analyzed", true));

        // <------- MESSAGE COMMANDS ---------->
        commands.addCommands(Commands.context(Command.Type.MESSAGE, "Lichess Daily Puzzle"));
        commands.addCommands(Commands.context(Command.Type.MESSAGE, "Play Chess"));
        commands.addCommands(Commands.context(Command.Type.MESSAGE, "View Lichess Broadcasts"));
        commands.addCommands(Commands.context(Command.Type.MESSAGE, "Watch GMs"));
        commands.addCommands(Commands.context(Command.Type.MESSAGE, "Chess.com Daily Puzzle"));


        commands.queue();


       // LichessBotRunner.main(args); // sun set of Liquid Chess Engine (turn it on if you want to run locally) and import the Engine module


    }

    @Override
    public void onReady(ReadyEvent event) {
        JDA jda = event.getJDA();
        int guildCount = jda.getGuilds().size();

        jda.getPresence().setActivity(Activity.watching("V15 Servers: " + guildCount));
    }


}
