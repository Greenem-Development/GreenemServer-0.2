package kernitus.plugin.OldCombatMechanics.module;

import kernitus.plugin.OldCombatMechanics.OCMMain;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.BrewingStand;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.BrewEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;

public class ModuleOldBrewingStand extends Module {
	public ModuleOldBrewingStand(OCMMain plugin) {
		super(plugin, "old-brewing-stand");
	}

	@EventHandler
	public void onBrew(BrewEvent e) {
		Block block = e.getBlock();
		if (this.isEnabled(block.getWorld())) {
			BlockState blockState = block.getState();
			this.refuel(blockState);
		}
	}

	private void refuel(BlockState blockState) {
		if (blockState instanceof BrewingStand) {
			BrewingStand brewingStand = (BrewingStand) blockState;
			brewingStand.setFuelLevel(20);
			brewingStand.update();
		}
	}

	@EventHandler
	public void onInventoryOpen(InventoryOpenEvent e) {
		if (this.isEnabled(e.getPlayer().getWorld())) {
			Inventory inv = e.getInventory();
			if (inv != null) {
				Location loc = null;

				try {
					loc = inv.getLocation();
				} catch (Exception var5) {
					;
				}

				if (loc != null) {
					Block block = loc.getBlock();
					this.refuel(block.getState());
				}
			}
		}
	}
}