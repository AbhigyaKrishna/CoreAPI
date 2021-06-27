package me.Abhigya.core.particle.data.color;

import me.Abhigya.core.particle.ParticleEffect;
import me.Abhigya.core.particle.data.ParticleData;
import me.Abhigya.core.particle.utils.MathUtils;

/**
 * A implementation of the {@link ParticleColor} class to support note colors.
 */
public class NoteColor extends ParticleColor {

    /**
     * Initializes a new {@link ParticleData} object.
     * <p>
     *
     * @param note Note that should be displayed
     */
    public NoteColor(int note) {
        super(MathUtils.getMaxOrMin(note, 24, 0), 0, 0);
        setEffect(ParticleEffect.NOTE);
    }

    /**
     * Sets the {@link ParticleEffect}.
     * <p>
     *
     * @param effect {@link ParticleEffect} that should be displayed
     */
    @Override
    public void setEffect(ParticleEffect effect) {
        super.setEffect(ParticleEffect.NOTE);
    }

    /**
     * Gets the red value of the color.
     * <p>
     *
     * @return Red value
     */
    @Override
    public float getRed() {
        return super.getRed() / 24f;
    }

    /**
     * Returns 0 because the offsetY isn't used by the color of notes.
     * <p>
     *
     * @return 0
     */
    @Override
    public float getGreen() {
        return 0;
    }

    /**
     * Returns 0 because the offsetZ isn't used by the color of notes.
     * <p>
     *
     * @return 0
     */
    @Override
    public float getBlue() {
        return 0;
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
    public Object toNMSData() {
        return new int[0];
    }

    /**
     * Generates a random {@link NoteColor} instance to support rainbow trails,
     * cloaks and other effects that can be constructed using the note particle.
     * <p>
     *
     * @return Random {@link NoteColor} instance
     */
    public static NoteColor random() {
        return new NoteColor(MathUtils.generateRandomInteger(0, 24));
    }

}
