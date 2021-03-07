package kernitus.plugin.OldCombatMechanics.utilities.packet;

import kernitus.plugin.OldCombatMechanics.utilities.packet.PacketEvent.ConnectionDirection;
import org.bukkit.entity.Player;

public class PacketEvent {
	private Packet packet;
	private Player player;
	private boolean cancelled;
	private ConnectionDirection direction;

	protected PacketEvent(Object packet, boolean cancelled, ConnectionDirection direction, Player player) {
		this.packet = Packet.createFromNMSPacket(packet);
		this.cancelled = cancelled;
		this.direction = direction;
		this.player = player;
	}

	protected PacketEvent(Object packet, ConnectionDirection direction, Player player) {
		this(packet, false, direction, player);
	}

	public Packet getPacket() {
		return this.packet;
	}

	public void setPacket(Packet packet) {
		this.packet = packet;
	}

	public boolean isCancelled() {
		return this.cancelled;
	}

	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}

	public ConnectionDirection getDirection() {
		return this.direction;
	}

	public Player getPlayer() {
		return this.player;
	}
}