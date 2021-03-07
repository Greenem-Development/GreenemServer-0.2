package kernitus.plugin.OldCombatMechanics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import kernitus.plugin.OldCombatMechanics.hooks.PlaceholderAPIHook;
import kernitus.plugin.OldCombatMechanics.hooks.api.Hook;
import kernitus.plugin.OldCombatMechanics.lib.bstats.bukkit.Metrics;
import kernitus.plugin.OldCombatMechanics.lib.bstats.bukkit.Metrics.AdvancedPie;
import kernitus.plugin.OldCombatMechanics.module.Module;
import kernitus.plugin.OldCombatMechanics.module.ModuleAttackCooldown;
import kernitus.plugin.OldCombatMechanics.module.ModuleAttackFrequency;
import kernitus.plugin.OldCombatMechanics.module.ModuleAttackSounds;
import kernitus.plugin.OldCombatMechanics.module.ModuleChorusFruit;
import kernitus.plugin.OldCombatMechanics.module.ModuleDisableBowBoost;
import kernitus.plugin.OldCombatMechanics.module.ModuleDisableCrafting;
import kernitus.plugin.OldCombatMechanics.module.ModuleDisableElytra;
import kernitus.plugin.OldCombatMechanics.module.ModuleDisableEnderpearlCooldown;
import kernitus.plugin.OldCombatMechanics.module.ModuleDisableOffHand;
import kernitus.plugin.OldCombatMechanics.module.ModuleDisableProjectileRandomness;
import kernitus.plugin.OldCombatMechanics.module.ModuleFishingKnockback;
import kernitus.plugin.OldCombatMechanics.module.ModuleFishingRodVelocity;
import kernitus.plugin.OldCombatMechanics.module.ModuleGoldenApple;
import kernitus.plugin.OldCombatMechanics.module.ModuleNoLapisEnchantments;
import kernitus.plugin.OldCombatMechanics.module.ModuleOldArmourDurability;
import kernitus.plugin.OldCombatMechanics.module.ModuleOldArmourStrength;
import kernitus.plugin.OldCombatMechanics.module.ModuleOldBrewingStand;
import kernitus.plugin.OldCombatMechanics.module.ModuleOldBurnDelay;
import kernitus.plugin.OldCombatMechanics.module.ModuleOldPotionEffects;
import kernitus.plugin.OldCombatMechanics.module.ModuleOldToolDamage;
import kernitus.plugin.OldCombatMechanics.module.ModulePlayerCollisions;
import kernitus.plugin.OldCombatMechanics.module.ModulePlayerRegen;
import kernitus.plugin.OldCombatMechanics.module.ModuleProjectileKnockback;
import kernitus.plugin.OldCombatMechanics.module.ModuleShieldDamageReduction;
import kernitus.plugin.OldCombatMechanics.module.ModuleSwordBlocking;
import kernitus.plugin.OldCombatMechanics.module.ModuleSwordSweep;
import kernitus.plugin.OldCombatMechanics.updater.ModuleUpdateChecker;
import kernitus.plugin.OldCombatMechanics.utilities.Config;
import kernitus.plugin.OldCombatMechanics.utilities.Messenger;
import kernitus.plugin.OldCombatMechanics.utilities.damage.EntityDamageByEntityListener;
import org.bukkit.Bukkit;
import org.bukkit.event.EventException;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.RegisteredListener;
import org.bukkit.plugin.java.JavaPlugin;

public class OCMMain extends JavaPlugin {
	private static OCMMain INSTANCE;
	private Logger logger = this.getLogger();
	private OCMConfigHandler CH = new OCMConfigHandler(this);
	private List<Runnable> disableListeners = new ArrayList();
	private List<Runnable> enableListeners = new ArrayList();
	private List<Hook> hooks = new ArrayList();

	public static OCMMain getInstance() {
		return INSTANCE;
	}

	public static String getVersion() {
		return INSTANCE.getDescription().getVersion();
	}

	public void onEnable() {
		INSTANCE = this;
		PluginDescriptionFile pdfFile = this.getDescription();
		this.CH.setupConfig();
		ModuleLoader.initialise(this);
		this.registerModules();
		this.registerHooks();
		this.hooks.forEach((hook) -> {
			hook.init(this);
		});
		this.getCommand("OldCombatMechanics").setExecutor(new OCMCommandHandler(this, this.getFile()));
		Messenger.initialise(this);
		Config.initialise(this);
		Metrics metrics = new Metrics(this, 53);
		metrics.addCustomChart(new AdvancedPie("most_used_modules", () -> {
			return (Map) ModuleLoader.getModules().stream().filter(Module::isEnabled)
					.collect(Collectors.toMap(Module::toString, (module) -> {
						return 1;
					}));
		}));
		this.enableListeners.forEach(Runnable::run);
		List<RegisteredListener> joinListeners = (List) Arrays
				.stream(PlayerJoinEvent.getHandlerList().getRegisteredListeners()).filter((registeredListener) -> {
					return registeredListener.getPlugin().equals(this);
				}).collect(Collectors.toList());
		Bukkit.getOnlinePlayers().forEach((player) -> {
			PlayerJoinEvent event = new PlayerJoinEvent(player, "");
			joinListeners.forEach((registeredListener) -> {
				try {
					registeredListener.callEvent(event);
				} catch (EventException var3) {
					var3.printStackTrace();
				}

			});
		});
		this.logger.info(pdfFile.getName() + " v" + pdfFile.getVersion() + " has been enabled");
	}

	public void onDisable() {
		PluginDescriptionFile pdfFile = this.getDescription();
		this.disableListeners.forEach(Runnable::run);
		List<RegisteredListener> quitListeners = (List) Arrays
				.stream(PlayerQuitEvent.getHandlerList().getRegisteredListeners()).filter((registeredListener) -> {
					return registeredListener.getPlugin().equals(this);
				}).collect(Collectors.toList());
		Bukkit.getOnlinePlayers().forEach((player) -> {
			PlayerQuitEvent event = new PlayerQuitEvent(player, "");
			quitListeners.forEach((registeredListener) -> {
				try {
					registeredListener.callEvent(event);
				} catch (EventException var3) {
					var3.printStackTrace();
				}

			});
		});
		this.logger.info(pdfFile.getName() + " v" + pdfFile.getVersion() + " has been disabled");
	}

	private void registerModules() {
		ModuleLoader.addModule(new ModuleUpdateChecker(this, this.getFile()));
		ModuleLoader.addModule(new ModuleAttackCooldown(this));
		ModuleLoader.addModule(new ModulePlayerCollisions(this));
		ModuleLoader.addModule(new ModuleOldToolDamage(this));
		ModuleLoader.addModule(new ModuleSwordSweep(this));
		ModuleLoader.addModule(new ModuleOldPotionEffects(this));
		ModuleLoader.addModule(new EntityDamageByEntityListener(this));
		ModuleLoader.addModule(new ModuleShieldDamageReduction(this));
		ModuleLoader.addModule(new ModuleOldArmourStrength(this));
		ModuleLoader.addModule(new ModuleSwordBlocking(this));
		ModuleLoader.addModule(new ModuleOldArmourDurability(this));
		ModuleLoader.addModule(new ModuleGoldenApple(this));
		ModuleLoader.addModule(new ModuleFishingKnockback(this));
		ModuleLoader.addModule(new ModulePlayerRegen(this));
		ModuleLoader.addModule(new ModuleDisableCrafting(this));
		ModuleLoader.addModule(new ModuleDisableOffHand(this));
		ModuleLoader.addModule(new ModuleOldBrewingStand(this));
		ModuleLoader.addModule(new ModuleDisableElytra(this));
		ModuleLoader.addModule(new ModuleDisableProjectileRandomness(this));
		ModuleLoader.addModule(new ModuleDisableBowBoost(this));
		ModuleLoader.addModule(new ModuleProjectileKnockback(this));
		ModuleLoader.addModule(new ModuleNoLapisEnchantments(this));
		ModuleLoader.addModule(new ModuleDisableEnderpearlCooldown(this));
		ModuleLoader.addModule(new ModuleChorusFruit(this));
		ModuleLoader.addModule(new ModuleAttackSounds(this));
		ModuleLoader.addModule(new ModuleOldBurnDelay(this));
		ModuleLoader.addModule(new ModuleAttackFrequency(this));
		ModuleLoader.addModule(new ModuleFishingRodVelocity(this));
	}

	private void registerHooks() {
		if (this.getServer().getPluginManager().isPluginEnabled("PlaceholderAPI")) {
			this.hooks.add(new PlaceholderAPIHook());
		}

	}

	public void upgradeConfig() {
		this.CH.upgradeConfig();
	}

	public boolean doesConfigExist() {
		return this.CH.doesConfigExist();
	}

	public void addDisableListener(Runnable action) {
		this.disableListeners.add(action);
	}

	public void addEnableListener(Runnable action) {
		this.enableListeners.add(action);
	}
}