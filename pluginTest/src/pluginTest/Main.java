package pluginTest;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import MoneyBag.CommandMoneyBag;
import bank.CommandBank;
import bank.EventBankInteraction;
import bank.EventBreakBank;
import bank.EventOpenBank;
import bank.MemoireBank;
import dropHead.EventOnDeathDropHead;
import scoreboard.EventSetScoreboard;
import team.MemoireTeam;

public class Main extends JavaPlugin{

	public void onEnable() {
		Bukkit.getConsoleSender().sendMessage("Plugin Start");
		MemoireBank.loadBank();
		MemoireTeam.loadTeam();

		getCommand("cite").setExecutor(new CommandManager());
		getCommand("bank").setExecutor(new CommandBank());
		getCommand("moneybag").setExecutor(new CommandMoneyBag());
	    getServer().getPluginManager().registerEvents(new EventOpenBank(), this);
	    getServer().getPluginManager().registerEvents(new EventOnDeathDropHead(), this);
	    getServer().getPluginManager().registerEvents(new EventSetScoreboard(), this);
	    getServer().getPluginManager().registerEvents(new EventBreakBank(), this);
	    getServer().getPluginManager().registerEvents(new EventBankInteraction(), this);
	}
	
	public void onDisable() {
		MemoireTeam.saveTeam();
		MemoireBank.saveBank();
		getLogger().info("PluginTest stop");
	}
}
