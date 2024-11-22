package discord.helpermodules;

import chesscom.CCProfile;
import discord.mainhandler.AntiSpam;
import lichess.Game;
import lichess.UserGame;
import chariot.Client;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import net.dv8tion.jda.api.interactions.modals.Modal;

import java.util.Objects;

public class ModalHelperContextModule {


    public ModalHelperContextModule() {

    }

    private void buildInputForm(SlashCommandInteractionEvent slashEvent, String inputid, String label, String placeholder, String modalid, String modaltitle) {
        TextInput ptext = TextInput.create(inputid, label, TextInputStyle.SHORT)
                .setPlaceholder(placeholder)
                .setMinLength(2)
                .setMaxLength(100)
                .setRequired(true)
                .build();
        Modal pmodal = Modal.create(modalid, modaltitle)
                .addComponents(ActionRow.of(ptext))
                .build();
        slashEvent.replyModal(pmodal).queue();
    }

    private void buildButtonInputForm(ButtonInteractionEvent buttonEvent) {
        TextInput ptext = TextInput.create("self-user", "Input Your Lichess Username", TextInputStyle.SHORT)
                .setPlaceholder("Input Your Lichess Username")
                .setMinLength(2)
                .setMaxLength(100)
                .setRequired(true)
                .build();

        TextInput targettext = TextInput.create("self-user" + "tar-user", "Enter Your Friend Lichess Username", TextInputStyle.SHORT)
                .setPlaceholder("Enter Your Friend Lichess Username")
                .setMinLength(2)
                .setMaxLength(100)
                .setRequired(true)
                .build();
        Modal pmodal = Modal.create("modal-self-user", "Challenge Friend ")
                .addComponents(ActionRow.of(ptext), ActionRow.of(targettext))
                .build();
        buttonEvent.replyModal(pmodal).queue();
    }


    public void sendSelfUserInputForm(ButtonInteractionEvent buttonEvent){
        buildButtonInputForm(buttonEvent);
    }


    public void sendChessComUserProfileInputForm(SlashCommandInteractionEvent slashCommandInteractionEvent) {
        buildInputForm(slashCommandInteractionEvent, "profileusercc", "Input Chess.com Username", "Input Chess.com Username", "modalproc", "View Chess.com Profiles!");
    }

    public void sendLichessWatchGameCommand(SlashCommandInteractionEvent slashEvent, AntiSpam spam) {
        if (spam.checkSpam(slashEvent)) {
            slashEvent.reply("You have hit max limit! You can only send 24 games in 1 day, try again in 24 hours!").setEphemeral(true).queue();
        } else {
            buildInputForm(slashEvent, "watch_user_or_game", "Input Lichess Username Or Lichess Game", "Input Lichess Username Or Lichess Game", "modalwatch", "Watch Live Or Recent Lichess Games!");
        }
    }

    public void handlePlayFriendChallenge(ModalInteractionEvent eventModal, Client client, Game game){
        String selfUser = Objects.requireNonNull(eventModal.getValue("self-user")).getAsString().trim().toLowerCase();
        String target = Objects.requireNonNull(eventModal.getValue("self-usertar-user")).getAsString().trim().toLowerCase();

        if(client.users().byId(selfUser).isPresent() && client.users().byId(target).isPresent()){
            eventModal.reply(game.generateOpenChallengeForTwoUsers(selfUser, target, client)).queue();

        }else{
            eventModal.reply("Self user or Friend user not present! Please type them properly, or ask your friend for the right Lichess.org username!").setEphemeral(true).queue();
        }

    }


    public void handleGameInputOnFormSubmit(ModalInteractionEvent eventModal, Client client) {
        String userInput = eventModal.getValue("watch_user_or_game").getAsString().trim();
        if (isLink(userInput)) {
            String validGameId = getValidGameId(userInput);
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

    public void handleChessComProfileOnFormSubmit(ModalInteractionEvent eventModal) {
        String usercc = eventModal.getValue("profileusercc").getAsString().trim();
        CCProfile ccProfile = new CCProfile(usercc);
        eventModal.deferReply(true).queue();
        eventModal.getChannel().sendMessageEmbeds(ccProfile.getCCProfile().build()).queue();
    }


    private boolean isLink(String link) {
        return link.contains("/") && link.contains(".");
    }


    private String getValidGameId(String link) {
        String[] spliter = link.split("/");
        String validGameId = "";

        if (spliter.length <= 3)
            return null;

        if (spliter[3].length() == 12) {
            validGameId += spliter[3].substring(0, spliter[3].length() - 4);
        } else {
            validGameId += spliter[3];
        }

        if (!(link.contains("https://lichess.org/") && Client.basic().games().byGameId(validGameId).isPresent())) {
            return null;
        }

        return validGameId;
    }


}