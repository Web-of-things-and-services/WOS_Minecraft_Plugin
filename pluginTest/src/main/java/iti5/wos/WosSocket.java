package iti5.wos;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

import java.net.URISyntaxException;

public class WosSocket {
    private Socket socket;
    private Game game;
    public WosSocket(String uri, Game game) throws URISyntaxException {
        Socket socket = IO.socket(uri);
        this.game = game;

        socket.on("new_move", new Emitter.Listener(){
            @Override
            public void call(Object... args) {
                game.movePlayed(
                    Integer.parseInt((String) args[0]), // Column Num
                        (String) args[1] // Pseudo player
                );
            }
        });

        socket.on("waiting_move", new Emitter.Listener(){
            @Override
            public void call(Object... args) {
                System.out.println("[Puissance 4] " + "waiting_move");
            }
        });

        socket.on("start_game", new Emitter.Listener(){
            @Override
            public void call(Object... args) {
                System.out.println("[Puissance 4] " + "start_game");
            }
        });

        socket.on("end_game", new Emitter.Listener(){
            @Override
            public void call(Object... args) {
                System.out.println("[Puissance 4] " + "end_game");
            }
        });

        socket.on("message", new Emitter.Listener(){
            @Override
            public void call(Object... args) {
                System.out.println("[Puissance 4] " + "message");
            }
        });

        socket.on("game_status", new Emitter.Listener(){
            @Override
            public void call(Object... args) {
                System.out.println("[Puissance 4] " + "game_status");
            }
        });

        socket.on("stop_game", new Emitter.Listener(){
            @Override
            public void call(Object... args) {
                System.out.println("[Puissance 4] " + "stop_game");
            }
        });

        socket.on("disconnect", new Emitter.Listener(){
            @Override
            public void call(Object... args) {
                System.out.println("[Puissance 4] " + "game_status");
            }
        });

        socket.connect();
        this.socket = socket;
    }

    public boolean play(String pseudo){
        socket.emit("connect_player", pseudo);
        return true;
    }

    public boolean move(int column){
        try{
            socket.emit("new_move", ""+column);
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
