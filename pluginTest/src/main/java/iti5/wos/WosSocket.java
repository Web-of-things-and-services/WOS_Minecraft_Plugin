package iti5.wos;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

import java.net.URISyntaxException;
import org.json.simple.JSONObject;
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
                System.out.println("[Puissance 4] Received : new_move");
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

        socket.on("waiting_move", new Emitter.Listener(){
            @Override
            public void call(Object... args) {
                System.out.println("[Puissance 4] Received : waiting_move");
            }
        });

        socket.on("start_game", new Emitter.Listener(){
            @Override
            public void call(Object... args) {
                System.out.println("[Puissance 4] Received : " + "start_game");
            }
        });

        socket.on("end_game", new Emitter.Listener(){
            @Override
            public void call(Object... args) {
                System.out.println("[Puissance 4] Received : " + "end_game");
            }
        });

        socket.on("message", new Emitter.Listener(){
            @Override
            public void call(Object... args) {
                System.out.println("[Puissance 4] Received : " + "message");
            }
        });

        socket.on("game_status", new Emitter.Listener(){
            @Override
            public void call(Object... args) {
                System.out.println("[Puissance 4] Received : " + "game_status");
            }
        });

        socket.on("stop_game", new Emitter.Listener(){
            @Override
            public void call(Object... args) {
                System.out.println("[Puissance 4] Received : " + "stop_game");
            }
        });

        socket.on("disconnect", new Emitter.Listener(){
            @Override
            public void call(Object... args) {
                System.out.println("[Puissance 4] Received : " + "game_status");
            }
        });

        socket.connect();
        this.socket = socket;
    }

    public boolean play(String pseudo){
        System.out.println("[Puissance 4] Emit : " + "connect_player");
        socket.emit("connect_player", pseudo);
        return true;
    }

    public boolean move(JSONObject request){
        try{
            System.out.println("[Puissance 4] Emit : " + "new_move");
            socket.emit("new_move", request);
        }
        catch(Exception e){
            System.out.println("[Puissance 4] " + e);
        }
        return true;
    }

    public void disconnect(){
        socket.disconnect();
    }
}
