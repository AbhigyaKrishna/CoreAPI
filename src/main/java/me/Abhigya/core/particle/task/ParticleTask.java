package me.Abhigya.core.particle.task;

import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * A {@link ParticleTask} is a repeating task that sends
 * a {@link #packets List of packets} to target {@link Player Players}
 * with a custom {@link #tickDelay}. The implementations
 * of this class support different ways of retrieving the
 * list of targeted {@link Player Players}. Custom implementations
 * of this class are encouraged if none of the built-in tasks match.
 * <p>
 * <b>A more in depth explanation can be found on the</b> <a href="https://github.com/ByteZ1337/ParticleLib/wiki/ParticleTasks">Wiki</a>
 * <p>
 * This class holds the basic information needed by the
 * {@link TaskManager}.
 *
 * @author ByteZ
 * @see GlobalTask
 * @see TargetedTask
 * @see SingularTask
 * @see FilteredTask
 * @see SuppliedTask
 */
public abstract class ParticleTask {

    /**
     * A {@link List} of packets to be sent to the target {@link Player Players}
     */
    private final List<Object> packets;
    /**
     * The amount of ticks between each execution
     */
    private final int tickDelay;

    /**
     * Creates a new {@link ParticleTask}
     * <p>
     *
     * @param packets   {@link List} of packets
     * @param tickDelay Delay of ticks between each execution
     */
    public ParticleTask(List<Object> packets, int tickDelay) {
        this.packets = Objects.requireNonNull(packets);
        this.tickDelay = tickDelay;
    }

    /**
     * Gets the packets that should be sent to the target {@link Player Players}.
     * <p>
     *
     * @return Value of the {@link #packets} field
     */
    public List<Object> getPackets() {
        return packets;
    }

    /**
     * Gets the amount of ticks between each execution
     * <p>
     *
     * @return Value of the {@link #tickDelay} field
     */
    public int getTickDelay() {
        return tickDelay;
    }

    /**
     * Returns a {@link Collection} of {@link Player Players}
     * that will receive the {@link #packets}. This method
     * has to be implemented by every direct subclass of
     * {@link ParticleTask}
     * <p>
     *
     * @return {@link Collection} of {@link Player Players} that should receive the specified {@link #packets}
     */
    public abstract Collection<Player> getTargetPlayers();

}
