import chariot.Client;
import chariot.ClientAuth;
import chariot.model.Ack;
import chariot.model.Result;

import java.time.LocalTime;
import java.time.ZonedDateTime;

public class MonthlyTournament{

    private String tournamentStatus;
    private String token;

    public MonthlyTournament(String token) {
       this.token = token;
    }


    public String getStatus() {

        ClientAuth clientAuth = Client.auth(this.token);

        try {


                for (int i = 0; i < 11; i++) {
                    var months = ZonedDateTime.now().plusMonths(i).with(
                            LocalTime.parse("17:00"));
                    clientAuth.tournaments().createArena(params -> params.clockBlitz3m1s().name("my tournament").description("tournament made by LISEBOT").startTime(months));
                }
                return this.tournamentStatus = "tournaments created!";




        } catch (Exception e) {
            e.printStackTrace();
            return this.tournamentStatus = "error";
        }

    }


}
