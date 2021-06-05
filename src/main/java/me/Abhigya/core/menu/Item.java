package me.Abhigya.core.menu;

import me.Abhigya.core.menu.action.ItemClickAction;
import me.Abhigya.core.util.StringUtils;
import me.Abhigya.core.util.itemstack.ItemMetaBuilder;
import me.Abhigya.core.util.itemstack.ItemStackUtils;
import me.Abhigya.core.util.material.MaterialUtils;
import org.apache.commons.lang.Validate;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public abstract class Item {

    protected String name;
    protected ItemStack icon;
    protected List<String> lore;
    protected ItemMenu menu;

    public Item(String name, ItemStack icon, Collection<String> lore) {
        Validate.notNull(icon, "The icon cannot be null!");
        this.name = name == null ? "" : name;
        this.icon = icon;
        this.lore = new ArrayList<>(lore);
    }

    public Item(String name, ItemStack icon, String... lore) {
        Validate.notNull(icon, "The icon cannot be null!");
        this.name = name == null ? "" : name;
        this.icon = icon;
        this.lore = Arrays.asList(lore);
    }

    public Item(ItemStack icon) {
        this(StringUtils.defaultIfBlank((icon.getItemMeta() != null ? icon.getItemMeta().getDisplayName() : null), "null name"),
                icon,
                (ItemStackUtils.extractLore(icon, false).toArray(new String[ItemStackUtils.extractLore(icon, false).size()])));

    }

    public String getName() {
        return name;
    }

    public ItemStack getIcon() {
        return icon;
    }

    public ItemStack getDisplayIcon() {
        return (getIcon().getType() == Material.AIR ? icon : new ItemMetaBuilder(MaterialUtils.getRightMaterial(getIcon()))
                .withDisplayName(StringUtils.translateAlternateColorCodes(getName()))
                .withLore(getLore()).applyTo(getIcon().clone()));
    }

    public List<String> getLore() {
        return lore;
    }

    /**
     * Gets the menu this item belongs.
     * <p>
     * Note that this will return <strong>null</strong> if this item has never been
     * set in a menu.
     * <p>
     *
     * @return The menu this item belongs.
     */
    public ItemMenu getMenu() {
        return menu;
    }

    public Item setName(String name) {
        this.name = name;
        return this;
    }

    public Item setIcon(ItemStack icon) {
        Validate.notNull(icon, "The icon cannot be null!");
        this.icon = icon;
        return this;
    }

    public Item setLore(List<String> lore) {
        this.lore = lore != null ? lore : new ArrayList<String>();
        return this;
    }

    public abstract void onClick(final ItemClickAction action);

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((icon == null) ? 0 : icon.hashCode());
        result = prime * result + ((lore == null) ? 0 : lore.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Item other = (Item) obj;
        if (icon == null) {
            if (other.icon != null)
                return false;
        } else if (!icon.equals(other.icon))
            return false;
        if (lore == null) {
            if (other.lore != null)
                return false;
        } else if (!lore.equals(other.lore))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }

}
