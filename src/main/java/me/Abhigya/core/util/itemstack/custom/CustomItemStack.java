package me.Abhigya.core.util.itemstack.custom;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CustomItemStack extends ItemStack {

    public boolean hasDisplayName() {
        return this.getItemMeta().hasDisplayName();
    }

    public String getDisplayName() {
        return this.getItemMeta().getDisplayName();
    }

    public CustomItemStack setDisplayName(String name) {
        this.getItemMeta().setDisplayName(name);
        return this;
    }

    public boolean hasLore() {
        return this.getItemMeta().hasLore();
    }

    public List<String> getLore() {
        return this.getItemMeta().getLore();
    }

    public CustomItemStack setLore(List<String> lore) {
        this.getItemMeta().setLore(lore);
        return this;
    }

    public CustomItemStack setLore(String[] lore) {
        return this.setLore(Arrays.asList(lore));
    }

    public boolean hasEnchants() {
        return this.getItemMeta().hasEnchants();
    }

    public boolean hasEnchant(Enchantment ench) {
        return this.getItemMeta().hasEnchant(ench);
    }

    public int getEnchantLevel(Enchantment ench) {
        return this.getItemMeta().getEnchantLevel(ench);
    }

    public Map<Enchantment, Integer> getEnchants() {
        return this.getItemMeta().getEnchants();
    }

    public boolean addEnchant(Enchantment ench, int level, boolean ignoreLevelRestriction) {
        return this.getItemMeta().addEnchant(ench, level, ignoreLevelRestriction);
    }

    public boolean removeEnchant(Enchantment ench) {
        return this.getItemMeta().removeEnchant(ench);
    }

    public boolean hasConflictingEnchant(Enchantment ench) {
        return this.getItemMeta().hasConflictingEnchant(ench);
    }

    public CustomItemStack addItemFlags(ItemFlag... itemFlags) {
        this.getItemMeta().addItemFlags(itemFlags);
        return this;
    }

    public CustomItemStack removeItemFlags(ItemFlag... itemFlags) {
        this.getItemMeta().removeItemFlags(itemFlags);
        return this;
    }

    public Set<ItemFlag> getItemFlags() {
        return this.getItemMeta().getItemFlags();
    }

    public boolean hasItemFlag(ItemFlag flag) {
        return this.getItemMeta().hasItemFlag(flag);
    }

}
