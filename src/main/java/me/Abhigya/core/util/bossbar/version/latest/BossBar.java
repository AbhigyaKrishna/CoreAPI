package me.Abhigya.core.util.bossbar.version.latest;

import me.Abhigya.core.main.CoreAPI;
import me.Abhigya.core.util.StringUtils;
import me.Abhigya.core.util.bossbar.BarColor;
import me.Abhigya.core.util.bossbar.BarFlag;
import me.Abhigya.core.util.bossbar.BarStyle;
import me.Abhigya.core.util.bossbar.base.BossBarBase;
import me.Abhigya.core.util.entity.UUIDPlayer;
import me.Abhigya.core.util.server.Version;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * A {@code BossBar} intended for server versions <strong>{@code >=}</strong> {@link
 * Version#v1_9_R1}.
 */
public class BossBar extends BossBarBase implements Listener {

    /** the bar's viewer */
    protected final UUIDPlayer player;

    /** the bukkit handle */
    protected final org.bukkit.boss.BossBar handle;

    /** flag to determine whether the bar must be destroyed because of the player is offline */
    protected boolean offline = false;

    /**
     * Constructs the {@code BossBar}.
     *
     * <p>
     *
     * @param title Initial title
     * @param progress Initial progress (must be between {@code 0.0} and {@code 1.0})
     * @param player Viewer player
     */
    public BossBar(String title, double progress, Player player) {
        this.player = new UUIDPlayer(player);
        this.handle =
                Bukkit.createBossBar(
                        StringUtils.EMPTY,
                        org.bukkit.boss.BarColor.PINK,
                        org.bukkit.boss.BarStyle.SOLID);
        this.handle.addPlayer(player);

        this.setTitle(title);
        this.setProgress(progress);

        Bukkit.getPluginManager().registerEvents(this, CoreAPI.getInstance().getHandlingPlugin());
    }

    @Override
    public String getTitle() {
        return this.handle.getTitle();
    }

    @Override
    public void setTitle(String title) {
        this.handle.setTitle(title);
        this.aliveCheck();
    }

    @Override
    public BarColor getColor() {
        return BarColor.valueOf(handle.getColor().name());
    }

    @Override
    public void setColor(BarColor color) {
        this.handle.setColor(org.bukkit.boss.BarColor.valueOf(color.name()));
        this.aliveCheck();
    }

    @Override
    public BarStyle getStyle() {
        return BarStyle.valueOf(handle.getStyle().name());
    }

    @Override
    public void setStyle(BarStyle style) {
        this.handle.setStyle(org.bukkit.boss.BarStyle.valueOf(style.name()));
        this.aliveCheck();
    }

    @Override
    public void removeFlag(BarFlag flag) {
        this.handle.removeFlag(org.bukkit.boss.BarFlag.valueOf(flag.name()));
        this.aliveCheck();
    }

    @Override
    public void addFlag(BarFlag flag) {
        this.handle.addFlag(org.bukkit.boss.BarFlag.valueOf(flag.name()));
        this.aliveCheck();
    }

    @Override
    public boolean hasFlag(BarFlag flag) {
        return this.handle.hasFlag(org.bukkit.boss.BarFlag.valueOf(flag.name()));
    }

    @Override
    public double getProgress() {
        return this.handle.getProgress();
    }

    @Override
    public void setProgress(double progress) {
        this.checkProgress(progress);
        this.handle.setProgress(progress);
        this.aliveCheck();
    }

    @Override
    public Player getPlayer() {
        return this.handle.getPlayers().get(0);
    }

    @Override
    public boolean isVisible() {
        return this.handle.isVisible();
    }

    @Override
    public void setVisible(boolean visible) {
        this.handle.setVisible(visible);
        this.aliveCheck();
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onDisconnect(PlayerQuitEvent event) {
        handle.removeAll();
        offline = true;
    }

    /**
     * Re-starts the updater when the player reconnects, and destroys the bar when player is
     * offline.
     */
    protected void aliveCheck() {
        Player player = this.player.get();

        if (player != null && player.isOnline()) {
            if (offline) {
                if (handle.getPlayers().isEmpty()
                        || handle.getPlayers().stream()
                                        .filter(
                                                other ->
                                                        other.getUniqueId()
                                                                .equals(player.getUniqueId()))
                                        .count()
                                == 0) {

                    handle.addPlayer(player);
                }

                offline = false;
            }
        } else {
            if (!offline) {
                handle.removeAll();
                offline = true;
            }
        }
    }
}
