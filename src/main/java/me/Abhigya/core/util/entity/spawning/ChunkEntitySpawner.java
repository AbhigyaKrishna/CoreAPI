package me.Abhigya.core.util.entity.spawning;

import org.bukkit.Chunk;

import java.util.function.Consumer;

/**
 * Class to deal with entity spawning in chunks
 */
public abstract class ChunkEntitySpawner implements Consumer<Chunk> {

    protected final Chunk chunk;

    public ChunkEntitySpawner(Chunk chunk) {
        this.chunk = chunk;
    }

    public Chunk getChunk() {
        return chunk;
    }

}
