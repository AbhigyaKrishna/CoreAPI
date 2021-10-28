package me.Abhigya.core.util.entity;

import org.bukkit.World;
import org.bukkit.entity.Entity;

import java.util.UUID;

/** Class for dealing with Bukkit {@link Entity} */
public class EntityUtils {

    /**
     * Returns the {@link Entity} associated with the given {@link UUID} and with the given type.
     *
     * <p>
     *
     * @param <T> Entity type
     * @param world World in which the entity is
     * @param type Class of the entity type
     * @param id UUID of the entity to find
     * @return {@link Entity} associated with the given {@link UUID} and with the given type, or
     *     null if could not be found.
     */
    @SuppressWarnings("unchecked")
    public static <T extends Entity> T getEntity(
            World world, Class<? extends Entity> type, UUID id) {
        return (T)
                world.getEntities().stream()
                        .filter(
                                entity ->
                                        type.isAssignableFrom(entity.getClass())
                                                && id.equals(entity.getUniqueId()))
                        .findAny()
                        .orElse(null);
    }

    /**
     * Returns the {@link Entity} associated with the given {@link UUID}.
     *
     * <p>
     *
     * @param world World in which the entity is
     * @param id UUID of the entity to find
     * @return {@link Entity} associated with the given {@link UUID}, or null if could not be found
     */
    public static Entity getEntity(World world, UUID id) {
        return world.getEntities().stream()
                .filter(entity -> id.equals(entity.getUniqueId()))
                .findAny()
                .orElse(null);
    }
}
