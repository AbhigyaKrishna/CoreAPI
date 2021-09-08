package me.Abhigya.core.menu.inventory.custom.updating.handler;

import me.Abhigya.core.menu.inventory.ItemMenu;
import me.Abhigya.core.menu.inventory.handler.ItemMenuHandler;
import me.Abhigya.core.util.scheduler.SchedulerUtils;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

/**
 * Implementation of  {@link ItemMenuHandler} for handling updatable menus.
 */
public class UpdatingItemMenuHandler extends ItemMenuHandler {

    protected BukkitTask updater_task;

    /**
     * Constructs the updating menu handler.
     * <p>
     *
     * @param menu   Updating item menu
     * @param plugin Plugin instance for the menu
     */
    public UpdatingItemMenuHandler(ItemMenu menu, Plugin plugin) {
        super(menu, plugin);
    }

    /**
     * Checks if the menu is updating.
     * <p>
     *
     * @return true if updating, else false
     */
    public boolean isUpdating() {
        return updater_task != null && Bukkit.getScheduler().isCurrentlyRunning(updater_task.getTaskId());
    }

    @Override
    public void unregisterListener() {
        super.unregisterListener();
        stopUpdating();
    }

    /**
     * Starts updating the menu.
     * <p>
     *
     * @param start_delay Start delay before updating menu
     * @param ticks       Ticks invterval between updating menu
     */
    public void startUpdating(int start_delay, int ticks) {
        stopUpdating();
        this.updater_task = SchedulerUtils.runTaskTimer(() -> {
            this.menu.updateOnlinePlayers();
        }, start_delay, ticks, plugin);
    }

    /**
     * Stops updating menu
     */
    public void stopUpdating() {
        if (updater_task != null) {
            updater_task.cancel();
            updater_task = null;
        }
    }

}
