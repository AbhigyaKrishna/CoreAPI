package me.Abhigya.core.util.tasks;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * A class that stores and computes {@link Workload} instances.
 */
public final class WorkloadThread implements Runnable {

    /**
     * The work deque.
     */
    private final Queue<Workload> deque = new ConcurrentLinkedQueue<>();

    /**
     * The maximum nano per tick.
     */
    private final long maxNanosPerTick;

    /**
     * The work thread id.
     */
    private final long workThreadId;

    /**
     * Constructs the class.
     * <p>
     *
     * @param workThreadId    Work thread id
     * @param maxNanosPerTick Maximum nano per tick
     */
    WorkloadThread(final long workThreadId, final long maxNanosPerTick) {
        this.workThreadId = workThreadId;
        this.maxNanosPerTick = maxNanosPerTick;
    }

    /**
     * Adds workload for execution.
     * <p>
     *
     * @param workload {@link Workload} for execution
     */
    public void add(Workload workload) {
        deque.add(workload);
    }

    @Override
    public void run() {
        final long stopTime = System.nanoTime() + this.maxNanosPerTick;
        final Workload first = this.deque.poll();
        if (first == null) {
            return;
        }
        this.computeWorkload(first);
        Workload workload;
        while (System.nanoTime() <= stopTime && (workload = this.deque.poll()) != null) {
            this.computeWorkload(workload);
            if (!first.reSchedule() && first.equals(workload)) {
                break;
            }
        }
    }

    /**
     * Runs {@link Workload#compute()} if {@link Workload#shouldExecute()} is {@code true}.
     * also reschedules if {@link Workload#reSchedule()} is {@code true}.
     * <p>
     *
     * @param workload Workload to compute
     */
    private void computeWorkload(final Workload workload) {
        if (workload.shouldExecute()) {
            workload.compute();
        }
        if (workload.reSchedule()) {
            this.deque.add(workload);
        }
    }
}