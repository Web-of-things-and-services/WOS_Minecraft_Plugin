package dropHead;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

import MoneyBag.MoneyBag;

public class EventOnDeathDropHead implements Listener {
	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		if(e.getEntity().getKiller() != null) {
			Player victim = e.getEntity();
			Bukkit.getConsoleSender().sendMessage("Mort:"+victim.getName()+" Killer:"+victim.getKiller().getName());
			if(MemoireDeath.getInstance().addDeath(victim)) {
				List<ItemStack> drops = e.getDrops();
				drops.add(MoneyBag.getMoneyBag());
			}
		}
	}
}
