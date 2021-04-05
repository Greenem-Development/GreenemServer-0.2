package me.greenem.server.PluginMessages;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.libs.jline.internal.Log;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.messaging.PluginMessageListener;

import com.google.common.collect.Iterables;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import me.greenem.server.dataBase.MainDao;

public class OutcomingMessages implements PluginMessageListener {
	
	private static volatile OutcomingMessages INSTANCE;
	public Plugin plugin = Bukkit.getPluginManager().getPlugin("GreenemServerMainPlugin");

	public static OutcomingMessages getInstance() {
		if(INSTANCE == null) {
			INSTANCE = new OutcomingMessages();
		}
		return INSTANCE;
	}
	
	private OutcomingMessages() {}
	
	@Override
	public void onPluginMessageReceived(String arg0, Player arg1, byte[] arg2) {
		Log.info(arg0 + " (overrided)");
	}
	
//	public void outcomingMessage(String channel, Player player, byte[] message) {
//		if (!channel.equals("BungeeCord")) {
//		      return;
//		    }
//		    ByteArrayDataInput in = ByteStreams.newDataInput(message);
//		    String subchannel = in.readUTF();
//		    if (subchannel.equals("SomeSubChannel")) {
//		      // Use the code sample in the 'Response' sections below to read
//		      // the data.
//		    }
//		    
//		    Log.info(channel);
//	}
	
	public void sendOutPluginMessage(String channel, Player player, byte[] message) {
		ByteArrayDataOutput out = ByteStreams.newDataOutput();
		  out.writeUTF("my:testchannel1");
		  out.writeUTF("Argument");

		  // If you don't care about the player
		  Player player2 = Iterables.getFirst(Bukkit.getOnlinePlayers(), null);
		  // Else, specify them
		  //Player player = Bukkit.getPlayerExact("Example");

		  player.sendPluginMessage(plugin, "BungeeCord", out.toByteArray());
	}

}
