package me.greenem.server.commandExecutors;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class SpawnHorseBikeCommandExecutor implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		Player p = sender.getServer().getPlayer(sender.getName());
		if(cmd.getName().equalsIgnoreCase("horsebike")) {
			spawnHorseBike(p);
		}
		return false;
	}

	public void spawnHorseBike(Player p) {
		Horse horse = (Horse) p.getWorld().spawnEntity(p.getLocation().add(0, 0, 0), EntityType.HORSE);
		horse.getInventory().setSaddle(new ItemStack(Material.SADDLE, 1));
        horse.setTamed(true);
		ArmorStand stand = (ArmorStand) p.getWorld().spawnEntity(horse.getLocation().add(0, 1, 0), EntityType.ARMOR_STAND);
		stand.setVisible(false);
		horse.setPassenger(stand);
		stand.getLocation().setY(stand.getLocation().getY()-1);
		stand.setPassenger(p);
	}
	
}
