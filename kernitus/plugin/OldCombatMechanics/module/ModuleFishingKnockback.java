package kernitus.plugin.OldCombatMechanics.module;

import com.google.common.base.Functions;
import com.google.common.collect.ImmutableMap;
import java.util.EnumMap;
import java.util.function.Function;
import java.util.function.Predicate;
import kernitus.plugin.OldCombatMechanics.OCMMain;
import kernitus.plugin.OldCombatMechanics.utilities.reflection.MemoizingFeatureBranch;
import kernitus.plugin.OldCombatMechanics.utilities.reflection.Reflector;
import kernitus.plugin.OldCombatMechanics.utilities.reflection.MemoizingFeatureBranch.ExceptionalFunction;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FishHook;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDamageEvent.DamageModifier;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerFishEvent.State;
import org.bukkit.plugin.RegisteredListener;
import org.bukkit.util.Vector;

public class ModuleFishingKnockback extends Module {
	private MemoizingFeatureBranch<PlayerFishEvent, Entity> hookEntityFeature = MemoizingFeatureBranch
			.onException((playerFishEvent) -> {
				return playerFishEvent.getHook();
			}, (playerFishEvent) -> {
				return playerFishEvent.getHook();
			}, Reflector.memoizeMethodAndInvoke(PlayerFishEvent.class, "getHook", new Object[0]));

	public ModuleFishingKnockback(OCMMain plugin) {
		super(plugin, "old-fishing-knockback");
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onRodLand(ProjectileHitEvent e) {
		Entity hookEntity = e.getEntity();
		World world = hookEntity.getWorld();
		if (this.isEnabled(world)) {
			if (e.getEntityType() == EntityType.FISHING_HOOK) {
				boolean knockbackNonPlayerEntities = this.isSettingEnabled("knockbackNonPlayerEntities");

				Entity hitEntity;
				try {
					hitEntity = e.getHitEntity();
				} catch (NoSuchMethodError var17) {
					hitEntity = (Entity) world.getNearbyEntities(hookEntity.getLocation(), 0.25D, 0.25D, 0.25D).stream()
							.filter((entity) -> {
								return !knockbackNonPlayerEntities && entity instanceof Player;
							}).findFirst().orElse((Object) null);
				}

				if (hitEntity != null) {
					if (hitEntity instanceof LivingEntity) {
						if (knockbackNonPlayerEntities || hitEntity instanceof Player) {
							if (!hitEntity.hasMetadata("NPC")) {
								FishHook hook = (FishHook) hookEntity;
								Player rodder = (Player) hook.getShooter();
								if (!knockbackNonPlayerEntities) {
									Player player = (Player) hitEntity;
									this.debug("You were hit by a fishing rod!", player);
									if (player.equals(rodder)) {
										return;
									}

									if (player.getGameMode() == GameMode.CREATIVE) {
										return;
									}
								}

								LivingEntity livingEntity = (LivingEntity) hitEntity;
								if ((float) livingEntity
										.getNoDamageTicks() <= (float) livingEntity.getMaximumNoDamageTicks() / 2.0F) {
									double damage = this.module().getDouble("damage");
									if (damage < 0.0D) {
										damage = 0.2D;
									}

									EntityDamageEvent event = this.makeEvent(rodder, hitEntity, damage);
									Bukkit.getPluginManager().callEvent(event);
									if (this.module().getBoolean("checkCancelled") && event.isCancelled()) {
										if (this.plugin.getConfig().getBoolean("debug.enabled")) {
											this.debug("You can't do that here!", rodder);
											HandlerList hl = event.getHandlers();
											RegisteredListener[] var13 = hl.getRegisteredListeners();
											int var14 = var13.length;

											for (int var15 = 0; var15 < var14; ++var15) {
												RegisteredListener rl = var13[var15];
												this.debug("Plugin Listening: " + rl.getPlugin().getName(), rodder);
											}
										}

									} else {
										livingEntity.damage(damage);
										livingEntity
												.setVelocity(this.calculateKnockbackVelocity(livingEntity.getVelocity(),
														livingEntity.getLocation(), hook.getLocation()));
									}
								}
							}
						}
					}
				}
			}
		}
	}

	private Vector calculateKnockbackVelocity(Vector currentVelocity, Location player, Location hook) {
		double xDistance = hook.getX() - player.getX();

		double zDistance;
		for (zDistance = hook.getZ() - player.getZ(); xDistance * xDistance
				+ zDistance * zDistance < 1.0E-4D; zDistance = (Math.random() - Math.random()) * 0.01D) {
			xDistance = (Math.random() - Math.random()) * 0.01D;
		}

		double distance = Math.sqrt(xDistance * xDistance + zDistance * zDistance);
		double y = currentVelocity.getY() / 2.0D;
		double x = currentVelocity.getX() / 2.0D;
		double z = currentVelocity.getZ() / 2.0D;
		x -= xDistance / distance * 0.4D;
		y += 0.4D;
		z -= zDistance / distance * 0.4D;
		if (y >= 0.4D) {
			y = 0.4D;
		}

		return new Vector(x, y, z);
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	private void onReelIn(PlayerFishEvent e) {
		if (e.getState() == State.CAUGHT_ENTITY) {
			String cancelDraggingIn = this.module().getString("cancelDraggingIn");
			boolean isPlayer = e.getCaught() instanceof HumanEntity;
			if (cancelDraggingIn.equals("players") && isPlayer || cancelDraggingIn.equals("mobs") && !isPlayer
					|| cancelDraggingIn.equals("all")) {
				((Entity) this.hookEntityFeature.apply(e)).remove();
				e.setCancelled(true);
			}

		}
	}

	private EntityDamageEvent makeEvent(Player rodder, Entity entity, double damage) {
		return (EntityDamageEvent) (this.module().getBoolean("useEntityDamageEvent")
				? new EntityDamageEvent(entity, DamageCause.PROJECTILE,
						new EnumMap(ImmutableMap.of(DamageModifier.BASE, damage)),
						new EnumMap(ImmutableMap.of(DamageModifier.BASE, Functions.constant(damage))))
				: new EntityDamageByEntityEvent(rodder, entity, DamageCause.PROJECTILE,
						new EnumMap(ImmutableMap.of(DamageModifier.BASE, damage)),
						new EnumMap(ImmutableMap.of(DamageModifier.BASE, Functions.constant(damage)))));
	}
}