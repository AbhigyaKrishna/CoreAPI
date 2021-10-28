package me.Abhigya.core.menu.anvil;

import me.Abhigya.core.menu.anvil.action.AnvilItemClickAction;
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

/** Class for creating custom Anvil Inventory items. */
public abstract class AnvilItem {

    protected String name;
    protected ItemStack icon;
    protected List<String> lore;
    protected AnvilMenu menu;

    /**
     * Constructs the AnvilItem.
     *
     * <p>
     *
     * @param name Name of the AnvilItem
     * @param icon ItemStack icon for the AnvilItem
     * @param lore Lore of the AnvilItem
     */
    public AnvilItem(String name, ItemStack icon, Collection<String> lore) {
        Validate.notNull(icon, "The icon cannot be null!");
        this.name = name == null ? "" : name;
        this.icon = icon;
        this.lore = new ArrayList(lore);
    }

    /**
     * Constructs the AnvilItem.
     *
     * <p>
     *
     * @param name Name of the AnvilItem
     * @param icon ItemStack icon for the AnvilItem
     * @param lore Lore of the AnvilItem
     */
    public AnvilItem(String name, ItemStack icon, String... lore) {
        this(name, icon, Arrays.asList(lore));
    }

    /**
     * Constructs the AnvilItem.
     *
     * <p>
     *
     * @param icon ItemStack icon for the AnvilItem
     */
    public AnvilItem(ItemStack icon) {
        this(
                StringUtils.defaultIfBlank(
                        icon.getItemMeta() != null ? icon.getItemMeta().getDisplayName() : null,
                        "null name"),
                icon,
                (String[])
                        ItemStackUtils.extractLore(icon, false)
                                .toArray(
                                        new String
                                                [ItemStackUtils.extractLore(icon, false).size()]));
    }

    /**
     * Returns the name of the AnvilItem.
     *
     * <p>
     *
     * @return Name of the AnvilItem
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the AnvilItem
     *
     * <p>
     *
     * @param name Name for the AnvilItem
     * @return This Object, for chaining
     */
    public AnvilItem setName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Returns the icon of the AnvilItem.
     *
     * <p>
     *
     * @return Icon of the AnvilItem
     */
    public ItemStack getIcon() {
        return icon;
    }

    /**
     * Sets the icon of the AnvilItem
     *
     * <p>
     *
     * @param icon ItemStack icon for the AnvilItem
     * @return This Object, for chaining
     */
    public AnvilItem setIcon(ItemStack icon) {
        Validate.notNull(icon, "The icon cannot be null!");
        this.icon = icon;
        return this;
    }

    /**
     * Returns the display icon of the AnvilItem.
     *
     * <p>
     *
     * @return Display icon of the AnvilItem
     */
    public ItemStack getDisplayIcon() {
        return this.getIcon().getType() == Material.AIR
                ? this.icon
                : (new ItemMetaBuilder(MaterialUtils.getRightMaterial(this.getIcon())))
                        .withDisplayName(StringUtils.translateAlternateColorCodes(this.getName()))
                        .withLore(this.getLore())
                        .applyTo(this.getIcon().clone());
    }

    /**
     * Returns the lore of the AnvilItem.
     *
     * <p>
     *
     * @return Lore of the AnvilItem
     */
    public List<String> getLore() {
        return lore;
    }

    /**
     * Sets the name of the AnvilItem
     *
     * <p>
     *
     * @param lore Lore for the AnvilItem
     * @return This Object, for chaining
     */
    public AnvilItem setLore(List<String> lore) {
        this.lore = lore != null ? lore : new ArrayList<>();
        return this;
    }

    /**
     * Gets the menu this item belongs.
     *
     * <p>Note that this will return <strong>null</strong> if this item has never been set in a
     * menu.
     *
     * <p>
     *
     * @return The menu this item belongs
     */
    public AnvilMenu getMenu() {
        return menu;
    }

    /**
     * Click trigger for this AnvilItem.
     *
     * <p>
     *
     * @param action {@link AnvilItemClickAction} for the AnvilItem
     */
    public abstract void onClick(AnvilItemClickAction action);
}
