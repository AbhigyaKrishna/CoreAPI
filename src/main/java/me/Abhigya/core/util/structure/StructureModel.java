package me.Abhigya.core.util.structure;

import org.bukkit.Material;
import org.bukkit.util.BlockVector;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class StructureModel implements Cloneable {

    protected final Map<BlockVector, Material> types;
    protected final int x_size;
    protected final int y_size;
    protected final int z_size;

    public StructureModel(Map<BlockVector, Material> types) {
        this.types = new HashMap<>(types);

        // calculating dimensions
        int x_min = Integer.MAX_VALUE;
        int x_max = Integer.MIN_VALUE;
        int y_min = Integer.MAX_VALUE;
        int y_max = Integer.MIN_VALUE;
        int z_min = Integer.MAX_VALUE;
        int z_max = Integer.MIN_VALUE;

        for (BlockVector key : this.types.keySet()) {
            int block_x = key.getBlockX();
            int block_y = key.getBlockY();
            int block_z = key.getBlockZ();

            if (block_x < x_min) {
                x_min = block_x;
            } else if (block_x > x_max) {
                x_max = block_x;
            }

            if (block_y < y_min) {
                y_min = block_y;
            } else if (block_y > y_max) {
                y_max = block_y;
            }

            if (block_z < z_min) {
                z_min = block_z;
            } else if (block_z > z_max) {
                z_max = block_z;
            }
        }

        this.x_size = Math.abs(x_min) + Math.abs(x_max) + 1;
        this.y_size = Math.abs(y_min) + Math.abs(y_max) + 1;
        this.z_size = Math.abs(z_min) + Math.abs(z_max) + 1;
    }

    public Map<BlockVector, Material> getTypeMap() {
        return Collections.unmodifiableMap(types);
    }

    public BlockVector getDimensions(BlockVector vector) {
        vector.setX(x_size);
        vector.setY(y_size);
        vector.setZ(z_size);

        return vector;
    }

    public BlockVector getDimensions() {
        return getDimensions(new BlockVector());
    }

    @Override
    public StructureModel clone() {
        try {
            return (StructureModel) super.clone();
        } catch (CloneNotSupportedException ex) {
            throw new Error(ex);
        }
    }

}
