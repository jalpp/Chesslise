import io.github.sornerol.chess.pubapi.client.DailyPuzzleClient;
import io.github.sornerol.chess.pubapi.exception.ChessComPubApiException;
import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;
import java.io.IOException;

public class puzzle {

    private DailyPuzzleClient dailyPuzzleClient;
    private EmbedBuilder embedBuilder;



    public puzzle(){
        this.dailyPuzzleClient = new DailyPuzzleClient();
    }

    public EmbedBuilder getRandom()  {
        this.embedBuilder = new EmbedBuilder();
        try {
            this.embedBuilder.setTitle(this.dailyPuzzleClient.getRandomDailyPuzzle().getTitle());
            this.embedBuilder.setColor(Color.blue);
            this.embedBuilder.setImage(this.dailyPuzzleClient.getRandomDailyPuzzle().getImageUrl());

        } catch (IOException e) {
            e.printStackTrace();
            this.embedBuilder.setDescription("Loading...Please try again");
        } catch (ChessComPubApiException e) {
            e.printStackTrace();
            this.embedBuilder.setDescription("Loading...Please try again");
        }


        return this.embedBuilder;
    }







}
