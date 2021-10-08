package me.Abhigya.core.item;

import com.google.common.base.Objects;
import me.Abhigya.core.util.StringUtils;
import me.Abhigya.core.util.itemstack.ItemMetaBuilder;
import me.Abhigya.core.util.itemstack.ItemStackUtils;
import org.bukkit.Material;
import org.bukkit.event.EventPriority;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Abstract class to be used for creating Action Items.
 */
public abstract class ActionItemBase implements ActionItem {

    protected final String display_name;
    protected final List< String > lore;
    protected final Material material;
    protected final EventPriority priority;

    /**
     * Constructs the Action Item.
     * <p>
     *
     * @param display_name Display name of the Action Item
     * @param lore         Lore of the Action Item
     * @param material     Material of the Action Item
     * @param priority     {@link EventPriority} of the Action Item
     */
    public ActionItemBase( String display_name, Collection< String > lore, Material material, EventPriority priority ) {
        this.display_name = StringUtils.translateAlternateColorCodes( display_name );
        this.lore = StringUtils
                .translateAlternateColorCodes( StringUtils.translateAlternateColorCodes( new ArrayList<>( lore ) ) );
        this.material = material;
        this.priority = priority;
    }

    /**
     * Constructs the Action Item.
     * <p>
     *
     * @param display_name Display name of the Action Item
     * @param lore         Lore of the Action Item
     * @param material     Material of the Action Item
     */
    public ActionItemBase( String display_name, Collection< String > lore, Material material ) {
        this( display_name, lore, material, EventPriority.NORMAL );
    }

    @Override
    public String getDisplayName( ) {
        return display_name;
    }

    @Override
    public List< String > getLore( ) {
        return lore;
    }

    @Override
    public Material getMaterial( ) {
        return material;
    }

    @Override
    public EventPriority getPriority( ) {
        return priority;
    }

    @Override
    public ItemStack toItemStack( ) {
        return new ItemMetaBuilder( getMaterial( ) )
                .withDisplayName( getDisplayName( ) )
                .withLore( getLore( ) ).toItemStack( );
    }

    @Override
    public boolean isThis( ItemStack item ) {
        if ( item != null ) {
            return item.getType( ) == getMaterial( )
                    && Objects.equal( ItemStackUtils.extractName( item, false ), getDisplayName( ) )
                    && Objects.equal( ItemStackUtils.extractLore( item, false ), getLore( ) );
        } else {
            return false;
        }
    }

}
