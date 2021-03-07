package kernitus.plugin.OldCombatMechanics.utilities;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

public class EventRegistry {
	private final Plugin plugin;
	private final List<Listener> listeners = new ArrayList();

	public EventRegistry(Plugin plugin) {
		this.plugin = plugin;
	}

	public boolean registerListener(Listener listener) {
		if (this.listeners.contains(listener)) {
			return false;
		} else {
			this.listeners.add(listener);
			this.plugin.getServer().getPluginManager().registerEvents(listener, this.plugin);
			return true;
		}
	}

	public boolean unregisterListener(Listener listener) {
		if (!this.listeners.contains(listener)) {
			return false;
		} else {
			this.listeners.remove(listener);
			HandlerList.unregisterAll(listener);
			return true;
		}
	}
}