package me.Abhigya.core.particle.task;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * A {@link ParticleTask} implementation that only
 * targets a single {@link Player}.
 *
 * @see ParticleTask
 */
public final class SingularTask extends ParticleTask {

    /**
     * The {@link UUID} of the target {@link Player}
     */
    private final UUID target;

    /**
     * Creates a new {@link SingularTask}.
     * <p>
     *
     * @param packets   {@link List} of packets
     * @param tickDelay Delay of ticks between each execution
     * @param target    {@link UUID} of the target {@link Player}
     */
    public SingularTask(List<Object> packets, int tickDelay, UUID target) {
        super(packets, tickDelay);
        this.target = Objects.requireNonNull(target);
    }

    /**
     * Creates a new {@link SingularTask}.
     * <p>
     *
     * @param packets   {@link List} of packets
     * @param tickDelay Delay of ticks between each execution
     * @param target    The target {@link Player}
     */
    public SingularTask(List<Object> packets, int tickDelay, Player target) {
        super(packets, tickDelay);
        this.target = Objects.requireNonNull(target).getUniqueId();
    }

    /**
     * Gets a singleton list with the target {@link Player}
     * or an empty list if the player isn't online.
     * <p>
     *
     * @return Singleton list with the target {@link Player}
     */
    @Override
    public List<Player> getTargetPlayers() {
        Player player = Bukkit.getPlayer(target);
        return player == null ? Collections.EMPTY_LIST : Collections.singletonList(player);
    }

}
