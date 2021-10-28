package me.Abhigya.core.util.configurable.duration;

import me.Abhigya.core.util.Duration;
import me.Abhigya.core.util.configurable.Configurable;
import me.Abhigya.core.util.reflection.general.EnumReflection;
import me.Abhigya.core.util.yaml.YamlUtils;
import org.bukkit.configuration.ConfigurationSection;

import java.util.concurrent.TimeUnit;

/**
 * Represents a {@link Duration} that is 'Configurable' because can be loaded from/saved on a {@link
 * ConfigurationSection}.
 */
public class ConfigurableDuration extends Duration implements Configurable {

    public static final String DURATION_KEY = "duration";
    public static final String UNIT_KEY = "unit";

    /**
     * Construct a new uninitialized duration.
     *
     * <p>This constructor is used only for {@link ConfigurableDuration}s that will be initialized
     * after constructing by using the method {@link
     * ConfigurableDuration#load(ConfigurationSection)}.
     */
    public ConfigurableDuration() { // uninitialized
        this(-1L, null);
    }

    /**
     * Construct duration.
     *
     * <p>
     *
     * @param duration Duration
     * @param unit Time unit
     */
    public ConfigurableDuration(long duration, TimeUnit unit) {
        super(duration, unit);
    }

    /**
     * Construct duration from milliseconds.
     *
     * <p>
     *
     * @param millis Duration in milliseconds
     */
    public ConfigurableDuration(long millis) {
        this(millis, TimeUnit.MILLISECONDS);
    }

    /**
     * Construct duration using the values of another {@link Duration}.
     *
     * <p>
     *
     * @param copy {@link Duration} to copy
     */
    public ConfigurableDuration(Duration copy) {
        this(copy.getDuration(), copy.getUnit());
    }

    @Override
    public ConfigurableDuration load(ConfigurationSection section) {
        this.duration = section.getLong(DURATION_KEY, -1L);
        this.unit =
                EnumReflection.getEnumConstant(TimeUnit.class, section.getString(UNIT_KEY, null));
        return this;
    }

    @Override
    public boolean isValid() {
        return this.getDuration() > -1L && this.getUnit() != null;
    }

    @Override
    public boolean isInvalid() {
        return !this.isValid();
    }

    @Override
    public int save(ConfigurationSection section) {
        return (YamlUtils.setNotEqual(section, DURATION_KEY, getDuration()) ? 1 : 0)
                + (YamlUtils.setNotEqual(section, UNIT_KEY, getUnit().name()) ? 1 : 0);
    }
}
