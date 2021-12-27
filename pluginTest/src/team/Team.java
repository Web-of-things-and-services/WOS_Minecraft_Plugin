package team;

import java.io.Serializable;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import pluginTest.Observer;
import util.PlayerNotExistException;
import util.ShortCut;

public class Team implements Serializable{

	private static final long serialVersionUID = 5611752022337179101L;
	private static ArrayList<Team> teamList = new ArrayList<Team>();
	private static ArrayList<Observer> observers = new ArrayList<Observer>();
	private ColorTeam color;
	private int score;
	private ArrayList<String> membres;
	
	public static Team getTeam(ColorTeam color) throws TeamNotExistException {
		for (Team val : teamList) {
			if(val.getColor().equals(color)){
				return val;
			}
		}
		throw new TeamNotExistException("Cette équipe n'existe pas");
	}
	public static void addObserver(Observer observer) {
		observers.add(observer);
	}
	public static void removeObserver(Observer observer) {
		observers.remove(observer);
	}
	private void updateObserver() {
		for(Observer val : Team.observers) {
			val.update(this);
		}
	}
	private void resetObserver() {
		for(Observer val : Team.observers) {
			val.reset();
		}
	}
	
	public static Team getPlayerTeam(String player) throws NotInTheTeamException {
		for(Team team : teamList) {
			for(String val : team.membres) {
				if(val.equalsIgnoreCase(player)) return team;
			}
		}
		throw new NotInTheTeamException("Ce joueur n'est dans aucune équipe");
	}
	public static boolean hasTeam(String player) {
		for(Team team : teamList) {
			for(String val : team.membres) {
				if(val.equalsIgnoreCase(player)) return true;
			}
		}
		return false;
	}
	
	public Team(String color) throws NotAColorTeamException, TeamAlreadyExistException {
		
		for(Team equipe : Team.getTeamList()) {
			if(equipe.getColor().toString().equalsIgnoreCase(color)) {
				throw new TeamAlreadyExistException("L'equipe existe déjà");
			}
		}
		score = 0;
		this.color = ColorTeam.toColor(color);
		membres = new ArrayList<String>();
		teamList.add(this);
		this.updateObserver();
	}


	public Team(ColorTeam color) throws NotAColorTeamException, TeamAlreadyExistException {
		this(color.toString());
	}
	
	public void delete() {
		int size = this.membres.size();
		
		for(int i=0; i<size;i++){
			try {
				this.remove(this.membres.get(0));
			} catch (NotInTheTeamException e) {
				e.printStackTrace();
			} catch (PlayerNotExistException e) {
				e.printStackTrace();
			}
		}
		teamList.remove(this);
		this.resetObserver();
	}
	
	public void add(String player) throws AlreadyInTheTeamException, PlayerNotExistException {
		if(this.isInTeam(player)) throw new AlreadyInTheTeamException();
		membres.add(player);
//		try {
//			applyColorPlayer(ShortCut.getPlayer(player));
//		} catch (NotInTheTeamException e) {
//			e.printStackTrace();
//		}
	}
	
	public void remove(String player) throws NotInTheTeamException, PlayerNotExistException {
		if(!membres.contains(player)) throw new NotInTheTeamException();
		membres.remove(player);
	}
	
	public boolean isInTeam(String player){
		return membres.contains(player);
	}
	
	public ColorTeam getColor() {
		return this.color;
	}
	
	public int addScore(int add) {
		this.score += add;
		this.updateObserver();
		return this.score;
	}
	
	public int removeScore(int minus) {
		this.score += minus;
		this.updateObserver();
		return this.score;
	}
	
	public int getScore() {
		return this.score;
	}

	public ArrayList<String> getMembres() {
		return membres;
	}

	public static ArrayList<Team> getTeamList() {
		return teamList;
	}

	public static void setTeamList(ArrayList<Team> teamList) {
		Team.teamList = teamList;
	}
	
//	@SuppressWarnings("deprecation")
//	public static void applyColorPlayer(Player player) throws NotInTheTeamException {
//		Team team = Team.getPlayerTeam(player.getName());
//		Bukkit.getScoreboardManager().getMainScoreboard().getTeam("cite_"+team.getColor().toString()).addPlayer(player);
//	}
	
	
	
	
	
	
	
	
}
