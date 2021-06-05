package me.Abhigya.core.util.itemstack.wool;

/**
 * The wool colors.
 */
public enum WoolColor {

    WHITE(0),

    ORANGE(1),

    MAGENTA(2),

    LIGHT_BLUE(3),

    YELLOW(4),

    LIME(5),

    PINK(6),

    GRAY(7),

    LIGHT_GRAY(8),

    CYAN(9),

    PURPLE(10),

    BLUE(11),

    BROWN(12),

    GREEN(13),

    RED(14),

    BLACK(15),
    ;

    private final short value;

    WoolColor(final int value) {
        this.value = (short) value;
    }

    public short getShortValue() {
        return value;
    }

    public WoolItemStack toItemStack() {
        return toItemStack(1);
    }

    public WoolItemStack toItemStack(int amount) {
        return new WoolItemStack(this, amount);
    }

    public static WoolColor getFromShort(final short value) {
        for (WoolColor color : values()) {
            if (color.getShortValue() == value) {
                return color;
            }
        }
        return null;
    }

}
