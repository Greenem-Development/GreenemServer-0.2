package kernitus.plugin.OldCombatMechanics.utilities.teams;

import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicInteger;
import kernitus.plugin.OldCombatMechanics.utilities.teams.TeamUtils.VersionData;
import org.bukkit.entity.Player;

public class TeamUtils {
	private static final VersionData VERSION_DATA = new VersionData();
	private static final AtomicInteger TEAM_NAME_COUNTER = new AtomicInteger();

	public static void setCollisionRule(Object packet, CollisionRule collisionRule) {
		try {
			VERSION_DATA.getFieldCollisionRule().set(packet, collisionRule.getName());
		} catch (IllegalAccessException var3) {
			throw new RuntimeException("Error setting collision field", var3);
		}
	}

	public static TeamAction getPacketAction(Object packet) {
		try {
			return TeamAction.fromId((Integer) VERSION_DATA.getFieldAction().get(packet));
		} catch (IllegalAccessException var2) {
			throw new RuntimeException("Error getting action field", var2);
		}
	}

	public static void setPacketAction(Object packet, TeamAction action) {
		try {
			VERSION_DATA.getFieldAction().set(packet, action.getMinecraftId());
		} catch (IllegalAccessException var3) {
			throw new RuntimeException("Error setting action field", var3);
		}
	}

	public static boolean targetsPlayer(Object packet, Player player) {
		return getTeamMembers(packet).contains(player.getName());
	}

	public static TeamPacket craftTeamCreatePacket(Player player, CollisionRule collisionRule) {
		String teamName = "OCM-" + TEAM_NAME_COUNTER.getAndIncrement() + "";
		Object teamPacket = VERSION_DATA.createTeamPacket(TeamAction.CREATE, collisionRule, teamName,
				Collections.singletonList(player));
		return new TeamPacket(teamPacket);
	}

	public static boolean isOcmTeam(String name) {
		return name.startsWith("OCM-");
	}

	public static void disband(String teamName, Player player) {
		Object nms = VERSION_DATA.createTeamPacket(TeamAction.DISBAND, CollisionRule.NEVER, teamName,
				Collections.singletonList(player));
		(new TeamPacket(nms)).send(player);
	}

	public static String getTeamName(Object packet) {
		try {
			return (String) VERSION_DATA.getFieldName().get(packet);
		} catch (IllegalAccessException var2) {
			throw new RuntimeException("Error setting members", var2);
		}
	}

	public static Collection<String> getTeamMembers(Object packet) {
		try {
			Collection<String> identifiers = (Collection) VERSION_DATA.getFieldPlayerNames().get(packet);
			return (Collection) (identifiers == null ? Collections.emptyList() : identifiers);
		} catch (IllegalAccessException var2) {
			throw new RuntimeException("Error getting players", var2);
		}
	}

	static Object createTeamNMSPacket(TeamAction action, CollisionRule collisionRule, String name,
			Collection<Player> players) {
		return VERSION_DATA.createTeamPacket(action, collisionRule, name, players);
	}
}