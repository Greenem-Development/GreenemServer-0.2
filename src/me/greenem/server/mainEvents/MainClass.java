package me.greenem.server.mainEvents;

import java.util.logging.Logger;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;


public class MainClass extends JavaPlugin {
	public Logger log = getLogger();
//	private static volatile MainClass INSTANCE;
	BukkitScheduler scheduler = getServer().getScheduler();
	public Plugin plugin = this;
	
//	public static MainClass getInstance() {
//		if(INSTANCE == null) {
//			INSTANCE = this;
//		}
//		return INSTANCE;
//	}
//	
//	public Plugin getPlugin() {
//		return plugin;
//	}
	
	@Override
	public void onDisable() {
		MixedEvents.getInstance().onDisable();
	}
	
	@Override
	public void onEnable() {
//		INSTANCE = this;
		RegisterAllEvents();
		MixedEvents.getInstance().onEnable();
	}
	
	public void RegisterAllEvents() {
		getServer().getPluginManager().registerEvents(MixedEvents.getInstance(), this);
		getServer().getPluginManager().registerEvents(AuthEvents.getInstance(), this);
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
		return MixedEvents.getInstance().onCommand(sender, cmd, commandLabel, args);
	}
}
