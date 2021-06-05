package me.Abhigya.core.util.math.collision;

import org.bukkit.util.Vector;

import java.util.Collection;

/**
 * Encapsulates an axis aligned bounding box represented by a minimum
 * and a maximum Vector. Additionally you can query for the bounding box's
 * center, dimensions and corner points.
 */
public class BoundingBox {

    public static final BoundingBox ZERO = new BoundingBox(new Vector(0D, 0D, 0D), new Vector(0D, 0D, 0D));
    public static final BoundingBox BLOCK = new BoundingBox(new Vector(0D, 0D, 0D), new Vector(1D, 1D, 1D));
    public static final BoundingBox INFINITY = new BoundingBox(
            new Vector(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY),
            new Vector(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY));

    protected final Vector minimum;
    protected final Vector maximum;

    protected final Vector center;
    protected final Vector dimensions;

    /**
     * Constructs the new bounding box using the provided minimum and maximum vector.
     *
     * @param minimum the minimum vector.
     * @param maximum the maximum vector.
     */
    public BoundingBox(final Vector minimum, final Vector maximum) {
        if (Double.isInfinite(minimum.getX()) && Double.isInfinite(minimum.getY()) && Double.isInfinite(minimum.getZ()) &&
                Double.isInfinite(maximum.getX()) && Double.isInfinite(maximum.getY()) && Double.isInfinite(maximum.getZ())) {
            this.minimum = minimum;
            this.maximum = maximum;

            this.center = new Vector(0D, 0D, 0D);
            this.dimensions = new Vector(0D, 0D, 0D);
        } else {
            this.minimum = new Vector(
                    Math.min(minimum.getX(), maximum.getX()),
                    Math.min(minimum.getY(), maximum.getY()),
                    Math.min(minimum.getZ(), maximum.getZ()));

            this.maximum = new Vector(
                    Math.max(minimum.getX(), maximum.getX()),
                    Math.max(minimum.getY(), maximum.getY()),
                    Math.max(minimum.getZ(), maximum.getZ()));

            this.center = this.minimum.clone().add(this.maximum).multiply(0.5F);
            this.dimensions = this.maximum.clone().subtract(this.minimum);
        }
    }

    /**
     * Constructs the new bounding box using the provided minimum and maximum coordinates.
     * <p>
     *
     * @param minimum_x the minimum x.
     * @param minimum_y the minimum y.
     * @param minimum_z the minimum z.
     * @param maximum_x the maximum x.
     * @param maximum_y the maximum y.
     * @param maximum_z the maximum z.
     */
    public BoundingBox(Double minimum_x, Double minimum_y, Double minimum_z,
                       Double maximum_x, Double maximum_y, Double maximum_z) {
        this(new Vector(minimum_x, minimum_y, minimum_z), new Vector(maximum_x, maximum_y, maximum_z));
    }

    /**
     * Constructs the new bounding box incorporating the provided points to calculate the minimum and maximum.
     * <p>
     *
     * @param points the points to incorporate.
     */
    public BoundingBox(Vector... points) {
        BoundingBox temp = INFINITY;
        for (Vector point : points) {
            temp = temp.extend(point);
        }

        this.minimum = temp.minimum;
        this.maximum = temp.maximum;

        this.center = temp.center;
        this.dimensions = temp.dimensions;
    }

    /**
     * Constructs the new bounding box incorporating the provided points to calculate the minimum and maximum.
     * <p>
     *
     * @param points the points to incorporate.
     */
    public BoundingBox(Collection<Vector> points) {
        this(points.toArray(new Vector[points.size()]));
    }

    /**
     * Gets the minimum vector of this bounding box.
     * <p>
     *
     * @return a new copy of the minimum vector.
     */
    public Vector getMinimum() {
        return minimum.clone();
    }

    /**
     * Gets the maximum vector of this bounding box.
     * <p>
     *
     * @return a new copy of the maximum Vector.
     */
    public Vector getMaximum() {
        return maximum.clone();
    }

    /**
     * Gets the center of this bounding box.
     * <p>
     *
     * @return a new copy of the center Vector of this bounding box.
     */
    public Vector getCenter() {
        return center.clone();
    }

    /**
     * Gets the dimensions of this bounding box.
     * <p>
     *
     * @return a new copy of the dimensions Vector of this bounding box.
     */
    public Vector getDimensions() {
        return dimensions.clone();
    }

    /**
     * Gets the width of this bounding box.
     * <p>
     * This is the equivalent of using: {@code BoundingBox.getDimensions().getX()}.
     * <p>
     *
     * @return the width of the bounding box.
     */
    public Double getWidth() {
        return dimensions.getX();
    }

    /**
     * Gets the height of this bounding box.
     * <p>
     * This is the equivalent of using: {@code BoundingBox.getDimensions().getY()}.
     * <p>
     *
     * @return the height of the bounding box.
     */
    public Double getHeight() {
        return dimensions.getY();
    }

    /**
     * Gets the depth of this bounding box.
     * <p>
     * This is the equivalent of using: {@code BoundingBox.getDimensions().getZ()}.
     * <p>
     *
     * @return the depth of the bounding box.
     */
    public Double getDepth() {
        return dimensions.getZ();
    }

    /* BoundingBox Corners */

    public Vector getCorner000() {
        return new Vector(minimum.getX(), minimum.getY(), minimum.getZ());
    }

    public Vector getCorner001() {
        return new Vector(minimum.getX(), minimum.getY(), maximum.getZ());
    }

    public Vector getCorner010() {
        return new Vector(minimum.getX(), maximum.getY(), minimum.getZ());
    }

    public Vector getCorner011() {
        return new Vector(minimum.getX(), maximum.getY(), maximum.getZ());
    }

    public Vector getCorner100() {
        return new Vector(maximum.getX(), minimum.getY(), minimum.getZ());
    }

    public Vector getCorner101() {
        return new Vector(maximum.getX(), minimum.getY(), maximum.getZ());
    }

    public Vector getCorner110() {
        return new Vector(maximum.getX(), maximum.getY(), minimum.getZ());
    }

    public Vector getCorner111() {
        return new Vector(maximum.getX(), maximum.getY(), maximum.getZ());
    }

    public Vector[] getCorners() {
        return new Vector[]{
                getCorner000(),
                getCorner001(),
                getCorner010(),
                getCorner011(),
                getCorner100(),
                getCorner101(),
                getCorner110(),
                getCorner111()
        };
    }

    /**
     * Adds the provided location {@link Vector}.
     * <p>
     * This is commonly used for locating unit bounding boxes.
     * <p>
     *
     * @param location the location to add.
     * @return a new BoundingBox containing the addition result.
     */
    public BoundingBox add(Vector location) {
        return new BoundingBox(minimum.add(location), maximum.add(location));
    }

    /**
     * Adds the provided location coordinates.
     * <p>
     * This is commonly used for locating unit bounding boxes.
     * <p>
     *
     * @param x the x coordinate.
     * @param y the y coordinate.
     * @param z the z coordinate.
     * @return a new BoundingBox containing the addition result.
     */
    public BoundingBox add(Double x, Double y, Double z) {
        return add(new Vector(x, y, z));
    }

    /**
     * Subtract by the provided location {@link Vector}.
     * <p>
     *
     * @param location the location to subtract.
     * @return a new BoundingBox containing the subtraction result.
     */
    public BoundingBox subtract(Vector location) {
        return new BoundingBox(minimum.subtract(location), maximum.subtract(location));
    }

    /**
     * Subtract by the provided location coordinates.
     * <p>
     *
     * @param x the x coordinate.
     * @param y the y coordinate.
     * @param z the z coordinate.
     * @return a new BoundingBox containing the subtraction result.
     */
    public BoundingBox subtract(Double x, Double y, Double z) {
        return subtract(new Vector(x, y, z));
    }

    /**
     * Multiply by the provided location {@link Vector}.
     * <p>
     *
     * @param location the location to multiply.
     * @return a new BoundingBox containing the multiplication result.
     */
    public BoundingBox multiply(Vector location) {
        return new BoundingBox(minimum.multiply(location), maximum.multiply(location));
    }

    /**
     * Multiply by the provided location coordinates.
     * <p>
     *
     * @param x the x coordinate.
     * @param y the y coordinate.
     * @param z the z coordinate.
     * @return a new BoundingBox containing the multiplication result.
     */
    public BoundingBox multiply(Double x, Double y, Double z) {
        return multiply(new Vector(x, y, z));
    }

    /**
     * Divide by the provided location {@link Vector}.
     * <p>
     *
     * @param location the location to divide.
     * @return a new BoundingBox containing the division result.
     */
    public BoundingBox divide(Vector location) {
        return new BoundingBox(minimum.divide(location), maximum.divide(location));
    }

    /**
     * Divide by the provided location coordinates.
     * <p>
     *
     * @param x the x coordinate.
     * @param y the y coordinate.
     * @param z the z coordinate.
     * @return a new BoundingBox containing the division result.
     */
    public BoundingBox divide(Double x, Double y, Double z) {
        return divide(new Vector(x, y, z));
    }

    /**
     * Extends this bounding box by the provided bounding box.
     * <p>
     *
     * @param a_bounds the other bounding box.
     * @return a new BoundingBox containing the extension result.
     */
    public BoundingBox extend(BoundingBox a_bounds) {
        return new BoundingBox(
                new Vector(
                        Math.min(minimum.getX(), a_bounds.minimum.getX()),
                        Math.min(minimum.getY(), a_bounds.minimum.getY()),
                        Math.min(minimum.getZ(), a_bounds.minimum.getZ())),

                new Vector(
                        Math.max(maximum.getX(), a_bounds.maximum.getX()),
                        Math.max(maximum.getY(), a_bounds.maximum.getY()),
                        Math.max(maximum.getZ(), a_bounds.maximum.getZ())));
    }

    /**
     * Extends this bounding box to incorporate the provided {@link Vector}.
     * <p>
     *
     * @param point the point to incorporate.
     * @return a new BoundingBox containing the extension result.
     */
    public BoundingBox extend(Vector point) {
        return extend(point.getX(), point.getY(), point.getZ());
    }

    /**
     * Extends this bounding box to incorporate the provided coordinates.
     * <p>
     *
     * @param x the x-coordinate.
     * @param y the y-coordinate.
     * @param z the z-coordinate.
     * @return a new BoundingBox containing the extension result.
     */
    public BoundingBox extend(Double x, Double y, Double z) {
        return new BoundingBox(
                new Vector(
                        Math.min(minimum.getX(), x),
                        Math.min(minimum.getY(), y),
                        Math.min(minimum.getZ(), z)),

                new Vector(
                        Math.max(maximum.getX(), x),
                        Math.max(maximum.getY(), y),
                        Math.max(maximum.getZ(), z)));
    }

    /**
     * Extends this bounding box by the given sphere.
     * <p>
     *
     * @param center the sphere center.
     * @param radius the sphere radius.
     * @return a new BoundingBox containing the extension result.
     */
    public BoundingBox extend(Vector center, Double radius) {
        return new BoundingBox(
                new Vector(
                        Math.min(minimum.getX(), (center.getX() - radius)),
                        Math.min(minimum.getY(), (center.getY() - radius)),
                        Math.min(minimum.getZ(), (center.getZ() - radius))),

                new Vector(
                        Math.max(maximum.getX(), (center.getX() + radius)),
                        Math.max(maximum.getY(), (center.getY() + radius)),
                        Math.max(maximum.getZ(), (center.getZ() + radius))));
    }

    /**
     * Gets whether the provided {@link BoundingBox} is intersecting this bounding box ( at least one point in ).
     * <p>
     *
     * @param other the bounding box to check.
     * @return true if intersecting.
     */
    public boolean intersects(final BoundingBox other) {
        /* test using SAT ( separating axis theorem ) */
        Double lx = Math.abs(this.center.getX() - other.center.getX());
        Double sumx = (this.dimensions.getX() / 2.0F) + (other.dimensions.getX() / 2.0F);

        Double ly = Math.abs(this.center.getY() - other.center.getY());
        Double sumy = (this.dimensions.getY() / 2.0F) + (other.dimensions.getY() / 2.0F);

        Double lz = Math.abs(this.center.getZ() - other.center.getZ());
        Double sumz = (this.dimensions.getZ() / 2.0F) + (other.dimensions.getZ() / 2.0F);

        return (lx <= sumx && ly <= sumy && lz <= sumz);
    }

    public boolean contains(final Vector vector) {
        return (minimum.getX() <= vector.getX()
                && maximum.getX() >= vector.getX()

                && minimum.getY() <= vector.getY()
                && maximum.getY() >= vector.getY()

                && minimum.getZ() <= vector.getZ()
                && maximum.getZ() >= vector.getZ());
    }

    public boolean contains(final BoundingBox other) {
        return (minimum.getX() <= other.minimum.getX()
                && minimum.getY() <= other.minimum.getY()

                && minimum.getZ() <= other.minimum.getZ()
                && maximum.getX() >= other.maximum.getX()

                && maximum.getY() >= other.maximum.getY()
                && maximum.getZ() >= other.maximum.getZ());
    }

    public boolean isValid() {
        return (minimum.getX() <= maximum.getX()
                && minimum.getY() <= maximum.getY()
                && minimum.getZ() <= maximum.getZ());
    }

    @Override
    public String toString() {
        return "[ " + minimum.toString() + " | " + maximum.toString() + " ]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((center == null) ? 0 : center.hashCode());
        result = prime * result + ((dimensions == null) ? 0 : dimensions.hashCode());
        result = prime * result + ((maximum == null) ? 0 : maximum.hashCode());
        result = prime * result + ((minimum == null) ? 0 : minimum.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof BoundingBox)) {
            return false;
        }

        BoundingBox other = (BoundingBox) obj;
        if (!maximum.equals(other.maximum) || !minimum.equals(other.minimum)) {
            return false;
        }

        if (!center.equals(other.center) || !dimensions.equals(other.dimensions)) {
            return false;
        }
        return true;
    }
}
