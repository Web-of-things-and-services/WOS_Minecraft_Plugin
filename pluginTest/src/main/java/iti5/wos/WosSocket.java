package iti5.wos;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

import java.net.URISyntaxException;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;

public class WosSocket {
    private Socket socket;
    private Game game;
    public WosSocket(String uri, Game game) throws URISyntaxException {
        Socket socket = IO.socket(uri);
        this.game = game;

        socket.on("new_move", new Emitter.Listener(){
            @Override
            public void call(Object... args) {
                System.out.println("[Puissance 4] [Received] : new_move");
                String data = args[0].toString();
                try {
                    JSONParser parser = new JSONParser();
                    JSONObject jsonObject = (JSONObject) parser.parse(data);
                    System.out.println("[Puissance 4] board : " + jsonObject);
                    game.movePlayed(
                        Integer.parseInt((String) jsonObject.get("column")), // Column Num
                            (String) jsonObject.get("name") // Pseudo player
                    );
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


        socket.on("game_status", new Emitter.Listener(){
            @Override
            public void call(Object... args) {
                System.out.println("[Puissance 4] [Received] : game_status");

                // TODO reçois le status de la partie

            }
        });

        
        socket.on("start_game", new Emitter.Listener(){
            @Override
            public void call(Object... args) {
                System.out.println("[Puissance 4] [Received] : start_game");
                game.reset();
                // TODO y a des choses passés en param mais ils servent à R ?

            }
        });

        // socket.on("start_game_error", new Emitter.Listener(){
        //     @Override
        //     public void call(Object... args) {
        //         System.out.println("[Puissance 4] [Received] : start_game_error");

        //         // TODO y a des choses passés en param mais ils servent à R ?

        //     }
        // });
        
        // socket.on("init_game_after_reset", new Emitter.Listener(){
        //     @Override
        //     public void call(Object... args) {
        //         System.out.println("[Puissance 4] [Received] : init_game_after_reset");
        //         try {
        //             JSONParser parser = new JSONParser();
        //             JSONArray jsonBoard = (JSONArray) parser.parse(args[0].toString()); // le json représentant le tableau
        //             String nextPlayer = args[1].toString(); // le nom du prochain joueur
        //             System.out.println("[Puissance 4] board : " + jsonBoard + ", nextPlayer : " + nextPlayer);

        //             // TODO jsonBoard nextPlayer

        //         } catch (Exception e) {
        //             e.printStackTrace();
        //         }
        //     }
        // });
        
        socket.on("connect_player", new Emitter.Listener(){
            @Override
            public void call(Object... args) {
                System.out.println("[Puissance 4] [Received] : connect_player");
                String playerName = args[0].toString();
                System.out.println("[Puissance 4] name : " + playerName);

                // TODO playerName

            }
        });
        
        socket.on("waiting_move", new Emitter.Listener(){
            @Override
            public void call(Object... args) {
                System.out.println("[Puissance 4] [Received] : waiting_move");
                String playerName = args[0].toString();
                System.out.println("[Puissance 4] name : " + playerName);

                // TODO playerName

            }
        });

        socket.on("end_game", new Emitter.Listener(){
            @Override
            public void call(Object... args) {
                System.out.println("[Puissance 4] [Received] : end_game");
                String winnerName = args[0].toString();
                System.out.println("[Puissance 4] Winner : " + winnerName);

                // TODO winnerName (peut etre "nobody")

                game.end(winnerName);

            }
        });
        
        socket.on("bad_player", new Emitter.Listener(){
            @Override
            public void call(Object... args) {
                System.out.println("[Puissance 4] [Received] : bad_player");
                String badPlayerName = args[0].toString();
                System.out.println("[Puissance 4] player : " + badPlayerName);

                // TODO badPlayerName

            }
        });
        
        socket.on("disconnected_player", new Emitter.Listener(){
            @Override
            public void call(Object... args) {
                System.out.println("[Puissance 4] [Received] : disconnected_player");

                // TODO (pareil y a des params passés jsp sil tu ten serviras) 

            }
        });

        socket.on("stop_game", new Emitter.Listener(){
            @Override
            public void call(Object... args) {
                System.out.println("[Puissance 4] [Received] : stop_game");

                game.reset();
                // TODO (pareil y a des params passés jsp sil tu ten serviras) 

            }
        });

        // socket.on("waiting_move", new Emitter.Listener(){
        //     @Override
        //     public void call(Object... args) {
        //         System.out.println("[Puissance 4] [Received] : waiting_move");
        //     }
        // });

        // socket.on("start_game", new Emitter.Listener(){
        //     @Override
        //     public void call(Object... args) {
        //         System.out.println("[Puissance 4] [Received] : " + "start_game");
        //     }
        // });

        // socket.on("message", new Emitter.Listener(){
        //     @Override
        //     public void call(Object... args) {
        //         System.out.println("[Puissance 4] [Received] : " + "message");
        //     }
        // });

        // socket.on("game_status", new Emitter.Listener(){
        //     @Override
        //     public void call(Object... args) {
        //         System.out.println("[Puissance 4] [Received] : " + "game_status");
        //     }
        // });

        // socket.on("stop_game", new Emitter.Listener(){
        //     @Override
        //     public void call(Object... args) {
        //         System.out.println("[Puissance 4] [Received] : " + "stop_game");
        //     }
        // });

        // socket.on("disconnect", new Emitter.Listener(){
        //     @Override
        //     public void call(Object... args) {
        //         System.out.println("[Puissance 4] [Received] : " + "game_status");
        //     }
        // });

        socket.connect();
        this.socket = socket;
    }

    public boolean play(String pseudo){
        System.out.println("[Puissance 4] [Emit] : " + "connect_player");
        socket.emit("connect_player", pseudo);
        return true;
    }

    public boolean move(JSONObject request){
        try{
            System.out.println("[Puissance 4] [Emit] : " + "new_move");
            socket.emit("new_move", request);
        }
        catch(Exception e){
            System.out.println("[Puissance 4] " + e);
        }
        return true;
    }

    public void disconnect(){
        System.out.println("[Puissance 4] [Disconnect]" );
        socket.disconnect();
    }
}
