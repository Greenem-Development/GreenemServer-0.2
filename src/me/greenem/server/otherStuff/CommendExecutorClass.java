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
		if((cmd.getName().equalsIgnoreCase("hub")) || (cmd.getName().equalsIgnoreCase("spawn"))){
			ServerPlugin.tpToSpawn(server.getPlayer(sender.getName()));
		}
		else if(cmd.getName().equalsIgnoreCase("mode")){
			ServerPlugin.ModeCommand(p, args);
		}
		else if(cmd.getName().equalsIgnoreCase("setcount")){
			SetCountCommand(p, args);
		}
		else if(cmd.getName().equalsIgnoreCase("setteam")){ // setplayer <team> <player> <remove>
			SetTeamCommand(sender);
		}
		else if(cmd.getName().equalsIgnoreCase("test")){
			TestCommand(p);
		}
		else if(cmd.getName().equalsIgnoreCase("gm")){
			GmCommand(sender, args);
		}
		else if(cmd.getName().equalsIgnoreCase("heal")){
			HealCommand(sender, args);
		}
		else if(cmd.getName().equalsIgnoreCase("feather")){
			FeatherCommand(p);
		}
		else if(cmd.getName().equalsIgnoreCase("v")){
			VanishCommand(p, args);
		}
		else if(cmd.getName().equalsIgnoreCase("chesttocode")){
			ChestToCodeCommand(p);
		}
		else if(cmd.getName().equalsIgnoreCase("createnps")){
			CreateNPC(p);
		}
		else if(cmd.getName().equalsIgnoreCase("destroynpc")){
			DeleteAllNPC(p);
		}
		else if(cmd.getName().equalsIgnoreCase("fly")){
			ChangeAllowFly(p);
		}
		*/
		return false;
	}
	
}
