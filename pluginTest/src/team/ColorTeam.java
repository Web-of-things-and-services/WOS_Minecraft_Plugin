package team;

import org.bukkit.ChatColor;

public enum ColorTeam {
	Bleu,Rouge,Vert,Jaune,Orange,Rose,Violet,Blanc,Noir;
	
	public static ColorTeam toColor(String color) throws NotAColorTeamException {
		switch(color.toLowerCase()){
			case "rouge":
				return ColorTeam.Rouge;
			case "bleu":
				return ColorTeam.Bleu;
			case "vert":
				return ColorTeam.Vert;
			case "jaune":
				return ColorTeam.Jaune;
			case "orange":
				return ColorTeam.Orange;
			case "rose":
				return ColorTeam.Rose;
			case "violet":
				return ColorTeam.Violet;
			case "blanc":
				return ColorTeam.Blanc;
			case "noir":
				return ColorTeam.Noir;
			default:
				throw new NotAColorTeamException("La couleur renseigné n'est pas une couleur valide");
		}
	}
	
	public static String getColorChat(ColorTeam color) {
		switch(color.toString().toLowerCase()) {
			case "rouge":
				return "§4";
			case "bleu":
				return "§1";
			case "vert":
				return "§a";
			case "jaune":
				return "§e";
			case "orange":
				return "§6";
			case "rose":
				return "§d";
			case "violet":
				return "§5";
			case "blanc":
				return "§f";
			case "noir":
				return "§0";
			default:
				return "";
		}
	}

	public static ChatColor getBukkitColor(String color) {
		switch(color.toString().toLowerCase()) {
			case "rouge":
				return ChatColor.RED;
			case "bleu":
				return ChatColor.BLUE;
			case "vert":
				return ChatColor.GREEN;
			case "jaune":
				return ChatColor.YELLOW;
			case "orange":
				return ChatColor.GOLD;
			case "rose":
				return ChatColor.LIGHT_PURPLE;
			case "violet":
				return ChatColor.DARK_PURPLE;
			case "blanc":
				return ChatColor.WHITE;
			case "noir":
				return ChatColor.BLACK;
			default:
				return ChatColor.WHITE;
		}
	}
}
