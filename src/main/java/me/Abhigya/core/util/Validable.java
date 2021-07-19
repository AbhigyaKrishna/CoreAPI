package me.Abhigya.core.util;

/**
 * Simple interface for validating Objects.
 */
public interface Validable {

    /**
     * Gets whether this Object represents a valid instance.
     * <p>
     *
     * @return true if valid
     */
    public boolean isValid();

    /**
     * Gets whether this Object represents an invalid instance.
     * <p>
     *
     * @return true if invalid
     */
    public boolean isInvalid();
}
