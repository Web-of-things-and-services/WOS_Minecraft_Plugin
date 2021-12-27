package util;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ShortCut {
	public static ItemStack creerStack(Material block, String Name) {
		ItemStack separateur = new ItemStack(block);
		ItemMeta metaseparateur = separateur.getItemMeta();
		metaseparateur.setDisplayName(Name);
		separateur.setItemMeta(metaseparateur);
		return separateur;
	}
	

	
	public static int countItem(Inventory inv, ItemStack item) {
		int count = 0;
		for(ItemStack stack : inv) {
			if(stack != null) {
				if(item.getItemMeta().getDisplayName().equals(stack.getItemMeta().getDisplayName()) &&
					item.getType().equals(stack.getType())) {
					count+=stack.getAmount();
				}
			}
		}
		return count;
	}
	
	public static boolean removeItem(Inventory inv,ItemStack item, int amount) {
		if(item == null) return false;
		if(countItem(inv,item) >= amount)
		{
			for(ItemStack stack : inv) {
				if(stack != null && amount > 0) {
					if(item.getItemMeta().getDisplayName().equals(stack.getItemMeta().getDisplayName()) &&
						item.getType().equals(stack.getType())) {
						if(stack.getAmount() <= amount)
						{
							amount -= stack.getAmount();
							stack.setAmount(0);
						}else {
							stack.setAmount(stack.getAmount()-amount);
							amount = 0;
						}
					}
				}
			}
			return true;
		}else {
			return false;
		}
	}
	
	public static void sendError(Player player, String error) {
		player.sendMessage("§c"+error);
	}
	
	public static Player getPlayer(String playerName) throws PlayerNotExistException {
		Player player = Bukkit.getPlayer(playerName);
		if(player == null) {
			throw new PlayerNotExistException("Ce joueur n'existe pas");
		}
		return player;
	}
}
