package me.Abhigya.core.util.structure;

import org.bukkit.util.BlockVector;
import org.bukkit.util.NumberConversions;

public class Structure {

    protected final StructureModel model;

    protected final int x;
    protected final int y;
    protected final int z;

    public Structure(StructureModel model, int x, int y, int z) {
        this.model = model;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Structure(StructureModel model, double x, double y, double z) {
        this(model, NumberConversions.floor(x), NumberConversions.floor(y),
                NumberConversions.floor(z));
    }

    /**
     * Gets this structure's model.
     * <br>
     *
     * @return This structure's model.
     */
    public StructureModel getModel() {
        return model;
    }

    /**
     * Gets the origin to which this structure will be pasted.
     * <br>
     *
     * @param vector Vector object to copy into.
     * @return This structure's origin.
     */
    public BlockVector getOrigin(BlockVector vector) {
        vector.setX(x);
        vector.setY(y);
        vector.setZ(z);

        return vector;
    }

    /**
     * Gets the origin to which this structure will be pasted.
     * <br>
     *
     * @return This structure's origin.
     */
    public BlockVector getOrigin() {
        return getOrigin(new BlockVector());
    }

}
