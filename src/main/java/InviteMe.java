import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;

public class InviteMe {

    private EmbedBuilder embedBuilder;

    public InviteMe(){
        this.embedBuilder = new EmbedBuilder();
    }

    public EmbedBuilder getInviteInfo(){
        this.embedBuilder = new EmbedBuilder();
        this.embedBuilder.setColor(Color.white);
        this.embedBuilder.setTitle("invite me!");
        this.embedBuilder.setDescription("\n [Click Here to invite LiSEBot](" + "https://discord.com/api/oauth2/authorize?client_id=896441195063029860&permissions=0&scope=bot%20applications.commands" + ")");



        return this.embedBuilder;
    }




}
