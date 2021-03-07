package kernitus.plugin.OldCombatMechanics.module;

import kernitus.plugin.OldCombatMechanics.OCMMain;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.util.Vector;

public class ModuleDisableProjectileRandomness extends Module {
	private static double EPSILON;

	public ModuleDisableProjectileRandomness(OCMMain plugin) {
		super(plugin, "disable-projectile-randomness");
		this.reload();
	}

	public void reload() {
		EPSILON = this.module().getDouble("epsilon");
	}

	@EventHandler
	public void onProjectileLaunch(ProjectileLaunchEvent e) {
		Projectile projectile = e.getEntity();
		ProjectileSource shooter = projectile.getShooter();
		if (shooter instanceof Player) {
			Player player = (Player) shooter;
			if (!this.isEnabled(player.getWorld())) {
				return;
			}

			this.debug("Making projectile go straight", player);
			Vector playerDirection = player.getLocation().getDirection().normalize();
			Vector projectileDirection = projectile.getVelocity();
			double originalMagnitude = projectileDirection.length();
			projectileDirection.normalize();
			if (!this.fuzzyVectorEquals(projectileDirection, playerDirection)) {
				if (this.fuzzyVectorEquals(projectileDirection, playerDirection.rotateAroundY(0.17D))) {
					this.debug("10° Offset", player);
				} else if (this.fuzzyVectorEquals(projectileDirection, playerDirection.rotateAroundY(-0.35D))) {
					this.debug("-10° Offset", player);
				}
			}

			playerDirection.multiply(originalMagnitude);
			projectile.setVelocity(playerDirection);
		}

	}

	private boolean fuzzyVectorEquals(Vector a, Vector b) {
		return Math.abs(a.getX() - b.getX()) < EPSILON && Math.abs(a.getZ() - b.getZ()) < EPSILON;
	}
}