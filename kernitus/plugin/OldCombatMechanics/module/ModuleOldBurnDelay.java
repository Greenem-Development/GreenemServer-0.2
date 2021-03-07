package kernitus.plugin.OldCombatMechanics.module;

import kernitus.plugin.OldCombatMechanics.OCMMain;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

public class ModuleOldBurnDelay extends Module {
	private int fireTicks;

	public ModuleOldBurnDelay(OCMMain plugin) {
		super(plugin, "old-burn-delay");
		this.reload();
	}

	public void reload() {
		this.fireTicks = this.module().getInt("fire-ticks");
	}

	@EventHandler
	public void onFireTick(EntityDamageEvent e) {
		Entity entity = e.getEntity();
		if (this.isEnabled(entity.getWorld())) {
			if (e.getCause() == DamageCause.FIRE) {
				entity.setFireTicks(this.fireTicks);
				this.debug("Setting fire ticks to " + this.fireTicks, entity);
			}

		}
	}
}