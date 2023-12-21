package Discord.MainHandler;

import java.util.HashMap;
import java.util.Map;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
public class AntiSpam {

    // Map to store user IDs and their last request timestamp
    private Map<String, Long> userRequestMap;

    // Time frame for rate limiting (in milliseconds)
    private long timeFrame;

    // Maximum number of requests allowed within the time frame
    private int maxRequests;

    public AntiSpam(long timeFrame, int maxRequests) {
        userRequestMap = new HashMap<>();
        this.timeFrame = timeFrame;
        this.maxRequests = maxRequests;
    }

    public boolean checkSpam(SlashCommandInteractionEvent event) {
        String userId = event.getUser().getId();

        // Check if the user has reached the maximum number of requests
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

        // Update the user's request timestamp
        userRequestMap.put(userId, System.currentTimeMillis());
        return false;
    }
}
