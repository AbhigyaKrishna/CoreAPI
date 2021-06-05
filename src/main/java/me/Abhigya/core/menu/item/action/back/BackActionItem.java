package me.Abhigya.core.menu.item.action.back;

import me.Abhigya.core.menu.action.ItemClickAction;
import me.Abhigya.core.menu.item.action.ActionItem;
import me.Abhigya.core.menu.item.action.ItemAction;
import me.Abhigya.core.menu.item.action.ItemActionPriority;
import org.bukkit.inventory.ItemStack;

public class BackActionItem extends ActionItem {

    public BackActionItem(String name, ItemStack icon, String[] lore) {
        super(name, icon, lore);
        addAction(new ItemAction() {

            @Override
            public ItemActionPriority getPriority() {
                return ItemActionPriority.LOW;
            }

            @Override
            public void onClick(ItemClickAction action) {
                action.setGoBack(true);
            }
        });
    }

}
