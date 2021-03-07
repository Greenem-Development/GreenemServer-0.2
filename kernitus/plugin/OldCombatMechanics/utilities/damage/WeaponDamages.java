package kernitus.plugin.OldCombatMechanics.utilities.damage;

import java.util.Map;
import kernitus.plugin.OldCombatMechanics.OCMMain;
import kernitus.plugin.OldCombatMechanics.utilities.ConfigUtils;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;

public class WeaponDamages {
	private static Map<String, Double> damages;
	private static OCMMain plugin;

	public static void initialise(OCMMain plugin) {
		plugin = plugin;
		reload();
	}

	private static void reload() {
		ConfigurationSection section = plugin.getConfig().getConfigurationSection("old-tool-damage.damages");
		damages = ConfigUtils.loadDoubleMap(section);
	}

	public static double getDamage(Material mat) {
		String name = mat.name().replace("GOLDEN", "GOLD").replace("WOODEN", "WOOD").replace("SHOVEL", "SPADE");
		return (Double) damages.getOrDefault(name, -1.0D);
	}
}