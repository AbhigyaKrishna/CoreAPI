package me.Abhigya.core.menu.size;

import java.util.Arrays;

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

    public int getSize() {
        return size;
    }

    public boolean isHigherThan(ItemMenuSize other) {
        return other != null && getSize() > other.getSize();
    }

    public boolean isHigherEqualsThan(ItemMenuSize other) {
        return other != null && getSize() >= other.getSize();
    }

    public boolean isLowerThan(ItemMenuSize other) {
        return other != null && getSize() < other.getSize();
    }

    public boolean isLowerEqualsThan(ItemMenuSize other) {
        return other != null && getSize() <= other.getSize();
    }

    public int getFirstSlotTest() {
        return beforeTo(this).getSize() + 1;
    }

    public int getLastSlotTest() {
        return getSize() - 1;
    }

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

    public static ItemMenuSize nextTo(ItemMenuSize from) {
        return fitOf(from.getSize() + 1);
    }

    public static ItemMenuSize beforeTo(ItemMenuSize from) {
        switch(from) {
            case ONE_LINE:
                return ONE_LINE;

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
