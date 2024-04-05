package Discord.HelperModules;

import Chesscom.CCProfile;
import Discord.MainHandler.AntiSpam;
import Lichess.UserGame;
import Lichess.UserProfile;
import chariot.Client;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import net.dv8tion.jda.api.interactions.modals.Modal;

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
                .addActionRows(ActionRow.of(ptext))
                .build();
        slashEvent.replyModal(pmodal).queue();
    }


    public void sendLichessUserProfileInputForm(SlashCommandInteractionEvent slashCommandInteractionEvent) {
        buildInputForm(slashCommandInteractionEvent, "profileuser", "Input Lichess Username", "Input Lichess Username", "modalpro", "View Lichess Profiles!");
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
            String link = "https://lichess.org/@/" + userInput.toLowerCase() + "/all";
            eventModal.deferReply(true).queue();
            eventModal.getChannel().sendMessage(userGame.getUserGames()).queue();

        } else {
            eventModal.reply("Please Provide A Valid Lichess Username!").queue();
        }
    }


    public void handleLichessProfileOnFormSubmit(ModalInteractionEvent eventModal, Client client) {
        String userID = eventModal.getValue("profileuser").getAsString().trim();
        UserProfile userProfile = new UserProfile(client, userID);
        eventModal.deferReply(true).queue();
        eventModal.getChannel().sendMessage(userProfile.getUserProfile()).queue();
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