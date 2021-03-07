package kernitus.plugin.OldCombatMechanics.utilities.reflection.sweep;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import kernitus.plugin.OldCombatMechanics.utilities.packet.Packet;
import kernitus.plugin.OldCombatMechanics.utilities.reflection.Reflector;
import kernitus.plugin.OldCombatMechanics.utilities.reflection.type.ClassType;

class SweepPacketDetectorFrom1_13 extends AbstractSweepPacketDetector {
	private Field particleParamField;
	private Method particleParamNameMethod;

	SweepPacketDetectorFrom1_13() {
		Field[] var1 = PACKET_CLASS.getDeclaredFields();
		int var2 = var1.length;

		int var3;
		for (var3 = 0; var3 < var2; ++var3) {
			Field field = var1[var3];
			if (field.getType().getSimpleName().equals("ParticleParam")) {
				this.particleParamField = field;
				this.particleParamField.setAccessible(true);
			}
		}

		if (this.particleParamField == null) {
			this.throwNewElementNotFoundException("Particle param field");
		}

		Class<?> particleParamClass = Reflector.getClass(ClassType.NMS, "ParticleParam");
		if (particleParamClass == null) {
			this.throwNewElementNotFoundException("ParticleParam class");
		}

		Method[] var7 = particleParamClass.getMethods();
		var3 = var7.length;

		for (int var8 = 0; var8 < var3; ++var8) {
			Method method = var7[var8];
			if (method.getReturnType() == String.class) {
				this.particleParamNameMethod = method;
			}
		}

		if (this.particleParamNameMethod == null) {
			this.throwNewElementNotFoundException("Particle param description method");
		}

	}

	public boolean isSweepPacket(Packet packet) {
		if (this.isWrongPacketType(packet)) {
			return false;
		} else {
			Object nmsPacket = packet.getNMSPacket();
			Object particleParam = this.tryGetField(this.particleParamField, nmsPacket);
			String minecraftName = (String) Reflector.invokeMethod(this.particleParamNameMethod, particleParam,
					new Object[0]);
			return minecraftName.contains("sweep");
		}
	}
}