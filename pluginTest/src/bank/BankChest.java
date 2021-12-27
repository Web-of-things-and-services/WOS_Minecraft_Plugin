package bank;

import java.io.Serializable;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

public class BankChest implements Serializable {

	private static final long serialVersionUID = -1509540502355106167L;
	private Coordonnee coord;
	
	public BankChest(Location coord)
	{
		this.coord = new Coordonnee(coord);
	}
	
	public boolean sameCoord(Location loc) {
		return coord.equals(new Coordonnee(loc));
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((coord == null) ? 0 : coord.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BankChest other = (BankChest) obj;
		if (coord == null) {
			if (other.coord != null)
				return false;
		} else if (!coord.equals(other.coord))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "BankChest [coord=" + coord + "]";
	}
	
	public static boolean isBankChest(Block block) {
		return block.getType() == Material.CHEST && 
				MemoireBank.getBankChest().contains((Object) new BankChest(block.getLocation()));
	}
	

		
}
