package discord.mainhandler;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.util.HashMap;
import java.util.Map;

/**
 * Class to handle the stopping spamming from user
 */
public class AntiSpam {

    private final Map<String, Long> userRequestMap;

    private final long timeFrame;
    private final int maxRequests;

    public AntiSpam(long timeFrame, int maxRequests) {
        userRequestMap = new HashMap<>();
        this.timeFrame = timeFrame;
        this.maxRequests = maxRequests;
    }

    /**
     * Check the spamming from user
     *
     * @param event the slash command event
     * @return true if the user is spamming
     */
    public boolean checkSpam(SlashCommandInteractionEvent event) {
        String userId = event.getUser().getId();

        Long lastRequestTime = userRequestMap.get(userId);
        if (lastRequestTime != null && System.currentTimeMillis() - lastRequestTime < timeFrame) {
            int numRequests = 0;
            for (long timestamp : userRequestMap.values()) {
                if (System.currentTimeMillis() - timestamp < timeFrame) {
                    numRequests++;
                }
            }
            if (numRequests >= maxRequests) {
                return true;
            }
        }

        userRequestMap.put(userId, System.currentTimeMillis());
        return false;
    }
}