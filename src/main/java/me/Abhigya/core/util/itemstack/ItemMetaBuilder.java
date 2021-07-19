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

/**
 * Represents a class for building {@link ItemMeta}
 */
public final class ItemMetaBuilder {

    /**
     * Returns the instance of {@link ItemMetaBuilder} for the given ItemStack.
     * <p>
     *
     * @param stack ItemStack to get ItemMeta of
     * @return {@link ItemMetaBuilder} instance
     */
    public static ItemMetaBuilder of(ItemStack stack) {
        return stack.hasItemMeta() ? of(stack.getType(), stack.getItemMeta()) : new ItemMetaBuilder(stack.getType());
    }

    /**
     * Returns the instance of {@link ItemMetaBuilder} for the
     * given Material and ItemMeta.
     * <p>
     *
     * @param material Material of ItemStack
     * @param meta     Meta of ItemStack
     * @return {@link ItemMetaBuilder} instance
     */
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
    private final ItemMeta result;

    /**
     * Constructs the ItemMetaBuilder.
     * <p>
     *
     * @param material Material for getting ItemMeta
     */
    public ItemMetaBuilder(Material material) {
        this.material = MaterialUtils.getRightMaterial(material);
        this.result = Bukkit.getItemFactory().getItemMeta(this.material);
        if (this.result == null) {
            throw new IllegalArgumentException("Unsupported Material: " + material.name());
        }
    }

    /**
     * Sets the display name in ItemMeta.
     * <p>
     *
     * @param display_name Display name
     * @return This Object, for chaining
     */
    public ItemMetaBuilder withDisplayName(String display_name) {
        result.setDisplayName(display_name);
        return this;
    }

    /**
     * Sets the lore in ItemMeta.
     * <p>
     *
     * @param lore Lore
     * @return This Object, for chaining
     */
    public ItemMetaBuilder withLore(List<String> lore) {
        result.setLore(lore);
        return this;
    }

    /**
     * Sets the lore in ItemMeta.
     * <p>
     *
     * @param lore Lore
     * @return This Object, for chaining
     */
    public ItemMetaBuilder withLore(String... lore) {
        return withLore(Arrays.asList(lore));
    }

    /**
     * Append line to the lore in ItemMeta.
     * <p>
     *
     * @param line Line to append to lore
     * @return This Object, for chaining
     */
    public ItemMetaBuilder appendToLore(String line) {
        List<String> lore = result.getLore();
        if (lore == null) {
            return withLore(line);
        } else {
            lore.add(line);
            return withLore(lore);
        }
    }

    /**
     * Remove lore line from ItemMeta.
     * <p>
     *
     * @param line Line to remove from lore
     * @return This Object, for chaining
     */
    public ItemMetaBuilder removeFromLore(String line) {
        List<String> lore = result.getLore();
        if (lore != null) {
            lore.remove(line);
            return withLore(lore);
        }
        return this;
    }

    /**
     * Sets the enchantment in ItemMeta.
     * <p>
     *
     * @param enchantment      Enchantment
     * @param level            Level of enchantment
     * @param ignore_max_level Ignore max level cap?
     * @return This Object, for chaining
     */
    public ItemMetaBuilder withEnchantment(Enchantment enchantment, int level, boolean ignore_max_level) {
        result.addEnchant(enchantment, level, ignore_max_level);
        return this;
    }

    /**
     * Sets the enchantment in ItemMeta.
     * <p>
     *
     * @param enchantment Enchantment
     * @param level       Level of enchantment
     * @return This Object, for chaining
     */
    public ItemMetaBuilder withEnchantment(Enchantment enchantment, int level) {
        return withEnchantment(enchantment, level, true);
    }

    /**
     * Sets the enchantment in ItemMeta.
     * <p>
     *
     * @param enchantment Enchantment
     * @return This Object, for chaining
     */
    public ItemMetaBuilder withEnchantment(Enchantment enchantment) {
        return withEnchantment(enchantment, 0);
    }

    /**
     * Remove the enchantment in ItemMeta.
     * <p>
     *
     * @param enchantment Enchantment to remove
     * @return This Object, for chaining
     */
    public ItemMetaBuilder withoutEnchantment(Enchantment enchantment) {
        result.removeEnchant(enchantment);
        return this;
    }

    /**
     * Sets the Item Flag in ItemMeta.
     * <p>
     *
     * @param flag Item Flag
     * @return This Object, for chaining
     */
    public ItemMetaBuilder withItemFlags(ItemFlag... flag) {
        result.addItemFlags(flag);
        return this;
    }

    /**
     * Remove the Item Flag in ItemMeta.
     * <p>
     *
     * @param flag Item Flag
     * @return This Object, for chaining
     */
    public ItemMetaBuilder withoutItemFlags(ItemFlag... flag) {
        result.removeItemFlags(flag);
        return this;
    }

    /**
     * Sets if item is unbreakable in ItemMeta.
     * <p>
     *
     * @param unbreakable unbreakable?
     * @return This Object, for chaining
     */
    @SuppressWarnings("deprecation")
    public ItemMetaBuilder unbreakable(boolean unbreakable) {
        result.setUnbreakable(unbreakable);
        return this;
    }

    /**
     * Create the {@link ItemMeta} with the given configuration.
     * <p>
     *
     * @return Created {@link ItemMeta}
     */
    public ItemMeta build() {
        return result;
    }

    /**
     * Returns ItemStack for the given configuration of ItemMeta.
     * <p>
     *
     * @param amount Amount of ItemStack
     * @return {@link ItemStack}
     */
    public ItemStack toItemStack(int amount) {
        return applyTo(new ItemStack(material, amount));
    }

    /**
     * Returns ItemStack for the given configuration of ItemMeta.
     * <p>
     *
     * @return {@link ItemStack}
     */
    public ItemStack toItemStack() {
        return applyTo(new ItemStack(material, 1));
    }

    /**
     * Applies the given ItemMeta configuration to the given ItemStack.
     * <p>
     *
     * @param stack ItemStack to apply to
     * @return {@link ItemStack}
     */
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
