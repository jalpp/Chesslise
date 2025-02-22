package discord.helpermodules;

import chesscom.CCProfile;
import lichess.Game;
import lichess.UserGame;
import chariot.Client;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;


import java.util.Objects;

public class ModalHelperModule {


    private final ModalInteractionEvent eventModal;
    private final Client client;

    public ModalHelperModule(ModalInteractionEvent eventModal, Client client) {
        this.eventModal = eventModal;
        this.client = client;
    }

    /**
     * Send the game input form
     */
    public void sendPlayFriendChallenge() {
        String selfUser = Objects.requireNonNull(eventModal.getValue("self-user")).getAsString().trim().toLowerCase();
        String target = Objects.requireNonNull(eventModal.getValue("self-usertar-user")).getAsString().trim().toLowerCase();

        if (client.users().byId(selfUser).isPresent() && client.users().byId(target).isPresent()) {
            eventModal.reply(Game.generateOpenChallengeForTwoUsers(selfUser, target, client)).queue();

        } else {
            eventModal.reply("Self user or Friend user not present! Please type them properly, or ask your friend for the right Lichess.org username!").setEphemeral(true).queue();
        }

    }

    /**
     * Send the game input on form submit
     */
    public void sendGameInputOnFormSubmit() {
        String userInput = eventModal.getValue("watch_user_or_game").getAsString().trim();
        if (Game.isLink(userInput)) {
            String validGameId = Game.getValidGameId(userInput);
            if (validGameId != null) {
                String gameGif = "https://lichess1.org/game/export/gif/" + validGameId + ".gif?theme=blue&piece=kosal";
                eventModal.reply(gameGif).queue();
            } else {
                eventModal.reply("Please provide a valid Lichess game!").queue();
            }
        } else if (client.users().byId(userInput).isPresent()) {
            UserGame userGame = new UserGame(client, userInput);
            eventModal.deferReply(true).queue();
            eventModal.getChannel().sendMessage(userGame.getUserGames()).queue();

        } else {
            eventModal.reply("Please Provide A Valid Lichess Username!").queue();
        }
    }

    /**
     * Send the chess.com profile on form submit
     */
    public void sendChessComProfileOnFormSubmit() {
        String usercc = eventModal.getValue("profileusercc").getAsString().trim();
        CCProfile ccProfile = new CCProfile(usercc);
        eventModal.deferReply(true).queue();
        eventModal.getChannel().sendMessageEmbeds(ccProfile.getCCProfile().build()).queue();
    }


}