import chariot.model.Trophy;

import java.util.HashMap;

public class UserTrophy {

    private String trophyName;
    private String ImageLink;
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





        if(this.trophyName.equals("Marathon Winner")){
            this.ImageLink = getLink.get("Marathon Winner");
        }
        else if(this.trophyName.equals("Marathon Top 10")){
            this.ImageLink = getLink.get("Marathon Top 10");
        }
        else if(!this.trophyName.equals("Marathon Winner") && !this.trophyName.equals("Marathon Top 10") && !this.trophyName.equals("Verified account") && !this.trophyName.equals("Lichess moderator") && !this.trophyName.equals("Lichess content team") && !this.trophyName.equals("Lichess developer")){
            this.ImageLink = getLink.get("Other");
        }
        else if(this.trophyName.equals("Verified account")){
            this.ImageLink = getLink.get("Verified account");
        }
        else if(this.trophyName.equals("Lichess moderator")){
            this.ImageLink = getLink.get("Lichess moderator");
        }else if(this.trophyName.equals("Lichess content team")){
            this.ImageLink = getLink.get("Lichess content team");
        }else if(this.trophyName.equals("Lichess developer")){
            this.ImageLink = getLink.get("Lichess developer");
        }







        return this.ImageLink + " " + this.trophyName;



    }


}
