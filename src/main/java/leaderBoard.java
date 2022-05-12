import chariot.Client;
import chariot.model.Result;
import chariot.model.User;
import chariot.model.UserPerformance;
import chariot.model.UserTopAll;
import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;
import java.util.List;

public class leaderBoard {

    private Client client;
    private EmbedBuilder embedBuilder;


    public leaderBoard(Client client){
        this.client = client;

    }


    /**
     * ,top10 command to see the top 10 players in the given variant
     *
     * input: Lichess variant
     *
     * output: The top 10 list with titled plus username and the rating
     */


    public EmbedBuilder getBlitzBoard(){
        Result<UserTopAll> top10 = this.client.users().top10();

        UserTopAll top = top10.get();


        List<UserPerformance> blitzPer = top.blitz();

        String output = "";

        for (int i = 0; i < 10; i++) {
            UserPerformance userPerformance = blitzPer.get(i);

            Result<User> topPlayer = this.client.users().byId(userPerformance.username());

            int rating = topPlayer.get().perfs().blitz().maybe().get().rating();

            String url = topPlayer.get().url();

            int j = i + 1;


            output += j + " " + userPerformance.title() + " " + "[" + userPerformance.username() + "]" + "(" + url + ")" + " " + rating + "\n";
        }


        this.embedBuilder = new EmbedBuilder();
        this.embedBuilder.setColor(Color.WHITE);
        this.embedBuilder.setTitle("Top 10 for Blitz");
        this.embedBuilder.setDescription(output);


        return this.embedBuilder;
    }


    public EmbedBuilder getClassicalBoard(){
        Result<UserTopAll> top10 = this.client.users().top10();

        UserTopAll top = top10.get();


        List<UserPerformance> classicalPer = top.classical();

        String output = "";

        for (int i = 0; i < 10; i++) {
            UserPerformance userPerformance = classicalPer.get(i);

            Result<User> topPlayer = this.client.users().byId(userPerformance.username());

            int rating = topPlayer.get().perfs().classical().maybe().get().rating();

            String url = topPlayer.get().url();

            int j = i + 1;


            output += j + " " + userPerformance.title() + " " + "[" + userPerformance.username() + "]" + "(" + url + ")" + " " + rating + "\n";
        }


        this.embedBuilder = new EmbedBuilder();
        this.embedBuilder.setColor(Color.WHITE);
        this.embedBuilder.setTitle("Top 10 for Classical");
        this.embedBuilder.setDescription(output);

        return this.embedBuilder;

    }


    public EmbedBuilder getRapidBoard(){
        Result<UserTopAll> top10 = this.client.users().top10();

        UserTopAll top = top10.get();


        List<UserPerformance> rapidPer = top.rapid();

        String output = "";

        for (int i = 0; i < 10; i++) {
            UserPerformance userPerformance = rapidPer.get(i);

            Result<User> topPlayer = this.client.users().byId(userPerformance.username());

            int rating = topPlayer.get().perfs().rapid().maybe().get().rating();

            String url = topPlayer.get().url();

            int j = i + 1;


            output += j + " " + userPerformance.title() + " " + "[" + userPerformance.username() + "]" + "(" + url + ")" + " " + rating + "\n";
        }


        this.embedBuilder = new EmbedBuilder();
        this.embedBuilder.setColor(Color.WHITE);
        this.embedBuilder.setTitle("Top 10 for Rapid");
        this.embedBuilder.setDescription(output);

        return this.embedBuilder;

    }


    public EmbedBuilder getBulletBoard(){
        Result<UserTopAll> top10 = this.client.users().top10();

        UserTopAll top = top10.get();


        List<UserPerformance> bulletPer = top.bullet();

        String output = "";

        for (int i = 0; i < 10; i++) {
            UserPerformance userPerformance = bulletPer.get(i);

            Result<User> topPlayer = this.client.users().byId(userPerformance.username());

            int rating = topPlayer.get().perfs().bullet().maybe().get().rating();

            String url = topPlayer.get().url();

            int j = i + 1;


            output += j + " " + userPerformance.title() + " " + "[" + userPerformance.username() + "]" + "(" + url + ")" + " " + rating + "\n";
        }


        this.embedBuilder = new EmbedBuilder();
        this.embedBuilder.setColor(Color.WHITE);
        this.embedBuilder.setTitle("Top 10 for Bullet");
        this.embedBuilder.setDescription(output);

        return this.embedBuilder;
    }


    public EmbedBuilder getUltraBoard(){

        Result<UserTopAll> top10 = this.client.users().top10();

        UserTopAll top = top10.get();


        List<UserPerformance> bulletPer = top.ultraBullet();

        String output = "";

        for (int i = 0; i < 10; i++) {
            UserPerformance userPerformance = bulletPer.get(i);

            Result<User> topPlayer = this.client.users().byId(userPerformance.username());

            int rating = topPlayer.get().perfs().ultraBullet().maybe().get().rating();

            String url = topPlayer.get().url();

            int j = i + 1;


            output += j + " " + userPerformance.title() + " " + "[" + userPerformance.username() + "]" + "(" + url + ")" + " " + rating + "\n";
        }


        this.embedBuilder = new EmbedBuilder();
        this.embedBuilder.setColor(Color.WHITE);
        this.embedBuilder.setTitle("Top 10 for Ultrabullet");
        this.embedBuilder.setDescription(output);

        return this.embedBuilder;

    }






}
