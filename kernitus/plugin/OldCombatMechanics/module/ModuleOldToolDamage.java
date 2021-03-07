package kernitus.plugin.OldCombatMechanics.module;

import java.util.Locale;
import kernitus.plugin.OldCombatMechanics.OCMMain;
import kernitus.plugin.OldCombatMechanics.utilities.damage.DamageUtils;
import kernitus.plugin.OldCombatMechanics.utilities.damage.OCMEntityDamageByEntityEvent;
import kernitus.plugin.OldCombatMechanics.utilities.damage.WeaponDamages;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;

public class ModuleOldToolDamage extends Module {
	private static final String[] WEAPONS = new String[]{"sword", "axe", "pickaxe", "spade", "shovel", "hoe"};

	public ModuleOldToolDamage(OCMMain plugin) {
		super(plugin, "old-tool-damage");
	}

	@EventHandler(ignoreCancelled = true)
	public void onEntityDamaged(OCMEntityDamageByEntityEvent event) {
		Entity damager = event.getDamager();
		World world = damager.getWorld();
		if (this.isEnabled(world)) {
			Material weaponMaterial = event.getWeapon().getType();
			if (this.isTool(weaponMaterial)) {
				double weaponDamage = WeaponDamages.getDamage(weaponMaterial);
				if (weaponDamage <= 0.0D) {
					this.debug("Unknown tool type: " + weaponMaterial, damager);
				} else {
					double oldBaseDamage = event.getBaseDamage();
					event.setBaseDamage(weaponDamage);
					this.debug(
							"Old " + weaponMaterial + " damage: " + oldBaseDamage + " New tool damage: " + weaponDamage,
							damager);
					double newSharpnessDamage = DamageUtils.getOldSharpnessDamage(event.getSharpnessLevel());
					this.debug("Old sharpness damage: " + event.getSharpnessDamage() + " New: " + newSharpnessDamage,
							damager);
					event.setSharpnessDamage(newSharpnessDamage);
				}
			}
		}
	}

	private boolean isTool(Material material) {
		String[] var2 = WEAPONS;
		int var3 = var2.length;

		for (int var4 = 0; var4 < var3; ++var4) {
			String type = var2[var4];
			if (this.isOfType(material, type)) {
				return true;
			}
		}

		return false;
	}

	private boolean isOfType(Material mat, String type) {
		return mat.toString().endsWith("_" + type.toUpperCase(Locale.ROOT));
	}
}