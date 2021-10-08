package me.Abhigya.core.util.tasks.schedule;

import me.Abhigya.core.util.tasks.Workload;

import java.util.function.Predicate;

/**
 * An abstract implementation for {@link Workload} and,
 * reschedules if {@link ConditionalScheduleWorkload#test(Object)} returns {@code true}.
 * <p>
 *
 * @param <T> The type of the element.
 */
public abstract class ConditionalScheduleWorkload< T > implements Workload, Predicate< T > {

    /**
     * The element to test {@link Workload#shouldExecute()}.
     */
    private final T element;

    protected ConditionalScheduleWorkload( T element ) {
        this.element = element;
    }

    @Override
    public final boolean reSchedule( ) {
        return this.test( this.element );
    }

    public T getElement( ) {
        return element;
    }

}