package me.Abhigya.core.menu.item.voidaction;

import me.Abhigya.core.menu.Item;
import me.Abhigya.core.menu.action.ItemClickAction;
import org.bukkit.inventory.ItemStack;

public class VoidActionItem extends Item {

    public VoidActionItem(String name, ItemStack icon, String... lore) {
        super(name, icon, lore);
    }

    @Override
    public final void onClick(ItemClickAction action) {
        /* do nothing */
    }

}
