package me.Abhigya.core.menu.inventory.item.action;

import me.Abhigya.core.menu.inventory.Item;
import me.Abhigya.core.menu.inventory.action.ItemClickAction;
import org.apache.commons.lang.Validate;
import org.bukkit.inventory.ItemStack;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/** Implementation of {@link Item}. */
public class ActionItem extends Item {

    private final Set<ItemAction> actions;

    /**
     * Constructs the menu Action Item.
     *
     * <p>
     *
     * @param name Name of the Item
     * @param icon ItemStack icon of the Item
     * @param lore Lore of the Item
     */
    public ActionItem(String name, ItemStack icon, String... lore) {
        super(name, icon, lore);
        this.actions = new HashSet<>();
    }

    /**
     * Constructs the menu Action Item.
     *
     * <p>
     *
     * @param name Name of the Item
     * @param icon ItemStack icon of the Item
     * @param lore Lore of the Item
     */
    public ActionItem(String name, ItemStack icon, List<String> lore) {
        super(name, icon, lore);

        this.actions = new HashSet<>();
    }

    /**
     * Constructs the menu Action Item.
     *
     * <p>
     *
     * @param icon ItemStack icon of the Item
     */
    public ActionItem(ItemStack icon) {
        super(icon);
        this.actions = new HashSet<>();
    }

    /**
     * Add action to the Action Item.
     *
     * <p>
     *
     * @param action Item Action
     * @return This Object, for chaining
     */
    public ActionItem addAction(ItemAction action) {
        Validate.notNull(action, "The action cannot be null!");
        Validate.notNull(action.getPriority(), "The action priority cannot be null!");
        actions.add(action);
        return this;
    }

    /**
     * Remove action from the Action Item.
     *
     * <p>
     *
     * @param action Item Action
     * @return This Object, for chaining
     */
    public ActionItem removeAction(ItemAction action) {
        actions.remove(action);
        return this;
    }

    @Override
    public void onClick(ItemClickAction action) {
        for (int i = ItemActionPriority.values().length - 1;
                i >= 0;
                i--) { // call in order, from high to low
            ItemActionPriority priority = ItemActionPriority.values()[i];
            actions.stream()
                    .filter(act -> act != null && priority.equals(act.getPriority()))
                    .forEach(act -> act.onClick(action));
        }
    }
}
