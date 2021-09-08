package me.Abhigya.core.util.itemstack.stainedglass;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * Enumeration of glass colors.
 */
public enum StainedGlassColor {

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

    StainedGlassColor(final int value) {
        this.value = (short) value;
    }

    /**
     * Returns {@link StainedGlassColor} instance from the given data value.
     * <p>
     *
     * @param value Data value
     * @return {@link StainedGlassColor} instance
     */
    public static StainedGlassColor getFromShort(final short value) {
        for (StainedGlassColor color : values()) {
            if (color.getShortValue() == value) {
                return color;
            }
        }
        return null;
    }

    /**
     * Returns the data value of the glass color.
     * <p>
     *
     * @return Data value
     */
    public short getShortValue() {
        return value;
    }

    /**
     * Returns Glass ItemStack for the defined color.
     * <p>
     * <strong>Note:</strong> This method is only supported in legacy versions.
     * <p>
     *
     * @return Glass ItemStack
     */
    @SuppressWarnings("deprecation")
    public ItemStack getColoredGlass() {
        return new ItemStack(Material.valueOf("STAINED_GLASS"), 1, getShortValue());
    }

    /**
     * Returns Glass Pane ItemStack for the defined color.
     * <p>
     * <strong>Note:</strong> This method is only supported in legacy versions.
     * <p>
     *
     * @return Glass Pane ItemStack
     */
    @SuppressWarnings("deprecation")
    public ItemStack getColoredPaneGlass() {
        return new ItemStack(Material.valueOf("STAINED_GLASS_PANE"), 1, getShortValue());
    }

}
