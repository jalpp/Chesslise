import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;

public class InviteMe {

    private EmbedBuilder embedBuilder;

    public InviteMe(){
        this.embedBuilder = new EmbedBuilder();
    }

    public EmbedBuilder getInviteInfo(){
        this.embedBuilder = new EmbedBuilder();
        this.embedBuilder.setColor(Color.orange);
        this.embedBuilder.setTitle("Invite me and Join our Discord Server!");
        this.embedBuilder.setDescription("\uD83D\uDC4B [Click here for invite me](https://discord.com/api/oauth2/authorize?client_id=930544707300393021&permissions=277025704000&scope=bot%20applications.commands) \n\n \uD83D\uDC4D [Vote me on top.gg](https://top.gg/bot/930544707300393021/vote) \n\n \uD83D\uDEE0️ [Join Support Server](https://discord.com/invite/6GdGqwxBdW) ");



        return this.embedBuilder;
    }




}
