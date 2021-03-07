package kernitus.plugin.OldCombatMechanics.module;

import kernitus.plugin.OldCombatMechanics.OCMMain;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDamageEvent.DamageModifier;

public class ModuleShieldDamageReduction extends Module {
	private String genericDamageReduction;
	private String projectileDamageReduction;

	public ModuleShieldDamageReduction(OCMMain plugin) {
		super(plugin, "shield-damage-reduction");
	}

	public void reload() {
		this.genericDamageReduction = this.module().getString("genericDamageReduction", "50%").replaceAll(" ", "");
		this.projectileDamageReduction = this.module().getString("projectileDamageReduction", "50%").replaceAll(" ",
				"");
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onHit(EntityDamageByEntityEvent e) {
		Entity entity = e.getEntity();
		if (entity instanceof Player) {
			Player player = (Player) entity;
			if (this.isEnabled(player.getWorld())) {
				if (this.shieldBlockedDamage(e)) {
					double reducedDamage = this.getReducedDamage(e.getDamage(), e.getCause());
					e.setDamage(DamageModifier.BLOCKING, 0.0D);
					if (e.getFinalDamage() >= reducedDamage) {
						e.setDamage(DamageModifier.BLOCKING, -reducedDamage);
					}

					this.debug("Damage reduced by: " + e.getDamage(DamageModifier.BLOCKING), player);
				}
			}
		}
	}

	private double getReducedDamage(double fullDamage, DamageCause damageCause) {
		String damageReductionString = damageCause == DamageCause.PROJECTILE
				? this.projectileDamageReduction
				: this.genericDamageReduction;
		if (damageReductionString.matches("\\d{1,3}%")) {
			int percentage = Integer.parseInt(damageReductionString.replace("%", ""));
			fullDamage = (fullDamage - 1.0D) * (double) percentage / 100.0D;
		} else if (damageReductionString.matches("\\d+")) {
			fullDamage = (double) Integer.parseInt(damageReductionString);
		} else {
			fullDamage = 0.0D;
		}

		if (fullDamage < 0.0D) {
			fullDamage = 0.0D;
		}

		return fullDamage;
	}

	private boolean shieldBlockedDamage(EntityDamageByEntityEvent e) {
		return e.getDamage(DamageModifier.BLOCKING) < 0.0D;
	}
}