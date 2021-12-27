package MoneyBag;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import util.ShortCut;

public class CommandMoneyBag implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] argd) {
		int amount = 1;
		Player player = (Player)sender;
		if(!sender.hasPermission("plugintest.moneyBag.give")) {
			ShortCut.sendError(player, "Vous n'avez pas la permission");
			return true;
		}
		if(argd.length > 0) {
			try {
				amount = Integer.parseInt(argd[0]);
			}catch(NumberFormatException e) {
				return false;
			}
		}else if(argd.length > 1){
			player = Bukkit.getPlayer(argd[1]);
			if(player == null) {
				sender.sendMessage("§cCe joueur n'est pas connecté");
				return true;
			}
		}

		MoneyBag.giveMoneyBag(player, amount);
		return true;
	}
	
}
