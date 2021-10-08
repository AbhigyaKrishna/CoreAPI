package me.Abhigya.core.menu.anvil.action;

import me.Abhigya.core.menu.anvil.AnvilMenu;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

public class AnvilMenuClickAction {

    protected final AnvilMenu menu;
    protected final InventoryView inventory_view;
    protected final ClickType click_type;
    protected final InventoryAction action;
    protected final InventoryType.SlotType slot_type;
    protected final int raw_slot;
    protected ItemStack current;
    protected int hotbar_key;

    public AnvilMenuClickAction( AnvilMenu menu, InventoryClickEvent event ) {
        this( menu, event.getView( ), event.getClick( ), event.getAction( ), event.getSlotType( ), event.getCurrentItem( ), event.getRawSlot( ), event.getHotbarButton( ) );
    }

    public AnvilMenuClickAction( AnvilMenu menu, InventoryView view, ClickType click_type, InventoryAction action, InventoryType.SlotType slot_type, ItemStack current, int raw_slot, int hotbar_key ) {
        this.menu = menu;
        this.inventory_view = view;
        this.click_type = click_type;
        this.action = action;
        this.slot_type = slot_type;
        this.current = current;
        this.raw_slot = raw_slot;
        this.hotbar_key = hotbar_key;
    }

    public AnvilMenu getMenu( ) {
        return menu;
    }

    public InventoryView getInventoryView( ) {
        return inventory_view;
    }

    public ClickType getClickType( ) {
        return click_type;
    }

    public InventoryAction getAction( ) {
        return action;
    }

    public InventoryType.SlotType getSlot_type( ) {
        return slot_type;
    }

    public ItemStack getCursor( ) {
        return this.getInventoryView( ).getCursor( );
    }

    public ItemStack getCurrent( ) {
        return current;
    }

    public int getRaw_slot( ) {
        return raw_slot;
    }

    public int getHotbarKey( ) {
        return hotbar_key;
    }

    public boolean isRightClick( ) {
        return this.getClickType( ).isRightClick( );
    }

    public boolean isLeftClick( ) {
        return this.getClickType( ).isLeftClick( );
    }

    public boolean isShiftClick( ) {
        return this.getClickType( ).isShiftClick( );
    }

}
