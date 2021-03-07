package kernitus.plugin.OldCombatMechanics.module;

import kernitus.plugin.OldCombatMechanics.OCMMain;
import kernitus.plugin.OldCombatMechanics.versions.materials.MaterialRegistry;
import kernitus.plugin.OldCombatMechanics.versions.materials.VersionedMaterial;
import org.bukkit.block.Block;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.EnchantingInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.permissions.Permissible;

public class ModuleNoLapisEnchantments extends Module {
	private VersionedMaterial lapisLazuli;

	public ModuleNoLapisEnchantments(OCMMain plugin) {
		super(plugin, "no-lapis-enchantments");
		this.lapisLazuli = MaterialRegistry.LAPIS_LAZULI;
	}

	@EventHandler
	public void onEnchant(EnchantItemEvent e) {
		Block block = e.getEnchantBlock();
		if (this.isEnabled(block.getWorld())) {
			if (!this.hasNoPermission(e.getEnchanter())) {
				EnchantingInventory ei = (EnchantingInventory) e.getInventory();
				ei.setSecondary(this.getLapis());
			}
		}
	}

	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		if (this.isEnabled(e.getWhoClicked().getWorld())) {
			if (e.getInventory().getType() == InventoryType.ENCHANTING) {
				if (!this.hasNoPermission(e.getWhoClicked())) {
					ItemStack item = e.getCurrentItem();
					if (item != null) {
						if (this.lapisLazuli.isSame(item) && e.getRawSlot() == 1) {
							e.setCancelled(true);
						} else if (e.getCursor() != null && this.lapisLazuli.isSame(e.getCursor())
								&& e.getClick() == ClickType.DOUBLE_CLICK) {
							e.setCancelled(true);
						}

					}
				}
			}
		}
	}

	@EventHandler
	public void onInventoryClose(InventoryCloseEvent e) {
		if (this.isEnabled(e.getPlayer().getWorld())) {
			Inventory inventory = e.getInventory();
			if (inventory != null && inventory.getType() == InventoryType.ENCHANTING) {
				((EnchantingInventory) inventory).setSecondary((ItemStack) null);
			}
		}
	}

	@EventHandler
	public void onInventoryOpen(InventoryOpenEvent e) {
		this.fillUpEnchantingTable(e.getPlayer(), e.getInventory());
	}

	private void fillUpEnchantingTable(HumanEntity player, Inventory inventory) {
		if (this.isEnabled(player.getWorld())) {
			if (inventory != null && inventory.getType() == InventoryType.ENCHANTING && !this.hasNoPermission(player)) {
				((EnchantingInventory) inventory).setSecondary(this.getLapis());
			}
		}
	}

	private ItemStack getLapis() {
		ItemStack lapis = this.lapisLazuli.newInstance();
		lapis.setAmount(64);
		return lapis;
	}

	private boolean hasNoPermission(Permissible player) {
		return this.isSettingEnabled("usePermission") && !player.hasPermission("oldcombatmechanics.nolapis");
	}
}