package scoreboard;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class EventSetScoreboard implements Listener {
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		ScoreEconomySideBar Economy = ScoreEconomySideBar.getInstance();
		event.getPlayer().setScoreboard(Economy.getScoreboard());
	}

}
