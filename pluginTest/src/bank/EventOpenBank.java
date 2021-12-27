package bank;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class EventOpenBank implements Listener {
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		
		if(event.getAction() == Action.RIGHT_CLICK_BLOCK && BankChest.isBankChest(event.getClickedBlock())) {
			event.setCancelled(true);
			Player player = (Player)event.getPlayer();
			
			DisplayBank display = new DisplayBank();

			player.openInventory(display.getInventory());
		}
	}
}
