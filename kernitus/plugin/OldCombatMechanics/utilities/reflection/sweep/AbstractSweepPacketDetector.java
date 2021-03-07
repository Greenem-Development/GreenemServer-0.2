package kernitus.plugin.OldCombatMechanics.utilities.reflection.sweep;

import java.lang.reflect.Field;
import java.util.logging.Level;
import kernitus.plugin.OldCombatMechanics.OCMMain;
import kernitus.plugin.OldCombatMechanics.utilities.packet.Packet;
import kernitus.plugin.OldCombatMechanics.utilities.reflection.Reflector.Packets;
import kernitus.plugin.OldCombatMechanics.utilities.reflection.type.PacketType;
import org.bukkit.Bukkit;

abstract class AbstractSweepPacketDetector implements SweepPacketDetector {
	static final Class<?> PACKET_CLASS;

	boolean isWrongPacketType(Packet packet) {
		return packet.getPacketClass() != PACKET_CLASS;
	}

	Object tryGetField(Field field, Object handle) {
		try {
			return field.get(handle);
		} catch (IllegalAccessException var4) {
			OCMMain.getInstance().getLogger().log(Level.INFO, "Error getting field " + field, var4);
			return null;
		}
	}

	void throwNewElementNotFoundException(String name) {
		throw new IllegalStateException("Couldn't find " + name
				+ ". Please report this on github. I am running server version " + Bukkit.getServer().getVersion());
	}

	static {
		PACKET_CLASS = Packets.getPacket(PacketType.PlayOut, "WorldParticles");
	}
}