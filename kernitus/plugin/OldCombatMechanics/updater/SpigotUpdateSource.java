package kernitus.plugin.OldCombatMechanics.updater;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.bukkit.ChatColor;

public class SpigotUpdateSource implements UpdateSource {
	private SpigetUpdateChecker updater = new SpigetUpdateChecker();

	public List<String> getUpdateMessages() {
		return this.updater.isUpdateAvailable()
				? Arrays.asList(
						ChatColor.BLUE + "An update for OldCombatMechanics to version "
								+ this.updater.getLatestVersion() + " is available!",
						ChatColor.BLUE + "Click here to download it: " + ChatColor.GRAY + this.updater.getUpdateURL())
				: Collections.emptyList();
	}
}