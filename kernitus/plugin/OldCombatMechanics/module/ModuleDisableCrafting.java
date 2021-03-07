package kernitus.plugin.OldCombatMechanics.module;

import java.util.List;
import java.util.function.Consumer;
import kernitus.plugin.OldCombatMechanics.OCMMain;
import kernitus.plugin.OldCombatMechanics.utilities.ConfigUtils;
import kernitus.plugin.OldCombatMechanics.utilities.Messenger;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;

public class ModuleDisableCrafting extends Module {
	private List<Material> denied;
	private String message;

	public ModuleDisableCrafting(OCMMain plugin) {
		super(plugin, "disable-crafting");
		this.reload();
	}

	public void reload() {
		this.denied = ConfigUtils.loadMaterialList(this.module(), "denied");
		this.message = this.module().getBoolean("showMessage") ? this.module().getString("message") : null;
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPrepareItemCraft(PrepareItemCraftEvent e) {
		List<HumanEntity> viewers = e.getViewers();
		if (viewers.size() >= 1) {
			World world = ((HumanEntity) viewers.get(0)).getWorld();
			if (this.isEnabled(world)) {
				CraftingInventory inv = e.getInventory();
				ItemStack result = inv.getResult();
				if (result != null && this.denied.contains(result.getType())) {
					inv.setResult((ItemStack) null);
					if (this.message != null) {
						viewers.forEach((viewer) -> {
							Messenger.sendNormalMessage(viewer, this.message, new Object[0]);
						});
					}
				}

			}
		}
	}
}