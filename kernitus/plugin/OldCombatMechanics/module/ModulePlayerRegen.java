package kernitus.plugin.OldCombatMechanics.module;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import kernitus.plugin.OldCombatMechanics.OCMMain;
import kernitus.plugin.OldCombatMechanics.utilities.MathHelper;
import me.vagdedes.spartan.api.API;
import me.vagdedes.spartan.system.Enums.HackType;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent.RegainReason;
import org.bukkit.event.player.PlayerQuitEvent;

public class ModulePlayerRegen extends Module {
	private final Map<UUID, Long> healTimes = new HashMap();
	private boolean spartanInstalled;

	public ModulePlayerRegen(OCMMain plugin) {
		super(plugin, "old-player-regen");
		this.initSpartan();
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onRegen(EntityRegainHealthEvent e) {
		if (!e.isCancelled()) {
			if (e.getEntityType() == EntityType.PLAYER && e.getRegainReason() == RegainReason.SATIATED) {
				Player p = (Player) e.getEntity();
				if (this.isEnabled(p.getWorld())) {
					UUID playerId = p.getUniqueId();
					e.setCancelled(true);
					float previousExhaustion = p.getExhaustion();
					float previousSaturation = p.getSaturation();
					long currentTime = System.currentTimeMillis();
					boolean hasLastHealTime = this.healTimes.containsKey(playerId);
					long lastHealTime = (Long) this.healTimes.computeIfAbsent(playerId, (id) -> {
						return currentTime;
					});
					this.debug("Exh: " + previousExhaustion + " Sat: " + previousSaturation + " Time: "
							+ (currentTime - lastHealTime), p);
					if (hasLastHealTime && currentTime - lastHealTime <= this.module().getLong("interval")) {
						Bukkit.getScheduler().runTaskLater(this.plugin, () -> {
							p.setExhaustion(previousExhaustion);
						}, 1L);
					} else {
						double maxHealth = p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
						double playerHealth = p.getHealth();
						if (playerHealth < maxHealth) {
							p.setHealth(MathHelper.clamp(playerHealth + (double) this.module().getInt("amount"), 0.0D,
									maxHealth));
							this.healTimes.put(playerId, currentTime);
							if (this.spartanInstalled) {
								this.disableSpartanRegenCheck(p);
							}
						}

						float exhaustionToApply = (float) this.module().getDouble("exhaustion");
						Bukkit.getScheduler().runTaskLater(this.plugin, () -> {
							p.setExhaustion(previousExhaustion + exhaustionToApply);
							this.debug("Exh before: " + previousExhaustion + " Now: " + p.getExhaustion() + " Sat now: "
									+ previousSaturation, p);
						}, 1L);
					}
				}
			}
		}
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e) {
		this.healTimes.remove(e.getPlayer().getUniqueId());
	}

	private void disableSpartanRegenCheck(Player player) {
		int ticksToCancel = this.plugin.getConfig().getInt("support.spartan-cancel-ticks", 1);
		API.cancelCheck(player, HackType.FastHeal, ticksToCancel);
	}

	private void initSpartan() {
		this.spartanInstalled = Bukkit.getPluginManager().getPlugin("Spartan") != null;
	}
}