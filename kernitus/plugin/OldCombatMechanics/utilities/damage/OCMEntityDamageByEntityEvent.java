package kernitus.plugin.OldCombatMechanics.utilities.damage;

import java.util.function.Function;
import kernitus.plugin.OldCombatMechanics.utilities.Messenger;
import kernitus.plugin.OldCombatMechanics.utilities.potions.PotionEffects;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class OCMEntityDamageByEntityEvent extends Event implements Cancellable {
	private boolean cancelled;
	private static final HandlerList handlers = new HandlerList();
	private Entity damager;
	private Entity damagee;
	private DamageCause cause;
	private double rawDamage;
	private ItemStack weapon;
	private int sharpnessLevel;
	private int strengthLevel;
	private double baseDamage = 0.0D;
	private double mobEnchantmentsDamage = 0.0D;
	private double sharpnessDamage = 0.0D;
	private double criticalMultiplier = 1.0D;
	private double strengthModifier = 0.0D;
	private double weaknessModifier = 0.0D;
	private boolean isStrengthModifierMultiplier = false;
	private boolean isStrengthModifierAddend = true;
	private boolean isWeaknessModifierMultiplier = false;

	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

	public OCMEntityDamageByEntityEvent(Entity damager, Entity damagee, DamageCause cause, double rawDamage) {
		this.damager = damager;
		this.damagee = damagee;
		this.cause = cause;
		this.rawDamage = rawDamage;
		if (!(damager instanceof LivingEntity)) {
			this.setCancelled(true);
		} else {
			LivingEntity le = (LivingEntity) damager;
			EntityEquipment equipment = le.getEquipment();
			this.weapon = equipment.getItemInMainHand();
			if (this.weapon == null) {
				this.weapon = new ItemStack(Material.AIR);
			}

			EntityType entity = damagee.getType();
			Messenger.debug(le, "Raw damage: " + rawDamage, new Object[0]);
			this.mobEnchantmentsDamage = MobDamage.applyEntityBasedDamage(entity, this.weapon, rawDamage) - rawDamage;
			this.sharpnessLevel = this.weapon.getEnchantmentLevel(Enchantment.DAMAGE_ALL);
			this.sharpnessDamage = DamageUtils.getNewSharpnessDamage(this.sharpnessLevel);
			Messenger.debug(le, "Mob: " + this.mobEnchantmentsDamage + " Sharpness: " + this.sharpnessDamage,
					new Object[0]);
			double tempDamage = rawDamage - this.mobEnchantmentsDamage - this.sharpnessDamage;
			Messenger.debug(le, "No ench damage: " + tempDamage, new Object[0]);
			if (le instanceof Player) {
				Player player = (Player) le;
				if (DamageUtils.isCriticalHit(player)) {
					this.criticalMultiplier = 1.5D;
					tempDamage /= 1.5D;
					Messenger.debug(player, "Critical hit detected", new Object[0]);
				}
			}

			int amplifier = (Integer) PotionEffects.get(le, PotionEffectType.INCREASE_DAMAGE)
					.map(PotionEffect::getAmplifier).orElse(-1);
			++amplifier;
			this.strengthLevel = amplifier;
			this.strengthModifier = (double) (this.strengthLevel * 3);
			Messenger.debug(le, "Strength Modifier: " + this.strengthModifier, new Object[0]);
			if (le.hasPotionEffect(PotionEffectType.WEAKNESS)) {
				this.weaknessModifier = -4.0D;
			}

			Messenger.debug(le, "Weakness Modifier: " + this.weaknessModifier, new Object[0]);
			this.baseDamage = tempDamage + this.weaknessModifier - this.strengthModifier;
			Messenger.debug(le, "Base tool damage: " + this.baseDamage, new Object[0]);
		}
	}

	public Entity getDamager() {
		return this.damager;
	}

	public Entity getDamagee() {
		return this.damagee;
	}

	public DamageCause getCause() {
		return this.cause;
	}

	public double getRawDamage() {
		return this.rawDamage;
	}

	public ItemStack getWeapon() {
		return this.weapon;
	}

	public int getSharpnessLevel() {
		return this.sharpnessLevel;
	}

	public double getStrengthModifier() {
		return this.strengthModifier;
	}

	public void setStrengthModifier(double strengthModifier) {
		this.strengthModifier = strengthModifier;
	}

	public int getStrengthLevel() {
		return this.strengthLevel;
	}

	public double getWeaknessModifier() {
		return this.weaknessModifier;
	}

	public void setWeaknessModifier(double weaknessModifier) {
		this.weaknessModifier = weaknessModifier;
	}

	public boolean isStrengthModifierMultiplier() {
		return this.isStrengthModifierMultiplier;
	}

	public void setIsStrengthModifierMultiplier(boolean isStrengthModifierMultiplier) {
		this.isStrengthModifierMultiplier = isStrengthModifierMultiplier;
	}

	public void setIsStrengthModifierAddend(boolean isStrengthModifierAddend) {
		this.isStrengthModifierAddend = isStrengthModifierAddend;
	}

	public boolean isWeaknessModifierMultiplier() {
		return this.isWeaknessModifierMultiplier;
	}

	public void setIsWeaknessModifierMultiplier(boolean weaknessModifierMultiplier) {
		this.isWeaknessModifierMultiplier = weaknessModifierMultiplier;
	}

	public boolean isStrengthModifierAddend() {
		return this.isStrengthModifierAddend;
	}

	public double getBaseDamage() {
		return this.baseDamage;
	}

	public void setBaseDamage(double baseDamage) {
		this.baseDamage = baseDamage;
	}

	public double getMobEnchantmentsDamage() {
		return this.mobEnchantmentsDamage;
	}

	public void setMobEnchantmentsDamage(double mobEnchantmentsDamage) {
		this.mobEnchantmentsDamage = mobEnchantmentsDamage;
	}

	public double getSharpnessDamage() {
		return this.sharpnessDamage;
	}

	public void setSharpnessDamage(double sharpnessDamage) {
		this.sharpnessDamage = sharpnessDamage;
	}

	public double getCriticalMultiplier() {
		return this.criticalMultiplier;
	}

	public void setCriticalMultiplier(double criticalMultiplier) {
		this.criticalMultiplier = criticalMultiplier;
	}

	public boolean isCancelled() {
		return this.cancelled;
	}

	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}
}