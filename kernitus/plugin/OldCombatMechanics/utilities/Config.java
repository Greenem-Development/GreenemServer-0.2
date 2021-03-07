package kernitus.plugin.OldCombatMechanics.utilities;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.logging.Level;
import kernitus.plugin.OldCombatMechanics.ModuleLoader;
import kernitus.plugin.OldCombatMechanics.OCMMain;
import kernitus.plugin.OldCombatMechanics.module.Module;
import kernitus.plugin.OldCombatMechanics.utilities.damage.EntityDamageByEntityListener;
import kernitus.plugin.OldCombatMechanics.utilities.damage.WeaponDamages;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class Config {
	private static OCMMain plugin;
	private static FileConfiguration config;
	private static List<Material> interactive = new ArrayList();

	public static void initialise(OCMMain plugin) {
		plugin = plugin;
		config = plugin.getConfig();
		reload();
	}

	private static boolean checkConfigVersion() {
		YamlConfiguration defaultConfig = YamlConfiguration
				.loadConfiguration(new InputStreamReader(plugin.getResource("config.yml")));
		if (config.getInt("config-version") != defaultConfig.getInt("config-version")) {
			plugin.getLogger().warning("Config version does not match, backing up old config and creating a new one");
			plugin.upgradeConfig();
			reload();
			return true;
		} else {
			return false;
		}
	}

	public static void reload() {
		if (plugin.doesConfigExist()) {
			plugin.reloadConfig();
			config = plugin.getConfig();
		} else {
			plugin.upgradeConfig();
		}

		if (!checkConfigVersion()) {
			Messenger.DEBUG_ENABLED = config.getBoolean("debug.enabled");
			WeaponDamages.initialise(plugin);
			reloadInteractiveBlocks();
			EntityDamageByEntityListener.getINSTANCE()
					.setEnabled(moduleEnabled("old-tool-damage") || moduleEnabled("old-potion-effects"));
			ModuleLoader.toggleModules();
			ModuleLoader.getModules().stream().filter(Module::isEnabled).forEach((module) -> {
				try {
					module.reload();
				} catch (Exception var2) {
					plugin.getLogger().log(Level.WARNING, "Error reloading module '" + module.toString() + "'", var2);
				}

			});
		}
	}

	public static boolean moduleEnabled(String name, World world) {
		boolean isBlacklist = config.getBoolean("worlds-is-blacklist");
		ConfigurationSection section = config.getConfigurationSection(name);
		if (section == null) {
			plugin.getLogger().warning("Tried to check module '" + name + "', but it didn't exist!");
			return false;
		} else if (!section.getBoolean("enabled")) {
			return false;
		} else if (world == null) {
			return true;
		} else {
			String worldName = world.getName();
			List<String> list = section.getStringList("worlds");
			if (list.size() <= 0) {
				return true;
			} else {
				boolean isInList = list.stream().anyMatch((entry) -> {
					return entry.equalsIgnoreCase(worldName);
				});
				return isBlacklist != isInList;
			}
		}
	}

	public static boolean moduleEnabled(String name) {
		return moduleEnabled(name, (World) null);
	}

	public static boolean debugEnabled() {
		return moduleEnabled("debug", (World) null);
	}

	public static List<?> getWorlds(String moduleName) {
		return config.getList(moduleName + ".worlds");
	}

	public static boolean moduleSettingEnabled(String moduleName, String moduleSettingName) {
		return config.getBoolean(moduleName + "." + moduleSettingName);
	}

	public static void setModuleSetting(String moduleName, String moduleSettingName, boolean value) {
		config.set(moduleName + "." + moduleSettingName, value);
		plugin.saveConfig();
	}

	private static void reloadInteractiveBlocks() {
		interactive = ConfigUtils.loadMaterialList(config, "interactive");
	}

	public static List<Material> getInteractiveBlocks() {
		return interactive;
	}

	public static FileConfiguration getConfig() {
		return plugin.getConfig();
	}
}