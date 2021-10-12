package me.Abhigya.core.main;

import me.Abhigya.core.plugin.Plugin;
import me.Abhigya.core.plugin.PluginAdapter;

public final class Main extends PluginAdapter {

    /**
     * Gets the {@link CoreAPI} plugin instance.
     * <p>
     *
     * @return Core instance.
     */
    public static Main getInstance( ) {
        return Plugin.getPlugin( Main.class );
    }

    @Override
    public void onLoad( ) {
        CoreAPI.init( this );
    }

    @Override
    protected boolean setUp( ) {
        CoreAPI.getInstance( ).load( );
        return true;
    }

}
