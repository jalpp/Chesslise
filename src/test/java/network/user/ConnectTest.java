package network.user;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import network.challenge.Create;
import network.challenge.Pairing;
import network.challenge.PairingNetworkType;
import network.user.friends.*;
import org.bson.Document;
import org.junit.jupiter.api.*;
import runner.Main;


class ConnectTest {

    private static MongoCollection<Document> networkPlayers;

    private static MongoCollection<Document> networkChallenges;

    private static Connect connect;


    @BeforeAll
    static void setUp() {
        String connectionString = Main.dotenv.get("CONNECTION_STRING");
        String dbname = Main.dotenv.get("DB_NAME");
        String playerColName = Main.dotenv.get("DB_PLAYER_COL");
        String challengeColName = Main.dotenv.get("DB_CHALL_COL");
        ServerApi serverApi = ServerApi.builder()
                .version(ServerApiVersion.V1)
                .build();

        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(connectionString))
                .serverApi(serverApi)
                .build();

        MongoClient mongoClient = MongoClients.create(settings);

        MongoDatabase database = mongoClient.getDatabase(dbname);

        networkChallenges = database.getCollection(challengeColName + "beta");

        networkPlayers = database.getCollection(playerColName + "beta");

        connect = new Connect(networkPlayers);
    }


    @Test
    @Order(1)
    void insertuser1() {

        String msg = connect.insertEntry("12345213123", "user1", PreferencePl.LICHESS, PreferenceTc.CLASSICAL, new FriendPrefBuilder(PreferenceFr.MAGNUS, PreferenceFr.KNIGHT, PreferenceFr.SICILIAN, PreferenceFr.AGGRESSIVE));
        System.out.println(msg);
        Assertions.assertEquals("user1 joined the network!", msg);
    }

    @Test
    @Order(2)
    void insertuser2() {

        String msg2 = connect.insertEntry("12123423123", "user2", PreferencePl.UNAUTH_LICHESS, PreferenceTc.BLITZ, new FriendPrefBuilder(PreferenceFr.MAGNUS, PreferenceFr.KNIGHT, PreferenceFr.SICILIAN, PreferenceFr.AGGRESSIVE));
        System.out.println(msg2);
        Assertions.assertEquals("user2 joined the network!", msg2);
    }

    @Test
    @Order(3)
    void insertuser3() {

        String msg2 = connect.insertEntry("111111111111", "user3", PreferencePl.UNAUTH_LICHESS, PreferenceTc.BLITZ, new FriendPrefBuilder(PreferenceFr.GUKESH, PreferenceFr.KING, PreferenceFr.QUEENS_GAMBIT, PreferenceFr.AGGRESSIVE));
        System.out.println(msg2);
        Assertions.assertEquals("user3 joined the network!", msg2);
    }

    @Test
    @Order(4)
    void insertuser4() {
        String msg2 = connect.insertEntry("11111111112", "user4", PreferencePl.UNAUTH_LICHESS, PreferenceTc.BLITZ, new FriendPrefBuilder(PreferenceFr.GUKESH, PreferenceFr.KING, PreferenceFr.QUEENS_GAMBIT, PreferenceFr.AGGRESSIVE));
        System.out.println(msg2);
        Assertions.assertEquals("user4 joined the network!", msg2);
    }

    @Test
    @Order(5)
    void insertuser5() {
        String msg2 = connect.insertEntry("11111111113", "user5", PreferencePl.UNAUTH_LICHESS, PreferenceTc.BLITZ, new FriendPrefBuilder(PreferenceFr.GUKESH, PreferenceFr.KING, PreferenceFr.QUEENS_GAMBIT, PreferenceFr.AGGRESSIVE));
        System.out.println(msg2);
        Assertions.assertEquals("user5 joined the network!", msg2);
    }

    @Test
    @Order(6)
    void insertuserNotAfriend() {
        String msg2 = connect.insertEntry("1111111111001", "user8", PreferencePl.UNAUTH_LICHESS, PreferenceTc.BLITZ, new FriendPrefBuilder(PreferenceFr.GUKESH, PreferenceFr.KING, PreferenceFr.QUEENS_GAMBIT, PreferenceFr.TACTICAL));
        System.out.println(msg2);
        Assertions.assertEquals("user8 joined the network!", msg2);
    }


    @Test
    @Order(7)
    void insertuser6() {
        String msg2 = connect.insertEntry("11111111112", "user4", PreferencePl.UNAUTH_LICHESS, PreferenceTc.BLITZ, new FriendPrefBuilder(PreferenceFr.FABI, PreferenceFr.PAWN, PreferenceFr.QUEENS_GAMBIT, PreferenceFr.AGGRESSIVE));
        System.out.println(msg2);
        Assertions.assertEquals("You have already connected your account!", msg2);
    }

    @Test
    @Order(8)
    void insertUser7() {
        String msg2 = connect.insertEntry("1111112121", "user6", PreferencePl.LICHESS, PreferenceTc.CLASSICAL, new FriendPrefBuilder(PreferenceFr.MAGNUS, PreferenceFr.KNIGHT, PreferenceFr.SICILIAN, PreferenceFr.AGGRESSIVE));
        Disconnect disconnect = new Disconnect(networkChallenges, networkPlayers);
        String msg3 = disconnect.goOffline("1111112121");
        System.out.println(msg3);
        Assertions.assertEquals("Successfully made your account offline! You won't see any challenges or friend pairings, to go online please run /connect", msg3);

    }

    @Test
    @Order(9)
    void sendfriendrequest1() {
        SendFriendRequest request = new SendFriendRequest(networkPlayers);
        ViewFriendRequest view = new ViewFriendRequest(networkPlayers);
        String messg = request.addFriend("12345213123", "user2");
        view.viewRequests("12345213123", ViewTypeFriend.OUTGOING_FRIEND);
        AcceptFriendRequest accept = new AcceptFriendRequest(networkPlayers);
        System.out.println(accept.acceptFriend("12123423123", "12345213123"));
        view.viewRequests("12345213123", ViewTypeFriend.LIST_FRIEND);
        System.out.println(request.addFriend("12123423123", "user3"));
        System.out.println(accept.acceptFriend("111111111111", "12123423123"));
        System.out.println(request.addFriend("111111111111", "user4"));
        System.out.println(accept.acceptFriend("11111111112", "111111111111"));
        System.out.println(request.addFriend("11111111112", "user5"));
        System.out.println(accept.acceptFriend("11111111113", "11111111112"));
    }

    @Test
    @Order(10)
    void viewFriendRequest() {
        ViewFriendRequest request = new ViewFriendRequest(networkPlayers);
        System.out.println(request.viewRequests("11111111113", ViewTypeFriend.LIST_FRIEND));
    }

    @Test
    @Order(11)
    void sendChallengeSelfNetwork() {
        // user 5 sends challenge to open/network
        Create create = new Create(networkChallenges, networkPlayers);
        create.createChallenge("12123423123", "user2", "blitz", "unauthlichess", "pending");

        Pairing pairing = new Pairing(networkChallenges, networkPlayers, PairingNetworkType.PAIR_NETWORK_CHALLENGE);

        String msg = pairing.bfspairing("11111111113", "user5");

        System.out.println(msg);
    }

    @Test
    @Order(12)
    void sendNormalChallenge() {
        Create create = new Create(networkChallenges, networkPlayers);
        create.createChallenge("11111111113", "user5", "blitz", "unauthlichess", "pending");
        Pairing pairing = new Pairing(networkChallenges, networkPlayers, PairingNetworkType.PAIR_NETWORK_FRIEND);

        String msg = pairing.pairPlayer("111111111111", "user3");

        System.out.println(msg);
    }

    @Test
    @Order(13)
    void findFriend() {
        Pairing pairing = new Pairing(networkChallenges, networkPlayers, PairingNetworkType.PAIR_NETWORK_FRIEND);

    }


    @AfterAll
    static void tearDown() {
        networkChallenges.deleteMany(new Document());
        networkPlayers.deleteMany(new Document());
    }
}