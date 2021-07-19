package me.Abhigya.core.util.itemstack.wool;

/**
 * Enumeration of wool colors.
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

    /**
     * Returns the data value of the wool color.
     * <p>
     *
     * @return Data value
     */
    public short getShortValue() {
        return value;
    }

    /**
     * Returns {@link WoolItemStack} for the defined color.
     * <p>
     * <strong>Note:</strong> This method is only supported in legacy versions.
     * <p>
     *
     * @return {@link WoolItemStack}
     */
    public WoolItemStack toItemStack() {
        return toItemStack(1);
    }

    /**
     * Returns {@link WoolItemStack} for the defined color.
     * <p>
     * <strong>Note:</strong> This method is only supported in legacy versions.
     * <p>
     *
     * @param amount Amount of wool for ItemStack
     * @return {@link WoolItemStack}
     */
    public WoolItemStack toItemStack(int amount) {
        return new WoolItemStack(this, amount);
    }

    /**
     * Returns {@link WoolColor} instance from the given data value.
     * <p>
     *
     * @param value Data value
     * @return {@link WoolColor} instance
     */
    public static WoolColor getFromShort(final short value) {
        for (WoolColor color : values()) {
            if (color.getShortValue() == value) {
                return color;
            }
        }
        return null;
    }

}
