package me.Abhigya.core.util.reflection.bukkit;

import me.Abhigya.core.main.CoreAPI;
import me.Abhigya.core.util.reflection.general.ClassReflection;
import me.Abhigya.core.util.reflection.general.FieldReflection;
import me.Abhigya.core.util.reflection.general.MethodReflection;
import me.Abhigya.core.util.server.Version;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;

/** Class for reflecting servers based on Bukkit. */
public class BukkitReflection {

    /**
     * Gets the handle (the represented nms object by a craftbukkit object) of the provided
     * craftbukkit object.
     *
     * <p>
     *
     * @param object Object to get
     * @return Handle of the provided craftbukkit object
     * @throws SecurityException reflection exception...
     * @throws InvocationTargetException reflection exception...
     * @throws IllegalArgumentException reflection exception...
     * @throws IllegalAccessException reflection exception...
     * @throws UnsupportedOperationException if couldn't get the handle of the provided object
     */
    public static Object getHandle(Object object)
            throws IllegalAccessException,
                    IllegalArgumentException,
                    InvocationTargetException,
                    SecurityException {
        try {
            return object.getClass().getMethod("getHandle").invoke(object);
        } catch (NoSuchMethodException ex_a) {
            throw new UnsupportedOperationException(
                    "cannot get the handle of the provided object!");
        }
    }

    /**
     * Sends the provided packet to the desired player.
     *
     * <p>
     *
     * @param player Player that will receive the packet
     * @param packet Packet instance to send
     */
    public static void sendPacket(Player player, Object packet) {
        try {
            Object nms_player = getHandle(player);
            Object connection;

            if (CoreAPI.getInstance().getServerVersion().isNewerEquals(Version.v1_17_R1))
                connection = FieldReflection.getValue(nms_player, "b");
            else connection = FieldReflection.getValue(nms_player, "playerConnection");

            MethodReflection.getAccessible(
                            connection.getClass(),
                            "sendPacket",
                            ClassReflection.getNmsClass("Packet", "network.protocol"))
                    .invoke(connection, packet);
        } catch (IllegalAccessException
                | IllegalArgumentException
                | InvocationTargetException
                | NoSuchMethodException
                | SecurityException
                | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets the MOTD of the current running server.
     *
     * <p>
     *
     * @param motd New MOTD for this server
     */
    public static void setMotd(String motd) {
        try {
            Class<?> server_class = ClassReflection.getNmsClass("MinecraftServer", "");
            Object server =
                    MethodReflection.invokeAccessible(
                            MethodReflection.get(server_class, "getServer"), server_class);

            MethodReflection.invokeAccessible(
                    MethodReflection.get(server_class, "setMotd", String.class), server, motd);
        } catch (IllegalAccessException
                | IllegalArgumentException
                | InvocationTargetException
                | NoSuchMethodException
                | SecurityException e) {
            e.printStackTrace();
        }
    }

    /**
     * Clears the world border of the desired {@link World}.
     *
     * <p>
     *
     * @param world World with the desired border to clear
     */
    public static void clearBorder(World world) {
        world.getWorldBorder().reset();

        try {
            FieldReflection.setValue(world, "worldBorder", null);
        } catch (SecurityException | NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
