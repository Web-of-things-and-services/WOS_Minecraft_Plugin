package bank;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import org.bukkit.Location;

public class MemoireBank {
	private static String filePath = "plugins/data/BankChest.data";
	private static ArrayList<BankChest> BankChest = new ArrayList<BankChest>();
	
	public static void loadBank() {
		MemoireBank.loadBankChest();
	}
	
	public static void saveBank(){
		MemoireBank.saveBankChest(MemoireBank.BankChest);
	}
	
	@SuppressWarnings("unchecked")
	private static void loadBankChest(){
		File fichierBankChest = new File(filePath);
		File dossier = new File("plugins/data");
		if(!dossier.exists()) {
			dossier.mkdir();
		}
		
		if(!fichierBankChest.exists())
		{
			try {
				fichierBankChest.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			saveBankChest(BankChest);
		}
			
		FileInputStream fichier;
		try {
			fichier = new FileInputStream(filePath);
			ObjectInputStream ois = new ObjectInputStream(fichier);
			MemoireBank.BankChest = (ArrayList<bank.BankChest>) ois.readObject();
			ois.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void saveBankChest(ArrayList<BankChest> list){
      FileOutputStream fichier;
		try {
		  fichier = new FileOutputStream(filePath);
	      ObjectOutputStream oos = new ObjectOutputStream(fichier);
	      oos.writeObject(list);
	      oos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static ArrayList<BankChest> getBankChest(){
		return MemoireBank.BankChest;
	}
	
	public static void add(BankChest bc) {
		MemoireBank.BankChest.add(bc);
	}
	public static void add(Location loc) {
		MemoireBank.BankChest.add(new BankChest(loc));
	}
	
	public static void remove(BankChest bc) {
		MemoireBank.BankChest.remove(bc);
	}
	
	public static void remove(Location loc) {
		MemoireBank.BankChest.remove(new BankChest(loc));
	}
}
