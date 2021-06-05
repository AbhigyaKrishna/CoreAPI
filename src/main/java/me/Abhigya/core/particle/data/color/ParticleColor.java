package me.Abhigya.core.particle.data.color;

import me.Abhigya.core.particle.data.ParticleData;

/**
 * The {@link ParticleColor} class is used to store the rgb values of colors and
 * convert them into the corresponding nms objects.
 */
public abstract class ParticleColor extends ParticleData {

    /**
     * The red value of the rgb value.
     */
    private final int red;
    /**
     * The green value of the rgb value.
     */
    private final int green;
    /**
     * The blue value of the rgb value.
     */
    private final int blue;

    /**
     * Initializes a new {@link ParticleData} object.
     * <p>
     *
     * @param red   Red value of the color
     * @param green Green value of the color
     * @param blue  Blue value of the color
     */
    ParticleColor(int red, int green, int blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    /**
     * Converts the current {@link ParticleData} instance into nms data. If the current
     * minecraft version was released before 1.13 a int array should be returned. If the
     * version was released after 1.12 a nms "ParticleParam" has to be returned.
     * <p>
     *
     * @return Nms data
     */
    @Override
    public abstract Object toNMSData();

    /**
     * Gets the red value of the color.
     * <p>
     *
     * @return Red value
     */
    public float getRed() {
        return red;
    }

    /**
     * Gets green red value of the color.
     * <p>
     *
     * @return Green value
     */
    public float getGreen() {
        return green;
    }

    /**
     * Gets the blue value of the color.
     * <p>
     *
     * @return Blue value.
     */
    public float getBlue() {
        return blue;
    }

}
