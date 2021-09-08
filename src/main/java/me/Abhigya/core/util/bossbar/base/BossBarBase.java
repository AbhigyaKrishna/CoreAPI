package me.Abhigya.core.util.bossbar.base;

import me.Abhigya.core.util.bossbar.BossBar;
import org.apache.commons.lang.Validate;

/**
 * {@link BossBar} base class.
 */
public abstract class BossBarBase extends BossBar {

    /**
     * Checks the progress is between {@code 0.0} and {@code 1.0}.
     * <p>
     *
     * @param progress the progress to check.
     */
    protected void checkProgress(double progress) {
        Validate.isTrue(progress >= 0D && progress <= 1D, "progress must be between 0.0 and 1.0!");
    }

}