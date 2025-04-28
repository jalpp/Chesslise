package discord.helpermodules;

import chesscom.CCProfile;
import discord.mainhandler.Thumbnail;
import lichess.Game;
import lichess.UserGame;
import chariot.Client;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import setting.SettingSchemaModule;
import java.awt.*;
import java.util.Objects;
import abstraction.CommandTrigger;

public class ModalHelperModule extends SettingSchemaModule implements CommandTrigger {

    private final ModalInteractionEvent eventModal;
    private static final Client client = Client.basic(conf -> conf.retries(0));

    public ModalHelperModule(ModalInteractionEvent eventModal) {
        super(eventModal.getUser().getId());
        this.eventModal = eventModal;
    }

    public void sendPlayFriendChallenge() {
        String selfUser = Objects.requireNonNull(eventModal.getValue("self-user")).getAsString().trim().toLowerCase();
        String target = Objects.requireNonNull(eventModal.getValue("self-usertar-user")).getAsString().trim()
                .toLowerCase();

        if (client.users().byId(selfUser).isPresent() && client.users().byId(target).isPresent()) {
            eventModal.reply(Game.generateOpenChallengeForTwoUsers(selfUser, target, client)).queue();

        } else {
            eventModal.reply(
                    "Self user or Friend user not present! Please type them properly, or ask your friend for the right Lichess.org username!")
                    .setEphemeral(true).queue();
        }

    }

    public void sendGameInputOnFormSubmit() {
        String userInput = eventModal.getValue("watch_user_or_game").getAsString().trim();
        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(Color.BLUE);
        builder.setThumbnail(Thumbnail.getChessliseLogo());
        builder.addField("Author", eventModal.getUser().getAsMention(), false);
        if (Game.isLink(userInput)) {
            String validGameId = Game.getValidGameId(userInput);
            if (validGameId != null) {

                String gameGif = "https://lichess1.org/game/export/gif/" + validGameId + ".gif?theme="
                        + getSettingSchema().getBoardTheme() + "&piece=" + getSettingSchema().getPieceType();
                builder.addField("Share", gameGif, false);
                builder.setImage(gameGif);
                eventModal.replyEmbeds(builder.build())
                        .addActionRow(Button.danger("delete", "delete"), Button.success("flip-board", "flip board"))
                        .queue();
            } else {
                eventModal.reply("Please provide a valid Lichess game!").queue();
            }
        } else if (client.users().byId(userInput).isPresent()) {
            UserGame userGame = new UserGame(client, userInput);
            eventModal.deferReply(true).queue();
            String gameGif = userGame.getUserGamesGif(getSettingSchema());
            builder.addField("Share", gameGif, false);
            builder.setImage(gameGif);
            eventModal.getChannel().sendMessageEmbeds(builder.build())
                    .addActionRow(Button.danger("delete", "delete"), Button.success("flip-board", "flip board"))
                    .queue();

        } else {
            eventModal.reply("Please Provide A Valid Lichess Username!").queue();
        }
    }

    public void sendChessComProfileOnFormSubmit() {
        String usercc = eventModal.getValue("profileusercc").getAsString().trim();
        CCProfile ccProfile = new CCProfile(usercc);
        eventModal.deferReply(true).queue();
        eventModal.getChannel().sendMessageEmbeds(ccProfile.getCCProfile().build()).queue();
    }

    @Override
    public void trigger(String commandName) {
        switch (commandName) {
            case "modalwatch" -> sendGameInputOnFormSubmit();
            case "modalproc" -> sendChessComProfileOnFormSubmit();
            case "modal-self-user" -> sendPlayFriendChallenge();
        }
    }

}