package me.Abhigya.core.util.tasks.schedule;

import java.util.concurrent.atomic.AtomicLong;

/**
 * An abstract implementation for {@link ConditionalScheduleWorkload} and,
 * computes the workload the given number times.
 */
public abstract class FixedScheduleWorkload extends ConditionalScheduleWorkload< AtomicLong > {

    /**
     * Constructs the class.
     * <p>
     *
     * @param numberOfExecutions Number of executions
     */
    protected FixedScheduleWorkload( final AtomicLong numberOfExecutions ) {
        super( numberOfExecutions );
    }

    /**
     * Constructs the class.
     * <p>
     *
     * @param numberOfExecutions Number of executions
     */
    protected FixedScheduleWorkload( final long numberOfExecutions ) {
        this( new AtomicLong( numberOfExecutions ) );
    }

    @Override
    public final boolean test( final AtomicLong atomicInteger ) {
        return atomicInteger.decrementAndGet( ) > 0L;
    }

}
