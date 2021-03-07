package kernitus.plugin.OldCombatMechanics.utilities.potions;

import java.util.Optional;
import java.util.function.Predicate;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PotionEffects {
	private static boolean canUseGetPotionEffectsMethod;

	public static Optional<PotionEffect> get(LivingEntity entity, PotionEffectType type) {
		return Optional.ofNullable(getOrNull(entity, type));
	}

	public static PotionEffect getOrNull(LivingEntity entity, PotionEffectType type) {
		return canUseGetPotionEffectsMethod
				? entity.getPotionEffect(type)
				: (PotionEffect) entity.getActivePotionEffects().stream().filter((potionEffect) -> {
					return potionEffect.getType().equals(type);
				}).findAny().orElse((Object) null);
	}

	static {
		try {
			LivingEntity.class.getDeclaredMethod("getPotionEffect", PotionEffectType.class);
			canUseGetPotionEffectsMethod = true;
		} catch (NoSuchMethodException var1) {
			canUseGetPotionEffectsMethod = false;
		}

	}
}