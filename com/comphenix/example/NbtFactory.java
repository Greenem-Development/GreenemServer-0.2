package com.comphenix.example;

import com.comphenix.example.NbtFactory.1;
import com.comphenix.example.NbtFactory.NbtCompound;
import com.comphenix.example.NbtFactory.NbtList;
import com.comphenix.example.NbtFactory.NbtType;
import com.comphenix.example.NbtFactory.Wrapper;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.primitives.Primitives;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kernitus.plugin.OldCombatMechanics.utilities.reflection.Reflector;
import kernitus.plugin.OldCombatMechanics.utilities.reflection.type.ClassType;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class NbtFactory {
	private static final BiMap<Integer, Class<?>> NBT_CLASS = HashBiMap.create();
	private static final BiMap<Integer, NbtType> NBT_ENUM = HashBiMap.create();
	private static NbtFactory INSTANCE;
	private final Field[] DATA_FIELD = new Field[12];
	private Class<?> BASE_CLASS;
	private Class<?> COMPOUND_CLASS;
	private Method NBT_CREATE_TAG;
	private Method NBT_GET_TYPE;
	private Field NBT_LIST_TYPE;
	private Class<?> CRAFT_STACK;
	private Field CRAFT_HANDLE;
	private Field STACK_TAG;

	private NbtFactory() {
		if (this.BASE_CLASS == null) {
			try {
				ClassLoader loader = NbtFactory.class.getClassLoader();
				String packageName = Bukkit.getServer().getClass().getPackage().getName();
				Class<?> offlinePlayer = loader.loadClass(packageName + ".CraftOfflinePlayer");
				this.COMPOUND_CLASS = getMethod(0, 8, offlinePlayer, "getData").getReturnType();
				this.BASE_CLASS = Reflector.getClass(ClassType.NMS, "NBTBase");
				this.NBT_GET_TYPE = getMethod(0, 8, this.BASE_CLASS, "getTypeId");
				this.NBT_CREATE_TAG = getMethod(8, 0, this.BASE_CLASS, "createTag", Byte.TYPE);
				this.CRAFT_STACK = loader.loadClass(packageName + ".inventory.CraftItemStack");
				this.CRAFT_HANDLE = getField((Object) null, this.CRAFT_STACK, "handle");
				this.STACK_TAG = getField((Object) null, this.CRAFT_HANDLE.getType(), "tag");
			} catch (ClassNotFoundException var4) {
				throw new IllegalStateException("Unable to find offline player.", var4);
			}
		}

	}

	private static NbtFactory get() {
		if (INSTANCE == null) {
			INSTANCE = new NbtFactory();
		}

		return INSTANCE;
	}

	public static NbtList createList(Object... content) {
		return createList((Iterable) Arrays.asList(content));
	}

	public static NbtList createList(Iterable<?> iterable) {
      NbtFactory var10002 = get();
      var10002.getClass();
      NbtList list = new NbtList(var10002, INSTANCE.createNbtTag(NbtType.TAG_LIST, "", (Object)null), (1)null);
      Iterator var2 = iterable.iterator();

      while(var2.hasNext()) {
         Object obj = var2.next();
         list.add(obj);
      }

      return list;
   }

	public static NbtCompound createCompound() {
      NbtFactory var10002 = get();
      var10002.getClass();
      return new NbtCompound(var10002, INSTANCE.createNbtTag(NbtType.TAG_COMPOUND, "", (Object)null), (1)null);
   }

	public static NbtCompound createRootCompound(String name) {
      NbtFactory var10002 = get();
      var10002.getClass();
      return new NbtCompound(var10002, INSTANCE.createNbtTag(NbtType.TAG_COMPOUND, name, (Object)null), (1)null);
   }

	public static NbtList fromList(Object nmsList) {
      NbtFactory var10002 = get();
      var10002.getClass();
      return new NbtList(var10002, nmsList, (1)null);
   }

	public static NbtCompound fromCompound(Object nmsCompound) {
      NbtFactory var10002 = get();
      var10002.getClass();
      return new NbtCompound(var10002, nmsCompound, (1)null);
   }

	public static void setItemTag(ItemStack stack, NbtCompound compound) {
		checkItemStack(stack);
		Object nms = getFieldValue(get().CRAFT_HANDLE, stack);
		setFieldValue(get().STACK_TAG, nms, compound.getHandle());
	}

	public static NbtCompound fromItemTag(ItemStack stack) {
		checkItemStack(stack);
		Object nms = getFieldValue(get().CRAFT_HANDLE, stack);
		Object tag = getFieldValue(get().STACK_TAG, nms);
		if (tag == null) {
			NbtCompound compound = createRootCompound("tag");
			setItemTag(stack, compound);
			return compound;
		} else {
			return fromCompound(tag);
		}
	}

	public static ItemStack getCraftItemStack(ItemStack stack) {
		if (stack != null && !get().CRAFT_STACK.isAssignableFrom(stack.getClass())) {
			try {
				Constructor<?> caller = INSTANCE.CRAFT_STACK.getDeclaredConstructor(ItemStack.class);
				caller.setAccessible(true);
				return (ItemStack) caller.newInstance(stack);
			} catch (Exception var2) {
				throw new IllegalStateException("Unable to convert " + stack + " + to a CraftItemStack.");
			}
		} else {
			return stack;
		}
	}

	private static void checkItemStack(ItemStack stack) {
		if (stack == null) {
			throw new IllegalArgumentException("Stack cannot be NULL.");
		} else if (!get().CRAFT_STACK.isAssignableFrom(stack.getClass())) {
			throw new IllegalArgumentException(
					"Stack must be a CraftItemStack, found " + stack.getClass().getSimpleName());
		} else if (stack.getType() == Material.AIR) {
			throw new IllegalArgumentException("ItemStacks representing air cannot store NMS information.");
		}
	}

	private static Object invokeMethod(Method method, Object target, Object... params) {
		try {
			return method.invoke(target, params);
		} catch (Exception var4) {
			throw new RuntimeException("Unable to invoke method " + method + " for " + target, var4);
		}
	}

	private static void setFieldValue(Field field, Object target, Object value) {
		try {
			field.set(target, value);
		} catch (Exception var4) {
			throw new RuntimeException("Unable to set " + field + " for " + target, var4);
		}
	}

	private static Object getFieldValue(Field field, Object target) {
		try {
			return field.get(target);
		} catch (Exception var3) {
			throw new RuntimeException("Unable to retrieve " + field + " for " + target, var3);
		}
	}

	private static Method getMethod(int requireMod, int bannedMod, Class<?> clazz, String methodName, Class... params) {
		Method[] var5 = clazz.getDeclaredMethods();
		int var6 = var5.length;

		for (int var7 = 0; var7 < var6; ++var7) {
			Method method = var5[var7];
			if ((method.getModifiers() & requireMod) == requireMod && (method.getModifiers() & bannedMod) == 0
					&& (methodName == null || method.getName().equals(methodName))
					&& Arrays.equals(method.getParameterTypes(), params)) {
				method.setAccessible(true);
				return method;
			}
		}

		if (clazz.getSuperclass() != null) {
			return getMethod(requireMod, bannedMod, clazz.getSuperclass(), methodName, params);
		} else {
			throw new IllegalStateException(
					String.format("Unable to find method %s (%s).", methodName, Arrays.asList(params)));
		}
	}

	private static Field getField(Object instance, Class<?> clazz, String fieldName) {
		if (clazz == null) {
			clazz = instance.getClass();
		}

		Field[] var3 = clazz.getDeclaredFields();
		int var4 = var3.length;

		for (int var5 = 0; var5 < var4; ++var5) {
			Field field = var3[var5];
			if (field.getName().equals(fieldName)) {
				field.setAccessible(true);
				return field;
			}
		}

		if (clazz.getSuperclass() != null) {
			return getField(instance, clazz.getSuperclass(), fieldName);
		} else {
			throw new IllegalStateException("Unable to find field " + fieldName + " in " + instance);
		}
	}

	private Map<String, Object> getDataMap(Object handle) {
		return (Map) getFieldValue(this.getDataField(NbtType.TAG_COMPOUND, handle), handle);
	}

	private List<Object> getDataList(Object handle) {
		return (List) getFieldValue(this.getDataField(NbtType.TAG_LIST, handle), handle);
	}

	private Object unwrapValue(String name, Object value) {
		if (value == null) {
			return null;
		} else if (value instanceof Wrapper) {
			return ((Wrapper) value).getHandle();
		} else if (value instanceof List) {
			throw new IllegalArgumentException("Can only insert a WrappedList.");
		} else if (value instanceof Map) {
			throw new IllegalArgumentException("Can only insert a WrappedCompound.");
		} else {
			return this.createNbtTag(this.getPrimitiveType(value), name, value);
		}
	}

	private Object wrapNative(Object nms) {
      if (nms == null) {
         return null;
      } else if (this.BASE_CLASS.isAssignableFrom(nms.getClass())) {
         NbtType type = this.getNbtType(nms);
         switch(1.$SwitchMap$com$comphenix$example$NbtFactory$NbtType[type.ordinal()]) {
         case 1:
            return new NbtCompound(this, nms, (1)null);
         case 2:
            return new NbtList(this, nms, (1)null);
         default:
            return getFieldValue(this.getDataField(type, nms), nms);
         }
      } else {
         throw new IllegalArgumentException("Unexpected type: " + nms);
      }
   }

	private Object createNbtTag(NbtType type, String name, Object value) {
		Object tag = invokeMethod(this.NBT_CREATE_TAG, (Object) null, (byte) type.id);
		if (value != null) {
			setFieldValue(this.getDataField(type, tag), tag, value);
		}

		return tag;
	}

	private Field getDataField(NbtType type, Object nms) {
		if (this.DATA_FIELD[type.id] == null) {
			this.DATA_FIELD[type.id] = getField(nms, (Class) null, NbtType.access$200(type));
		}

		return this.DATA_FIELD[type.id];
	}

	private NbtType getNbtType(Object nms) {
		int type = (Byte) invokeMethod(this.NBT_GET_TYPE, nms);
		return (NbtType) NBT_ENUM.get(Integer.valueOf(type));
	}

	private NbtType getPrimitiveType(Object primitive) {
		NbtType type = (NbtType) NBT_ENUM.get(NBT_CLASS.inverse().get(Primitives.unwrap(primitive.getClass())));
		if (type == null) {
			throw new IllegalArgumentException(String.format("Illegal type: %s (%s)", primitive.getClass(), primitive));
		} else {
			return type;
		}
	}
}