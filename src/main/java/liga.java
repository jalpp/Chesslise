//DEPS io.github.tors42:chariot:0.0.35
//JAVA 17+
// Tors42/chariot-scripts source code
// modified version to obtain string version of run()
import java.io.IOException;
import java.nio.file.*;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.*;
import java.util.stream.Collectors;

import chariot.Client;
import chariot.model.Enums.TournamentState;
import chariot.model.Tournament.TeamBattle;
import chariot.model.Tournament;

class liga {

    static void usage() {
        System.out.println(
                """

                Usage: liga <option1>=<value1> <option2>=<value2> ...
                where the options can be:

                 team=<value> (default: lichess-discord-bundesliga-team)

                     The id of the team to check team battles for.
                     The id can be found as the last part of the URL of the team page,
                     https://lichess.org/team/lichess-discord-bundesliga-team


                 battles=<value> (default: 1)

                     The number of recent team battles to include in the report.
                     A negative value such as -1 will include all team battles.


                 file=<value> (default: none)

                     The name of a file to store the report in.
                     Specify "none" in order to only show results in terminal.


                Example:

                 $ liga team=the-chess-lounge
                 Using parameters:
                  team=the-chess-lounge
                  battles=1
                  file=none

                 Processing Team Battle 1/1
                 2022-01-27 "Lichess Liga 6C Team Battle"
                 Players Total/Team: 122/12
                 Rank  Score  Perf  Player
                 ----  -----  ----  ------
                    5     44  2491  Sonata2
                    8     41  2473  Averbakh_Student
                   25     31  2279  Gashimov_Student
                   42     24  2293  chessunable
                   58     20  2229  WadeWilson
                   72     16  2233  ElectroWizardy
                   77     15  2174  Grischuk_Student
                   82     12  2069  Stockfish_Student
                """
        );
    }

    public static void main(String[] args) {
        if (Arrays.stream(args).anyMatch("help"::equals)) {
            usage();
            System.exit(0);
        }

        var input = Arrays.stream(args).collect(Collectors.toMap(s -> s.split("=")[0], s -> s.split("=")[1]));

        String teamOption    = input.getOrDefault("team",    "lichess-discord-bundesliga-team");
        String battlesOption = input.getOrDefault("battles", "1");
        String fileOption    = input.getOrDefault("file",    "none");

        System.out.println(
                """
                Using parameters:
                 team=%s
                 battles=%s
                 file=%s
                 """
                        .formatted(teamOption, battlesOption, fileOption)
        );

        String teamId              = teamOption;
        int desiredNumberOfBattles = Integer.parseInt(battlesOption);
        Path outputFile            = fileOption.equals("none") ?  null : Path.of(fileOption);

        new liga(teamId, desiredNumberOfBattles, outputFile).run();
    }

    final String teamId;
    final int desiredNumberOfBattles;
    final Path outputFile;

    liga(String teamId, int desiredNumberOfBattles, Path outputFile) {
        this.teamId = teamId;
        this.desiredNumberOfBattles = desiredNumberOfBattles < 0 ? Integer.MAX_VALUE : desiredNumberOfBattles;
        this.outputFile = outputFile;
    }

    Predicate<Tournament> isFinishedTeamBattle =
            tournament -> tournament instanceof TeamBattle tb && tb.status() == TournamentState.finished;

    BiConsumer<Integer, Integer> printProgress =
            (current, total) -> System.out.format("\rProcessing Team Battle %d/%d".formatted(current, total));

    BiConsumer<String, Path> appendToFile =
            (string, path) -> {
                if (path != null) {
                    try {
                        Files.writeString(path, string,
                                StandardOpenOption.CREATE,
                                StandardOpenOption.WRITE,
                                StandardOpenOption.APPEND);
                    } catch (IOException ioe) {
                        System.out.println("Failed to write to " + path.toAbsolutePath());
                    }
                }
            };

    record Player(String name, int rank, int score, int perf) {}
    record Summary(String name, ZonedDateTime date, int totalPlayers, int teamPlayers, List<Player> leaders) {}

    String run() {

        var client = Client.basic();

        if (outputFile != null && outputFile.toFile().exists()) outputFile.toFile().delete();

        int maxArenas = 500;
        var battlesCounter = new LongAdder();
        var arenasByTeam = client.teams().arenaByTeamId(teamId, maxArenas).stream()
                .takeWhile(arena -> battlesCounter.longValue() < desiredNumberOfBattles)
                .peek(arena -> { if (isFinishedTeamBattle.test(arena)) battlesCounter.increment(); })
                .toList();

        if (arenasByTeam.size() == maxArenas) {
            System.out.println("""
                    Note, hit a limit of %d arenas played by team %s, when
                    searching for arenas of type Team Battle. I.e, it is
                    possible/likely that there could be more arenas played, but
                    those won't be included in the statistics.
                    """.formatted(maxArenas, teamId)
            );
        }

        var battlesByTeam = arenasByTeam.stream()
                .filter(isFinishedTeamBattle)
                .map(arena -> (TeamBattle) arena)
                .toList();

        int totalBattlesToProcess = Math.min(desiredNumberOfBattles, battlesByTeam.size());

        Consumer<Integer> print = n -> printProgress.accept(n, totalBattlesToProcess);

        if (outputFile != null) {
            System.out.println("Writing results to " + outputFile.toAbsolutePath());
        }
        print.accept(0);
        var processedCounter = new LongAdder();
        var list = battlesByTeam.stream()
                .limit(totalBattlesToProcess)
                .peek(tb -> { processedCounter.increment(); print.accept(processedCounter.intValue()); })
                .map(tb -> {
                    var results = client.tournaments().resultsByArenaId(tb.id()).stream()
                            .filter(r -> teamId.equals(r.team()))
                            .toList();

                    var leaders = results.stream()
                            .filter(ar -> ar.performance() != null)
                            .map(ar -> new Player(ar.username(), ar.rank(), ar.score(), ar.performance()))
                            .sorted(Comparator.comparing(Player::score).reversed().thenComparing(Player::rank))
                            .limit(tb.teamBattle().nbLeaders())
                            .toList();

                    var summary = new Summary(tb.fullName(), tb.startsTime(), tb.nbPlayers(), results.size(), leaders);

                    appendToFile.accept(formatSummary(summary)+"\n", outputFile);

                    return summary;
                }).toList();
        print.accept(totalBattlesToProcess);
       // System.out.println();

      //  if (outputFile == null) {
            String table = list.stream()
                    .map(this::formatSummary)
                    .collect(Collectors.joining("\n"));
         //   System.out.println(table);

        return table;
        }


    String formatSummary(Summary summary) {
        String stat = summary.leaders().stream()
                .map(l -> "%4d    %3d  %4d  %s".formatted(l.rank(), l.score(), l.perf(), l.name()))
                .collect(Collectors.joining("\n"));

        String result =
                """
                %s "%s"
                Players Total/Team: %d/%d
                Rank  Score  Perf  Player
                ----  -----  ----  ------
                %s
                """
                        .formatted(summary.date().format(DateTimeFormatter.ISO_LOCAL_DATE),
                                summary.name(),
                                summary.totalPlayers(),
                                summary.teamPlayers(),
                                stat);
        return result;
    }
}
