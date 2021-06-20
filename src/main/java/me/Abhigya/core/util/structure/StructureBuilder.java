package me.Abhigya.core.util.structure;

import me.Abhigya.core.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.util.BlockVector;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class StructureBuilder {

    protected final Structure structure;

    public StructureBuilder(Structure structure) {
        this.structure = structure;
    }

    public Structure getStructure() {
        return structure;
    }

    public StructureBuildTask build(World world) {
        StructureBuildTask build_task = new StructureBuildTask(this, world);

        Bukkit.getScheduler().runTask(Main.getInstance(), build_task);
        return build_task;
    }

    public StructureBuildTask buildAsynchronously(World world) {
        StructureBuildTask build_task = new StructureBuildTask(this, world);

        Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), build_task);
        return build_task;
    }

    /**
     * TODO: Description
     */
    protected static class StructureBuildTask implements Runnable {

        // TODO: implement a feature that allows developers to specify a priority by type, so some blocks will be placed before others to avoid bugs

        protected final StructureBuilder builder;
        protected final World world;
        protected final Map<BlockVector, Material> type_map;
        protected final int size;

        /**
         * <li> -1 = stopped.
         * <li> 0  = processing.
         * <li> 1  = paused.
         */
        protected int state;

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

        public double getProgress() {
            return (double) type_map.size() / size;
        }

        public void resume() {
            if (state != -1) {
                this.state = 0;
            } else {
                throw new IllegalStateException("Cannot resume a stopped build task!");
            }
        }

        public void pause() {
            this.state = 1;
        }

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
            Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
                @Override
                public void run() {
                    block.setType(type);
                    block.getState().update();
                }
            });
        }
    }

}
