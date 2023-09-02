package me.Abhigya.core.util.reflection.bukkit;

import io.netty.channel.Channel;
import me.Abhigya.core.main.CoreAPI;
import me.Abhigya.core.util.reflection.general.FieldReflection;
import me.Abhigya.core.util.server.Version;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;

/** Class for reflecting {@link Player} */
public class PlayerReflection {

    public static final String PLAYER_CONNECTION_FIELD_NAME =
            CoreAPI.getInstance().getServerVersion().isNewerEquals(Version.v1_17_R1)
                    ? "b"
                    : "playerConnection";
    public static final String NETWORK_MANAGER_FIELD_NAME =
            CoreAPI.getInstance().getServerVersion().isNewerEquals(Version.v1_17_R1)
                    ? "a"
                    : "networkManager";

    /**
     * Gets the handle ( the represented nms player by the craftbukkit player ) of the provided
     * {@code player}.
     *
     * <p>
     *
     * @param player Player to get
     * @return Handle of the provided craftbukkit player
     * @throws SecurityException reflection exception...
     * @throws InvocationTargetException reflection exception...
     * @throws IllegalArgumentException reflection exception...
     * @throws IllegalAccessException reflection exception...
     * @throws UnsupportedOperationException if couldn't get the handle of the provided player
     * @see BukkitReflection#getHandle(Object)
     */
    public static Object getHandle(Player player)
            throws IllegalAccessException,
                    IllegalArgumentException,
                    InvocationTargetException,
                    SecurityException {
        return BukkitReflection.getHandle(player);
    }

    /**
     * Gets player's connection.
     *
     * <p>
     *
     * @param player Player to get
     * @return Player's connection
     * @throws SecurityException reflection exception...
     * @throws NoSuchFieldException reflection exception...
     * @throws IllegalArgumentException reflection exception...
     * @throws IllegalAccessException reflection exception...
     * @throws InvocationTargetException reflection exception...
     */
    public static Object getPlayerConnection(Player player)
            throws SecurityException,
                    NoSuchFieldException,
                    IllegalArgumentException,
                    IllegalAccessException,
                    InvocationTargetException {
        return FieldReflection.getValue(
                BukkitReflection.getHandle(player), PLAYER_CONNECTION_FIELD_NAME);
    }

    /**
     * Gets player's network manager.
     *
     * <p>
     *
     * @param player Player to get
     * @return Player's network manager
     * @throws SecurityException reflection exception...
     * @throws NoSuchFieldException reflection exception...
     * @throws IllegalArgumentException reflection exception...
     * @throws IllegalAccessException reflection exception...
     * @throws InvocationTargetException reflection exception...
     */
    public static Object getNetworkManager(Player player)
            throws SecurityException,
                    NoSuchFieldException,
                    IllegalArgumentException,
                    IllegalAccessException,
                    InvocationTargetException {
        return FieldReflection.getValue(getPlayerConnection(player), NETWORK_MANAGER_FIELD_NAME);
    }

    /**
     * Gets player's channel.
     *
     * <p>
     *
     * @param player Player to get
     * @return Player's channel
     * @throws SecurityException reflection exception...
     * @throws NoSuchFieldException reflection exception...
     * @throws IllegalArgumentException reflection exception...
     * @throws IllegalAccessException reflection exception...
     * @throws InvocationTargetException reflection exception...
     */
    public static Channel getChannel(Player player)
            throws SecurityException,
                    NoSuchFieldException,
                    IllegalArgumentException,
                    IllegalAccessException,
                    InvocationTargetException {
        String channelField;
        switch (CoreAPI.getInstance().getServerVersion()) {
            case v1_8_R1:
                channelField = "i";
            case v1_8_R2:
            case v1_17_R1:
                channelField = "k";
            default:
                channelField = "channel";
        }
        return (Channel) FieldReflection.getValue(getNetworkManager(player), channelField);
    }
}
