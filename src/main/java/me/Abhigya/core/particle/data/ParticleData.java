package me.Abhigya.core.particle.data;

import me.Abhigya.core.particle.ParticleEffect;

/**
 * A Object to easier hold data of a particle.
 */
public abstract class ParticleData {

    /**
     * The {@link ParticleEffect} the current {@link ParticleData} instance is
     * assigned to.
     */
    private ParticleEffect effect;

    /**
     * Sets the {@link ParticleEffect}.
     * <p>
     *
     * @param effect {@link ParticleEffect} that should be displayed.
     */
    public void setEffect(ParticleEffect effect) {
        this.effect = effect;
    }

    /**
     * Converts the current {@link ParticleData} instance into nms data. If the current
     * minecraft version was released before 1.13 a int array should be returned. If the
     * version was released after 1.12 a nms "ParticleParam" has to be returned.
     * <p>
     *
     * @return Nms data
     */
    public abstract Object toNMSData();

    /**
     * Gets the {@link ParticleEffect} the current {@link ParticleData} is assigned to.
     * <p>
     *
     * @return Current {@link ParticleEffect}
     */
    public ParticleEffect getEffect() {
        return effect;
    }

}
