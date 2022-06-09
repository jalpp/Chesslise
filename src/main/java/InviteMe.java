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
        this.embedBuilder.setDescription("[Click here for invite, vote, and Support Server](https://discordbotlist.com/bots/lisebot)");



        return this.embedBuilder;
    }




}
