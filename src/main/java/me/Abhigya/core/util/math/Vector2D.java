package me.Abhigya.core.util.math;

import org.bukkit.Location;
import org.bukkit.World;

import java.util.HashMap;
import java.util.Map;

/**
 * Encapsulates an immutable cached Vector of two dimensions ( x and y ).
 * <p>
 * The values of {@link #length()}, {@link #lengthSquared()},
 * {@link #normalize()} and {@link #hashCode()} are cached for a better
 * performance.
 */
public class Vector2D implements Vector {

    public static final Vector2D ZERO = new Vector2D(0F, 0F);
    public static final Vector2D ONE = new Vector2D(1F, 1F);
    public static final Vector2D X = new Vector2D(1F, 0F);
    public static final Vector2D Y = new Vector2D(0F, 1F);

    protected final float x;
    protected final float y;

    /* cached values */
    private float length_squared = Float.NaN;
    private float length = Float.NaN;

    private Vector2D normalized = null;

    private boolean hashed = false;
    private int hashcode = 0;

    private int x_bits = 0;
    private int y_bits = 0;
    private boolean bitset = false;

    public Vector2D(final float x, final float y) {
        this.x = x;
        this.y = y;
    }

    public Vector2D(final int x, final int y) {
        this((float) x, (float) y);
    }

    public Vector2D(final double x, final double y) {
        this((float) x, (float) y);
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public Vector2D add(final Vector2D other) {
        return new Vector2D((this.x + other.x), (this.y + other.y));
    }

    public Vector2D add(final float x, final float y) {
        return new Vector2D((this.x + x), (this.y + y));
    }

    public Vector2D subtract(final Vector2D other) {
        return new Vector2D((this.x - other.x), (this.y - other.y));
    }

    public Vector2D subtract(final float x, final float y) {
        return new Vector2D((this.x - x), (this.y - y));
    }

    public Vector2D multiply(final Vector2D other) {
        return new Vector2D((this.x * other.x), (this.y * other.y));
    }

    public Vector2D multiply(final float x, final float y) {
        return new Vector2D((this.x * x), (this.y * y));
    }

    public Vector2D divide(final Vector2D other) {
        return new Vector2D((this.x / other.x), (this.y / other.y));
    }

    public Vector2D divide(final float x, final float y) {
        return new Vector2D((this.x / x), (this.y / y));
    }

    @Override
    public float length() {
        if (Float.isNaN(length)) {
            this.length = (float) Math.sqrt(lengthSquared());
        }
        return length;
    }

    @Override
    public float lengthSquared() {
        if (Float.isNaN(length_squared)) {
            this.length_squared = (x * x) + (y * y);
        }
        return length_squared;
    }

    public float distance(final Vector2D other) {
        return (float) Math.sqrt(distanceSquared(other));
    }

    public float distanceSquared(final Vector2D other) {
        final float x_d = (this.x - other.x);
        final float y_d = (this.y - other.y);

        return ((x_d * x_d) + (y_d * y_d));
    }

    /**
     * Gets the angle between this vector and another in degrees.
     * <p>
     *
     * @param other Other vector
     * @return Angle in degrees
     */
    public float angle(final Vector2D other) {
        return (float) Math.toDegrees(Math.atan2(crossProduct(other), dotProduct(other)));
    }

    public Vector2D midpoint(final Vector2D other) {
        final float x = (this.x + other.x) / 2;
        final float y = (this.y + other.y) / 2;

        return new Vector2D(x, y);
    }

    public float dotProduct(final Vector2D other) {
        return (this.x * other.x) + (this.y * other.y);
    }

    public float crossProduct(final Vector2D other) {
        return (this.x * other.y) - (this.y * other.x);
    }

    @Override
    public Vector2D normalize() {
        if (this.normalized == null) {
            if (Math.abs(length()) < FLOAT_EPSILON) {
                this.normalized = Vector2D.ZERO;
            } else {
                this.normalized = new Vector2D((this.x / length()), (this.y / length()));
            }
        }
        return normalized;
    }

    @Override
    public Vector2D toVector2D() {
        return this;
    }

    @Override
    public Vector3D toVector3D() {
        throw new UnsupportedOperationException("not supported ( missing z component )");
    }

    public Vector3D toVector3D(final float z) {
        return new Vector3D(this.x, this.y, z);
    }

    /**
     * Creates and returns a new {@link Vector3D} by specifying the components to
     * provide, following the rules explain below:
     * <pre><strong>-1</strong> represents the value of {@link #getX()} in this
     * vector.</pre>
     * <pre><strong>0</strong> represents the value of {@link #getY()} in this
     * vector.</pre>
     * <pre><strong>1</strong> represents 0.</pre>
     * <p>
     *
     * @param x Providing <strong>-1</strong>, will construct the Vector3D with its x component
     *          having the same value as the x component of this vector. Providing
     *          <strong>0</strong>, will construct the Vector3D with its x component having the same
     *          value as the y component of this vector. Providing <strong>1</strong>, will construct
     *          the Vector3D with its x being 0.
     * @param y Providing <strong>-1</strong>, will construct the Vector3D with its y component
     *          having the same value as the x component of this vector. Providing
     *          <strong>0</strong>, will construct the Vector3D with its y component having the same
     *          value as the y component of this vector. Providing <strong>1</strong>, will construct
     *          the Vector3D with its y being 0.
     * @param z Providing <strong>-1</strong>, will construct the Vector3D with its z component
     *          having the same value as the x component of this vector. Providing
     *          <strong>0</strong>, will construct the Vector3D with its z component having the same
     *          value as the y component of this vector. Providing <strong>1</strong>, will construct
     *          the Vector3D with its z being 0.
     * @return the 3D version.
     */
    public Vector3D toVector3D(final int x, final int y, final int z) {
        // -1 = x
        //  0 = y
        //  1 = 0

        final float vector_x = x < 0 ? this.x : (x == 0 ? this.y : 0F);
        final float vector_y = y < 0 ? this.x : (x == 0 ? this.y : 0F);
        final float vector_z = z < 0 ? this.x : (x == 0 ? this.y : 0F);

        return new Vector3D(vector_x, vector_y, vector_z);
    }

    @Override
    public org.bukkit.util.Vector toBukkit() {
        throw new UnsupportedOperationException("not supported ( missing z component )");
    }

    public org.bukkit.util.Vector toBukkit(final double z) {
        return new org.bukkit.util.Vector(this.x, this.y, z);
    }

    @Override
    public Location toLocation(final World world, final float yaw, final float pitch) {
        throw new UnsupportedOperationException("not supported ( missing z component )");
    }

    public Location toLocation(final World world, final double z, final float yaw, final float pitch) {
        return new Location(world, this.x, this.y, z, yaw, pitch);
    }

    @Override
    public Location toLocation(final World world) {
        throw new UnsupportedOperationException("not supported ( missing z component )");
    }

    public Location toLocation(final World world, final double z) {
        return toLocation(world, z, 0F, 0F);
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    @Override
    public Map<String, Object> serialize() {
        final Map<String, Object> serialized = new HashMap<>();

        serialized.put("x", this.x);
        serialized.put("y", this.y);

        return serialized;
    }

    @Override
    public int hashCode() {
        if (!hashed) {
            this.hashcode = 7;
            this.hashcode = 79 * hashcode + (int) (Float.floatToIntBits(this.x) ^ (Float.floatToIntBits(this.x) >>> 32));
            this.hashcode = 79 * hashcode + (int) (Float.floatToIntBits(this.y) ^ (Float.floatToIntBits(this.y) >>> 32));

            this.hashed = true;
        }
        return hashcode;
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof Vector2D)) {
            return false;
        }

        Vector2D other = (Vector2D) obj;
        this.bitset();
        other.bitset();

        return (this.x_bits == other.x_bits)
                && (this.y_bits == other.y_bits);
    }

    protected void bitset() {
        if (!bitset) {
            this.x_bits = Float.floatToIntBits(this.x);
            this.y_bits = Float.floatToIntBits(this.y);

            this.bitset = true;
        }
    }

}
