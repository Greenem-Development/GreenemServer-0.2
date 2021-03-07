package kernitus.plugin.OldCombatMechanics.module;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import kernitus.plugin.OldCombatMechanics.OCMMain;
import kernitus.plugin.OldCombatMechanics.utilities.ConfigUtils;
import kernitus.plugin.OldCombatMechanics.utilities.damage.OCMEntityDamageByEntityEvent;
import kernitus.plugin.OldCombatMechanics.utilities.potions.GenericPotionDurations;
import kernitus.plugin.OldCombatMechanics.utilities.potions.PotionDurations;
import kernitus.plugin.OldCombatMechanics.utilities.potions.PotionEffects;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

public class ModuleOldPotionEffects extends Module {
	private static final Set<PotionType> EXCLUDED_POTION_TYPES;
	private HashMap<PotionType, PotionDurations> durations;

	public ModuleOldPotionEffects(OCMMain plugin) {
		super(plugin, "old-potion-effects");

		try {
			EXCLUDED_POTION_TYPES.add(PotionType.TURTLE_MASTER);
		} catch (NoSuchFieldError var3) {
			this.debug("Skipping excluding a potion (probably older server version)");
		}

		this.reload();
	}

	public void reload() {
		this.durations = ConfigUtils.loadPotionDurationsList(this.module());
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
	public void onPlayerDrinksPotion(PlayerItemConsumeEvent event) {
		Player player = event.getPlayer();
		if (this.isEnabled(player.getWorld())) {
			ItemStack potionItem = event.getItem();
			if (potionItem.getType() == Material.POTION) {
				PotionMeta potionMeta = (PotionMeta) potionItem.getItemMeta();
				if (potionMeta != null) {
					PotionData potionData = potionMeta.getBasePotionData();
					PotionType potionType = potionData.getType();
					if (!EXCLUDED_POTION_TYPES.contains(potionType)) {
						event.setCancelled(true);
						int amplifier = potionData.isUpgraded() ? 1 : 0;
						int duration = this.getPotionDuration(potionData, false);
						PotionEffectType effectType = (PotionEffectType) Objects
								.requireNonNull(potionType.getEffectType());
						PotionEffect potionEffect = new PotionEffect(effectType, duration, amplifier);
						this.setNewPotionEffect(player, potionEffect);
						if (player.getGameMode() != GameMode.CREATIVE) {
							PlayerInventory playerInventory = player.getInventory();
							int amount = potionItem.getAmount();
							ItemStack toSet = new ItemStack(Material.GLASS_BOTTLE);
							boolean isInMainHand = potionItem.equals(playerInventory.getItemInMainHand());
							if (amount > 1) {
								playerInventory.addItem(new ItemStack[]{toSet});
								toSet = potionItem;
								potionItem.setAmount(amount - 1);
							}

							if (isInMainHand) {
								playerInventory.setItemInMainHand(toSet);
							} else {
								playerInventory.setItemInOffHand(toSet);
							}

						}
					}
				}
			}
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPotionSplash(PotionSplashEvent event) {
		if (this.isEnabled(event.getEntity().getWorld())) {
			ThrownPotion thrownPotion = event.getPotion();
			PotionMeta potionMeta = (PotionMeta) thrownPotion.getItem().getItemMeta();
			Iterator var4 = thrownPotion.getEffects().iterator();

			while (var4.hasNext()) {
				PotionEffect potionEffect = (PotionEffect) var4.next();
				PotionData potionData = potionMeta.getBasePotionData();
				if (EXCLUDED_POTION_TYPES.contains(potionData.getType())) {
					return;
				}

				event.setCancelled(true);
				int duration = this.getPotionDuration(potionData, true);
				PotionEffect newEffect = new PotionEffect(potionEffect.getType(), duration,
						potionEffect.getAmplifier());
				event.getAffectedEntities().forEach((livingEntity) -> {
					this.setNewPotionEffect(livingEntity, newEffect);
				});
			}

		}
	}

	@EventHandler(ignoreCancelled = true)
	public void onDamageByEntity(OCMEntityDamageByEntityEvent event) {
		Entity damager = event.getDamager();
		if (this.isEnabled(damager.getWorld())) {
			double weaknessModifier = event.getWeaknessModifier();
			double strengthModifier;
			if (weaknessModifier != 0.0D) {
				event.setIsWeaknessModifierMultiplier(this.module().getBoolean("weakness.multiplier"));
				strengthModifier = this.module().getDouble("weakness.modifier");
				event.setWeaknessModifier(strengthModifier);
				this.debug("Old weakness modifier: " + weaknessModifier + " New: " + strengthModifier, damager);
			}

			strengthModifier = event.getStrengthModifier();
			if (strengthModifier != 0.0D) {
				event.setIsStrengthModifierMultiplier(this.module().getBoolean("strength.multiplier"));
				event.setIsStrengthModifierAddend(this.module().getBoolean("strength.addend"));
				double newStrengthModifier = this.module().getDouble("strength.modifier");
				event.setStrengthModifier(newStrengthModifier);
				this.debug("Old strength modifier: " + strengthModifier + " New: " + newStrengthModifier, damager);
			}

		}
	}

	private void setNewPotionEffect(LivingEntity livingEntity, PotionEffect potionEffect) {
		if (!livingEntity.hasPotionEffect(potionEffect.getType())) {
			livingEntity.addPotionEffect(potionEffect, false);
		} else {
			PotionEffect activeEffect = PotionEffects.getOrNull(livingEntity, potionEffect.getType());
			int remainingDuration = activeEffect.getDuration();
			int newAmplifier = potionEffect.getAmplifier();
			int activeAmplifier = activeEffect.getAmplifier();
			if (newAmplifier >= activeAmplifier) {
				if (newAmplifier > activeAmplifier || remainingDuration < potionEffect.getDuration()) {
					livingEntity.addPotionEffect(potionEffect, true);
				}

			}
		}
	}

	private int getPotionDuration(PotionData potionData, boolean splash) {
		PotionType potionType = potionData.getType();
		this.debug("Potion type: " + potionType.name());
		GenericPotionDurations potionDurations = splash
				? ((PotionDurations) this.durations.get(potionType)).getSplash()
				: ((PotionDurations) this.durations.get(potionType)).getDrinkable();
		int duration;
		if (potionData.isExtended()) {
			duration = potionDurations.getExtendedTime();
		} else if (potionData.isUpgraded()) {
			duration = potionDurations.getIITime();
		} else {
			duration = potionDurations.getBaseTime();
		}

		return duration * 20;
	}

	static {
		EXCLUDED_POTION_TYPES = EnumSet.of(PotionType.INSTANT_DAMAGE, PotionType.INSTANT_HEAL, PotionType.AWKWARD,
				PotionType.MUNDANE, PotionType.THICK, PotionType.UNCRAFTABLE, PotionType.WATER);
	}
}