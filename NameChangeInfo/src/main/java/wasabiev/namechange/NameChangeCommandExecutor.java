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

	protected static final Logger log = NameChangeInfo.log;
	protected static final String logPrefix = NameChangeInfo.logPrefix;
	protected static final String msgPrefix = NameChangeInfo.msgPrefix;

	public NameChangeCommandExecutor(NameChangeInfo plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (!sender.hasPermission("namechange.info")) {
			SendMessage.sendMessage((Player) sender, msgPrefix + "&c権限がありません！");
			return false;
		}

		if (args.length != 1) {
			SendMessage.sendMessage((Player) sender, msgPrefix + "&c対象のプレイヤーを指定してください");
			return false;
		}

		CheckNameHistory.getNameHistory(plugin.getServer().getPlayer(args[0]), sender.getName());

		return true;

	}
}
