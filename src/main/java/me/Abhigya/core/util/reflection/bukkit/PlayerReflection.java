package me.Abhigya.core.util.reflection.bukkit;

import io.netty.channel.Channel;
import me.Abhigya.core.util.reflection.general.FieldReflection;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;

/**
 * Class for reflecting {@link Player}
 */
public class PlayerReflection {

    /**
     * Gets the handle ( the represented nms player by the craftbukkit player ) of
     * the provided {@code player}.
     * <p>
     *
     * @param player Player to get
     * @return Handle of the provided craftbukkit player
     * @throws SecurityException             reflection exception...
     * @throws InvocationTargetException     reflection exception...
     * @throws IllegalArgumentException      reflection exception...
     * @throws IllegalAccessException        reflection exception...
     * @throws UnsupportedOperationException if couldn't get the handle of the
     *                                       provided player
     * @see BukkitReflection#getHandle(Object)
     */
    public static Object getHandle(Player player)
            throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, SecurityException {
        return BukkitReflection.getHandle(player);
    }

    /**
     * Gets player's connection.
     * <p>
     *
     * @param player Player to get
     * @return Player's connection
     * @throws SecurityException         reflection exception...
     * @throws NoSuchFieldException      reflection exception...
     * @throws IllegalArgumentException  reflection exception...
     * @throws IllegalAccessException    reflection exception...
     * @throws InvocationTargetException reflection exception...
     */
    public static Object getPlayerConnection(Player player) throws SecurityException, NoSuchFieldException,
            IllegalArgumentException, IllegalAccessException, InvocationTargetException {
        return FieldReflection.getValue(BukkitReflection.getHandle(player), "playerConnection");
    }

    /**
     * Gets player's network manager.
     * <p>
     *
     * @param player Player to get
     * @return Player's network manager
     * @throws SecurityException         reflection exception...
     * @throws NoSuchFieldException      reflection exception...
     * @throws IllegalArgumentException  reflection exception...
     * @throws IllegalAccessException    reflection exception...
     * @throws InvocationTargetException reflection exception...
     */
    public static Object getNetworkManager(Player player) throws SecurityException, NoSuchFieldException,
            IllegalArgumentException, IllegalAccessException, InvocationTargetException {
        return FieldReflection.getValue(getPlayerConnection(player), "networkManager");
    }

    /**
     * Gets player's channel.
     * <p>
     *
     * @param player Player to get
     * @return Player's channel
     * @throws SecurityException         reflection exception...
     * @throws NoSuchFieldException      reflection exception...
     * @throws IllegalArgumentException  reflection exception...
     * @throws IllegalAccessException    reflection exception...
     * @throws InvocationTargetException reflection exception...
     */
    public static Channel getChannel(Player player) throws SecurityException, NoSuchFieldException,
            IllegalArgumentException, IllegalAccessException, InvocationTargetException {
        return (Channel) FieldReflection.getValue(getNetworkManager(player), "channel");
    }
}
