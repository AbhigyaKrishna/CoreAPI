package me.Abhigya.core.particle.data.color;

import me.Abhigya.core.particle.ParticleConstants;
import me.Abhigya.core.particle.ParticleEffect;
import me.Abhigya.core.particle.utils.ParticleReflectionUtils;

import java.awt.*;

public class DustColorTransitionData extends DustData{

    /**
     * The red value of the second color.
     */
    private final int fadeRed;
    /**
     * The green value of the second color.
     */
    private final int fadeGreen;
    /**
     * The blue value of the second color.
     */
    private final int fadeBlue;

    /**
     * Creates a new {@link DustColorTransitionData} instance.
     *
     * @param color     the start color of the particle.
     * @param fadeColor the color the particle will fade to.
     * @param size      the size of the particle.
     */
    public DustColorTransitionData(Color color, Color fadeColor, float size) {
        super(color, size);
        fadeRed = fadeColor.getRed();
        fadeGreen = fadeColor.getGreen();
        fadeBlue = fadeColor.getBlue();
    }

    /**
     * Creates a new {@link DustColorTransitionData} instance.
     * <p>
     *
     * @param red       Red value of the start color
     * @param green     Green value of the start color
     * @param blue      Blue value of the start color
     * @param fadeRed   Red value of the second color
     * @param fadeGreen Green value of the second color
     * @param fadeBlue  Blue value of the second color
     * @param size      Size of the particle
     */
    public DustColorTransitionData(int red, int green, int blue, int fadeRed, int fadeGreen, int fadeBlue, float size) {
        super(red, green, blue, size);
        this.fadeRed = fadeRed;
        this.fadeGreen = fadeGreen;
        this.fadeBlue = fadeBlue;
    }

    /**
     * Gets the red value of the color the particle will
     * fade to. <b>(Value range is 0f-1f)</b>
     * <p>
     *
     * @return Red value of the second color
     */
    public float getFadeRed() {
        return fadeRed / 255f;
    }

    /**
     * Gets the green value of the color the particle will
     * fade to. <b>(Value range is 0f-1f)</b>
     * <p>
     *
     * @return Green value of the second color
     */
    public float getFadeGreen() {
        return fadeGreen / 255f;
    }

    /**
     * Gets the blue value of the color the particle will
     * fade to. <b>(Value range is 0f-1f)</b>
     * <p>
     *
     * @return Blue value of the second color
     */
    public float getFadeBlue() {
        return fadeBlue / 255f;
    }

    /**
     * Creates a new instance of the nms counterpart
     * of this class.
     * <p>
     * Please note that this class is not supported in
     * any versions before 1.17 and could lead to errors
     * if used in legacy versions.
     * <p>
     *
     * @return Nms counterpart of this class
     */
    @Override
    public Object toNMSData() {
        if (ParticleReflectionUtils.MINECRAFT_VERSION < 17 || getEffect() != ParticleEffect.DUST_COLOR_TRANSITION)
            return null;
        Object fadeStart = ParticleReflectionUtils.createVector3fa(getRed(), getGreen(), getBlue());
        Object fadeEnd = ParticleReflectionUtils.createVector3fa(getFadeRed(), getFadeGreen(), getFadeBlue());
        try {
            return ParticleConstants.PARTICLE_PARAM_DUST_COLOR_TRANSITION_CONSTRUCTOR.newInstance(fadeStart, fadeEnd, getSize());
        } catch (Exception ex) {
            return null;
        }
    }

}
