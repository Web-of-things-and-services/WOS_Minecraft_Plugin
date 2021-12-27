package bank;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandBank implements CommandExecutor  {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] argd) {
		if(sender instanceof Player) {
			Player joueur = (Player) sender;
			if(argd == null || argd.length <= 0) this.help(joueur);
			else {
				switch(argd[0]) {
					case "add":
						return this.add(joueur);
					case "remove":
						return this.remove(joueur);
					default:
						return this.help(joueur);
				}
			}
		}
		return true;
	}
	
	private boolean add(Player sender) {
		Location coordJoueur = sender.getLocation().getBlock().getLocation();
		coordJoueur.getBlock().setType(Material.CHEST);
		MemoireBank.add(new BankChest(coordJoueur));
		return true;
	}

	private boolean remove(Player sender) {
		Block targetBlock = ((Player) sender).getPlayer().getTargetBlockExact(5);
		if(targetBlock == null) {
			sender.sendMessage("§cAucun block détecté");
			return true;
		}
		if(BankChest.isBankChest(targetBlock)) {
			MemoireBank.remove(targetBlock.getLocation());
			targetBlock.setType(Material.AIR);
		}else {
			sender.sendMessage("§cLe block que vous visez n'est pas une banque");
		}
		return true;
	}
	
	private boolean help(Player sender)
	{
		sender.sendMessage("§4/bank add§c: Créer une banque à tes pieds");
		sender.sendMessage("§4/bank remove§c: Supprime la banque que tu regardes");
		return true;
	}
}
