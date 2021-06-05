package me.Abhigya.core.util.math;

import me.Abhigya.core.util.math.collision.BoundingBox;
import me.Abhigya.core.util.math.collision.Ray;
import org.bukkit.util.Vector;

/**
 * Class offering various static methods for intersection testing between
 * different geometric objects.
 */
public class IntersectionUtils {

//	private final static Vector v0 = new Vector();
//	private final static Vector v1 = new Vector();
    private final static Vector v2 = new Vector();

    /**
     * Intersects a {@link Ray} and a {@link BoundingBox}, returning the intersection point in intersection. This intersection is
     * defined as the point on the ray closest to the origin which is within the specified bounds.
     *
     * <p>
     * The returned intersection (if any) is guaranteed to be within the bounds of the bounding box, but it can occasionally
     * diverge slightly from ray, due to small floating-point errors.
     * </p>
     *
     * <p>
     * If the origin of the ray is inside the box, this method returns true and the intersection point is set to the origin of the
     * ray, accordingly to the definition above.
     * </p>
     *
     * @param ray          The ray
     * @param box          The box
     * @param intersection The intersection point (optional)
     * @return Whether an intersection is present
     */
    public static boolean intersectRayBounds(Ray ray, BoundingBox box, Vector intersection) {
        if (box.contains(ray.getOrigin())) {
            if (intersection != null) {
                set(intersection, ray.getOrigin());
            }
            return true;
        }

        double lowest = 0, t;
        boolean hit = false;

        // min x
        if (ray.getOrigin().getX() <= box.getMinimum().getX() && ray.getDirection().getX() > 0) {
            t = (box.getMinimum().getX() - ray.getOrigin().getX()) / ray.getDirection().getX();
            if (t >= 0) {
                set(v2, ray.getDirection()).multiply(t).add(ray.getOrigin());
                if (v2.getY() >= box.getMinimum().getY() && v2.getY() <= box.getMaximum().getY()
                        && v2.getZ() >= box.getMinimum().getZ() && v2.getZ() <= box.getMaximum().getZ() && (!hit || t < lowest)) {
                    hit = true;
                    lowest = t;
                }
            }
        }
        // max x
        if (ray.getOrigin().getX() >= box.getMaximum().getX() && ray.getDirection().getX() < 0) {
            t = (box.getMaximum().getX() - ray.getOrigin().getX()) / ray.getDirection().getX();
            if (t >= 0) {
                set(v2, ray.getDirection()).multiply(t).add(ray.getOrigin());
                if (v2.getY() >= box.getMinimum().getY() && v2.getY() <= box.getMaximum().getY()
                        && v2.getZ() >= box.getMinimum().getZ() && v2.getZ() <= box.getMaximum().getZ() && (!hit || t < lowest)) {
                    hit = true;
                    lowest = t;
                }
            }
        }
        // min y
        if (ray.getOrigin().getY() <= box.getMinimum().getY() && ray.getDirection().getY() > 0) {
            t = (box.getMinimum().getY() - ray.getOrigin().getY()) / ray.getDirection().getY();
            if (t >= 0) {
                set(v2, ray.getDirection()).multiply(t).add(ray.getOrigin());
                if (v2.getX() >= box.getMinimum().getX() && v2.getX() <= box.getMaximum().getX()
                        && v2.getZ() >= box.getMinimum().getZ() && v2.getZ() <= box.getMaximum().getZ() && (!hit || t < lowest)) {
                    hit = true;
                    lowest = t;
                }
            }
        }
        // max y
        if (ray.getOrigin().getY() >= box.getMaximum().getY() && ray.getDirection().getY() < 0) {
            t = (box.getMaximum().getY() - ray.getOrigin().getY()) / ray.getDirection().getY();
            if (t >= 0) {
                set(v2, ray.getDirection()).multiply(t).add(ray.getOrigin());
                if (v2.getX() >= box.getMinimum().getX() && v2.getX() <= box.getMaximum().getX()
                        && v2.getZ() >= box.getMinimum().getZ() && v2.getZ() <= box.getMaximum().getZ() && (!hit || t < lowest)) {
                    hit = true;
                    lowest = t;
                }
            }
        }
        // min z
        if (ray.getOrigin().getZ() <= box.getMinimum().getZ() && ray.getDirection().getZ() > 0) {
            t = (box.getMinimum().getZ() - ray.getOrigin().getZ()) / ray.getDirection().getZ();
            if (t >= 0) {
                set(v2, ray.getDirection()).multiply(t).add(ray.getOrigin());
                if (v2.getX() >= box.getMinimum().getX() && v2.getX() <= box.getMaximum().getX()
                        && v2.getY() >= box.getMinimum().getY() && v2.getY() <= box.getMaximum().getY() && (!hit || t < lowest)) {
                    hit = true;
                    lowest = t;
                }
            }
        }
        // max y
        if (ray.getOrigin().getZ() >= box.getMaximum().getZ() && ray.getDirection().getZ() < 0) {
            t = (box.getMaximum().getZ() - ray.getOrigin().getZ()) / ray.getDirection().getZ();
            if (t >= 0) {
                set(v2, ray.getDirection()).multiply(t).add(ray.getOrigin());
                if (v2.getX() >= box.getMinimum().getX() && v2.getX() <= box.getMaximum().getX()
                        && v2.getY() >= box.getMinimum().getY() && v2.getY() <= box.getMaximum().getY() && (!hit || t < lowest)) {
                    hit = true;
                    lowest = t;
                }
            }
        }
        if (hit && intersection != null) {
            Vector temp = ray.getDirection().clone().multiply(lowest).add(ray.getOrigin());
            intersection.setX(temp.getX()).setY(temp.getY()).setZ(temp.getZ());
            if (intersection.getX() < box.getMinimum().getX()) {
                intersection.setX(box.getMinimum().getX());
            } else if (intersection.getX() > box.getMaximum().getX()) {
                intersection.setX(box.getMaximum().getX());
            }
            if (intersection.getY() < box.getMinimum().getY()) {
                intersection.setY(box.getMinimum().getY());
            } else if (intersection.getY() > box.getMaximum().getY()) {
                intersection.setY(box.getMaximum().getY());
            }
            if (intersection.getZ() < box.getMinimum().getZ()) {
                intersection.setZ(box.getMinimum().getZ());
            } else if (intersection.getZ() > box.getMaximum().getZ()) {
                intersection.setZ(box.getMaximum().getZ());
            }
        }
        return hit;
    }

    /**
     * Quick check whether the given {@link Ray} and {@link BoundingBox} intersect.
     *
     * @param ray The ray
     * @param box The bounding box
     * @return Whether the ray and the bounding box intersect
     */
    static public boolean intersectRayBoundsFast(Ray ray, BoundingBox box) {
        return intersectRayBoundsFast(ray, box.getCenter(), box.getDimensions());
    }

    /**
     * Quick check whether the given {@link Ray} and {@link BoundingBox} intersect.
     *
     * @param ray        The ray
     * @param center     The center of the bounding box
     * @param dimensions The dimensions (width, height and depth) of the bounding box
     * @return Whether the ray and the bounding box intersect
     */
    static public boolean intersectRayBoundsFast(Ray ray, Vector center, Vector dimensions) {
        final double divX = 1f / ray.getDirection().getX();
        final double divY = 1f / ray.getDirection().getY();
        final double divZ = 1f / ray.getDirection().getZ();

        double minx = ((center.getX() - dimensions.getX() * .5f) - ray.getOrigin().getX()) * divX;
        double maxx = ((center.getX() + dimensions.getX() * .5f) - ray.getOrigin().getX()) * divX;
        if (minx > maxx) {
            final double t = minx;
            minx = maxx;
            maxx = t;
        }

        double miny = ((center.getY() - dimensions.getY() * .5f) - ray.getOrigin().getY()) * divY;
        double maxy = ((center.getY() + dimensions.getY() * .5f) - ray.getOrigin().getY()) * divY;
        if (miny > maxy) {
            final double t = miny;
            miny = maxy;
            maxy = t;
        }

        double minz = ((center.getZ() - dimensions.getZ() * .5f) - ray.getOrigin().getZ()) * divZ;
        double maxz = ((center.getZ() + dimensions.getZ() * .5f) - ray.getOrigin().getZ()) * divZ;
        if (minz > maxz) {
            final double t = minz;
            minz = maxz;
            maxz = t;
        }

        double min = Math.max(Math.max(minx, miny), minz);
        double max = Math.min(Math.min(maxx, maxy), maxz);

        return max >= 0 && max >= min;
    }

    /**
     * Quick check whether the provided {@link Ray} and {@link BoundingBox}
     * intersects.
     * <p>
     *
     * @param ray    Ray
     * @param bounds Bounding box.
     * @return Whether the ray and the bounding box intersects.
     */
    public static boolean intersectRayBoundsFast2(Ray ray, BoundingBox bounds) {
        final Vector origin = ray.getOrigin();
        if (bounds.contains(origin)) {
            return true;
        }

        final double d0 = origin.distance(bounds.getCenter());
        final double d1 = origin.distance(bounds.getMinimum());
        final double d2 = origin.distance(bounds.getMaximum());

        final Vector v0 = ray.getDirection();
        final Vector v1 = v0.clone().multiply(d0);
        final Vector v2 = v0.clone().multiply(d1);
        final Vector v3 = v0.clone().multiply(d2);

        final boolean c0 = bounds.contains(origin.clone().add(v1));
        final boolean c1 = bounds.contains(origin.clone().add(v2));
        final boolean c2 = bounds.contains(origin.clone().add(v3));

        return c0 || c1 || c2;
    }

    private static Vector set(Vector v0, Vector v1) {
        v0
                .setX(v1.getX())
                .setY(v1.getY())
                .setZ(v1.getZ());
        return v0;
    }

}
