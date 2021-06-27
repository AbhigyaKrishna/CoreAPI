package me.Abhigya.core.particle.task;

import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * A {@link ParticleTask} implementation that targets
 * a provided {@link Collection} of {@link Player Players}.
 * <p>
 * Please Note that players that leave the server aren't
 * automatically removed from the targets collection.
 *
 * @see ParticleTask
 */
public final class TargetedTask extends ParticleTask {

    /**
     * The {@link Player Players} that will receive the particles.
     */
    private final Collection<Player> targets;

    /**
     * Creates a new {@link TargetedTask}.
     * <p>
     *
     * @param packets   {@link List} of packets
     * @param tickDelay Delay of ticks between each execution
     * @param targets   {@link Collection} of {@link Player Players} that will receive the particles.
     */
    public TargetedTask(List<Object> packets, int tickDelay, Collection<Player> targets) {
        super(packets, tickDelay);
        this.targets = Objects.requireNonNull(targets);
    }

    /**
     * Returns the pre-specified {@link Collection} of target {@link Player Players}
     * <p>
     *
     * @return {@link #targets}
     */
    @Override
    public Collection<Player> getTargetPlayers() {
        return targets;
    }

}