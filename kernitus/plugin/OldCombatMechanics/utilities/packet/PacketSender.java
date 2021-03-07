package kernitus.plugin.OldCombatMechanics.utilities.packet;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Objects;
import kernitus.plugin.OldCombatMechanics.utilities.reflection.Reflector;
import kernitus.plugin.OldCombatMechanics.utilities.reflection.type.ClassType;
import org.bukkit.entity.Player;

class PacketSender {
	private static final Class<?> CRAFT_PLAYER;
	private static final Class<?> PLAYER_CONNECTION;
	private static final Class<?> ENTITY_PLAYER;
	private static final Method GET_HANDLE;
	private static final Method SEND_PACKET;
	private static final Field PLAYER_CONNECTION_FIELD;
	private static final PacketSender instance;

	static PacketSender getInstance() {
		return instance;
	}

	void sendPacket(Packet packet, Player player) {
		this.sendPacket(packet.getNMSPacket(), this.getConnection(player));
	}

	private void sendPacket(Object nmsPacket, Object playerConnection) {
		Reflector.invokeMethod(SEND_PACKET, playerConnection, new Object[]{nmsPacket});
	}

	Object getConnection(Player player) {
		Object handle = Reflector.invokeMethod(GET_HANDLE, player, new Object[0]);
		return Reflector.getFieldValue(PLAYER_CONNECTION_FIELD, handle);
	}

	static {
		CRAFT_PLAYER = (Class) Objects.requireNonNull(Reflector.getClass(ClassType.CRAFTBUKKIT, "entity.CraftPlayer"));
		PLAYER_CONNECTION = (Class) Objects.requireNonNull(Reflector.getClass(ClassType.NMS, "PlayerConnection"));
		ENTITY_PLAYER = (Class) Objects.requireNonNull(Reflector.getClass(ClassType.NMS, "EntityPlayer"));
		GET_HANDLE = Reflector.getMethod(CRAFT_PLAYER, "getHandle");
		SEND_PACKET = Reflector.getMethod(PLAYER_CONNECTION, "sendPacket");
		PLAYER_CONNECTION_FIELD = Reflector.getField(ENTITY_PLAYER, "playerConnection");
		instance = new PacketSender();
	}
}