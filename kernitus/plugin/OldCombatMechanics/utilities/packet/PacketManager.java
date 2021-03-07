package kernitus.plugin.OldCombatMechanics.utilities.packet;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;
import kernitus.plugin.OldCombatMechanics.OCMMain;
import kernitus.plugin.OldCombatMechanics.utilities.Messenger;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

public class PacketManager implements Listener {
	private static PacketManager instance;
	private final Map<UUID, PacketInjector> injectorMap = new HashMap();

	private PacketManager(Plugin plugin) {
		Bukkit.getPluginManager().registerEvents(this, plugin);
		OCMMain.getInstance().addDisableListener(() -> {
			this.removeAll();
			instance = null;
		});
	}

	public static synchronized PacketManager getInstance() {
		if (instance == null) {
			instance = new PacketManager(OCMMain.getInstance());
		}

		return instance;
	}

	public void addListener(PacketListener listener, Player player) {
		Objects.requireNonNull(listener, "listener can not be null");
		Objects.requireNonNull(player, "player can not be null");
		Map var3 = this.injectorMap;
		synchronized (this.injectorMap) {
			if (this.injectorMap.containsKey(player.getUniqueId())) {
				((PacketInjector) this.injectorMap.get(player.getUniqueId())).addPacketListener(listener);
			} else {
				try {
					PacketInjector injector = new PacketInjector(player);
					injector.addPacketListener(listener);
					this.injectorMap.put(player.getUniqueId(), injector);
				} catch (Exception var6) {
					Messenger.debug("Error attaching packet listener!", var6);
				}
			}

		}
	}

	public void removeListener(PacketListener listener, Player player) {
		Objects.requireNonNull(listener, "listener can not be null");
		Objects.requireNonNull(player, "player can not be null");
		Map var3 = this.injectorMap;
		synchronized (this.injectorMap) {
			if (this.injectorMap.containsKey(player.getUniqueId())) {
				PacketInjector injector = (PacketInjector) this.injectorMap.get(player.getUniqueId());
				injector.removePacketListener(listener);
				if (injector.getListenerAmount() < 1) {
					injector.detach();
					this.injectorMap.remove(player.getUniqueId());
				}

			}
		}
	}

	public void removeAllListeners(UUID uuid) {
		Objects.requireNonNull(uuid, "uuid can not be null");
		Map var2 = this.injectorMap;
		synchronized (this.injectorMap) {
			if (this.injectorMap.containsKey(uuid)) {
				((PacketInjector) this.injectorMap.get(uuid)).detach();
				this.injectorMap.remove(uuid);
			}

		}
	}

	private void removeAll() {
		Map var1 = this.injectorMap;
		synchronized (this.injectorMap) {
			Set<UUID> keys = new HashSet(this.injectorMap.keySet());
			keys.forEach(this::removeAllListeners);
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onLeave(PlayerQuitEvent event) {
		this.removeAllListeners(event.getPlayer().getUniqueId());
	}
}