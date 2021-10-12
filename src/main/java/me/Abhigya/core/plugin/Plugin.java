package me.Abhigya.core.plugin;

import me.Abhigya.core.metrics.MetricsAdaptor;
import me.Abhigya.core.util.console.ConsoleUtils;
import me.Abhigya.core.util.reflection.general.ClassReflection;
import me.Abhigya.core.version.CoreVersion;
import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

/**
 * An implementation of {@link JavaPlugin} that adds some useful utilities.
 */
public abstract class Plugin extends JavaPlugin {

    protected String compilation_id;

    @Override
    public final void onEnable( ) {
        /* checking the required core version */
//        if ( getRequiredCoreVersion( ) != null
//                && CoreVersion.getCoreVersion( ).isOlder( getRequiredCoreVersion( ) ) ) {
//            ConsoleUtils.sendPluginMessage( ChatColor.RED, "Obsolete core version! A core version newer than or equal to "
//                    + getRequiredCoreVersion( ).name( ) + " is required!", this );
//            Bukkit.getPluginManager( ).disablePlugin( this );
//            return;
//        }

        /* checking the plugin dependencies */
        if ( getDependences( ) != null && getDependences( ).length > 0 ) {
            for ( PluginDependence dependence : getDependences( ) ) {
                final org.bukkit.plugin.Plugin plugin = Bukkit.getPluginManager( ).getPlugin( dependence.getName( ) );
                final Boolean result = dependence.apply( plugin );

                if ( result == null || result == false ) {
                    Bukkit.getPluginManager( ).disablePlugin( this );
                    return;
                }
            }
        }

        /* checking metrics */
        if ( getMetrics( ) != null ) {
            MetricsAdaptor metrics = getMetrics( );
            metrics.register( );
        }

        /* plugin setup */
        if ( !setUp( ) || !isEnabled( ) ) {
            Bukkit.getPluginManager( ).disablePlugin( this );
            return;
        }

        /* finalizing plugin setup */
        try {
            this.setUpConfig( );
            this.setUpHandlers( );
            this.setUpCommands( );
            this.setUpListeners( );
        } catch ( Throwable ex ) {
            // any exception will disable the plugin
            ex.printStackTrace( );
            Bukkit.getPluginManager( ).disablePlugin( this );
        }
    }

    /**
     * Setups this plugin. This methods will be called for the initialization of
     * this plugin after checking the required core version, and the dependences.
     * <p>
     * Also this methods should return <strong>{@code true}</strong> if the
     * initialization was successfully, and <strong>{@code false}</strong> if not.
     * Returning <strong>{@code false}</strong> from this method means that the
     * initialization was unsuccessfully, then the plugin will be disabled
     * automatically.
     * <p>
     *
     * @return true if the initialization was successfully.
     */
    protected abstract boolean setUp( );

    /**
     * Gets the metrics adaptor and setup the metrics with the graphs accordingly.
     * <p>
     * This method might return <strong>{@code null}</strong> if no metrics is
     * required.
     * <p>
     *
     * @return The required metrics adaptor, or null if not required.
     */
    public abstract MetricsAdaptor getMetrics( );

    /**
     * Gets the plugins on which this plugin depends.
     * <p>
     * This methods might return an empty array or <strong>{@code null}</strong> if
     * this plugin doesn't depend on another.
     * <p>
     *
     * @return The dependencies or null if this plugin doesn't depend on another.
     * @see PluginDependence
     */
    public abstract PluginDependence[] getDependences( );

    /**
     * This method should setups the configuration.
     * <p>
     * Also this should return <strong>{@code true}</strong> if the configuration
     * was loaded successfully, and <strong>{@code false}</strong> if not.
     * <p>
     * Note that if this methods returns <strong>{@code false}</strong>, then
     * the plugin will be disabled automatically.
     * <p>
     * Note that this methods will be called after:
     * <ul>
     * <li> {@link #setUp()}
     * </ul>
     * <p>
     *
     * @return whether the configuration was loaded successfully.
     */
    protected abstract boolean setUpConfig( );

    /**
     * This method should setups the plugin handlers.
     * <p>
     * Also this should return <strong>{@code true}</strong> if the initialization
     * of the handlers was successfully, and <strong>{@code false}</strong> if not.
     * <p>
     * Note that if this methods returns <strong>{@code false}</strong>, then
     * the plugin will be disabled automatically.
     * <p>
     * Note that this methods will be called after:
     * <ul>
     * <li> {@link #setUp()}
     * <li> {@link #setUpConfig()}
     * </ul>
     * <p>
     *
     * @return Whether the initialization of the handlers was successfully.
     */
    protected abstract boolean setUpHandlers( );

    /**
     * This method should setups the commands of the plugin.
     * <p>
     * Also this should return <strong>{@code true}</strong> if the initialization
     * of the commands was successfully, and <strong>{@code false}</strong> if not.
     * <p>
     * Note that if this methods returns <strong>{@code false}</strong>, then
     * the plugin will be disabled automatically.
     * <p>
     * Note that this methods will be called after:
     * <ul>
     * <li> {@link #setUp()}
     * <li> {@link #setUpConfig()}
     * <li> {@link #setUpHandlers()}
     * </ul>
     * <p>
     *
     * @return Whether the initialization of the commands was successfully.
     */
    protected abstract boolean setUpCommands( );

    /**
     * This method should setups the listeners of the plugin.
     * <p>
     * Also this should return <strong>{@code true}</strong> if the initialization
     * of the listeners was successfully, and <strong>{@code false}</strong> if not.
     * <p>
     * Note that if this methods returns <strong>{@code false}</strong>, then
     * the plugin will be disabled automatically.
     * <p>
     * Note that this methods will be called after:
     * <ul>
     * <li> {@link #setUp()}
     * <li> {@link #setUpConfig()}
     * <li> {@link #setUpHandlers()}
     * <li> {@link #setUpCommands()}
     * </ul>
     *
     * @return Whether the initialization of the listeners was successfully.
     */
    protected abstract boolean setUpListeners( );

    /**
     * Loads all the listeners within the provided package.
     * <p>
     * A listener within the provided package will be registered only when the
     * following conditions are met:
     * <ul>
     * <li>The class implements the interface {@link Listener}.
     * <li>The class is not <strong>{@code abstract}</strong>.
     * <li>The class has a public constructor with the class of this plugin as parameter.
     * </ul>
     * In other case, the listener will be ignored.
     * <p>
     * Implementation example:
     *
     * <h1><strong>Plugin Class:</strong></h1>
     * <pre><code>
     * public class MyPlugin extends Plugin { <br>
     * &nbsp;&nbsp;&nbsp;&nbsp;..... <br>
     * }
     * </code></pre>
     *
     * <h1><strong>Listener Class:</strong></h1>
     * <pre><code>
     * public class MyListener implements Listener { <br>
     *
     * &nbsp;&nbsp;&nbsp;&nbsp;public MyListener(MyPlugin plugin) { <br>
     * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Bukkit.getPluginManager().registerEvents(this, plugin); <br>
     * &nbsp;&nbsp;&nbsp;&nbsp;} <br>
     * }
     * </code></pre>
     * <p>
     *
     * @param packaje The listeners package
     * @throws InvocationTargetException exception to handle
     * @throws IllegalArgumentException  exception to handle
     * @throws IllegalAccessException    exception to handle
     * @throws InstantiationException    exception to handle
     */
    protected void setUpListenersPackage( String packaje ) throws InvocationTargetException, InstantiationException,
            IllegalAccessException {
        for ( Class< ? extends Listener > clazz : ClassReflection.getClasses( packaje, Listener.class ) ) {
            if ( Modifier.isAbstract( clazz.getModifiers( ) )
                    || !Listener.class.isAssignableFrom( clazz ) ) {
                continue;
            }

            Constructor< ? > constructor;
            try {
                constructor = clazz.getConstructor( getClass( ) );
            } catch ( NoSuchMethodException | SecurityException ex ) {
                continue;
            }

            constructor.newInstance( this );
        }
    }

    /**
     * Saves the raw contents of any resource embedded with this plugin's .jar file
     * assuming it can be found using {@link #getResource(String)}.
     * <p>
     * The resource is saved into the desired {@code out_directory}.
     * <p>
     *
     * @param resource_path The embedded resource path to look for within the
     *                      plugin's .jar file. (No preceding slash).
     * @param out_directory The directory into which the resource will be saved.
     * @param replace       If true, the embedded resource will overwrite the
     *                      contents of an existing file.
     */
    public void saveResource( String resource_path, File out_directory, boolean replace ) {
        InputStream input = getResource( resource_path = resource_path.replace( '\\', '/' ) );
        if ( input != null ) {
            File out = new File( out_directory,
                    resource_path.lastIndexOf( '/' ) != -1
                            ? resource_path.substring( resource_path.lastIndexOf( '/' ) + 1 )
                            : resource_path );

            if ( !out.exists( ) || replace ) {
                try {
                    FileUtils.copyInputStreamToFile( input, out );
                } catch ( IOException ex ) {
                    ConsoleUtils.sendPluginMessage( ChatColor.RED,
                            "Couldn't save resource " + resource_path + " to " + out, this );
                    ex.printStackTrace( );
                }
            }
        } else {
            throw new IllegalArgumentException(
                    "The embedded resource '" + resource_path + "' cannot be found in " + getFile( ) );
        }
    }

    protected String getCompilationId( ) {
        if ( compilation_id == null ) {
            YamlConfiguration plugin = YamlConfiguration.loadConfiguration(
                    new InputStreamReader( getResource( "plugin.yml" ) ) );

            compilation_id = plugin.getString( "compid", String.valueOf( 0 ) );
        }

        return compilation_id;
    }

}
