package me.Abhigya.core.menu.action;

import me.Abhigya.core.menu.ItemMenu;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

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

    public ItemMenuClickAction(ItemMenu menu, InventoryClickEvent event) {
        this(menu, event.getView(), event.getClick(), event.getAction(), event.getSlotType(), event.getSlot(),
                event.getRawSlot(), event.getCurrentItem(), event.getHotbarButton());
    }

    public ItemMenuClickAction(ItemMenu menu, InventoryView inventory_view, ClickType click_type, InventoryAction action,
                               SlotType slot_type, int slot, int raw_slot, ItemStack current, int hotbar_key) {
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

    public ItemMenu getMenu() {
        return menu;
    }

    public Inventory getInventory() {
        return this.getInventoryView().getTopInventory();
    }

    public InventoryView getInventoryView() {
        return inventory_view;
    }

    public Player getPlayer() {
        return (Player) this.getInventoryView().getPlayer();
    }

    public ClickType getClickType() {
        return click_type;
    }

    public InventoryAction getInventoryAction() {
        return action;
    }

    public SlotType getSlotType() {
        return slot_type;
    }

    public int getSlot() {
        return slot;
    }

    public int getRawSlot() {
        return raw_slot;
    }

    /**
     * Gets the current ItemStack on the cursor.
     * <p>
     *
     * @return Cursor ItemStack
     */
    public ItemStack getCursor() {
        return getInventoryView().getCursor();
    }

    /**
     * Gets the ItemStack currently in the clicked slot.
     * <p>
     *
     * @return Item in the clicked
     */
    public ItemStack getCurrentItem() {
        return current;
    }

    public int getHotbarKey() {
        return hotbar_key;
    }

    /**
     * Gets whether or not the ClickType for this event represents a right
     * click.
     * <p>
     *
     * @return true if the ClickType uses the right mouse button.
     * @see ClickType#isRightClick()
     */
    public boolean isRightClick() {
        return this.getClickType().isRightClick();
    }

    /**
     * Gets whether or not the ClickType for this event represents a left
     * click.
     * <p>
     *
     * @return true if the ClickType uses the left mouse button.
     * @see ClickType#isLeftClick()
     */
    public boolean isLeftClick() {
        return this.getClickType().isLeftClick();
    }

    /**
     * Gets whether the ClickType for this event indicates that the key was
     * pressed down when the click was made.
     * <p>
     *
     * @return true if the ClickType uses Shift or Ctrl.
     * @see ClickType#isShiftClick()
     */
    public boolean isShiftClick() {
        return this.getClickType().isShiftClick();
    }

    /**
     * Sets the item on the cursor.
     * <p>
     *
     * @param stack New cursor item
     * @deprecated This changes the ItemStack in their hand before any
     * calculations are applied to the Inventory, which has a tendency to
     * create inconsistencies between the Player and the server, and to
     * make unexpected changes in the behavior of the clicked Inventory.
     */
    @Deprecated
    public void setCursor(ItemStack stack) {
        getInventoryView().setCursor(stack);
    }

    /**
     * Sets the ItemStack currently in the clicked slot.
     * <p>
     *
     * @param current Item to be placed in the current slot
     */
    public void setCurrentItem(ItemStack current) {
        this.current = current;
    }

}
