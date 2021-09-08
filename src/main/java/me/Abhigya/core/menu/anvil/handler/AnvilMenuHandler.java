package me.Abhigya.core.menu.anvil.handler;

import me.Abhigya.core.menu.anvil.AnvilMenu;
import me.Abhigya.core.menu.anvil.action.AnvilMenuClickAction;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.plugin.Plugin;

public class AnvilMenuHandler implements Listener {

    protected final AnvilMenu menu;
    protected final Plugin plugin;

    public AnvilMenuHandler(AnvilMenu menu, Plugin plugin) {
        this.menu = menu;
        this.plugin = plugin;
    }

    public Plugin getPlugin() {
        return plugin;
    }

    /**
     * Unregisters this listener.
     */
    public void unregisterListener() {
        HandlerList.unregisterAll(this);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getInventory() != null && event.getInventory().getType() == InventoryType.ANVIL &&
                event.getWhoClicked() instanceof Player && this.menu.isMenuOpen((Player) event.getWhoClicked()) &&
                this.menu.isThisMenu(event.getInventory())) {
            AnvilMenuClickAction action = new AnvilMenuClickAction(this.menu, event);
            this.menu.onClick(action);
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onInventoryClose(InventoryCloseEvent event) {
        if (event.getInventory() != null && event.getInventory().getType() == InventoryType.ANVIL &&
                event.getPlayer() instanceof Player && this.menu.isMenuOpen((Player) event.getPlayer()) &&
                this.menu.isThisMenu(event.getInventory())) {
            for (AnvilMenu.OpenAnvilGui gui : this.menu.getOpenMenus()) {
                if (gui.getPlayer().equals(event.getPlayer())) {
                    this.menu.getOpenMenus().remove(gui);
                    break;
                }
            }
            event.getInventory().clear();
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPluginDisable(PluginDisableEvent event) {
        if (event.getPlugin().equals(this.plugin)) {
            this.menu.closeOnlinePlayers();
        }
    }

}
