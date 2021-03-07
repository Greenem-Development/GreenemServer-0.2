package kernitus.plugin.OldCombatMechanics.module;

import kernitus.plugin.OldCombatMechanics.OCMMain;
import kernitus.plugin.OldCombatMechanics.utilities.Config;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class ModuleDisableElytra extends Module {
	private static final int CHEST_PIECE = 38;

	public ModuleDisableElytra(OCMMain plugin) {
		super(plugin, "disable-elytra");
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onInventoryClick(InventoryClickEvent e) {
		if (e.getWhoClicked() instanceof Player) {
			Player player = (Player) e.getWhoClicked();
			if (this.isEnabled(player.getWorld())) {
				if (player.getGameMode() != GameMode.CREATIVE) {
					InventoryType type = e.getInventory().getType();
					if (type == InventoryType.CRAFTING || type == InventoryType.PLAYER) {
						ItemStack cursor = e.getCursor();
						ItemStack currentItem = e.getCurrentItem();
						if (e.getAction() == InventoryAction.HOTBAR_SWAP) {
							if (!this.isElytra(player.getInventory().getItem(e.getHotbarButton()))) {
								return;
							}

							if (e.getSlot() != 38) {
								return;
							}
						}

						if (this.isElytra(currentItem) && e.getSlot() != 38
								&& (e.getClick() == ClickType.SHIFT_LEFT || e.getClick() == ClickType.SHIFT_RIGHT)) {
							e.setCancelled(true);
						}

						if (e.getAction() == InventoryAction.HOTBAR_SWAP
								&& this.isElytra(player.getInventory().getItem(e.getHotbarButton()))) {
							e.setCancelled(true);
						}

						if (e.getSlot() == 38 && this.isElytra(cursor)) {
							e.setCancelled(true);
						}

					}
				}
			}
		}
	}

	private boolean isElytra(ItemStack item) {
		return item != null && item.getType() == Material.ELYTRA;
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onRightClick(PlayerInteractEvent e) {
		if (this.isEnabled(e.getPlayer().getWorld())) {
			Action a = e.getAction();
			if (a == Action.RIGHT_CLICK_AIR || a == Action.RIGHT_CLICK_BLOCK) {
				if (this.isElytra(e.getItem())) {
					Block block = e.getClickedBlock();
					if (block == null || !Config.getInteractiveBlocks().contains(block.getType())) {
						e.setCancelled(true);
					}
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onDrag(InventoryDragEvent e) {
		if (this.isEnabled(e.getWhoClicked().getWorld())) {
			if (e.getOldCursor() != null && this.isElytra(e.getCursor())) {
				if (e.getInventorySlots().contains(38)) {
					e.setCancelled(true);
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onWorldChange(PlayerChangedWorldEvent e) {
		Player player = e.getPlayer();
		World world = player.getWorld();
		if (this.isEnabled(world)) {
			PlayerInventory inventory = player.getInventory();
			ItemStack chestplate = inventory.getChestplate();
			if (this.isElytra(chestplate)) {
				inventory.setChestplate(new ItemStack(Material.AIR));
				if (inventory.firstEmpty() != -1) {
					inventory.addItem(new ItemStack[]{chestplate});
				} else {
					world.dropItem(player.getLocation(), chestplate);
				}

			}
		}
	}
}