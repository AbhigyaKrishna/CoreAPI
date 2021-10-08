package me.Abhigya.core.util.structure;

import org.bukkit.util.BlockVector;
import org.bukkit.util.NumberConversions;

/**
 * Represents a class dealing with structures.
 */
public class Structure {

    protected final StructureModel model;

    protected final int x;
    protected final int y;
    protected final int z;

    /**
     * Constructs the structure.
     * <p>
     *
     * @param model {@link StructureModel} of structure
     * @param x     X-axis position
     * @param y     Y-axis position
     * @param z     Z-axis position
     */
    public Structure( StructureModel model, int x, int y, int z ) {
        this.model = model;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Constructs the structure.
     * <p>
     *
     * @param model {@link StructureModel} of structure
     * @param x     X-axis position
     * @param y     Y-axis position
     * @param z     Z-axis position
     */
    public Structure( StructureModel model, double x, double y, double z ) {
        this( model, NumberConversions.floor( x ), NumberConversions.floor( y ),
                NumberConversions.floor( z ) );
    }

    /**
     * Returns this structure's model.
     * <p>
     *
     * @return This structure's model
     */
    public StructureModel getModel( ) {
        return model;
    }

    /**
     * Returns the origin to which this structure will be pasted.
     * <p>
     *
     * @param vector Vector object to copy into
     * @return This structure's origin
     */
    public BlockVector getOrigin( BlockVector vector ) {
        vector.setX( x );
        vector.setY( y );
        vector.setZ( z );

        return vector;
    }

    /**
     * Returns the origin to which this structure will be pasted.
     * <p>
     *
     * @return This structure's origin
     */
    public BlockVector getOrigin( ) {
        return getOrigin( new BlockVector( ) );
    }

}
