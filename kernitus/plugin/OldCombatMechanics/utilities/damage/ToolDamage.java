package kernitus.plugin.OldCombatMechanics.utilities.damage;

import org.bukkit.Material;

public enum ToolDamage {
	WOOD_SWORD(4.0F), WOOD_SPADE(2.5F), WOOD_PICKAXE(2.0F), WOOD_AXE(7.0F), WOOD_HOE(1.0F), STONE_SWORD(
			5.0F), STONE_SPADE(3.5F), STONE_PICKAXE(3.0F), STONE_AXE(9.0F), STONE_HOE(1.0F), IRON_SWORD(
					6.0F), IRON_SPADE(4.5F), IRON_PICKAXE(4.0F), IRON_AXE(9.0F), IRON_HOE(1.0F), GOLD_SWORD(
							4.0F), GOLD_SPADE(2.5F), GOLD_PICKAXE(2.0F), GOLD_AXE(7.0F), GOLD_HOE(
									1.0F), DIAMOND_SWORD(7.0F), DIAMOND_SPADE(
											5.5F), DIAMOND_PICKAXE(5.0F), DIAMOND_AXE(9.0F), DIAMOND_HOE(1.0F);

	private float damage;

	private ToolDamage(float damage) {
		this.damage = damage;
	}

	public static float getDamage(String mat) {
		return valueOf(mat).damage;
	}

	public static float getDamage(Material mat) {
		return getDamage(mat.toString());
	}

	public float getDamage() {
		return this.damage;
	}
}