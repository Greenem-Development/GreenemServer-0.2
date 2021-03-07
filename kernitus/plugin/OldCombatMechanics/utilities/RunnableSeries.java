package kernitus.plugin.OldCombatMechanics.utilities;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import org.bukkit.scheduler.BukkitRunnable;

public class RunnableSeries {
	private final List<BukkitRunnable> runnables;

	public RunnableSeries(BukkitRunnable... runnables) {
		this.runnables = Arrays.asList(runnables);
	}

	public List<BukkitRunnable> getRunnables() {
		return this.runnables;
	}

	public void cancelAll() {
		this.runnables.forEach(BukkitRunnable::cancel);
	}
}