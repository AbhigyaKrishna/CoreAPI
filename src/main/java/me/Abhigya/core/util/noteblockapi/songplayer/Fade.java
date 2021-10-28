package me.Abhigya.core.util.noteblockapi.songplayer;

import me.Abhigya.core.util.noteblockapi.model.FadeType;
import me.Abhigya.core.util.noteblockapi.utils.Interpolator;

public class Fade {

    private FadeType type;
    private byte fadeStart;
    private byte fadeTarget;
    private int fadeDuration;
    private int fadeDone = 0;

    /**
     * Create new fade effect
     *
     * @param type Type of fade effect
     * @param fadeDuration - duration of fade effect in ticks
     */
    public Fade(FadeType type, int fadeDuration) {
        this.type = type;
        this.fadeDuration = fadeDuration;
    }

    protected byte calculateFade() {
        switch (type) {
            case LINEAR:
                if (fadeDone == fadeDuration) {
                    return -1; // no fade today
                }
                double targetVolume =
                        Interpolator.interpLinear(
                                new double[] {0, fadeStart, fadeDuration, fadeTarget}, fadeDone);
                fadeDone++;
                return (byte) targetVolume;
            default:
                fadeDone++;
                return -1;
        }
    }

    protected int getFadeDone() {
        return fadeDone;
    }

    protected void setFadeDone(int fadeDone) {
        this.fadeDone = fadeDone;
    }

    /**
     * Returns fade effect type
     *
     * @return {@link FadeType}
     */
    public FadeType getType() {
        return type;
    }

    /**
     * Set fade effect type
     *
     * @param type FadeType
     */
    public void setType(FadeType type) {
        this.type = type;
    }

    /**
     * Returns duration of fade effect
     *
     * @return duration in ticks
     */
    public int getFadeDuration() {
        return fadeDuration;
    }

    /**
     * Set fade effect duration
     *
     * @param fadeDuration duration in ticks
     */
    public void setFadeDuration(int fadeDuration) {
        this.fadeDuration = fadeDuration;
    }

    protected byte getFadeStart() {
        return fadeStart;
    }

    protected void setFadeStart(byte fadeStart) {
        this.fadeStart = fadeStart;
    }

    protected byte getFadeTarget() {
        return fadeTarget;
    }

    protected void setFadeTarget(byte fadeTarget) {
        this.fadeTarget = fadeTarget;
    }

    public boolean isDone() {
        return fadeDone >= fadeDuration;
    }
}
