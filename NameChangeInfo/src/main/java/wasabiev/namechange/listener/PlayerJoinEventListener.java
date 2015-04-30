package wasabiev.namechange.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import wasabiev.namechange.NameChangeInfo;
import wasabiev.namechange.hook.CheckNameHistory;

public class PlayerJoinEventListener implements Listener {

	private static CheckNameHistory checkname;

	private final NameChangeInfo plugin;

	public PlayerJoinEventListener(final NameChangeInfo plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onPlayerJoinEvent(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		String sender = "";

		checkname.getNameHistory(player, sender);

	}
}
