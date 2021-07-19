package me.Abhigya.core.util.permission;

import org.bukkit.Bukkit;
import org.bukkit.permissions.Permission;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Simple class for managing Permissions.
 */
public class Permissions {

    private static final Map<String, Permission> PERMISSIONS = new HashMap<>();

    /**
     * Returns permissions of a player.
     * <p>
     *
     * @param name Name of the player
     * @return Permissions of the player
     */
    public static Permission of(String name) {
        name = name.toLowerCase(Locale.ENGLISH);
        if (!Permissions.PERMISSIONS.containsKey(name)) {
            Permissions.PERMISSIONS.put(name, new Permission(name));
        }
        return Permissions.PERMISSIONS.get(name);
    }

    /**
     * Register a permission in permissions.yml.
     * <p>
     *
     * @param permission Permission to register
     */
    public static void register(Permission permission) {
        if (!Permissions.isAlreadyDefined(permission)) {
            Bukkit.getPluginManager().addPermission(permission);
            permission.recalculatePermissibles();
        }
    }

    /**
     * Checks whether the permission is already defined.
     * <p>
     *
     * @param permission Permission to check
     * @return true if present, else false
     */
    public static boolean isAlreadyDefined(String permission) {
        return Bukkit.getPluginManager().getPermission(permission) != null;
    }

    /**
     * Checks whether the permission is already defined.
     * <p>
     *
     * @param permission Permission to check
     * @return true if present, else false
     */
    public static boolean isAlreadyDefined(Permission permission) {
        return Permissions.isAlreadyDefined(permission.getName());
    }

}
