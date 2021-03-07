package kernitus.plugin.OldCombatMechanics.utilities.teams;

public enum TeamAction {
	CREATE(0), DISBAND(1), UPDATE(2), ADD_PLAYER(3), REMOVE_PLAYER(4);

	private final int minecraftId;

	private TeamAction(int minecraftId) {
		this.minecraftId = minecraftId;
	}

	public int getMinecraftId() {
		return this.minecraftId;
	}

	public static TeamAction fromId(int id) {
		TeamAction[] var1 = values();
		int var2 = var1.length;

		for (int var3 = 0; var3 < var2; ++var3) {
			TeamAction rule = var1[var3];
			if (rule.getMinecraftId() == id) {
				return rule;
			}
		}

		return null;
	}
}