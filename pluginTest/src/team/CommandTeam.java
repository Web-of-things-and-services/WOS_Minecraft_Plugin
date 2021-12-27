package team;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import util.PlayerNotExistException;
import util.ShortCut;

public class CommandTeam {
	private boolean resultat;
	public CommandTeam(CommandSender sender,String[] argd) {
		if(sender instanceof Player) {
			Player joueur = (Player) sender;
			if(argd == null || argd.length <= 1) this.help(joueur);
			else {
				switch(argd[1]) {
					case "create":
						this.resultat = this.create(joueur,argd);
						break;
					case "delete":
						this.resultat = this.delete(joueur,argd);
						break;
					case "addPlayer":
						this.resultat = this.add(joueur,argd);
						break;
					case "add":
						this.resultat = this.add(joueur,argd);
						break;
					case "removePlayer":
						this.resultat = this.remove(joueur,argd);
						break;
					case "remove":
						this.resultat = this.remove(joueur,argd);
						break;
					case "info":
						this.resultat = this.info(joueur,argd);
						break;
					default:
						this.resultat = this.help(joueur);
						break;
				}
			}
		}
	}

	private boolean remove(Player sender, String[] argd) {
		try {
			Team equipe = Team.getPlayerTeam(argd[2]);
			equipe.remove(argd[2]);
			sender.sendMessage("§2Le joueur a été supprimé");
		} catch (NotInTheTeamException e) {
			ShortCut.sendError(sender, e.getMessage());
		} catch (PlayerNotExistException e) {
			e.printStackTrace();
		}
		return true;
	}

	private boolean add(Player sender, String[] argd) {
		if(argd.length >= 4){
			try {
				Team team = Team.getTeam(ColorTeam.toColor(argd[2]));
				ShortCut.getPlayer(argd[3]);
				if(Team.hasTeam(argd[3])) {
					ShortCut.sendError(sender, "Ce joueur a déjà une équipe");
				}else {
					team.add(argd[3]);
					sender.sendMessage("§2Le joueur a été ajouté à l'équipe !");
				}
				
			} catch (TeamNotExistException e) {
				ShortCut.sendError(sender, "Cette équipe n'existe pas");
			} catch (NotAColorTeamException e) {
				ShortCut.sendError(sender,"Le nom de l'équipe n'est pas valide");
			} catch (PlayerNotExistException e) {
				ShortCut.sendError(sender,"Le joueur n'existe pas");
			} catch (AlreadyInTheTeamException e) {
				ShortCut.sendError(sender,"Le joueur est déjà dans cette équipe");
			}
		}
		return true;
	}

	private boolean delete(Player sender, String[] argd) {
		if(argd.length >= 3){
			try {
				Team equipe = Team.getTeam(ColorTeam.toColor(argd[2]));
				equipe.delete();
				sender.sendMessage("§2L'équipe a été supprimé");
			} catch (TeamNotExistException e) {
				ShortCut.sendError(sender,e.getMessage());
			} catch (NotAColorTeamException e) {
				ShortCut.sendError(sender,e.getMessage());
			}
		}else {
			return this.help(sender);
		}
		return true;
	}

	private boolean create(Player sender, String[] argd) {
		if(argd.length >= 3){
			try {
				ColorTeam color = ColorTeam.toColor(argd[2]);
				new Team(color);
				sender.sendMessage("§2L'équipe "+color.toString()+" a été créée");
			} catch (NotAColorTeamException e) {
				ShortCut.sendError(sender, e.getMessage());
			} catch (TeamAlreadyExistException e) {
				ShortCut.sendError(sender, e.getMessage());
			}
			
		}
		else {
			return this.help(sender);
		}
		return true;
	}

	private boolean help(Player joueur) {
		joueur.sendMessage("§4Help: §cLa section help n'est pas encore disponible");
		return true;
	}
	
	private boolean info(Player sender, String[] argd) {
		if(argd.length == 2) return this.info(sender);
		else {
			return this.info(sender,argd[2]);
		}
	}
	
	private boolean info(Player sender, String team) { // INFO ONE TEAM
		try {
			Team equipe = Team.getTeam(ColorTeam.toColor(team));
			sender.sendMessage(ColorTeam.getColorChat(equipe.getColor())+"Equipe "+equipe.getColor().toString()+":");
			sender.sendMessage(ColorTeam.getColorChat(equipe.getColor())+"Score : "+equipe.getScore());
			sender.sendMessage(ColorTeam.getColorChat(equipe.getColor())+"Membres:");
			if(equipe.getMembres().size() == 0) {
				sender.sendMessage(ColorTeam.getColorChat(equipe.getColor())+"Aucun Membre");
			}
			else {
				for(String val : equipe.getMembres()) {
					sender.sendMessage(ColorTeam.getColorChat(equipe.getColor())+"-"+val);
				}
			}
			
		} catch (TeamNotExistException e) {
			ShortCut.sendError(sender, e.getMessage());
		} catch (NotAColorTeamException e) {
			ShortCut.sendError(sender, e.getMessage());
		}
		return true;
	}

	private boolean info(Player sender) { // INFO ALL TEAM
		for(Team equipe : Team.getTeamList()) {
			this.info(sender, equipe.getColor().toString());
		}
		return true;
	}
	
	
	public boolean getResponse() {
		return this.resultat;
	}
	
}
