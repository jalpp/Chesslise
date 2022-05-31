import chariot.Client;
import net.dv8tion.jda.api.EmbedBuilder;

public class AdminActionsManager {

    private String userID;
    private EmbedBuilder embedBuilder;
    private Client basicClient;
    private String AdminToken;

    public AdminActionsManager(Client client, String LichessToken, String userID){
        this.userID = userID;
        this.AdminToken = LichessToken;
        this.basicClient = client;
    }


    public String getLichessToken(){
        return this.AdminToken;
    }


    public String getUserID(){
        return this.userID;
    }


    public Client getBasicClient() {

        return this.basicClient;

    }








}
