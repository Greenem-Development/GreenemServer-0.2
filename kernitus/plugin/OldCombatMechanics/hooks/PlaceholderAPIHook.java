package kernitus.plugin.OldCombatMechanics.hooks;

import kernitus.plugin.OldCombatMechanics.OCMMain;
import kernitus.plugin.OldCombatMechanics.hooks.PlaceholderAPIHook.1;
import kernitus.plugin.OldCombatMechanics.hooks.api.Hook;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;

public class PlaceholderAPIHook implements Hook {
	private PlaceholderExpansion expansion;

	public void init(OCMMain plugin) {
      this.expansion = new 1(this, plugin);
      this.expansion.register();
   }

	public void deinit(OCMMain plugin) {
		if (this.expansion != null) {
			this.expansion.unregister();
		}

	}
}