package bases;

import org.bukkit.Location;

import team.ColorTeam;

public class Base {
	private Zone zone;
	private ColorTeam color;
	
	public Base(Location loc, int radius,ColorTeam color) {
		zone = new Zone(loc.getBlock().getLocation(),radius);
		this.color = color;
	}
	
	
}
