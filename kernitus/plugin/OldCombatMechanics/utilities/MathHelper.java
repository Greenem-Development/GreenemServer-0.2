package kernitus.plugin.OldCombatMechanics.utilities;

public class MathHelper {
	public static double clamp(double value, double min, double max) {
		double realMin = Math.min(min, max);
		double realMax = Math.max(min, max);
		if (value < realMin) {
			value = realMin;
		}

		if (value > realMax) {
			value = realMax;
		}

		return value;
	}
}