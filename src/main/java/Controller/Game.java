import chariot.Client;
import net.dv8tion.jda.api.EmbedBuilder;



public class Game {

    private Client client;

    private EmbedBuilder embedBuilder;

    private String variant;

    private String challengeType;

    private String random = "";


    public Game(){

    }


    public Game(Client client, String variant, String challengeType){
        this.client = client;
        this.variant = variant;
        this.challengeType = challengeType;

    }



    public void DifferentGameGenVar(int min, int sec, String type, int key){

       switch (key){
           case 3:
               if(type.equals("r")){

                   int timeer = min*60;

                   Client clientone = Client.basic();


                   var result = clientone.challenges().challengeOpenEnded(conf -> conf.clock(timeer, sec).name("LISEBOT Challenge Created! Please Join the Game!").variant(provider -> provider.threeCheck()).rated(true));

                   result.ifPresent(play -> {

                       this.random += play.challenge().url();
                   });
               }else{
                   int timeer = min*60;

                   Client clientone = Client.basic();


                   var result = clientone.challenges().challengeOpenEnded(conf -> conf.clock(timeer, sec).name("LISEBOT Challenge Created! Please Join the Game!").variant(provider -> provider.threeCheck()).rated(false));

                   result.ifPresent(play -> {


                       this.random += play.challenge().url();
                   });
               }
               break;

           case 9:
               if(type.equals("r")){

                   int timeer = min*60;

                   Client clientone = Client.basic();


                   var result = clientone.challenges().challengeOpenEnded(conf -> conf.clock(timeer, sec).name("LISEBOT Challenge Created! Please Join the Game!").variant(provider -> provider.chess960()).rated(true));

                   result.ifPresent(play -> {


                       this.random += play.challenge().url();
                   });
               }else{
                   int timeer = min*60;

                   Client clientone = Client.basic();


                   var result = clientone.challenges().challengeOpenEnded(conf -> conf.clock(timeer, sec).name("LISEBOT Challenge Created! Please Join the Game!").variant(provider -> provider.chess960()).rated(false));

                   result.ifPresent(play -> {


                       this.random += play.challenge().url();
                   });
               }
               break;
           case 2:
               if(type.equals("r")){

                   int timeer = min*60;

                   Client clientone = Client.basic();


                   var result = clientone.challenges().challengeOpenEnded(conf -> conf.clock(timeer, sec).name("LISEBOT Challenge Created! Please Join the Game!").variant(provider -> provider.atomic()).rated(true));

                   result.ifPresent(play -> {


                       this.random += play.challenge().url();
                   });
               }else{
                   int timeer = min*60;

                   Client clientone = Client.basic();


                   var result = clientone.challenges().challengeOpenEnded(conf -> conf.clock(timeer, sec).name("LISEBOT Challenge Created! Please Join the Game!").variant(provider -> provider.atomic()).rated(false));

                   result.ifPresent(play -> {


                       this.random += play.challenge().url();
                   });
               }
               break;

       }




    }


    public void DifferentGameGen(int min, int sec, String type){

        if(type.equals("r")){
            if(min == 0){
                int ultra = 15;

                Client clientone = Client.basic();


                var result = clientone.challenges().challengeOpenEnded(conf -> conf.clock(ultra, sec).name("LISEBOT Challenge Created! Please Join the Game!").rated(true));

                result.ifPresent(play -> {


                    this.random += play.challenge().url();
                });
            }else {

                int timeer = min * 60;

                Client clientone = Client.basic();


                var result = clientone.challenges().challengeOpenEnded(conf -> conf.clock(timeer, sec).name("LISEBOT Challenge Created! Please Join the Game!").rated(true));

                result.ifPresent(play -> {


                    this.random += play.challenge().url();
                });
            }
        }else{
            if(min == 0){
                int ultraC = 15;

                Client clientone = Client.basic();


                var result = clientone.challenges().challengeOpenEnded(conf -> conf.clock(ultraC, sec).name("LISEBOT Challenge Created! Please Join the Game!").rated(false));

                result.ifPresent(play -> {


                    this.random += play.challenge().url();
                });
            }
            {
                int timeer = min * 60;

                Client clientone = Client.basic();


                var result = clientone.challenges().challengeOpenEnded(conf -> conf.clock(timeer, sec).name("LISEBOT Challenge Created! Please Join the Game!").rated(false));

                result.ifPresent(play -> {


                    this.random += play.challenge().url();
                });
            }
        }


    }


    public String getRandom() {
        return random;
    }


}