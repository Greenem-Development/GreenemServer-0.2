package kernitus.plugin.OldCombatMechanics.versions.materials;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class NameListVersionedMaterial implements VersionedMaterial {
	private Material finalMaterial;

	private NameListVersionedMaterial(Material finalMaterial) {
		this.finalMaterial = finalMaterial;
	}

	public ItemStack newInstance() {
		return new ItemStack(this.finalMaterial);
	}

	public boolean isSame(ItemStack other) {
		return other.getType() == this.finalMaterial;
	}

	public static VersionedMaterial ofNames(String... names) {
		String[] var1 = names;
		int var2 = names.length;

		for (int var3 = 0; var3 < var2; ++var3) {
			String name = var1[var3];
			Material material = Material.matchMaterial(name);
			if (material != null) {
				return new NameListVersionedMaterial(material);
			}

			material = Material.matchMaterial(name, true);
			if (material != null) {
				return new NameListVersionedMaterial(material);
			}
		}

		throw new IllegalArgumentException(
				"Could not find any working material, tried: " + String.join(",", names) + ".");
	}
}