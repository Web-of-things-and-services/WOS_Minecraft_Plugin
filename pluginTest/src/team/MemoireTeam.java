package team;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.scoreboard.Scoreboard;

public class MemoireTeam {
	private static String filePath = "plugins/data/Team.data";
	
	@SuppressWarnings("unchecked")
	public static void loadTeam() {
		File fichierTeam = new File(filePath);
		File dossier = new File("plugins/data");
		if(!dossier.exists()) {
			dossier.mkdir();
		}
		
		if(!fichierTeam.exists())
		{
			try {
				fichierTeam.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			saveTeam();
		}
			
		FileInputStream fichier;
		try {
			fichier = new FileInputStream(filePath);
			ObjectInputStream ois = new ObjectInputStream(fichier);
			Team.setTeamList((ArrayList<team.Team>) ois.readObject());
			ois.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		loadTeamScoreboard();
	}
	
	public static void saveTeam() {
		ArrayList<Team> teamlist = Team.getTeamList();
	      FileOutputStream fichier;
			try {
			  fichier = new FileOutputStream(filePath);
		      ObjectOutputStream oos = new ObjectOutputStream(fichier);
		      oos.writeObject(teamlist);
		      oos.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	
	public static void loadTeamScoreboard() {
		Scoreboard s = Bukkit.getScoreboardManager().getMainScoreboard();
		org.bukkit.scoreboard.Team teamSc;
		for(ColorTeam val : ColorTeam.values()) {
			if(s.getTeam("cite_"+val.toString()) == null) {
				teamSc = s.registerNewTeam("cite_"+val.toString());
				teamSc.setColor(ColorTeam.getBukkitColor(val.toString()));
			}
		}
	}
}
