package kernitus.plugin.OldCombatMechanics.utilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import kernitus.plugin.OldCombatMechanics.utilities.potions.GenericPotionDurations;
import kernitus.plugin.OldCombatMechanics.utilities.potions.PotionDurations;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.potion.PotionType;

public class ConfigUtils {
	public static Map<String, Double> loadDoubleMap(ConfigurationSection section) {
		Objects.requireNonNull(section, "section cannot be null!");
		Stream var10000 = section.getKeys(false).stream();
		Predicate var10001 = section::isDouble;
		section.getClass();
		var10000 = var10000.filter(var10001.or(section::isInt));
		Function var1 = (key) -> {
			return key;
		};
		section.getClass();
		return (Map) var10000.collect(Collectors.toMap(var1, section::getDouble));
	}

	public static List<Material> loadMaterialList(ConfigurationSection section, String key) {
		Objects.requireNonNull(section, "section cannot be null!");
		Objects.requireNonNull(key, "key cannot be null!");
		return (List) (!section.isList(key)
				? new ArrayList()
				: (List) section.getStringList(key).stream().map(Material::matchMaterial).filter(Objects::nonNull)
						.collect(Collectors.toList()));
	}

	public static HashMap<PotionType, PotionDurations> loadPotionDurationsList(ConfigurationSection section) {
		Objects.requireNonNull(section, "section cannot be null!");
		HashMap<PotionType, PotionDurations> durationsHashMap = new HashMap();
		ConfigurationSection durationsSection = section.getConfigurationSection("potion-durations");
		Iterator var3 = durationsSection.getKeys(false).iterator();

		while (var3.hasNext()) {
			String potionName = (String) var3.next();
			ConfigurationSection potionSection = durationsSection.getConfigurationSection(potionName);
			ConfigurationSection drinkable = potionSection.getConfigurationSection("drinkable");
			ConfigurationSection splash = potionSection.getConfigurationSection("splash");
			potionName = potionName.toUpperCase(Locale.ROOT);

			try {
				PotionType potionType = PotionType.valueOf(potionName);
				durationsHashMap.put(potionType,
						new PotionDurations(getGenericDurations(drinkable), getGenericDurations(splash)));
			} catch (IllegalArgumentException var9) {
				Messenger.debug("Skipping loading " + potionName + " potion", new Object[0]);
			}
		}

		return durationsHashMap;
	}

	private static GenericPotionDurations getGenericDurations(ConfigurationSection section) {
		return new GenericPotionDurations(section.getInt("base"), section.getInt("II"), section.getInt("extended"));
	}
}