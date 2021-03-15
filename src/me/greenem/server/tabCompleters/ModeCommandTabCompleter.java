package me.greenem.server.tabCompleters;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

public class ModeCommandTabCompleter implements TabCompleter {

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if(cmd.getName().equalsIgnoreCase("mode")) {
			List<String> list = new ArrayList<String>();
			if(args.length==1) { //ff mm jr bw sw tr dw tg au test
				list.add("ff");
				list.add("mm");
				list.add("jr");
				list.add("bw");
				list.add("sw");
				list.add("tr");
				list.add("dw");
				list.add("tg");
				list.add("au");
				list.add("test");
			}
			for(String s : list) {
				if(!args[0].startsWith(s)) {
					list.remove(s);
				}
			}
			return list;
		}
		return null;
	}
}
