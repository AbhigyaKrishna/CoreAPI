package me.Abhigya.core.util.math.target;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

/**
 * An implementation of {@link TargetFilterType} for filtering entities of type
 * {@link Player}.
 */
public class TargetFilterPlayer extends TargetFilterType {

    /**
     * Constructs the target filter.
     */
    public TargetFilterPlayer() {
        super(EntityType.PLAYER);
    }

}
