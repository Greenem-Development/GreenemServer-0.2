package kernitus.plugin.OldCombatMechanics.module;

import java.util.function.Consumer;
import kernitus.plugin.OldCombatMechanics.OCMMain;
import kernitus.plugin.OldCombatMechanics.module.ModuleAttackCooldown.PVPMode;
import kernitus.plugin.OldCombatMechanics.utilities.Config;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ModuleAttackCooldown extends Module {
	public ModuleAttackCooldown(OCMMain plugin) {
		super(plugin, "disable-attack-cooldown");
	}

	public void reload() {
		Bukkit.getOnlinePlayers().forEach(this::adjustAttackSpeed);
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerLogin(PlayerJoinEvent e) {
		this.adjustAttackSpeed(e.getPlayer());
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onWorldChange(PlayerChangedWorldEvent e) {
		this.adjustAttackSpeed(e.getPlayer());
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerQuit(PlayerQuitEvent e) {
		Player player = e.getPlayer();
		this.setAttackSpeed(player, PVPMode.NEW_PVP.getBaseAttackSpeed());
	}

	private void adjustAttackSpeed(Player player) {
		World world = player.getWorld();
		double attackSpeed = Config.moduleEnabled("disable-attack-cooldown", world)
				? this.module().getDouble("generic-attack-speed")
				: PVPMode.NEW_PVP.getBaseAttackSpeed();
		this.setAttackSpeed(player, attackSpeed);
	}

	private void setAttackSpeed(Player player, double attackSpeed) {
		AttributeInstance attribute = player.getAttribute(Attribute.GENERIC_ATTACK_SPEED);
		if (attribute != null) {
			double baseValue = attribute.getBaseValue();
			if (baseValue != attackSpeed) {
				attribute.setBaseValue(attackSpeed);
				player.saveData();
			}

		}
	}
}