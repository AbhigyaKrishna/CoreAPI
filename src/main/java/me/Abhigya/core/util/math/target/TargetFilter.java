package me.Abhigya.core.util.math.target;

import org.bukkit.entity.Entity;

/** A filter intended for finding a desired {@link Entity}. */
public interface TargetFilter {

    /**
     * Tests whether or not the specified entity is the desired one.
     *
     * <p>
     *
     * @param entity Entity to be tested
     * @return <code>true</code> if and only if the <code>entity</code> is the desired one.
     */
    public boolean accept(Entity entity);
}
