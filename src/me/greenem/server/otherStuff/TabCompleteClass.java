package me.greenem.server.otherStuff;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

public class TabCompleteClass implements TabCompleter {

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		List<String> arguments = new ArrayList<>();
		sender.sendMessage(commandLabel);
		if(cmd.getName().equalsIgnoreCase("mode")) {
			if(args.length==1) {
				arguments.add("ff");
				arguments.add("mm");
				arguments.add("jr");
				arguments.add("tr");
				arguments.add("dw");
				arguments.add("sw");
				arguments.add("tg");
				return arguments;
			}
		}
		
		return null;
	}
	
}