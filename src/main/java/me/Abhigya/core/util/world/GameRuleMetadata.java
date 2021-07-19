package me.Abhigya.core.util.world;

import org.apache.commons.lang.Validate;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.NumberConversions;

/**
 * Class for changing GameRule Metadata values
 */
public class GameRuleMetadata extends FixedMetadataValue {

    protected final GameRuleType type;

    /**
     * Constructs the {@link GameRuleMetadata}.
     * <p>
     *
     * @param owning_plugin Plugin instance
     * @param type          {@link GameRuleType} type
     * @param value         Value of GameRule
     */
    public GameRuleMetadata(Plugin owning_plugin, GameRuleType type, Object value) {
        super(owning_plugin, value);
        Validate.isTrue(type.isSameDataType(value));
        this.type = type;
    }

    /**
     * Returns the type of GameRule.
     * <p>
     *
     * @return {@link GameRuleType}
     */
    public GameRuleType getType() {
        return type;
    }

    /**
     * Gets Int meta data.
     * <p>
     *
     * @return Int meta data
     */
    public int asInt() {
        validateNumericValue();
        return NumberConversions.toInt(value());
    }

    /**
     * Gets Float meta data.
     * <p>
     *
     * @return Float meta data
     */
    public float asFloat() {
        validateNumericValue();
        return NumberConversions.toFloat(value());
    }

    /**
     * Gets Double meta data.
     * <p>
     *
     * @return Double meta data
     */
    public double asDouble() {
        validateNumericValue();
        return NumberConversions.toDouble(value());
    }

    /**
     * Gets Long meta data.
     * <p>
     *
     * @return Long meta data
     */
    public long asLong() {
        validateNumericValue();
        return NumberConversions.toLong(value());
    }

    /**
     * Gets Short meta data.
     * <p>
     *
     * @return Short meta data
     */
    public short asShort() {
        validateNumericValue();
        return NumberConversions.toShort(value());
    }

    /**
     * Gets Byte meta data.
     * <p>
     *
     * @return Byte meta data
     */
    public byte asByte() {
        validateNumericValue();
        return NumberConversions.toByte(value());
    }

    /**
     * Gets Boolean meta data.
     * <p>
     *
     * @return Boolean meta data
     */
    public boolean asBoolean() {
        validateBooleanValue();
        return (Boolean) value();
    }

    protected void validateNumericValue() {
        Validate.isTrue((getType().isNumericalValue() && value() instanceof Number), "wrong value type!");
    }

    protected void validateBooleanValue() {
        Validate.isTrue((getType().isBooleanValue() && value() instanceof Boolean), "wrong value type!");
    }
}
