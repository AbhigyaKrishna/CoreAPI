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

    public GameRuleMetadata(Plugin owning_plugin, GameRuleType type, Object value) {
        super(owning_plugin, value);
        Validate.isTrue(type.isSameDataType(value));
        this.type = type;
    }

    public GameRuleType getType() {
        return type;
    }

    public int asInt() {
        validateNumericValue();
        return NumberConversions.toInt(value());
    }

    public float asFloat() {
        validateNumericValue();
        return NumberConversions.toFloat(value());
    }

    public double asDouble() {
        validateNumericValue();
        return NumberConversions.toDouble(value());
    }

    public long asLong() {
        validateNumericValue();
        return NumberConversions.toLong(value());
    }

    public short asShort() {
        validateNumericValue();
        return NumberConversions.toShort(value());
    }

    public byte asByte() {
        validateNumericValue();
        return NumberConversions.toByte(value());
    }

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
