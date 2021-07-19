package me.Abhigya.core.util.math;

import org.bukkit.Location;
import org.bukkit.World;

import java.util.HashMap;
import java.util.Map;

/**
 * Encapsulates an immutable cached Vector of three dimensions ( x, y and z ).
 * <p>
 * The values of {@link #length()}, {@link #lengthSquared()},
 * {@link #normalize()} and {@link #hashCode()} are cached for a better
 * performance.
 */
public class Vector3D implements Vector {

    public static final Vector3D ZERO = new Vector3D(0F, 0F, 0F);
    public static final Vector3D ONE = new Vector3D(1F, 1F, 1F);
    public static final Vector3D X = new Vector3D(1F, 0F, 0F);
    public static final Vector3D Y = new Vector3D(0F, 1F, 0F);
    public static final Vector3D Z = new Vector3D(0F, 0F, 1F);

    protected final float x;
    protected final float y;
    protected final float z;

    /* cached values */
    protected float length_squared = Float.NaN;
    protected float length = Float.NaN;

    protected Vector3D normalized = null;

    protected boolean hashed = false;
    protected int hashcode = 0;

    protected int x_bits = 0;
    protected int y_bits = 0;
    protected int z_bits = 0;
    protected boolean bitset = false;

    public Vector3D(final float x, final float y, final float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3D(final int x, final int y, final int z) {
        this((float) x, (float) y, (float) z);
    }

    public Vector3D(final double x, final double y, final double z) {
        this((float) x, (float) y, (float) z);
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getZ() {
        return z;
    }

    public Vector3D add(final Vector3D other) {
        return new Vector3D((this.x + other.x), (this.y + other.y), (this.z + other.z));
    }

    public Vector3D add(final float x, final float y, final float z) {
        return new Vector3D((this.x + x), (this.y + y), (this.z + z));
    }

    public Vector3D subtract(final Vector3D other) {
        return new Vector3D((this.x - other.x), (this.y - other.y), (this.z - other.z));
    }

    public Vector3D subtract(final float x, final float y, final float z) {
        return new Vector3D((this.x - x), (this.y - y), (this.z - z));
    }

    public Vector3D multiply(final Vector3D other) {
        return new Vector3D((this.x * other.x), (this.y * other.y), (this.z * other.z));
    }

    public Vector3D multiply(final float x, final float y, final float z) {
        return new Vector3D((this.x * x), (this.y * y), (this.z * z));
    }

    public Vector3D multiply(final float factor) {
        return new Vector3D((this.x * factor), (this.y * factor), (this.z * factor));
    }

    public Vector3D divide(final Vector3D other) {
        return new Vector3D((this.x / other.x), (this.y / other.y), (this.z / other.z));
    }

    public Vector3D divide(final float x, final float y, final float z) {
        return new Vector3D((this.x / x), (this.y / y), (this.z / z));
    }

    public Vector3D divide(final float factor) {
        return new Vector3D((this.x / factor), (this.y / factor), (this.z / factor));
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
            this.length_squared = (x * x) + (y * y) + (z * z);
        }
        return length_squared;
    }

    public float distance(final Vector3D other) {
        return (float) Math.sqrt(distanceSquared(other));
    }

    public float distanceSquared(final Vector3D other) {
        final float x_d = (this.x - other.x);
        final float y_d = (this.y - other.y);
        final float z_d = (this.z - other.z);

        return (x_d * x_d) + (y_d * y_d) + (z_d * z_d);
    }

    /**
     * Gets the angle between this vector and another in degrees.
     * <p>
     *
     * @param other Other vector
     * @return Angle in degrees
     */
    public float angle(final Vector3D other) {
        return (float) Math.toDegrees(Math.acos(dotProduct(other) / (length() * other.length())));
    }

    public Vector3D midpoint(final Vector3D other) {
        final float x = (this.x + other.x) / 2;
        final float y = (this.y + other.y) / 2;
        final float z = (this.z + other.z) / 2;

        return new Vector3D(x, y, z);
    }

    public float dotProduct(final Vector3D other) {
        return (this.x * other.x) +
                (this.y * other.y) +
                (this.z * other.z);
    }

    public Vector3D crossProduct(final Vector3D other) {
        final float x = (this.y * other.z) - (other.y * this.z);
        final float y = (this.z * other.x) - (other.z * this.x);
        final float z = (this.x * other.y) - (other.x * this.y);

        return new Vector3D(x, y, z);
    }

    @Override
    public Vector3D normalize() {
        if (this.normalized == null) {
            if (Math.abs(length()) < FLOAT_EPSILON) {
                this.normalized = Vector3D.ZERO;
            } else {
                this.normalized = new Vector3D((this.x / length()), (this.y / length()), (this.z / length()));
            }
        }
        return normalized;
    }

    @Override
    public Vector2D toVector2D() {
        return toVector2D(-1, 0);
    }

    /**
     * Creates and returns a new {@link Vector2D} by specifying the components to
     * provide, following the rules explain below:
     * <pre><strong>-1</strong> represents the value of {@link #getX()} in this
     * vector.</pre>
     * <pre><strong>0</strong> represents the value of {@link #getY()} in this
     * vector.</pre>
     * <pre><strong>1</strong> represents the value of {@link #getZ()} in this
     * vector.</pre>
     * <p>
     *
     * @param x Providing <strong>-1</strong>, will construct the Vector2D with its x component
     *          having the same value as the x component of this vector. Providing
     *          <strong>0</strong>, will construct the Vector2D with its x component having the same
     *          value as the y component of this vector. Providing <strong>1</strong>, will construct
     *          the Vector2D with its x component having the same value as the z
     *          component of this vector.
     * @param y Providing <strong>-1</strong>, will construct the Vector2D with its y component
     *          having the same value as the x component of this vector. Providing
     *          <strong>0</strong>, will construct the Vector2D with its y component having the same
     *          value as the y component of this vector. Providing <strong>1</strong>, will construct
     *          the Vector2D with its y component having the same value as the z
     *          component of this vector.
     * @return 2D version
     */
    public Vector2D toVector2D(final int x, final int y) {
        // -1 = x
        //  0 = y
        //  1 = z

        final float vector_x = x < 0 ? this.x : (x == 0 ? this.y : this.z);
        final float vector_y = y < 0 ? this.x : (x == 0 ? this.y : this.z);

        return new Vector2D(vector_x, vector_y);
    }

    @Override
    public Vector3D toVector3D() {
        return this;
    }

    @Override
    public org.bukkit.util.Vector toBukkit() {
        return new org.bukkit.util.Vector(this.x, this.y, this.z);
    }

    @Override
    public Location toLocation(final World world, final float yaw, final float pitch) {
        return new Location(world, this.x, this.y, this.z, yaw, pitch);
    }

    public Location toLocation(final World world) {
        return toLocation(world, 0F, 0F);
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ", " + z + ")";
    }

    @Override
    public Map<String, Object> serialize() {
        final Map<String, Object> serialized = new HashMap<>();

        serialized.put("x", this.x);
        serialized.put("y", this.y);
        serialized.put("z", this.z);

        return serialized;
    }

    @Override
    public int hashCode() {
        if (!hashed) {
            this.hashcode = 7;
            this.hashcode = 79 * hashcode + (int) (Float.floatToIntBits(this.x) ^ (Float.floatToIntBits(this.x) >>> 32));
            this.hashcode = 79 * hashcode + (int) (Float.floatToIntBits(this.y) ^ (Float.floatToIntBits(this.y) >>> 32));
            this.hashcode = 79 * hashcode + (int) (Float.floatToIntBits(this.z) ^ (Float.floatToIntBits(this.z) >>> 32));

            this.hashed = true;
        }
        return hashcode;
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof Vector3D)) {
            return false;
        }

        Vector3D other = (Vector3D) obj;
        this.bitset();
        other.bitset();

        return (this.x_bits == other.x_bits)
                && (this.y_bits == other.y_bits)
                && (this.z_bits == other.z_bits);
    }

    protected void bitset() {
        if (!bitset) {
            this.x_bits = Float.floatToIntBits(this.x);
            this.y_bits = Float.floatToIntBits(this.y);
            this.z_bits = Float.floatToIntBits(this.z);

            this.bitset = true;
        }
    }

}
