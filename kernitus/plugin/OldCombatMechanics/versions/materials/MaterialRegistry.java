package kernitus.plugin.OldCombatMechanics.versions.materials;

import java.util.function.Supplier;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class MaterialRegistry {
	public static VersionedMaterial LAPIS_LAZULI = new DualVersionedMaterial(() -> {
		return new ItemStack(Material.matchMaterial("INK_SACK"), 1, (short) 4);
	}, () -> {
		return new ItemStack(Material.matchMaterial("LAPIS_LAZULI"));
	});
	public static VersionedMaterial ENCHANTED_GOLDEN_APPLE = new DualVersionedMaterial(() -> {
		return new ItemStack(Material.GOLDEN_APPLE, 1, (short) 1);
	}, () -> {
		return new ItemStack(Material.valueOf("ENCHANTED_GOLDEN_APPLE"));
	});
}