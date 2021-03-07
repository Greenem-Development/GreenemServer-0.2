package kernitus.plugin.OldCombatMechanics.module;

import kernitus.plugin.OldCombatMechanics.OCMMain;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.projectiles.ProjectileSource;

public class ModuleDisableBowBoost extends Module {
	public ModuleDisableBowBoost(OCMMain plugin) {
		super(plugin, "disable-bow-boost");
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onProjectileHit(EntityDamageByEntityEvent e) {
		if (e.getEntity() instanceof Player) {
			Player player = (Player) e.getEntity();
			if (this.isEnabled(player.getWorld())) {
				if (e.getDamager() instanceof Arrow) {
					Arrow arrow = (Arrow) e.getDamager();
					ProjectileSource shooter = arrow.getShooter();
					if (shooter instanceof Player) {
						Player shootingPlayer = (Player) shooter;
						if (player.getUniqueId().equals(shootingPlayer.getUniqueId())) {
							e.setCancelled(true);
							this.debug("We cancelled your bow boost", player);
						}
					}

				}
			}
		}
	}
}