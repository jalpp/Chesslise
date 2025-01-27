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

/**
 * NetworkHelperModule class to handle the network
 */
public class NetworkHelperModule {


    private final MongoCollection<Document> players;
    private final MongoCollection<Document> challenges;
    private final SlashCommandInteractionEvent event;

    public NetworkHelperModule(SlashCommandInteractionEvent event) {
        this.players = Main.getNetworkPlayers();
        this.challenges = Main.getNetworkChallenges();
        this.event = event;
    }

    /**
     * Send the connect network
     */
    public void sendConnect() {
        Connect connect = new Connect(players);
        connect.connect(event);
    }

    /**
     * Send the disconnect network
     */
    public void sendDisconnect() {
        Disconnect disconnect = new Disconnect(challenges, players);
        disconnect.disconnect(event);
    }

    /**
     * Send the set preference network
     */
    public void sendSetPreference() {
        PreferenceUpdate preferenceUpdate = new PreferenceUpdate(players);
        preferenceUpdate.update(event);
    }

    /**
     * Send the view preference network
     */
    public void sendMyChallenge() {
        View view = new View(challenges, players);
        view.view(event);
    }

    /**
     * Send the challenge network
     */
    public void sendChallengeGlobal() {
        Pairing pairing = new Pairing(challenges, players, PairingNetworkType.PAIR_NETWORK_CHALLENGE);
        pairing.pairOpenNetwork(event);
    }

    /**
     * Send the challenge self network
     */
    public void sendChallengeSelf() {
        Pairing pairing = new Pairing(challenges, players, PairingNetworkType.PAIR_NETWORK_CHALLENGE);
        pairing.pairSelfNetwork(event);
    }

    /**
     * Send the friend finder network
     */
    public void sendFriendFinderNetwork() {
        Pairing pairing = new Pairing(challenges, players, PairingNetworkType.PAIR_NETWORK_FRIEND);
        pairing.pairSelfNetwork(event);
    }

    /**
     * Send the seek challenge network
     */
    public void sendSeekChallenge() {
        Create create = new Create(challenges, players);
        create.create(event);
    }

    /**
     * Send the cancel challenge network
     */
    public void sendCancelChallenge() {
        Cancel cancel = new Cancel(challenges, players);
        cancel.cancel(event);
    }

    /**
     * Send the complete challenge network
     */
    public void sendCompleteChallenge() {
        Complete complete = new Complete(challenges, players);
        complete.complete(event);
    }

    /**
     * Send the friend request network
     */
    public void sendSendFriendRequest() {
        SendFriendRequest send = new SendFriendRequest(players);
        send.sendFriendReq(event);
    }

    /**
     * Send the accept friend request network
     */
    public void sendAcceptFriendRequest() {
        AcceptFriendRequest accept = new AcceptFriendRequest(players);
        accept.acceptFriendRequest(event);
    }

    /**
     * Send the block friend request network
     */
    public void sendBlockFriendRequest() {
        BlockFriendRequest block = new BlockFriendRequest(players);
        block.blockFriendRequest(event);
    }

    /**
     * Send the cancel friend request network
     */
    public void sendcancelFriendRequest() {
        CancelFriendRequest cancel = new CancelFriendRequest(players);
        cancel.cancelFriendRequest(event);
    }

    /**
     * Send the remove friend request network
     */
    public void sendRemoveFriendRequest() {
        RemoveFriendRequest remove = new RemoveFriendRequest(players);
        remove.removeFriendRequest(event);
    }

    /**
     * Send the view friend request network
     */
    public void sendViewFriendRequest() {
        ViewFriendRequest view = new ViewFriendRequest(players);
        view.viewFriendRequests(event);
    }


}
