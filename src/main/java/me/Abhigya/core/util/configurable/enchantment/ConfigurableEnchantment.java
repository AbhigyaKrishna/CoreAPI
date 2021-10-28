package me.Abhigya.core.util.configurable.enchantment;

import me.Abhigya.core.util.configurable.Configurable;
import me.Abhigya.core.util.loadable.Loadable;
import me.Abhigya.core.util.loadable.LoadableEntry;
import me.Abhigya.core.util.saveable.SaveableEntry;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;

import java.util.Arrays;

/**
 * Represents a {@link Enchantment} that is 'Configurable' because can be loaded from/saved on a
 * {@link ConfigurationSection}.
 */
public class ConfigurableEnchantment implements Configurable {

    public static final String ENCHANT_KEY = "enchant";
    public static final String LEVEL_KEY = "level";

    @LoadableEntry(key = ENCHANT_KEY)
    @SaveableEntry(key = ENCHANT_KEY)
    public String name;

    @LoadableEntry(key = LEVEL_KEY)
    @SaveableEntry(key = LEVEL_KEY)
    public Integer level;

    /** Construct a new uninitialized {@link ConfigurableEnchantment}. */
    public ConfigurableEnchantment() {
        /* uninitialized */
    }

    /**
     * Constructs the {@link ConfigurableEnchantment}.
     *
     * <p>
     *
     * @param enchantment_name Enchantment name
     * @param level Enchantment level
     */
    public ConfigurableEnchantment(String enchantment_name, Integer level) {
        this.name = enchantment_name;
        this.level = level;
    }

    /**
     * Constructs the {@link ConfigurableEnchantment}.
     *
     * <p>
     *
     * @param enchantment Enchantment
     * @param level Enchantment level
     */
    @SuppressWarnings("deprecation")
    public ConfigurableEnchantment(Enchantment enchantment, Integer level) {
        this(enchantment.getName(), level);
    }

    @Override
    public Loadable load(ConfigurationSection section) {
        return this.loadEntries(section);
    }

    @Override
    public int save(ConfigurationSection section) {
        return this.saveEntries(section);
    }

    /**
     * Returns the Enchantment name.
     *
     * <p>
     *
     * @return Enchantment name
     */
    public String getEnchantmentName() {
        return name;
    }

    /**
     * Returns the Enchantment level.
     *
     * <p>
     *
     * @return Enchantment level
     */
    public Integer getEnchantmentLevel() {
        return level;
    }

    /**
     * Gets the enchantment bound to the enchantment name.
     *
     * <p>
     *
     * @return Enchantment {@link Enchantment} bound to the {@link ConfigurableEnchantment#name}
     */
    @SuppressWarnings("deprecation")
    public Enchantment getEnchantment() {
        return Arrays.stream(Enchantment.values())
                .filter(enchantment -> enchantment.getName().equalsIgnoreCase(getEnchantmentName()))
                .findAny()
                .orElse(null);
    }

    @Override
    public boolean isValid() {
        return getEnchantment() != null
                && getEnchantmentLevel() != null
                && getEnchantmentLevel().intValue() > -1;
    }

    @Override
    public boolean isInvalid() {
        return !isValid();
    }
}
