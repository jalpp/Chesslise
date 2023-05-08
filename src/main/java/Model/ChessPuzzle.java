import chariot.Client;
import com.github.bhlangonijr.chesslib.Board;
import io.github.sornerol.chess.pubapi.client.DailyPuzzleClient;
import net.dv8tion.jda.api.EmbedBuilder;

public abstract class ChessPuzzle {


    private final DailyPuzzleClient dailyPuzzleClient;
    private final Client client;
    private final Board board;

    public ChessPuzzle(){
        this.client = Client.basic();
        this.dailyPuzzleClient = new DailyPuzzleClient();
        this.board = new Board();
    }

    public ChessPuzzle(Client client, Board board){
        this.client = client;
        this.dailyPuzzleClient = new DailyPuzzleClient();
        this.board = new Board();
    }

    public ChessPuzzle(DailyPuzzleClient client, Board board){
        this.dailyPuzzleClient = client;
        this.client = Client.basic();
        this.board = new Board();

    }


    public abstract String getPuzzle();

    public abstract String getSolution();

    public abstract String getPuzzleURL();

    public EmbedBuilder getThemes(){
        return null;
    }

    public abstract String getPuzzleSideToMove();

    public boolean checkSolution(String answer){
        return false;
    }

    public Client getClient() {
        return client;
    }

    public DailyPuzzleClient getDailyPuzzleClient() {
        return dailyPuzzleClient;
    }

    public Board getBoard() {
        return board;
    }

    public int getRating(){
        return 0;
    }
}
