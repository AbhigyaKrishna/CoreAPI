package me.Abhigya.core.util.tasks.workload;

import me.Abhigya.core.util.tasks.Workload;

import java.util.function.Predicate;

/**
 * An abstract implementation for {@link Workload} and,
 * computes the workload if the {@link ConditionalWorkload#shouldExecute()}
 * returns {@code true}.
 * <p>
 *
 * @param <T> The type of the element
 */
public abstract class ConditionalWorkload<T> implements Workload, Predicate<T> {

    /**
     * The element to test {@link Workload#shouldExecute()}.
     */
    private final T element;

    public ConditionalWorkload(T element) {
        this.element = element;
    }

    @Override
    public boolean shouldExecute() {
        return this.test(element);
    }

    public T getElement() {
        return element;
    }
}
