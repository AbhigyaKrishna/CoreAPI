package me.Abhigya.core.main;

import io.github.retrooper.packetevents.PacketEvents;
import me.Abhigya.core.item.ActionItemHandler;
import me.Abhigya.core.plugin.Plugin;
import me.Abhigya.core.util.server.Version;
import me.Abhigya.core.util.world.GameRuleHandler;
import org.bukkit.Bukkit;
import org.bukkit.plugin.ServicePriority;

/** Api main class */
public final class CoreAPI {

    private static CoreAPI instance;

    private final Plugin plugin;
    private Version serverVersion;

    public static void init(Plugin plugin) {
        if (CoreAPI.instance != null)
            throw new IllegalStateException("CoreAPI is already created!");

        CoreAPI.instance = new CoreAPI(plugin);
        Bukkit.getServicesManager()
                .register(CoreAPI.class, CoreAPI.instance, plugin, ServicePriority.High);
        PacketEvents.create(plugin);
        PacketEvents.get().loadAsyncNewThread();
    }

    public static CoreAPI getInstance() {
        return CoreAPI.instance;
    }

    CoreAPI(Plugin plugin) {
        this.plugin = plugin;
    }

    public void load() {
        PacketEvents.get().init();
        this.serverVersion = Version.getServerVersion();
        new GameRuleHandler(this);
        new ActionItemHandler(this);

        Metrics metrics = new Metrics(this, 11582);
        metrics.register();
    }

    public Plugin getHandlingPlugin() {
        return plugin;
    }

    public Version getServerVersion() {
        return serverVersion;
    }
}
