package kernitus.plugin.OldCombatMechanics.module;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import kernitus.plugin.OldCombatMechanics.OCMMain;
import kernitus.plugin.OldCombatMechanics.module.ModuleOldArmourDurability.1;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class ModuleOldArmourDurability extends Module {
	private final Map<UUID, List<ItemStack>> explosionDamaged = new HashMap();

	public ModuleOldArmourDurability(OCMMain plugin) {
		super(plugin, "old-armour-durability");
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onItemDamage(PlayerItemDamageEvent e) {
		Player player = e.getPlayer();
		if (this.isEnabled(player.getWorld())) {
			ItemStack item = e.getItem();
			Material itemType = item.getType();
			if (!Arrays.stream(player.getInventory().getArmorContents()).noneMatch((armourPiece) -> {
				return armourPiece != null && armourPiece.getType() == itemType
						&& armourPiece.getType() != Material.ELYTRA;
			})) {
				UUID uuid = player.getUniqueId();
				if (this.explosionDamaged.containsKey(uuid)) {
					List<ItemStack> armour = (List) this.explosionDamaged.get(uuid);
					List<ItemStack> matchedPieces = (List) armour.stream().filter((piece) -> {
						return piece.equals(item);
					}).collect(Collectors.toList());
					armour.removeAll(matchedPieces);
					this.debug("Item matched explosion, ignoring...", player);
					if (!matchedPieces.isEmpty()) {
						return;
					}
				}

				int reduction = this.module().getInt("reduction");
				int damageChance = 60 + 40 / (item.getEnchantmentLevel(Enchantment.DURABILITY) + 1);
				Random random = new Random();
				int randomInt = random.nextInt(100);
				if (randomInt >= damageChance) {
					reduction = 0;
				}

				this.debug("Item damaged: " + itemType + " Damage: " + e.getDamage() + " Changed to: " + reduction,
						player);
				e.setDamage(reduction);
			}
		}
	}

	@EventHandler(
      priority = EventPriority.MONITOR
   )
   public void onPlayerExplosionDamage(EntityDamageEvent e) {
      if (!e.isCancelled()) {
         if (e.getEntityType() == EntityType.PLAYER) {
            DamageCause cause = e.getCause();
            if (cause == DamageCause.BLOCK_EXPLOSION || cause == DamageCause.ENTITY_EXPLOSION) {
               Player player = (Player)e.getEntity();
               UUID uuid = player.getUniqueId();
               List<ItemStack> armour = (List)Arrays.stream(player.getInventory().getArmorContents()).filter(Objects::nonNull).collect(Collectors.toList());
               this.explosionDamaged.put(uuid, armour);
               BukkitRunnable runnable = new 1(this, uuid, player);
               runnable.runTaskLaterAsynchronously(this.plugin, 1L);
               this.debug("Detected explosion!", player);
            }
         }
      }
   }
}