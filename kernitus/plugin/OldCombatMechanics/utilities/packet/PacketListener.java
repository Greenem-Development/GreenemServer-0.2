package kernitus.plugin.OldCombatMechanics.utilities.packet;

public interface PacketListener {
	void onPacketReceived(PacketEvent var1);

	void onPacketSend(PacketEvent var1);
}