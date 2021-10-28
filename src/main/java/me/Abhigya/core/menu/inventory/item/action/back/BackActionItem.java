package me.Abhigya.core.menu.inventory.item.action.back;

import me.Abhigya.core.menu.inventory.action.ItemClickAction;
import me.Abhigya.core.menu.inventory.item.action.ActionItem;
import me.Abhigya.core.menu.inventory.item.action.ItemAction;
import me.Abhigya.core.menu.inventory.item.action.ItemActionPriority;
import org.bukkit.inventory.ItemStack;

/** Implementation of {@link ActionItem} handling back button. */
public class BackActionItem extends ActionItem {

    /**
     * Constructs the Back Action Item.
     *
     * <p>
     *
     * @param name Name of the Item
     * @param icon ItemStack icon of the Item
     * @param lore Lore of the Item
     */
    public BackActionItem(String name, ItemStack icon, String[] lore) {
        super(name, icon, lore);
        addAction(
                new ItemAction() {

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
