package iti5.wos;

import io.socket.client.Socket;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.text.NumberFormat;

public class CommandManager implements CommandExecutor {
    private Game game;
    public CommandManager(Game game){
        super();
        this.game = game;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args != null && args.length > 0) {
            switch (args[0]){
                case "play":
                    if(sender instanceof Player){
                        System.out.println("[Puissance 4] " + sender.getName() + " se conencte au serveur wos");
                        return game.play((Player)sender);
                    }else{
                        System.out.println("[Puissance 4] " + sender.getName() + " Erreur, le joueur n'est pas un joueur");
                        return false;
                    }
                case "move":
                    System.out.println("[Puissance 4] " + sender.getName() + " veut jouer un coup");
                    if(args[1] != null){
                        System.out.println("[Puissance 4] " + args[1]);
                        try{
                            int column = Integer.parseInt(args[1]);
                            game.move(column, sender.getName());
                        }catch( NumberFormatException e){
                            return false;
                        }
                        return true;
                    }
                    return false;
                    
                case "address":
                    if(sender instanceof Player){
                        System.out.println("[Puissance 4] " + sender.getName() + " veut changer l'adresse sur " + args[1]);
                        return game.changeAddress(args[1]);
                    }else{
                        System.out.println("[Puissance 4] " + sender.getName() + " Erreur, le joueur n'est pas un joueur");
                        return false;
                    }
                default:
                    return false;
            }
        }
        return false;
    }
}
