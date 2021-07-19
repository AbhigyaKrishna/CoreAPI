package me.Abhigya.core.menu.size;

import java.util.Arrays;

/**
 * Enumeration for ItemMenu size.
 */
public enum ItemMenuSize {

    ONE_LINE(9),
    TWO_LINE(18),
    THREE_LINE(27),
    FOUR_LINE(36),
    FIVE_LINE(45),
    SIX_LINE(54);

    private final int size;

    ItemMenuSize(int size) {
        this.size = size;
    }

    /**
     * Returns the size of menu.
     * <p>
     *
     * @return Size of menu
     */
    public int getSize() {
        return size;
    }

    /**
     * Checks if the given size is higher than this size.
     * <p>
     *
     * @param other Other size
     * @return <strong>{@code true}</strong> if the given size is higher, else false
     */
    public boolean isHigherThan(ItemMenuSize other) {
        return other != null && getSize() > other.getSize();
    }

    /**
     * Checks if the given size is higher than or equal to this size.
     * <p>
     *
     * @param other Other size
     * @return <strong>{@code true}</strong> if the given size is higher or equal, else false
     */
    public boolean isHigherEqualsThan(ItemMenuSize other) {
        return other != null && getSize() >= other.getSize();
    }

    /**
     * Checks if the given size is lower than this size.
     * <p>
     *
     * @param other Other size
     * @return <strong>{@code true}</strong> if the given size is lower, else false
     */
    public boolean isLowerThan(ItemMenuSize other) {
        return other != null && getSize() < other.getSize();
    }

    /**
     * Checks if the given size is lower than or equal to this size.
     * <p>
     *
     * @param other Other size
     * @return <strong>{@code true}</strong> if the given size is lower or equal, else false
     */
    public boolean isLowerEqualsThan(ItemMenuSize other) {
        return other != null && getSize() <= other.getSize();
    }

    public int getFirstSlotTest() {
        return beforeTo(this).getSize() + 1;
    }

    public int getLastSlotTest() {
        return getSize() - 1;
    }

    /**
     * Returns the {@link ItemMenuSize} which fits the given size.
     * <p>
     *
     * @param size Size to fit in ItemMenu
     * @return ItemMenuSize instance with the given size
     */
    public static ItemMenuSize fitOf(int size) {
        int difference = -1;
        for (ItemMenuSize constant : ItemMenuSize.values()) {
            difference = ((difference == -1 || (Math.max(size - constant.getSize(), 0)) < difference)
                    ? (size - constant.getSize())
                    : difference);
        }
        return Arrays.stream(ItemMenuSize.values()).filter(constant -> Math.max(size - constant.getSize(), 0) == 0)
                .findAny().orElse(SIX_LINE);
    }

    /**
     * Returns the size enumeration next to this size.
     * <p>
     *
     * @param from Size to get next to
     * @return The next size enumeration
     */
    public static ItemMenuSize nextTo(ItemMenuSize from) {
        return fitOf(from.getSize() + 1);
    }

    /**
     * Returns the size enumeration before this size.
     * <p>
     *
     * @param from Size to get before size
     * @return The before size enumeration
     */
    public static ItemMenuSize beforeTo(ItemMenuSize from) {
        switch (from) {
            case ONE_LINE:

            case TWO_LINE:
                return ONE_LINE;

            case THREE_LINE:
                return TWO_LINE;

            case FOUR_LINE:
                return THREE_LINE;

            case FIVE_LINE:
                return FOUR_LINE;

            case SIX_LINE:
                return FIVE_LINE;

            default:
                return null;
        }
    }

}
