package me.Abhigya.core.util.console;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;

/**
 * Methods for dealing with the console of servers based on Bukkit.
 */
public class ConsoleUtils {

    /**
     * Writes to console the desired message.
     * <p>
     *
     * @param message Message to send.
     */
    public static void sendMessage(String message) {
        Bukkit.getConsoleSender().sendMessage(message);
    }

    /**
     * Writes to console the desired formatted message.
     * <p>
     *
     * @param color   Color of the format.
     * @param message Content of the message.
     * @param plugin  Plugin sender.
     */
    public static void sendPluginMessage(ChatColor color, String message, Plugin plugin) {
        sendMessage(color + "[" + plugin.getName() + "] " + message);
    }

    /**
     * Writes to console the desired formatted message.
     * <p>
     *
     * @param message Content of the message.
     * @param plugin  Plugin sender.
     */
    public static void sendPluginMessage(String message, Plugin plugin) {
        sendMessage("[" + plugin.getName() + "] " + message);
    }

    /**
     * Writes to console the desired formatted message.
     * <p>
     *
     * @param color   Color of the format.
     * @param message Content of the message.
     * @param alias   Plugin sender alias.
     */
    public static void sendPluginMessage(ChatColor color, String message, String alias) {
        sendMessage(color + "[" + alias + "] " + message);
    }

    /**
     * Writes to console the desired formatted message.
     * <p>
     *
     * @param message Content of the message.
     * @param alias   Plugin sender alias.
     */
    public static void sendPluginMessage(String message, String alias) {
        sendMessage("[" + alias + "] " + message);
    }

}
