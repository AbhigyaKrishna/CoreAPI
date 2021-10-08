package me.Abhigya.core.util.tasks;

import java.util.HashMap;
import java.util.Map;

/**
 * A class that stores and distributes {@link WorkloadThread} instances.
 */
public final class WorkloadDistributor implements Runnable {

    /**
     * The work load thread map.
     */
    private final Map< Long, WorkloadThread > map = new HashMap<>( );

    /**
     * The next work load id.
     */
    private long nextId = 0L;

    /**
     * Creates a new {@link WorkloadThread} instance.
     * <p>
     *
     * @param nanoPerTick Nano per tick
     * @return New {@link WorkloadThread} instance
     */
    public WorkloadThread createThread( final long nanoPerTick ) {
        final WorkloadThread thread = new WorkloadThread( ++this.nextId, nanoPerTick );
        this.map.put( this.nextId, thread );
        return thread;
    }

    @Override
    public void run( ) {
        this.map.values( ).forEach( WorkloadThread::run );
    }

}