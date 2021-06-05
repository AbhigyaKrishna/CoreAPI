package me.Abhigya.core.menu.item.action;

public enum ItemActionPriority {

    /**
     * Should run at last.
     */
    LOW ,

    /**
     * Should run after the high priority and before low priority.
     */
    NORMAL ,

    /**
     * Should run first.
     */
    HIGH ;

}
