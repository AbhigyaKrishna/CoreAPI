package me.Abhigya.core.util.bungeecord;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * Class for sending plugin messages via Bungeecord.
 */
public class MessagingUtils {

    /**
     * Plugin Messaging arguments.
     */
    public static final String MESSAGING_CHANNEL = "BungeeCord";
    public static final String PLAYER_IP_ARGUMENT = "IP";
    public static final String SERVER_PLAYER_COUNT_ARGUMENT = "PlayerCount";
    public static final String SERVER_PLAYER_LIST_ARGUMENT = "PlayerList";
    public static final String SERVER_NAME_ARGUMENT = "GetServer";
    public static final String SERVERS_NAMES_ARGUMENT = "GetServers";
    public static final String SERVER_IP_ARGUMENT = "ServerIP";
    public static final String CONNECT_OTHER_ARGUMENT = "ConnectOther";

    /**
     * Send plugin message using the channel 'BungeeCord'.
     * <p>
     *
     * @param arguments the arguments to send.
     * @throws IOException
     * @throws SecurityException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    public static void sendPluginMessage(Plugin plugin, Writable... arguments) throws IOException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
        /* donnot send empty arguments */
        if (arguments == null || arguments.length == 0) {
            return;
        }

        /* make streams and write arguments */
        final ByteArrayOutputStream array_stream = new ByteArrayOutputStream();
        final DataOutputStream out_stream = new DataOutputStream(array_stream);
        for (Writable argument : arguments) {
            if (argument != null && argument.getObjectToWrite() != null && argument.getWriteType() != null) {
                argument.writeTo(out_stream);
            }
        }

        /* send */
        Bukkit.getServer().sendPluginMessage(plugin, MESSAGING_CHANNEL, array_stream.toByteArray());
    }

    /**
     * Send plugin message using the channel 'BungeeCord'.
     * <p>
     *
     * @param written arguments from {@link Written}
     * @throws IOException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     * @throws SecurityException
     */
    public static void sendPluginMessage(Plugin plugin, Written written) throws IOException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
        /* donnot send empty arguments */
        if (written == null || written.getWritables().isEmpty()) {
            return;
        }

        /* make streams and write arguments */
        final ByteArrayOutputStream array_stream = new ByteArrayOutputStream();
        final DataOutputStream out_stream = new DataOutputStream(array_stream);
        for (Writable argument : written.getWritables()) {
            if (argument != null && argument.getObjectToWrite() != null && argument.getWriteType() != null) {
                argument.writeTo(out_stream);
            }
        }

        /* send */
        Bukkit.getServer().sendPluginMessage(plugin, MESSAGING_CHANNEL, array_stream.toByteArray());
    }

    /**
     * Send plugin message using the channel 'BungeeCord' to a specific
     * {@link Player}.
     * <p>
     *
     * @param plugin    Messaging plugin
     * @param player    Player messenger
     * @param arguments Arguments to send
     * @throws IOException
     * @throws SecurityException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    public static void sendPluginMessage(Plugin plugin, Player player, Writable... arguments)
            throws IOException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
            NoSuchMethodException, SecurityException {
        /* donnot send empty arguments */
        if (player == null || arguments == null || arguments.length == 0) {
            return;
        }

        /* make streams and write arguments */
        final ByteArrayOutputStream array_stream = new ByteArrayOutputStream();
        final DataOutputStream out_stream = new DataOutputStream(array_stream);
        for (Writable argument : arguments) {
            if (argument != null && argument.getObjectToWrite() != null && argument.getWriteType() != null) {
                argument.writeTo(out_stream);
            }
        }

        /* send */
        player.sendPluginMessage(plugin, MESSAGING_CHANNEL, array_stream.toByteArray());
    }

    /**
     * Send plugin message using the channel 'BungeeCord' to a specific {@link Player}.
     * <p>
     *
     * @param plugin  Messaging plugin
     * @param player  Player messenger
     * @param written Arguments to send
     * @throws IOException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     * @throws SecurityException
     */
    public static void sendPluginMessage(Plugin plugin, Player player, Written written)
            throws IOException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
            NoSuchMethodException, SecurityException {
        /* donnot send empty arguments */
        if (player == null || written == null || written.getWritables().isEmpty()) {
            return;
        }

        /* make streams and write arguments */
        final ByteArrayOutputStream array_stream = new ByteArrayOutputStream();
        final DataOutputStream out_stream = new DataOutputStream(array_stream);
        for (Writable argument : written.getWritables()) {
            if (argument != null && argument.getObjectToWrite() != null && argument.getWriteType() != null) {
                argument.writeTo(out_stream);
            }
        }

        /* send */
        player.sendPluginMessage(plugin, MESSAGING_CHANNEL, array_stream.toByteArray());
    }

}
