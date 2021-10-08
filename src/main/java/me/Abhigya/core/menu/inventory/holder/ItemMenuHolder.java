package me.Abhigya.core.menu.inventory.holder;

import me.Abhigya.core.menu.inventory.ItemMenu;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

/**
 * Implementation of {@link InventoryHolder} interacting with ItemMenu.
 */
public class ItemMenuHolder implements InventoryHolder {

    private final ItemMenu menu;
    private final Inventory inventory;

    /**
     * Constructs the ItemMenuHolder.
     * <p>
     *
     * @param menu      ItemMenu
     * @param inventory Inventory
     */
    public ItemMenuHolder( ItemMenu menu, Inventory inventory ) {
        this.menu = menu;
        this.inventory = inventory;
    }

    /**
     * Returns the ItemMenu.
     * <p>
     *
     * @return ItemMenu
     */
    public ItemMenu getItemMenu( ) {
        return menu;
    }

    /**
     * Returns the Inventory.
     * <p>
     *
     * @return Inventory
     */
    @Override
    public Inventory getInventory( ) {
        return inventory;
    }

}
