package me.greenem.server.mainEvents;

import java.util.Random;
import java.util.logging.Logger;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;

import me.greenem.server.dataBase.MainDao;

public class AuthEvents implements Listener {
	//public Logger log = getLogger();
	//final Random random = new Random(System.currentTimeMillis());
	
	private static volatile AuthEvents INSTANCE;
	
	public static AuthEvents getInstance() {
		if(INSTANCE == null) {
			INSTANCE = new AuthEvents();
		}
		return INSTANCE;
	}
	
	public void MoveEventNew1(PlayerMoveEvent e) {
		System.out.println(e.getPlayer().getName());
		//ServerPlugin.getInstance().log.info("yes, another event listener file almost (2x) works!");
		
		//log.info("yes, another event listener file almost works!");
	}
	
}
