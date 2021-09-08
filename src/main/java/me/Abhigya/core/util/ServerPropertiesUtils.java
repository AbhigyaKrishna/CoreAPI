package me.Abhigya.core.util;

import me.Abhigya.core.util.reflection.general.ClassReflection;
import me.Abhigya.core.util.reflection.general.FieldReflection;
import me.Abhigya.core.util.reflection.general.MethodReflection;

import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

/**
 * Class for dealing with the server properties (Stored in "server.properties" file).
 */
public class ServerPropertiesUtils {

    private static final Properties PROPERTIES;

    static {
        Properties properties = null;
        try {
            Object server = MethodReflection.invoke(MethodReflection.getAccessible(
                    ClassReflection.getNmsClass("MinecraftServer", ""), "getServer"), (Object) null);
            Object property_manager = FieldReflection.getValue(server, "propertyManager");

            // then getting properties.
            properties = FieldReflection.getValue(property_manager, "properties");
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
                | NoSuchMethodException | SecurityException | NoSuchFieldException ex) {
            ex.printStackTrace();
        }

        PROPERTIES = properties;
    }

    /**
     * Get property from server.properties file with String type
     * <p>
     *
     * @param key           Keyword to get the value
     * @param default_value Default value if not found
     * @return Value of the key
     */
    public static String getStringProperty(String key, String default_value) {
        try {
            return PROPERTIES.getProperty(key, default_value);
        } catch (Throwable ex) {
            return default_value;
        }
    }

    /**
     * Get property from server.properties file with Integer type
     * <p>
     *
     * @param key           Keyword to get the value
     * @param default_value Default value if not found
     * @return Value of the key
     */
    public static int getIntProperty(String key, int default_value) {
        try {
            return Integer.parseInt(PROPERTIES.getProperty(key, String.valueOf(default_value)));
        } catch (Throwable ex) {
            return default_value;
        }
    }

    /**
     * Get property from server.properties file with Long type
     * <p>
     *
     * @param key           Keyword to get the value
     * @param default_value Default value if not found
     * @return Value of the key
     */
    public static long getLongProperty(String key, long default_value) {
        try {
            return Long.parseLong(PROPERTIES.getProperty(key, String.valueOf(default_value)));
        } catch (Throwable ex) {
            return default_value;
        }
    }

    /**
     * Get property from server.properties file with Boolean type
     * <p>
     *
     * @param key           Keyword to get the value
     * @param default_value Default value if not found
     * @return Value of the key
     */
    public static boolean getBooleanProperty(String key, boolean default_value) {
        try {
            return Boolean.parseBoolean(PROPERTIES.getProperty(key, String.valueOf(default_value)));
        } catch (Throwable ex) {
            return default_value;
        }
    }
}
