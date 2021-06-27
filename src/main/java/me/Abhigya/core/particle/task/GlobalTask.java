package me.Abhigya.core.particle.task;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.List;

/**
 * A {@link ParticleTask} implementation to send particles
 * to every player that is currently online.
 *
 * @see ParticleTask
 */
public final class GlobalTask extends ParticleTask{

    /**
     * Creates a new {@link GlobalTask}.
     * <p>
     *
     * @param packets   {@link List} of packets
     * @param tickDelay Delay of ticks between each execution
     */
    public GlobalTask(List<Object> packets, int tickDelay) {
        super(packets, tickDelay);
    }

    /**
     * Returns a {@link Collection} of all {@link Player Players}
     * that are currently online using {@link Bukkit#getOnlinePlayers()}.
     * <p>
     *
     * @return {@link Collection} of all online {@link Player Players}.
     */
    @Override
    public Collection<Player> getTargetPlayers() {
        return (Collection<Player>) Bukkit.getOnlinePlayers();
    }

}
