package wasabiev.namechange;

import java.util.logging.Logger;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import wasabiev.namechange.hook.CheckNameHistory;
import wasabiev.namechange.utils.SendMessage;

public class NameChangeCommandExecutor implements CommandExecutor {

	private Plugin plugin = NameChangeInfo.getInstance();

	private SendMessage sendMessage;
	private CheckNameHistory checkNameHistory;

	protected static final Logger log = NameChangeInfo.log;
	protected static final String logPrefix = NameChangeInfo.logPrefix;
	protected static final String msgPrefix = NameChangeInfo.msgPrefix;

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		//namehistory
		if (cmd.getName().equalsIgnoreCase("namehistory")) {
			if (!sender.hasPermission("namechange.info")) {
				sendMessage.sendMessage((Player) sender, msgPrefix + "&c権限がありません！");
				return false;
			}

			if (args.length != 1) {
				sendMessage.sendMessage((Player) sender, msgPrefix + "&c対象のプレイヤーを指定してください");
				return false;
			}

			checkNameHistory.getNameHistory(plugin.getServer().getPlayer(args[0]), sender.getName());

		}
		return false;
	}
}
