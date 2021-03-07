package kernitus.plugin.OldCombatMechanics.utilities.reflection.type;

public enum PacketType {
	PlayOut("PlayOut"), PlayIn("PlayIn");

	public String prefix;

	private PacketType(String prefix) {
		this.prefix = prefix;
	}
}