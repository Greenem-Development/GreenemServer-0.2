package kernitus.plugin.OldCombatMechanics.utilities.teams;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.Optional;
import kernitus.plugin.OldCombatMechanics.utilities.reflection.Reflector.Packets;
import kernitus.plugin.OldCombatMechanics.utilities.teams.TeamPacket.1;
import org.bukkit.entity.Player;

public class TeamPacket {
	private final Object packetShallowClone;

	public TeamPacket(Object packet) {
		this.packetShallowClone = Objects.requireNonNull(packet, "packet can not be null!");
	}

	public TeamPacket() {
		this(TeamUtils.createTeamNMSPacket(TeamAction.CREATE, CollisionRule.NEVER, "placeholder",
				Collections.emptyList()));
	}

	public Optional<TeamPacket> adjustToUpdate(Object updatePacket, Player player) {
      switch(1.$SwitchMap$kernitus$plugin$OldCombatMechanics$utilities$teams$TeamAction[TeamUtils.getPacketAction(updatePacket).ordinal()]) {
      case 1:
         Collection<String> removedPlayers = TeamUtils.getTeamMembers(updatePacket);
         if (removedPlayers.contains(player.getName())) {
            return Optional.empty();
         }

         return Optional.of(new TeamPacket(updatePacket));
      case 2:
      case 3:
      case 4:
         return Optional.of(new TeamPacket(updatePacket));
      case 5:
         return Optional.empty();
      default:
         throw new IllegalArgumentException("Unknown team action");
      }
   }

	public void setCollisionRule(CollisionRule collisionRule) {
		TeamUtils.setCollisionRule(this.packetShallowClone, collisionRule);
	}

	public void setTeamAction(TeamAction teamAction) {
		TeamUtils.setPacketAction(this.packetShallowClone, teamAction);
	}

	public String getTeamName() {
		return this.packetShallowClone == null ? null : TeamUtils.getTeamName(this.packetShallowClone);
	}

	public void send(Player player) {
		Packets.sendPacket(player, this.packetShallowClone);
	}
}