package kernitus.plugin.OldCombatMechanics;

import java.io.File;
import java.util.Locale;
import kernitus.plugin.OldCombatMechanics.utilities.Config;
import kernitus.plugin.OldCombatMechanics.utilities.Messenger;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.jetbrains.annotations.NotNull;

public class OCMCommandHandler implements CommandExecutor {
	private static final String NO_PERMISSION = "&cYou need the permission '%s' to do that!";
	private final OCMMain plugin;
	private final File pluginFile;

	public OCMCommandHandler(OCMMain instance, File pluginFile) {
		this.plugin = instance;
		this.pluginFile = pluginFile;
	}

	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label,
			String[] args) {
		FileConfiguration config = this.plugin.getConfig();
		if (args.length < 1) {
			PluginDescriptionFile description = this.plugin.getDescription();
			Messenger.send(sender, ChatColor.DARK_GRAY + Messenger.HORIZONTAL_BAR, new Object[0]);
			Messenger.send(sender, "&6&lOldCombatMechanics&e by &ckernitus&e and &cRayzr522&e version &6%s",
					new Object[]{description.getVersion()});
			Messenger.send(sender, "&eYou can use &c/ocm reload&e to reload the config file", new Object[0]);
			if (sender instanceof Player && sender.hasPermission("oldcombatmechanics.toggle")
					&& config.getBoolean("enableIndividualToggle")) {
				Messenger.send(sender, "&eYou can use &c/ocm toggle&e to turn your attack cooldown on/off",
						new Object[0]);
			}

			Messenger.send(sender, ChatColor.DARK_GRAY + Messenger.HORIZONTAL_BAR, new Object[0]);
			Bukkit.getScheduler().runTaskAsynchronously(this.plugin, () -> {
				(new UpdateChecker(this.plugin, this.pluginFile)).sendUpdateMessages(sender);
			});
			return true;
		} else {
			String subcommand = args[0].toLowerCase(Locale.ROOT);
			if (subcommand.equals("reload")) {
				if (sender.hasPermission("oldcombatmechanics.reload")) {
					Config.reload();
					Messenger.send(sender, "&6&lOldCombatMechanics&e config file reloaded", new Object[0]);
				} else {
					Messenger.send(sender, "&cYou need the permission '%s' to do that!",
							new Object[]{"oldcombatmechanics.reload"});
				}

				return true;
			} else if (sender instanceof Player && config.getBoolean("enableIndividualToggle")
					&& subcommand.equals("toggle")) {
				if (!sender.hasPermission("oldcombatmechanics.toggle")) {
					Messenger.send(sender, "&cYou need the permission '%s' to do that!",
							new Object[]{"oldcombatmechanics.toggle"});
					return true;
				} else {
					Player player = (Player) sender;
					double speed = config.getDouble("disable-attack-cooldown.generic-attack-speed");
					AttributeInstance attribute = player.getAttribute(Attribute.GENERIC_ATTACK_SPEED);
					double baseValue = attribute.getBaseValue();
					String message = "&1[OCM] &aAttack cooldown ";
					if (baseValue == speed) {
						speed = 4.0D;
						message = message + "enabled";
					} else {
						message = message + "disabled";
					}

					attribute.setBaseValue(speed);
					player.saveData();
					Messenger.send(player, message, new Object[0]);
					return true;
				}
			} else {
				return false;
			}
		}
	}
}