package kernitus.plugin.OldCombatMechanics.module;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.OptionalInt;
import java.util.UUID;
import java.util.function.IntConsumer;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;
import kernitus.plugin.OldCombatMechanics.OCMMain;
import kernitus.plugin.OldCombatMechanics.module.ModuleGoldenApple.1;
import kernitus.plugin.OldCombatMechanics.module.ModuleGoldenApple.Cooldown;
import kernitus.plugin.OldCombatMechanics.module.ModuleGoldenApple.LastEaten;
import kernitus.plugin.OldCombatMechanics.utilities.Messenger;
import kernitus.plugin.OldCombatMechanics.versions.materials.MaterialRegistry;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Statistic;
import org.bukkit.World;
import org.bukkit.advancement.Advancement;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerStatisticIncrementEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ModuleGoldenApple extends Module {
	private List<PotionEffect> enchantedGoldenAppleEffects;
	private List<PotionEffect> goldenAppleEffects;
	private ShapedRecipe enchantedAppleRecipe;
	private Map<UUID, LastEaten> lastEaten;
	private Cooldown cooldown;

	public ModuleGoldenApple(OCMMain plugin) {
		super(plugin, "old-golden-apples");
	}

	public void reload() {
		this.cooldown = new Cooldown(this.module().getLong("cooldown.normal"),
				this.module().getLong("cooldown.enchanted"), this.module().getBoolean("cooldown.is-shared"));
		this.lastEaten = new HashMap();
		this.enchantedGoldenAppleEffects = this.getPotionEffects("napple");
		this.goldenAppleEffects = this.getPotionEffects("gapple");

		try {
			this.enchantedAppleRecipe = new ShapedRecipe(new NamespacedKey(this.plugin, "MINECRAFT"),
					MaterialRegistry.ENCHANTED_GOLDEN_APPLE.newInstance());
		} catch (NoClassDefFoundError var2) {
			this.enchantedAppleRecipe = new ShapedRecipe(MaterialRegistry.ENCHANTED_GOLDEN_APPLE.newInstance());
		}

		this.enchantedAppleRecipe.shape(new String[]{"ggg", "gag", "ggg"}).setIngredient('g', Material.GOLD_BLOCK)
				.setIngredient('a', Material.APPLE);
		this.registerCrafting();
	}

	private void registerCrafting() {
		if (this.isEnabled() && this.module().getBoolean("enchanted-golden-apple-crafting")) {
			if (Bukkit.getRecipesFor(MaterialRegistry.ENCHANTED_GOLDEN_APPLE.newInstance()).size() > 0) {
				return;
			}

			Bukkit.addRecipe(this.enchantedAppleRecipe);
			Messenger.debug("Added napple recipe", new Object[0]);
		}

	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onPrepareItemCraft(PrepareItemCraftEvent e) {
		ItemStack item = e.getInventory().getResult();
		if (item != null) {
			if (MaterialRegistry.ENCHANTED_GOLDEN_APPLE.isSame(item)) {
				World world = e.getView().getPlayer().getWorld();
				if (this.isSettingEnabled("no-conflict-mode")) {
					return;
				}

				if (!this.isEnabled(world)) {
					e.getInventory().setResult((ItemStack) null);
				} else if (this.isEnabled(world) && !this.isSettingEnabled("enchanted-golden-apple-crafting")) {
					e.getInventory().setResult((ItemStack) null);
				}
			}

		}
	}

	@EventHandler(
      priority = EventPriority.HIGH
   )
   public void onItemConsume(PlayerItemConsumeEvent e) {
      if (!e.isCancelled()) {
         Player p = e.getPlayer();
         if (this.isEnabled(p.getWorld()) && this.isSettingEnabled("old-potion-effects")) {
            ItemStack item = e.getItem();
            Material consumedMaterial = item.getType();
            if (consumedMaterial == Material.GOLDEN_APPLE || MaterialRegistry.ENCHANTED_GOLDEN_APPLE.isSame(e.getItem())) {
               e.setCancelled(true);
               this.lastEaten.putIfAbsent(p.getUniqueId(), new LastEaten((1)null));
               if (!this.cooldown.isOnCooldown(item, (LastEaten)this.lastEaten.get(p.getUniqueId()))) {
                  LastEaten.access$100((LastEaten)this.lastEaten.get(p.getUniqueId()), item);
                  ItemStack originalItem = e.getItem();
                  PlayerInventory inv = p.getInventory();
                  int foodLevel = Math.min(p.getFoodLevel() + 4, 20);
                  p.setFoodLevel(foodLevel);
                  if (p.getGameMode() != GameMode.CREATIVE) {
                     item.setAmount(item.getAmount() - 1);
                  }

                  float saturation = p.getSaturation() + 9.6F;
                  if (saturation > (float)foodLevel) {
                     saturation = (float)foodLevel;
                  }

                  p.setSaturation(saturation);
                  if (MaterialRegistry.ENCHANTED_GOLDEN_APPLE.isSame(item)) {
                     this.applyEffects(p, this.enchantedGoldenAppleEffects);
                  } else {
                     this.applyEffects(p, this.goldenAppleEffects);
                  }

                  if (item.getAmount() <= 0) {
                     item = null;
                  }

                  ItemStack mainHand = inv.getItemInMainHand();
                  ItemStack offHand = inv.getItemInOffHand();
                  if (mainHand.equals(originalItem)) {
                     inv.setItemInMainHand(item);
                  } else if (offHand.equals(originalItem)) {
                     inv.setItemInOffHand(item);
                  } else if (mainHand.getType() == Material.GOLDEN_APPLE || MaterialRegistry.ENCHANTED_GOLDEN_APPLE.isSame(mainHand)) {
                     inv.setItemInMainHand(item);
                  }

                  int initialValue = p.getStatistic(Statistic.USE_ITEM, consumedMaterial);
                  p.incrementStatistic(Statistic.USE_ITEM, consumedMaterial);
                  PlayerStatisticIncrementEvent psie = new PlayerStatisticIncrementEvent(p, Statistic.USE_ITEM, initialValue, initialValue + 1, consumedMaterial);
                  Bukkit.getServer().getPluginManager().callEvent(psie);

                  try {
                     NamespacedKey nsk = NamespacedKey.minecraft("husbandry/balanced_diet");
                     Advancement advancement = Bukkit.getAdvancement(nsk);
                     if (advancement != null) {
                        p.getAdvancementProgress(advancement).awardCriteria(consumedMaterial.name().toLowerCase());
                     }
                  } catch (NoClassDefFoundError var15) {
                     ;
                  }

               }
            }
         }
      }
   }

	private List<PotionEffect> getPotionEffects(String apple) {
		List<PotionEffect> appleEffects = new ArrayList();
		ConfigurationSection sect = this.module().getConfigurationSection(apple + "-effects");
		Iterator var4 = sect.getKeys(false).iterator();

		while (var4.hasNext()) {
			String key = (String) var4.next();
			int duration = sect.getInt(key + ".duration");
			int amplifier = sect.getInt(key + ".amplifier");
			PotionEffectType type = PotionEffectType.getByName(key);
			Objects.requireNonNull(type, String.format("Invalid potion effect type '%s'!", key));
			PotionEffect fx = new PotionEffect(type, duration, amplifier);
			appleEffects.add(fx);
		}

		return appleEffects;
	}

	private void applyEffects(LivingEntity target, List<PotionEffect> effects) {
		Iterator var3 = effects.iterator();

		while (var3.hasNext()) {
			PotionEffect effect = (PotionEffect) var3.next();
			OptionalInt maxActiveAmplifier = target.getActivePotionEffects().stream().filter((potionEffect) -> {
				return potionEffect.getType() == effect.getType();
			}).mapToInt(PotionEffect::getAmplifier).max();
			if (maxActiveAmplifier.orElse(-1) <= effect.getAmplifier()) {
				maxActiveAmplifier.ifPresent((ignored) -> {
					target.removePotionEffect(effect.getType());
				});
				target.addPotionEffect(effect);
			}
		}

	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e) {
		if (this.lastEaten != null) {
			this.lastEaten.remove(e.getPlayer().getUniqueId());
		}

	}
}