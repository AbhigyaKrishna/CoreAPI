package me.Abhigya.core.menu.inventory.custom.updating;

import me.Abhigya.core.menu.inventory.Item;
import me.Abhigya.core.menu.inventory.ItemMenu;
import me.Abhigya.core.menu.inventory.custom.updating.handler.UpdatingItemMenuHandler;
import me.Abhigya.core.menu.inventory.size.ItemMenuSize;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

/**
 * Implementation of {@link ItemMenu} handling updatable content item menu.
 */
public class UpdatingItemMenu extends ItemMenu {

    /**
     * Constructs the Updatable menu.
     * <p>
     *
     * @param title    Title of the menu
     * @param size     Size of the menu
     * @param parent   Parent of the menu if any. or null
     * @param contents Content of the menu
     */
    public UpdatingItemMenu(String title, ItemMenuSize size, ItemMenu parent, Item... contents) {
        super(title, size, parent, contents);
    }

    @Override
    public UpdatingItemMenuHandler getHandler() {
        return (UpdatingItemMenuHandler) this.handler;
    }

    @Override
    public boolean registerListener(Plugin plugin) {
        if (this.handler == null) {
            Bukkit.getPluginManager().registerEvents((this.handler = new UpdatingItemMenuHandler(this, plugin)), plugin);
            return true;
        }
        return false;
    }

}
