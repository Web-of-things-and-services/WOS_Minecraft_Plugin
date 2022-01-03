package iti5.wos;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.ChatColor;

import java.net.URISyntaxException;
import org.json.simple.JSONObject;


import org.json.simple.JSONObject;
import org.json.simple.JSONArray;

public class Game {
    private Player joueur = null;
    private static Game game = null;
    private Location playerPositionInGame = new Location(Bukkit.getServer().getWorld("World"), 0, 180, 0);
    private Board board = new Board(playerPositionInGame.add(0,0,-9));
    private WosSocket wSocket;
    // final String serverAddress = "http://192.168.1.10:24856";
    private String serverAddress = "http://localhost:8888";

    public static Game getGame() throws URISyntaxException {
        if(game == null){
            Game newGame = new Game();
            game = newGame;
            return newGame;
        }else{
            return Game.game;
        }
    }

    public Game() throws URISyntaxException {
        this.wSocket = new WosSocket(serverAddress, this);
        System.out.println("[Puissance 4] Connection a " + serverAddress);
    }

    public WosSocket getSocket() {
        return this.wSocket;
    }

    // public boolean canPlay(){
    //     return this.joueur == null;
    // }

    public boolean play(Player player){
        // if(!this.canPlay())
        //     return false;
        // this.joueur = player;
        // this.initPlayer(player);
        // board.renderStaticElements();
        // return true;
        createSocket(serverAddress);
        wSocket.play(player.getName());
        this.joueur = player;
        this.initPlayer(player);
        board.renderStaticElements();
        // wSocket.gameStatus();
        return true;
    }

    // public void editGame(Boolean gameStarted, String nextPlayer, JSONArray jsonBoard){
    //     if(gameStarted){
    //         JSONObject jsonObject;
    //         Integer column;
    //         String pseudo;
    //         for (int i = 0; i < jsonBoard.size(); i++) {
    //             jsonObject = (JSONObject) jsonBoard.get(i);
    //             column = Integer.parseInt((String) jsonObject.get("column"));
    //             pseudo = (String) jsonObject.get("name");
    //             move(column, pseudo);
    //         }
    //         message("Partie déjà commencée !");
    //         message("C'est à " + nextPlayer + " de jouer.");
    //     }
    // }

    public boolean move(Integer column, String pseudo){
        JSONObject request = new JSONObject();
        request.put("column", column.toString());
        request.put("name", pseudo);

        wSocket.move(request);
        return true;
    }

    public void movePlayed(int column, String pseudo){
        this.board.addToken(column, pseudoToTokenColor(pseudo));
    }

    public CouleurToken pseudoToTokenColor(String pseudo){
        if(pseudo.equals(this.joueur.getName())){
            return CouleurToken.JAUNE;
        }else{
            return CouleurToken.ROUGE;
        }
    }

    public void disable(){
        wSocket.disconnect();
    }

    public void initPlayer(Player player){
        player.teleport(this.playerPositionInGame);
    }

    public void reset(){
        board.resetBoard();
    }

    public boolean changeAddress(String address) {
        if(address == null)
            address = "http://localhost:8888";
        else {
            switch (address) {
                case "local":
                    address = "http://localhost:8888";
                    break;
                case "rasp":
                    address = "http://192.168.1.10:24856";
                    break;
                default:
                    break;
            }
        }
        return createSocket(address);
    }

    private boolean createSocket(String address){
        try {
            this.wSocket.disconnect();
            this.wSocket = new WosSocket(address, this);
            System.out.println("[Puissance 4] Connection a " + address);
            this.serverAddress = address;
            return true;
        } catch (Exception e) {
            System.out.println("[Puissance 4] error creation socket");
            e.printStackTrace();
            return false;
        }
    }


    public void end(String winnerName) {
        String message;
        if(winnerName.equals("nobody")) message = "Partie terminée, mais aucun gagnant !";
        else if(winnerName.equals(joueur.getName())) message = "Partie terminée, VOUS AVEZ GAGNÉ !!!";
        else message = "Partie terminée, vous avez perdu.";

        this.message(message);
    }

    public void message(String message) {
        joueur.sendMessage(ChatColor.RED + "[Puissance 4] " + ChatColor.GOLD + message);
    }
}
