package me.greenem.server.general;

import java.sql.SQLException;
import java.util.logging.Logger;

import org.bukkit.plugin.Plugin;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import me.greenem.server.dataBase.MainDao;


public class Profiles implements Listener {
	public Plugin plugin = Bukkit.getPluginManager().getPlugin("GreenemServerMainPlugin");
	public Logger log = plugin.getLogger();
	
	private static volatile Profiles INSTANCE;
	
	public static Profiles getInstance() {
		if(INSTANCE == null) {
			INSTANCE = new Profiles();
		}
		return INSTANCE;
	}
	
	public boolean getOp(Player p) {
		String isOp = "";
		try {
			isOp = MainDao.getInstance().getAnything("players_profiles", "name", p.getName(), "isOP").toString();
		} catch (SQLException e) {
			//e.printStackTrace();
		}
		if(isOp.equalsIgnoreCase("1")) {
			return true;
		}
		return false;
	}
}
