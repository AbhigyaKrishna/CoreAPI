package me.Abhigya.core.menu.custom.updating;

import me.Abhigya.core.menu.Item;
import me.Abhigya.core.menu.ItemMenu;
import me.Abhigya.core.menu.custom.updating.handler.UpdatingItemMenuHandler;
import me.Abhigya.core.menu.size.ItemMenuSize;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class UpdatingItemMenu extends ItemMenu {

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
