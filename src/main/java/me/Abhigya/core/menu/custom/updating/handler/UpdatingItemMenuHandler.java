package me.Abhigya.core.menu.custom.updating.handler;

import me.Abhigya.core.menu.ItemMenu;
import me.Abhigya.core.menu.handler.ItemMenuHandler;
import me.Abhigya.core.util.scheduler.SchedulerUtils;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

public class UpdatingItemMenuHandler extends ItemMenuHandler {

    protected BukkitTask updater_task;

    public UpdatingItemMenuHandler(ItemMenu menu, Plugin plugin) {
        super(menu, plugin);
    }

    public boolean isUpdating() {
        return updater_task != null && Bukkit.getScheduler().isCurrentlyRunning(updater_task.getTaskId());
    }

    @Override
    public void unregisterListener() {
        super.unregisterListener();
        stopUpdating();
    }

    public void startUpdating(int start_delay, int ticks) {
        stopUpdating();
        this.updater_task = SchedulerUtils.runTaskTimer(() -> {
            this.menu.updateOnlinePlayers();
        }, start_delay, ticks, plugin);
    }

    public void stopUpdating() {
        if (updater_task != null) {
            updater_task.cancel();
            updater_task = null;
        }
    }

}
