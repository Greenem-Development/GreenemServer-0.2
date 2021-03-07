package kernitus.plugin.OldCombatMechanics.utilities.damage;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import java.util.Map;
import java.util.function.Consumer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

public class MobDamage {
	private static final Map<EntityType, Enchantment> enchants;

	public static double applyEntityBasedDamage(EntityType entity, ItemStack item, double startDamage) {
		Enchantment enchantment = (Enchantment) enchants.get(entity);
		if (enchantment == null) {
			return startDamage;
		} else {
			return enchantment != Enchantment.DAMAGE_UNDEAD && enchantment != Enchantment.DAMAGE_ARTHROPODS
					? startDamage
					: startDamage + 2.5D * (double) item.getEnchantmentLevel(enchantment);
		}
	}

	static {
		Map<String, Enchantment> allMobs = ImmutableMap.builder().put("SKELETON", Enchantment.DAMAGE_UNDEAD)
				.put("ZOMBIE", Enchantment.DAMAGE_UNDEAD).put("WITHER", Enchantment.DAMAGE_UNDEAD)
				.put("WITHER_SKELETON", Enchantment.DAMAGE_UNDEAD).put("ZOMBIFIED_PIGLIN", Enchantment.DAMAGE_UNDEAD)
				.put("SKELETON_HORSE", Enchantment.DAMAGE_UNDEAD).put("STRAY", Enchantment.DAMAGE_UNDEAD)
				.put("HUSK", Enchantment.DAMAGE_UNDEAD).put("PHANTOM", Enchantment.DAMAGE_UNDEAD)
				.put("DROWNED", Enchantment.DAMAGE_UNDEAD).put("ZOGLIN", Enchantment.DAMAGE_UNDEAD)
				.put("SPIDER", Enchantment.DAMAGE_ARTHROPODS).put("CAVE_SPIDER", Enchantment.DAMAGE_ARTHROPODS)
				.put("BEE", Enchantment.DAMAGE_ARTHROPODS).put("SILVERFISH", Enchantment.DAMAGE_ARTHROPODS)
				.put("ENDERMITE", Enchantment.DAMAGE_ARTHROPODS).build();
		Builder<EntityType, Enchantment> enchantsBuilder = ImmutableMap.builder();
		allMobs.keySet().forEach((entityName) -> {
			try {
				EntityType entityType = EntityType.valueOf(entityName);
				Enchantment enchantment = (Enchantment) allMobs.get(entityName);
				enchantsBuilder.put(entityType, enchantment);
			} catch (IllegalArgumentException var5) {
				;
			}

		});
		enchants = enchantsBuilder.build();
	}
}