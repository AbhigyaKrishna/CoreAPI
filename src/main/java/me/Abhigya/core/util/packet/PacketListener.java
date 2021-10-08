package me.Abhigya.core.util.packet;

/**
 * Simple interface called when receiving or sending packets.
 */
public interface PacketListener {

    /**
     * Called when receiving a packet.
     * <p>
     *
     * @param event the packet event.
     */
    public void onReceiving( final PacketEvent event );

    /**
     * Called when sending a packet.
     * <p>
     *
     * @param event the packet event.
     */
    public void onSending( final PacketEvent event );

    /**
     * Enumeration for Packet Event priority.
     */
    public static enum Priority {

        /**
         * Very low importance and should be run first.
         */
        LOWEST( 0 ),

        /**
         * Low importance.
         */
        LOW( 1 ),

        /**
         * Neither important nor unimportant, and may be run normally.
         */
        NORMAL( 2 ),

        /**
         * High importance
         */
        HIGH( 3 ),

        /**
         * Critical and must have the final say in what happens.
         */
        HIGHEST( 4 ),

        /**
         * Listened to purely for monitoring the outcome of an event.
         * <p>
         * No modifications to the packet should be made under this priority
         */
        MONITOR( 5 );

        private final int slot;

        private Priority( final int slot ) {
            this.slot = slot;
        }

        public int getSlot( ) {
            return slot;
        }
    }

}
