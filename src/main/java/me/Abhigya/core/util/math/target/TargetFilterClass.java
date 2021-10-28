package me.Abhigya.core.util.math.target;

import org.bukkit.entity.Enderman;
import org.bukkit.entity.Entity;

/**
 * An implementation of {@link TargetFilter} for filtering entities with an entity class.
 *
 * <p>
 *
 * @param <T> the entity class, for example: {@link Enderman}.
 */
public class TargetFilterClass<T extends Entity> implements TargetFilter {

    /** The class extending {@link Entity} */
    protected final Class<T> clazz;

    /**
     * Construct the target filter.
     *
     * <p>
     *
     * @param clazz Entity class
     */
    public TargetFilterClass(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public boolean accept(Entity entity) {
        return clazz.isAssignableFrom(entity.getClass());
    }
}
