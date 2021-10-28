package me.Abhigya.core.util.math.target;

import me.Abhigya.core.util.math.IntersectionUtils;
import me.Abhigya.core.util.math.collision.BoundingBox;
import me.Abhigya.core.util.math.collision.Ray;
import me.Abhigya.core.util.reflection.bukkit.EntityReflection;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.*;

/** Class for calculating targets */
public class TargetUtils {

    /**
     * Gets a target entity located an the provided world, from a {@link Ray} and a range, using the
     * specified {@link TargetFilter} for filtering.
     *
     * <p>Also it is guaranteed that the closest target entity will be returned.
     *
     * <p>
     *
     * @param ray Ray
     * @param world World target entities resides in
     * @param range Range that determines how distant an entity can be to be considered as a target.
     *     {@link Integer#MAX_VALUE} can be used to consider all the entities residing in the world,
     *     but note that the performance might be affected because of more entities will be
     *     considered.
     * @param filter Filter
     * @return Target entity or null
     */
    public static Entity getTarget(Ray ray, World world, double range, TargetFilter filter) {
        Map<Double, Entity> map = new HashMap<>();
        List<Double> keys = new ArrayList<>();
        for (Entity entity : world.getEntities()) {
            if (!entity.isValid()) {
                continue;
            }

            /* checking range */
            final double distance = entity.getLocation().toVector().distance(ray.getOrigin());
            if (distance > range) {
                continue;
            }

            /* checking ray-bounds intersection */
            final BoundingBox bounds = EntityReflection.getBoundingBox(entity);
            if (!IntersectionUtils.intersectRayBoundsFast(ray, bounds)
                    && !IntersectionUtils.intersectRayBounds(ray, bounds, null)) {
                continue;
            }

            map.put(distance, entity);
            keys.add(distance);
        }

        /* ordered by distance */
        Collections.sort(keys);

        /* iteration from closest -> most far */
        for (double key : keys) {
            Entity target = map.get(key);
            if (filter == null || filter.accept(target)) {
                return target;
            }
        }
        return null;
    }

    /**
     * Gets a target entity located an the provided world, from a {@link Ray} and a range, using a
     * {@link TargetFilterClass} with the specified {@code clazz} for filtering entities by class.
     *
     * <p>Also it is guaranteed that the closest target entity will be returned.
     *
     * <p>
     *
     * @param <T> Entity class
     * @param ray Ray
     * @param world World target entities resides in.
     * @param range Range that determines how distant an entity can be to be considered as a target.
     *     {@link Integer#MAX_VALUE} can be used to consider all the entities residing in the world,
     *     but note that the performance might be affected because of more entities will be
     *     considered.
     * @param clazz Entity class for the {@link TargetFilterClass} filter
     * @return Target entity or null
     * @see TargetFilterClass
     * @see TargetUtils#getTarget(Ray, World, double, TargetFilter)
     */
    public static <T extends Entity> T getTarget(
            Ray ray, World world, double range, Class<T> clazz) {
        return clazz.cast(getTarget(ray, world, range, new TargetFilterClass<T>(clazz)));
    }

    /**
     * Gets a target entity located an the provided world, from a {@link Ray} and a range, using a
     * {@link TargetFilterType} with the specified {@code type} for filtering entities by type.
     *
     * <p>Also it is guaranteed that the closest target entity will be returned.
     *
     * <p>
     *
     * @param ray Ray
     * @param world World target entities resides in
     * @param range Range that determines how distant an entity can be to be considered as a target.
     *     {@link Integer#MAX_VALUE} can be used to consider all the entities residing in the world,
     *     but note that the performance might be affected because of more entities will be
     *     considered.
     * @param type Entity type for the {@link TargetFilterType} filter
     * @return Target entity or null
     * @see TargetFilterType
     * @see TargetUtils#getTarget(Ray, World, double, TargetFilter)
     */
    public static Entity getTarget(Ray ray, World world, double range, EntityType type) {
        return getTarget(ray, world, range, new TargetFilterType(type));
    }

    /**
     * Gets a target entity of the desired {@link Player} located in the world of the player, within
     * a range, using the specified {@link TargetFilter} for filtering.
     *
     * <p>Also it is guaranteed that the closest target entity will be returned.
     *
     * <p>
     *
     * @param player Player
     * @param range the range that determines how distant an entity can be to be considered as a
     *     target. {@link Integer#MAX_VALUE} can be used to consider all the entities residing in
     *     the world, but note that the performance might be affected because of more entities will
     *     be considered.
     * @param filter Filter
     * @return Target entity or null
     * @see TargetUtils#getTarget(Ray, World, double, TargetFilter)
     */
    public static Entity getTarget(Player player, double range, TargetFilter filter) {
        return getTarget(
                new Ray(player.getLocation()),
                player.getWorld(),
                range,
                new TargetFilterMultiplexer(new TargetFilterSelf(player), filter));
    }

    /**
     * Gets a target entity of the desired {@link Player} located in the world of the player, within
     * a range, using a {@link TargetFilterClass} with the specified {@code clazz} for filtering
     * entities by class.
     *
     * <p>Also it is guaranteed that the closest target entity will be returned.
     *
     * <p>
     *
     * @param <T> Entity class
     * @param player Player
     * @param range Range that determines how distant an entity can be to be considered as a target.
     *     {@link Integer#MAX_VALUE} can be used to consider all the entities residing in the world,
     *     but note that the performance might be affected because of more entities will be
     *     considered.
     * @param clazz Entity class for the {@link TargetFilterClass} filter
     * @return Target entity or null
     * @see TargetFilterClass
     * @see TargetUtils#getTarget(Ray, World, double, TargetFilter)
     */
    public static <T extends Entity> T getTarget(Player player, double range, Class<T> clazz) {
        return clazz.cast(getTarget(player, range, new TargetFilterClass<T>(clazz)));
    }

    /**
     * Gets a target entity of the desired {@link Player} located in the world of the player, using
     * a {@link TargetFilterType} with the specified {@code type} for filtering entities by type.
     *
     * <p>Also it is guaranteed that the closest target entity will be returned.
     *
     * <p>
     *
     * @param player Player
     * @param range Range that determines how distant an entity can be to be considered as a target.
     *     {@link Integer#MAX_VALUE} can be used to consider all the entities residing in the world,
     *     but note that the performance might be affected because of more entities will be
     *     considered.
     * @param type Entity type for the {@link TargetFilterType} filter
     * @return Target entity or null
     * @see TargetFilterType
     * @see TargetUtils#getTarget(Ray, World, double, TargetFilter)
     */
    public static Entity getTarget(Player player, double range, EntityType type) {
        return getTarget(player, range, new TargetFilterType(type));
    }

    /**
     * Gets a target player of the desired {@link Player} located in the world of the player. This
     * uses a {@link TargetFilterType} with the {@link EntityType#PLAYER} for filtering.
     *
     * <p>Also it is guaranteed that the closest target player will be returned.
     *
     * <p>
     *
     * @param player Player
     * @param range Range that determines how distant a player can be to be considered as a target.
     *     {@link Integer#MAX_VALUE} can be used to consider all the entities residing in the world,
     *     but note that the performance might be affected because of more entities will be
     *     considered.
     * @return Target player or null
     * @see TargetUtils#getTarget(Player, double, EntityType)
     */
    public static Player getTargetPlayer(Player player, double range) {
        return (Player) getTarget(player, range, EntityType.PLAYER);
    }

    /**
     * An implementation of {@link TargetFilter} to avoid a specified {@link Player} to be
     * considered when looking for a target entity.
     */
    protected static class TargetFilterSelf implements TargetFilter {

        /** Self player */
        protected final Player self;

        /**
         * Constructs the filter.
         *
         * <p>
         *
         * @param self Self player
         */
        public TargetFilterSelf(Player self) {
            this.self = self;
        }

        @Override
        public boolean accept(Entity entity) {
            return !(entity instanceof Player) || !entity.getUniqueId().equals(self.getUniqueId());
        }
    }

    /**
     * A {@link TargetFilter} that delegates to an ordered array of other filters. Delegation for a
     * filter stops if a filter returns {@code false}.
     */
    protected static class TargetFilterMultiplexer implements TargetFilter {

        /** Array of filters */
        protected final TargetFilter[] filters;

        /**
         * Construct the filter.
         *
         * <p>
         *
         * @param filters Filters children
         */
        public TargetFilterMultiplexer(TargetFilter... filters) {
            this.filters = filters;
        }

        @Override
        public boolean accept(Entity entity) {
            for (TargetFilter filter : filters) {
                if (!filter.accept(entity)) {
                    return false;
                }
            }
            return true;
        }
    }
}
