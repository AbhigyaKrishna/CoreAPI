package me.Abhigya.core.util.material;

import me.Abhigya.core.util.reflection.general.ClassReflection;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@SuppressWarnings("deprecation")
public class MaterialUtils {

    public static Class<?> CRAFT_MAGIC_NUMBERS_CLASS;
    public static Method FROM_LEGACY_DATA_PRIORITY;

    /* initialize util fields */
    static {
        try {
            CRAFT_MAGIC_NUMBERS_CLASS = ClassReflection.getCraftClass("CraftMagicNumbers", "util");
        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        }

        try {
            FROM_LEGACY_DATA_PRIORITY = CRAFT_MAGIC_NUMBERS_CLASS.getMethod("fromLegacy", MaterialData.class,
                    boolean.class);
        } catch (NoSuchMethodException | SecurityException e) {
            /* ignore */
        }
    }

    public static Material getRightMaterial(ItemStack stack) {
        try {
            if (isLegacy(stack.getType())) {
                return (Material) FROM_LEGACY_DATA_PRIORITY.invoke(CRAFT_MAGIC_NUMBERS_CLASS, stack.getData(), true);
            }
        } catch (Throwable t) {
            /* ignore */
        }
        return stack.getType();
    }

    public static Material getRightMaterial(Material material) {
        return getRightMaterial(new ItemStack(material));
    }

    public static boolean isLegacy(Material material) {
        try {
            return (boolean) material.getClass().getMethod("isLegacy").invoke(material.getClass());
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
                | SecurityException e) {
            return false;
        }
    }

    public static boolean equals(Material a, Material b) {
        return (a != null && b != null && getRightMaterial(a) == getRightMaterial(b));
    }

    /**
     * Creates a new BlockData instance for the specified Material, with all
     * properties initialized to unspecified defaults.
     * <p>
     *
     * @param material Material
     * @return New data instance, or null if invoked from a server version older than 1_13_R1.
     */
    public static Object createBlockData(Material material) {
        try {
            Method create = material.getClass().getMethod("createBlockData");
            if (create != null) { // null if is a server version older than 1_13_R1
                return create.invoke(material);
            }
        } catch (Throwable t) {
            return null;
        }
        return null;
    }

}
