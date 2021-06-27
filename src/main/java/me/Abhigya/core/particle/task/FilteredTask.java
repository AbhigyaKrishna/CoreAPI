package me.Abhigya.core.particle.task;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * A {@link ParticleTask} implementation using a java
 * {@link Predicate} to only send the packets to {@link Player Players}
 * that match the filter.
 *
 * @see ParticleTask
 */
public final class FilteredTask extends ParticleTask {

    /**
     * The {@link Predicate} used to filter all online players
     */
    private final Predicate<Player> filter;

    /**
     * Creates a new {@link FilteredTask}
     * <p>
     *
     * @param packets   {@link List} of packets
     * @param tickDelay Delay of ticks between each execution
     * @param filter    {@link Predicate} to filter the {@link Player Players}
     */
    public FilteredTask(List<Object> packets, int tickDelay, Predicate<Player> filter) {
        super(packets, tickDelay);
        this.filter = Objects.requireNonNull(filter);
    }

    /**
     * Uses the provided {@link #filter} to filter all
     * online {@link Player Players} and only returns
     * players that match the given {@link Predicate}.
     * <p>
     *
     * @return List of {@link Player Players} matching the {@link #filter}
     */
    @Override
    public Collection<Player> getTargetPlayers() {
        return Bukkit.getOnlinePlayers().stream().filter(filter).collect(Collectors.toList());
    }

}
