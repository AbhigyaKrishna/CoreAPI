package me.Abhigya.core.main;

import me.Abhigya.core.item.ActionItemHandler;
import me.Abhigya.core.plugin.Plugin;
import me.Abhigya.core.plugin.PluginAdapter;
import me.Abhigya.core.util.console.ConsoleUtils;
import me.Abhigya.core.util.world.GameRuleHandler;
import org.bukkit.ChatColor;

/**
 * Api main class
 */
public final class Main extends PluginAdapter {

    @Override
    protected boolean setUp() {
        ConsoleUtils.sendPluginMessage(ChatColor.GREEN, "Plugin Started", this);
        return true;
    }

    private static String format(String suppose_version) {
        return ("v" + suppose_version.trim().toLowerCase().replace('.', '_').replace('v', (char) 0));
    }

    @Override
    protected boolean setUpHandlers() {
        new GameRuleHandler(this);
        new ActionItemHandler(this);
        return true;
    }

    /**
     * Gets the {@link Main} plugin instance.
     * <p>
     *
     * @return Core instance.
     */
    public static Main getInstance() {
        return Plugin.getPlugin(Main.class);
    }

}
