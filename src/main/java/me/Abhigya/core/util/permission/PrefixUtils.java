package me.Abhigya.core.util.permission;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Useful class for getting the prefix of a player. Supported permission
 * plugins:
 * <ul>
 * <li> PermissionsEx </li>
 * <li> PowerfulPerms </li>
 * <li> LuckPerms </li>
 * </ul>
 */
public class PrefixUtils {

    /**
     * Gets the next prefix of player defined by the permission plugin
     * <p>
     *
     * @param player Player of whom to get prefix from permission plugin
     * @return Next defined prefix by the permission plugin
     */
    public static String getNextAvailablePrefix(Player player) {
        List<String> list = getAvailablePrefixes(player);
        return list.isEmpty() ? null : list.get(0);
    }

    /**
     * Gets the prefix of player defined by the permission plugin
     * <p>
     *
     * @param player Player of whom to get prefix from permission plugin
     * @return Defined prefix by the permission plugin
     */
    public static List<String> getAvailablePrefixes(Player player) {
        List<String> prefixes = new ArrayList<>();
        for (String prefix : new String[]
                {
                        getPermissionsExPrefix(player),
                        getPowerfulPermsPrefix(player),
                        getLuckPermsPrefix(player),
                }) {
            if (prefix != null) {
                prefixes.add(prefix);
            }
        }
        return prefixes;
    }

    /**
     * Gets the prefix of player from PermissionEx plugin
     * <p>
     *
     * @param player Required player
     * @return Prefix of the player
     */
    public static String getPermissionsExPrefix(Player player) {
        try {
            return ru.tehkode.permissions.bukkit.PermissionsEx.getUser(player).getPrefix();
        } catch (Throwable ex) {
            return null;
        }
    }

    /**
     * Gets the prefix of player from PowerfulPerms plugin
     * <p>
     *
     * @param player Required player
     * @return Prefix of the player
     */
    public static String getPowerfulPermsPrefix(Player player) {
        try {
            return com.github.gustav9797.PowerfulPerms.PowerfulPerms.getPlugin().getPermissionManager()
                    .getPermissionPlayer(player.getUniqueId()).getPrefix();
        } catch (Throwable ex) {
            return null;
        }
    }

    /**
     * Gets the prefix of player from LuckPerms plugin
     * <p>
     *
     * @param player Required player
     * @return Prefix of the player
     */
    public static String getLuckPermsPrefix(Player player) {
        try {
            return net.luckperms.api.LuckPermsProvider.get().getUserManager().getUser(player.getUniqueId())
                    .getCachedData().getMetaData().getPrefix();
        } catch (Throwable ex) {
            return null;
        }
    }

}
