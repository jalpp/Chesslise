import chariot.Client;
import chariot.ClientAuth;
import net.dv8tion.jda.api.EmbedBuilder;

public class AdminLoginChat extends AdminLoginChallenge{

    private EmbedBuilder embedBuilder;
    private String message;

    public AdminLoginChat(Client client, String LichessToken, String userID, String message) {
        super(client, LichessToken, userID);
        this.message = message;
    }


   public EmbedBuilder getChatStatus(){

        this.embedBuilder = new EmbedBuilder();

        ClientAuth clientAuth = Client.auth(this.getLichessToken());

        clientAuth.users().sendMessageToUser(this.getUserID(), this.message);

        this.embedBuilder.setDescription(this.message + " is send to: " + this.getUserID());


        return embedBuilder;

   }
}
