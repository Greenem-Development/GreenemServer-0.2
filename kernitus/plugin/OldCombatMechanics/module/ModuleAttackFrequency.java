package kernitus.plugin.OldCombatMechanics.module;

import java.util.function.Consumer;
import kernitus.plugin.OldCombatMechanics.OCMMain;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityTeleportEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class ModuleAttackFrequency extends Module {
	private static final int DEFAULT_DELAY = 20;
	private static int playerDelay;
	private static int mobDelay;

	public ModuleAttackFrequency(OCMMain plugin) {
		super(plugin, "attack-frequency");
		this.module().getInt("delay");
	}

	public void reload() {
		playerDelay = this.module().getInt("playerDelay");
		mobDelay = this.module().getInt("mobDelay");
		Bukkit.getWorlds().forEach((world) -> {
			world.getLivingEntities().forEach((livingEntity) -> {
				int toApply = livingEntity instanceof HumanEntity ? playerDelay : mobDelay;
				livingEntity.setMaximumNoDamageTicks(this.isEnabled(world) ? toApply : 20);
			});
		});
	}

	@EventHandler
	public void onPlayerLogin(PlayerJoinEvent e) {
		Player player = e.getPlayer();
		World world = player.getWorld();
		if (this.isEnabled(world)) {
			this.setDelay(player, playerDelay);
		}

	}

	@EventHandler
	public void onPlayerLogout(PlayerQuitEvent e) {
		this.setDelay(e.getPlayer(), 20);
	}

	@EventHandler
	public void onPlayerChangeWorld(PlayerChangedWorldEvent e) {
		Player player = e.getPlayer();
		World world = player.getWorld();
		this.setDelay(player, this.isEnabled(world) ? playerDelay : 20);
	}

	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent e) {
		Player player = e.getPlayer();
		World world = player.getWorld();
		this.setDelay(player, this.isEnabled(world) ? playerDelay : 20);
	}

	private void setDelay(Player player, int delay) {
		player.setMaximumNoDamageTicks(delay);
		this.debug("Set hit delay to " + delay, player);
	}

	@EventHandler
	public void onCreatureSpawn(CreatureSpawnEvent e) {
		LivingEntity livingEntity = e.getEntity();
		World world = livingEntity.getWorld();
		if (this.isEnabled(world)) {
			livingEntity.setMaximumNoDamageTicks(mobDelay);
		}

	}

	@EventHandler
	public void onEntityTeleportEvent(EntityTeleportEvent e) {
		Entity entity = e.getEntity();
		if (entity instanceof LivingEntity) {
			LivingEntity livingEntity = (LivingEntity) entity;
			World fromWorld = e.getFrom().getWorld();
			World toWorld = e.getTo().getWorld();
			if (fromWorld.getUID() != toWorld.getUID()) {
				livingEntity.setMaximumNoDamageTicks(this.isEnabled(toWorld) ? mobDelay : 20);
			}

		}
	}
}