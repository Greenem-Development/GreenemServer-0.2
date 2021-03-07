package kernitus.plugin.OldCombatMechanics.utilities.reflection.sweep;

import java.lang.reflect.Field;
import java.util.Locale;
import kernitus.plugin.OldCombatMechanics.utilities.packet.Packet;

class SweepPacketDetectorUpTo1_12 extends AbstractSweepPacketDetector {
	private Field enumParticleField;

	SweepPacketDetectorUpTo1_12() {
		Field[] var1 = PACKET_CLASS.getDeclaredFields();
		int var2 = var1.length;

		for (int var3 = 0; var3 < var2; ++var3) {
			Field field = var1[var3];
			if (field.getType().getSimpleName().equals("EnumParticle")) {
				this.enumParticleField = field;
				this.enumParticleField.setAccessible(true);
			}
		}

		if (this.enumParticleField == null) {
			this.throwNewElementNotFoundException("EnumParticle field");
		}

	}

	public boolean isSweepPacket(Packet packet) {
		if (this.isWrongPacketType(packet)) {
			return false;
		} else {
			Object nmsPacket = packet.getNMSPacket();
			Enum<?> enumParticle = (Enum) this.tryGetField(this.enumParticleField, nmsPacket);
			return enumParticle.name().toUpperCase(Locale.ROOT).contains("SWEEP");
		}
	}
}