package me.Abhigya.core.menu.inventory.item.action;

import me.Abhigya.core.menu.inventory.action.ItemClickAction;

/** Class to classify menu item action. */
public interface ItemAction {

    /**
     * Returns the priority of the action.
     *
     * <p>
     *
     * @return {@link ItemActionPriority}
     */
    public ItemActionPriority getPriority();

    /**
     * Click action on the item.
     *
     * <p>
     *
     * @param action {@link ItemClickAction} on the Item
     */
    public void onClick(ItemClickAction action);
}
