package kernitus.plugin.OldCombatMechanics.module;

import java.util.Iterator;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.Map.Entry;
import kernitus.plugin.OldCombatMechanics.OCMMain;
import kernitus.plugin.OldCombatMechanics.module.ModulePlayerCollisions.1;
import kernitus.plugin.OldCombatMechanics.module.ModulePlayerCollisions.CollisionPacketListener;
import kernitus.plugin.OldCombatMechanics.utilities.packet.PacketManager;
import kernitus.plugin.OldCombatMechanics.utilities.teams.CollisionRule;
import kernitus.plugin.OldCombatMechanics.utilities.teams.TeamAction;
import kernitus.plugin.OldCombatMechanics.utilities.teams.TeamPacket;
import kernitus.plugin.OldCombatMechanics.utilities.teams.TeamUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class ModulePlayerCollisions extends Module {
	private final CollisionPacketListener collisionPacketListener = new CollisionPacketListener(this, (1)null);
	private final Map<Player, TeamPacket> playerTeamMap = new WeakHashMap();

	public ModulePlayerCollisions(OCMMain plugin) {
		super(plugin, "disable-player-collisions");
		OCMMain.getInstance().addDisableListener(() -> {
			Map var1 = this.playerTeamMap;
			synchronized (this.playerTeamMap) {
				Iterator var2 = this.playerTeamMap.entrySet().iterator();

				while (var2.hasNext()) {
					Entry<Player, TeamPacket> entry = (Entry) var2.next();
					if (TeamUtils.isOcmTeam(((TeamPacket) entry.getValue()).getTeamName())) {
						TeamUtils.disband(((TeamPacket) entry.getValue()).getTeamName(), (Player) entry.getKey());
					}
				}

			}
		});
		OCMMain.getInstance().addEnableListener(() -> {
			Iterator var1 = Bukkit.getOnlinePlayers().iterator();

			while (var1.hasNext()) {
				Player player = (Player) var1.next();
				PacketManager.getInstance().addListener(this.collisionPacketListener, player);
			}

		});
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onPlayerLogin(PlayerJoinEvent e) {
		PacketManager.getInstance().addListener(this.collisionPacketListener, e.getPlayer());
		this.createOrUpdateTeam(e.getPlayer());
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerChangeWorld(PlayerChangedWorldEvent e) {
		this.createOrUpdateTeam(e.getPlayer());
	}

	private void createOrUpdateTeam(Player player) {
		CollisionRule collisionRule = this.isEnabled(player.getWorld()) ? CollisionRule.NEVER : CollisionRule.ALWAYS;
		Map var3 = this.playerTeamMap;
		synchronized (this.playerTeamMap) {
			if (this.playerTeamMap.containsKey(player)) {
				TeamPacket teamPacket = (TeamPacket) this.playerTeamMap.get(player);
				teamPacket.setTeamAction(TeamAction.UPDATE);
				teamPacket.setCollisionRule(collisionRule);
				teamPacket.send(player);
			} else {
				this.debug("Fake collision team created for you.", player);
				this.createAndSendNewTeam(player, collisionRule);
			}

		}
	}

	private void createAndSendNewTeam(Player player, CollisionRule collisionRule) {
		Map var3 = this.playerTeamMap;
		synchronized (this.playerTeamMap) {
			TeamPacket newTeamPacket = TeamUtils.craftTeamCreatePacket(player, collisionRule);
			this.playerTeamMap.put(player, newTeamPacket);
			newTeamPacket.send(player);
		}
	}

	public void reload() {
		Map var1 = this.playerTeamMap;
		synchronized (this.playerTeamMap) {
			Iterator var2 = Bukkit.getOnlinePlayers().iterator();

			while (var2.hasNext()) {
				Player player = (Player) var2.next();
				this.createOrUpdateTeam(player);
			}

		}
	}
}