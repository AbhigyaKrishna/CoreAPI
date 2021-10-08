package me.Abhigya.core.util.math;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

/**
 * Encapsulates a immutable Vector. Methods like {@link #normalize()} will not
 * modify this Vector, and a new Vector containing the result will be returned
 * instead.
 * <p>
 * Also values like {@link #length()} are cached for a better performance.
 */
public interface Vector extends ConfigurationSerializable {

    /**
     * A "close to zero" float epsilon value for use
     */
    public static final float FLOAT_EPSILON = Float.intBitsToFloat( 0x34000000 );

    /**
     * Gets the magnitude of the Vector, defined as sqrt ( x^2 + y^2 + z^2 ). The
     * value of this method is cached, so repeatedly calling this method to get the
     * vector's magnitude will not re-calculate it. NaN will be returned if the
     * inner result of the sqrt() function overflows, which will be caused if the
     * length is too long.
     * <p>
     * This is the equivalent of using:
     * <pre><code>Math.sqrt ( {@link #lengthSquared()} )</code></pre>
     * <p>
     *
     * @return Magnitude
     */
    public float length( );

    /**
     * Gets the magnitude of the vector squared.
     * <p>
     *
     * @return Magnitude
     */
    public float lengthSquared( );

    /**
     * Gets the unit equivalent of this vector. ( a Vector with length of 1 ).
     * <p>
     *
     * @return Created unit vector from this
     */
    public Vector normalize( );

    /**
     * Gets the 2D equivalent of this vector.
     * <p>
     *
     * @return Equivalent, or the same vector if called from an instance of the
     * same class.
     */
    public Vector2D toVector2D( );

    /**
     * Gets the 3D equivalent of this vector.
     * <p>
     * Note that calling this method from {@link Vector2D} will result in an
     * {@link UnsupportedOperationException}. The method
     * {@link Vector2D#toVector3D(float)} must be used instead.
     * <p>
     *
     * @return Equivalent, or the same vector if called from an instance of the
     * same class.
     */
    public Vector3D toVector3D( );

    /**
     * Gets the Bukkit equivalent of this vector.
     * <p>
     * Note that calling this method from {@link Vector2D} will result in an
     * {@link UnsupportedOperationException}. The method
     * {@link Vector2D#toBukkit(double)} must be used instead.
     * <p>
     *
     * @return The equivalent
     */
    public org.bukkit.util.Vector toBukkit( );

    /**
     * Gets a Location version of this Vector.
     * <p>
     * Note that calling this method from {@link Vector2D} will result in an
     * {@link UnsupportedOperationException}. The method
     * {@link Vector2D#toLocation(World, double, float, float)} must be used instead.
     * <p>
     *
     * @param world World to link the location to
     * @param yaw   Desired yaw
     * @param pitch Desired pitch
     * @return Location
     */
    public Location toLocation( final World world, final float yaw, final float pitch );

    /**
     * Gets a Location version of this Vector with yaw and pitch being 0.
     * <p>
     * Note that calling this method from {@link Vector2D} will result in an
     * {@link UnsupportedOperationException}. The method
     * {@link Vector2D#toLocation(World, double)} must be used instead.
     * <p>
     *
     * @param world World to link the location to
     * @return Location
     */
    public Location toLocation( final World world );

}
