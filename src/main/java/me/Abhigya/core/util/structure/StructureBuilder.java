package me.Abhigya.core.util.structure;

import me.Abhigya.core.main.CoreAPI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.util.BlockVector;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/** Represents a class dealing with building of the structure. */
public class StructureBuilder {

    protected final Structure structure;

    /**
     * Constructs the Structure builder.
     *
     * <p>
     *
     * @param structure Structure to build
     */
    public StructureBuilder(Structure structure) {
        this.structure = structure;
    }

    /**
     * Returns the structure.
     *
     * <p>
     *
     * @return {@link Structure}
     */
    public Structure getStructure() {
        return structure;
    }

    /**
     * Returns a synchronous structure builder task.
     *
     * <p>
     *
     * @param world World to build in
     * @return Synchronous {@link StructureBuildTask}
     */
    public StructureBuildTask build(World world) {
        StructureBuildTask build_task = new StructureBuildTask(this, world);

        Bukkit.getScheduler().runTask(CoreAPI.getInstance().getHandlingPlugin(), build_task);
        return build_task;
    }

    /**
     * Returns a asynchronous structure builder task.
     *
     * <p>
     *
     * @param world World to build in
     * @return Asynchronous {@link StructureBuildTask}
     */
    public StructureBuildTask buildAsynchronously(World world) {
        StructureBuildTask build_task = new StructureBuildTask(this, world);

        Bukkit.getScheduler()
                .runTaskAsynchronously(CoreAPI.getInstance().getHandlingPlugin(), build_task);
        return build_task;
    }

    /** Represents a implementation of {@link Runnable} class which builds the structure. */
    protected static class StructureBuildTask implements Runnable {

        // TODO: implement a feature that allows developers to specify a priority by type, so some
        // blocks will be placed before others to avoid bugs

        protected final StructureBuilder builder;
        protected final World world;
        protected final Map<BlockVector, Material> type_map;
        protected final int size;

        /**
         *
         *
         * <ul>
         *   <li>-1 = stopped
         *   <li>0 = processing
         *   <li>1 = paused
         * </ul>
         */
        protected int state;

        /**
         * Constructs the builder task class.
         *
         * <p>
         *
         * @param builder {@link StructureBuilder} to build
         * @param world World to build in
         */
        public StructureBuildTask(StructureBuilder builder, World world) {
            this.builder = builder;
            this.world = world;

            // here we're copying map contents
            // to avoid concurrent modifications
            this.type_map = new HashMap<>(builder.getStructure().getModel().getTypeMap());
            this.size = type_map.size();

            // marking as processing
            this.state = 0;
        }

        /**
         * Returns the progress of the builder.
         *
         * <p>
         *
         * @return Progress of builder
         */
        public double getProgress() {
            return (double) type_map.size() / size;
        }

        /** Resumes the structure builder. */
        public void resume() {
            if (state != -1) {
                this.state = 0;
            } else {
                throw new IllegalStateException("Cannot resume a stopped build task!");
            }
        }

        /** Pauses the structure builder. */
        public void pause() {
            this.state = 1;
        }

        /** Stops the structure builder. */
        public void stop() {
            this.state = -1;
        }

        @Override
        public void run() {
            Iterator<BlockVector> key_iterator = type_map.keySet().iterator();

            while (key_iterator.hasNext()) {
                if (state == 1) {
                    continue;
                } else if (state == -1) {
                    break;
                }

                BlockVector position = key_iterator.next();
                Material type = type_map.get(position);

                set(position, type);
                key_iterator.remove();
            }
        }

        protected void set(BlockVector position, Material type) {
            Structure structure = builder.getStructure();
            BlockVector origin = structure.getOrigin();

            int x = origin.getBlockX() + position.getBlockX();
            int y = origin.getBlockY() + position.getBlockY();
            int z = origin.getBlockZ() + position.getBlockZ();

            Block block = world.getBlockAt(x, y, z);

            // block set synchronously
            Bukkit.getScheduler()
                    .scheduleSyncDelayedTask(
                            CoreAPI.getInstance().getHandlingPlugin(),
                            new Runnable() {
                                @Override
                                public void run() {
                                    block.setType(type);
                                    block.getState().update();
                                }
                            });
        }
    }
}
