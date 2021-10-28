package me.Abhigya.core.economy;

import me.Abhigya.core.main.CoreAPI;
import me.Abhigya.core.util.PluginUtils;
import me.Abhigya.core.util.console.ConsoleUtils;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.RegisteredServiceProvider;

/** Class for dealing with Vault economy */
public class EconomyManager {

    private static boolean enabled = false;
    private static Economy economy;

    /** Enables the economy manager and hooks into Vault */
    public static void enable() {
        ConsoleUtils.sendPluginMessage(
                ChatColor.YELLOW,
                "Disabling economy until vault loads",
                CoreAPI.getInstance().getHandlingPlugin());
        enabled = false;

        PluginUtils.onPluginLoaded(
                "Vault",
                p -> {
                    RegisteredServiceProvider<Economy> rsp =
                            Bukkit.getServicesManager().getRegistration(Economy.class);
                    if (rsp == null) {
                        ConsoleUtils.sendPluginMessage(
                                ChatColor.RED,
                                "Cannot find any economy service, economy not supported",
                                CoreAPI.getInstance().getHandlingPlugin());
                        enabled = false;
                        return;
                    }
                    enabled = true;
                    economy = rsp.getProvider();
                });
    }

    /**
     * Gets the Vault Economy class if enabled
     *
     * <p>
     *
     * @return {@link Economy} if economy enabled otherwise null
     */
    public static Economy getEconomy() {
        return enabled ? economy : null;
    }

    /**
     * Gets the {@link Balance} of a player
     *
     * <p>
     *
     * @param player Player to get balance of
     * @return {@link Balance} of the given player
     */
    public static Balance get(OfflinePlayer player) {
        return economy == null ? null : new Balance(player);
    }

    /**
     * Format amount into a human readable String. This provides translation into economy specific
     * formatting to improve consistency between plugins.
     *
     * <p>
     *
     * @param v Amount to format
     * @return Formatted amount
     */
    public static String format(double v) {
        return economy.format(v);
    }

    /**
     * Returns if economy system enabled successfully
     *
     * <p>
     *
     * @return {@code true} if Vault hook was successful, false otherwise
     */
    public static boolean isEnabled() {
        return enabled;
    }
}
