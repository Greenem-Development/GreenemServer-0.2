package kernitus.plugin.OldCombatMechanics.module;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kernitus.plugin.OldCombatMechanics.OCMMain;
import kernitus.plugin.OldCombatMechanics.module.ModuleSwordSweep.1;
import kernitus.plugin.OldCombatMechanics.module.ModuleSwordSweep.ParticleListener;
import kernitus.plugin.OldCombatMechanics.utilities.damage.ToolDamage;
import kernitus.plugin.OldCombatMechanics.utilities.packet.PacketManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class ModuleSwordSweep extends Module {
	private BukkitRunnable task;
	private List<Location> sweepLocations = new ArrayList();
	private DamageCause sweepDamageCause;
	private ParticleListener particleListener = new ParticleListener(this);

	public ModuleSwordSweep(OCMMain plugin) {
		super(plugin, "disable-sword-sweep");

		try {
			this.sweepDamageCause = DamageCause.valueOf("ENTITY_SWEEP_ATTACK");
		} catch (IllegalArgumentException var3) {
			this.sweepDamageCause = null;
		}

		OCMMain.getInstance().addEnableListener(() -> {
			Iterator var1 = Bukkit.getOnlinePlayers().iterator();

			while (var1.hasNext()) {
				Player player = (Player) var1.next();
				PacketManager.getInstance().addListener(this.particleListener, player);
			}

		});
	}

	public void reload() {
      if (this.sweepDamageCause == null) {
         if (this.task != null) {
            this.task.cancel();
         }

         this.task = new 1(this);
         this.task.runTaskTimer(this.plugin, 0L, 1L);
      }
   }

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onPlayerLogin(PlayerJoinEvent e) {
		PacketManager.getInstance().addListener(this.particleListener, e.getPlayer());
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onEntityDamaged(EntityDamageByEntityEvent e) {
		Entity damager = e.getDamager();
		World world = damager.getWorld();
		if (this.isEnabled(world)) {
			if (damager instanceof Player) {
				if (this.sweepDamageCause != null) {
					if (e.getCause() == this.sweepDamageCause) {
						e.setCancelled(true);
						this.debug("Sweep cancelled", damager);
					}

				} else {
					Player attacker = (Player) e.getDamager();
					ItemStack weapon = attacker.getInventory().getItemInMainHand();
					if (this.isHoldingSword(weapon.getType())) {
						this.onSwordAttack(e, attacker, weapon);
					}

				}
			}
		}
	}

	private void onSwordAttack(EntityDamageByEntityEvent e, Player attacker, ItemStack weapon) {
		Location attackerLocation = attacker.getLocation();
		int level = 0;

		try {
			level = weapon.getEnchantmentLevel(Enchantment.SWEEPING_EDGE);
		} catch (NoSuchFieldError var7) {
			;
		}

		float damage = ToolDamage.getDamage(weapon.getType()) * (float) level / (float) (level + 1) + 1.0F;
		if (e.getDamage() == (double) damage) {
			if (this.sweepLocations.contains(attackerLocation)) {
				this.debug("Cancelling sweep...", attacker);
				e.setCancelled(true);
			}
		} else {
			this.sweepLocations.add(attackerLocation);
		}

	}

	private boolean isHoldingSword(Material mat) {
		return mat.toString().endsWith("_SWORD");
	}
}