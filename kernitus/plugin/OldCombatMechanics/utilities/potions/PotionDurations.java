package kernitus.plugin.OldCombatMechanics.utilities.potions;

public class PotionDurations {
	private final GenericPotionDurations drinkable;
	private final GenericPotionDurations splash;

	public PotionDurations(GenericPotionDurations drinkable, GenericPotionDurations splash) {
		this.drinkable = drinkable;
		this.splash = splash;
	}

	public PotionDurations(int drinkableBase, int drinkableII, int drinkableExtended, int splashBase, int splashII,
			int splashExtended) {
		this(new GenericPotionDurations(drinkableBase, drinkableII, drinkableExtended),
				new GenericPotionDurations(splashBase, splashII, splashExtended));
	}

	public GenericPotionDurations getDrinkable() {
		return this.drinkable;
	}

	public GenericPotionDurations getSplash() {
		return this.splash;
	}
}