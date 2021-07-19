package me.Abhigya.core.util.world;

import org.bukkit.World;

/**
 * A game rule that disables the daylight cycle and sets a permanent time.
 */
public class GameRuleDisableDaylightCycle extends GameRule {

    /**
     * The default final time worlds with this rule will have.
     */
    public static final long DEFAULT_FINAL_TIME = 500L;

    /**
     * The permanent time for the world.
     */
    private final long permanent_time;

    /**
     * Construct a new {@link GameRule} that disables the daylight cycle of the
     * worlds. With the default final time
     * {@link GameRuleDisableDaylightCycle#DEFAULT_FINAL_TIME}
     */
    public GameRuleDisableDaylightCycle() {
        this(GameRuleDisableDaylightCycle.DEFAULT_FINAL_TIME);
    }

    /**
     * Construct a new {@link GameRule} that disables the daylight cycle of the
     * worlds.
     * <p>
     *
     * @param permanent_time Permanent time for the world
     */
    public GameRuleDisableDaylightCycle(long permanent_time) {
        super(GameRuleType.DAYLIGHT_CYCLE, false);
        this.permanent_time = permanent_time;
    }

    @Override
    public World apply(World world) {
        super.apply(world);
        world.setTime(permanent_time);
        return world;
    }
}
