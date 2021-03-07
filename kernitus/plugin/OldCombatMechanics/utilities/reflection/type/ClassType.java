package kernitus.plugin.OldCombatMechanics.utilities.reflection.type;

public enum ClassType {
	NMS("net.minecraft.server"), CRAFTBUKKIT("org.bukkit.craftbukkit");

	private String pkg;

	private ClassType(String pkg) {
		this.pkg = pkg;
	}

	public String getPackage() {
		return this.pkg;
	}
}