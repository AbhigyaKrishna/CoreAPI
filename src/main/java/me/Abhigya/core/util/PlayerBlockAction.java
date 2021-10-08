package me.Abhigya.core.util;

import org.bukkit.event.block.Action;

/**
 * Class for dealing with {@link Action}
 * <p>
 * Implements methods like {@link #isLeftClick()} that makes validations easier.
 */
public enum PlayerBlockAction {

    /**
     * Left-clicking a block
     */
    LEFT_CLICK_BLOCK,

    /**
     * Right-clicking a block
     */
    RIGHT_CLICK_BLOCK,

    /**
     * Left-clicking the air
     */
    LEFT_CLICK_AIR,

    /**
     * Right-clicking the air
     */
    RIGHT_CLICK_AIR,

    /**
     * Stepping onto or into a block (Ass-pressure)
     * <p>
     * Examples:
     * <ul>
     * <li>Jumping on soil
     * <li>Standing on pressure plate
     * <li>Triggering redstone ore
     * <li>Triggering tripwire
     * </ul>
     */
    PHYSICAL;

    /**
     * Gets the corresponding {@link PlayerBlockAction}.
     * <p>
     *
     * @param action Action to get from
     * @return The corresponding {@link PlayerBlockAction}
     */
    public static PlayerBlockAction ofBukkit( Action action ) {
        return PlayerBlockAction.valueOf( action.name( ) );
    }

    /**
     * Gets whether this is left click (regardless of whether a block or air was clicked).
     * <p>
     *
     * @return Whether this is left click
     */
    public boolean isLeftClick( ) {
        return this == LEFT_CLICK_AIR || this == LEFT_CLICK_BLOCK;
    }

    /**
     * Gets whether this is right click (regardless of whether a block or air was clicked).
     * <p>
     *
     * @return Whether this is right click
     */
    public boolean isRightClick( ) {
        return this == RIGHT_CLICK_AIR || this == RIGHT_CLICK_BLOCK;
    }

    /**
     * Gets whether this is a physical action.
     * <p>
     *
     * @return Whether this is a physical action
     */
    public boolean isPhysical( ) {
        return this == PHYSICAL;
    }

    /**
     * Gets the corresponding {@link Action}.
     * <p>
     *
     * @return The corresponding {@link Action}
     */
    public Action toBukkit( ) {
        return Action.valueOf( name( ) );
    }
}
