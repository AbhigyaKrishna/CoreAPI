package me.Abhigya.core.util.hologram;

import org.bukkit.Axis;

public class HologramConfiguration {

    public static final float NAME_THRESHOLD = 0.2F;
    boolean autoCenter = true;
    private double offsetX = 0D;
    private double offsetY = 0D;
    private double offsetZ = 0D;
    private double lineSeparation = 0.25D;
    private double smallItemAboveSeparator = 0.75D;
    private double smallItemBelowSeparator = 0.55D;
    private double bigItemAboveSeparator = 1D;
    private double bigItemBelowSeparator = 0.25D;

    /**
     * Get a new hologram configuration instance
     * with the default values
     */
    public HologramConfiguration( ) {
    }

    /**
     * Initialize the hologram configuration
     *
     * @param offX     the hologram offset x
     * @param offY     the hologram offset y
     * @param offZ     the hologram offset z
     * @param lSep     the hologram text separation size
     * @param iSASep   the hologram small item above separation size
     * @param iSBSep   the hologram small item below separation size
     * @param iBASep   the hologram big item above separation size
     * @param iBBSep   the hologram big item below separation size
     * @param autoCent the hologram auto center status
     */
    public HologramConfiguration( double offX, double offY, double offZ, double lSep, double iSASep, double iSBSep, double iBASep, double iBBSep, boolean autoCent ) {
        offsetX = offX;
        offsetY = offY;
        offsetZ = offZ;
        lineSeparation = lSep;
        smallItemAboveSeparator = iSASep;
        smallItemBelowSeparator = iSBSep;
        bigItemAboveSeparator = iBASep;
        bigItemBelowSeparator = iBBSep;
        autoCenter = autoCent;
    }

    /**
     * Set the new offset configuration
     * for the specified axis
     *
     * @param axis      the axis
     * @param newOffset the new offset for the
     *                  specified axis
     */
    public void setOffset( Axis axis, double newOffset ) {
        switch ( axis ) {
            case X:
                offsetX = newOffset;
                break;
            case Y:
                offsetY = newOffset;
                break;
            case Z:
                offsetZ = newOffset;
                break;
        }
    }

    /**
     * Set the new offset configuration
     *
     * @param x the X offset
     * @param y the Y offset
     * @param z the Z offset
     */
    public void setOffset( double x, double y, double z ) {
        offsetX = x;
        offsetY = y;
        offsetZ = z;
    }

    /**
     * Set if the hologram should try to center
     * himself at block center when created
     *
     * @param status the auto center status
     */
    public void centerAutomatically( final boolean status ) {
        autoCenter = status;
    }

    /**
     * Get the offset configuration
     *
     * @return the offset configuration
     */
    public OffsetConfiguration getOffsetConfiguration( ) {
        return new OffsetConfiguration( this );
    }

    /**
     * Get the hologram separation between lines
     *
     * @return the hologram separation between lines
     */
    public double getLineSeparation( ) {
        return lineSeparation;
    }

    /**
     * Set the new separation size between text and item
     * lines
     *
     * @param separationSize the new separation size
     */
    public void setLineSeparation( double separationSize ) {
        lineSeparation = separationSize;
    }

    /**
     * Get the hologram separation between items
     *
     * @return the hologram separation between items
     */
    public double getSmallItemAboveSeparator( ) {
        return smallItemAboveSeparator;
    }

    /**
     * Set the new separation size between text and small item lines
     *
     * @param separationSize the new separation size
     */
    public void setSmallItemAboveSeparator( double separationSize ) {
        smallItemAboveSeparator = separationSize;
    }

    public double getSmallItemBelowSeparator( ) {
        return smallItemBelowSeparator;
    }

    /**
     * Set the new separation size between text and small item lines
     *
     * @param separationSize the new separation size
     */
    public void setSmallItemBelowSeparator( double separationSize ) {
        smallItemBelowSeparator = separationSize;
    }

    public double getBigItemAboveSeparator( ) {
        return bigItemAboveSeparator;
    }

    /**
     * Set the new separation size between text and big item lines
     *
     * @param separationSize the new separation size
     */
    public void setBigItemAboveSeparator( double separationSize ) {
        bigItemAboveSeparator = separationSize;
    }

    public double getBigItemBelowSeparator( ) {
        return bigItemBelowSeparator;
    }

    /**
     * Set the new separation size between text and big item lines
     *
     * @param separationSize the new separation size
     */
    public void setBigItemBelowSeparator( double separationSize ) {
        bigItemBelowSeparator = separationSize;
    }

    /**
     * Get if the hologram should center himself
     *
     * @return if the hologram centers himself
     */
    public final boolean isAutoCenter( ) {
        return autoCenter;
    }

    public static class OffsetConfiguration {

        private final double x, y, z;

        /**
         * Initialize the offset configuration
         *
         * @param configuration the hologram configuration
         */
        OffsetConfiguration( HologramConfiguration configuration ) {
            x = configuration.offsetX;
            y = configuration.offsetY;
            z = configuration.offsetZ;
        }

        /**
         * Get the Z offset axis
         *
         * @return the Z offset axis
         */
        public final double getX( ) {
            return x;
        }

        /**
         * Get the Z offset axis
         *
         * @return the Z offset axis
         */
        public final double getY( ) {
            return y;
        }

        /**
         * Get the Z offset axis
         *
         * @return the Z offset axis
         */
        public final double getZ( ) {
            return z;
        }

    }

}
