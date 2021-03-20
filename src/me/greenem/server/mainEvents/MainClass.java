package me.greenem.server.mainEvents;

import java.util.logging.Logger;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import me.greenem.server.commandExecutors.SpawnHorseBikeCommandExecutor;
import me.greenem.server.tabCompleters.ModeCommandTabCompleter;


public class MainClass extends JavaPlugin {
	public Logger log = getLogger();
	BukkitScheduler scheduler = getServer().getScheduler();
	public Plugin plugin = this;
	
	@Override
	public void onDisable() {
		MixedEvents.getInstance().onDisable();
	}
	
	@Override
	public void onEnable() {
		RegisterAllEvents();
		RegisterAllCommands();
		MixedEvents.getInstance().onEnable();
	}
	
	public void RegisterAllEvents() {
		getServer().getPluginManager().registerEvents(MixedEvents.getInstance(), this);
		getServer().getPluginManager().registerEvents(AuthEvents.getInstance(), this);
		getServer().getPluginManager().registerEvents(Tests.getInstance(), this);
	}
	
	public void RegisterAllCommands() {
		getCommand("mode").setTabCompleter(new ModeCommandTabCompleter());
		getCommand("horsebike").setExecutor(new SpawnHorseBikeCommandExecutor());
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
		return MixedEvents.getInstance().onCommand(sender, cmd, commandLabel, args);
	}
}
