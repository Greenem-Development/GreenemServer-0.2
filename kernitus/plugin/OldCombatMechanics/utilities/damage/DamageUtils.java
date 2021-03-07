package kernitus.plugin.OldCombatMechanics.utilities.damage;

import java.util.function.Predicate;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

public class DamageUtils {
	public static double getNewSharpnessDamage(int level) {
		return level >= 1 ? 1.0D + (double) (level - 1) * 0.5D : 0.0D;
	}

	public static double getOldSharpnessDamage(int level) {
		return level >= 1 ? (double) level * 1.25D : 0.0D;
	}

	public static boolean isCriticalHit(Player p) {
		return !p.isOnGround() && p.getFallDistance() > 0.0F && !p.getLocation().getBlock().isLiquid()
				&& !p.isInsideVehicle() && !p.isSprinting() && p.getActivePotionEffects().stream().noneMatch((pe) -> {
					return pe.getType() == PotionEffectType.BLINDNESS;
				});
	}
}