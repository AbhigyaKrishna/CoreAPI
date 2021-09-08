package me.Abhigya.core.util.configurable.itemstack;

import me.Abhigya.core.util.StringUtils;
import me.Abhigya.core.util.configurable.Configurable;
import me.Abhigya.core.util.configurable.enchantment.ConfigurableEnchantment;
import me.Abhigya.core.util.itemstack.ItemMetaBuilder;
import me.Abhigya.core.util.loadable.LoadableCollectionEntry;
import me.Abhigya.core.util.material.MaterialUtils;
import me.Abhigya.core.util.saveable.SaveableCollectionEntry;
import me.Abhigya.core.util.saveable.SaveableEntry;
import org.apache.commons.lang.Validate;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Represents a {@link ItemStack} that is 'Configurable'
 * because can be loaded from/saved on a {@link ConfigurationSection}.
 */
public class ConfigurableItemStack implements Configurable {

    public static final String TYPE_KEY = "type";
    public static final String SIZE_KEY = "size";
    public static final String NAME_KEY = "name";
    public static final String LORE_KEY = "lore";
    public static final String DATA_KEY = "data";
    public static final String ENCHANTS_SECTION = "enchantments";
    public static final String ENCHANT_SUBSECTION_PREFIX = "enchantment-";
    @LoadableCollectionEntry(subsection = ENCHANTS_SECTION)
    @SaveableCollectionEntry(subsection = ENCHANTS_SECTION, subsectionprefix = ENCHANT_SUBSECTION_PREFIX)
    public final Set<ConfigurableEnchantment> enchants = new HashSet<>();
    public @SaveableEntry(key = TYPE_KEY)
    String type;
    public @SaveableEntry(key = SIZE_KEY)
    int size;
    public @SaveableEntry(key = NAME_KEY)
    String name;
    public @SaveableEntry(key = LORE_KEY)
    List<String> lore;
    public @SaveableEntry(key = DATA_KEY)
    short data;

    /**
     * Construct a new uninitialized {@link ConfigurableItemStack}.
     */
    public ConfigurableItemStack() {
        /* uninitialized */
    }

    /**
     * Construct a {@link ConfigurableItemStack}
     * getting the default values from the
     * given {@link ItemStack}.
     * <p>
     *
     * @param stack {@link ItemStack} to get the default values
     */
    @SuppressWarnings("deprecation")
    public ConfigurableItemStack(ItemStack stack) {
        this(MaterialUtils.getRightMaterial(stack).name(), stack.getAmount(), stack.getItemMeta().getDisplayName(),
                stack.getItemMeta().getLore(), stack.getDurability());
    }

    /**
     * Construct a {@link ConfigurableItemStack}
     * with default values.
     * <p>
     *
     * @param type Default type
     * @param size Default size
     * @param name Default name
     * @param lore Default lore
     * @param data Default data
     */
    public ConfigurableItemStack(String type, int size, String name, List<String> lore, short data) {
        this.type = type;
        this.size = size;
        this.name = name;
        this.lore = lore;
        this.data = data;
    }

    /**
     * Construct a {@link ConfigurableItemStack}
     * with default values.
     * <p>
     *
     * @param type Default type
     * @param size Default size
     * @param name Default name
     * @param lore Default lore
     * @param data Default data
     */
    public ConfigurableItemStack(String type, int size, String name, String[] lore, short data) {
        this(type, size, name, Arrays.asList(lore), data);
    }

    /**
     * Load values from the given {@link ConfigurationSection}.
     * <p>
     *
     * @param section {@link ConfigurationSection} to load values
     * @return This Object, for chaining
     */
    @Override
    public ConfigurableItemStack load(ConfigurationSection section) {
        Validate.notNull(section, "The section cannot be null!");
        this.type = section.getString(TYPE_KEY, null);
        this.size = section.getInt(SIZE_KEY, -1);
        this.name = section.getString(NAME_KEY, null);
        this.lore = section.isList(LORE_KEY) ? section.getStringList(LORE_KEY) : null;
        this.data = (short) section.getInt(DATA_KEY, 0);
        return (ConfigurableItemStack) this.loadEntries(section); // load enchantments by loading entries
    }

    @Override
    public int save(ConfigurationSection section) {
        return this.saveEntries(section);
    }

    /**
     * Returns the type string of the ItemStack.
     * <p>
     *
     * @return Type of ItemStack as string
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the type string of the ItemStack.
     * <p>
     *
     * @param type Type for ItemStack
     */
    public void setType(String type) {
        this.setType(type, true);
    }

    /**
     * Sets the type string of the ItemStack.
     * <p>
     *
     * @param type      Type for ItemStack
     * @param overwrite whether to overwrite the previous value
     */
    public void setType(String type, boolean overwrite) {
        this.type = overwrite ? type : (this.type == null ? type : this.type);
    }

    /**
     * Returns the amount of the ItemStack.
     * <p>
     *
     * @return Amount of ItemStack
     */
    public int getSize() {
        return size;
    }

    /**
     * Sets the amount of the ItemStack.
     * <p>
     *
     * @param size Amount for ItemStack
     */
    public void setSize(int size) {
        this.setSize(size, true);
    }

    /**
     * Sets the amount of the ItemStack.
     * <p>
     *
     * @param size      Amount for ItemStack
     * @param overwrite whether to overwrite the previous value
     */
    public void setSize(int size, boolean overwrite) {
        this.size = overwrite ? size : (this.size == -1 ? size : this.size);
    }

    /**
     * Returns the name of the ItemStack.
     * <p>
     *
     * @return Name of ItemStack
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the ItemStack.
     * <p>
     *
     * @param name Name for ItemStack
     */
    public void setName(String name) {
        this.setName(name, true);
    }

    /**
     * Sets the name of the ItemStack.
     * <p>
     *
     * @param name      Name for ItemStack
     * @param overwrite whether to overwrite the previous value
     */
    public void setName(String name, boolean overwrite) {
        this.name = overwrite ? name : (this.name == null ? name : this.name);
    }

    /**
     * Returns the lore of the ItemStack.
     * <p>
     *
     * @return Lore of ItemStack
     */
    public List<String> getLore() {
        return lore;
    }

    /**
     * Sets the lore of the ItemStack.
     * <p>
     *
     * @param lore Lore for ItemStack
     */
    public void setLore(List<String> lore) {
        this.setLore(lore, true);
    }

    /**
     * Sets the lore of the ItemStack.
     * <p>
     *
     * @param lore      Lore for ItemStack
     * @param overwrite whether to overwrite the previous value
     */
    public void setLore(List<String> lore, boolean overwrite) {
        this.lore = overwrite ? lore : (this.lore == null ? lore : this.lore);
    }

    /**
     * Returns the data of the ItemStack.
     * <p>
     *
     * @return Data of ItemStack
     */
    public short getData() {
        return data;
    }

    /**
     * Sets the data of the ItemStack.
     * <p>
     *
     * @param data Data for ItemStack
     */
    public void setData(short data) {
        this.setData(data, true);
    }

    /**
     * Sets the data of the ItemStack.
     * <p>
     *
     * @param data      Data for ItemStack
     * @param overwrite whether to overwrite the previous value
     */
    public void setData(short data, boolean overwrite) {
        this.data = overwrite ? data : (this.data == -1 ? data : this.data);
    }

    /**
     * Returns the enchantments of the ItemStack.
     * <p>
     *
     * @return {@link ConfigurableEnchantment} of ItemStack
     */
    public Set<ConfigurableEnchantment> getEnchantments() {
        return enchants;
    }

    /**
     * Adds the enchantment of the ItemStack.
     * <p>
     *
     * @param enchantment {@link ConfigurableEnchantment} for ItemStack
     */
    public void addEnchantment(ConfigurableEnchantment enchantment) {
        Validate.notNull(enchantment, "The enchantment cannot be null!");
        Validate.isTrue(enchantment.isInvalid(), "The enchantment is invalid!");
        this.enchants.add(enchantment);
    }

    /**
     * Returns the ItemStack.
     * <p>
     *
     * @return ItemStack
     */
    @SuppressWarnings("deprecation")
    public ItemStack toItemStack() {
        ItemMetaBuilder builder = new ItemMetaBuilder(MaterialUtils.getRightMaterial(Material.valueOf(getType())));
        getEnchantments().stream()
                .filter(ConfigurableEnchantment::isValid)
                .forEach(enchantment -> builder
                        .withEnchantment(enchantment.getEnchantment(), enchantment.getEnchantmentLevel()));

        return builder.withDisplayName(StringUtils.translateAlternateColorCodes(getName()))
                .withLore(StringUtils.translateAlternateColorCodes(getLore()))
                .applyTo(new ItemStack(MaterialUtils.getRightMaterial(Material.valueOf(getType())), getSize(), getData()));
    }

    @Override
    public boolean isValid() {
        return getType() != null && Material.valueOf(getType()) != null
                && getName() != null
                && getSize() > -1;
    }

    @Override
    public boolean isInvalid() {
        return !isValid();
    }

}
