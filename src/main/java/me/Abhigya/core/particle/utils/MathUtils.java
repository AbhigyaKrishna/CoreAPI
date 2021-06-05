package me.Abhigya.core.particle.utils;

import me.Abhigya.core.particle.data.ParticleData;

import java.util.Random;

/**
 * Utility for Maths
 */
public class MathUtils {

    /**
     * A easy to access {@link Random} implementation for random number
     * generation. This specific field is mostly used by the random
     * methods of the {@link ParticleData} types.
     */
    public static final Random RANDOM = new Random();

    /**
     * Generates a random {@link Integer}.
     * <p>
     *
     * @param minimum Minimum value of the generated value
     * @param maximum Maximum value of the generated value
     * @return Randomly generated {@link Integer} in the defined range
     * @see #RANDOM
     */
    public static int generateRandomInteger(int minimum, int maximum) {
        return minimum + (int) (RANDOM.nextDouble() * ((maximum - minimum) + 1));
    }


    /**
     * Checks if a specific {@link Integer} is in the given range.
     * If not the respective bound of the range is returned.
     * <p>
     *
     * @param value Value which should be checked
     * @param max   Maximum value
     * @param min   Minimum value
     * @return Calculated value
     */
    public static int getMaxOrMin(int value, int max, int min) {
        return value < max ? (Math.max(value, min)) : max;
    }

}
