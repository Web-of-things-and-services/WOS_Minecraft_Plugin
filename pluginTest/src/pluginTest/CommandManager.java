package pluginTest;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import team.CommandTeam;

public class CommandManager implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
			if(arg3 != null && arg3.length > 0) {
				switch(arg3[0]) {
					case "team":
						CommandTeam commande = new CommandTeam(arg0,arg3);
						return commande.getResponse();
					default:
						return false;
				}
			}else {
				return false;
			}
	}

}
