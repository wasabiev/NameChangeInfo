package wasabiev.namechange.hook;

import java.util.logging.Logger;

import org.bukkit.entity.Player;

import wasabiev.namechange.NameChangeInfo;

import com.mcbans.firestar.mcbans.api.MCBansAPI;
import com.mcbans.firestar.mcbans.api.data.PlayerLookupData;
import com.mcbans.firestar.mcbans.callBacks.LookupCallback;

public class LookupPlayer {

	protected static final Logger log = NameChangeInfo.log;
	protected static final String logPrefix = NameChangeInfo.logPrefix;

	private MCBansAPI mcbansAPI;

	private Integer bans;
	private Double rep;

	public void getReputation(final Player player, String sender) {

		//初期化
		bans = -1;
		rep = -0.1;

		if (sender.length() == 0 || sender == null) {
			sender = "console";
		}

		mcbansAPI.lookupPlayer(player.getName(), null, sender, null, new LookupCallback() {

			@Override
			public void success(PlayerLookupData data) {
				bans = data.getTotal();
				rep = data.getReputation();
			}

			@Override
			public void error(String message) {
				log.info(message);
			}
		});
	}

	/*
	 * getter
	 */

	public int getBans() {
		return bans;
	}

	public double getRep() {
		return rep;
	}
}
