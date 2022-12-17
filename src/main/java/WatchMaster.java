import chariot.Client;
import chariot.model.ExploreResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class WatchMaster {

    private Client client;
    private String masterGif;
    private String openingGif;


    public WatchMaster(Client client){
        this.client = client;
        this.masterGif = "";
        this.openingGif = "";

    }




    public String getMasterGames(){

        String e4games = "";
        String d4games = "";
        String Nf3games = "";
        String c4games = "";
        List<String> masterGifs = new ArrayList<>();
        Random random = new Random();



        List<ExploreResult.DBGame> gamesE4 = chariot.Client.basic().openingExplorer().masters(conf -> conf.play("e2e4")).get().topGames();
        List<ExploreResult.DBGame> gamesD4 = chariot.Client.basic().openingExplorer().masters(conf -> conf.play("d2d4")).get().topGames();
        List<ExploreResult.DBGame> gamesNf3 = chariot.Client.basic().openingExplorer().masters(conf -> conf.play("g1f3")).get().topGames();
        List<ExploreResult.DBGame> gamesc4 = chariot.Client.basic().openingExplorer().masters(conf -> conf.play("c2c4")).get().topGames();



        int randome4size = random.nextInt(gamesE4.size());
        int randomd4size = random.nextInt(gamesD4.size());
        int randomnf3size = random.nextInt(gamesNf3.size());
        int randomc4size = random.nextInt(gamesc4.size());


        e4games += "https://lichess1.org/game/export/gif/" + gamesE4.get(randome4size).id() + ".gif";
        d4games += "https://lichess1.org/game/export/gif/" + gamesD4.get(randomd4size).id() + ".gif";
        Nf3games += "https://lichess1.org/game/export/gif/" + gamesNf3.get(randomnf3size).id() + ".gif";
        c4games += "https://lichess1.org/game/export/gif/" + gamesc4.get(randomc4size).id() + ".gif";

        masterGifs.add(e4games);
        masterGifs.add(d4games);
        masterGifs.add(Nf3games);
        masterGifs.add(c4games);

        int gifrandom = random.nextInt(4);

        this.masterGif = masterGifs.get(gifrandom);


        return masterGif;
    }

    public String getOpenings(String moves){
        Random random = new Random();
        List<ExploreResult.DBGame> games = chariot.Client.basic().openingExplorer().masters(conf -> conf.play(moves)).get().topGames();
        int randomsizer = random.nextInt(games.size());

        this.openingGif +=  "https://lichess1.org/game/export/gif/" + games.get(randomsizer).id() + ".gif";

        return this.openingGif;
    }

    public String[] getOpeningId(){
        String[] split = this.openingGif.split(".gif");

        return split;
    }




    public String[] getGameId(){
        String[] split = this.masterGif.split(".gif");
        return  split;
    }






}
