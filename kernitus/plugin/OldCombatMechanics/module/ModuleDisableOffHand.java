package kernitus.plugin.OldCombatMechanics.module;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiPredicate;
import kernitus.plugin.OldCombatMechanics.OCMMain;
import kernitus.plugin.OldCombatMechanics.module.ModuleDisableOffHand.BlockType;
import kernitus.plugin.OldCombatMechanics.utilities.ConfigUtils;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Event.Result;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;

public class ModuleDisableOffHand extends Module {
	private static final int OFFHAND_SLOT = 40;
	private List<Material> materials = new ArrayList();

	public ModuleDisableOffHand(OCMMain plugin) {
		super(plugin, "disable-offhand");
	}

	public void reload() {
		this.materials = ConfigUtils.loadMaterialList(this.module(), "items");
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onSwapHandItems(PlayerSwapHandItemsEvent e) {
		if (this.isEnabled(e.getPlayer().getWorld()) && this.shouldWeCancel(e.getOffHandItem())) {
			e.setCancelled(true);
		}

	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onInventoryClick(InventoryClickEvent e) {
		if (this.isEnabled(e.getWhoClicked().getWorld())) {
			ClickType clickType = e.getClick();

			try {
				if (clickType == ClickType.SWAP_OFFHAND) {
					e.setResult(Result.DENY);
					e.setCancelled(true);
					return;
				}
			} catch (NoSuchFieldError var4) {
				;
			}

			if (e.getInventory().getType() == InventoryType.CRAFTING && e.getSlot() == 40) {
				if (clickType == ClickType.NUMBER_KEY || this.shouldWeCancel(e.getCursor())) {
					e.setResult(Result.DENY);
					e.setCancelled(true);
				}

			}
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onInventoryDrag(InventoryDragEvent e) {
		if (this.isEnabled(e.getWhoClicked().getWorld()) && e.getInventory().getType() == InventoryType.CRAFTING
				&& e.getInventorySlots().contains(40)) {
			if (this.shouldWeCancel(e.getOldCursor())) {
				e.setResult(Result.DENY);
				e.setCancelled(true);
			}

		}
	}

	private boolean shouldWeCancel(ItemStack item) {
		if (item != null && item.getType() != Material.AIR) {
			return !this.getBlockType().isAllowed(this.materials, item.getType());
		} else {
			return false;
		}
	}

	private BlockType getBlockType() {
		return this.module().getBoolean("whitelist") ? BlockType.WHITELIST : BlockType.BLACKLIST;
	}

	private static <T, U> BiPredicate<T, U> not(BiPredicate<T, U> predicate) {
		return predicate.negate();
	}
}