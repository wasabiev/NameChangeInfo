package wasabiev.namechange.hook;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import wasabiev.namechange.NameChangeInfo;
import wasabiev.namechange.utils.SendMessage;

public class CheckNameHistory {

	protected static final Logger log = NameChangeInfo.log;
	protected static final String logPrefix = NameChangeInfo.logPrefix;
	protected static final String msgPrefix = NameChangeInfo.msgPrefix;

	private static final String MOJANG_NAME_HISTORY_URL = "https://api.mojang.com/user/profiles/";
	private static final String MOJANG_DATE = "changedToAt";
	private static final String DATE_PATTERN = "yyyy/MM/dd";

	private static final JSONParser jsonParser = new JSONParser();

	private static Plugin plugin = NameChangeInfo.getInstance();

	public static void getNameHistory(Player player, String sender) {

		Map<String, Date> map;

		try {
			UUID uuid = player.getUniqueId();
			map = getNameHistoryMap(uuid);
		} catch (Exception e) {
			SendMessage.sendMessage(sender, msgPrefix + "&cプレイヤー" + player.getName() + "が存在しません");
			log.info(logPrefix + "Failed to get UUID! (" + player.getName() + ")");
			return;
		}

		if (map.size() < 1) {
			if (sender.length() == 0 || sender == null) {
				SendMessage.sendPermMessage(msgPrefix + player.getName() + "は最初のユーザーネームです");
			} else {
				SendMessage.sendMessage(sender, msgPrefix + player.getName() + "は最初のユーザーネームです");
			}
		} else {

			if (sender.length() == 0 || sender == null) {
				SendMessage.sendPermMessage(msgPrefix + player.getName() + "は" + map.size() + 1 + "番目のユーザーネームです");
				SendMessage.sendPermMessage("&8  Name          | Ban | Rep. |  Time");

			} else {
				SendMessage.sendMessage(sender, msgPrefix + player.getName() + "は" + map.size() + 1 + "番目のユーザーネームです");
				SendMessage.sendMessage(sender, "&8  Name          | Ban | Rep. |  Time");

			}
			for (String p : map.keySet()) {

				LookupPlayer.getReputation(plugin.getServer().getPlayer(p), null);

				String l = "";
				for (int i = 0; i < (16 - p.length()); i++) {
					l = l + " ";
				}

				String d = "";

				SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN);
				d = sdf.format(map.get(p));

				String b_st = "";
				String r_st = "";

				String b_c = "";
				String r_c = "";

				int bans = LookupPlayer.getBans();
				double rep = LookupPlayer.getRep();

				int b_l = Integer.toString(bans).length();

				switch (b_l) {
				case 1:
					b_st = "  " + bans + "  ";
					break;
				case 2:
					b_st = "  " + bans + " ";
					break;
				case 3:
					b_st = " " + bans + " ";
					break;
				case 4:
					b_st = " " + bans;
					break;
				default:
					b_st = Integer.toString(bans);
				}

				if (bans == 0) {
					b_c = "&a";
				} else if (bans > 0 && bans <= 4) {
					b_c = "&e";
				} else if (bans > 5) {
					b_c = "&c";
				} else {
					b_c = "&f";
				}

				if (Double.toString(rep).length() == 3) {
					r_st = "  " + rep + " ";
				} else if (Double.toString(rep).length() == 4) {
					r_st = " " + rep + " ";
				} else {
					r_st = Double.toString(rep);
				}

				if (rep >= 0 && rep < 3) {
					r_c = "&c";
				} else if (rep >= 3 && rep < 7) {
					r_c = "&e";
				} else if (rep >= 7 && rep < 10) {
					r_c = "&3";
				} else if (rep == 10.0) {
					r_c = "&a";
				} else {
					r_c = "&f";
				}

				if (sender.length() == 0 || sender == null) {
					SendMessage.sendPermMessage("&7" + p + l + "|" + b_c + b_st + "|" + r_c + r_st + "|" + d);
				} else {
					SendMessage.sendMessage(sender, "&7" + p + l + "|" + b_c + b_st + "|" + r_c + r_st + "|" + d);
				}
			}
		}
	}

	private static JSONArray getNameHistoryJSON(UUID uuid) {
		JSONArray array = null;
		try {
			HttpURLConnection connection = (HttpURLConnection) new URL(MOJANG_NAME_HISTORY_URL + uuid.toString().replace("-", "") + "/names").openConnection();
			array = (JSONArray) jsonParser.parse(new InputStreamReader(connection.getInputStream()));
		} catch (Exception e) {
			log.info(logPrefix + "Failed to connect MojangAPI!");
		}
		return array;
	}

	private static Map<String, Date> getNameHistoryMap(UUID uuid) {
		Map<String, Date> map = new HashMap<String, Date>();
		try {
			JSONArray array = getNameHistoryJSON(uuid);
			Iterator<JSONObject> iterator = array.iterator();
			while (iterator.hasNext()) {
				JSONObject obj = (JSONObject) iterator.next();
				Date date = null;
				String playerName = (String) obj.get("name");
				if (obj.get(MOJANG_DATE) != null) {
					long dateL = ((Long) obj.get(MOJANG_DATE)).longValue();
					date = new Date(dateL);
				}
				map.put(playerName, date);
			}
		} catch (Exception e) {
			log.info(logPrefix + "Failed to get HashMap!");
		}
		return map;
	}
}
