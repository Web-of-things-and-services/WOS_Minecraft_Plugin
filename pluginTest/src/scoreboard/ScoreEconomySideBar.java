package scoreboard;


import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import pluginTest.Observer;
import team.ColorTeam;
import team.Team;

public class ScoreEconomySideBar implements Observer{
	private static ScoreEconomySideBar instance = null;
	private Scoreboard scoreboard;
	private Objective objective;
	
	private ScoreEconomySideBar() {
		ScoreboardManager SBM = Bukkit.getScoreboardManager();
		this.scoreboard = SBM.getNewScoreboard();
		this.objective = scoreboard.registerNewObjective("eco_banque", "dummy", "Banque");
		objective.setDisplaySlot(DisplaySlot.SIDEBAR);
		this.update();
		Team.addObserver(this);
	}
	
	public void update() {
		for(Team val : Team.getTeamList()) {
			this.displayScore(val);
		}
	}
	
	public void update(Team team) {
		displayScore(team);
	}
	
	public void displayScore(Team team) {
		String text = team.getColor().toString();
		int score = team.getScore();
		this.objective.getScore(ColorTeam.getColorChat(team.getColor())+text).setScore(score);
		
	}
	
	public Scoreboard getScoreboard() {
		return scoreboard;
	}
	
	public void reset() {
		Team.removeObserver(ScoreEconomySideBar.instance);
		ScoreEconomySideBar.instance = new ScoreEconomySideBar();
		for(Player val : Bukkit.getOnlinePlayers()) {
			val.setScoreboard(ScoreEconomySideBar.instance.getScoreboard());
		}
	}
	
	public static ScoreEconomySideBar getInstance() {
		if(ScoreEconomySideBar.instance == null) {
			ScoreEconomySideBar.instance = new ScoreEconomySideBar();
		}
		return ScoreEconomySideBar.instance;
	}
}
