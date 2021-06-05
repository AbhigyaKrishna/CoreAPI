package me.Abhigya.core.particle.data.color;

import me.Abhigya.core.particle.ParticleConstants;
import me.Abhigya.core.particle.ParticleEffect;
import me.Abhigya.core.particle.data.ParticleData;
import me.Abhigya.core.particle.utils.MathUtils;
import me.Abhigya.core.util.server.Version;

import java.awt.*;

/**
 * A implementation of the {@link ParticleColor} class that supports normal RGB values.
 */
public class RegularColor extends ParticleColor {

    /**
     * Initializes a new {@link ParticleData} object.
     * <p>
     *
     * @param color {@link Color} the particle should have
     */
    public RegularColor(Color color) {
        super(color.getRed(), color.getGreen(), color.getBlue());
    }

    /**
     * Initializes a new {@link ParticleData} object.
     * <p>
     *
     * @param red   Red value of the color
     * @param green Green value of the color
     * @param blue  Blue value of the color
     */
    public RegularColor(int red, int green, int blue) {
        super(MathUtils.getMaxOrMin(red, 255, 0), MathUtils.getMaxOrMin(green, 255, 0), MathUtils.getMaxOrMin(blue, 255, 0));
    }

    /**
     * Gets the red value of the color.
     * <p>
     *
     * @return Red value
     */
    @Override
    public float getRed() {
        return super.getRed() / 255f;
    }

    /**
     * Gets green red value of the color.
     * <p>
     *
     * @return Green value
     */
    @Override
    public float getGreen() {
        return super.getGreen() / 255f;
    }

    /**
     * Gets the blue value of the color.
     * <p>
     *
     * @return Blue value.
     */
    @Override
    public float getBlue() {
        return super.getBlue() / 255f;
    }

    /**
     * Converts the current {@link ParticleData} instance into nms data. If the current
     * minecraft version was released before 1.13 an int array should be returned. If the
     * version was released after 1.12 a nms "ParticleParam" has to be returned.
     * <p>
     *
     * @return Nms data
     */
    @Override
    public Object toNMSData() {
        if (getEffect() != ParticleEffect.REDSTONE || Version.getServerVersion().isOlder(Version.v1_13_R1))
            return new int[0];
        try {
            return ParticleConstants.PARTICLE_PARAM_REDSTONE_CONSTRUCTOR.newInstance(getRed(), getGreen(), getBlue(), 1f);
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * Generates a random {@link RegularColor} instance with a high saturation. If you
     * want a completely random {@link Color} use {@link #random(boolean)} with false
     * as the highSaturarion parameter.
     * <p>
     *
     * @return Randomly generated {@link RegularColor} instance
     */
    public static RegularColor random() {
        return random(true);
    }

    /**
     * Generates a random {@link RegularColor} instance. If the highSaturation parameter
     * is set to true, a random hue from the HSV spectrum will be used. Otherwise 3 random
     * integers ranging from 0 to 255 for the RGB values will be generated.
     * <p>
     *
     * @param highSaturation Determines if the colors should have a high saturation
     * @return Randomly generated {@link RegularColor} instance
     */
    public static RegularColor random(boolean highSaturation) {
        if (highSaturation)
            return fromHSVHue(MathUtils.generateRandomInteger(0, 360));
        else
            return new RegularColor(new Color(MathUtils.RANDOM.nextInt(256), MathUtils.RANDOM.nextInt(256), MathUtils.RANDOM.nextInt(256)));
    }

    /**
     * Constructs a {@link RegularColor} using the HSV color spectrum.
     * <p>
     *
     * @param hue Hue the the specific color has
     * @return {@link RegularColor} instance with the given HSV value as its {@link Color}
     * @see Color#HSBtoRGB(float, float, float)
     */
    public static RegularColor fromHSVHue(int hue) {
        return new RegularColor(new Color(Color.HSBtoRGB(hue / 360f, 1f, 1f)));
    }

}
