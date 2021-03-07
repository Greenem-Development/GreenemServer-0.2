package kernitus.plugin.OldCombatMechanics.module;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;
import kernitus.plugin.OldCombatMechanics.OCMMain;
import kernitus.plugin.OldCombatMechanics.module.ModuleSwordBlocking.1;
import kernitus.plugin.OldCombatMechanics.module.ModuleSwordBlocking.2;
import kernitus.plugin.OldCombatMechanics.utilities.Config;
import kernitus.plugin.OldCombatMechanics.utilities.ConfigUtils;
import kernitus.plugin.OldCombatMechanics.utilities.RunnableSeries;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.scheduler.BukkitRunnable;

public class ModuleSwordBlocking extends Module {
	private static final ItemStack SHIELD;
	private final Map<UUID, ItemStack> storedOffhandItems = new HashMap();
	private final Map<UUID, RunnableSeries> correspondingTasks = new HashMap();
	private int restoreDelay;
	private boolean blacklist;
	private List<Material> noBlockingItems = new ArrayList();

	public ModuleSwordBlocking(OCMMain plugin) {
		super(plugin, "sword-blocking");
	}

	public void reload() {
		this.restoreDelay = this.module().getInt("restoreDelay", 40);
		this.blacklist = this.module().getBoolean("blacklist");
		this.noBlockingItems = ConfigUtils.loadMaterialList(this.module(), "noBlockingItems");
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onRightClick(PlayerInteractEvent e) {
		if (e.getItem() != null) {
			Action action = e.getAction();
			if (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
				Block block = e.getClickedBlock();
				if (action != Action.RIGHT_CLICK_BLOCK || block == null
						|| !Config.getInteractiveBlocks().contains(block.getType())) {
					Player p = e.getPlayer();
					World world = p.getWorld();
					if (this.isEnabled(world)) {
						UUID id = p.getUniqueId();
						if (!p.isBlocking()) {
							ItemStack item = e.getItem();
							if (!this.isHoldingSword(item.getType()) || this.hasShield(p)) {
								return;
							}

							PlayerInventory inv = p.getInventory();
							boolean isANoBlockingItem = this.noBlockingItems.contains(inv.getItemInOffHand().getType());
							if (this.blacklist && isANoBlockingItem || !this.blacklist && !isANoBlockingItem) {
								return;
							}

							this.storedOffhandItems.put(id, inv.getItemInOffHand());
							inv.setItemInOffHand(SHIELD);
						}

						this.scheduleRestore(p);
					}
				}
			}
		}
	}

	@EventHandler
	public void onHotBarChange(PlayerItemHeldEvent e) {
		this.restore(e.getPlayer());
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onWorldChange(PlayerChangedWorldEvent e) {
		this.restore(e.getPlayer());
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerLogout(PlayerQuitEvent e) {
		this.restore(e.getPlayer());
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerDeath(PlayerDeathEvent e) {
		if (this.isBlocking(e.getEntity().getUniqueId())) {
			Player p = e.getEntity();
			UUID id = p.getUniqueId();
			e.getDrops().replaceAll((item) -> {
				if (item.getType().equals(Material.SHIELD)) {
					item = (ItemStack) this.storedOffhandItems.remove(id);
				}

				return item;
			});
			this.restore(p);
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerSwapHandItems(PlayerSwapHandItemsEvent e) {
		Player p = e.getPlayer();
		if (this.isBlocking(p.getUniqueId())) {
			e.setCancelled(true);
		}

	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onInventoryClick(InventoryClickEvent e) {
		if (e.getWhoClicked() instanceof Player) {
			Player p = (Player) e.getWhoClicked();
			if (this.isBlocking(p.getUniqueId())) {
				ItemStack cursor = e.getCursor();
				ItemStack current = e.getCurrentItem();
				if (cursor != null && cursor.getType() == Material.SHIELD
						|| current != null && current.getType() == Material.SHIELD) {
					e.setCancelled(true);
					this.restore(p);
				}
			}
		}

	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onItemDrop(PlayerDropItemEvent e) {
		Item is = e.getItemDrop();
		Player p = e.getPlayer();
		if (this.isBlocking(p.getUniqueId()) && is.getItemStack().getType() == Material.SHIELD) {
			e.setCancelled(true);
			this.restore(p);
		}

	}

	private void restore(Player p) {
		UUID id = p.getUniqueId();
		this.tryCancelTask(id);
		if (this.isBlocking(id)) {
			if (p.isBlocking()) {
				this.scheduleRestore(p);
			} else {
				p.getInventory().setItemInOffHand((ItemStack) this.storedOffhandItems.get(id));
				this.storedOffhandItems.remove(id);
			}

		}
	}

	private void tryCancelTask(UUID id) {
		Optional.ofNullable(this.correspondingTasks.remove(id)).ifPresent(RunnableSeries::cancelAll);
	}

	private void scheduleRestore(Player p) {
      UUID id = p.getUniqueId();
      this.tryCancelTask(id);
      BukkitRunnable removeItem = new 1(this, p);
      removeItem.runTaskLater(this.plugin, (long)this.restoreDelay);
      BukkitRunnable checkBlocking = new 2(this, p);
      checkBlocking.runTaskTimer(this.plugin, 10L, 2L);
      this.correspondingTasks.put(p.getUniqueId(), new RunnableSeries(new BukkitRunnable[]{removeItem, checkBlocking}));
   }

	private boolean isBlocking(UUID uuid) {
		return this.storedOffhandItems.containsKey(uuid);
	}

	private boolean hasShield(Player p) {
		return p.getInventory().getItemInOffHand().getType() == Material.SHIELD;
	}

	private boolean isHoldingSword(Material mat) {
		return mat.toString().endsWith("_SWORD");
	}

	static {
		SHIELD = new ItemStack(Material.SHIELD);
	}
}