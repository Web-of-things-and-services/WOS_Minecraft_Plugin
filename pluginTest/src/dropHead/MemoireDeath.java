package dropHead;

import java.util.HashMap;

import org.bukkit.entity.Player;

import team.NotInTheTeamException;
import team.Team;

public class MemoireDeath {
	private static MemoireDeath instance;
	private final long timeBetweenDrop = ((0*60)+10)*1000; //en ms (5 minutes)
	private HashMap<Player,Long> lastdeath;
	
	private MemoireDeath() {
		this.lastdeath = new HashMap<Player,Long>();
	}
	
	public static MemoireDeath getInstance() {
		if(MemoireDeath.instance == null) {
			MemoireDeath.instance = new MemoireDeath();
		}
		return MemoireDeath.instance;
	}
	
	public boolean addDeath(Player player) {
		boolean result = this.dropHead(player);
		lastdeath.put(player,System.currentTimeMillis());
		return result;
	}
	
	public boolean dropHead(Player player) {
		Long lastDeath = lastdeath.get(player);
		boolean sameTeam = false;
		try {
			sameTeam = Team.getPlayerTeam(player.getKiller().getName()).getColor() == Team.getPlayerTeam(player.getName()).getColor();
		} catch (NotInTheTeamException e) {
			// TODO Auto-generated catch block
			sameTeam = true;
		}
		return (lastDeath == null || lastDeath < System.currentTimeMillis() - timeBetweenDrop) && !sameTeam;
	}
}
