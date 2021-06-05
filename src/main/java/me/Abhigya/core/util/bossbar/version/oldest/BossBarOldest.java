package me.Abhigya.core.util.bossbar.version.oldest;

import me.Abhigya.core.main.Main;
import me.Abhigya.core.util.bossbar.BarColor;
import me.Abhigya.core.util.bossbar.BarFlag;
import me.Abhigya.core.util.bossbar.BarStyle;
import me.Abhigya.core.util.bossbar.base.BossBarBase;
import me.Abhigya.core.util.entity.UUIDPlayer;
import me.Abhigya.core.util.math.VectorUtils;
import me.Abhigya.core.util.server.Version;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

/**
 * Base class for {@code BossBar}s for server versions {@code <=}
 * {@link Version#v1_8_R3}.
 */
public abstract class BossBarOldest extends BossBarBase implements Listener {

    protected static final float MINIMUM_PROGRESS = 0.00000000000000000000001F;
    protected static final float MAXIMUM_PROGRESS = 1F;

    protected static final String DEFAULT_TITLE = "untitled";
    protected static final double DEFAULT_PROGRESS = MAXIMUM_PROGRESS;

    /**
     * the bar's viewer
     */
    protected final UUIDPlayer player;
    /**
     * the bar's title
     */
    protected volatile String title = DEFAULT_TITLE;
    /**
     * The bar's progress
     */
    protected volatile double progress = DEFAULT_PROGRESS;
    /**
     * the bar's visibility status
     */
    private volatile boolean visible = true;
    /**
     * flag to determine if the bar has been destroyed because of the player was offline
     */
    private volatile boolean offline = false;

    /**
     * the update executor
     */
    protected volatile BukkitTask update_executor;
    /**
     * the bar's updater command
     */
    protected final Runnable update_command = () -> {
        if (isVisible()) {
            Player player = getPlayer();

            if (player == null || !player.isOnline()) {
                if (!offline) {
                    this.destroy();
                    this.offline = true;
                }
            } else {
                if (offline) {
                    this.create();
                    this.offline = false;
                }
            }

            if (!offline) {
                this.update();
            }
        } else {
            this.destroy();
        }
    };

    public BossBarOldest(String title, double progress, Player player) {
        Validate.isTrue(player.isOnline(), "the player must be online!");

        this.player = new UUIDPlayer(player);

        this.setTitle(title);
        this.setProgress(progress);
        this.create();

        Bukkit.getPluginManager().registerEvents(this, Main.getInstance());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onTeleport(final PlayerTeleportEvent event) {
        Bukkit.getScheduler().runTaskLater(Main.getInstance(), new Runnable() {
            @Override
            public void run() {
                Player player = event.getPlayer();

                if (player.getUniqueId().equals(BossBarOldest.this.player.getUniqueId())
                        && !offline && isVisible()) {
                    BossBarOldest.this.create();
                }
            }
        }, 10L);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onRespawn(final PlayerRespawnEvent event) {
        Bukkit.getScheduler().runTaskLater(Main.getInstance(), new Runnable() {
            @Override
            public void run() {
                Player player = event.getPlayer();

                if (player.getUniqueId().equals(BossBarOldest.this.player.getUniqueId())
                        && !offline && isVisible()) {
                    BossBarOldest.this.create();
                }
            }
        }, 10L);
    }

    /**
     * Creates the bar.
     */
    protected synchronized void create() {
        disposeExecutors();

        update_executor = Bukkit.getScheduler().runTaskTimerAsynchronously(Main.getInstance(),
                update_command, 0L, 0L);
    }

    /**
     * Updates the title and the progress of the bar.
     */
    protected synchronized void update() {
        throw new UnsupportedOperationException("not implemented yet");
    }

    /**
     * Destroys the bar.
     */
    protected synchronized void destroy() {
        disposeExecutors();
    }

    /**
     * Disposes the {@link #update_executor}.
     */
    protected synchronized void disposeExecutors() {
        if (update_executor != null) {
            update_executor.cancel();
            update_executor = null;
        }
    }

    @Override
    public synchronized String getTitle() {
        return this.title;
    }

    @Override
    public synchronized void setTitle(String title) {
        this.checkTitle(title);
        this.title = title;

        aliveCheck();
    }

    @Override
    public synchronized double getProgress() {
        return this.progress;
    }

    @Override
    public synchronized void setProgress(double progress) {
        this.checkProgress(progress);
        this.progress = progress;

        aliveCheck();
    }

    /**
     * Re-starts the updater when the player reconnects.
     */
    protected synchronized void aliveCheck() {
        if (offline) {
            Player player = getPlayer();

            if (player != null && player.isOnline()) {
                create();

                offline = false;
            }
        }
    }

    @Override
    public final synchronized boolean isVisible() {
        return this.visible;
    }

    @Override
    public final synchronized void setVisible(boolean visible) {
        if (visible != this.visible) {
            if (visible) {
                this.create();
            } else {
                this.destroy();
            }

            this.visible = visible;
        }
    }

    @Override
    public final Player getPlayer() {
        return player.get();
    }

    @Override
    public final BarColor getColor() {
        return BarColor.PINK;
    }

    @Override
    public final void setColor(BarColor color) {
    }

    @Override
    public final BarStyle getStyle() {
        return BarStyle.SOLID;
    }

    @Override
    public final void setStyle(BarStyle style) {
    }

    @Override
    public final void removeFlag(BarFlag flag) {
    }

    @Override
    public final void addFlag(BarFlag flag) {
    }

    @Override
    public final boolean hasFlag(BarFlag flag) {
        return false;
    }

    /**
     * Checks the provided title is valid.
     * <p>
     *
     * @param title Title to check
     */
    protected void checkTitle(String title) {
        Validate.notNull(title, "the title cannot be null!");
    }

    /**
     * Calculates and gets the location for the handle entity.
     * <p>
     *
     * @return Location for the handle entity
     */
    protected Location calculateHandleLocation() {
        Vector direction = getPlayer().getLocation().getDirection().multiply(20D);
        direction = VectorUtils.rotateAroundAxisY(direction, Math.toRadians(15.0D));
        direction = VectorUtils.rotateAroundAxisX(direction, Math.toRadians(15.0D));
        direction = VectorUtils.rotateAroundAxisZ(direction, Math.toRadians(15.0D));

        return getPlayer().getLocation().add(0.0D, 3.0D, 0.0D).add(direction);
    }
}
