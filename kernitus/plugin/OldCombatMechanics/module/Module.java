package kernitus.plugin.OldCombatMechanics.module;

import kernitus.plugin.OldCombatMechanics.OCMMain;
import kernitus.plugin.OldCombatMechanics.utilities.Config;
import kernitus.plugin.OldCombatMechanics.utilities.Messenger;
import org.apache.commons.lang.WordUtils;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.Listener;

public abstract class Module implements Listener {
	protected OCMMain plugin;
	private final String configName;
	private final String moduleName;

	protected Module(OCMMain plugin, String configName) {
		this.plugin = plugin;
		this.configName = configName;
		this.moduleName = this.getClass().getSimpleName();
	}

	public boolean isEnabled(World world) {
		return Config.moduleEnabled(this.configName, world);
	}

	public boolean isEnabled() {
		return this.isEnabled((World) null);
	}

	public boolean isSettingEnabled(String name) {
		return this.plugin.getConfig().getBoolean(this.configName + "." + name, false);
	}

	public ConfigurationSection module() {
		return this.plugin.getConfig().getConfigurationSection(this.configName);
	}

	public void reload() {
	}

	protected void debug(String text) {
		Messenger.debug("[" + this.moduleName + "] " + text, new Object[0]);
	}

	protected void debug(String text, CommandSender sender) {
		if (Config.debugEnabled()) {
			Messenger.send(sender, "&8&l[&fDEBUG&8&l][&f" + this.moduleName + "&8&l]&7 " + text, new Object[0]);
		}

	}

	public String toString() {
		return WordUtils.capitalizeFully(this.configName.replaceAll("-", " "));
	}
}