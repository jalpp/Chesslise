import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;

public class OpeningObject {

    private String name;
    private String fen;

    public OpeningObject(String name, String fen){
        this.name = name;
        this.fen = fen;
    }

    public EmbedBuilder getOpeningEmbed(){
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setColor(Color.green);
        embedBuilder.setTitle(this.name);
        embedBuilder.setImage("https://chessboardimage.com/" + this.fen + ".png");
        return embedBuilder;
    }




}
