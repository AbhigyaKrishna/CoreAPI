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

/** Class for managing ItemMenu click action in menu. */
public class ItemMenuClickAction {

    protected final ItemMenu menu;
    protected final InventoryView inventory_view;
    protected final ClickType click_type;
    protected final InventoryAction action;
    protected final SlotType slot_type;
    protected final int slot;
    protected final int raw_slot;
    protected ItemStack current;
    protected int hotbar_key;

    /**
     * Constructs the Item Menu Click Action.
     *
     * <p>
     *
     * @param menu ItemMenu to bind action
     * @param event {@link InventoryClickEvent}
     */
    public ItemMenuClickAction(ItemMenu menu, InventoryClickEvent event) {
        this(
                menu,
                event.getView(),
                event.getClick(),
                event.getAction(),
                event.getSlotType(),
                event.getSlot(),
                event.getRawSlot(),
                event.getCurrentItem(),
                event.getHotbarButton());
    }

    /**
     * Constructs the Item Menu Click Action.
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
     * @param current Current clicked ItemStack
     * @param hotbar_key Hotbar Key
     */
    public ItemMenuClickAction(
            ItemMenu menu,
            InventoryView inventory_view,
            ClickType click_type,
            InventoryAction action,
            SlotType slot_type,
            int slot,
            int raw_slot,
            ItemStack current,
            int hotbar_key) {
        this.menu = menu;
        this.inventory_view = inventory_view;
        this.click_type = click_type;
        this.action = action;
        this.slot_type = slot_type;
        this.slot = slot;
        this.raw_slot = raw_slot;
        this.current = current;
        this.hotbar_key = hotbar_key;
    }

    /**
     * Returns the current menu.
     *
     * <p>
     *
     * @return ItemMenu
     */
    public ItemMenu getMenu() {
        return menu;
    }

    /**
     * Returns the current inventory.
     *
     * <p>
     *
     * @return Inventory
     */
    public Inventory getInventory() {
        return this.getInventoryView().getTopInventory();
    }

    /**
     * Returns the current InventoryView.
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
     * Returns the {@link InventoryAction}.
     *
     * <p>
     *
     * @return {@link InventoryAction}
     */
    public InventoryAction getInventoryAction() {
        return action;
    }

    /**
     * Returns the {@link SlotType}.
     *
     * <p>
     *
     * @return {@link SlotType}
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
     * Returns the current ItemStack on the cursor.
     *
     * <p>
     *
     * @return Cursor ItemStack
     */
    public ItemStack getCursor() {
        return getInventoryView().getCursor();
    }

    /**
     * Sets the item on the cursor.
     *
     * <p>
     *
     * @param stack New cursor item
     * @deprecated This changes the ItemStack in their hand before any calculations are applied to
     *     the Inventory, which has a tendency to create inconsistencies between the Player and the
     *     server, and to make unexpected changes in the behavior of the clicked Inventory.
     */
    @Deprecated
    public void setCursor(ItemStack stack) {
        getInventoryView().setCursor(stack);
    }

    /**
     * Returns the ItemStack currently in the clicked slot.
     *
     * <p>
     *
     * @return Item in the clicked
     */
    public ItemStack getCurrentItem() {
        return current;
    }

    /**
     * Sets the ItemStack currently in the clicked slot.
     *
     * <p>
     *
     * @param current Item to be placed in the current slot
     */
    public void setCurrentItem(ItemStack current) {
        this.current = current;
    }

    public int getHotbarKey() {
        return hotbar_key;
    }

    /**
     * Returns whether or not the ClickType for this event represents a right click.
     *
     * <p>
     *
     * @return true if the ClickType uses the right mouse button
     * @see ClickType#isRightClick()
     */
    public boolean isRightClick() {
        return this.getClickType().isRightClick();
    }

    /**
     * Returns whether or not the ClickType for this event represents a left click.
     *
     * <p>
     *
     * @return true if the ClickType uses the left mouse button
     * @see ClickType#isLeftClick()
     */
    public boolean isLeftClick() {
        return this.getClickType().isLeftClick();
    }

    /**
     * Returns whether the ClickType for this event indicates that the key was pressed down when the
     * click was made.
     *
     * <p>
     *
     * @return true if the ClickType uses Shift or Ctrl
     * @see ClickType#isShiftClick()
     */
    public boolean isShiftClick() {
        return this.getClickType().isShiftClick();
    }
}
