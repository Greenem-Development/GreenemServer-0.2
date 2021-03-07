package kernitus.plugin.OldCombatMechanics.module;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import kernitus.plugin.OldCombatMechanics.OCMMain;
import kernitus.plugin.OldCombatMechanics.utilities.Messenger;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.projectiles.ProjectileSource;

public class ModuleDisableEnderpearlCooldown extends Module {
	private final Set<UUID> ignoredPlayers = new HashSet();
	private Map<UUID, Long> lastLaunched;
	private int cooldown;
	private String message;

	public ModuleDisableEnderpearlCooldown(OCMMain plugin) {
		super(plugin, "disable-enderpearl-cooldown");
		this.reload();
	}

	public void reload() {
		this.cooldown = this.module().getInt("cooldown");
		if (this.cooldown > 0) {
			if (this.lastLaunched == null) {
				this.lastLaunched = new HashMap();
			}
		} else {
			this.lastLaunched = null;
		}

		this.message = this.module().getBoolean("showMessage") ? this.module().getString("message") : null;
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerShoot(ProjectileLaunchEvent e) {
		if (!e.isCancelled()) {
			Projectile projectile = e.getEntity();
			if (projectile instanceof EnderPearl) {
				ProjectileSource shooter = projectile.getShooter();
				if (shooter instanceof Player) {
					Player player = (Player) shooter;
					if (this.isEnabled(player.getWorld())) {
						UUID uuid = player.getUniqueId();
						if (!this.ignoredPlayers.contains(uuid)) {
							e.setCancelled(true);
							if (this.lastLaunched != null) {
								long currentTime = System.currentTimeMillis() / 1000L;
								if (this.lastLaunched.containsKey(uuid)) {
									long elapsedSeconds = currentTime - (Long) this.lastLaunched.get(uuid);
									if (elapsedSeconds < (long) this.cooldown) {
										if (this.message != null) {
											Messenger.sendNormalMessage(player, this.message,
													new Object[]{(long) this.cooldown - elapsedSeconds});
										}

										return;
									}
								}

								this.lastLaunched.put(uuid, currentTime);
							}

							this.ignoredPlayers.add(uuid);
							EnderPearl pearl = (EnderPearl) player.launchProjectile(EnderPearl.class);
							this.ignoredPlayers.remove(uuid);
							pearl.setVelocity(player.getEyeLocation().getDirection().multiply(2));
							if (player.getGameMode() != GameMode.CREATIVE) {
								PlayerInventory playerInventory = player.getInventory();
								ItemStack mainHand = playerInventory.getItemInMainHand();
								ItemStack offHand = playerInventory.getItemInOffHand();
								ItemStack enderpearlItemStack;
								if (this.isEnderPearl(mainHand)) {
									enderpearlItemStack = mainHand;
								} else {
									if (!this.isEnderPearl(offHand)) {
										return;
									}

									enderpearlItemStack = offHand;
								}

								enderpearlItemStack.setAmount(enderpearlItemStack.getAmount() - 1);
							}
						}
					}
				}
			}
		}
	}

	private boolean isEnderPearl(ItemStack itemStack) {
		return itemStack != null && itemStack.getType() == Material.ENDER_PEARL;
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e) {
		if (this.lastLaunched != null) {
			this.lastLaunched.remove(e.getPlayer().getUniqueId());
		}

	}
}