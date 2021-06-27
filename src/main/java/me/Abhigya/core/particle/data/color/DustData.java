package me.Abhigya.core.particle.data.color;

import me.Abhigya.core.particle.ParticleConstants;
import me.Abhigya.core.particle.ParticleEffect;
import me.Abhigya.core.particle.PropertyType;
import me.Abhigya.core.particle.utils.ParticleReflectionUtils;

import java.awt.*;

/**
 * The DustData class hols information on the colors
 * and size of various dust particles. The standard
 * implementation below supports a custom color and
 * size. If you're looking for Transitioning colors
 * check out {@link DustColorTransitionData} for more
 * information. The size of these particles has to
 * lie between 0-4. Every size above 4 is displayed
 * as size 4.
 * <p>
 * Please note that the size also influences the
 * display time of the particle.
 *
 * @author ByteZ
 * @see PropertyType#DUST
 * @see RegularColor
 * @see ParticleEffect#REDSTONE
 * @see ParticleEffect#DUST_COLOR_TRANSITION
 */
public class DustData extends RegularColor{

    /**
     * The size of the dust particle. <b>(Range 0.0-4.0)</b>
     */
    private final float size;

    /**
     * Creates a new {@link DustData} instance.
     * <p>
     *
     * @param color Color of the dust particle
     * @param size  Size of the particle
     */
    public DustData(Color color, float size) {
        super(color);
        this.size = size;
    }

    /**
     * Creates a new {@link DustData} instance.
     * <p>
     *
     * @param red   Red value of the color
     * @param green Green value of the color
     * @param blue  Blue value of the color
     * @param size  Size of the particle
     */
    public DustData(int red, int green, int blue, float size) {
        super(red, green, blue);
        this.size = size;
    }

    /**
     * Gets the size of the particle.
     * <p>
     *
     * @return Size of the particle
     */
    public float getSize() {
        return size;
    }

    /**
     * Converts the underlying {@link DustData} into it's
     * nms counterparts. The return type is dependent on
     * the value of the {@link #getEffect()}. If the getter
     * returns neither {@link ParticleEffect#REDSTONE} or
     * {@link ParticleEffect#DUST_COLOR_TRANSITION},
     * then {@code null} is returned.
     * <p>
     * Please note that this class is not supported in
     * any versions before 1.13 and could lead to errors
     * if used in legacy versions.
     * <p>
     *
     * @return Nms counterpart of this {@link DustData}
     */
    @Override
    public Object toNMSData() {
        try {
            if (ParticleReflectionUtils.MINECRAFT_VERSION < 13 || getEffect() == null || !getEffect().hasProperty(PropertyType.DUST))
                return new int[0];
            else if (ParticleReflectionUtils.MINECRAFT_VERSION < 17 && getEffect() == ParticleEffect.REDSTONE)
                return ParticleConstants.PARTICLE_PARAM_REDSTONE_CONSTRUCTOR.newInstance(getRed(), getGreen(), getBlue(), getSize());
            else if (ParticleReflectionUtils.MINECRAFT_VERSION >= 17) {
                Object colorVector = ParticleReflectionUtils.createVector3fa(getRed(), getGreen(), getBlue());
                return getEffect() == ParticleEffect.REDSTONE
                        ? ParticleConstants.PARTICLE_PARAM_REDSTONE_CONSTRUCTOR.newInstance(colorVector, getSize())
                        : ParticleConstants.PARTICLE_PARAM_DUST_COLOR_TRANSITION_CONSTRUCTOR.newInstance(colorVector, colorVector, getSize());
            }
        } catch (Exception ignored) {
        }
        return null;
    }

}
