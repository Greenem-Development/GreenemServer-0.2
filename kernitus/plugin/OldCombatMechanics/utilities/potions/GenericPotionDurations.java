package kernitus.plugin.OldCombatMechanics.utilities.potions;

public class GenericPotionDurations {
	private final int base;
	private final int II;
	private final int extended;

	public GenericPotionDurations(int base, int II, int extended) {
		this.base = base;
		this.II = II;
		this.extended = extended;
	}

	public int getBaseTime() {
		return this.base;
	}

	public int getIITime() {
		return this.II;
	}

	public int getExtendedTime() {
		return this.extended;
	}
}