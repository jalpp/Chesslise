import chariot.model.Trophy;

import java.util.HashMap;

public class UserTrophy {

    private final String trophyName;
    private Trophy trophy;



    public UserTrophy(Trophy trophy){

        this.trophy = trophy;
        this.trophyName = trophy.name();

    }


    public String getImageLink(){


        HashMap<String, String> getLink = new HashMap<>();


        getLink.put("Marathon Winner", "\uD83D\uDD2E");
        getLink.put("Marathon Top 10", "\uD83C\uDF15");
        getLink.put("Other","\uD83C\uDFC6");
        getLink.put("Verified account", "✅");
        getLink.put("Lichess moderator", "\uD83D\uDC41️" );
        getLink.put("Lichess content team","✍️");
        getLink.put("Lichess developer", "\uD83D\uDEE0️");



        String imageLink;
        if(this.trophyName.equals("Marathon Winner")){
            imageLink = getLink.get("Marathon Winner");
        }
        else if(this.trophyName.equals("Marathon Top 10")){
            imageLink = getLink.get("Marathon Top 10");
        }
        else if(!this.trophyName.equals("Verified account") && !this.trophyName.equals("Lichess moderator") && !this.trophyName.equals("Lichess content team") && !this.trophyName.equals("Lichess developer")){
            imageLink = getLink.get("Other");
        }
        else if(this.trophyName.equals("Verified account")){
            imageLink = getLink.get("Verified account");
        }
        else if(this.trophyName.equals("Lichess moderator")){
            imageLink = getLink.get("Lichess moderator");
        }else if(this.trophyName.equals("Lichess content team")){
            imageLink = getLink.get("Lichess content team");
        }else {
            imageLink = getLink.get("Lichess developer");
        }


        return this.ImageLink + " " + this.trophyName;



    }


}
