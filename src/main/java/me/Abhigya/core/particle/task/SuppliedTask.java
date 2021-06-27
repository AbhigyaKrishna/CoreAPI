package me.Abhigya.core.particle.task;

import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * A {@link ParticleTask} implementation using a {@link Supplier}
 * to retrieve the target {@link Player Players}. The Supplier
 * is called each time {@link #getTargetPlayers()} is called.
 *
 * @see ParticleTask
 */
public final class SuppliedTask extends ParticleTask {

    /**
     * The {@link Supplier} used to retrieve the target {@link Player Players}
     */
    private final Supplier<Collection<Player>> supplier;

    /**
     * Creates a new {@link SuppliedTask}.
     * <p>
     *
     * @param packets   {@link List} of packets
     * @param tickDelay Delay of ticks between each execution
     * @param supplier  {@link Supplier} used to retrieve the {@link Collection} of target {@link Player Players}
     */
    public SuppliedTask(List<Object> packets, int tickDelay, Supplier<Collection<Player>> supplier) {
        super(packets, tickDelay);
        this.supplier = Objects.requireNonNull(supplier);
    }

    /**
     * Calls the {@link #supplier} to retrieve the
     * target {@link Player Players}
     * <p>
     *
     * @return {@link Supplier#get()} with {@link #supplier}
     */
    @Override
    public Collection<Player> getTargetPlayers() {
        return supplier.get();
    }

}