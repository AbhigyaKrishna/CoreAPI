package me.Abhigya.core.main;

import me.Abhigya.core.item.ActionItemHandler;
import me.Abhigya.core.metrics.MetricsAdaptor;
import me.Abhigya.core.plugin.Plugin;
import me.Abhigya.core.plugin.PluginAdapter;
import me.Abhigya.core.util.console.ConsoleUtils;
import me.Abhigya.core.util.world.GameRuleHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.ServicePriority;

/**
 * Api main class
 */
public final class CoreAPI extends PluginAdapter {

    @Override
    protected boolean setUp() {
        Bukkit.getServicesManager().register(CoreAPI.class, getInstance(), getInstance(), ServicePriority.Highest);
        ConsoleUtils.sendPluginMessage(ChatColor.GREEN, "Plugin Started", this);
        return true;
    }

    @Override
    public MetricsAdaptor getMetrics() {
        return new Metrics(this, 11582);
    }

    @Override
    protected boolean setUpHandlers() {
        new GameRuleHandler(this);
        new ActionItemHandler(this);
        return true;
    }

    /**
     * Gets the {@link CoreAPI} plugin instance.
     * <p>
     *
     * @return Core instance.
     */
    public static CoreAPI getInstance() {
        return Plugin.getPlugin(CoreAPI.class);
    }

}