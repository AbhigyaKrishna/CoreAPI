package me.Abhigya.core.util.itemstack.custom;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Class to interact with custom ItemStack.
 */
public class CustomItemStack extends ItemStack {

    /**
     * Checks whether this item has display name.
     * <p>
     *
     * @return true if it has display name, else false
     */
    public boolean hasDisplayName() {
        return this.getItemMeta().hasDisplayName();
    }

    /**
     * Returns the display name of this item.
     * <p>
     *
     * @return Display name of this item
     */
    public String getDisplayName() {
        return this.getItemMeta().getDisplayName();
    }

    /**
     * Sets the display name of this item.
     * <p>
     *
     * @param name Display name
     * @return This Object, for chaining
     */
    public CustomItemStack setDisplayName(String name) {
        this.getItemMeta().setDisplayName(name);
        return this;
    }

    /**
     * Checks whether this item has lore.
     * <p>
     *
     * @return true if it has lore, else false
     */
    public boolean hasLore() {
        return this.getItemMeta().hasLore();
    }

    /**
     * Returns the lore of this item.
     * <p>
     *
     * @return Lore of this item
     */
    public List<String> getLore() {
        return this.getItemMeta().getLore();
    }

    /**
     * Sets the lore of this item.
     * <p>
     *
     * @param lore Lore
     * @return This Object, for chaining
     */
    public CustomItemStack setLore(List<String> lore) {
        this.getItemMeta().setLore(lore);
        return this;
    }

    /**
     * Sets the lore of this item.
     * <p>
     *
     * @param lore Lore
     * @return This Object, for chaining
     */
    public CustomItemStack setLore(String[] lore) {
        return this.setLore(Arrays.asList(lore));
    }

    /**
     * Checks whether this item has enchantments.
     * <p>
     *
     * @return true if it has enchantments, else false
     */
    public boolean hasEnchants() {
        return this.getItemMeta().hasEnchants();
    }

    /**
     * Checks whether this item has the given enchantment.
     * <p>
     *
     * @return true if it has the given enchantment, else false
     */
    public boolean hasEnchant(Enchantment ench) {
        return this.getItemMeta().hasEnchant(ench);
    }

    /**
     * Returns the enchantment level of the given enchantment.
     * <p>
     *
     * @return Enchantments level
     */
    public int getEnchantLevel(Enchantment ench) {
        return this.getItemMeta().getEnchantLevel(ench);
    }

    /**
     * Returns the enchantments of this item.
     * <p>
     *
     * @return Enchantments of this item
     */
    public Map<Enchantment, Integer> getEnchants() {
        return this.getItemMeta().getEnchants();
    }

    /**
     * Adds enchantment to this item.
     * <p>
     *
     * @param ench                   Enchantment
     * @param level                  Level of enchantment
     * @param ignoreLevelRestriction Whether to ignore max level cap
     * @return true if added successfully, else false
     */
    public boolean addEnchant(Enchantment ench, int level, boolean ignoreLevelRestriction) {
        return this.getItemMeta().addEnchant(ench, level, ignoreLevelRestriction);
    }

    /**
     * Removes enchantment from this item.
     * <p>
     *
     * @param ench Enchantment
     * @return true if removed successfully, else false
     */
    public boolean removeEnchant(Enchantment ench) {
        return this.getItemMeta().removeEnchant(ench);
    }

    /**
     * Checks whether this item has any vanilla conflicting enchantment
     * for the given enchantment.
     * <p>
     *
     * @param ench Enchantment to check for conflict
     * @return true if there is conflicting enchantment, else false
     */
    public boolean hasConflictingEnchant(Enchantment ench) {
        return this.getItemMeta().hasConflictingEnchant(ench);
    }

    /**
     * Adds ItemFlag to this item.
     * <p>
     *
     * @param itemFlags ItemFlag to add
     * @return This Object, for chaining
     */
    public CustomItemStack addItemFlags(ItemFlag... itemFlags) {
        this.getItemMeta().addItemFlags(itemFlags);
        return this;
    }

    /**
     * Removes ItemFlag from this item.
     * <p>
     *
     * @param itemFlags ItemFlag to remove
     * @return This Object, for chaining
     */
    public CustomItemStack removeItemFlags(ItemFlag... itemFlags) {
        this.getItemMeta().removeItemFlags(itemFlags);
        return this;
    }

    /**
     * Returns the ItemFlag of this item.
     * <p>
     *
     * @return ItemFlag of this item
     */
    public Set<ItemFlag> getItemFlags() {
        return this.getItemMeta().getItemFlags();
    }

    /**
     * Checks whether this item has the given ItemFlag.
     * <p>
     *
     * @param flag ItemFlag to check
     * @return true if present, else false
     */
    public boolean hasItemFlag(ItemFlag flag) {
        return this.getItemMeta().hasItemFlag(flag);
    }

}
