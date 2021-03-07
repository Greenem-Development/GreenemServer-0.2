package kernitus.plugin.OldCombatMechanics.versions.materials;

import java.util.function.Supplier;
import kernitus.plugin.OldCombatMechanics.utilities.reflection.Reflector;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class DualVersionedMaterial implements VersionedMaterial {
	private Supplier<ItemStack> oldItem;
	private Supplier<ItemStack> newItem;

	public DualVersionedMaterial(Supplier<ItemStack> oldItem, Supplier<ItemStack> newItem) {
		this.oldItem = oldItem;
		this.newItem = newItem;
	}

	public ItemStack newInstance() {
		return (ItemStack) this.getItemSupplier().get();
	}

	public boolean isSame(ItemStack other) {
		ItemStack baseInstance = this.newInstance();
		return baseInstance.getType() == other.getType() && baseInstance.getDurability() == other.getDurability();
	}

	private Supplier<ItemStack> getItemSupplier() {
		return Reflector.versionIsNewerOrEqualAs(1, 13, 0) ? this.newItem : this.oldItem;
	}

	public String toString() {
		return "DualVersionedMaterial{picked=" + (this.getItemSupplier() == this.newItem ? "new" : "old") + ", item="
				+ this.newInstance() + '}';
	}

	public static VersionedMaterial ofMaterialNames(String nameOld, String nameNew) {
		return new DualVersionedMaterial(fromMaterial(nameOld), fromMaterial(nameNew));
	}

	private static Supplier<ItemStack> fromMaterial(String name) {
		return () -> {
			return new ItemStack(Material.matchMaterial(name));
		};
	}
}