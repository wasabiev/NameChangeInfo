package wasabiev.namechange.utils;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import wasabiev.namechange.NameChangeInfo;

public class SendMessage {

	private Plugin plugin = NameChangeInfo.getInstance();

	/*
	 * 個別に送信
	 */
	public void sendMessage(Player player, String message) {
		player.sendMessage(replaceColor(message));
	}

	public void sendMessage(String playerName, String message) {
		plugin.getServer().getPlayer(playerName).sendMessage(replaceColor(message));
	}

	/*
	 * 権限プレイヤーに送信
	 */
	public void sendPermMessage(String message) {
		for (Player player : plugin.getServer().getOnlinePlayers()) {
			if (player.hasPermission("namechange.mod")) {
				player.sendMessage(replaceColor(message));
			}
		}
	}

	/*
	 * カラーコード変換
	 */
	private String replaceColor(String message) {
		String cm = message.replaceAll("&([0-9a-fk-or])", "\u00A7$1");
		return cm;
	}
}
