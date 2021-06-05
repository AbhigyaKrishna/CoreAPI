package me.Abhigya.core.util.structure;

import org.apache.commons.lang.Validate;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.util.BlockVector;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Map;

public class StructureModelBuilder {

    protected final Map<BlockVector, Material> types = new HashMap<>();

    public StructureModelBuilder() {
        // nothing to do
    }

    public StructureModelBuilder(StructureModel to_copy) {
        types.putAll(to_copy.getTypeMap());
    }

    public StructureModelBuilder set(Vector position, Material material) {
        Validate.notNull(position, "position cannot be null!");

        Vector cloned = position.clone();
        BlockVector block_position = cloned instanceof BlockVector
                ? (BlockVector) cloned : cloned.toBlockVector();

        if (material != null) {
            types.put(block_position, material);
        } else {
            types.remove(block_position);
        }

        return this;
    }

    public StructureModelBuilder set(BlockFace face, int distance, Material material) {
        return set(new BlockVector(
                face.getModX() * distance,
                face.getModY() * distance,
                face.getModZ() * distance), material);
    }

    public StructureModelBuilder set(BlockFace face, Material material) {
        return set(face, 1, material);
    }

    public StructureModelBuilder set(int x, int y, int z, Material material) {
        return set(new BlockVector(x, y, z), material);
    }

    public StructureModelBuilder clear(Vector position) {
        set(position, null);

        return this;
    }

    public StructureModelBuilder clear() {
        types.clear();

        return this;
    }

    public StructureModel build() {
        return new StructureModel(types);
    }

}
