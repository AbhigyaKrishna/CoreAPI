package me.Abhigya.core.menu.inventory.item.action.close;

import me.Abhigya.core.menu.inventory.action.ItemClickAction;
import me.Abhigya.core.menu.inventory.item.action.ActionItem;
import me.Abhigya.core.menu.inventory.item.action.ItemAction;
import me.Abhigya.core.menu.inventory.item.action.ItemActionPriority;
import me.Abhigya.core.util.itemstack.safe.SafeItemStack;
import me.Abhigya.core.util.itemstack.stainedglass.StainedGlassColor;
import me.Abhigya.core.util.itemstack.stainedglass.StainedGlassItemStack;
import me.Abhigya.core.util.reflection.general.EnumReflection;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * Implementation of {@link ActionItem} handling the close menu option.
 */
public class CloseMenuActionItem extends ActionItem {

    @Deprecated // Compatibility with server versions <= 1.8
    public static final ItemStack DEFAULT_ICON = EnumReflection.getEnumConstant( Material.class, "BARRIER" ) != null
            ? new SafeItemStack( Material.BARRIER )
            : new StainedGlassItemStack( StainedGlassColor.RED, true );

    /**
     * Constructs the Close Menu Action Item.
     * <p>
     *
     * @param lore Lore of the Item
     */
    public CloseMenuActionItem( String... lore ) {
        this( ChatColor.RED + "Close", lore );
    }

    /**
     * Constructs the Close Menu Action Item.
     * <p>
     *
     * @param name Name of the Item
     * @param lore Lore of the Item
     */
    public CloseMenuActionItem( String name, String... lore ) {
        this( name, DEFAULT_ICON, lore );
    }

    /**
     * Constructs the Close Menu Action Item.
     * <p>
     *
     * @param name Name of the Item
     * @param icon ItemStack icon of the Item
     * @param lore Lore of the Item
     */
    public CloseMenuActionItem( String name, ItemStack icon, String... lore ) {
        super( name, icon, lore );
        addAction( new ItemAction( ) {

            @Override
            public ItemActionPriority getPriority( ) {
                return ItemActionPriority.LOW;
            }

            @Override
            public void onClick( ItemClickAction action ) {
                action.setClose( true );
            }
        } );
    }

}
