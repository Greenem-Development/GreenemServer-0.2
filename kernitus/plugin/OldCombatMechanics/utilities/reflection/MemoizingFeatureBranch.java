package kernitus.plugin.OldCombatMechanics.utilities.reflection;

import java.util.function.Function;
import kernitus.plugin.OldCombatMechanics.utilities.reflection.MemoizingFeatureBranch.ExceptionalFunction;
import kernitus.plugin.OldCombatMechanics.utilities.reflection.MemoizingFeatureBranch.ExceptionalFunction.WrappedException;

public class MemoizingFeatureBranch<T, R> {
	private final Function<T, Boolean> test;
	private final Function<T, R> trueBranch;
	private final Function<T, R> falseBranch;
	private Function<T, R> chosen;

	public MemoizingFeatureBranch(Function<T, Boolean> test, Function<T, R> trueBranch, Function<T, R> falseBranch) {
		this.test = test;
		this.trueBranch = trueBranch;
		this.falseBranch = falseBranch;
	}

	public R apply(T target) {
		if (this.chosen == null) {
			synchronized (this) {
				if (this.chosen == null) {
					this.chosen = (Boolean) this.test.apply(target) ? this.trueBranch : this.falseBranch;
				}
			}
		}

		return this.chosen.apply(target);
	}

	public static <T, R> MemoizingFeatureBranch<T, R> onException(ExceptionalFunction<T, R> action,
			Function<T, R> success, Function<T, R> failure) {
		return new MemoizingFeatureBranch((t) -> {
			try {
				action.apply(t);
				return true;
			} catch (WrappedException var3) {
				return false;
			}
		}, success, failure);
	}
}