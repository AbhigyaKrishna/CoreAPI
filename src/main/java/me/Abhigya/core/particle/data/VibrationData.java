package me.Abhigya.core.particle.data;

import me.Abhigya.core.particle.ParticleConstants;
import me.Abhigya.core.particle.ParticleEffect;
import me.Abhigya.core.particle.utils.ParticleReflectionUtils;
import org.bukkit.Location;

import java.util.Objects;

/**
 * This class holds all data that is needed
 * by the client to display a {@link ParticleEffect#VIBRATION}
 * particle. The required information is:
 * The start {@link Location}, The destination
 * and the amount of ticks it will take the
 * particle to fly this path.
 * <p>
 * Minecraft only supports full block coordinates
 * for the start and destination location. So any
 * particle will spawn at the center of a block.
 */
public final class VibrationData extends ParticleData {

    /**
     * The start {@link Location} of the particle. (Will be mapped to the block location)
     */
    private final Location start;
    /**
     * The destination {@link Location} of the particle. (Will be mapped to the block location)
     */
    private final Location destination;
    /**
     * The amount of ticks it will take the particle to fly from the {@link #start} to the {@link #destination}
     */
    private final int ticks;

    /**
     * Creates a new {@link VibrationData} instance.
     * <p>
     *
     * @param start       Start {@link Location} of the particle.
     * @param destination Destination {@link Location} of the particle.
     * @param ticks       Amount of ticks it will take the particle to reach the {@link #destination}
     */
    public VibrationData(Location start, Location destination, int ticks) {
        this.start = Objects.requireNonNull(start);
        this.destination = Objects.requireNonNull(destination);
        this.ticks = ticks;
    }

    /**
     * Gets the start {@link Location} of the particle.
     * <p>
     *
     * @return the start {@link Location} of the particle.
     */
    public Location getStart() {
        return start;
    }

    /**
     * Gets the destination {@link Location} of the particle.
     * <p>
     *
     * @return the destination {@link Location} of the particle.
     */
    public Location getDestination() {
        return destination;
    }

    /**
     * Gets the amount of ticks it will take the particle to travel.
     * <p>
     *
     * @return the travel time in ticks.
     */
    public int getTicks() {
        return ticks;
    }

    /**
     * Creates a new VibrationParticleOption instance with the data of
     * the current {@link VibrationData} instance.
     * <p>
     * Please note that this class is not supported in
     * any versions before 1.17 and could lead to errors
     * if used in legacy versions.
     * <p>
     *
     * @return a new VibrationParticleOption with the data of the current {@link VibrationData} instance.
     */
    @Override
    public Object toNMSData() {
        if (ParticleReflectionUtils.MINECRAFT_VERSION < 17 || getEffect() != ParticleEffect.VIBRATION)
            return null;
        Object start = ParticleReflectionUtils.createBlockPosition(getStart());
        Object dest = ParticleReflectionUtils.createBlockPosition(getDestination());
        try {
            Object source = ParticleConstants.BLOCK_POSITION_SOURCE_CONSTRUCTOR.newInstance(dest);
            Object path = ParticleConstants.VIBRATION_PATH_CONSTRUCTOR.newInstance(start, source, getTicks());
            return ParticleConstants.PARTICLE_PARAM_VIBRATION_CONSTRUCTOR.newInstance(path);
        } catch (Exception ex) {
            return null;
        }
    }

}
