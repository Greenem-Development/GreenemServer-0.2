package me.greenem.server.mainEvents;

import java.util.logging.Logger;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.bukkit.scheduler.BukkitScheduler;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;

import me.greenem.server.PluginMessages.IncomingMessages;
import me.greenem.server.PluginMessages.OutcomingMessages;
import me.greenem.server.commandExecutors.SpawnHorseBikeCommandExecutor;
import me.greenem.server.tabCompleters.ModeCommandTabCompleter;


public class MainClass extends JavaPlugin implements PluginMessageListener {
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
		getServer().getMessenger().registerIncomingPluginChannel(this, "my:testchannel1", new IncomingMessages());
		getServer().getMessenger().registerOutgoingPluginChannel(this, "my:testchannel1");
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

	@Override
	  public void onPluginMessageReceived(String channel, Player player, byte[] message) {
	    OutcomingMessages.getInstance().sendOutPluginMessage(channel, player, message);
	}
}
