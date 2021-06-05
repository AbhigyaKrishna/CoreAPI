package me.Abhigya.core.util.itemstack;

import me.Abhigya.core.util.material.MaterialUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

public final class ItemMetaBuilder {

    public static ItemMetaBuilder of(ItemStack stack) {
        return stack.hasItemMeta() ? of(stack.getType(), stack.getItemMeta()) : new ItemMetaBuilder(stack.getType());
    }

    public static ItemMetaBuilder of(Material material, ItemMeta meta) {
        ItemMetaBuilder builder = new ItemMetaBuilder(material);
        if (meta != null) {
            builder.withDisplayName(meta.getDisplayName());
            builder.withLore(meta.getLore());
            builder.withItemFlags(meta.getItemFlags().toArray(new ItemFlag[meta.getItemFlags().size()]));
            meta.getEnchants().keySet().stream()
                    .filter(enchantment -> enchantment != null)
                    .forEach(enchantment -> builder.withEnchantment(enchantment, meta.getEnchantLevel(enchantment)));

        }
        return builder;
    }

    private final Material material;
    private final ItemMeta   result;

    public ItemMetaBuilder(Material material) {
        this.material = MaterialUtils.getRightMaterial(material);
        this.result   = Bukkit.getItemFactory().getItemMeta(this.material);
        if (this.result == null) {
            throw new IllegalArgumentException("Unsupported Material: " + material.name());
        }
    }

    public ItemMetaBuilder withDisplayName(String display_name) {
        result.setDisplayName(display_name);
        return this;
    }

    public ItemMetaBuilder withLore(List<String> lore) {
        result.setLore(lore);
        return this;
    }

    public ItemMetaBuilder withLore(String... lore) {
        return withLore(Arrays.asList(lore));
    }

    public ItemMetaBuilder appendToLore(String line) {
        List<String> lore = result.getLore();
        if (lore == null) {
            return withLore(line);
        } else {
            lore.add(line);
            return withLore(lore);
        }
    }

    public ItemMetaBuilder removeFromLore(String line) {
        List<String> lore = result.getLore();
        if (lore != null) {
            lore.remove(line);
            return withLore(lore);
        }
        return this;
    }

    public ItemMetaBuilder withEnchantment(Enchantment enchantment, int level, boolean ignore_max_level) {
        result.addEnchant(enchantment, level, ignore_max_level);
        return this;
    }

    public ItemMetaBuilder withEnchantment(Enchantment enchantment, int level) {
        return withEnchantment(enchantment, level, true);
    }

    public ItemMetaBuilder withEnchantment(Enchantment enchantment) {
        return withEnchantment(enchantment, 0);
    }

    public ItemMetaBuilder withoutEnchantment(Enchantment enchantment) {
        result.removeEnchant(enchantment);
        return this;
    }

    public ItemMetaBuilder withItemFlags(ItemFlag... flag) {
        result.addItemFlags(flag);
        return this;
    }

    public ItemMetaBuilder withoutItemFlags(ItemFlag... flag) {
        result.removeItemFlags(flag);
        return this;
    }

    @SuppressWarnings("deprecation")
    public ItemMetaBuilder unbreakable(boolean unbreakable) {
        result.setUnbreakable(unbreakable);
        return this;
    }

    public ItemMeta build() {
        return result;
    }

    public ItemStack toItemStack(int amount) {
        return applyTo(new ItemStack(material, amount));
    }

    public ItemStack toItemStack() {
        return applyTo(new ItemStack(material, 1));
    }

    public ItemStack applyTo(ItemStack stack) {
        if (stack == null) {
            return null;
        }

        if (MaterialUtils.getRightMaterial(stack) != material) {
            return stack;
        }

        stack.setItemMeta(result);
        return stack;
    }

}
