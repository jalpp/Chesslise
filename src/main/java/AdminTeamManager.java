import chariot.Client;
import chariot.ClientAuth;


public class AdminTeamManager {


    private String teamID;
    private Client client;
    private String userID;
    private String adminToken;
    private ClientAuth clientAuth;


    public AdminTeamManager(ClientAuth clientAuth,Client client, String teamID, String userID, String AdminToken){
        this.teamID = teamID;
        this.userID = userID;
        this.client = client;
        this.adminToken = AdminToken;
        this.clientAuth = Client.auth(this.adminToken);
    }


    public String getTeamID() {
        return teamID;
    }

    public Client getClient() {
        return client;
    }

    public String getUserID() {
        return userID;
    }

    public String getAdminToken(){
        return adminToken;
    }

    public ClientAuth getClientAuth(){
        return clientAuth;
    }




}
