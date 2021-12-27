package iti5.wos;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.net.URISyntaxException;

public class Game {
    private Player joueur = null;
    private static Game game = null;
    private Location playerPositionInGame = new Location(Bukkit.getServer().getWorld("World"), 0, 180, 0);
    private Board board = new Board(playerPositionInGame.add(0,0,-9));
    private WosSocket wSocket;

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
        this.wSocket = new WosSocket("http://192.168.1.10:24856", this);
    }

    public WosSocket getSocket() {
        return this.wSocket;
    }

    public boolean canPlay(){
        return this.joueur == null;
    }

    public boolean play(Player player){
        wSocket.play(player.getName());
        if(!this.canPlay())
            return false;
        this.joueur = player;
        this.initPlayer(player);
        return true;
    }

    public boolean move(int column){
        wSocket.move(column);
        return true;
    }

    public void movePlayed(int column, String pseudo){
        this.board.addToken(column,pseudoToTokenColor(pseudo));
    }

    public CouleurToken pseudoToTokenColor(String pseudo){
        if(pseudo == this.joueur.getName()){
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
}
