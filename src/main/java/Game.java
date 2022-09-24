import chariot.Client;
import net.dv8tion.jda.api.EmbedBuilder;


public class Game {

    private Client client;
    private EmbedBuilder embedBuilder;
    private String variant;
    private String challengeType;

    private String black = "";
    private String white = "";
    private String random = "";

    public Game(){

    }



    public Game(Client client, String variant, String challengeType){
        this.client = client;
        this.variant = variant;
        this.challengeType = challengeType;

    }


    public void DifferentGameGen(int min, int sec, String type){

        if(type.equals("r")){

            int timeer = min*60;

            Client clientone = Client.basic();


            var result = clientone.challenges().challengeOpenEnded(conf -> conf.clock(timeer, sec).name("LISEBOT Challenge Created! Please Join the Game!").rated(true));

            result.ifPresent(play -> {

                this.black += play.urlBlack();
                this.white += play.urlWhite();
                this.random += play.challenge().url();
            });
        }else{
            int timeer = min*60;

            Client clientone = Client.basic();


            var result = clientone.challenges().challengeOpenEnded(conf -> conf.clock(timeer, sec).name("LISEBOT Challenge Created! Please Join the Game!").rated(false));

            result.ifPresent(play -> {

                this.black += play.urlBlack();
                this.white += play.urlWhite();
                this.random += play.challenge().url();
            });
        }


    }





    public void getNewGame(){

        this.embedBuilder = new EmbedBuilder();


        if (this.variant.equals("rapid")) {


            if (this.challengeType.equals("rated")) {


                Client clientone = Client.basic();


                var result = clientone.challenges().challengeOpenEnded(conf -> conf.clock(300, 5).name("LISEBOT Challenge Created! Please Join the Game!").rated(true));

                result.ifPresent(play -> {

                    this.black += play.urlBlack();
                    this.white += play.urlWhite();
                    this.random += play.challenge().url();
                });


            }


            else if (this.challengeType.equals("casual")) {


                var clientone = Client.basic();

                var result = clientone.challenges().challengeOpenEnded(conf -> conf.clock(300, 5).name("LISEBOT Challenge Created! Please Join the Game!").rated(false));

                result.ifPresent(play -> {

                    this.black += play.urlBlack();
                    this.white += play.urlWhite();
                    this.random += play.challenge().url();

                });


            }



        }


        if (this.variant.equals("blitz")) {


            if (this.challengeType.equals("rated")) {


                var clientone = Client.basic();

                var result = clientone.challenges().challengeOpenEnded(conf -> conf.clock(180, 2).name("LISEBOT Challenge Created! Please Join the Game!").rated(true));

                result.ifPresent(play -> {

                    this.black += play.urlBlack();
                    this.white += play.urlWhite();
                    this.random += play.challenge().url();



                });


            }


            if (this.challengeType.equals("casual")) {


                var clientone = Client.basic();

                var result = clientone.challenges().challengeOpenEnded(conf -> conf.clock(180, 2).name("LISEBOT Challenge Created! Please Join the Game!").rated(false));

                result.ifPresent(play -> {

                    this.black += play.urlBlack();
                    this.white += play.urlWhite();
                    this.random += play.challenge().url();


                });


            }


        }




        if (this.variant.equals("classical")) {


            if (this.challengeType.equals("rated")) {


                var clientone = Client.basic();

                var result = clientone.challenges().challengeOpenEnded(conf -> conf.clock(  1800 , 20).name("LISEBOT Challenge Created! Please Join the Game!").rated(true));

                result.ifPresent(play -> {

                    this.black += play.urlBlack();
                    this.white += play.urlWhite();
                    this.random += play.challenge().url();


                });


            }



            if (this.challengeType.equals("casual")) {


                var clientone = Client.basic();

                var result = clientone.challenges().challengeOpenEnded(conf -> conf.clock(  1800 , 20).name("LISEBOT Challenge Created! Please Join the Game!").rated(false));

                result.ifPresent(play -> {

                    this.black += play.urlBlack();
                    this.white += play.urlWhite();
                    this.random += play.challenge().url();


                });


            }


        }


        if (this.variant.equals("bullet")) {


            if (this.challengeType.equals("rated")) {


                var clientone = Client.basic();

                var result = clientone.challenges().challengeOpenEnded(conf -> conf.clock(  60 , 0).name("LISEBOT Challenge Created! Please Join the Game!").rated(true));

                result.ifPresent(play -> {
                    this.black += play.urlBlack();
                    this.white += play.urlWhite();
                    this.random += play.challenge().url();

                });


            }


            if (this.challengeType.equals("casual")) {


                var clientone = Client.basic();

                var result = clientone.challenges().challengeOpenEnded(conf -> conf.clock(  60 , 0).name("LISEBOT Challenge Created! Please Join the Game!").rated(false));

                result.ifPresent(play -> {
                    this.black += play.urlBlack();
                    this.white += play.urlWhite();
                    this.random += play.challenge().url();


                });


            }



        }



        if (this.variant.equals("ultrabullet")) {


            if (this.challengeType.equals("rated")) {


                var clientone = Client.basic();

                var result = clientone.challenges().challengeOpenEnded(conf -> conf.clock(  15 , 0).name("LISEBOT Challenge Created! Please Join the Game!").rated(true));

                result.ifPresent(play -> {
                    this.black += play.urlBlack();
                    this.white += play.urlWhite();
                    this.random += play.challenge().url();

                });


            }

            if (this.challengeType.equals("casual")) {


                var clientone = Client.basic();

                var result = clientone.challenges().challengeOpenEnded(conf -> conf.clock(  15 , 0).name("LISEBOT Challenge Created! Please Join the Game!").rated(false));

                result.ifPresent(play -> {
                    this.black += play.urlBlack();
                    this.white += play.urlWhite();
                    this.random += play.challenge().url();

                });


            }



        }



    }

    public String getBlack() {
        return black;
    }

    public String getRandom() {
        return random;
    }

    public String getWhite() {
        return white;
    }
}
