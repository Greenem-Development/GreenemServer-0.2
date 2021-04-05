package me.greenem.server.PluginMessages;

import org.bukkit.craftbukkit.libs.jline.internal.Log;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;

public class IncomingMessages implements PluginMessageListener {

	@Override
	public void onPluginMessageReceived(String channel, Player player, byte[] message) {
		if(channel.equals("my:testchannel1")) {
			ByteArrayDataInput in = ByteStreams.newDataInput(message);
		    String subchannel = in.readUTF();
		    if (subchannel.equals("my:testchannel1")) {
		    	Log.info(message);
		    }
		}
	}
	
}
