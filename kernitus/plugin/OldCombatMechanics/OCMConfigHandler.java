package kernitus.plugin.OldCombatMechanics;

import java.io.File;
import org.bukkit.configuration.file.YamlConfiguration;

public class OCMConfigHandler {
	private OCMMain plugin;

	public OCMConfigHandler(OCMMain instance) {
		this.plugin = instance;
	}

	public void upgradeConfig() {
		if (this.doesConfigExist()) {
			File backup = this.getFile("config-backup.yml");
			if (backup.exists()) {
				backup.delete();
			}

			File configFile = this.getFile("config.yml");
			configFile.renameTo(backup);
			this.plugin.saveResource("config.yml", false);
		}

		this.plugin.saveDefaultConfig();
	}

	public void setupConfig() {
		if (!this.getFile("config.yml").exists()) {
			this.setupConfig("config.yml");
		}

	}

	private void setupConfig(String fileName) {
		this.plugin.saveResource(fileName, false);
		this.plugin.getLogger().info("Config file " + fileName + " generated");
	}

	public YamlConfiguration getConfig(String fileName) {
		return YamlConfiguration.loadConfiguration(this.getFile(fileName));
	}

	public File getFile(String fileName) {
		return new File(this.plugin.getDataFolder(), fileName.replace('/', File.separatorChar));
	}

	public boolean doesConfigExist() {
		return this.getFile("config.yml").exists();
	}
}