package me.Abhigya.core.util.math.target;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

/**
 * An implementation of {@link TargetFilter} for filtering entities with an
 * {@link EntityType}.
 */
public class TargetFilterType implements TargetFilter {

    /**
     * The entity type
     */
    protected final EntityType type;

    /**
     * Constructs the target filter.
     * <p>
     *
     * @param type Entity type to filter
     */
    public TargetFilterType(EntityType type) {
        this.type = type;
    }

    @Override
    public boolean accept(Entity entity) {
        return type.equals(entity.getType());
    }

}
