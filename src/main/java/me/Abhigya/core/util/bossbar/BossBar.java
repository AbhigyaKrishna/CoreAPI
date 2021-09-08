package me.Abhigya.core.util.bossbar;

import me.Abhigya.core.util.server.Version;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;

/**
 * A {@code BossBar} is a bar that is displayed at the top of the screen.
 */
public abstract class BossBar {

    /**
     * The package that holds the different versions of {@code BossBar}.
     */
    protected static final String VERSIONS_PACKAGE = "me.Abhigya.core.util.bossbar.version.";

    /**
     * Creates a boss bar instance to display to players.
     * <p>
     *
     * @param player   Player viewer
     * @param title    Title of the boss bar
     * @param progress Progress of the boss bar
     * @param color    Color of the boss bar
     * @param style    Style of the boss bar
     * @param flags    Optional list of flags to set on the boss bar
     * @return Created boss bar
     */
    public static BossBar createBossBar(Player player, String title, double progress, BarColor color, BarStyle style,
                                        BarFlag... flags) {
        if (Version.getServerVersion().isNewerEquals(Version.v1_9_R1)) {
            final BossBar bossbar = new me.Abhigya.core.util.bossbar.version.latest.BossBar(title, progress, player);
            bossbar.setColor(color);
            bossbar.setStyle(style);

            for (BarFlag flag : flags) {
                bossbar.addFlag(flag);
            }

            return bossbar;
        }

        try {
            final Class<?> c0 = Class.forName(VERSIONS_PACKAGE + Version.getServerVersion().name() + ".BossBar");
            final Class<? extends BossBar> c1 = c0.asSubclass(BossBar.class);

            return c1.getConstructor(String.class, double.class, Player.class)
                    .newInstance(title, progress, player);
        } catch (ClassNotFoundException ex) {
            throw new UnsupportedOperationException("unsupported server version!");
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
                | NoSuchMethodException | SecurityException ex_b) {
            ex_b.printStackTrace();
        }
        return null;
    }

    /**
     * Creates a boss bar instance to display to players. The progress defaults to
     * {@code 1.0}.
     * <p>
     *
     * @param player Player viewer
     * @param title  Title of the boss bar
     * @param color  Color of the boss bar
     * @param style  Style of the boss bar
     * @param flags  Optional list of flags to set on the boss bar
     * @return Created boss bar
     */
    public static BossBar createBossBar(Player player, String title, BarColor color, BarStyle style, BarFlag... flags) {
        return createBossBar(player, title, 1F, color, style, flags);
    }

    /**
     * Creates a boss bar instance to display to players. The color defaults to
     * {@link BarColor#PINK}. The style defaults to {@link BarStyle#SOLID}.
     * <p>
     *
     * @param player   Player viewer
     * @param title    Title of the boss bar
     * @param progress Progress of the boss bar
     * @return Created boss bar
     */
    public static BossBar createBossBar(Player player, String title, double progress) {
        return createBossBar(player, title, 1F, BarColor.PINK, BarStyle.SOLID);
    }

    /**
     * Creates a boss bar instance to display to players. The color defaults to
     * {@link BarColor#PINK}. The style defaults to {@link BarStyle#SOLID}. The
     * progress defaults to {@code 1.0}.
     * <p>
     *
     * @param player Player viewer
     * @param title  Title of the boss bar
     * @return Created boss bar
     */
    public static BossBar createBossBar(Player player, String title) {
        return createBossBar(player, title, 1F);
    }

    /**
     * Returns the title of this boss bar
     * <p>
     *
     * @return Title of the bar
     */
    public abstract String getTitle();

    /**
     * Sets the title of this boss bar
     * <p>
     *
     * @param title Title of the bar
     */
    public abstract void setTitle(String title);

    /**
     * Returns the color of this boss bar
     * <p>
     *
     * @return Color of the bar
     */
    public abstract BarColor getColor();

    /**
     * Sets the color of this boss bar.
     * <p>
     *
     * @param color Color of the bar
     */
    public abstract void setColor(BarColor color);

    /**
     * Returns the style of this boss bar
     * <p>
     *
     * @return Style of the bar
     */
    public abstract BarStyle getStyle();

    /**
     * Sets the bar style of this boss bar
     * <p>
     *
     * @param style Style of the bar
     */
    public abstract void setStyle(BarStyle style);

    /**
     * Remove an existing flag on this boss bar
     * <p>
     *
     * @param flag Existing flag to remove
     */
    public abstract void removeFlag(BarFlag flag);

    /**
     * Add an optional flag to this boss bar
     * <p>
     *
     * @param flag Optional flag to set on the boss bar
     */
    public abstract void addFlag(BarFlag flag);

    /**
     * Returns whether this boss bar as the passed flag set
     * <p>
     *
     * @param flag Flag to check
     * @return Whether it has the flag
     */
    public abstract boolean hasFlag(BarFlag flag);

    /**
     * Returns the progress of the bar between 0.0 and 1.0
     * <p>
     *
     * @return Progress of the bar
     */
    public abstract double getProgress();

    /**
     * Sets the progress of the bar. Values should be between 0.0 (empty) and 1.0
     * (full)
     * <p>
     *
     * @param progress Progress of the bar
     */
    public abstract void setProgress(double progress);

    /**
     * Returns the player viewing this boss bar
     * <p>
     *
     * @return Player viewing this bar
     */
    public abstract Player getPlayer();

    /**
     * Return if the boss bar is displayed to attached player.
     * <p>
     *
     * @return Visible status
     */
    public abstract boolean isVisible();

    /**
     * Set if the boss bar is displayed to attached player.
     * <p>
     *
     * @param visible Visible status
     */
    public abstract void setVisible(boolean visible);

    /**
     * Shows the previously hidden boss bar to all attached player
     * <p>
     * This is the equivalent of calling {@link #setVisible(boolean)} providing
     * <strong>{@code false}</strong>.
     */
    public void show() {
        setVisible(true);
    }

    /**
     * Hides this boss bar from all attached player
     * <p>
     * This is the equivalent of calling {@link #setVisible(boolean)} providing
     * <strong>{@code true}</strong>.
     */
    public void hide() {
        setVisible(false);
    }
}
