package me.Abhigya.core.util.itemstack.stainedglass;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * The glass colors.
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

    public short getShortValue() {
        return value;
    }

    @SuppressWarnings("deprecation")
    public ItemStack getColoredGlass() {
        return new ItemStack(Material.valueOf("STAINED_GLASS"), 1, getShortValue());
    }

    @SuppressWarnings("deprecation")
    public ItemStack getColoredPaneGlass() {
        return new ItemStack(Material.valueOf("STAINED_GLASS_PANE"), 1, getShortValue());
    }

    public static StainedGlassColor getFromShort(final short value) {
        for (StainedGlassColor color : values()) {
            if (color.getShortValue() == value) {
                return color;
            }
        }
        return null;
    }

}
