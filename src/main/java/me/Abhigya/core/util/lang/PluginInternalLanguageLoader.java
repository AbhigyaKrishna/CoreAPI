package me.Abhigya.core.util.lang;

import me.Abhigya.core.plugin.Plugin;
import me.Abhigya.core.util.StringUtils;
import me.Abhigya.core.util.console.ConsoleUtils;
import org.apache.commons.lang.Validate;
import org.bukkit.ChatColor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Locale;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;

/**
 * Language loader for custom plugin
 */
public class PluginInternalLanguageLoader {

    protected static final String LANG_EXTENSION = ".lang";
    protected static final char VALUE_INDICATOR = ':';
    protected static final char VALUE_BRACES = '\'';
    protected static final String LOCALE_NAME_KEY = "language.name";
    protected static final String LOCALE_REGION_KEY = "language.region";
    protected static final String LOCALE_CODE_FORMAT = "%s_%s";

    private final Plugin plugin;
    private final JarFile jar;
    private final Class<? extends Enum<? extends PluginInternalLanguageEnumContainer>> container;
    private final String resources_package;

    public PluginInternalLanguageLoader(Plugin plugin, JarFile jar) {
        Validate.notNull(plugin, "The plugin cannot be null!");
        Validate.notNull(jar, "The jar cannot be null!");
        Validate.notNull(plugin.getInternalLanguageContainer(), "the language enum container of the plugin cannot be null!");
        Validate.isTrue(!org.apache.commons.lang.StringUtils.isBlank(plugin.getInternalLanguageResourcesPackage()),
                "the language resources package of the plugin cannot be blank!");

        this.plugin = plugin;
        this.jar = jar;
        this.container = plugin.getInternalLanguageContainer();
        this.resources_package = plugin.getInternalLanguageResourcesPackage();
    }

    public boolean load() {
        String package_path = resources_package.replace('\\', '/');
        for (JarEntry entry : jar.stream().filter(other -> other.getName().startsWith(package_path)).collect(Collectors.toList())) {
            if (!entry.getName().toLowerCase().endsWith(LANG_EXTENSION)) {
                continue;
            }

            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(jar.getInputStream(entry)));
                StringBuilder builder = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                    builder.append(StringUtils.LINE_SEPARATOR);
                }
                return loadFromString(entry.getName(), builder.toString());
            } catch (IOException | IllegalArgumentException ex) {
                ConsoleUtils.sendPluginMessage(ChatColor.RED, String.format(
                        "The supposed language resource '%s', inside the resouces package '%s' couldn't be loaded correctly!",
                        entry.getName(), package_path), plugin);
                ex.printStackTrace();
            }
        }
        return false;
    }

    public boolean loadFromString(String file_name, String contents) throws IllegalArgumentException {
        String[] lines = contents.split(String.valueOf(StringUtils.LINE_SEPARATOR));
        String locale_name = null;
        String locale_region = null;
        Locale locale = null;
        for (String line : lines) {
            if (StringUtils.isBlank(line)) {
                continue;
            }

            int val_indi_index = line.indexOf(VALUE_INDICATOR);
            int val_index = (val_indi_index + 1);
            if (val_indi_index == -1) {
                throw new IllegalArgumentException(
                        String.format("The line '%s' doesn't end contains the value indicator '%s'!", line,
                                String.valueOf(VALUE_INDICATOR)));
            } else if (val_index >= line.trim().length()) {
                throw new IllegalArgumentException(
                        String.format("The value of the line '%s' is invalid!", line));
            }

            String key = line.trim().substring(0, val_indi_index);
            String value = line.substring(val_index).trim();
            if (value.indexOf(VALUE_BRACES) == 0 && value.lastIndexOf(VALUE_BRACES) == (value.length() - 1)) {
                value = value.substring(1, (value.length() - 1)); // removing braces
            } else {
                throw new IllegalArgumentException(
                        String.format("Invalid format at the value of the line '%s'!", key));
            }

            if (locale == null) { // load locale
                if (key.equalsIgnoreCase(LOCALE_NAME_KEY)) {
                    locale_name = value;
                } else if (key.equalsIgnoreCase(LOCALE_REGION_KEY)) {
                    locale_region = value;
                }

                String locale_code = String.format(LOCALE_CODE_FORMAT, locale_name, locale_region);
                locale = Arrays.stream(Locale.getAvailableLocales())
                        .filter(other -> locale_code
                                .equals(String.format(LOCALE_CODE_FORMAT, other.getLanguage(), other.getCountry())))
                        .findAny().orElse(null);
            } else { // load item
                Enum<? extends PluginInternalLanguageEnumContainer> item_constant = Arrays.stream(container.getEnumConstants()).filter(
                        constant -> key.equalsIgnoreCase(((PluginInternalLanguageEnumContainer) constant).getKey()))
                        .findAny().orElse(null);
                if (item_constant != null) {
                    ((PluginInternalLanguageEnumContainer) item_constant).setValue(value, locale);
                }
            }
        }

        if (locale == null) {
            throw new IllegalArgumentException(String.format(
                    "The locale is not specified in the language file %s! (keys: '%s', '%s') (It must be specified at the start of the configuration)",
                    file_name, LOCALE_NAME_KEY, LOCALE_REGION_KEY));
        }
        return true;
    }

}
