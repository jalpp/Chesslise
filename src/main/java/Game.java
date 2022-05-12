import chariot.Client;
import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;

public class Game {

    private Client client;
    private EmbedBuilder embedBuilder;
    private String variant;
    private String challengeType;


    public Game(Client client, String variant, String challengeType){
        this.client = client;
        this.variant = variant;
        this.challengeType = challengeType;
    }


    /**
     *
     *
     * ,play command gives the user an open ended challenge where the two users can join to play
     *
     * input:  ,play [variant] [rated/casual]
     *
     *
     * output: the openended challenge for 2 users to play
     *
     *
     *
     *
     * ≤ 29s = UltraBullet
     * ≤ 179s = Bullet
     * ≤ 479s = Blitz
     * ≤ 1499s = Rapid
     * ≥ 1500s = Classical
     *
     *
     *
     *
     *
     * */


    public EmbedBuilder getNewGame(){

        this.embedBuilder = new EmbedBuilder();

        if (this.variant.equals("rapid")) {


            if (this.challengeType.equals("rated")) {


                var clientone = Client.basic();

                var result = clientone.challenges().challengeOpenEnded(conf -> conf.clock(300, 5).rated(true));

                result.ifPresent(play -> {
                    this.embedBuilder = new EmbedBuilder();
                    this.embedBuilder.setColor(Color.white);
                    this.embedBuilder.setTitle("Challenge rapid rated loaded!");
                    this.embedBuilder.setDescription("\n player playing white [Click to Join the game](" + play.urlWhite() + ")" + "\n\n player playing black [Click to join the game](" + play.urlBlack() + ")");

                });


            }


            else if (this.challengeType.equals("casual")) {


                var clientone = Client.basic();

                var result = clientone.challenges().challengeOpenEnded(conf -> conf.clock(300, 5).rated(false));

                result.ifPresent(play -> {
                    this.embedBuilder = new EmbedBuilder();
                    this.embedBuilder.setColor(Color.white);
                    this.embedBuilder.setTitle("Challenge rapid Casual loaded!");
                    this.embedBuilder.setDescription("\n player playing white [Click to Join the game](" + play.urlWhite() + ")" + "\n\n player playing black [Click to join the game](" + play.urlBlack() + ")");


                });


            }



        }


        if (this.variant.equals("blitz")) {


            if (this.challengeType.equals("rated")) {


                var clientone = Client.basic();

                var result = clientone.challenges().challengeOpenEnded(conf -> conf.clock(180, 2).rated(true));

                result.ifPresent(play -> {
                    this.embedBuilder = new EmbedBuilder();
                    this.embedBuilder.setColor(Color.white);
                    this.embedBuilder.setTitle("Challenge Blitz rated loaded!");
                    this.embedBuilder.setDescription("\n player playing white [Click to Join the game](" + play.urlWhite() + ")" + "\n\n player playing black [Click to join the game](" + play.urlBlack() + ")");


                });


            }


            if (this.challengeType.equals("casual")) {


                var clientone = Client.basic();

                var result = clientone.challenges().challengeOpenEnded(conf -> conf.clock(180, 2).rated(false));

                result.ifPresent(play -> {
                    this.embedBuilder = new EmbedBuilder();
                    this.embedBuilder.setColor(Color.white);
                    this.embedBuilder.setTitle("Challenge Blitz Casual loaded!");
                    this.embedBuilder.setDescription("\n player playing white [Click to Join the game](" + play.urlWhite() + ")" + "\n\n player playing black [Click to join the game](" + play.urlBlack() + ")");


                });


            }


        }



        if (this.variant.equals("classical")) {


            if (this.challengeType.equals("rated")) {


                var clientone = Client.basic();

                var result = clientone.challenges().challengeOpenEnded(conf -> conf.clock(  1800 , 20).rated(true));

                result.ifPresent(play -> {
                    this. embedBuilder = new EmbedBuilder();
                    this.embedBuilder.setColor(Color.white);
                    this.embedBuilder.setTitle("Challenge Classical rated loaded!");
                    this.embedBuilder.setDescription("\n player playing white [Click to Join the game](" + play.urlWhite() + ")" + "\n\n player playing black [Click to join the game](" + play.urlBlack() + ")");


                });


            }



            if (this.challengeType.equals("casual")) {


                var clientone = Client.basic();

                var result = clientone.challenges().challengeOpenEnded(conf -> conf.clock(  1800 , 20).rated(false));

                result.ifPresent(play -> {
                    this.embedBuilder = new EmbedBuilder();
                    this. embedBuilder.setColor(Color.white);
                    this.embedBuilder.setTitle("Challenge Classical Casual loaded!");
                    this.embedBuilder.setDescription("\n player playing white [Click to Join the game](" + play.urlWhite() + ")" + "\n\n player playing black [Click to join the game](" + play.urlBlack() + ")");


                });


            }


        }


        if (this.variant.equals("bullet")) {


            if (this.challengeType.equals("rated")) {


                var clientone = Client.basic();

                var result = clientone.challenges().challengeOpenEnded(conf -> conf.clock(  60 , 0).rated(true));

                result.ifPresent(play -> {
                    this.embedBuilder = new EmbedBuilder();
                    this.embedBuilder.setColor(Color.white);
                    this.embedBuilder.setTitle("Challenge Bullet rated loaded!");
                    this.embedBuilder.setDescription("\n player playing white [Click to Join the game](" + play.urlWhite() + ")" + "\n\n player playing black [Click to join the game](" + play.urlBlack() + ")");


                });


            }


            if (this.challengeType.equals("casual")) {


                var clientone = Client.basic();

                var result = clientone.challenges().challengeOpenEnded(conf -> conf.clock(  60 , 0).rated(false));

                result.ifPresent(play -> {
                    this.embedBuilder = new EmbedBuilder();
                    this.embedBuilder.setColor(Color.white);
                    this.embedBuilder.setTitle("Challenge Bullet casual loaded!");
                    this.embedBuilder.setDescription("\n player playing white [Click to Join the game](" + play.urlWhite() + ")" + "\n\n player playing black [Click to join the game](" + play.urlBlack() + ")");


                });


            }



        }



        if (this.variant.equals("ultrabullet")) {


            if (this.challengeType.equals("rated")) {


                var clientone = Client.basic();

                var result = clientone.challenges().challengeOpenEnded(conf -> conf.clock(  15 , 0).rated(true));

                result.ifPresent(play -> {
                    this.embedBuilder = new EmbedBuilder();
                    this.embedBuilder.setColor(Color.white);
                    this.embedBuilder.setTitle("Challenge UltraBullet rated loaded!");
                    this.embedBuilder.setDescription("\n player playing white [Click to Join the game](" + play.urlWhite() + ")" + "\n\n player playing black [Click to join the game](" + play.urlBlack() + ")");


                });


            }

            if (this.challengeType.equals("casual")) {


                var clientone = Client.basic();

                var result = clientone.challenges().challengeOpenEnded(conf -> conf.clock(  15 , 0).rated(false));

                result.ifPresent(play -> {
                    this.embedBuilder = new EmbedBuilder();
                    this.embedBuilder.setColor(Color.white);
                    this.embedBuilder.setTitle("Challenge UltraBullet casual loaded!");
                    this.embedBuilder.setDescription("\n player playing white [Click to Join the game](" + play.urlWhite() + ")" + "\n\n player playing black [Click to join the game](" + play.urlBlack() + ")");


                });


            }



        }

        return this.embedBuilder;

    }








}
