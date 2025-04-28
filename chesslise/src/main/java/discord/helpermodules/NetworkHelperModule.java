package discord.helpermodules;

import com.mongodb.client.MongoCollection;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import network.challenge.*;
import network.user.Connect;
import network.user.Disconnect;
import network.user.PreferenceUpdate;
import network.user.friends.*;
import org.bson.Document;
import runner.Main;
import abstraction.CommandTrigger;

public class NetworkHelperModule implements CommandTrigger {

    private final MongoCollection<Document> players;
    private final MongoCollection<Document> challenges;
    private final SlashCommandInteractionEvent event;

    public NetworkHelperModule(SlashCommandInteractionEvent event) {
        this.players = Main.getNetworkPlayers();
        this.challenges = Main.getNetworkChallenges();
        this.event = event;
    }

    public void sendConnect() {
        Connect connect = new Connect(players);
        connect.connect(event);
    }

    public void sendDisconnect() {
        Disconnect disconnect = new Disconnect(challenges, players);
        disconnect.disconnect(event);
    }

    public void sendSetPreference() {
        PreferenceUpdate preferenceUpdate = new PreferenceUpdate(players);
        preferenceUpdate.update(event);
    }

    public void sendMyChallenge() {
        View view = new View(challenges, players);
        view.view(event);
    }

    public void sendChallengeGlobal() {
        Pairing pairing = new Pairing(challenges, players, PairingNetworkType.PAIR_NETWORK_CHALLENGE);
        pairing.pairOpenNetwork(event);
    }

    public void sendChallengeSelf() {
        Pairing pairing = new Pairing(challenges, players, PairingNetworkType.PAIR_NETWORK_CHALLENGE);
        pairing.pairSelfNetwork(event);
    }

    public void sendFriendFinderNetwork() {
        Pairing pairing = new Pairing(challenges, players, PairingNetworkType.PAIR_NETWORK_FRIEND);
        pairing.pairSelfNetwork(event);
    }

    public void sendSeekChallenge() {
        Create create = new Create(challenges, players);
        create.create(event);
    }

    public void sendCancelChallenge() {
        Cancel cancel = new Cancel(challenges, players);
        cancel.cancel(event);
    }

    public void sendCompleteChallenge() {
        Complete complete = new Complete(challenges, players);
        complete.complete(event);
    }

    public void sendSendFriendRequest() {
        SendFriendRequest send = new SendFriendRequest(players);
        send.sendFriendReq(event);
    }

    public void sendAcceptFriendRequest() {
        AcceptFriendRequest accept = new AcceptFriendRequest(players);
        accept.acceptFriendRequest(event);
    }

    public void sendBlockFriendRequest() {
        BlockFriendRequest block = new BlockFriendRequest(players);
        block.blockFriendRequest(event);
    }

    public void sendcancelFriendRequest() {
        CancelFriendRequest cancel = new CancelFriendRequest(players);
        cancel.cancelFriendRequest(event);
    }

    public void sendRemoveFriendRequest() {
        RemoveFriendRequest remove = new RemoveFriendRequest(players);
        remove.removeFriendRequest(event);
    }

    public void sendViewFriendRequest() {
        ViewFriendRequest view = new ViewFriendRequest(players);
        view.viewFriendRequests(event);
    }

    @Override
    public void trigger(String commandName) {
        switch (commandName) {
            case "connect" -> sendConnect();

            case "disconnect" -> sendDisconnect();

            case "setpreference" -> sendSetPreference();

            case "mychallenges" -> sendMyChallenge();

            case "pairchallenge" -> sendChallengeGlobal();

            case "pairchallengenetwork" -> sendChallengeSelf();

            case "seekchallenge" -> sendSeekChallenge();

            case "cancelchallenge" -> sendCancelChallenge();

            case "completechallenge" -> sendCompleteChallenge();

            case "sendfriendrequest" -> sendSendFriendRequest();

            case "acceptfriendrequest" -> sendAcceptFriendRequest();

            case "cancelfriendrequest" -> sendcancelFriendRequest();

            case "findfriend" -> sendFriendFinderNetwork();

            case "removefriend" -> sendRemoveFriendRequest();

            case "blockfriend" -> sendBlockFriendRequest();

            case "viewfriends" -> sendViewFriendRequest();

        }
    }

}
