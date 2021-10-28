package me.Abhigya.core.menu.inventory.action;

import me.Abhigya.core.menu.inventory.ItemMenu;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

/** Class for managing Item click action in menu. */
public class ItemClickAction {

    protected final ItemMenu menu;
    protected final InventoryView inventory_view;
    protected final ClickType click_type;
    protected final InventoryAction action;
    protected final SlotType slot_type;
    protected final int slot;
    protected final int raw_slot;
    protected final ItemStack clicked;
    protected int hotbar_key;
    protected boolean go_back;
    protected boolean close;
    protected boolean update;

    /**
     * Constructs the Item Click Action.
     *
     * <p>
     *
     * @param menu Item to bind action
     * @param event {@link InventoryClickEvent}
     */
    public ItemClickAction(ItemMenu menu, InventoryClickEvent event) {
        this(
                menu,
                event.getView(),
                event.getClick(),
                event.getAction(),
                event.getSlotType(),
                event.getSlot(),
                event.getRawSlot(),
                event.getCurrentItem(),
                event.getHotbarButton(),
                false,
                false,
                false);
    }

    /**
     * Constructs the Item Click Action.
     *
     * <p>
     *
     * @param menu ItemMenu to bind action
     * @param inventory_view Inventory View of the open inventory
     * @param click_type Click type
     * @param action InventoryAction type
     * @param slot_type {@link SlotType}
     * @param slot Slot index
     * @param raw_slot Raw slot index
     * @param clicked Clicked ItemStack
     * @param hotbar_key Hotbar Key
     * @param go_back Go back
     * @param close Close
     * @param update Update
     */
    public ItemClickAction(
            ItemMenu menu,
            InventoryView inventory_view,
            ClickType click_type,
            InventoryAction action,
            SlotType slot_type,
            int slot,
            int raw_slot,
            ItemStack clicked,
            int hotbar_key,
            boolean go_back,
            boolean close,
            boolean update) {
        this.menu = menu;
        this.inventory_view = inventory_view;
        this.click_type = click_type;
        this.action = action;
        this.slot_type = slot_type;
        this.slot = slot;
        this.raw_slot = raw_slot;
        this.clicked = clicked;
        this.hotbar_key = hotbar_key;
        this.go_back = go_back;
        this.close = close;
        this.update = update;
    }

    /**
     * Returns the menu.
     *
     * <p>
     *
     * @return ItemMenu
     */
    public ItemMenu getMenu() {
        return menu;
    }

    /**
     * Returns the Inventory.
     *
     * <p>
     *
     * @return Inventory
     */
    public Inventory getInventory() {
        return this.getInventoryView().getTopInventory();
    }

    /**
     * Returns the InventoryView.
     *
     * <p>
     *
     * @return InventoryView
     */
    public InventoryView getInventoryView() {
        return inventory_view;
    }

    /**
     * Returns the Player who clicked.
     *
     * <p>
     *
     * @return Player who clicked
     */
    public Player getPlayer() {
        return (Player) this.getInventoryView().getPlayer();
    }

    /**
     * Returns the ClickType.
     *
     * <p>
     *
     * @return ClickType
     */
    public ClickType getClickType() {
        return click_type;
    }

    /**
     * Returns the InventoryAction.
     *
     * <p>
     *
     * @return InventoryAction
     */
    public InventoryAction getInventoryAction() {
        return action;
    }

    /**
     * Returns the SlotType.
     *
     * <p>
     *
     * @return SlotType
     */
    public SlotType getSlotType() {
        return slot_type;
    }

    /**
     * Returns the slot index.
     *
     * <p>
     *
     * @return Slot index
     */
    public int getSlot() {
        return slot;
    }

    /**
     * Returns the raw slot index.
     *
     * <p>
     *
     * @return Raw slot index
     */
    public int getRawSlot() {
        return raw_slot;
    }

    /**
     * Returns the clicked ItemStack.
     *
     * <p>
     *
     * @return Clicked ItemStack
     */
    public ItemStack getClickedItem() {
        return clicked;
    }

    /**
     * Returns the Hotbar key.
     *
     * <p>
     *
     * @return Hotbar Key
     */
    public int getHotbarKey() {
        return hotbar_key;
    }

    /**
     * Returns the Go back boolean.
     *
     * <p>
     *
     * @return if it will go back
     */
    public boolean isWillGoBack() {
        return go_back;
    }

    /**
     * Returns the Close boolean.
     *
     * <p>
     *
     * @return if it will close
     */
    public boolean isWillClose() {
        return close;
    }

    /**
     * Returns the Update boolean.
     *
     * <p>
     *
     * @return if it will update
     */
    public boolean isWillUpdate() {
        return update;
    }

    /**
     * Sets the Go back boolean.
     *
     * <p>
     *
     * @param go_back Boolean value
     */
    public void setGoBack(boolean go_back) {
        this.go_back = go_back;
        if (go_back) {
            this.close = false;
            this.update = false;
        }
    }

    /**
     * Sets the Close boolean.
     *
     * <p>
     *
     * @param close Boolean value
     */
    public void setClose(boolean close) {
        this.close = close;
        if (close) {
            this.go_back = false;
            this.update = false;
        }
    }

    /**
     * Sets the Update boolean.
     *
     * <p>
     *
     * @param update Boolean value
     */
    public void setUpdate(boolean update) {
        this.update = update;
        if (update) {
            this.go_back = false;
            this.close = false;
        }
    }
}
