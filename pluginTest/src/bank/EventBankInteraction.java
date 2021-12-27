package bank;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import MoneyBag.MoneyBag;
import util.ShortCut;

public class EventBankInteraction implements Listener {
	@EventHandler
	public void onPlayerClick(InventoryClickEvent event) {
		if(event.getView().getTitle().equalsIgnoreCase(DisplayBank.NOMBANK))
		{
			ItemStack current = event.getCurrentItem();
			event.setCancelled(true);
			
			if(current == null)return;
			
			if(current.getItemMeta().getDisplayName().equals(DisplayBank.PUTGOLD1)) {
				DisplayBank.ajouteSacOr((Player)event.getWhoClicked(),1);
			}else if(current.getItemMeta().getDisplayName().equals(DisplayBank.PUTGOLD2)){
				DisplayBank.ajouteSacOr((Player)event.getWhoClicked(),10);
			}else if(current.getItemMeta().getDisplayName().equals(DisplayBank.PUTGOLD3)){
				Bukkit.getConsoleSender().sendMessage("Nombre Count "+ShortCut.countItem(event.getWhoClicked().getInventory(), MoneyBag.getMoneyBag()));
				DisplayBank.ajouteSacOr(
						(Player)event.getWhoClicked(),
						ShortCut.countItem(event.getWhoClicked().getInventory(), MoneyBag.getMoneyBag()));
			}
		}
	}
}
