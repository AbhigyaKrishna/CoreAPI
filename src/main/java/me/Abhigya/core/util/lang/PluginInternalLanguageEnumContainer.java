package me.Abhigya.core.util.lang;

import org.bukkit.plugin.Plugin;

import java.util.Locale;

/**
 * Like the name says, this is a container for the different internal languages
 * that a {@link Plugin} may support.
 * The {@link Plugin} loads the configuration of a specific language and saves
 * it on a {@link PluginInternalLanguageEnumContainer}, then the plugin can use that language
 * for internal process.
 * Note that the {@link PluginInternalLanguageEnumContainer}s must have only the internal language
 * of the plugin, the containers are not intended to be configured externally in any way!
 */
public interface PluginInternalLanguageEnumContainer {

    /**
     * Returns the key (or path) where the
     * value of this container item can be found.
     * <p>
     *
     * @return Key (or path)
     */
    public String getKey();

    /**
     * Gets the value of the specific given {@link Locale},
     * or null if the given locale is not supported.
     * <p>
     *
     * @param locale Language locale
     * @return Value of the locale or null if not supported
     */
    public String getValue(Locale locale);

    /**
     * Sets the value for the specified {@link Locale}.
     * <p>
     *
     * @param value  Value of the given locale
     * @param locale Language identifier of this item
     */
    public void setValue(String value, Locale locale);

}
