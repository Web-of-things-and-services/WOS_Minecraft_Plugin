package bank;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class EventBreakBank implements Listener {
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		
		if(event.getAction() == Action.LEFT_CLICK_BLOCK && 
		event.getClickedBlock().getType() == Material.CHEST && 
		MemoireBank.getBankChest().contains((Object) new BankChest(event.getClickedBlock().getLocation()))) {
			
			Bukkit.getConsoleSender().sendMessage("Ouvre un chest");
			event.setCancelled(true);
			Player player = (Player)event.getPlayer();
			
			player.sendMessage("§cTu ne peux pas casser une banque.");
			if(player.isOp()){
				player.sendMessage("§cFait /bank remove en regradant la banque.");
			}
			
		}
	}
}
