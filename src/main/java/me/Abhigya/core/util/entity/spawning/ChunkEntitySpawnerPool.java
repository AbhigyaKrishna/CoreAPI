package me.Abhigya.core.util.entity.spawning;

import me.Abhigya.core.util.server.Version;
import org.bukkit.Chunk;

import java.util.Iterator;
import java.util.Stack;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Class for managing spawning entities chunk
 */
public class ChunkEntitySpawnerPool implements Runnable {

    /**
     * Spawners pool stack.
     */
    protected final Stack<ChunkEntitySpawner> spawners;
    protected ExecutorService executor;
    protected boolean started;

    public ChunkEntitySpawnerPool() {
        this.spawners = new Stack<>();
    }

    public void start() {
        if (executor == null) {
            executor = Executors.newSingleThreadExecutor();
            executor.execute(this);
        }
    }

    public void stop() {
        executor.shutdownNow();
        executor = null;
    }

    public boolean isTerminated() {
        return started && spawners.size() == 0;
    }

    public void submit(ChunkEntitySpawner... spawners) {
        for (ChunkEntitySpawner spawner : spawners) {
            this.spawners.addElement(spawner);
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public void run() {
        started = true; // marking as started

        while (spawners.size() > 0) {
            Iterator<ChunkEntitySpawner> iterator = spawners.iterator();

            while (iterator.hasNext()) {
                ChunkEntitySpawner spawner = iterator.next();
                Chunk chunk = spawner.getChunk();

                if (Version.getServerVersion().isNewerEquals(Version.v1_14_R1) ? chunk.isLoaded()
                        : chunk.getWorld().isChunkInUse(chunk.getX(), chunk.getZ())) {

                    spawner.accept(chunk);
                    iterator.remove();
                }
            }
        }

        if (executor != null) {
            executor.shutdownNow();
        }

        executor = null;
    }

}
