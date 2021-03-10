package me.greenem.server.otherStuff;

import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import me.greenem.server.*;
import me.greenem.server.classes.*;
import me.greenem.server.mainEvents.*;
import me.greenem.server.otherStuff.*;

public class CommendExecutorClass implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
		/*
		Player p = sender.getServer().getPlayer(sender.getName());
		Server server = sender.getServer();
		if(cmd.getName().equalsIgnoreCase("mode")){
			ServerPlugin.ModeCommand(p, args);
		}
		*/
		return false;
	}
	
}
