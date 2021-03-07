package kernitus.plugin.OldCombatMechanics.module;

import java.util.concurrent.ThreadLocalRandom;
import kernitus.plugin.OldCombatMechanics.OCMMain;
import kernitus.plugin.OldCombatMechanics.module.ModuleChorusFruit.1;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

public class ModuleChorusFruit extends Module {
	public ModuleChorusFruit(OCMMain plugin) {
		super(plugin, "chorus-fruit");
	}

	@EventHandler
   public void onEat(PlayerItemConsumeEvent e) {
      if (e.getItem().getType() == Material.CHORUS_FRUIT) {
         Player player = e.getPlayer();
         if (this.isEnabled(player.getWorld())) {
            if (this.module().getBoolean("prevent-eating")) {
               e.setCancelled(true);
            } else {
               int hungerValue = this.module().getInt("hunger-value");
               double saturationValue = this.module().getDouble("saturation-value");
               int previousFoodLevel = player.getFoodLevel();
               float previousSaturation = player.getSaturation();
               (new 1(this, hungerValue, previousFoodLevel, saturationValue, previousSaturation, player)).runTaskLater(this.plugin, 2L);
            }
         }
      }
   }

	@EventHandler
	public void onTeleport(PlayerTeleportEvent e) {
		if (e.getCause() == TeleportCause.CHORUS_FRUIT) {
			Player player = e.getPlayer();
			if (this.isEnabled(player.getWorld())) {
				double distance = this.getMaxTeleportationDistance();
				if (distance == 8.0D) {
					this.debug("Using vanilla teleport implementation!", player);
				} else if (distance <= 0.0D) {
					this.debug("Chorus teleportation is not allowed", player);
					e.setCancelled(true);
				} else {
					Location toLocation = e.getTo();
					if (toLocation == null) {
						this.debug("Teleport target is null", player);
					} else {
						int maxheight = toLocation.getWorld().getMaxHeight();
						e.setTo(player.getLocation().add(ThreadLocalRandom.current().nextDouble(-distance, distance),
								this.clamp(ThreadLocalRandom.current().nextDouble(-distance, distance), 0.0D,
										(double) (maxheight - 1)),
								ThreadLocalRandom.current().nextDouble(-distance, distance)));
					}
				}
			}
		}
	}

	private double clamp(double x, double min, double max) {
		return Math.max(Math.min(x, max), min);
	}

	private double getMaxTeleportationDistance() {
		return this.module().getDouble("max-teleportation-distance");
	}
}