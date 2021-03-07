package kernitus.plugin.OldCombatMechanics.module;

import java.util.EnumSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import kernitus.plugin.OldCombatMechanics.OCMMain;
import kernitus.plugin.OldCombatMechanics.module.ModuleOldArmourStrength.EnchantmentType;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDamageEvent.DamageModifier;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;

public class ModuleOldArmourStrength extends Module {
	private static final double REDUCTION_PER_ARMOUR_POINT = 0.04D;
	private static final Set<DamageCause> NON_REDUCED_CAUSES;

	public ModuleOldArmourStrength(OCMMain plugin) {
		super(plugin, "old-armour-strength");
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onEntityDamage(EntityDamageEvent e) {
		if (e.getEntity() instanceof LivingEntity) {
			LivingEntity damagedEntity = (LivingEntity) e.getEntity();
			if (e.isApplicable(DamageModifier.MAGIC)) {
				double armourPoints = damagedEntity.getAttribute(Attribute.GENERIC_ARMOR).getValue();
				double reductionPercentage = armourPoints * 0.04D;
				double reducedDamage = e.getDamage() * reductionPercentage;
				DamageCause damageCause = e.getCause();
				if (!NON_REDUCED_CAUSES.contains(damageCause) && e.isApplicable(DamageModifier.ARMOR)) {
					e.setDamage(DamageModifier.ARMOR, -reducedDamage);
				}

				double enchantmentReductionPercentage = this
						.calculateEnchantmentReductionPercentage(damagedEntity.getEquipment(), e.getCause());
				if (enchantmentReductionPercentage > 0.0D) {
					e.setDamage(DamageModifier.MAGIC, 0.0D);
					e.setDamage(DamageModifier.MAGIC, -e.getFinalDamage() * enchantmentReductionPercentage);
				}

			}
		}
	}

	private double calculateEnchantmentReductionPercentage(EntityEquipment equipment, DamageCause cause) {
		int totalEpf = 0;
		ItemStack[] var4 = equipment.getArmorContents();
		int var5 = var4.length;

		for (int var6 = 0; var6 < var5; ++var6) {
			ItemStack armourItem = var4[var6];
			if (armourItem != null && armourItem.getType() != Material.AIR) {
				EnchantmentType[] var8 = EnchantmentType.values();
				int var9 = var8.length;

				for (int var10 = 0; var10 < var9; ++var10) {
					EnchantmentType enchantmentType = var8[var10];
					if (enchantmentType.protectsAgainst(cause)) {
						int enchantmentLevel = armourItem.getEnchantmentLevel(enchantmentType.getEnchantment());
						if (enchantmentLevel > 0) {
							totalEpf += enchantmentType.getEpf(enchantmentLevel);
						}
					}
				}
			}
		}

		totalEpf = Math.min(25, totalEpf);
		totalEpf = (int) Math.ceil((double) totalEpf * ThreadLocalRandom.current().nextDouble(0.5D, 1.0D));
		totalEpf = Math.min(20, totalEpf);
		return 0.04D * (double) totalEpf;
	}

	static {
		NON_REDUCED_CAUSES = EnumSet.of(DamageCause.FIRE_TICK, DamageCause.VOID, DamageCause.SUFFOCATION,
				DamageCause.DROWNING, DamageCause.STARVATION, DamageCause.FALL, DamageCause.MAGIC,
				DamageCause.LIGHTNING);
	}
}