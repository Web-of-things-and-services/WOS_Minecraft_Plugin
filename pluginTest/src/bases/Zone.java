package bases;

import org.bukkit.Location;

import bank.Coordonnee;

public class Zone {
	private Coordonnee diag1;
	private Coordonnee diag2;

	public Zone(Location loc, int radius) {
		diag1 = new Coordonnee(loc.getX()+radius,loc.getY(),loc.getZ()+radius);
	}
	
	public boolean inTheZone(Location loc) {
		return betweenX(loc.getX()) && betweenZ(loc.getZ());
	}
	
	public boolean betweenX(double X) {
		return (diag1.getX() > X) ? X > diag2.getX() : diag2.getX() > X;
		
	}
	public boolean betweenZ(double Z) {
		return (diag1.getZ() > Z) ? Z > diag2.getZ() : diag2.getZ() > Z;
	}
}
