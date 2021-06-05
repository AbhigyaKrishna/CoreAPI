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

    public ItemClickAction(ItemMenu menu, InventoryClickEvent event) {
        this(menu, event.getView(), event.getClick(), event.getAction(), event.getSlotType(), event.getSlot(),
                event.getRawSlot(), event.getCurrentItem(), event.getHotbarButton(), false, false, false);
    }

    public ItemClickAction(ItemMenu menu, InventoryView inventory_view, ClickType click_type, InventoryAction action,
                           SlotType slot_type, int slot, int raw_slot, ItemStack clicked, int hotbar_key, boolean go_back,
                           boolean close, boolean update) {
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

    public ItemStack getClickedItem() {
        return clicked;
    }

    public int getHotbarKey() {
        return hotbar_key;
    }

    public boolean isWillGoBack() {
        return go_back;
    }

    public boolean isWillClose() {
        return close;
    }

    public boolean isWillUpdate() {
        return update;
    }

    public void setGoBack(boolean go_back) {
        this.go_back = go_back;
        if (go_back) {
            this.close = false;
            this.update = false;
        }
    }

    public void setClose(boolean close) {
        this.close = close;
        if (close) {
            this.go_back = false;
            this.update = false;
        }
    }

    public void setUpdate(boolean update) {
        this.update = update;
        if (update) {
            this.go_back = false;
            this.close = false;
        }
    }

}
