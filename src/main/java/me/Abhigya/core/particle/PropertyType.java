package me.Abhigya.core.particle;

import me.Abhigya.core.particle.data.color.RegularColor;

/**
 * {@link Enum} to easily define which properties are supported by specific
 * {@link ParticleEffect ParticleEffects}.
 * <p>
 * Current Property types
 * <ul>
 * <li>{@link #REQUIRES_WATER}</li>
 * <li>{@link #REQUIRES_BLOCK}</li>
 * <li>{@link #REQUIRES_ITEM}</li>
 * <li>{@link #DIRECTIONAL}</li>
 * <li>{@link #COLORABLE}</li>
 * <li>{@link #RESIZEABLE}</li>
 * </ul>
 */
public enum PropertyType {
    /**
     * Directional particles accept a custom vector as the direction. This
     * data is set as the offsetX, offsetY and offsetZ of the particle packet.
     */
    DIRECTIONAL,
    /**
     * Specifies that the color of the given particle can be set. Before 1.13 the
     * RGB values of the color is simply set as the offsetX, offsetY and offsetZ of
     * the particle packet. But that has changed since 1.13. Redstone particles
     * now have a custom ParticleParam called "ParticleParamRedstone". However other
     * particles like spell_mob/effect_entity are still handled the old way.
     */
    COLORABLE,
    /**
     * Specifies that the given particle  needs the data of a block to be
     * functional. Before 1.13 this data was saved in an int array that
     * could easily be set in the constructor of the packet. However, because of
     * the major 1.13 particle changes this data can no longer be set in a simple
     * int array. There is now a custom object to set the data of such
     * particles. The class of this object is ParticleParamBlock and it
     * implements the ParticleParam interface, which is now needed in the constructor.
     */
    REQUIRES_BLOCK,
    /**
     * Specifies that the given particle needs the data of an item to be
     * functional. Before 1.13 this data was saved in an int array that could
     * easily be set in the constructor of the packet. However, because of the
     * major 1.13 particle changes this data can no longer be set in a simple int
     * array. There is now a custom object to set the data of such particles. The
     * class of this object is ParticleParamItem  and it implements the ParticleParam
     * interface, which is now needed in the constructor.
     */
    REQUIRES_ITEM,
    /**
     * Specifies that the given particle needs water in order to be displayed correctly.
     */
    REQUIRES_WATER,
    /**
     * Specifies that the size of the given particle can be changed in the offsetX parameter.
     */
    RESIZEABLE,
    /**
     * A dust particle accepts a custom color and a custom size (between 0-4). Please note that
     * this {@link PropertyType} is not supported on pre 1.13 servers.
     *
     * @see RegularColor
     * @see ParticleEffect#REDSTONE
     * @see ParticleEffect#DUST_COLOR_TRANSITION
     */
    DUST

}
