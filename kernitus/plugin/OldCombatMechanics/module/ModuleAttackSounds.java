package kernitus.plugin.OldCombatMechanics.module;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import kernitus.plugin.OldCombatMechanics.OCMMain;
import kernitus.plugin.OldCombatMechanics.module.ModuleAttackSounds.1;
import kernitus.plugin.OldCombatMechanics.module.ModuleAttackSounds.SoundListener;
import kernitus.plugin.OldCombatMechanics.utilities.packet.PacketManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;

public class ModuleAttackSounds extends Module {
	private final SoundListener soundListener = new SoundListener(this, (1)null);
	private final Set<String> blockedSounds = new HashSet(this.getBlockedSounds());

	public ModuleAttackSounds(OCMMain plugin) {
		super(plugin, "disable-attack-sounds");
		OCMMain.getInstance().addEnableListener(() -> {
			Iterator var1 = Bukkit.getOnlinePlayers().iterator();

			while (var1.hasNext()) {
				Player player = (Player) var1.next();
				PacketManager.getInstance().addListener(this.soundListener, player);
			}

		});
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onPlayerLogin(PlayerJoinEvent e) {
		PacketManager.getInstance().addListener(this.soundListener, e.getPlayer());
	}

	public void reload() {
		this.blockedSounds.clear();
		this.blockedSounds.addAll(this.getBlockedSounds());
	}

	private Collection<String> getBlockedSounds() {
		return this.module().getStringList("blocked-sounds");
	}
}