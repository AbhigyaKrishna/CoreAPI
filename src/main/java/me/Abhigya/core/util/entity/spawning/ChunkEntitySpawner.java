package me.Abhigya.core.util.entity.spawning;

import org.bukkit.Chunk;

import java.util.function.Consumer;

/** Class to deal with entity spawning in chunks */
public abstract class ChunkEntitySpawner implements Consumer<Chunk> {

    protected final Chunk chunk;

    /**
     * Chunk handle for spawning entity.
     *
     * <p>
     *
     * @param chunk {@link Chunk}
     */
    public ChunkEntitySpawner(Chunk chunk) {
        this.chunk = chunk;
    }

    /**
     * Returns the chunk handle
     *
     * <p>
     *
     * @return {@link Chunk}
     */
    public Chunk getChunk() {
        return chunk;
    }
}
