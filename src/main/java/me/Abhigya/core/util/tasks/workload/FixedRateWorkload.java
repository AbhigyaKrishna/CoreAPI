package me.Abhigya.core.util.tasks.workload;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * An abstract implementation for {@link ConditionalWorkload} and,
 * computes the workload per the given tick.
 */
public abstract class FixedRateWorkload extends ConditionalWorkload< Integer > {

    /**
     * The checked.
     */
    private final AtomicInteger checked = new AtomicInteger( 0 );

    /**
     * Constructs the class.
     * <p>
     *
     * @param ticksPerExecution Tick per execution
     */
    protected FixedRateWorkload( final int ticksPerExecution ) {
        super( ticksPerExecution );
    }

    @Override
    public final boolean test( final Integer ticksPerExecution ) {
        return this.checked.incrementAndGet( ) % ticksPerExecution == 0;
    }

}
