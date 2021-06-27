package me.Abhigya.core.particle.task;

import me.Abhigya.core.main.Main;
import me.Abhigya.core.particle.utils.ParticleUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * A manager to handle different {@link ParticleTask ParticleTasks}
 *
 * @see ParticleTask
 */
public final class TaskManager {

    /**
     * Singleton instance of the {@link TaskManager}
     */
    private final static TaskManager INSTANCE = new TaskManager();

    /**
     * Private constructor because this is a singleton class.
     */
    private TaskManager() {
    }

    /**
     * Starts a new Timer for the given task.
     * <p>
     *
     * @param task Task that should be added to the scheduler
     * @return Id of the BukkitTask which can be cancelled using {@link TaskManager#stopTask(int)}
     * @see TaskManager#stopTask(int)
     */
    public int startTask(ParticleTask task) {
        //noinspection CodeBlock2Expr
        int taskId = Bukkit.getScheduler().runTaskTimerAsynchronously(Main.getInstance(), () -> {
            ParticleUtils.sendBulk(task.getPackets(), task.getTargetPlayers());
        }, 0, task.getTickDelay()).getTaskId();

        return taskId;
    }

    /**
     * Stops a task that is currently running.
     * <p>
     *
     * @param taskId Id of the task to be stopped.
     */
    public void stopTask(int taskId) {
        Bukkit.getScheduler().cancelTask(taskId);
    }

    /**
     * Get the singleton instance of the {@link TaskManager}
     * <p>
     *
     * @return Singleton instance of the {@link TaskManager}
     */
    public static TaskManager getTaskManager() {
        return INSTANCE;
    }

    /**
     * Starts a new {@link GlobalTask}.
     * <p>
     *
     * @param packets   {@link List} of packets
     * @param tickDelay Delay of ticks between each execution
     * @return Id of the BukkitTask which can be cancelled using {@link TaskManager#stopTask(int)}
     * @see GlobalTask
     * @see TaskManager#stopTask(int)
     */
    public static int startGlobalTask(List<Object> packets, int tickDelay) {
        return getTaskManager().startTask(new GlobalTask(packets, tickDelay));
    }

    /**
     * Starts a new {@link TargetedTask}.
     * <p>
     *
     * @param packets   {@link List} of packets
     * @param tickDelay Delay of ticks between each execution
     * @param targets   {@link Collection} of {@link Player Players} that will receive the particles.
     * @return Id of the BukkitTask which can be cancelled using {@link TaskManager#stopTask(int)}
     * @see TargetedTask
     * @see TaskManager#stopTask(int)
     */
    public static int startTargetedTask(List<Object> packets, int tickDelay, Collection<Player> targets) {
        return getTaskManager().startTask(new TargetedTask(packets, tickDelay, targets));
    }

    /**
     * Starts a new {@link SingularTask}.
     * <p>
     *
     * @param packets   {@link List} of packets
     * @param tickDelay Delay of ticks between each execution
     * @param target    {@link UUID} of the target {@link Player}
     * @return Id of the BukkitTask which can be cancelled using {@link TaskManager#stopTask(int)}
     * @see SingularTask
     * @see TaskManager#stopTask(int)
     */
    public static int startSingularTask(List<Object> packets, int tickDelay, UUID target) {
        return getTaskManager().startTask(new SingularTask(packets, tickDelay, target));
    }

    /**
     * Starts a new {@link SingularTask}.
     * <p>
     *
     * @param packets   {@link List} of packets
     * @param tickDelay Delay of ticks between each execution
     * @param target    The target {@link Player}
     * @return Id of the BukkitTask which can be cancelled using {@link TaskManager#stopTask(int)}
     * @see SingularTask
     * @see TaskManager#stopTask(int)
     */
    public static int startSingularTask(List<Object> packets, int tickDelay, Player target) {
        return getTaskManager().startTask(new SingularTask(packets, tickDelay, target));
    }

    /**
     * Starts a new {@link FilteredTask}.
     * <p>
     *
     * @param packets   {@link List} of packets
     * @param tickDelay Delay of ticks between each execution
     * @param filter    {@link Predicate} to filter the {@link Player Players}
     * @return Id of the BukkitTask which can be cancelled using {@link TaskManager#stopTask(int)}
     * @see FilteredTask
     * @see TaskManager#stopTask(int)
     */
    public static int startFilteredTask(List<Object> packets, int tickDelay, Predicate<Player> filter) {
        return getTaskManager().startTask(new FilteredTask(packets, tickDelay, filter));
    }

    /**
     * Starts a new {@link SuppliedTask}.
     * <p>
     *
     * @param packets   {@link List} of packets
     * @param tickDelay Delay of ticks between each execution
     * @param supplier  {@link Supplier} used to retrieve the {@link Collection} of target {@link Player Players}
     * @return Id of the BukkitTask which can be cancelled using {@link TaskManager#stopTask(int)}
     * @see SuppliedTask
     * @see TaskManager#stopTask(int)
     */
    public static int startSuppliedTask(List<Object> packets, int tickDelay, Supplier<Collection<Player>> supplier) {
        return getTaskManager().startTask(new SuppliedTask(packets, tickDelay, supplier));
    }

}