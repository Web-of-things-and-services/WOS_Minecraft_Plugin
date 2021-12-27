package MoneyBag;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class MoneyBag {

	public static void giveMoneyBag(Player player, int amount) {
		player.getInventory().addItem(MoneyBag.getMoneyBag(amount));
	}
	
	public static ItemStack getMoneyBag(String Name, int amount) {
		//OfflinePlayer moneyBagPlayer = Bukkit.getOfflinePlayer(UUID.fromString("311deb92-9612-40da-992c-355d959d6513"));
		@SuppressWarnings("deprecation")
		OfflinePlayer moneyBagPlayer = Bukkit.getOfflinePlayer("MrSnowDK");
		ItemStack moneyBag = new ItemStack(Material.PLAYER_HEAD);
		SkullMeta metaseparateur = (SkullMeta) moneyBag.getItemMeta();
		metaseparateur.setOwningPlayer(moneyBagPlayer);
		metaseparateur.setDisplayName(Name);
		moneyBag.setItemMeta(metaseparateur);
		moneyBag.setAmount(amount);
		return moneyBag;
	}
	
	public static ItemStack getMoneyBag() {
		return getMoneyBag("§6Sac d'or",1);
	}

	public static ItemStack getMoneyBag(String name) {
		return getMoneyBag(name,1);
	}

	public static ItemStack getMoneyBag(int amount) {
		return getMoneyBag("§6Sac d'or",amount);
	}

}
