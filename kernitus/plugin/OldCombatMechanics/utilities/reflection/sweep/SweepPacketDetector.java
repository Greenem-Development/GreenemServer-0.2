package kernitus.plugin.OldCombatMechanics.utilities.reflection.sweep;

import kernitus.plugin.OldCombatMechanics.utilities.packet.Packet;
import kernitus.plugin.OldCombatMechanics.utilities.reflection.Reflector;

public interface SweepPacketDetector {
	boolean isSweepPacket(Packet var1);

	static SweepPacketDetector getInstance() {
		return (SweepPacketDetector) (Reflector.versionIsNewerOrEqualAs(1, 13, 0)
				? new SweepPacketDetectorFrom1_13()
				: new SweepPacketDetectorUpTo1_12());
	}
}