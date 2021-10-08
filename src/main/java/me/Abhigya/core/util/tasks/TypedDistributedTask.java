package me.Abhigya.core.util.tasks;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.IntStream;

/**
 * A class that represents typed distributed tasks.
 * <p>
 *
 * @param <T> type of the work.
 */
public final class TypedDistributedTask< T > implements Runnable {

    /**
     * The action.
     */
    private final Consumer< T > action;

    /**
     * The distribution size.
     */
    private final int distributionSize;

    /**
     * The escape condition.
     */
    private final Predicate< T > escapeCondition;

    /**
     * The world-load matrix.
     */
    private final List< LinkedList< Supplier< T > > > suppliedValueMatrix;

    /**
     * The current position.
     */
    private int currentPosition = 0;

    /**
     * Constructs the class.
     * <p>
     *
     * @param action           The action
     * @param escapeCondition  The escape condition
     * @param distributionSize The distribution size
     */
    public TypedDistributedTask( final Consumer< T > action, final Predicate< T > escapeCondition, final int distributionSize ) {
        this.distributionSize = distributionSize;
        this.action = action;
        this.escapeCondition = escapeCondition;
        this.suppliedValueMatrix = new ArrayList<>( this.distributionSize );
        IntStream.range( 0, this.distributionSize )
                .< LinkedList< Supplier< T > > >mapToObj( index -> new LinkedList<>( ) )
                .forEach( this.suppliedValueMatrix::add );
    }

    /**
     * Adds the given workload into the {@link #suppliedValueMatrix}.
     * <p>
     *
     * @param workload The workload to add
     */
    public void add( final Supplier< T > workload ) {
        List< Supplier< T > > smallestList = this.suppliedValueMatrix.get( 0 );
        for ( int index = 0; index < this.distributionSize; index++ ) {
            if ( smallestList.size( ) == 0 ) {
                break;
            }
            final List< Supplier< T > > next = this.suppliedValueMatrix.get( index );
            final int size = next.size( );
            if ( size < smallestList.size( ) ) {
                smallestList = next;
            }
        }
        smallestList.add( workload );
    }

    @Override
    public void run( ) {
        this.suppliedValueMatrix.get( this.currentPosition ).removeIf( this::executeThenCheck );
        this.proceedPosition( );
    }

    /**
     * Executes the given value supplier then checks {@link #escapeCondition}.
     * <p>
     *
     * @param valueSupplier The value supplier to execute and check
     * @return {@code true} if {@link #escapeCondition} is null or the test succeeded.
     */
    private boolean executeThenCheck( final Supplier< T > valueSupplier ) {
        final T value = valueSupplier.get( );
        if ( this.action != null ) {
            this.action.accept( value );
        }
        return this.escapeCondition == null || this.escapeCondition.test( value );
    }

    /**
     * Processes the {@link #currentPosition}.
     */
    private void proceedPosition( ) {
        if ( ++this.currentPosition == this.distributionSize ) {
            this.currentPosition = 0;
        }
    }

}