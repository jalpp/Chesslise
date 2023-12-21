package Discord.HandlerModules;

import Abstraction.Context.ContextHandler;
import Chesscom.CCProfile;
import Discord.MainHandler.AntiSpam;
import Lichess.Game;
import Lichess.UserGame;
import Lichess.UserProfile;
import chariot.Client;
import com.github.bhlangonijr.chesslib.Board;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.MessageContextInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

public class ModalContextModule implements ContextHandler {


    @Override
    public void handleLogic(MessageContextInteractionEvent context, SlashCommandInteractionEvent slashEvent, ButtonInteractionEvent buttonEvent, ModalInteractionEvent eventModal, Client client, Board board, Board blackboard, AntiSpam spam, AntiSpam dailyspam, AntiSpam watchlimit) {

        String name = eventModal.getModalId();

        switch (name) {
            case "modalwatch" -> {
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
            case "modalpro" -> {
                String userID = eventModal.getValue("profileuser").getAsString().trim();
                UserProfile userProfile = new UserProfile(client, userID);
                eventModal.deferReply(true).queue();
                eventModal.getChannel().sendMessage(userProfile.getUserProfile()).queue();
            }
            case "modalproc" -> {
                String usercc = eventModal.getValue("profileusercc").getAsString().trim();
                CCProfile ccProfile = new CCProfile(usercc);
                eventModal.deferReply(true).queue();
                eventModal.getChannel().sendMessageEmbeds(ccProfile.getCCProfile().build()).queue();
            }
        }
    }


    private boolean isLink(String link){
        return link.contains("/") && link.contains(".");
    }
    private String getValidGameId(String link){
        String[] spliter = link.split("/");
        String validGameId = "";

        if(spliter.length<=3)
            return null;

        if(spliter[3].length() == 12){
            validGameId += spliter[3].substring(0, spliter[3].length() - 4);
        }else{
            validGameId += spliter[3];
        }

        if(!(link.contains("https://lichess.org/") && Client.basic().games().byGameId(validGameId).isPresent())){
            return null;
        }

        return validGameId;
    }




}
