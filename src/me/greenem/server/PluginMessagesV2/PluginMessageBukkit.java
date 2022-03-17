package me.greenem.server.PluginMessagesV2;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.messaging.PluginMessageListener;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import me.greenem.server.mainEvents.MainClass;

public class PluginMessageBukkit implements PluginMessageListener {

	public Plugin plugin = Bukkit.getPluginManager().getPlugin("GreenemServerMainPlugin");
	
	@Override
	public void onPluginMessageReceived(String channel, Player p, byte[] message) {
		if(channel.equals("BungeeMain")) {
			ByteArrayDataInput input = ByteStreams.newDataInput(message);
			String subchannnel = input.readUTF();
		}
	}
	
	public void connect(Player p, String server) {
		ByteArrayDataOutput output = ByteStreams.newDataOutput();
		output.writeUTF("Connect");
		output.writeUTF(server);
		p.sendPluginMessage(plugin, "BungeeMain", output.toByteArray());
		//p.sendBlockChange(new Location(Bukkit.getWorld(""), 1, 1, 1), );
	}

}
