import chariot.Client;
import chariot.ClientAuth;
import net.dv8tion.jda.api.EmbedBuilder;
import java.awt.*;
import java.time.LocalTime;
import java.time.ZonedDateTime;


public class AdminLoginCreateTournament{

    private EmbedBuilder embedBuilder;
    private String adminToken;
    private String timecontrol;

    public AdminLoginCreateTournament(String token, String timeformat){

        this.adminToken = token;
        this.timecontrol = timeformat;
    }


    public EmbedBuilder getCreatedTournament(){

        ClientAuth clientAuth = Client.auth(this.adminToken);

        try {



                this.embedBuilder = new EmbedBuilder();
                this.embedBuilder.setColor(Color.orange);
                this.embedBuilder.setTitle("Tournament Created!");
                this.embedBuilder.setDescription("Tournament created! Please note, tournament time, name, description are pre set, change it according from your Lichess account!");
                var tomorrow = ZonedDateTime.now().plusDays(3).with(
                        LocalTime.parse("17:00"));


                switch (this.timecontrol) {
                    case "blitz":
                        clientAuth.tournaments().createArena(params -> params.clockBlitz3m1s().name("my tournament").description("tournament made by LISEBOT").startTime(tomorrow));

                        break;
                    case "rapid":
                        clientAuth.tournaments().createArena(params -> params.clockRapid10m0s().name("my tournament").description("tournament made by LISEBOT").startTime(tomorrow));

                        break;
                    case "classical":
                        clientAuth.tournaments().createArena(params -> params.clockClassical30m0s().name("my tournament").description("tournament made by LISEBOT").startTime(tomorrow));
                        break;
                    case "bullet":
                        clientAuth.tournaments().createArena(params -> params.clockBullet1m0s().name("my tournament").description("tournament made by LISEBOT").startTime(tomorrow));
                        break;
                    default:
                        return this.embedBuilder.setDescription("error occured, please input a proper variant: supported varaints are: blitz,rapid,classical,bullet");

                }

        }catch (Exception e){
            e.printStackTrace();
            this.embedBuilder = new EmbedBuilder();
            return this.embedBuilder.setDescription("Error Occurred, Please provide proper input!");
        }

        return this.embedBuilder;

    }



}

