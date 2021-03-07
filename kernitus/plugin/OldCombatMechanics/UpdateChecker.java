package kernitus.plugin.OldCombatMechanics;

import java.io.File;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;
import kernitus.plugin.OldCombatMechanics.updater.SpigotUpdateSource;
import kernitus.plugin.OldCombatMechanics.updater.UpdateSource;
import kernitus.plugin.OldCombatMechanics.utilities.Messenger;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class UpdateChecker {
	private UpdateSource updateSource = new SpigotUpdateSource();

	public UpdateChecker(OCMMain plugin, File pluginFile) {
	}

	public void sendUpdateMessages(CommandSender sender) {
		if (sender instanceof Player) {
			Player var10001 = (Player) sender;
			((Player) sender).getClass();
			this.sendUpdateMessages(var10001::sendMessage);
		} else {
			this.sendUpdateMessages((x$0) -> {
				Messenger.info(x$0, new Object[0]);
			});
		}

	}

	private void sendUpdateMessages(Consumer<String> target) {
		this.updateSource.getUpdateMessages().stream().filter(Objects::nonNull).filter((message) -> {
			return !message.isEmpty();
		}).forEach(target);
	}
}