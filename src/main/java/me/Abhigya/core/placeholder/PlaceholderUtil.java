package me.Abhigya.core.placeholder;

import me.Abhigya.core.main.CoreAPI;
import me.Abhigya.core.placeholder.events.PlaceholderManagerHookEvent;
import me.Abhigya.core.placeholder.managers.CustomPlaceholderManager;
import me.Abhigya.core.placeholder.managers.PAPIPlaceholderManager;
import me.Abhigya.core.util.PluginUtils;
import me.Abhigya.core.util.console.ConsoleUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public final class PlaceholderUtil {

    private static PlaceholderManager manager = null;
    private static boolean fallback = false;

    private PlaceholderUtil() {
    }

    public static PlaceholderValue<Byte> parseByte(Object obj) {
        if (obj instanceof Number) {
            return PlaceholderValue.fake(((Number) obj).byteValue());
        } else if (obj instanceof String)
            return PlaceholderValue.byteValue((String) obj);
        else return null;
    }

    public static PlaceholderValue<Short> parseShort(Object obj) {
        if (obj instanceof Number) {
            return PlaceholderValue.fake(((Number) obj).shortValue());
        } else if (obj instanceof String)
            return PlaceholderValue.shortValue((String) obj);
        else return null;
    }

    public static PlaceholderValue<Integer> parseInt(Object obj) {
        if (obj instanceof Number) {
            return PlaceholderValue.fake(((Number) obj).intValue());
        } else if (obj instanceof String)
            return PlaceholderValue.intValue((String) obj);
        else return null;
    }

    public static PlaceholderValue<Long> parseLong(Object obj) {
        if (obj instanceof Number) {
            return PlaceholderValue.fake(((Number) obj).longValue());
        } else if (obj instanceof String)
            return PlaceholderValue.longValue((String) obj);
        else return null;
    }

    public static PlaceholderValue<Float> parseFloat(Object obj) {
        if (obj instanceof Number) {
            return PlaceholderValue.fake(((Number) obj).floatValue());
        } else if (obj instanceof String)
            return PlaceholderValue.floatValue((String) obj);
        else return null;
    }

    public static PlaceholderValue<Double> parseDouble(Object obj) {
        if (obj instanceof Number) {
            return PlaceholderValue.fake(((Number) obj).doubleValue());
        } else if (obj instanceof String)
            return PlaceholderValue.doubleValue((String) obj);
        else return null;
    }

    public static void tryHook(Plugin plugin) {
        PlaceholderManagerHookEvent event = new PlaceholderManagerHookEvent();
        Bukkit.getPluginManager().callEvent(event);
        if (event.getPlaceholderManager() != null) {
            manager = event.getPlaceholderManager();
            ConsoleUtils.sendPluginMessage(ChatColor.GREEN, "Successfully hooked with custom PlaceholderManager", plugin);
        } else if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            manager = new PAPIPlaceholderManager();
            ConsoleUtils.sendPluginMessage(ChatColor.GREEN, "Successfully hooked into PlaceholderAPI", plugin);
        } else {
            manager = new CustomPlaceholderManager();
            ConsoleUtils.sendPluginMessage(ChatColor.YELLOW, "Cannot find PlaceholderAPI, using internal system.", plugin);
            fallback = true;
            PluginUtils.onPluginLoaded("PlaceholderAPI", PlaceholderUtil::onPapiEnable);
        }
    }

    public static void onPapiEnable(Plugin plugin) {
        if (!fallback) return; // Another manager has been registered (and it is not a fallback)
        ConsoleUtils.sendMessage(ChatColor.YELLOW + "Late hooking into PlaceholderAPI");
        manager = new PAPIPlaceholderManager();
    }

    public static void register(Plugin plugin, Placeholder placeholder) {
        manager.register(plugin, placeholder);
    }

    public static PlaceholderValue<String> process(String message) {
        message = ChatColor.translateAlternateColorCodes('&', message);
        return PlaceholderValue.stringValue(message);
    }

    public static String resolve(Player player, String str) {
        return manager.apply(player, str);
    }

    public static String resolve(Player player, String str, PlaceholderRegistry<?> local) {
        return manager.apply(player, str, local);
    }

    public static String placeholder(Player player, String str) {
        return manager.apply(player, str);
    }

    public static boolean hasPlaceholders(String str) {
        return manager.hasPlaceholders(str);
    }

    public static PlaceholderRegistry<?> getRegistry() {
        return manager.getRegistry();
    }

    public static PlaceholderManager getManager() {
        return manager;
    }

    public static void setManager(PlaceholderManager manager) {
        PlaceholderUtil.manager = manager;
    }
}
