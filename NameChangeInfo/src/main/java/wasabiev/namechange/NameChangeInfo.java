package wasabiev.namechange;

import java.util.logging.Logger;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import wasabiev.namechange.listener.PlayerJoinEventListener;

public class NameChangeInfo extends JavaPlugin {

	private static Plugin plugin;

	// Logger
	public final static Logger log = Logger.getLogger("Minecraft");
	public final static String logPrefix = "[NameInfo] ";
	public final static String msgPrefix = "&d[NameInfo]&f ";

	// Command
	NameChangeCommandExecutor commandExecutor = new NameChangeCommandExecutor(this);

	// Listener
	PlayerJoinEventListener playerJoinEvent = new PlayerJoinEventListener(this);

	@Override
	public void onEnable() {
		plugin = this;

		PluginManager pm = getServer().getPluginManager();

		if (!pm.isPluginEnabled(this)) {
			return;
		}

		getCommand("namehistory").setExecutor(this.commandExecutor);

		pm.registerEvents(playerJoinEvent, this);

		// Log
		PluginDescriptionFile pdfFile = this.getDescription();
		log.info("[" + pdfFile.getName() + "] version " + pdfFile.getVersion() + " is enabled!");

	}

	@Override
	public void onDisable() {
		// Log
		PluginDescriptionFile pdfFile = this.getDescription();
		log.info("[" + pdfFile.getName() + "] version " + pdfFile.getVersion()
				+ " is disabled!");
	}

	public static Plugin getInstance() {
		return plugin;
	}
}
