import chariot.Client;
import net.dv8tion.jda.api.EmbedBuilder;
import java.awt.*;

public class LigaEmbed {

    private String teamName;
    private Client client;
    private EmbedBuilder embedBuilder;



    public LigaEmbed(Client client, String teamName){
        this.client = client;
        this.teamName = teamName;


    }


    public EmbedBuilder getLigaEmbed(){
        this.embedBuilder = new EmbedBuilder();

        String lowercase = teamName.toLowerCase();

        char[] split = lowercase.toCharArray();

        for(int i = 0; i < split.length; i++){
            if(split[i] == ' '){
                split[i] = '-';
            }
        }


        String realName = String.valueOf(split);

        liga liga = new liga(realName, 1 , null);
       this.embedBuilder.setTitle("Liga Leaderboard");
       this.embedBuilder.setThumbnail("https://images.prismic.io/lichess/25a60c33-96ad-4fd1-b9b2-dca8e289961f_lichesslogo.png?auto=compress,format");
       this.embedBuilder.setColor(Color.blue);
       this.embedBuilder.setDescription(liga.run());
        return this.embedBuilder;
    }


}
