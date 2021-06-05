package me.Abhigya.core.menu.item.action;

import me.Abhigya.core.menu.action.ItemClickAction;

public interface ItemAction {

    public ItemActionPriority getPriority();

    public void onClick(ItemClickAction action);

}
