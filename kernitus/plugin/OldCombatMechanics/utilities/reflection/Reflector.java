package kernitus.plugin.OldCombatMechanics.utilities.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.function.Function;
import java.util.function.Predicate;
import kernitus.plugin.OldCombatMechanics.utilities.reflection.type.ClassType;
import org.bukkit.Bukkit;

public class Reflector {
	private static String version = "";

	public static String getVersion() {
		return version;
	}

	public static boolean versionIsNewerOrEqualAs(int major, int minor, int patch) {
		if (getMajorVersion() < major) {
			return false;
		} else if (getMinorVersion() < minor) {
			return false;
		} else {
			return getPatchVersion() >= patch;
		}
	}

	private static int getMajorVersion() {
		return Integer.parseInt(getVersionSanitized().split("_")[0]);
	}

	private static String getVersionSanitized() {
		return getVersion().replaceAll("[^\\d_]", "");
	}

	private static int getMinorVersion() {
		return Integer.parseInt(getVersionSanitized().split("_")[1]);
	}

	private static int getPatchVersion() {
		String[] split = getVersionSanitized().split("_");
		return split.length < 3 ? 0 : Integer.parseInt(split[2]);
	}

	public static Class<?> getClass(ClassType type, String name) {
		try {
			return Class.forName(String.format("%s.%s.%s", type.getPackage(), version, name));
		} catch (ClassNotFoundException var3) {
			var3.printStackTrace();
			return null;
		}
	}

	public static Method getMethod(Class<?> clazz, String name) {
		return (Method) Arrays.stream(clazz.getMethods()).filter((method) -> {
			return method.getName().equals(name);
		}).findFirst().orElse((Object) null);
	}

	public static <T> T invokeMethod(Method method, Object handle, Object... params) {
		try {
			T t = method.invoke(handle, params);
			return t;
		} catch (InvocationTargetException | IllegalAccessException var4) {
			throw new RuntimeException(var4);
		}
	}

	public static <T, R> Function<T, R> memoizeMethodAndInvoke(Class<T> clazz, String name, Object... params) {
		Method method = getMethod(clazz, name);
		return (t) -> {
			return invokeMethod(method, t, params);
		};
	}

	public static Field getField(Class<?> clazz, String fieldName) {
		try {
			return clazz.getDeclaredField(fieldName);
		} catch (NoSuchFieldException var3) {
			throw new RuntimeException(var3);
		}
	}

	public static Field getInaccessibleField(Class<?> clazz, String fieldName) {
		Field field = getField(clazz, fieldName);
		field.setAccessible(true);
		return field;
	}

	public static Object getFieldValue(Object object, String fieldName) throws Exception {
		return findFieldWithinHierarchy(object, fieldName).get(object);
	}

	private static Field findFieldWithinHierarchy(Object object, String fieldName) throws NoSuchFieldException {
		for (Class clazz = object.getClass(); clazz != null; clazz = clazz.getSuperclass()) {
			Field[] var3 = clazz.getDeclaredFields();
			int var4 = var3.length;

			for (int var5 = 0; var5 < var4; ++var5) {
				Field field = var3[var5];
				if (field.getName().equals(fieldName)) {
					field.setAccessible(true);
					return field;
				}
			}
		}

		throw new NoSuchFieldException(fieldName);
	}

	public static Object getFieldValue(Field field, Object handle) {
		field.setAccessible(true);

		try {
			return field.get(handle);
		} catch (IllegalAccessException var3) {
			throw new RuntimeException(var3);
		}
	}

	public static void setFieldValue(Object object, String fieldName, Object value) throws Exception {
		getInaccessibleField(object.getClass(), fieldName).set(object, value);
	}

	public static Constructor<?> getConstructor(Class<?> clazz, int numParams) {
		return (Constructor) Arrays.stream(clazz.getConstructors()).filter((constructor) -> {
			return constructor.getParameterCount() == numParams;
		}).findFirst().orElse((Object) null);
	}

	public static boolean inheritsFrom(Class<?> toCheck, Class<?> inheritedClass) {
		if (inheritedClass.isAssignableFrom(toCheck)) {
			return true;
		} else {
			Class[] var2 = toCheck.getInterfaces();
			int var3 = var2.length;

			for (int var4 = 0; var4 < var3; ++var4) {
				Class<?> implementedInterface = var2[var4];
				if (inheritsFrom(implementedInterface, inheritedClass)) {
					return true;
				}
			}

			return false;
		}
	}

	static {
		try {
			version = Bukkit.getServer().getClass().getName().split("\\.")[3];
			Class<?> CRAFT_PLAYER = getClass(ClassType.CRAFTBUKKIT, "entity.CraftPlayer");

			assert CRAFT_PLAYER != null;
		} catch (Exception var1) {
			System.err.println("Failed to load Reflector");
			var1.printStackTrace();
		}

	}
}