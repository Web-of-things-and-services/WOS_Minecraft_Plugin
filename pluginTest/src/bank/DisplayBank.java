package bank;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import MoneyBag.MoneyBag;
import team.NotInTheTeamException;
import team.Team;
import util.ShortCut;
public class DisplayBank {

	private Inventory display;
	private static final int nbPut1 = 1;
	private static final int nbPut2 = 10;
	public static final String NOMBANK = "§eBanque";
	public static final String PUTGOLD1 = "§6Ajouter "+nbPut1+" sacs d'or";
	public static final String PUTGOLD2 = "§6Ajouter "+nbPut2+" sacs d'or";
	public static final String PUTGOLD3 = "§6Ajouter tous vos sac d'or";
	
	DisplayBank(){
		this.initDisplay();
	}
	
	private void initDisplay()
	{
		Inventory BankDisplay = Bukkit.createInventory(null, 27, DisplayBank.NOMBANK);

		

		ItemStack gBlock = getPutGold3() ;
		ItemStack gIngot = getPutGold2() ;
		ItemStack gNugget = getPutGold1();
		
		ItemStack separateur = ShortCut.creerStack(Material.LIGHT_GRAY_STAINED_GLASS_PANE," ");
		
		
		BankDisplay.setItem(1, gNugget);
		BankDisplay.setItem(4, gIngot);
		BankDisplay.setItem(7, gBlock);
		
		for(int i= 9; i<18;i++) BankDisplay.setItem(i, separateur);
		
		int slot0TeamDisplay = 17;
		List<Team> listTeam = Team.getTeamList();
		int[] slotDisplay =getIdSlotTeamByCountTeam(listTeam.size());
		for (int i =0; i<listTeam.size();i++) {
			Team team = listTeam.get(i);
			ItemStack iteaTeam = ShortCut.creerStack(getTeamIcon(team),"§6"+team.getScore()+" Sac d'or");
			BankDisplay.setItem(slot0TeamDisplay+slotDisplay[i],iteaTeam);
		}
		
		this.display = BankDisplay;
	}
	
	public int[] getIdSlotTeamByCountTeam(int countTeam) {
		int[][] slotByTeamNumber = {
				{5},
				{3,7},
				{3,5,7},
				{2,4,6,8},
				{1,3,5,7,9},
				{2,3,4,6,7,8},
				{2,3,4,5,6,7,8},
				{1,2,3,4,6,7,8,9},
				{1,2,3,4,5,6,7,8,9},
		};
		return slotByTeamNumber[countTeam-1];
	}
	
	public Material getTeamIcon(Team team) {
		switch (team.getColor().toString().toLowerCase()) {
			case "rouge":
				return Material.RED_CONCRETE_POWDER;
			case "bleu":
				return Material.LIGHT_BLUE_CONCRETE_POWDER;
			case "vert":
				return Material.LIME_CONCRETE_POWDER;
			case "jaune":
				return Material.YELLOW_CONCRETE_POWDER;
			case "orange":
				return Material.ORANGE_CONCRETE_POWDER;
			case "rose":
				return Material.PINK_CONCRETE_POWDER;
			case "violet":
				return Material.MAGENTA_CONCRETE_POWDER;
			case "blanc":
				return Material.WHITE_CONCRETE_POWDER;
			case "noir":
				return Material.BLACK_CONCRETE_POWDER;
			default:
				return Material.BARRIER;
		}
	}
	
	public static ItemStack getPutGold1() {
		return MoneyBag.getMoneyBag(DisplayBank.PUTGOLD1,1);
	}
	public static ItemStack getPutGold2() {
		return MoneyBag.getMoneyBag(DisplayBank.PUTGOLD2,10);
	}
	public static ItemStack getPutGold3() {
		return MoneyBag.getMoneyBag(DisplayBank.PUTGOLD3,64);
	}
	
	public Inventory getInventory() {
		return this.display;
	}
	
	public static boolean ajouteSacOr(Player player, int amount) {
		if(ShortCut.removeItem(player.getInventory(),MoneyBag.getMoneyBag(),amount)) {
			player.sendMessage("§eVous avez ajouté "+amount+" sac d'or dans la banque !");
			try {
				Team.getPlayerTeam(player.getName()).addScore(amount);
			} catch (NotInTheTeamException e) {
			}
			return true;
		}else {
			player.sendMessage("§cVous n'avez pas assez de sac d'or.");
			return false;
		}
	}
}
