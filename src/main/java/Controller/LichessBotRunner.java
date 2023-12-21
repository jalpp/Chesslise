import chariot.Client;
import chariot.model.Enums;
import chariot.model.Event;
import chariot.model.GameStateEvent;
import chariot.model.VariantType;
import chariot.model.Enums.Speed;

import com.github.bhlangonijr.chesslib.*;
import com.github.bhlangonijr.chesslib.move.Move;

import java.util.Random;

public class LichessBotRunner {

    public LichessBotRunner(){

    }


    public static void main(String[] args) {

        var client = Client.auth(System.getenv("lichess_bot_token"));
        var bot = client.bot();
        var events = bot.connect().stream();
        var username = client.account().profile().get().name().toLowerCase();
        String[] s = {"maia1", "maia5", "maia9", "charibot",  "TurtleBot", "SxRandom", "zeekat", "roundmoundofrebounds", "WorstFish", "pawnrobot", "knucklefish", "LeelaRogue"};
        int picker = s.length;
        int index = new Random().nextInt(picker);
        String botname = s[index];
        try {
            bot.challengeKeepAlive(botname, challengeBuilder -> challengeBuilder.clockBlitz3m2s().rated(false).color(Enums.ColorPref.white));
        }catch (Exception e){
            System.out.println("Failed Challege");
        }

        events.forEach(event -> {


            switch (event.type()) {

                case challenge:

                    var challenge = (Event.ChallengeEvent) event;
                    boolean std = challenge.challenge().gameType().variant() == VariantType.Variant.standard;
                    boolean non_rated = challenge.challenge().gameType().rated();
                    boolean isCoores = challenge.challenge().gameType().timeControl().speed() == Speed.correspondence;
                    if(std && !non_rated && !isCoores ){
                        bot.acceptChallenge(event.id());
                    }else if(non_rated){
                        bot.declineChallenge(event.id(), provider -> provider.casual());

                    }else if(isCoores) {
                        bot.declineChallenge(event.id(), provider -> provider.timeControl());

                    }
                    else{
                        bot.declineChallenge(event.id(), provider -> provider.variant());
                    }

                    break;



                case challengeDeclined:
                    bot.abort(event.id());

                    break;

                case challengeCanceled:
                    bot.abort(event.id());

                    break;

                case gameFinish:
                    bot.chat(event.id(), "Thanks for playing me! ggs");
                    bot.chatSpectators(event.id(), "Thanks for watching!");

                    break;


                case gameStart:

                    var gameEvents = bot.connectToGame(event.id()).stream();
                    var board = new Board();
                    var engine = new LiseChessEngine(board);
                    boolean[] isWhite = {false};



                    bot.chat(event.id(), "omg you are very strong.. I'm scared but hey good luck!!");


                    
                    gameEvents.forEach(gameEvent -> {

    


                        switch (gameEvent.type()) {


                            case opponentGone:
                                bot.abort(event.id());

                                break;

                            case gameFull:

                                try {

                                    isWhite[0] = ((GameStateEvent.Full) gameEvent).white().name().toLowerCase().equals(username);
                                    if (isWhite[0]) {
                                        try {

                                            var move = engine.generateMoveFromIndexLookUp(board);
                                            bot.move(event.id(), move.toString());
                                            board.doMove(move);

                                        } catch (Exception e) {
                                            bot.resign(event.id());
                                        }
                                    }

                                }catch (Exception e){
                                    bot.resign(event.id());
                                    System.out.println(e.getMessage());
                                }

                                break;

                            case gameState:
                                try {

                                    var names = ((GameStateEvent.State) gameEvent).moves().split(" ");
                                    var whiteTurn = names.length % 2 == 0;

                                    if (isWhite[0] == whiteTurn) {
                                        try {
                                            var name = names[names.length - 1];
                                            var from = Square.fromValue(name.substring(0, 2).toUpperCase());
                                            var to = Square.fromValue(name.substring(2, 4).toUpperCase());
                                            if (names.length == 5) {
                                                var type = PieceType.fromSanSymbol(name.substring(4).toUpperCase());
                                                var piece = Piece.make(whiteTurn ? Side.WHITE : Side.BLACK, type);
                                                var move = new Move(from, to, piece);
                                                board.doMove(move);
                                            } else {
                                                var move = new Move(from, to);
                                                board.doMove(move);
                                            }
                                        }catch (Exception e){
                                            bot.resign(event.id());
                                        }


                                        var move = engine.generateMoveFromIndexLookUp(board);
                                        bot.move(event.id(), move.toString());
                                        board.doMove(move);

                                    }

                                }catch (Exception e){
                                    bot.resign(event.id());

                                }
                                break;
                        }
                    });



                    break;
            }
        });


    }



}
