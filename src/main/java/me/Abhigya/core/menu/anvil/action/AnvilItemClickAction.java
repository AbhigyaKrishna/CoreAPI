package me.Abhigya.core.menu.anvil.action;

import me.Abhigya.core.menu.anvil.AnvilMenu;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

/** Class for managing AnvilItem click action in menu. */
public class AnvilItemClickAction {

    protected final AnvilMenu menu;
    protected final InventoryView inventory_view;
    protected final ClickType click_type;
    protected final InventoryAction action;
    protected final InventoryType.SlotType slot_type;
    protected final int raw_slot;
    protected final ItemStack clicked;
    protected int hotbar_key;
    protected boolean update;

    /**
     * Constructs the Item Click Action.
     *
     * <p>
     *
     * @param menu AnvilItem to bind action
     * @param event {@link InventoryClickEvent}
     */
    public AnvilItemClickAction(AnvilMenu menu, InventoryClickEvent event) {
        this(
                menu,
                event.getView(),
                event.getClick(),
                event.getAction(),
                event.getSlotType(),
                event.getRawSlot(),
                event.getCurrentItem(),
                event.getHotbarButton(),
                false);
    }

    public AnvilItemClickAction(
            AnvilMenu menu,
            InventoryView view,
            ClickType click_type,
            InventoryAction action,
            InventoryType.SlotType slot_type,
            int raw_slot,
            ItemStack clicked,
            int hotbar_key,
            boolean update) {
        this.menu = menu;
        this.inventory_view = view;
        this.click_type = click_type;
        this.action = action;
        this.slot_type = slot_type;
        this.raw_slot = raw_slot;
        this.clicked = clicked;
        this.hotbar_key = hotbar_key;
        this.update = update;
    }

    public AnvilMenu getMenu() {
        return menu;
    }

    public Inventory getInventory() {
        return this.getInventoryView().getTopInventory();
    }

    public InventoryView getInventoryView() {
        return this.inventory_view;
    }

    public Player getPlayer() {
        return (Player) this.getInventoryView().getPlayer();
    }

    public ClickType getClickType() {
        return this.click_type;
    }

    public InventoryAction getInventoryAction() {
        return this.action;
    }

    public InventoryType.SlotType getSlotType() {
        return this.slot_type;
    }

    public int getRawSlot() {
        return this.raw_slot;
    }

    public ItemStack getClickedItem() {
        return this.clicked;
    }

    public int getHotbarKey() {
        return this.hotbar_key;
    }

    public boolean isWillUpdate() {
        return this.update;
    }

    public void setUpdate(boolean update) {
        this.update = update;
    }
}
