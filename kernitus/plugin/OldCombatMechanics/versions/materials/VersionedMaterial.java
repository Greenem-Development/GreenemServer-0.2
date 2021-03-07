package kernitus.plugin.OldCombatMechanics.versions.materials;

import org.bukkit.inventory.ItemStack;

public interface VersionedMaterial {
	ItemStack newInstance();

	boolean isSame(ItemStack var1);
}