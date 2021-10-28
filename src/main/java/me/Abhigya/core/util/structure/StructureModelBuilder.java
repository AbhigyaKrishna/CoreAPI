package me.Abhigya.core.util.structure;

import org.apache.commons.lang.Validate;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.util.BlockVector;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Map;

/** Represents a class handling {@link StructureModel} builder. */
public class StructureModelBuilder {

    protected final Map<BlockVector, Material> types = new HashMap<>();

    /** Constructs an uninitialized {@link StructureModelBuilder}. */
    public StructureModelBuilder() {
        // nothing to do
    }

    /**
     * Constructs the structure model builder.
     *
     * <p>
     *
     * @param to_copy Model to copy the blocks from
     */
    public StructureModelBuilder(StructureModel to_copy) {
        types.putAll(to_copy.getTypeMap());
    }

    /**
     * Sets a new blocks in the structure model.
     *
     * <p>
     *
     * @param position Positional block vector
     * @param material Material of block
     * @return This Object, for chaining
     */
    public StructureModelBuilder set(Vector position, Material material) {
        Validate.notNull(position, "Position cannot be null!");

        Vector cloned = position.clone();
        BlockVector block_position =
                cloned instanceof BlockVector ? (BlockVector) cloned : cloned.toBlockVector();

        if (material != null) {
            types.put(block_position, material);
        } else {
            types.remove(block_position);
        }

        return this;
    }

    /**
     * Sets a new blocks in the structure model.
     *
     * <p>
     *
     * @param face Block face of block
     * @param distance Distance
     * @param material Material of block
     * @return This Object, for chaining
     */
    public StructureModelBuilder set(BlockFace face, int distance, Material material) {
        return set(
                new BlockVector(
                        face.getModX() * distance,
                        face.getModY() * distance,
                        face.getModZ() * distance),
                material);
    }

    /**
     * Sets a new blocks in the structure model.
     *
     * <p>
     *
     * @param face Block face of block
     * @param material Material of block
     * @return This Object, for chaining
     */
    public StructureModelBuilder set(BlockFace face, Material material) {
        return set(face, 1, material);
    }

    /**
     * Sets a new blocks in the structure model.
     *
     * <p>
     *
     * @param x X-axis position
     * @param y Y-axis position
     * @param z Z-axis position
     * @param material Material of block
     * @return This Object, for chaining
     */
    public StructureModelBuilder set(int x, int y, int z, Material material) {
        return set(new BlockVector(x, y, z), material);
    }

    /**
     * Remove a block vector position from model.
     *
     * <p>
     *
     * @param position Position to remove
     * @return This Object, for chaining
     */
    public StructureModelBuilder clear(Vector position) {
        set(position, null);

        return this;
    }

    /**
     * Remove all vector positions from model.
     *
     * <p>
     *
     * @return This Object, for chaining
     */
    public StructureModelBuilder clear() {
        types.clear();

        return this;
    }

    /**
     * Builds the structure model and return it.
     *
     * <p>
     *
     * @return {@link StructureModel}
     */
    public StructureModel build() {
        return new StructureModel(types);
    }
}
