package me.Abhigya.core.particle.utils;

import me.Abhigya.core.particle.ParticleConstants;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static me.Abhigya.core.particle.ParticleConstants.BLOCK_POSITION_CONSTRUCTOR;

public class ParticleReflectionUtils {

    /* ---------------- NMS & CB paths ---------------- */

    /**
     * Represents the net minecraft server path
     * <p>
     * e.g. {@code net.minecraft.server.v1_8_R3}, {@code net.minecraft.server.v1_12_R1}
     */
    private static final String NET_MINECRAFT_SERVER_PACKAGE_PATH;
    /**
     * Represents the craftbukkit path
     * <p>
     * e.g. {@code org.bukkit.craftbukkit.v1_8_R3}, {@code org.bukkit.craftbukkit.v1_12_R1}
     */
    private static final String CRAFT_BUKKIT_PACKAGE_PATH;

    /* ---------------- PlayerConnection caching ---------------- */

    /**
     * The current Minecraft version as an int.
     */
    public static final int MINECRAFT_VERSION;

    /**
     * A cache for playerconnections.
     */
    public static final PlayerConnectionCache PLAYER_CONNECTION_CACHE;

    static {
        String serverPath = Bukkit.getServer().getClass().getPackage().getName();
        String version = serverPath.substring(serverPath.lastIndexOf(".") + 1);
        String packageVersion = serverPath.substring(serverPath.lastIndexOf(".") + 2);
        MINECRAFT_VERSION = Integer.parseInt(packageVersion.substring(0, packageVersion.lastIndexOf("_")).replace("_", ".").substring(2));
        NET_MINECRAFT_SERVER_PACKAGE_PATH = "net.minecraft" + (MINECRAFT_VERSION < 17 ? ".server." + version : "");
        CRAFT_BUKKIT_PACKAGE_PATH = "org.bukkit.craftbukkit." + version;
        PLAYER_CONNECTION_CACHE = new PlayerConnectionCache();
    }

    /**
     * Gets a class but returns null instead of throwing
     * a {@link ClassNotFoundException}.
     * <p>
     *
     * @param path Path of the class
     * @return Class, if the class isn't found null
     */
    public static Class<?> getClassSafe(String path) {
        try {
            return Class.forName(path);
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * Gets the nms path of a class without depending on versions
     * <p>
     * e.g.
     * getNMSPath("Block")  = "net.minecraft.server.v1_14_R1.Block"
     * getNMSPath("Entity") = "net.minecraft.server.v1_12_R1.Entity"
     * <p>
     *
     * @param path Path that should be added to the nms path
     * @return Nms path
     */
    public static String getNMSPath(String path) {
        return getNetMinecraftServerPackagePath() + "." + path;
    }

    /**
     * Directly gets the class object over the path
     * <p>
     *
     * @param path Path of the class
     * @return Class, if the class isn't found null
     */
    public static Class<?> getNMSClass(String path) {
        return getClassSafe(getNMSPath(path));
    }

    /**
     * Gets the craftbukkit path of a class without depending on versions
     * <p>
     * e.g.
     * getCraftBukkitPath("CraftChunk")              = "org.bukkit.craftbukkit.v1_15_R1.CraftChunk"
     * getCraftBukkitPath("event.CraftEventFactory") = "org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory"
     * <p>
     *
     * @param path Path that should be added to the craftbukkit path
     * @return Craftbukkit path
     */
    public static String getCraftBukkitPath(String path) {
        return getCraftBukkitPackagePath() + "." + path;
    }

    /**
     * Method to directly get the class object over the path.
     * <p>
     *
     * @param path Path of the class
     * @return Class, if the class isn't found null
     */
    public static Class<?> getCraftBukkitClass(String path) {
        return getClassSafe(getCraftBukkitPath(path));
    }

    /**
     * Method to not get disturbed by the forced try catch block.
     * <p>
     *
     * @param targetClass    {@link Class} the {@link Method} is in
     * @param methodName     Name of the target {@link Method}
     * @param parameterTypes ParameterTypes of the {@link Method}
     * @return If found the target {@link Method}, if not found null.
     */
    public static Method getMethodOrNull(Class targetClass, String methodName, Class<?>... parameterTypes) {
        try {
            return targetClass.getMethod(methodName, parameterTypes);
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * Method to not get disturbed by the forced try catch block.
     * <p>
     *
     * @param targetClass {@link Class} the {@link Field} is in
     * @param fieldName   Name of the target {@link Field}
     * @param declared    Defines if the target {@link Field} is private
     * @return If found the target {@link Field}, if not found null.
     */
    public static Field getFieldOrNull(Class targetClass, String fieldName, boolean declared) {
        try {
            return declared ? targetClass.getDeclaredField(fieldName) : targetClass.getField(fieldName);
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * Gets a constructor without throwing exceptions.
     * <p>
     *
     * @param targetClass    {@link Class} the {@link Constructor} is in
     * @param parameterTypes ParameterTypes of the {@link Constructor}
     * @return If found the target {@link Constructor}, if not found null.
     */
    public static Constructor getConstructorOrNull(Class targetClass, Class... parameterTypes) {
        try {
            return targetClass.getConstructor(parameterTypes);
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * Checks if a class exists.
     * <p>
     *
     * @param path Path of the class that should be checked
     * @return true if the defined class exists
     */
    public static boolean existsClass(String path) {
        try {
            Class.forName(path);
            return true;
        } catch (Exception ignored) {
            return false;
        }
    }

    /**
     * Gets the specified Field over the specified fieldName and the given targetClass. Then reads the specified
     * {@link Field} from the specified {@link Object}. When the {@link Field} is static
     * set the object to {@code null}.
     * <p>
     *
     * @param targetClass {@link Class} of the field
     * @param fieldName   Name of the searched {@link Field}
     * @param object      {@link Object} from which the specified {@link Field Fields} value is to be extracted
     * @return Extracted value of the specified {@link Field} in the specified {@link Object}
     */
    public static Object readField(Class targetClass, String fieldName, Object object) {
        if (targetClass == null || fieldName == null)
            return null;
        return readField(getFieldOrNull(targetClass, fieldName, false), object);
    }

    /**
     * Reads the specified {@link Field} from the specified {@link Object}. When the
     * {@link Field} is static set the object to {@code null}.
     * <p>
     *
     * @param field  {@link Field} from which the value should be extracted.
     * @param object {@link Object} from which the specified {@link Field Fields} value is to be extracted
     * @return Extracted value of the specified {@link Field} in the specified {@link Object}
     */
    public static <T> T readField(Field field, Object object) {
        if (field == null)
            return null;
        try {
            return (T) field.get(object);
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * Gets the specified declared Field over  the specified fieldName and the given
     * targetClass. Then reads the specified {@link Field} from the specified
     * {@link Object}. When the {@link Field} is static set the object to {@code null}.
     * <p>
     *
     * @param targetClass {@link Class} of the field
     * @param fieldName   Name of the searched {@link Field}
     * @param object      {@link Object} from which the specified {@link Field Fields} value is to be extracted
     * @return Extracted value of the specified {@link Field} in the specified {@link Object}
     */
    public static Object readDeclaredField(Class targetClass, String fieldName, Object object) {
        if (targetClass == null || fieldName == null)
            return null;
        return readDeclaredField(getFieldOrNull(targetClass, fieldName, true), object);
    }

    /**
     * Reads the declared specified {@link Field} from the specified {@link Object}.
     * When the {@link Field} is static set the object to {@code null}.
     * <p>
     *
     * @param field  {@link Field} from which the value should be extracted.
     * @param object {@link Object} from which the specified {@link Field Fields} value is to be extracted
     * @return Extracted value of the specified {@link Field} in the specified {@link Object}
     */
    public static <T> T readDeclaredField(Field field, Object object) {
        if (field == null)
            return null;
        field.setAccessible(true);
        try {
            return (T) field.get(object);
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * Gets the specified declared {@link Field} over the specified fieldName and the given
     * targetClass. Then writes the specified value into the specified {@link Field}
     * in the given {@link Object}.
     * <p>
     *
     * @param targetClass {@link Class} of the field
     * @param fieldName   Name of the searched {@link Field}
     * @param object      {@link Object} whose {@link Field Fields} value should be changed
     * @param value       Value which should be set in the specified {@link Field}
     */
    public static void writeDeclaredField(Class targetClass, String fieldName, Object object, Object value) {
        if (targetClass == null || fieldName == null)
            return;
        writeDeclaredField(getFieldOrNull(targetClass, fieldName, true), object, value);
    }

    /**
     * Writes a value to the specified declared {@link Field} in the
     * given {@link Object}.
     * <p>
     *
     * @param field  {@link Field} which should be changed
     * @param object {@link Object} whose {@link Field Fields} value should be changed
     * @param value  Value which should be set in the specified {@link Field}
     */
    public static void writeDeclaredField(Field field, Object object, Object value) {
        if (field == null)
            return;
        field.setAccessible(true);
        try {
            field.set(object, value);
        } catch (Exception ignored) {
        }
    }

    /**
     * Gets the specified {@link Field} over the specified fieldName and the given
     * targetClass. Then writes the specified value into the specified {@link Field}
     * in the given {@link Object}.
     * <p>
     *
     * @param targetClass {@link Class} of the field
     * @param fieldName   Name of the searched {@link Field}
     * @param object      {@link Object} whose {@link Field Fields} value should be changed
     * @param value       Value which should be set in the specified {@link Field}
     */
    public static void writeField(Class targetClass, String fieldName, Object object, Object value) {
        if (targetClass == null || fieldName == null)
            return;
        writeField(getFieldOrNull(targetClass, fieldName, false), object, value);
    }

    /**
     * Writes a value to the specified {@link Field} in the given
     * {@link Object}.
     * <p>
     *
     * @param field  {@link Field} which should be changed
     * @param object {@link Object} whose {@link Field Fields} value should be changed
     * @param value  Value which should be set in the specified {@link Field}
     */
    public static void writeField(Field field, Object object, Object value) {
        if (field == null)
            return;
        try {
            field.set(object, value);
        } catch (Exception ignored) {
        }
    }

    /**
     * @return Nms path
     */
    public static String getNetMinecraftServerPackagePath() {
        return NET_MINECRAFT_SERVER_PACKAGE_PATH;
    }

    /**
     * @return Craftbukkit path
     */
    public static String getCraftBukkitPackagePath() {
        return CRAFT_BUKKIT_PACKAGE_PATH;
    }

    /**
     * Creates a new MinecraftKey with the given data.
     * <p>
     *
     * @param key Data that should be used in the constructor of the key.
     * @return New MinecraftKey
     */
    public static Object getMinecraftKey(String key) {
        if (key == null)
            return null;
        try {
            return ParticleConstants.MINECRAFT_KEY_CONSTRUCTOR.newInstance(key);
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * Creates a new Vector3fa instance.
     * <p>
     *
     * @param x X value of the vector
     * @param y Y value of the vector
     * @param z Z value of the vector
     * @return Vector3fa instance with the specified coordinates
     */
    public static Object createVector3fa(float x, float y, float z) {
        try {
            return ParticleConstants.VECTOR_3FA_CONSTRUCTOR.newInstance(x, y, z);
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * Creates a new BlockPosition.
     * <p>
     *
     * @param location {@link Location} of the block
     * @return BlockPosition of the location
     */
    public static Object createBlockPosition(Location location) {
        try {
            return BLOCK_POSITION_CONSTRUCTOR.newInstance(location.getBlockX(), location.getBlockY(), location.getBlockZ());
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * Gets the EntityPlayer instance of a CraftPlayer
     * <p>
     *
     * @param player CraftPlayer
     * @return EntityPlayer instance of the defined CraftPlayer or {@code null}
     * if either the given parameter is invalid or an error occurs.
     */
    public static Object getPlayerHandle(Player player) {
        if (player == null || player.getClass() != ParticleConstants.CRAFT_PLAYER_CLASS)
            return null;
        try {
            return ParticleConstants.CRAFT_PLAYER_GET_HANDLE_METHOD.invoke(player);
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * Gets the PlayerConnection of a {@link Player}
     * <p>
     *
     * @param target Target {@link Player}
     * @return PlayerConnection of the specified target {@link Player}
     */
    public static Object getPlayerConnection(Player target) {
        try {
            return readField(ParticleConstants.ENTITY_PLAYER_PLAYER_CONNECTION_FIELD, getPlayerHandle(target));
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * Sends a packet to a defined player.
     * <p>
     *
     * @param player Player that should receive the packet
     * @param packet Packet that should be sent
     */
    public static void sendPacket(Player player, Object packet) {
        try {
            Object connection = PLAYER_CONNECTION_CACHE.getConnection(player);
            ParticleConstants.PLAYER_CONNECTION_SEND_PACKET_METHOD.invoke(connection, packet);
        } catch (Exception ignored) {
        }
    }

}
