package kernitus.plugin.OldCombatMechanics;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import kernitus.plugin.OldCombatMechanics.module.Module;
import kernitus.plugin.OldCombatMechanics.utilities.EventRegistry;
import kernitus.plugin.OldCombatMechanics.utilities.Messenger;

public class ModuleLoader {
	private static EventRegistry eventRegistry;
	private static List<Module> modules = new ArrayList();

	public static void initialise(OCMMain plugin) {
		eventRegistry = new EventRegistry(plugin);
	}

	public static void toggleModules() {
		modules.forEach((module) -> {
			setState(module, module.isEnabled());
		});
	}

	private static void setState(Module module, boolean state) {
		if (state) {
			if (eventRegistry.registerListener(module)) {
				Messenger.debug("Enabled " + module.getClass().getSimpleName(), new Object[0]);
			}
		} else if (eventRegistry.unregisterListener(module)) {
			Messenger.debug("Disabled " + module.getClass().getSimpleName(), new Object[0]);
		}

	}

	public static void addModule(Module module) {
		modules.add(module);
	}

	public static List<Module> getModules() {
		return modules;
	}
}