package kernitus.plugin.OldCombatMechanics.utilities.packet;

import java.lang.reflect.InvocationTargetException;
import java.util.Objects;
import kernitus.plugin.OldCombatMechanics.utilities.reflection.Reflector;
import kernitus.plugin.OldCombatMechanics.utilities.reflection.type.ClassType;
import org.bukkit.entity.Player;

public class Packet {
	private static final Class<?> NMS_PACKET_CLASS;
	private Class<?> packetClass;
	private Object rawPacket;

	private Packet(Class<?> packetClass)
			throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
		this(packetClass.getConstructor().newInstance());
	}

	private Packet(Object packet) {
		this.rawPacket = packet;
		this.packetClass = packet.getClass();
	}

	public static Packet create(String name) {
		String packetName = name;
		if (!name.startsWith("Packet")) {
			packetName = "Packet" + name;
		}

		Class<?> packetClass = Reflector.getClass(ClassType.NMS, packetName);
		Objects.requireNonNull(packetClass, "packetClass can not be null!");

		try {
			return new Packet(packetClass);
		} catch (IllegalAccessException | InvocationTargetException | InstantiationException
				| NoSuchMethodException var5) {
			String message = "Failed to create packet!"
					+ String.format("Name: %s, Replaced, %s Class: %s", name, packetName, packetClass);
			throw new RuntimeException(message, var5);
		}
	}

	public static Packet createFromNMSPacket(Object nmsPacket) {
		Objects.requireNonNull(nmsPacket, "nmsPacket can not be null");
		if (NMS_PACKET_CLASS == null) {
			throw new IllegalStateException("Could not find packet class! Therefore this class is broken.");
		} else if (!isNmsPacket(nmsPacket)) {
			throw new IllegalArgumentException("You must pass a 'Packet' object!");
		} else {
			try {
				return new Packet(nmsPacket);
			} catch (Exception var2) {
				throw new RuntimeException("Failed to create packet!", var2);
			}
		}
	}

	public static boolean isNmsPacket(Object nmsPacket) {
		return Reflector.inheritsFrom(nmsPacket.getClass(), NMS_PACKET_CLASS);
	}

	public Object getNMSPacket() {
		return this.rawPacket;
	}

	public void send(Player... players) {
		Player[] var2 = players;
		int var3 = players.length;

		for (int var4 = 0; var4 < var3; ++var4) {
			Player player = var2[var4];
			PacketSender.getInstance().sendPacket(this, player);
		}

	}

	public Class<?> getPacketClass() {
		return this.packetClass;
	}

	static {
		NMS_PACKET_CLASS = Reflector.getClass(ClassType.NMS, "Packet");
	}
}