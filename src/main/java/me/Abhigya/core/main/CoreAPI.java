package me.Abhigya.core.main;

import io.github.retrooper.packetevents.PacketEvents;
import me.Abhigya.core.item.ActionItemHandler;
import me.Abhigya.core.metrics.MetricsAdaptor;
import me.Abhigya.core.plugin.Plugin;
import me.Abhigya.core.plugin.PluginAdapter;
import me.Abhigya.core.util.server.Version;
import me.Abhigya.core.util.world.GameRuleHandler;
import org.bukkit.Bukkit;
import org.bukkit.plugin.ServicePriority;

/**
 * Api main class
 */
public final class CoreAPI extends PluginAdapter {

    private Version serverVersion;

    /**
     * Gets the {@link CoreAPI} plugin instance.
     * <p>
     *
     * @return Core instance.
     */
    public static CoreAPI getInstance( ) {
        return Plugin.getPlugin( CoreAPI.class );
    }

    @Override
    public void onLoad( ) {
        PacketEvents.create( this );
        PacketEvents.get( ).loadAsyncNewThread( );
    }

    @Override
    protected boolean setUp( ) {
        PacketEvents.get( ).init( );
        Bukkit.getServicesManager( ).register( CoreAPI.class, getInstance( ), getInstance( ), ServicePriority.Highest );
        this.serverVersion = Version.getServerVersion( );
        return true;
    }

    @Override
    public MetricsAdaptor getMetrics( ) {
        return new Metrics( this, 11582 );
    }

    @Override
    protected boolean setUpHandlers( ) {
        new GameRuleHandler( this );
        new ActionItemHandler( this );
        return true;
    }

    public Version getServerVersion( ) {
        return serverVersion;
    }

}
